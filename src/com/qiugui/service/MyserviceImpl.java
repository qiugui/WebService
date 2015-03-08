package com.qiugui.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface="com.qiugui.service.IMyservice")
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
	public User login(String username, String password) {
		for (User user : users){
			if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
				return user;
			}
		}
		return null;
		 
	}

	@Override
	public List<User> list() {
		 return users;	 
	}

}
