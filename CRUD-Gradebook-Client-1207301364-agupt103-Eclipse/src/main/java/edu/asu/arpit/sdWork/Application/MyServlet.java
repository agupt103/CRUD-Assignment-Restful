package edu.asu.arpit.sdWork.Application;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;






import javax.xml.bind.Marshaller;

import org.w3c.dom.Document;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.asu.arpit.sdWork.Model.CreateItem;

@WebServlet("/myservlet")
public class MyServlet extends HttpServlet {
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		final String dir = System.getProperty("user.dir");
		String fullPath = dir + "/file.xml";
		try{
		
        	CreateItem create = null;
        	createGradeItem grades = new createGradeItem();
        	if(request.getParameter("id") != null
        		&&	request.getParameter("item") != null && request.getParameter("grades") != null
        		&& request.getParameter("fb") != null)
        	{
        		create = new CreateItem();
        		create.setId(request.getParameter("id").replaceAll("\\s",""));
        		create.setFb(request.getParameter("fb").replaceAll("\\s",""));
        		create.setGrades(request.getParameter("grades").replaceAll("\\s",""));
        		create.setItem(request.getParameter("item").replaceAll("\\s",""));
        	}
        	Client client = Client.create();
        	WebResource web = client.resource("http://localhost:8080/CRUD-Gradebook-Server-1207301364-agupt103-Eclipse/GradeBookService/addBook")
        			.path(create.getId())
        			.path(create.getItem()).path(create.getFb())
        			.path(create.getGrades());
        	ClientResponse cResponse = web.type(MediaType.APPLICATION_XML)
        			.accept(MediaType.APPLICATION_XML).post(ClientResponse.class,create);  
            System.out.println(cResponse.getLocation());
            CreateItem createItem = cResponse.getEntity(CreateItem.class);
            JAXBContext jaxbContext = JAXBContext.newInstance(CreateItem.class);
            Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(createItem, System.out);
			request.setAttribute("createItem", createItem);
			request.getSession().setAttribute("path", fullPath);
			request.getSession().setAttribute("message", "Sucessfully created");
			request.getSession().setAttribute("location", cResponse.getLocation());
            response.sendRedirect("index.jsp");
		}
		catch(Exception e)
		{
			
		}
        }
	
}


