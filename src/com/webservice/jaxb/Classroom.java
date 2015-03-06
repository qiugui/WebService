 package com.webservice.jaxb;
 public class Classroom {
	 private int grade;
	 private String classname;
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public Classroom(int grade, String classname) {
		super();
		this.grade = grade;
		this.classname = classname;
	}
	public Classroom() {
		super();
	}
	 
}

 