 package com.webservice.jaxb.test;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.webservice.jaxb.Classroom;
import com.webservice.jaxb.Student;
 public class TestJAXB {

	 @Test
	 public void testJToXml() throws JAXBException{
		 Student student = new Student("张三", 20, "男", new Classroom(2010, "计算机"));
		 JAXBContext jaxb = JAXBContext.newInstance(Student.class);
		 Marshaller marshaller = jaxb.createMarshaller();
		 marshaller.marshal(student, System.out);
	 }
	 
	 @Test
	 public void testXMLToJ() throws JAXBException{
		 String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student><age>20</age><classroom><classname>计算机</classname><grade>2010</grade></classroom><name>张三</name><sex>男</sex></student>";
		 
		 JAXBContext jaxb = JAXBContext.newInstance(Student.class);
		 
		 Student student = null;
		 
		 Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		 
		 student = (Student) unmarshaller.unmarshal(new StringReader(xml));
		 
		 System.out.println(student.getName()+" "+student.getClassroom().getClassname());
	 }
}

 