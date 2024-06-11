<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meal Edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal Edit</h2>
<section>
    <form action="meals">
        <dl>
            <dt>Date Time</dt>
            <dd><input type="datetime-local" value=""></dd>
        </dl>
        <dl>
            <dt>Description</dt>
            <dd><input type="text" size="30" value=""></dd>
        </dl>
        <dl>
            <dt>Calories</dt>
            <dd><input type="text" size="10"></dd>
        </dl>
        <button type="submit">Save</button>
        <button class="delete" type="reset" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>