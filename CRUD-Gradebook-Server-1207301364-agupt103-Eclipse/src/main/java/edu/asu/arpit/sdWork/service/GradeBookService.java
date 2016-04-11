package edu.asu.arpit.sdWork.service;

import java.io.File;
import java.lang.*;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;

import edu.asu.arpit.sdWork.Model.CreateItem;


@Path("/GradeBookService")
public class GradeBookService{
	private static final Logger LOG = LoggerFactory
			.getLogger(GradeBookService.class);
	@Context
    private UriInfo context;

	@POST
	@Path("/addBook/{id}/{item}/{fb}/{grades}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createGradeBook(@PathParam("id") String id,@PathParam("item") String item,
			@PathParam("fb") String fbs,@PathParam("grades") String grade) throws IOException, JAXBException{
		Document doc = null;
		try {
			CreateItem gradeBook = new CreateItem();
			gradeBook.setId(id);
			gradeBook.setItem(item);
			gradeBook.setGrades(grade);
			gradeBook.setFb(fbs);
			System.out.println("File saved!");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    		// root elements
    		doc = docBuilder.newDocument();
    		Element rootElement = doc.createElement("Gradebook");
    		doc.appendChild(rootElement);

    		// staff elements
    		Element student = doc.createElement("Student");
    		rootElement.appendChild(student);

    		// set attribute to staff element

    		// shorten way
    		 student.setAttribute("id", gradeBook.getId());

    		// grading Items elements
    		Element gradeItems = doc.createElement("GradingItems");
    		gradeItems.appendChild(doc.createTextNode(gradeBook.getItem()));
    		student.appendChild(gradeItems);

    		// grades elements
    		Element grades = doc.createElement("Grades");
    		grades.appendChild(doc.createTextNode(gradeBook.getGrades()));
    		student.appendChild(grades);

    		// feedback elements
    		Element fb = doc.createElement("Feedback");
    		fb.appendChild(doc.createTextNode(gradeBook.getFb()));
    		student.appendChild(fb);

    		// write the content into xml file
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    		DOMSource source = new DOMSource(doc);
            
    		final String dir = System.getProperty("user.dir");
			String fullPath = dir + "/file.xml";
			//File file = new File(fullPath);
    		StreamResult result = new StreamResult(new File(fullPath));

    		transformer.transform(source, result);
    		System.out.println("File saved!");
    		URI locationURI = URI.create("http://localhost:8080/CRUD-Gradebook-Server-1207301364-agupt103-Eclipse/GradeBookService/"
    				+ "readBook"+ "/"+id+"/"+item);
            System.out.println(locationURI.toString());
    		return Response.status(200).location(locationURI).entity(gradeBook).build();

    	  } catch (ParserConfigurationException pce) {
    		pce.printStackTrace();
    	  } catch (TransformerException tfe) {
    		tfe.printStackTrace();
    	  }
		String loc = context.getAbsolutePath().toString();
		
		URI locationURI = URI.create(context.getAbsolutePath().toString());
        System.out.println(locationURI.toString());
		return Response.status(200).location(locationURI).entity(doc).build();
		
	}
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response updateGradeBook(CreateItem gradeBook) throws IOException, SAXException{   
		CreateItem grade = new CreateItem();
		try {
			GradeReadService read = new GradeReadService();
			Boolean flag=false;
			final String dir = System.getProperty("user.dir");
			String fullPath = dir + "/file.xml";
			File file = new File(fullPath);
			if (file.exists()) 
			{
				
				InputStream inputStream= new FileInputStream(file);
	            Reader reader = new InputStreamReader(inputStream,"UTF-8");
	            InputSource is = new InputSource(reader);
	            is.setEncoding("UTF-8");   
			    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		    Document doc = docBuilder.parse(is);
    		    grade= read.readGrades(fullPath,gradeBook,doc);
    		    if(grade.getId() == null)
    		    {
    		    	System.out.println("Already present");
    		    	return Response.status(404).entity(grade).build();
    		    }
    		// root elements
    		    Element eElement=doc.createElement("Student");
				eElement.setAttribute("id", gradeBook.getId());
				Element gradeItems=doc.createElement("GradingItems");
				gradeItems.setTextContent(String.valueOf(gradeBook.getItem()));
				Element egrades=doc.createElement("Grades");
				egrades.setTextContent(gradeBook.getGrades());
				Element eFb=doc.createElement("Feedback");
				eFb.setTextContent(gradeBook.getFb());

				eElement.appendChild(gradeItems);
				eElement.appendChild(egrades);
				eElement.appendChild(eFb);
				Element rootTag=(Element) doc.getElementsByTagName("Gradebook").item(0);
				rootTag.appendChild(eElement);
            
    		//saveDoc(doc,"D://file.xml");
    		System.out.println("File saved!");
    		return Response.status(200).entity(gradeBook).build();
			}
			else
			{
				return Response.status(404).entity(gradeBook).build();
			}
    	  } catch (ParserConfigurationException pce) {
    		pce.printStackTrace();
    	  }
		return null;
	}
	
