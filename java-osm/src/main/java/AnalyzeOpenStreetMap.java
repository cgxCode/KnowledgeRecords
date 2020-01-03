import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/**
 * <b><code>AnalyzeOpenStreetMap</code></b>
 * <p/>
 * 处理从OpenStreetMap下载的原始数据，将抽取的数据输出为txt文件
 * <p/>
 * <b>Creation Time:</b> 2019/12/5 14:54.
 *
 * @author cgx
 * @since KnowledgeRecords
 */
public class AnalyzeOpenStreetMap {
    public static void main(String[] args) throws Exception {
        //读取原始的地图数据
        SAXReader saxReader = new SAXReader();
        String path = "C:\\Users\\Administrator\\Desktop\\OSM\\interpreter";
        Document document = saxReader.read(new File(path));

        //先处理点信息
        File pointFile = new File("C:\\Users\\Administrator\\Desktop\\OSM\\Point.txt");
        FileOutputStream fosPoint = new FileOutputStream(pointFile);
        OutputStreamWriter oswPoint = new OutputStreamWriter(fosPoint);
        BufferedWriter bwPoint = new BufferedWriter(oswPoint);
        //线信息
        File arcFile = new File("C:\\Users\\Administrator\\Desktop\\OSM\\Arc.txt");
        FileOutputStream fosArc = new FileOutputStream(arcFile);
        OutputStreamWriter oswArc = new OutputStreamWriter(fosArc);
        BufferedWriter bwArc = new BufferedWriter(oswArc);

        //获取根节点元素对象 osm
        Element rootElements = document.getRootElement();
        //遍历osm下的所有子节点
        Iterator<Element> rootElementIterator = rootElements.elementIterator();
        while (rootElementIterator.hasNext()) {
            Element rootElement = rootElementIterator.next();

            //输出点信息
            if ("node".equals(rootElement.getName())) {
                StringBuilder stringBuilder = new StringBuilder();
                //首先获取当前节点的所有属性节点
                List<Attribute> attributeList = rootElement.attributes();
                //遍历属性节点
                for (Attribute attribute : attributeList) {
                    if ("id".equals(attribute.getName())) {
                        stringBuilder.append(attribute.getValue() + ",");
                    }
                    if ("lat".equals(attribute.getName())) {
                        stringBuilder.append(attribute.getValue() + ",");
                    }
                    if ("lon".equals(attribute.getName())) {
                        stringBuilder.append(attribute.getValue());
                    }
                }
                bwPoint.write(stringBuilder.toString() + "\r\n");
                bwPoint.flush();
            }else if("way".equals(rootElement.getName())){
                StringBuilder wayStringBuilder = new StringBuilder();
                String s = "";
                List<Attribute> wayAttributesList = rootElement.attributes();
                for (Attribute wayAttribute: wayAttributesList) {
                    if("id".equals(wayAttribute.getName())){
                        s += wayAttribute.getValue()+ ",";
                    }
                }
                Iterator<Element> childElements = rootElement.elementIterator();
                while (childElements.hasNext()){
                    Element childElement = childElements.next();
                    List<Attribute> childAttributeList = childElement.attributes();
                    for(Attribute childAttribute:childAttributeList){
                        if("ref".equals(childAttribute.getName())){
                            wayStringBuilder.append(s + childAttribute.getValue()+","+ "\r\n");
                        }
                        if("k".equals(childAttribute.getName())){
                            if("name:zh".equals(childAttribute.getValue())){
                                if("v".equals(childAttribute.getName())){
                                    wayStringBuilder.append(s + ",,"+childAttribute.getValue()+ "\r\n");
                                }
                            }
                        }
                    }
                }
                bwArc.write(wayStringBuilder.toString());
                bwArc.flush();
            }

        }
        bwPoint.close();
        oswPoint.close();
        fosPoint.close();
        bwArc.close();
        oswArc.close();
        fosArc.close();
        System.out.println("输出完成！");
    }
}
