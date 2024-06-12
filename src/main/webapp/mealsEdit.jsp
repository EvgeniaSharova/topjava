<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <title>Meal</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>
<section>
    <form method="post" action="meals">
        <input type="hidden" name="id">
        <dl>
            <dt>Date Time</dt>
            <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}}"></dd>
        </dl>
        <dl>
            <dt>Description</dt>
            <dd><input type="text" size="30" name="description" value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt>Calories</dt>
            <dd><input type="number" size="10" name="calories" value="${meal.calories}"></dd>
        </dl>
        <button type="submit">Save</button>
        <button class="delete" type="reset" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>