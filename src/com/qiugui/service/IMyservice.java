package com.qiugui.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IMyservice {

	@WebResult(name="plusResult")
	public int plus(@WebParam(name="a")int a,@WebParam(name="b")int b);
	
	@WebResult(name="minusResult")
	public int minus(@WebParam(name="a")int a,@WebParam(name="b")int b);
}
