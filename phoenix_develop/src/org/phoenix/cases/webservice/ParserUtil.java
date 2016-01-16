package org.phoenix.cases.webservice;

import org.junit.Test;
import org.phoenix.api.utils.JsonPaser;
import org.phoenix.api.utils.XmlParser;
import org.w3c.dom.Node;

public class ParserUtil {
	
	@Test
	public void testXmlFileParser(){
		System.out.println(XmlParser.getInstance().parserXmlFile("Suite.xml").getChildNames("//*/test").toString());
		System.out.println(XmlParser.getInstance().parserXmlFile("Suite.xml").getChildValues("//*/test/classes/*").toString());
		
		Node node = XmlParser.getInstance().parserXmlFile("Suite.xml").getNodeElement("/suite/test[2]");
		System.out.println("获取指定节点的属性值："+node.getAttributes().item(0).getTextContent());
		
		Node node2 = XmlParser.getInstance().parserXmlFile("Suite.xml").getNodeElement("/suite/test[1]/*/class");
		System.out.println("获取指定节点的值："+node2.getTextContent());
	}
	
	@Test
	public void testXmlStringParser(){
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"><channel><title>Java Tutorials and Examples 2</title><language>en-us</language><item><title><![CDATA[Java Examples 2]]></title><link>http://examples.javacodegeeks.com/</link></item></channel></rss>";
		System.out.println(XmlParser.getInstance().parserXmlContent(xmlString).getChildValues("/rss/*").toString());
		System.out.println(XmlParser.getInstance().parserXmlContent(xmlString).getChildValues("//*[name() = 'title']").toString());
	}
	
	@Test
	public void testJsonStringParser() throws Exception{
		String jsonString = "{\"l1\": {\"l1_1\": [\"l1_1_1\",\"l1_1_2\"],\"l1_2\": {\"l1_2_1\": 121}},\"l2\": {\"l2_1\": null,\"l2_2\": true,\"l2_3\": {}}}";
		System.out.println(JsonPaser.getNodeValue(jsonString, "JSON.l2.l2_2"));
	}
}
