package hhh.books;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/**
 * Created by huanghh on 2017/4/17.
 */

public class TextFileUtils {

    public static void main(String[] args) throws IOException {
        String sectionStr = "helloworld我叼";

        String srcFilePath = "D:/3H/compMsg/brightoil/template/freemind/temp.mm";
        String targetFileDirPath = "D:/3H/compMsg/brightoil/template/freemind";
        String srcStr = "<node CREATED=\"1472613963222\" ID=\"ID_698403439\" MODIFIED=\"1472613981311\" TEXT=";
        String targetStr = String.format("%s\"%s\"/>",srcStr,sectionStr);

        File srcFile = new File(srcFilePath);
        File targetFile = new File(targetFileDirPath,sectionStr+".mm");

        FileUtils.copyFile(srcFile,targetFile);
        updateFileTextContent(targetFile.getAbsolutePath(), srcStr, targetStr);
    }

    public static void updateFileTextContent(String targetFilePath, String srcStr, String targetStr) {

        SeekContentMsg seekContentMsg = readFileContent(targetFilePath, srcStr);
        if (seekContentMsg.getSeek() < 0) {
            return;
        } else {
            writeFileContent(targetFilePath,targetStr, seekContentMsg);
        }
    }

    /**
     * 搜索内容的信息
     */
    private static class SeekContentMsg {
        private long seek;
        private String srcLineStr;

        public SeekContentMsg(long initSeek, String initStr) {
            seek = initSeek;
            srcLineStr = initStr;
        }

        public long getSeek() {
            return seek;
        }

        public void setSeek(long seek) {
            this.seek = seek;
        }

        public String getSrcLineStr() {
            return srcLineStr;
        }

        public void setSrcLineStr(String srcLineStr) {
            this.srcLineStr = srcLineStr;
        }
    }

    //读取文本文件
    private static SeekContentMsg readFileContent(String filePath, String findStr) {
        File file = new File(filePath);
        long previousFilePointer = -1;
        SeekContentMsg seekContentMsg = new SeekContentMsg(previousFilePointer,"");
        try {

            if (file.isFile() && file.exists()) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                String lineStr = null;
                while ((lineStr = raf.readLine()) != null) {
//                System.out.println(lineStr);
                    if (lineStr.contains(findStr)) {
//                    System.out.println("stop:"+seek);
                        seekContentMsg.setSeek(previousFilePointer);
                        seekContentMsg.setSrcLineStr(lineStr);
                        return seekContentMsg;
                    } else {
                        previousFilePointer = raf.getFilePointer();
                    }
                }
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seekContentMsg;
    }

    private static void writeFileContent(String filePath,  String targetStr, SeekContentMsg seekContentMsg) {
        String srcLineStr = seekContentMsg.getSrcLineStr();

        targetStr = getReplenishStr(srcLineStr,targetStr);

        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
//            RandomAccessFile raf = new RandomAccessFile(file, "rw");
//            raf.seek(seekContentMsg.getSeek());
//            raf.write(targetStr.getBytes(), 0, targetStr.length());
//            raf.close();
            replaceTxtByStr(file,srcLineStr,targetStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceTxtByStr(File file,String oldStr,String replaceStr) {
        String temp = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该行前面的内容
            for (int j = 1; (temp = br.readLine()) != null
                    && !temp.equals(oldStr); j++) {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }

            // 将内容插入
            buf = buf.append(replaceStr);

            // 保存该行后面的内容
            while ((temp = br.readLine()) != null) {
                buf = buf.append(System.getProperty("line.separator"));
                buf = buf.append(temp);
            }

            br.close();
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 为目的文本补充空格
     * @param srcStr
     * @param targetStr
     * @return
     */
    private static String getReplenishStr(String srcStr, String targetStr) {
        int srcLineLen = srcStr.length();
        int targetLineLen = targetStr.length();
        /***
         * //为了不让原行文本还存在，而无法进行覆盖重写，导致格式出错
         */
        if (targetLineLen < srcLineLen) {
            //补充空格数量
            int replenish = srcLineLen - targetLineLen;
            StringBuffer sb = new StringBuffer();
            sb.append(targetStr);
            for (int i = 0; i < replenish; i++) {
                sb.append(" ");
            }
            targetStr = sb.toString();
        }
        return targetStr;
    }


}
