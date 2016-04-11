package edu.asu.arpit.sdWork.service;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.asu.arpit.sdWork.Model.CreateItem;

public class GradeWriteService {

	public static void write(Document doc2,CreateItem gradeBook, String fullpath) throws TransformerFactoryConfigurationError, TransformerException
	{
		Element eElement=doc2.createElement("Student");
		eElement.setAttribute("id", gradeBook.getId());
		Element gradeItems=doc2.createElement("GradingItems");
		gradeItems.setTextContent(String.valueOf(gradeBook.getItem()));
		Element egrades=doc2.createElement("Grades");
		egrades.setTextContent(gradeBook.getGrades());
		Element eFb=doc2.createElement("Feedback");
		eFb.setTextContent(gradeBook.getFb());
		eElement.appendChild(gradeItems);
		eElement.appendChild(egrades);
		eElement.appendChild(eFb);
		Element rootTag=(Element) doc2.getElementsByTagName("Gradebook").item(0);
		rootTag.appendChild(eElement);
		GradeBookService.saveDoc(doc2,fullpath);
	}
}
