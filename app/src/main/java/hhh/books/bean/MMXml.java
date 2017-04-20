package hhh.books.bean;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by huanghh on 2017/4/20.
 */

public class MMXml {
    private Document document;
    private String path;

    public MMXml(String path) throws Exception {

        SAXBuilder bulider = new SAXBuilder();
        InputStream inSt = new FileInputStream(path);
        document = bulider.build(inSt);
    }

    //添加节点
    public  void addElement(String text) throws  Exception{
        //创建一个person节点
        Element node = new Element("node");
        node.setAttribute("CREATED","1492671900673");
        node.setAttribute("ID","ID_106373449");
        node.setAttribute("MODIFIED","1492671903141");
        node.setAttribute("POSITION","right");
        node.setAttribute("TEXT",text);

        Element rootMap = document.getRootElement();		//获取根节点对象
        List<Element> mapList = rootMap.getChildren();
        Element rootNode = mapList.get(0);

        rootNode.addContent(node);

        //将添加的保存到文件中
        XMLOutputter out = new XMLOutputter();
        out.output(document, new FileOutputStream(path));
    }

    //遍历解析文档
    public void xmlParse(){
        Element rootMap = document.getRootElement();		//获取根节点对象
        List<Element> mapList = rootMap.getChildren();
        Element rootNode = mapList.get(0);

        List<Element> rootChildNodeList = rootNode.getChildren();
        for(Element el: rootChildNodeList){
            System.out.println(el.getAttributeValue("CREATED"));
            System.out.println(el.getAttributeValue("ID"));
            System.out.println(el.getAttributeValue("MODIFIED"));
            System.out.println(el.getAttributeValue("TEXT"));
        }

    }
}
