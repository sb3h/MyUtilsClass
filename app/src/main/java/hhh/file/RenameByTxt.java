package hhh.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;


public class RenameByTxt {
	/**
	 * 
	 * @param renameDir_str 更名的文件的文件夹
	 * @param txtFilePath 包含一行下载路径，一行该文件的名字
	 * @param host 下载的网络主机地址
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void renameByTxt(String renameDir_str, String txtFilePath,List<String> host_list) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		Map<String, String> oldName_newName = getMap_oldNameNewName(txtFilePath,host_list);
//		for (String key : oldName_newName.keySet()) {
//			System.out.println(key);
//			System.out.println(oldName_newName.get(key));
//		}
		
		renameByWithMap(renameDir_str, oldName_newName,null);
	}


	public static void renameByWithMap(String renameDir_str,Map<String, String> oldName_newName, String txtFileName) {
		File renameDir = new File(renameDir_str);
		File[] renameFiles = renameDir.listFiles();
		
		
//		Set<String> keys =  oldName_newName.keySet();	
//		oldName_newName = keyValue_reversal(oldName_newName, keys);
		doWork(oldName_newName, renameDir, renameFiles,txtFileName);
	}


	private static Map<String, String> keyValue_reversal(Map<String, String> oldName_newName, Set<String> keys) {
		Map<String, String> new_oldName_newName = new HashMap<String, String>();
		for (String key : keys) {
			new_oldName_newName.put(oldName_newName.get(key)/*.replaceAll(".mp4", ".avi")*/, key);
//			new_oldName_newName.put(key.replaceAll(".mp4", ".avi"), oldName_newName.get(key));

		}
		return oldName_newName = new_oldName_newName;
	}
	
	
	private static void doWork(Map<String, String> oldName_newName,
			File renameDir, File[] renameFiles, String txtFileName) {
		for (int i = 0; i < renameFiles.length; i++) {
			File renameFile = renameFiles[i];
			if (renameFile.exists()&&renameFile.isFile()) {
				if (oldName_newName.containsKey(renameFile.getName())) {
//					System.out.println(oldName_newName.get(renameFile.getName()));
					renameFile(oldName_newName, renameDir, renameFile,txtFileName);
					
					
//					copyMD5ToCNFile(oldName_newName, renameDir, renameFile);
					System.out.println(String.format("总文件个数:%d,当前为第%d个", renameFiles.length,i+1));
				}
			}else {
//				System.out.println(renameFile.getName()+"不存在");
			}
			
		}
	}
	private static void copyMD5ToCNFile(Map<String, String> oldName_newName,File renameDir, File renameFile) throws IOException {
		String newName = oldName_newName.get(renameFile.getName());
		newName = newName.replaceAll(".mp4", ".avi");
		FileUtils.copyFile(renameFile, new File(renameDir,newName));
	}
	
	private static void renameFile(Map<String, String> oldName_newName,
			File renameDir, File renameFile, String txtFileName) {
		String newName = oldName_newName.get(renameFile.getName());
		newName = newName.replaceAll("<", "").replaceAll(">", "").replaceAll("\"", "").replaceAll("|", "")
//				.replaceAll("*", "").replaceAll("\\", "")
				.replaceAll(":", "").replaceAll("/", "-").replaceAll("	", "");
		File dest = new File(renameDir,newName);
		if (txtFileName!=null) {
			File dest_dir = new File(renameDir+ File.separator + txtFileName);
			if (!dest_dir.exists()) {
				dest_dir.mkdirs();
			}
			dest = new File(dest_dir,newName);
		}
		renameFile.renameTo(dest);
	}
	/**
	 * 
	 * @param textFilePath 被选中的文件名
	 * @param host 下载的网络主机地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map<String, String> getMap_oldNameNewName(String textFilePath,List<String> host_list) throws UnsupportedEncodingException,FileNotFoundException, IOException {
		File textFile = new File(textFilePath);
		
		ReplaceTxtLineHead.changeDirTextFile(textFile);
		
		Map<String,String> oldName_newName = new LinkedHashMap<String,String>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile),ReplaceTxtLineHead.CHARSET_TXT));  
		String data = br.readLine();//一次读入一行，直到读入null为文件结束 
		String oldName = "";
		String newName = "";
		String host = null;
		while (data != null) {
			// System.out.println(data);
				if (data.startsWith("http://")) {
					if (host == null) {
						for (String host_str : host_list) {
							if (data.contains(host_str)) {
								host = host_str;
								oldName_newName.put("host", host);
							}
						}
					}else {
						if (!data.contains(host)) {
							for (String host_str : host_list) {
								if (data.contains(host_str)) {
									host = host_str;
								}
							}
						}
					}
					data = getFileNameFromURL(host, data);
					oldName = data;
					if (!"".equals(oldName)) {
						oldName_newName.put(oldName, newName);
					}
				}else {
					newName = StringEscapeUtils.unescapeHtml(data);
				}
				data = br.readLine(); // 接着读下一行
		}
		br.close();
		return oldName_newName;
	}
	/**
	 * 获取下载的URL
	 * @param textFilePath
	 * @param host_list
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Set<String> getDownloadUrls(String textFilePath,List<String> host_list) throws UnsupportedEncodingException,FileNotFoundException, IOException {
		File textFile = new File(textFilePath);
		
		ReplaceTxtLineHead.changeDirTextFile(textFile);
		
		Set<String> downloadUrls = new LinkedHashSet<String>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile),ReplaceTxtLineHead.CHARSET_TXT));  
		String data = br.readLine();//一次读入一行，直到读入null为文件结束 
		String oldName = "";
		String host = null;
		while (data != null) {
			// System.out.println(data);
				if (data.startsWith("http://")) {
//					if (host == null) {
//						for (String host_str : host_list) {
//							if (data.contains(host_str)) {
//								host = host_str;
//							}
//						}
//					}else {
//						if (!data.contains(host)) {
//							for (String host_str : host_list) {
//								if (data.contains(host_str)) {
//									host = host_str;
//								}
//							}
//						}
//					}
//					data = getFileNameFromURL(host, data);
					oldName = data;
					if (!"".equals(oldName)) {
						downloadUrls.add(oldName);
					}
				}
				data = br.readLine(); // 接着读下一行
		}
		br.close();
		return downloadUrls;
	}
	/**
	 * 
	 * @param host 网络地址主机
	 * @param netURL 网络地址
	 * @return 去除网络地址的文件名
	 */
	public static String getFileNameFromURL(String host, String netURL) {
		int host_length = host.length();
		String fileName = netURL.substring(host_length, netURL.length());
		
		return fileName;
	}


	public static void renameAndCopyToDirByTxt(String renameDir_str,String txtFilePath, String txtfileName, List<String> host_list) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		Map<String, String> oldName_newName = getMap_oldNameNewName(txtFilePath,host_list);
//		for (String key : oldName_newName.keySet()) {
//			System.out.println(key);
//			System.out.println(oldName_newName.get(key));
//		}
		
		renameByWithMap(renameDir_str, oldName_newName,txtfileName);
	}
}
