package edu.asu.arpit.sdWork.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import edu.asu.arpit.sdWork.Model.CreateItem;

public class GradeReadService {
	public CreateItem readGrades(String fullpath, CreateItem gradeBook, Document doc2)
	{
		CreateItem grade = new CreateItem();	
		int l=0;
		try {
            
			Boolean flag = false;
			doc2.getDocumentElement().normalize();

			System.out.println("Root element :" + doc2.getDocumentElement().getNodeName());
					
			NodeList nList = doc2.getElementsByTagName("Student");
					
			System.out.println("----------------------------");
			System.out.println(nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++) {
                l=0;
				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    int s = eElement.getElementsByTagName("GradingItems").getLength();
                    System.out.println(s);
                    while(l<s)
                    {
                    String items = eElement.getElementsByTagName("GradingItems").item(l).getTextContent();
                    String grades = eElement.getElementsByTagName("Grades").item(l).getTextContent();
                    String fb = eElement.getElementsByTagName("Feedback").item(l).getTextContent();
                    l++;
					if (gradeBook.getId().equals(id)
							&& gradeBook.getItem().equals(items) && gradeBook.getGrades().equals(grades)
							&& gradeBook.getFb().equals(fb)) 
					{
						flag = true;
       					return grade;
					}
                    }
				}
			}
			for (int temp = 0; temp < nList.getLength(); temp++) {
                l=0;
				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    int s = eElement.getElementsByTagName("GradingItems").getLength();
                    System.out.println(s);
                    while(l<s)
                    {
                    String items = eElement.getElementsByTagName("GradingItems").item(l).getTextContent();
                    String grades = eElement.getElementsByTagName("Grades").item(l).getTextContent();
                    String fb = eElement.getElementsByTagName("Feedback").item(l).getTextContent();
                    Node gradeNode = eElement.getElementsByTagName("Grades").item(l);
                 
					if(gradeBook.getId().equals(id)
							&& gradeBook.getItem().equals(items))
					{
						System.out.println("yes");
						flag = true;
						Node node = eElement.getElementsByTagName("Grades").item(l);
						System.out.println("yoyo"+node.getTextContent());
						node.setTextContent(gradeBook.getGrades());
						Node node2 = eElement.getElementsByTagName("Feedback").item(l);
						System.out.println("yoyo"+node2.getTextContent());
						node2.setTextContent(gradeBook.getFb());
						/*eElement.getParentNode().removeChild(eElement.getElementsByTagName("GradingItems").item(l));
						eElement.getParentNode().removeChild(eElement.getElementsByTagName("Grades").item(l));*/
						GradeBookService.saveDoc(doc2,fullpath);
						//GradeWriteService.write(doc2,gradeBook,fullpath);
						return gradeBook;
					}
					l++;
                    }
				}
			}
			for (int temp = 0; temp < nList.getLength(); temp++) {
                l=0;
				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    int s = eElement.getElementsByTagName("GradingItems").getLength();
                    System.out.println(s);
                    String items = eElement.getElementsByTagName("GradingItems").item(0).getTextContent();
                    String grades = eElement.getElementsByTagName("Grades").item(0).getTextContent();
                    String fb = eElement.getElementsByTagName("Feedback").item(0).getTextContent();
                    l++;
                    System.out.println(nList.getLength());
                    System.out.println(gradeBook.getId());
					System.out.println(id);
					if(gradeBook.getId().equals(id))
					{
						System.out.println(gradeBook.getId());
						System.out.println(id);
						//NodeList gradingItems=eElement.getElementsByTagName("GradingItems");
						Element studentGradingElement=doc2.createElement("GradingItems");
						studentGradingElement.setTextContent(gradeBook.getItem());
		            	Element studentGradesElement=doc2.createElement("Grades");
		            	studentGradesElement.setTextContent(gradeBook.getGrades());
		            	Element studentFeedbackElement=doc2.createElement("Feedback");
		            	studentFeedbackElement.setTextContent(gradeBook.getFb());
		            	eElement.appendChild(studentGradingElement);
		            	eElement.appendChild(studentGradesElement);
		            	eElement.appendChild(studentFeedbackElement);
		            	flag = true;
		            	//rootElement.appendChild(studentElement);
						GradeBookService.saveDoc(doc2,fullpath);
						return gradeBook;
					}
                    }
			}
				System.out.println("yes");
				GradeWriteService.write(doc2,gradeBook,fullpath);
				return gradeBook;
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		return gradeBook;
		}
	
	public CreateItem readGradeBook(String fullpath, CreateItem gradeBook, Document doc2)
	{
		CreateItem grade = new CreateItem();	
		try {
			doc2.getDocumentElement().normalize();
				
			NodeList nList = doc2.getElementsByTagName("Student");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
					Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    if(id.equals(gradeBook.getId()))
                    {
				       NodeList childList = nNode.getChildNodes();
				       for (int j = 0; j < childList.getLength(); j++)
				       {
			            	if(childList.item(j).getTextContent().trim().equals(gradeBook.getItem()))
			            	{
			            		for(int i=j;i<=j+5;i++)
			            		{
			            			if(i==(j+2))
			            			{
			            		      System.out.println("Grades are - "+childList.item(i).
			            		    		  getTextContent().trim());
			            		      grade.setGrades(childList.item(i).
			            		    		  getTextContent().trim());
			            			}
			            			if(i==(j+4))
			            			{
			            				System.out.println("Feedback is - "+childList.item(i).
				            		    		  getTextContent().trim());	
			            				grade.setFb(childList.item(i).
				            		    		  getTextContent().trim());
			            			}
			            		}
			            		return grade;      	
			            }	                
				 }
			}
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return grade;
	}
}
