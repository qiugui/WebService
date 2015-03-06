 package com.webservice.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
 public class Student {

	 private String  name;
	 private int age;
	 private String sex;
	 private Classroom classroom;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Classroom getClassroom() {
		return classroom;
	}
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	public Student(String name, int age, String sex, Classroom classroom) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.classroom = classroom;
	}
	public Student() {
		super();
	}
	 
}

 