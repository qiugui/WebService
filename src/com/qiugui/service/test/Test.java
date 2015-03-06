package com.qiugui.service.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.qiugui.service.IMyservice;

public class Test {
	public static void main(String[] args) throws MalformedURLException {
		URL url=new URL("http://127.0.0.1:8888/ns?wsdl");
		QName qName = new QName("http://service.qiugui.com/", "MyserviceImplService");
		Service service = Service.create(url, qName);
		
		IMyservice iMyservice = service.getPort(IMyservice.class);
		System.out.println(iMyservice.minus(3, 5));
	}
}
