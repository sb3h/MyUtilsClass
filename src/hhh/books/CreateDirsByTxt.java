package hhh.books;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.io.FileUtils;

public class CreateDirsByTxt {
	public static void main(String[] args) {
		String txtFilePath = "E:/comp_doc/myselt/freemind/java/android/books/android_dp_analysis/android_dp_analysis.txt"; 
		File file = new File(txtFilePath);
		
		String copyFilePath = "D:/workspaces/study/language/java/android/eclipse/test/createDirsByTxt/src/createDirsByTxt/temp.mm";
		
		String destFileDir = "E:/comp_doc/myselt/freemind/java/android/books/android_dp_analysis"; 
		
		
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
				
				boolean isChapter = isChapter(currentLineStr);
				
				String chapterStr = currentLineStr;
				
				if (isChapter) {
					System.out.println(chapterStr);
					File chapterDir = new File(destFileDir,chapterStr);
					if (!chapterDir.exists()) {
						chapterDir.mkdir();
					}
					
					try {
						currentLineStr = reader.readLine();
						
						while (isSection(currentLineStr, copyFilePath, chapterDir.getAbsolutePath())) {
							
							currentLineStr = reader.readLine();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return currentLineStr;
			}
		});    
	}

	protected static boolean isSection(String readLineStr,String srcFilePath,String destFileDir) throws IOException {
		if (readLineStr == null||1 > readLineStr.length()) {
			return false;
		}
		String headStr = readLineStr.substring(0, 1);
		String numRe = "(-?\\d+)(\\.\\d+)?";
		
		boolean isSection = headStr.matches(numRe);
		
		if (isSection) {
			System.out.println(readLineStr);
			FileUtils.copyFile(new File(srcFilePath), new File(destFileDir,readLineStr+".mm"));
		}
		return isSection;
	}

	protected static boolean isChapter(String readLineStr) {
		if (1 > readLineStr.length()) {
			return false;
		}
		boolean isChapter = readLineStr.startsWith("第");

		return isChapter;
	}

	private static void printFileContent(File file, RunParam runnable) {
		try {    
            FileReader fr = new FileReader(file);    
            BufferedReader reader = new BufferedReader(fr);    
            String currentLineStr = reader.readLine();    
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
