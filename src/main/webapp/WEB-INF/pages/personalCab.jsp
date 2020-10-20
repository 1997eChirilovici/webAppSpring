<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>${title}</h1>
<br>
<h2>${message}</h2>
<c:forEach items="${users}" var="user">
    <c:choose>
        <c:when test="${role=='ROLE_USER'}">
            <p>First Name: ${user.firstName} | Last Name: ${user.lastName} | <a href="details">more details</a></p>
        </c:when>
        <c:otherwise>
            <p>First Name: ${user.firstName} | Last Name: ${user.lastName} </p>
        </c:otherwise>
    </c:choose>
</c:forEach>

</body>
</html>