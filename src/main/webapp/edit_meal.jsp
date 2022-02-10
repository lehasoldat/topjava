<%--
  Created by IntelliJ IDEA.
  User: salda
  Date: 07.02.2022
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${param.action == 'update' ? 'Edit meal' : 'Create meal'}</h2>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="meals">
    <p>
        <input type="hidden" name="id" value="${meal.id}"><br><br>
        DateTime: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"><br><br>
        Description:<input type="text" name="description" value="${meal.description}"><br><br>
        Calories:<input type="text" name="calories" value="${meal.calories}"><br><br>
        <input type="submit" value="Save"> <input type="button" value="Cancel" onclick="window.location.href='meals'">
    </p>
</form>
</body>
</html>
