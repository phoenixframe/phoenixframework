package org.phoenix.api.utils;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
/**
 * 通过XPath方式解析xml的字符串或xml文件
 * @author mengfeiyang
 *
 */
public class XmlParser {
    private Document doc;
    private XPath xpath;
    
    private volatile static XmlParser xmlParser;  
    private XmlParser (){}  

	public static XmlParser getInstance() {
		if (xmlParser == null) {
			synchronized (XmlParser.class) {
				if (xmlParser == null) {
					
					xmlParser = new XmlParser();
				}
			}
		}
		return xmlParser;
	} 
 
    public XmlParser parserXmlFile(String xmlFilePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        try{
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        doc = db.parse(new FileInputStream(new File(xmlFilePath)));
        }catch(Exception e){
        	e.printStackTrace();
        }
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return getInstance();
        
    }
    
    public XmlParser parserXmlContent(String xmlContent){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        try{
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        doc = db.parse(IOUtils.toInputStream(xmlContent));
        }catch(Exception e){
        	e.printStackTrace();
        }
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return getInstance();
    }
    
    public int getLevels(){
    	return doc.getDocumentElement().getChildNodes().getLength();
    }
    public List<String> getRootElements(){
    	List<String> names = new ArrayList<String>();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
            	names.add(nodeList.item(i).getNodeName());
            }
        }
        return names;
    }
    public Node getNodeElement(String xpathContent){
    	try {
			return (Node) xpath.evaluate(xpathContent, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    	return null;
    }
    public String getRoot(String rootXpath) {
		try {
			Node node = (Node) xpath.evaluate(rootXpath, doc, XPathConstants.NODE);
	        return node.getNodeValue();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
    }
 
    public List<String> getChildNames(String xpathContent){
		try {
			List<String> nodeNames = new ArrayList<String>();
			NodeList nodeList = (NodeList) xpath.evaluate(xpathContent, doc,XPathConstants.NODESET);
	        for (int i = 0; i < nodeList.getLength(); i++) {
	        	nodeNames.add(nodeList.item(i).getNodeName());
	        }
	        return nodeNames;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
    }
 
    public List<String> getChildValues(String xpathContent) {
		try {
			List<String> nodeValues = new ArrayList<String>();
			NodeList nodeList = (NodeList) xpath.evaluate(xpathContent,doc, XPathConstants.NODESET);
	        for (int i = 0; i < nodeList.getLength(); i++) {
	        	nodeValues.add(nodeList.item(i).getTextContent());
	        }
	        return nodeValues;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
    }
 
    public List<String> haveChildsElements(String xpathContent) {
		try {
			List<String> nodeNames = new ArrayList<String>();
			NodeList nodeList = (NodeList) xpath.evaluate(xpathContent, doc,XPathConstants.NODESET);
	        for (int i = 0; i < nodeList.getLength(); i++) {
	        	nodeNames.add(nodeList.item(i).getNodeName());
	        }
	        return nodeNames;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
    }
 
    public List<String> getNodeNameAndValues(String xpathContent){
    	List<String> nodeNameAndValues = new ArrayList<String>();
        NodeList nodeList;
		try {
			nodeList = (NodeList) xpath.evaluate(xpathContent, doc,XPathConstants.NODESET);
	        for (int i = 0; i < nodeList.getLength(); i++) {
	        	nodeNameAndValues.add(nodeList.item(i).getNodeName() + "-->"+ nodeList.item(i).getNodeValue());
	        }
	        return nodeNameAndValues;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
    }
 
    public NodeList getAttriNode(String xpathContent) {
        NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate(xpathContent, doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
        return nodeList;
    }
}