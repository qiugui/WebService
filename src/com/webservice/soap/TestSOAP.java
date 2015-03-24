 package com.webservice.soap;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.qiugui.service.User;
 public class TestSOAP {

	 /**   
	 * @Title: test01   
	 * @Description: 创建 soapmessage           
	 */
	 
	@Test
	 public void test01(){
		 
		 try {
			//1.创建消息工厂
			MessageFactory messageFactory = MessageFactory.newInstance();
			//2.根据消息工厂创建soapmessage
			SOAPMessage soapMessage = messageFactory.createMessage();
			//3.创建soappart
			SOAPPart soapPart = soapMessage.getSOAPPart();
			//4.获取soapenvelope
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			//5.通过soapEnvelope有效地获取header body等信息
			SOAPBody soapBody = soapEnvelope.getBody();
			//6.根据QName创建相应的节点（QName就是带有命名空间的）
			QName qName = new QName("http://service.qiugui.com/", 
					"minus", "ns2");//<ns2:minus xmlns="http://service.qiugui.com/">
			//如果使用以下方式设置，会将<转化成&lg;
			//soapBody.addBodyElement(qName).setValue("<a>1</a>");
			SOAPBodyElement soapBodyElement = soapBody.addBodyElement(qName);
			soapBodyElement.addChildElement("a").setValue("5");
			soapBodyElement.addChildElement("b").setValue("3");
			soapMessage.writeTo(System.out);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	/**   
	 * @Title: test02   
	 * @Description: 通过SOAPMessage与服务器进行通信           
	 */
	 
	@Test
	public void test02(){
		String wsdlurl = "http://127.0.0.1:7777/ns?wsdl";
		String namespace = "http://service.qiugui.com/";
		
		try {
			//1.创建服务
			URL url = new URL(wsdlurl);
			QName sName = new QName(namespace, "MyserviceImplService");
			Service service = Service.create(url, sName);
			
			//2.创建dispatch
			Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(namespace,"MyserviceImplPort"), 
					SOAPMessage.class, Service.Mode.MESSAGE);
			
			//3.创建soapmessage
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			SOAPBody body = envelope.getBody();
			
			//4.创建qname来指定消息中传递的数据
			QName qName = new QName(namespace, "minus", "ns2");
			SOAPBodyElement bodyElement = body.addBodyElement(qName);
			bodyElement.addChildElement("a").setValue("5");
			bodyElement.addChildElement("b").setValue("3");
			message.writeTo(System.out);
			
			System.out.println("\n"+"invoking...");
			//5.通过dispatche传递消息，会返回响应消息
			SOAPMessage response = (SOAPMessage) dispatch.invoke(message);
			response.writeTo(System.out);
			
			//将响应的消息转换为dom对象
			Document doc = response.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
			
			String result = doc.getElementsByTagName("minusResult").item(0).getTextContent();
			System.out.println("\n"+"运算结果是"+result);
			
		} catch (Exception e) {
			 e.printStackTrace();
			 
		}	
	}
	
	/**   
	 * @Title: test03   
	 * @Description: 通过负载payload与服务器进行通信          
	 */
	 
	@Test
	public void test03(){
		String wsdlurl = "http://127.0.0.1:7777/ns?wsdl";
		String namespace = "http://service.qiugui.com/";
		try {
			//1.创建服务
			URL url = new URL(wsdlurl);
			QName qName = new QName(namespace, "MyserviceImplService");
			Service service = Service.create(url, qName);
			
			//2.创建dispatch
			Dispatch<Source> dispatch = service.createDispatch(new QName(namespace, "MyserviceImplPort"), 
					Source.class, Service.Mode.PAYLOAD);
			
			//3.根据用户对象创建相应的xml
			User user = new User(2, "zs", "张三", "111111");
			JAXBContext jaxb = JAXBContext.newInstance(User.class);
			Marshaller marshaller = jaxb.createMarshaller();
			//取消 startdocument(<?xml version="1.0" ...?>) 的输出
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter writer = new StringWriter();
			marshaller.marshal(user, writer);

			//4.封装相应的part addUser
			String payload = "<ns2:addUser xmlns:ns2=\""+namespace+
					"\">"+writer.toString()+"</ns2:addUser>";
			System.out.println(payload);
			StreamSource streamSource = new StreamSource(new StringReader(payload));
			
			//5.通过dispatch传递payload，并返回响应消息
			Source response = dispatch.invoke(streamSource);
			
			//6.将Source转化为DOM进行操作，使用Transform对象转化
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMResult result = new DOMResult();
			transformer.transform(response, result);
			
			//7.通过Xpath处理相应信息
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList list = (NodeList) xPath.evaluate("//user", result.getNode(), XPathConstants.NODESET);
			User ur = (User) jaxb.createUnmarshaller().unmarshal(list.item(0));
			
			System.out.println(ur.getNickname());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test04(){
		String wsurl = "http://127.0.0.1:8888/ns?wsdl";
		String namespace = "http://service.qiugui.com/";
		
		try {
			URL url = new URL(wsurl);
			QName sName = new QName(namespace, "MyserviceImplService");
			Service service = Service.create(url, sName);
			
			Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(namespace, "MyserviceImplPort"), 
					SOAPMessage.class, Service.Mode.MESSAGE);
			
			SOAPMessage message = MessageFactory.newInstance().createMessage();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			SOAPBody body = envelope.getBody();
			
			QName qName = new QName(namespace, "list", "nn");
			body.addBodyElement(qName);
			message.writeTo(System.out);
			
			System.out.println("\n"+"invoking...");
			SOAPMessage response = dispatch.invoke(message);
			response.writeTo(System.out);
			
			Document doc = response.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
			NodeList nodeList = (NodeList)doc.getElementsByTagName("user");
			JAXBContext jxt = JAXBContext.newInstance(User.class);
			for(int i =0;i<nodeList.getLength();i++){
				Node node = nodeList.item(i);
				User user =(User) jxt.createUnmarshaller().unmarshal(node);
				System.out.println("\n"+user.getNickname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

 