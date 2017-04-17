package hhh.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class AllFileToClassifyByTxtFile {
	/**
	 * 
	 * @param host
	 * @param allFilePath
	 * @param files
	 * @param txtFile
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void allFileToClassifyByTxtFile_mkdir(List<String> host_list, String allFilePath,File txtFile) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		
		String filePath = txtFile.getAbsolutePath();
//			System.out.println("filePath:"+filePath);
		
		String suffix = ".txt";
		String txtFileName = txtFile.getName();
//		 3.0 获取txtName用于建文件夹
		File txtNameFile_dir = new File(allFilePath,txtFileName.substring(0, txtFileName.indexOf(suffix)));
		if (!txtNameFile_dir.exists()) {
			if (!txtNameFile_dir.isDirectory()) {
				txtNameFile_dir.mkdir();
			}
			
		}else {
			
		}
		try {
			// 1.通过指定文件txt获取Map
			Map<String, String> oldName_newName = RenameByTxt.getMap_oldNameNewName(filePath, host_list);
			// 2.通过map来换名
			 RenameByTxt.renameByWithMap(allFilePath, oldName_newName,null);
			// // 3.1通过value移动文件到相应文件目录
			 moveFile(allFilePath, oldName_newName, txtNameFile_dir);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		
	}
	
	/**
	 * 单单换名字而已
	 * @param host
	 * @param allFilePath
	 * @param files
	 * @param txtFile
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void allFileToClassifyByTxtFile_noMkdir(List<String> host_list, String allFilePath,File txtFile) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		
		String filePath = txtFile.getAbsolutePath();
//			System.out.println("filePath:"+filePath);
		
		String suffix = ".txt";
		String txtFileName = txtFile.getName();
//		 3.0 获取txtName用于建文件夹
		File txtNameFile_dir = new File(allFilePath,txtFileName.substring(0, txtFileName.indexOf(suffix)));
		if (!txtNameFile_dir.exists()) {
			if (!txtNameFile_dir.isDirectory()) {
				txtNameFile_dir.mkdir();
			}
			
		}else {
			
		}
		try {
			// 1.通过指定文件txt获取Map
			Map<String, String> oldName_newName = RenameByTxt.getMap_oldNameNewName(filePath, host_list);
			// 2.通过map来换名
			 RenameByTxt.renameByWithMap(txtNameFile_dir.getAbsolutePath(), oldName_newName,null);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		
	}
	
	private static void moveFile(String allFilePath,
			Map<String, String> oldName_newName, File txtNameFile_dir)
			throws IOException {
		File srcFile = null;
		File destFile = null;
		for (String realName : oldName_newName.values()) {
			srcFile = new File(allFilePath,realName);
			if (srcFile.exists()) {
				destFile = new File(txtNameFile_dir,realName);
				boolean success = srcFile.renameTo(destFile); 
//				FileUtils.copyFile(srcFile,destFile );
				if (!success) {
					System.out.println(realName+":移动失败");
				}
			}else {
				System.out.println(realName+"不存在");
			}
			
		}
	}
	/**
	 * 获取制定的txt文件
	 * @param allTextDir_str 某一txt文件夹
	 * @param filenameFilterStrs 该文件夹需要提取的txt文件名字，假如为空(null)的话。直接可以将文件下的txt全部取出
	 * @return
	 */
	public static File[] getTargetTxtFiles(String allTextDir_str,String[] filenameFilterStrs) {
		File[] files = null;
		File file = new File(allTextDir_str);
		
		if (filenameFilterStrs!=null) {
			final List<String> filenameFilter_list =  Arrays.asList(filenameFilterStrs);
			
			FilenameFilter filenameFilter = new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					if (filenameFilter_list.contains(name)) {
						return false;
					}else {
						return true;
					}
				}
			};
			
			files = file.listFiles(filenameFilter);
		}else {
			files = file.listFiles();
		}
		
		return files;
	}
	
	/**
	 * 获取制定的txt文件
	 * @param allTextDir_str 某一txt文件夹
	 * @param filenameFilterStrs 该文件夹需要提取的txt文件名字，假如为空(null)的话。直接可以将文件下的txt全部取出
	 * @return
	 */
	public static File[] getFilterTargetTxtFiles(String allTextDir_str,String[] filenameFilterStrs) {
		File[] files = null;
		File file = new File(allTextDir_str);
		
		if (filenameFilterStrs!=null) {
			final List<String> filenameFilter_list =  Arrays.asList(filenameFilterStrs);
			
			FilenameFilter filenameFilter = new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					if (filenameFilter_list.contains(name)) {
						return true;
					}else {
						return false;
					}
				}
			};
			
			files = file.listFiles(filenameFilter);
		}else {
			files = file.listFiles();
		}
		
		return files;
	}

}
