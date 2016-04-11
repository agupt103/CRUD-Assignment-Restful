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
<form  action="${pageContext.request.contextPath}/ReadServlet" method="POST">
Enter the id of the student:
<input type="text" name="id">
<br>
<br>
Grading Item(Assignment,Midterm and others):
<input type="text" name="item">
<br>
<br>
<br>
<br>
<c:if test="${not empty message}">
<p>Message: ${message}</p>
<c:remove var="message" scope="session" /> 
</c:if>
<c:choose>
<c:when test="${not empty readGrades}">
       <p>Grades: ${readGrades}</p>
<c:remove var="readGrades" scope="session" /> 
    </c:when>    
    <c:otherwise>
       <p>Grades: No grades retrieved</p>
    </c:otherwise>
    </c:choose>
    <c:choose>
<c:when test="${not empty fb}">
       <p>Feedback: ${fb}</p>
<c:remove var="fb" scope="session" /> 
    </c:when>    
    <c:otherwise>
       <p>Feedback: No Feedback retrieved</p>
        <br />
    </c:otherwise>
    </c:choose>
    <br>
<br>
<c:if test="${not empty location}">
<p>The URI for reading is: ${location}</p>
<c:remove var="location" scope="session" /> 
</c:if>
<br>
<c:if test="${not empty path}">
<p>You can view the output file at this path: ${path}</p>
<c:remove var="path" scope="session" /> 
</c:if>
<!--<c:if test="${empty readGrades}">
<p>Grades: No grades retreived</p>
</c:if>
<c:if test="${empty fb}">
<p>Feedback: ${fb}</p>
</c:if>
<c:if test="${not empty readGrades}">
<p>Grades: ${readGrades}</p>
<c:remove var="readGrades" scope="session" /> 
</c:if>-->
<input type="submit" value="Submit">
</form>
<br>
<br>
<a href="index.jsp" id="AuthorizationManager">Home</a>
</body>
</html>