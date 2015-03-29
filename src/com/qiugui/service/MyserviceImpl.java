package com.qiugui.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

@WebService(endpointInterface="com.qiugui.service.IMyservice")
@HandlerChain(file="handler-chain.xml")
public class MyserviceImpl implements IMyservice {

	private static List<User> users = new ArrayList<User>();
	
	public MyserviceImpl() {
		users.add(new User(1, "admin", "管理员", "111111"));
	}
	
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

	@Override
	public User addUser(User user) {
		users.add(user); 
		for (User ur : users) {
			System.out.println(ur.getNickname());
		}
		return user;
		 
	}

	@Override
	public User login(String username, String password) throws UserException {
		for (User user : users){
			if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
				return user;
			}
		}
		throw new UserException("用户不存在！");
		 
	}

	@Override
	public List<User> list(String authInfo) {
		System.out.println("进入List"+authInfo); 
		return users;	 
	}

}
