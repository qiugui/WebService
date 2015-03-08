package com.qiugui.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IMyservice {

	@WebResult(name="plusResult")
	public int plus(@WebParam(name="a")int a,@WebParam(name="b")int b);
	
	@WebResult(name="minusResult")
	public int minus(@WebParam(name="a")int a,@WebParam(name="b")int b);
	
	@WebResult(name="user")
	public User addUser(@WebParam(name="user")User user);
	
	@WebResult(name="user")
	public User login(@WebParam(name="username")String username,@WebParam(name="password")String password);
	
	@WebResult(name="user")
	public List<User> list();
}
