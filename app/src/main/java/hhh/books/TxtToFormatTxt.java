package hhh.books;

import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Created by huanghh on 2017/6/20.
 */
public class TxtToFormatTxt {
    public static void main(String[] args) throws IOException {
        String txtFileName = "目录.txt";
        String targetFileName = "目录2.txt";

        String destFileDir = "D:/3H/mm/function/platforms/bigdata/framework/spark/books/spark核心源码分析与开发实战";
        String txtFilePath = String.format("%s/%s", destFileDir,txtFileName);
        String targetFilePath = String.format("%s/%s", destFileDir,targetFileName);

        File targetFile = new File(targetFilePath);

        File file = new File(txtFilePath);

        if (!targetFile.exists()) {
//            FileUtils.copyFile(file,targetFile);
            targetFile.createNewFile();
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String currentLineStr = reader.readLine();
            FileWriter fileWritter = new FileWriter(targetFile,true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            while (currentLineStr != null) {
                String targetStr = getFormatLine(currentLineStr);

                bufferWritter.write(targetStr);
                bufferWritter.write("\n");

//                System.out.println(String.format("srcStr:%s replace target:%s",currentLineStr,targetStr));

                currentLineStr = reader.readLine();
            }
            bufferWritter.close();
            fileWritter.close();
        } catch (Exception e) {
            //当抛出多个异常时，子异常当在父异常前抛出。
            e.printStackTrace();
        }
//
    }

    private static String getFormatLine(String line) {

        if (CreateDirsByTxt.isChapter(line)) {
            return line;
        }
        if (isSkip(line)) {
            return "";
        }


        String[] strs = line.split("\\.");
        int len = strs.length;

//        System.out.println(line.indexOf("."));
        int lastPointIndex = line.indexOf(".",len-1)+2;

        StringBuffer lineSb = new StringBuffer();
        lineSb.append(line.substring(0,lastPointIndex));
        lineSb.append(" ");
        lineSb.append(line.substring(lastPointIndex,line.length()));
        lineSb.append(" ");
        lineSb.append(" ");
        line = lineSb.toString();
        return line;
    }

    private static boolean isSkip(String line) {
        boolean isSkip = line.startsWith("思");
        return isSkip;
    }
}
