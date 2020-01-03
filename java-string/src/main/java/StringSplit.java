import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>StringSplit</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2019/12/11 11:10.
 *
 * @author cgx
 * @since KnowledgeRecords
 */
public class StringSplit {

    public static void main(String[] args) {
        String str = "尊敬的市民：2019深圳国际马拉松将于12月15日早上八点在市民广场鸣枪起跑，比赛途径福田区、南山区、罗湖区，沿深南大道、沙河西路、望海路绕行。为保障您的观赛安全，" +
                "请听从现场安保人员引导指挥，不拥挤、不起哄，彰显深圳市民文明风采。感谢您的理解与配合！【深圳市文化广电旅游体育局】";
        int num = 70;
        List<String> strings = stringSplit(str, num);
        strings.stream().forEach(f ->{
            System.out.println(f);
        });
    }

    private static List<String> stringSplit(String str, int num) {
        int lines = (str.length() + (num - 1)) / num;
        char[] chars = str.toCharArray();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            int startIndex = num * i;
            int endIndex = num * (i + 1);
            if (endIndex >= chars.length) {
                endIndex = chars.length;
            }
            String s = str.substring(startIndex, endIndex);
            stringList.add(s);
        }
        return stringList;
    }
}
