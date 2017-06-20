package hhh.books.bean;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hhh.books.Native2AsciiUtils;

/**
 * Created by huanghh on 2017/4/20.
 */

public class MMDom4jXml {
    private String filePath;

    private Document doc = null;

    public MMDom4jXml(String filePath) {
        this.filePath = filePath;
        File f = new File(filePath);
        try {
            SAXReader reader = new SAXReader();
            InputStream in = new FileInputStream(f);
            doc = reader.read(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNode(String text) {
        Element root = doc.getRootElement();
        List<Element> nodeElements = root.elements();
        Element rootNode = nodeElements.get(0);
        Element childNode = rootNode.addElement("node");
        childNode.addAttribute("CREATED", "1472613963222");
        childNode.addAttribute("ID", "ID_698403439");
        childNode.addAttribute("MODIFIED", "1472613981311");
        childNode.addAttribute("POSITION", "right");
        String asciiStr = Native2AsciiUtils.native2Ascii(text);

        System.out.println("asciiStr1:"+asciiStr);
        asciiStr = asciiStr.replaceAll("\\\\u","&#x");
        System.out.println("asciiStr2:"+asciiStr);
        asciiStr = asciiStr.replaceAll("&amp;","&");
        System.out.println("asciiStr3:"+asciiStr);
        text = StringEscapeUtils.unescapeXml(asciiStr)+".mm";
        childNode.addAttribute("TEXT", text);
        childNode.addAttribute("LINK", text.replaceAll(" ","%20"));

        try {
            FileWriter fw = new FileWriter(filePath);
            OutputFormat format = new OutputFormat("", true, "US-ASCII");
            XMLWriter writer = new XMLWriter(fw, format);
            writer.setMaximumAllowedCharacter(-1);
            writer.write(doc);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parserXml() {
        Element root = doc.getRootElement();
        readNode(root);
    }

    @SuppressWarnings("unchecked")
    public void readNode(Element root) {
        if (root == null) return;

        System.out.println(root.getName() + "1:" + (String) root.getData());

        // 获取属性
        List<Attribute> attrs = root.attributes();
        if (attrs != null && attrs.size() > 0) {
            for (Attribute attr : attrs) {
                System.out.println(attr.getName() + "2: " + attr.getValue());
            }
        }
        // 获取他的子节点
        List<Element> childNodes = root.elements();
        for (Element e : childNodes) {
            readNode(e);
        }
    }


}
