package hhh.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class ReplaceTxtLineHead {
	


	public static final String CHARSET_GBK = "GBK";
	public static final String CHARSET_UTF8 = "UTF-8";

	public static String CHARSET_TXT = CHARSET_UTF8;

	public static void changeDirTextFile(File changeDirOrFile) {
		if (changeDirOrFile.isDirectory()) {
			for (File file : changeDirOrFile.listFiles()) {
				if (file.isDirectory()) {
					changeDirTextFile(file);
				}else {
//					System.out.println(file.getName());
					doReplace(file);
				}
			}
		}else {
			doReplace(changeDirOrFile);
		}
		
	}


	private static void doReplace(File file) {
		replaceTxtByStr(file,"INFO:root:", "");
		replaceTxtByStr(file,"&nbsp;", "");
	}
	
	
    /***
     * 将文件中指定内容的第一行替换为其它内容.
     * 
     * @param textFile 一个文本文件
     * @param oldStr 查找内容
     * @param replaceStr 替换内容
     */
    public static void replaceTxtByStr(File textFile,String oldStr,String replaceStr) {
        String temp = "";
        try {
            FileInputStream fis = new FileInputStream(textFile);
            InputStreamReader isr = new InputStreamReader(fis,CHARSET_TXT);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该行前面的内容
            for (int j = 1; (temp = br.readLine()) != null
                    && !temp.equals(oldStr); j++) {
                buf = buf.append(temp.replaceAll(oldStr, replaceStr));
                buf = buf.append(System.getProperty("line.separator"));
            }


            // 保存该行后面的内容
            while ((temp = br.readLine()) != null) {
                buf = buf.append(System.getProperty("line.separator"));
                buf = buf.append(temp);
            }

            br.close();
//            FileOutputStream fos = new FileOutputStream(textFile);
            PrintWriter pw = new PrintWriter(textFile.getAbsolutePath(),CHARSET_TXT);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
