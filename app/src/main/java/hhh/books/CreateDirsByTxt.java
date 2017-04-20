package hhh.books;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import hhh.books.bean.MMDom4jXml;


public class CreateDirsByTxt {

	public final static String destFileDir = "D:/3H/compMsg/brightoil/function/books/Android安全技术揭秘与防范";

	public final static String copyFilePath = "D:/3H/compMsg/brightoil/template/freemind/temp.mm";
	/**
	 * 该文本被替换时，是替换整行的
	 */
	public final static String copyFilePathReplaceStr = "<node CREATED=\"1472613963222\" ID=\"ID_698403439\" MODIFIED=\"1472613981311\" TEXT=";



	private static String createChapter(BufferedReader reader, String chapterStr) throws IOException, ParseException {
		String currentLineStr;
		System.out.println("章:"+chapterStr);
		File chapterDir = new File(destFileDir,chapterStr);
		String chapterDirStr = chapterDir.getAbsolutePath();
		if (!chapterDir.exists()) {
            chapterDir.mkdir();
        }
		currentLineStr = handlerReadStrSpecialStr(reader);
		String sectionStr = "";
		currentLineStr = createSection(reader, currentLineStr, chapterDir, chapterDirStr);
		return currentLineStr;
	}

	private static String createSection(BufferedReader reader, String currentLineStr, File chapterDir, String chapterDirStr) throws IOException, ParseException {
		String sectionStr;
		while(isSection(reader,currentLineStr, copyFilePath, chapterDirStr)){
            sectionStr = currentLineStr;
            currentLineStr = handlerReadStrSpecialStr(reader);
            //如果，想放在同一目录的话就传入“章”目录，而不是传入该“章节”目录
            List<String> sectionBars = new ArrayList<String>();
			currentLineStr = createSectionBar(reader, currentLineStr, chapterDir, sectionBars);
			//因为处理位置追加，过于麻烦，只好引入xml来定位，在进行追加
            System.out.println("sectionBars:"+sectionBars);
			if (!isChapter(sectionStr)) {
				File sectionFile = new File(chapterDirStr,sectionStr+".mm");
				System.out.println("sectionFile.getAbsolutePath():"+sectionFile.getAbsolutePath());
				MMDom4jXml mmXml = new MMDom4jXml(sectionFile.getAbsolutePath());
				for (int i = 0; i < sectionBars.size(); i++) {
					String sectionBar = sectionBars.get(i);
					mmXml.addNode(sectionBar);
				}
			}
		}
		return currentLineStr;
	}

	private static String createSectionBar(BufferedReader reader, String currentLineStr, File chapterDir, List<String> sectionBar) throws IOException, ParseException {
		while(isSectionBar(currentLineStr, copyFilePath,
                chapterDir
//								new File(chapterDirStr, sectionStr)
                )){
            sectionBar.add(currentLineStr);
            currentLineStr = handlerReadStrSpecialStr(reader);
        }
		return currentLineStr;
	}

	private static String handlerReadStrSpecialStr(BufferedReader reader) throws IOException {
		String resultStr = null;
		resultStr =	reader.readLine();
		if (resultStr!=null) {
			resultStr = resultStr.replaceAll("/", "-").replaceAll("\\t","").trim();
		}
		return resultStr;
	}

	/**
	 * 每一行的格式
	 */
	private final static MessageFormat lineFormat = new MessageFormat("{0} {1} {2}");

	protected static boolean isSection(BufferedReader reader,String readLineStr,String srcFilePath,String destFileDir)  {
		if (StringUtils.isEmpty(readLineStr)) {
			return false;
		}
		boolean isSection = false;
		try {
			Object[] strs = lineFormat.parse(readLineStr);
			String sectionNumStr = strs[0].toString();

			isSection = sectionNumStr.matches("\\d+\\.\\d+");

			if (isSection) {
				String sectionStr = readLineStr;
				createMMFile(sectionStr, srcFilePath, destFileDir);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		String headStr = readLineStr.substring(0, 1);
//		String numRe = "(-?\\d+)(\\.\\d+)?";
//
//		isSection = headStr.matches(numRe);
//
//		if (isSection) {
//			createMMFile(readLineStr, srcFilePath, destFileDir);
//		}
//
//		testLast(readLineStr);
//
		return isSection;
	}

	/**
	 * 是否 章小节
	 * @param currentLineStr
	 * @param srcFilePath
	 * @param sectionDir
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private static boolean isSectionBar(String currentLineStr, String srcFilePath, File sectionDir ) throws IOException, ParseException {
		if (StringUtils.isEmpty(currentLineStr)) {
			return false;
		}
		Object[] sectionBarStrs = lineFormat.parse(currentLineStr);
		boolean isSectionBar = false;
		String sectionBarNumStr = sectionBarStrs[0].toString();

		isSectionBar = sectionBarNumStr.matches("\\d+\\.\\d+\\.\\d+");
//		System.out.println("isSectionBar1:"+isSectionBar);

		if (isSectionBar) {
            if (!sectionDir.exists()) {
				sectionDir.mkdirs();
            }
			createMMFile(currentLineStr, srcFilePath, sectionDir.getAbsolutePath());
        }
        return isSectionBar;
	}

	private static void createMMFile(String readLineStr, String srcFilePath, String destFileDir)  {
		try{
			System.out.println(readLineStr);
			File srcFile = new File(srcFilePath);
			File targetFile = new File(destFileDir,readLineStr+".mm");
			//先复制到目的文件夹
			FileUtils.copyFile(srcFile,targetFile);
			//更改目的文件内容
			String targetStr = String.format("%s\"%s\">",copyFilePathReplaceStr,readLineStr);
			TextFileUtils.updateFileTextContent(targetFile.getAbsolutePath(),copyFilePathReplaceStr,targetStr);
		}catch (Exception e){
			e.printStackTrace();
		}
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
        } catch (Exception e) {
            //当抛出多个异常时，子异常当在父异常前抛出。  
            e.printStackTrace();    
        }
	}
	
	public interface RunParam{
		public abstract Object run(Object o0,Object o1) throws Exception;
	}

	public static void main(String[] args) {

		//该文件文本内容必须使用UTF-8
		String txtFileName = "目录.txt";

		String txtFilePath = String.format("%s/%s", destFileDir,txtFileName);

		File file = new File(txtFilePath);

		printFileContent(file,new RunParam() {
			@Override
			public Object run(Object o0,Object o1) throws Exception {
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
					currentLineStr = createChapter(reader, chapterStr);
				}else {
//					System.out.println("无法进入下一行");
					throw new Exception("无法进入下一行");
				}
				return currentLineStr;
			}


		});
	}
}
