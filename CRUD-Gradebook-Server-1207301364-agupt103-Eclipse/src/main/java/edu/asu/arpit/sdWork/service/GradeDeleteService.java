package edu.asu.arpit.sdWork.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.asu.arpit.sdWork.Model.CreateItem;

public class GradeDeleteService {
	public CreateItem deleteGradeBook(String fullpath, CreateItem gradeBook, Document doc2)
	{
		CreateItem grade = new CreateItem();
		boolean flag=false;
		int l=0;
		try{
			doc2.getDocumentElement().normalize();

			System.out.println("Root element :" + doc2.getDocumentElement().getNodeName());
					
			NodeList nList = doc2.getElementsByTagName("Student");
		
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
                if(gradeBook.getId().equals(id) && !gradeBook.getItem().equals(items))
                		{
                	     grade.setId(id);
                	     grade.setItem(null);
                		}
				if(gradeBook.getId().equals(id)
						&& gradeBook.getItem().equals(items))
				{
					System.out.println("yes");
					flag = true;
					grade.setId(gradeBook.getId());
					Node node1 = eElement.getElementsByTagName("GradingItems").item(l);
					System.out.println("yoyo"+node1.getTextContent());
					grade.setItem(node1.getTextContent());
					node1.getParentNode().removeChild(node1);
					Node node2 = eElement.getElementsByTagName("Grades").item(l);
					System.out.println("yoyo"+node2.getTextContent());
					grade.setGrades(node2.getTextContent());
					node2.getParentNode().removeChild(node2);
					Node node3 = eElement.getElementsByTagName("Feedback").item(l);
					System.out.println("yoyo"+node3.getTextContent());
					grade.setFb(node3.getTextContent());
					node3.getParentNode().removeChild(node3);
					/*eElement.getParentNode().removeChild(eElement.getElementsByTagName("GradingItems").item(l));
					eElement.getParentNode().removeChild(eElement.getElementsByTagName("Grades").item(l));*/
					GradeBookService.saveDoc(doc2,fullpath);
					//GradeWriteService.write(doc2,gradeBook,fullpath);
					return grade;
				}
				l++;
                }
			}
		}
		}
		catch(Exception e)
		{
			
		}
		return grade;
	
	}
}
