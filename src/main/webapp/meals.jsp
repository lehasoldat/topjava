<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://javawebinar.ru/topjava/functions" prefix="f" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.LocalDate" %><%--
  Created by IntelliJ IDEA.
  User: salda
  Date: 06.02.2022
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <tr>
        <th><b>Date</b></th>
        <th><b>Description</b></th>
        <th><b>Calories</b></th>
        <th></th>
        <th></th>
    </tr>

    <c:forEach var="meal" items="${mealsTo}">
        <tr style="color: ${meal.excess ? 'red' : 'forestgreen'}">
            <td>${f:formatLocalDateTime(meal.dateTime, 'dd-MM-yyyy hh:mm')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
<br>
<br>
<a href="meals?action=edit">Create new meal</a>
</body>
</html>
