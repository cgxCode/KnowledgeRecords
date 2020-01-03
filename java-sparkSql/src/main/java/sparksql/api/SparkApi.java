package sparksql.api;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.hive.HiveContext;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple1;
import scala.Tuple2;

import java.util.List;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.split;

/**
 * <b><code>SparkApi</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/1/2 9:55.
 *
 * @author cgx
 * @since KnowledgeRecords
 */
public class SparkApi {

    private JavaSparkContext javaSparkContext;

    private HiveContext hiveContext;

    private DataFrame dataFrame;

    @Before
    public void before(){
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("SparkApiTest");
        javaSparkContext = new JavaSparkContext(conf);
        hiveContext = new HiveContext(javaSparkContext);
        dataFrame = hiveContext.read().text("D:\\user\\text.txt")
                .withColumn("siteLineRel", split(col("value"), ","))
                .select(col("siteLineRel").getItem(0).as("id"),
                        col("siteLineRel").getItem(1).as("num"),
                        col("siteLineRel").getItem(2).as("name"))
                .drop("siteLineRel");
    }

    @Test
    public void sortTest(){
        dataFrame.sort("id").show();
        dataFrame.sort("num").show();
        dataFrame.sort("name").show();
    }

    @Test
    public void collectTest(){
        Row[] collect = dataFrame.collect();
        List<Row> rows = dataFrame.collectAsList();
    }

    @Test
    public void describeTest(){
        DataFrame describe = dataFrame.describe("id", "num", "name");
        describe.show();
    }

    @Test
    public void whereTest(){
        DataFrame filterDataFrame = dataFrame.where("id=1");
        filterDataFrame.show();
        DataFrame frame = dataFrame.where("id=1 and num = 4");
        frame.show();
    }

    @Test
    public void createDataFrameTest(){
        // 1. 创建dataframe
        //一般文件
        DataFrame dataFrame1 = hiveContext.read().text("D:\\user\\text.txt")
                .withColumn("siteLineRel", split(col("value"), ","))
                .select(col("siteLineRel").getItem(0).as("id"),
                        col("siteLineRel").getItem(1).as("num2"),
                        col("siteLineRel").getItem(2).as("name"))
                .drop("siteLineRel");
        dataFrame1.show();
    }

    @Test
    public void createDataFrameForParquet(){
//        parquet文件
        DataFrame dataFrame = hiveContext.read()
                .parquet("D:\\dm\\dmp_ztf\\gdtc\\nil-metro\\nil_metro_site_line_rel.csv");
        dataFrame.show();
    }

    @Test
    public void groupByTest(){
        dataFrame.registerTempTable("test");
        DataFrame frame = hiveContext.sql("select name from test group by id having count(id) >=3");
        frame.show();
    }
}
