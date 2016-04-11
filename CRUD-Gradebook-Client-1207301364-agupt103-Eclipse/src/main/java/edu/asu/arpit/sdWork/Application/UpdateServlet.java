package edu.asu.arpit.sdWork.Application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.asu.arpit.sdWork.Model.CreateItem;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		final String dir = System.getProperty("user.dir");
		String fullPath = dir + "/file.xml";
		try{
		
        	CreateItem create = null;
        	createGradeItem grades = new createGradeItem();
        	if(!request.getParameter("id").isEmpty()
        		&&	!request.getParameter("item").isEmpty())
        	{
        		create = new CreateItem();
        		create.setId(request.getParameter("id").replaceAll("\\s",""));
        		create.setFb(request.getParameter("fb").replaceAll("\\s",""));
        		create.setGrades(request.getParameter("grades").replaceAll("\\s",""));
        		create.setItem(request.getParameter("item".replaceAll("\\s","")));
        		Client client = Client.create();
            	WebResource web = client.resource("http://localhost:8080/CRUD-Gradebook-Server-1207301364-agupt103-Eclipse/GradeBookService");
                ClientResponse cresponse = web.path(request.getParameter("id").replaceAll("\\s","")).type(MediaType.APPLICATION_XML)
        				.accept(MediaType.APPLICATION_XML).put(ClientResponse.class,create);
                CreateItem exists = cresponse.getEntity(CreateItem.class);
                if(cresponse.getStatus() != 404)
                {
                if(exists.getId() != null)
                {
                	String message = "Details Updated";
                	String id = exists.getId();
                	String item = exists.getItem();
                	String readGrades = exists.getGrades();
                	String fb = exists.getFb();
                	request.getSession().setAttribute("message", message);
                	request.getSession().setAttribute("item", item);
                	request.getSession().setAttribute("id", id);
                	request.getSession().setAttribute("readGrades", readGrades);
                	request.getSession().setAttribute("fb", fb);
                	request.getSession().setAttribute("path", fullPath);
                	response.sendRedirect("update.jsp");
                }
                else
                {
                	String message = "Duplicate details!! No need to update...";
                	request.getSession().setAttribute("message", message);
                	request.getSession().setAttribute("path", fullPath);
                	response.sendRedirect("update.jsp");
                }
                }
                else
                {
                	String message = "No file available....";
                	request.getSession().setAttribute("message", message);
                	response.sendRedirect("update.jsp");
                }
        	}
        	else
        	{
        		String message = "Enter the id of the student and the item";
        		request.getSession().setAttribute("message", message);
        		response.sendRedirect("update.jsp");
        	}
            //re.getStatus();
        	//grades.createGradeBook(create);
		}
		catch(Exception e)
		{
			
		}
	}

}
