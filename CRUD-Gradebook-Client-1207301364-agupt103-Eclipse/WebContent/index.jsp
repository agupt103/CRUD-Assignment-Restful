<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Client started!</h1>
<h2>Select the task</h2>
<a href="abc.jsp" id="CreateCourse" >Create Gradebook</a>&nbsp;
<a href="update.jsp" id="AuthorizationManager">Update Gradebook</a>
<a href="read.jsp" id="AuthorizationManager">Read Gradebook</a>
<a href="delete.jsp" id="AuthorizationManager">Delete Gradebook</a>
<br>
<br>
<c:if test="${not empty message}">
<p>${message}</p>
<c:remove var="message" scope="session" /> 
</c:if>
<c:if test="${not empty path}">
<p>You can view the output file at this path: ${path}</p>
<c:remove var="path" scope="session" /> 
</c:if>
<br>
<c:if test="${not empty location}">
<p>The URI for reading is: ${location}</p>
<c:remove var="location" scope="session" /> 
</c:if>
<br>
</body>
</html>