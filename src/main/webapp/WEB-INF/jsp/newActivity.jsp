<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en" xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="UTF-8">
    <title>Activity</title>
</head>
<body>
<h1>Voer de activiteit in!</h1>
<br>

<form:form action="/activity/new" modelAttribute="activity" method="post">
    <input type="text" name="activityName" value="${updateActivityName}"><br><br>
        <select name="activityCategory">
            <option value="Huishouden">Huishouden</option>
            <option value="Medisch">Medisch</option>
            <option value="Vrije tijd" >Vrije tijd</option>
        </select>
    <input type="submit" value="Bewaar activiteit!">
</form:form>

</body>
</html>