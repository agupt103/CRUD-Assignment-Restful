package edu.asu.arpit.sdWork.Application;

import java.io.File;
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

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

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
        		create.setItem(request.getParameter("item".replaceAll("\\s","")));
        		Client client = Client.create();
            	WebResource web = client.resource("http://localhost:8080/CRUD-Gradebook-Server-1207301364-agupt103-Eclipse/GradeBookService/deleteBook")
            			.path(create.getId())
            			.path(create.getItem());
                ClientResponse cresponse = web
        				.delete(ClientResponse.class);
                System.out.println("yes del");
                CreateItem exists = cresponse.getEntity(CreateItem.class);
                System.out.println(cresponse.getStatus());
                
                if(cresponse.getStatus() != 404)
                {
                if(cresponse.getStatus() == 200)
                {
                	String message = "Details Deleted";
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
                	request.getSession().setAttribute("location", cresponse.getLocation());
                	response.sendRedirect("delete.jsp");
                }
                else
                {
                	String message = "Details not present!!Can't delete....";
                	request.getSession().setAttribute("message", message);
                	request.getSession().setAttribute("path", fullPath);
                	request.getSession().setAttribute("location", cresponse.getLocation());
                	response.sendRedirect("delete.jsp");
                }
                }
                else
                {
                	String message = "No file available....";
                	request.getSession().setAttribute("message", message);
                	response.sendRedirect("delete.jsp");
                }
        	}
        	else
        	{
        		String message = "Enter the id of the student and the item";
        		request.getSession().setAttribute("message", message);
        		response.sendRedirect("delete.jsp");
        	}
		}
		catch(Exception e)
		{
			
		}
	}
}
