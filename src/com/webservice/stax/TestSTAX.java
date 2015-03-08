package com.webservice.stax;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/** 
* @ClassName: TestSTAX 
* @Description: 对XML文档的元素进行读写
* @author qiugui 
* @date 2015年3月8日 上午12:09:28 
*  
*/ 
public class TestSTAX {

	/**   
	 * @Title: stax   
	 * @Description: 用游标读取XML文档           
	 */
	 
	@Test
	public void test01(){
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		
		InputStream is = null;
		is = TestSTAX.class.getClassLoader().getResourceAsStream("com/webservice/stax/books.xml");
		try {
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(is);
			
			while(xmlStreamReader.hasNext()){
				/**
				 * 判断节点类型，如下
				 * 1-开始节点  4-characters文本节点  2-结束节点
				 */
				int type = xmlStreamReader.next();
				if (type == XMLStreamConstants.START_ELEMENT){
					System.out.println(xmlStreamReader.getName().toString());
				} else if (type == XMLStreamConstants.CHARACTERS) {
					System.out.println(xmlStreamReader.getText().trim());
				} else if (type == XMLStreamConstants.END_ELEMENT){
					System.out.println("/"+xmlStreamReader.getName());
				}
			}
		} catch (XMLStreamException e) {
			 e.printStackTrace();
			 
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					 e.printStackTrace();	 
				}
				is = null;
			}
			
		}
	}
	
	@Test
	public void test02(){
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		
		InputStream is = null;
		is = TestSTAX.class.getClassLoader().getResourceAsStream("com/webservice/stax/books.xml");
		try {
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(is);
			
			while(xmlStreamReader.hasNext()){
				int type = xmlStreamReader.next();
				if (type == XMLStreamConstants.START_ELEMENT){
					String name = xmlStreamReader.getName().toString();
					if ("book".equals(name)){
						System.out.println(
								xmlStreamReader.getAttributeName(0)+":"+xmlStreamReader.getAttributeValue(0));
					}
				}
			}
		} catch (XMLStreamException e) {
			 e.printStackTrace();
			 
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					 e.printStackTrace();	 
				}
				is = null;
			}
			
		}
	}
	
	@Test
	public void test03(){
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		
		InputStream is = null;
		is = TestSTAX.class.getClassLoader().getResourceAsStream("com/webservice/stax/books.xml");
		try {
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(is);
			
			while(xmlStreamReader.hasNext()){
				int type = xmlStreamReader.next();
				if (type == XMLStreamConstants.START_ELEMENT){
					String name = xmlStreamReader.getName().toString();
					if ("title".equals(name)){
						System.out.print(xmlStreamReader.getElementText()+":");
					}
					if ("price".equals(name)){
						System.out.println(xmlStreamReader.getElementText());
					}
				}
			}
		} catch (XMLStreamException e) {
			 e.printStackTrace();
			 
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					 e.printStackTrace();	 
				}
				is = null;
			}
			
		}
	}
	
	/**   
	 * @Title: test04   
	 * @Description: 基于迭代模型读取xml          
	 */
	 
	@Test
	public void test04(){
		//实例化一个XMLInputFactory
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		
		InputStream is = null;
		is = TestSTAX.class.getClassLoader().getResourceAsStream("com/webservice/stax/books.xml");
		try {
			//迭代模型的操作方式
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(is);
			
			while(xmlEventReader.hasNext()){
				//通过XMLEvent获得是否是某个节点
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()){
					//通过xmlEvent.asxxx转换节点
					String name = xmlEvent.asStartElement().getName().toString();
					if ("title".equals(name)){
						System.out.print(xmlEventReader.getElementText()+":");
					}
					if ("price".equals(name)){
						System.out.println(xmlEventReader.getElementText());
					}
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					 e.printStackTrace();	 
				}
				is = null;
			}
			
		}
	}
	
	/**   
	 * @Title: test06   
	 * @Description: 使用过滤器模式读取xml元素，提高效率           
	 */
	 
	@Test
	public void test05(){
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		InputStream is = null;
		is=TestSTAX.class.getClassLoader().getResourceAsStream("com/webservice/stax/books.xml");
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createFilteredReader(xmlInputFactory.createXMLEventReader(is),
					new EventFilter() {
						
						@Override
						public boolean accept(XMLEvent event) {
							if (event.isStartElement()){
								if ("book".equals(event.asStartElement().getName().toString())){
									return true;									
								}
							}
							return false;
							
						}
					});
			int sum = 0;
			
			while (xmlEventReader.hasNext()){
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()){
					sum++;
				}
			}
			System.out.println(sum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (Exception e) {
					 e.printStackTrace(); 
				}
				is = null;
			}
		}
	}
	
	/**   
	 * @Title: test06   
	 * @Description: 使用xpath模式读取xml元素    
	 */
	 
	@Test
	public void test06(){
		InputStream is = null;
		is = TestSTAX.class.getClassLoader().getResourceAsStream("com/webservice/stax/books.xml");
		try {
			//创建文档处理对象
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//通过DocumentBuilder创建doc的文档对象
			Document doc = db.parse(is);
			//创建XPath
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			NodeList nodeList = (NodeList) xPath.evaluate("//book[@category='WEB']", doc , XPathConstants.NODESET);
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);
				NodeList list = element.getElementsByTagName("title");
				System.out.println(list.item(0).getTextContent());
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
			 
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					 e.printStackTrace();	 
				}
				is = null;
			}
			
		}
	}
	
	/**   
	 * @Title: test07   
	 * @Description: 使用XMLStreamWrite写文档          
	 */
	 
	@Test
	public void test07(){
		
		try {
			XMLStreamWriter xmlStreamWriter=XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
			xmlStreamWriter.writeStartDocument("UTF-8","1.0");
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.writeStartElement("student");
			xmlStreamWriter.writeStartElement("id");
			xmlStreamWriter.writeCharacters("1");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.flush();
			xmlStreamWriter.close();
		} catch (Exception e) {
			 e.printStackTrace(); 
		}
	}
	
	/**   
	 * @Title: test08   
	 * @Description: 使用Transformer修改xml中的元素         
	 */
	 
	@Test
	public void test08(){
		InputStream is = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			is = TestSTAX.class.getClassLoader().getResourceAsStream("com/webservice/stax/books.xml");
			Document doc = db.parse(is);
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			NodeList nodeList = (NodeList) xPath.evaluate("//book[title='Learning XML']", doc, XPathConstants.NODESET);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			Element elementBook = (Element) nodeList.item(0);
			Element elementPrice = (Element) elementBook.getElementsByTagName("price").item(0);
			
			elementPrice.setTextContent("339.9");
			Result result = new StreamResult(System.out);
			transformer.transform(new DOMSource(doc), result);
			
			
		} catch (Exception e) {
			 e.printStackTrace();	 
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					 e.printStackTrace(); 
				}
				is = null;
			}
		}
	}
}
