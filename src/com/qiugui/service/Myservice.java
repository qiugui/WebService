package com.qiugui.service;

import javax.xml.ws.Endpoint;

public class Myservice {

	public static void main(String[] args) {
		String address="http://127.0.0.1:8888/ns";
		Endpoint.publish(address, new MyserviceImpl());

	}

}