	@GET
	@Path("/readBook/{id}/{item}")
	@Produces(MediaType.APPLICATION_XML)
	public Response readGradeBook(@PathParam("id") String id,@PathParam("item") String item) throws IOException, SAXException{   
		CreateItem gradeBook = new CreateItem();
		gradeBook.setId(id);
		gradeBook.setItem(item);
		CreateItem grade = new CreateItem();
		try {
			GradeReadService read = new GradeReadService();
			Boolean flag=false;
			final String dir = System.getProperty("user.dir");
			String fullPath = dir + "/file.xml";
			File file = new File(fullPath);
			if (file.exists()) 
			{
				
				InputStream inputStream= new FileInputStream(file);
	            Reader reader = new InputStreamReader(inputStream,"UTF-8");
	            InputSource is = new InputSource(reader);
	            is.setEncoding("UTF-8");   
			    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		    Document doc = docBuilder.parse(is);
    		    grade= read.readGradeBook(fullPath,gradeBook,doc);
    		    URI locationURI = URI.create(context.getAbsolutePath().toString());
                System.out.println(locationURI.toString());
    		    if(grade.getGrades() !=null && grade.getFb() != null)
    		    {
    		    	System.out.println("Already present");
    		    	return Response.status(200).location(locationURI).entity(grade).build();
    		    }
			}
			else
			{
				return Response.status(404).entity(gradeBook).build();
			}
    	  } catch (ParserConfigurationException pce) {
    		pce.printStackTrace();
    	  }
		URI locationURI = URI.create(context.getAbsolutePath().toString());
		return Response.status(400).location(locationURI).entity(grade).build();
	}
	
	@DELETE
	@Path("/deleteBook/{id}/{item}")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteGradeBook(@PathParam("id") String id,@PathParam("item") String item) throws IOException, SAXException{   
		CreateItem gradeBook = new CreateItem();
		gradeBook.setId(id);
		gradeBook.setItem(item);
		CreateItem grade = new CreateItem();
		try {
			GradeDeleteService delete = new GradeDeleteService();
			Boolean flag=false;
			final String dir = System.getProperty("user.dir");
			String fullPath = dir + "/file.xml";
			File file = new File(fullPath);
			System.out.println("Yes delte");
			if (file.exists()) 
			{		
				InputStream inputStream= new FileInputStream(file);
	            Reader reader = new InputStreamReader(inputStream,"UTF-8");
	            InputSource is = new InputSource(reader);
	            is.setEncoding("UTF-8");   
			    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		    Document doc = docBuilder.parse(is);
    		    grade= delete.deleteGradeBook(fullPath,gradeBook,doc);
    		    if(grade.getId() != null)
    		    {
    		    	if(grade.getItem()!=null)
    		    	{
    		    	System.out.println("Already present");
    		    	 URI locationURI = URI.create(context.getAbsolutePath().toString());
    		    	return Response.status(200).location(locationURI).entity(grade).build();
    		    	}
    		    	else
    		    	{
    		    		System.out.println("Already present");
    		    		 URI locationURI = URI.create(context.getAbsolutePath().toString());
        		    	return Response.status(201).location(locationURI).entity(grade).build();
    		    	}
    		    }
			}
			else
			{
				return Response.status(404).entity(grade).build();
			}
    	  } catch (ParserConfigurationException pce) {
    		pce.printStackTrace();
    	  }
		 URI locationURI = URI.create(context.getAbsolutePath().toString());
		return Response.status(401).location(locationURI).entity(grade).build();
	}
	
	public static String saveDoc(Node node, String fullPath) throws TransformerFactoryConfigurationError, TransformerException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		Transformer xform = TransformerFactory.newInstance().newTransformer();
	    Result output=new StreamResult(new File(fullPath));
	    Source input=new DOMSource(node);
	    transformer.transform(input, output);
	    return output.toString();
	}
	}
