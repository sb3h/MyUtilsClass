package hhh;

/**
 * Created by huanghh on 2017/6/20.
 */
public class HelloWorld {
    public static void main(String[] args) {
        String section = "1.2Spark生态系统BDAS";
        String mm = "1.2.1Spark Core";
        String line = mm;



//        switch (len) {
//            case 2:
//                System.out.println(line.indexOf("."));
//                line = line.substring(line.indexOf(".",len-1)+2,line.length());
//                break;
//            case 3:
//
//                break;
//        }
        line = getFormatLine(line);
        System.out.println(line.toString());
    }

    private static String getFormatLine(String line) {
        String[] strs = line.split("\\.");
        int len = strs.length;

        System.out.println(line.indexOf("."));
        int lastPointIndex = line.indexOf(".",len-1)+2;

        StringBuffer lineSb = new StringBuffer();
        lineSb.append(line.substring(0,lastPointIndex));
        lineSb.append(" ");
        lineSb.append(line.substring(lastPointIndex,line.length()));
        lineSb.append(" ");
        line = lineSb.toString();
        return line;
    }
}
