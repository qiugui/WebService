package com.qiugui.service;

import javax.jws.WebService;

@WebService(endpointInterface="com.qiugui.service.IMyservice")
public class MyserviceImpl implements IMyservice {

	@Override
	public int plus(int a, int b) {
		System.out.println(a+"+"+b+"="+(a+b));
		return a+b;
	}

	@Override
	public int minus(int a, int b) {
		System.out.println(a+"-"+b+"="+(a-b));
		return a-b;
	}

}
