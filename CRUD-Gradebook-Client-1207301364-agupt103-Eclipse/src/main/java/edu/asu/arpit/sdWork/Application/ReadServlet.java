package edu.asu.arpit.sdWork.Application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.asu.arpit.sdWork.Model.CreateItem;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@WebServlet("/ReadServlet")
public class ReadServlet extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		final String dir = System.getProperty("user.dir");
		String fullPath = dir + "/file.xml";
		request.getSession().setAttribute("message", "");
    	request.getSession().setAttribute("readGrades", "");
    	request.getSession().setAttribute("fb", "");
    	request.getSession().setAttribute("path", "");
    	request.getSession().setAttribute("location", "");
		try{
		
        	CreateItem create = null;
        	createGradeItem grades = new createGradeItem();
        	if(!request.getParameter("id").isEmpty()
        		&&	!request.getParameter("item").isEmpty())
        	{
        		create = new CreateItem();
        		create.setId(request.getParameter("id").replaceAll("\\s",""));
        		create.setItem(request.getParameter("item").replaceAll("\\s",""));
        		Client client = Client.create();
            	WebResource web = client.resource("http://localhost:8080/CRUD-Gradebook-Server-1207301364-agupt103-Eclipse/GradeBookService/readBook").path(create.getId())
            			.path(create.getItem());
                 
                ClientResponse cresponse = web
        				.accept(MediaType.APPLICATION_XML).get(ClientResponse.class); 
                CreateItem exists = cresponse.getEntity(CreateItem.class);
    			/*JAXBContext context = JAXBContext.newInstance(CreateItem.class);
    			Marshaller m = context.createMarshaller();
    			m.marshal(exists, System.out);*/
                System.out.println("Grades - "+exists.getGrades());
                System.out.println("Feedback - "+exists.getFb());
                if(cresponse.getStatus() != 404)
                {
                if(exists.getGrades() != null && exists.getFb() != null)
                {
                	String message = "Details present";
                	String readGrades = exists.getGrades();
                	String fb = exists.getFb();
                	request.getSession().setAttribute("message", message);
                	request.getSession().setAttribute("readGrades", readGrades);
                	request.getSession().setAttribute("fb", fb);
                	request.getSession().setAttribute("path", fullPath);
                	request.getSession().setAttribute("location", cresponse.getLocation());
                	response.sendRedirect("read.jsp");
                }
                else
                {
                	String message = "Details not present";
                	request.getSession().setAttribute("message", message);
                	request.getSession().setAttribute("location", cresponse.getLocation());
                	response.sendRedirect("read.jsp");
                }
                }
                else
                {
                	String message = "No file available....";
                	request.getSession().setAttribute("message", message);
                	response.sendRedirect("read.jsp");
                }
        	}
        	else
        	{
        		String message = "Enter all the details";
        		request.getSession().setAttribute("message", message);
        		response.sendRedirect("read.jsp");
        	}
        	
		}
		catch(Exception e)
		{
			
		}
	}

}
