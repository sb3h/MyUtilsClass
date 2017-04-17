package hhh.file;

import org.apache.commons.lang.StringEscapeUtils;

public class TestStr {
	public static void main(String[] args) {
		String str = "1.&nbsp;介绍.mp4";
		str = StringEscapeUtils.unescapeHtml(str);
		System.out.println(str);
	}
}
