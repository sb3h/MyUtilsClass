package hhh.books;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import hhh.books.bean.MMXml;

public class MMXmlDemo {
    private static Document document; // 存放读取的文件
	private static String path = "D:\\3H\\compMsg\\brightoil\\template\\freemind\\123.mm"; // 文件存放路径

	public static void main(String[] args) throws Exception {
		MMXml td = new MMXml(path);
//		td.addElement();//添加节点
		td.xmlParse();//便利xml文件
//		td.deleteElement(3);//删除节点
	}
}