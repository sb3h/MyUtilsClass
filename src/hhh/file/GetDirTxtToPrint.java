package hhh.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetDirTxtToPrint {

	

	public static void getDirTxtToPrint(String allTextDir_str, List<String> host_list,
			String[] filenameFilterStrs) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		
		File[] files = AllFileToClassifyByTxtFile.getTargetTxtFiles(allTextDir_str, filenameFilterStrs);
		int files_size = files.length;

		
		for (int i = 0; i < files_size; i++) {
			String filePath = files[i].getAbsolutePath();
//			System.out.println("filePath:"+filePath);
			Set< String> downloadUrls = null;
			// System.out.println("filePath:"+filePath);
			downloadUrls = RenameByTxt.getDownloadUrls(filePath,host_list);
			for (String downloadUrl : downloadUrls) {
				System.out.println( downloadUrl);
			}
		}
		
		
	}
	
	public static void getFilterDirTxtToPrint(String allTextDir_str, List<String> host_list,
			String[] filenameFilterStrs) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		
		File[] files = AllFileToClassifyByTxtFile.getFilterTargetTxtFiles(allTextDir_str, filenameFilterStrs);
		int files_size = files.length;

		
		for (int i = 0; i < files_size; i++) {
			String filePath = files[i].getAbsolutePath();
//			System.out.println("filePath:"+filePath);
			Set< String> downloadUrls = null;
			// System.out.println("filePath:"+filePath);
			downloadUrls = RenameByTxt.getDownloadUrls(filePath,host_list);
			for (String downloadUrl : downloadUrls) {
				System.out.println( downloadUrl);
			}
		}
		
		
	}
	
	public static void getFileTxtToPrint(String textFile_str, List<String> host_list) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {

		Set< String> downloadUrls = null;
		// System.out.println("filePath:"+filePath);
		downloadUrls = RenameByTxt.getDownloadUrls(textFile_str,host_list);
		for (String downloadUrl : downloadUrls) {
				System.out.println( downloadUrl);
		}
	}
}
