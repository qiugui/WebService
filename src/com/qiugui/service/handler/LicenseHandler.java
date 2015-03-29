 package com.qiugui.service.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 public class LicenseHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public void close(MessageContext context) {
		 
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		 return false;
		 
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		try {
			Boolean out = (Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
			if(!out){
				SOAPMessage message = context.getMessage();
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPBody body = envelope.getBody();
				Node node = body.getChildNodes().item(0);
				String paramName = node.getLocalName();
				SOAPHeader header = envelope.getHeader();
				
				if("list".equals(paramName) || "addUser".equals(paramName)){
					Iterator<SOAPHeaderElement> iterator = header.extractAllHeaderElements();
					if(!iterator.hasNext()){
						//添加一个错误信息
						System.out.println("*********");
						SOAPFault fault = body.addFault();
						fault.setFaultString("头部信息不正确");
						throw new SOAPFaultException(fault);
					}
					while (iterator.hasNext()) {
						SOAPHeaderElement soapHeaderElement = (SOAPHeaderElement) iterator
								.next();
						System.out.println(soapHeaderElement.getNodeName()+":"+soapHeaderElement.getTextContent());
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		return true;
		 
	}

	@Override
	public Set<QName> getHeaders() {
		 return null;
		 
	}

}

 