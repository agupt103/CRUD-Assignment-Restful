<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="edu.asu.arpit.sdWork.Application.*, java.util.*, java.io.*" %>
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
<form  action="${pageContext.request.contextPath}/myservlet" method="POST">
Please enter the id of the student:
<input type="text" name="id">
<br>
<br>
Grading Item(Assignment,Midterm and others):
<input type="text" name="item">
<br>
<br>
Grades:
<input type="text" name="grades">
<br>
<br>
Feedback:
<input type="text" name="fb">
<br>
<br>
<c:if test="${not empty path}">
<p>You can view the output file at this path: ${path}</p>
<c:remove var="path" scope="session" /> 
</c:if>
<br>
<br>
<input type="submit" value="Submit">
</form>
       <br>
<br>
<a href="index.jsp" id="AuthorizationManager">Home</a> 
       
</body>
</html>