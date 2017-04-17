package hhh.books;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.io.FileUtils;

public class CreateDirsByTxt {
	public static void main(String[] args) {
		
		
		
		String copyFilePath = "D:/3H/compMsg/brightoil/template/freemind/temp.mm";
		
		String destFileDir = "D:/3H/compMsg/brightoil/function/books/Android安全技术揭秘与防范";
		//该文件文本内容必须使用UTF-8
		String txtFileName = "目录.txt";
		
		String txtFilePath = String.format("%s/%s", destFileDir,txtFileName);
		
		File file = new File(txtFilePath);
		
		
		printFileContent(file,new RunParam() {
			@Override
			public Object run(Object o0,Object o1) {
				if (o0 == null) {
					return null;
				}
				if (o1 == null) {
					return null;
				}
				
				BufferedReader reader = (BufferedReader) o0;
				
				String currentLineStr = o1.toString();
//				try {
//					currentLineStr = new String(o1.toString().getBytes("UTF-8"));
//				} catch (UnsupportedEncodingException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
				boolean isChapter = isChapter(currentLineStr);
				
				String chapterStr = currentLineStr;
				
				if (isChapter) {
					System.out.println(chapterStr);
					File chapterDir = new File(destFileDir,chapterStr);
					if (!chapterDir.exists()) {
						chapterDir.mkdir();
					}
					
					try {
						currentLineStr = handlerReadStrSpecialStr(reader);
						
						while (isSection(currentLineStr, copyFilePath, chapterDir.getAbsolutePath())) {
							
							currentLineStr = handlerReadStrSpecialStr(reader);

						}
					} catch (Exception e) {
						System.err.println(e);
						e.printStackTrace();
											}
				}else {
					System.out.println("无法进入下一行");
				}
				return currentLineStr;
			}

			
		});    
	}
	
	private static String handlerReadStrSpecialStr(BufferedReader reader) throws IOException {
		String resultStr = null;
		resultStr =	reader.readLine();
		if (resultStr!=null) {
			resultStr = resultStr.replaceAll("/", "-").replaceAll("\\t","").replaceAll(" ","").trim();
		}
		return resultStr;
	}

	protected static boolean isSection(String readLineStr,String srcFilePath,String destFileDir) throws IOException {
		if (isNull(readLineStr)) {
			return false;
		}
		String headStr = readLineStr.substring(0, 1);
		String numRe = "(-?\\d+)(\\.\\d+)?";
		
		boolean isSection = headStr.matches(numRe);
		
		if (isSection) {
			System.out.println(readLineStr);
			FileUtils.copyFile(new File(srcFilePath), new File(destFileDir,readLineStr+".mm"));
		}
		
		testLast(readLineStr);
		
		return isSection;
	}

	private static void testLast(String readLineStr) {
		String endStr = "7.8 本章总结 306";
		if (endStr.equals(readLineStr)) {
			System.out.println("End");
		}
	}

	private static boolean isNull(String readLineStr) {
		return readLineStr == null||1 > readLineStr.length();
	}
	/**
	 * 因为有些时候，因为文本编码问题，
	 * 导致获取第一位字符是其他字符，又因为通常章节都包含“第”字
	* isChapter:(TODO)<br/> 
	* date: 2016年7月5日 上午10:48:24 <br/> 
	* @author sam3h 
	* @param readLineStr
	* @return
	 */
	protected static boolean isChapter(String readLineStr) {
		if (1 > readLineStr.length()) {
			return false;
		}
		boolean isChapter = readLineStr.contains("第");
		isChapter = isChapter||readLineStr.contains("附");

		return isChapter;
	}

	private static void printFileContent(File file, RunParam runnable) {
		try {    
            FileReader fr = new FileReader(file);    
            BufferedReader reader = new BufferedReader(fr);    
            String currentLineStr = handlerReadStrSpecialStr(reader);    
            while (currentLineStr != null) {    
            	Object o = runnable.run(reader,currentLineStr);
            	currentLineStr = (o!=null?String.valueOf(o):null);
//                str = reader.readLine();    
            }    
        } catch (FileNotFoundException e) {   
            //当抛出多个异常时，子异常当在父异常前抛出。  
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }
	}
	
	public interface RunParam{
		public abstract Object run(Object o0,Object o1);
	}
}
