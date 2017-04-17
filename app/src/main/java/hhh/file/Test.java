package hhh.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class Test {
	//https://git.oschina.net/470441372/MyUtilsClass.git
 //	static String host = "http://ocs.maiziedu.com/";
//	static String host = "http://ocsource.maiziedu.com/";
	/**
	 * 预防有多个host作为过滤条件
	 */
	private static String[] host_arr = {
		"http://ocs.maiziedu.com/",//old HOST
		"http://ocsource.maiziedu.com/",//new HOST
		"http://qiniudn.microoh.com/",//new HOST
		};
	/**
	 * 预防有多个host作为过滤条件
	 */
	private static List<String> host_list = null;
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		host_list = Arrays.asList(host_arr);
		
//		testAllFileToClassifyByTxtFile_mkdir();
//		testAllFileToClassifyByTxtFile_noMkdir();
//		testRenameByTxt();
		testRenameAndCopyToDirByTxt();
//		testGetDirTxtToPrint();
//		getFilterDirTxtToPrint();
//		testGetFileTxtToPrint();
		
//		testReplaceTxtLineHeadOneFile();
	}

	private static void testReplaceTxtLineHeadOneFile() {
		String txtFileNameDir = "E:/workspaces/pythod/炒股一招先";
		
		File txtFileNameDir_file = new File(txtFileNameDir);
		for (File txtFile : txtFileNameDir_file.listFiles()) {
			
			testReplaceTxtLineHeadByFile(txtFile);
		}
	}

	private static void testReplaceTxtLineHeadByFile(File file) {
		ReplaceTxtLineHead.replaceTxtByStr(file,"INFO:root:", "");
		ReplaceTxtLineHead.replaceTxtByStr(file,"&nbsp;", "");
	}

	private static void testAllFileToClassifyByTxtFile_mkdir()
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		String allTextDir_str = "E:/workspaces/pythod/麦子学院/iOS应用开发";
		String[] filenameFilterStrs = 
				{
//				"1.2218.android架构师需要掌握的OOP及架构分析.txt",
//				"1.Cocos2d-x概要.txt","2.了解Cocos2d-x整个引擎框架.txt",
//				"2.2243.android架构之认识框架.txt","2.2257.android架构之认识线程模式.txt",
				}
//				null
				;
		
		
		String allFilePath = "E:/迅雷下载/1/iOS应用开发";

		File[] files = AllFileToClassifyByTxtFile.getTargetTxtFiles(allTextDir_str, filenameFilterStrs);
		int files_size = files.length;

		
		
		File txtFile = null;
		for (int i = 0; i < files_size; i++) {
			txtFile = files[i];
			AllFileToClassifyByTxtFile.allFileToClassifyByTxtFile_mkdir(host_list, allFilePath,  txtFile);
			
		}
	}
	
	private static void testAllFileToClassifyByTxtFile_noMkdir()
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		String allTextDir_str = "E:/workspaces/pythod/麦子学院/产品经理";
		String[] filenameFilterStrs = 
//				{
//				"1.2218.android架构师需要掌握的OOP及架构分析.txt",
//				"1.Cocos2d-x概要.txt","2.了解Cocos2d-x整个引擎框架.txt",
//				"2.2243.android架构之认识框架.txt","2.2257.android架构之认识线程模式.txt",
//				}
				null
				;
		
		
		String allFilePath = "M:/private/编程视频/麦子学院/video/产品经理";

		File[] files = AllFileToClassifyByTxtFile.getTargetTxtFiles(allTextDir_str, filenameFilterStrs);
		int files_size = files.length;

		
		
		File txtFile = null;
		for (int i = 0; i < files_size; i++) {
			txtFile = files[i];
			AllFileToClassifyByTxtFile.allFileToClassifyByTxtFile_noMkdir(host_list, allFilePath,  txtFile);
			
		}
	}

	


	private static void testRenameByTxt() throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		String txtfileName = "4.5176.建筑绘制";
		String renameDir_str = String.format("E:/迅雷下载/1/游戏原画设计", txtfileName);
		String txtFileName = String.format("E:/workspaces/pythod/麦子学院/temp/%s.txt", txtfileName);
		
		RenameByTxt.renameByTxt(renameDir_str, txtFileName, host_list);
	}
	
	private static void testRenameAndCopyToDirByTxt() throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		String txtfileName = "4.5136.武器绘制";
		String renameDir_str = String.format("E:/迅雷下载/1/游戏原画设计", txtfileName);
		String txtFilePath = String.format(
				"E:/workspaces/pythod/麦子学院/游戏原画设计/%s.txt", txtfileName);

		RenameByTxt.renameAndCopyToDirByTxt(renameDir_str, txtFilePath,txtfileName, host_list);
	}
	
	private static void testGetDirTxtToPrint()
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		String allTextDir_str = "E:/workspaces/pythod/麦子学院/iOS应用开发";
		
		String[] alreadyReadFilePaths = null
//				{
//				"1.2218.android架构师需要掌握的OOP及架构分析.txt",
//				"1.2228.android架构之EIT介绍.txt","1.2236.android架构之设计模式介绍.txt",
//				"2.2243.android架构之认识框架.txt","2.2257.android架构之认识线程模式.txt",
//				}
				;
		
		GetDirTxtToPrint.getDirTxtToPrint(allTextDir_str, host_list, alreadyReadFilePaths);
	}
	
	private static void getFilterDirTxtToPrint()
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		String allTextDir_str = "E:/workspaces/pythod/麦子学院/游戏原画设计";
		
		String[] alreadyReadFilePaths = 
//				null
				{
				"4.5136.武器绘制.txt",
				"4.5144.角色绘制.txt","4.5165.怪物绘制.txt",
				"4.5176.建筑绘制.txt","5.5155.暗黑风格界面.txt",
				"5.5160.暗黑风格logo设计.txt",
				}
				;
		
		GetDirTxtToPrint.getFilterDirTxtToPrint(allTextDir_str, host_list, alreadyReadFilePaths);
	}
	
	private static void testGetFileTxtToPrint()
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		String textFile_str = "E:/workspaces/pythod/麦子学院/iOS应用开发/1.5188.IOS动画编程.txt";
		
		
		GetDirTxtToPrint.getFileTxtToPrint(textFile_str, host_list);
	}
}
