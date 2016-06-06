package hhh.url.change;

public class GradleLibNameConvertToUrl {
	public static final Class thisClass = GradleLibNameConvertToUrl.class;
	public static final String TAG = thisClass.getName();

	public static void main(String[] args) {
		String url_host = "https://jcenter.bintray.com/";
//		String url_host = "http://repo1.maven.org/maven2/";
		String libInGradleName = "com.google.android.gms:play-services:7.5.0";
		
		String JarName = "httpclientandroidlib-1.1.0.jar";
		
		String packageByJarName = "ch.boye.httpclientandroidlib";
		
//		printLibsURLByGradleName(url_host, JarName, packageByJarName);
		printLibsURLByGradleName(url_host, libInGradleName);
		
	}

	private static void printLibsURLByGradleName(String url_host, String JarName, String packageByJarName) {
		String versionByJarName = "";
		String libNameByJarName = "";
		
		StringBuffer libsURL = new StringBuffer();
		String[] JarNameMsg = JarName.split("-");
		for (int i = 0; i < JarNameMsg.length; i++) {
			if (i+1<JarNameMsg.length) {
				libsURL.append(JarNameMsg[i]);
				libsURL.append("-");
			}else {
				versionByJarName = JarNameMsg[i].substring(0, JarNameMsg[i].indexOf(".jar"));
			}
			
		}
		libNameByJarName = libsURL.toString().substring(0,libsURL.length()-1);
		System.out.println(url_host+packageByJarName.replaceAll("\\.", "/")+"/"+libNameByJarName+"/"+versionByJarName);
	}

	private static void printLibsURLByGradleName(String url_host,String libInGradleName) {
		String[] libsMsg =  libInGradleName.split(":");
		
		StringBuffer libsURL = new StringBuffer();
		
		String msg = "";
		for (int i = 0; i < libsMsg.length; i++) {
			msg = libsMsg[i];
			if (i==0) {
				msg = msg.replaceAll("\\.", "/");
			}
			libsURL.append(msg);
			libsURL.append("/");
		}
		System.out.println(url_host.concat(libsURL.toString()));
	}
}
