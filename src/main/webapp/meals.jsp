<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=add">Add meal</a>
<table border="1">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${meal.excess ? 'red' : 'green'}">
            <th>${TimeUtil.format(meal.dateTime)}</th>
            <th>${meal.description}</th>
            <th>${meal.calories}</th>
            <th><a href="meals?id=${meal.id}&action=edit">edit</a></th>
            <th><a href="meals?id=${meal.id}&action=delete">delete</a></th>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
