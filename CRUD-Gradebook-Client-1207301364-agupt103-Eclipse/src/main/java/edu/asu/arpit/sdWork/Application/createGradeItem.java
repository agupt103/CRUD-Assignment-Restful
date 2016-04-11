package edu.asu.arpit.sdWork.Application;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import edu.asu.arpit.sdWork.Model.CreateItem;

public class createGradeItem {
	
	private WebResource webResource;
	private Client client;
	private static final String BASE_URI = "http://localhost:8080/CRUD-Gradebook-Server-1207301364-agupt103-Eclipse";
	public void createGradeBook(CreateItem gradeBook) throws JAXBException 
	{
		webResource = client.resource(BASE_URI).path("GradeBookService/addBook");

		ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, gradeBook);
		
	}

}
