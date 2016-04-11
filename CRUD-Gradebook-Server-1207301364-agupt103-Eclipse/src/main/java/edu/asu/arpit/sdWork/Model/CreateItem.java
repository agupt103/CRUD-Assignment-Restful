package edu.asu.arpit.sdWork.Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Gradebook")
public class CreateItem {
private String id;
private String item;
private String grades;
private String fb;


public String getItem() {
	return item;
}

@XmlElement(name="item")
public void setItem(String item) {
	this.item = item;
}

public String getGrades() {
	return grades;
}

@XmlElement(name="grades")
public void setGrades(String grades) {
	this.grades = grades;
}

public String getFb() {
	return fb;
}

@XmlElement(name="fb")
public void setFb(String fb) {
	this.fb = fb;
}

public String getId() {
	return id;
}

@XmlElement(name="id")
public void setId(String id) {
	this.id = id;
}

}
