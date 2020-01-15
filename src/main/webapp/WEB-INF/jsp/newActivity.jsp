<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
</head>
<body>
  <jsp:include page="newEvent.jsp" />
<h1>Voer de activiteit in!</h1>
<br>

<form action="/activity/new" modelAttribute="activity" method="Post">
    <input type="text" name="activityName" value="${updateActivityName}"><br><br>
        <select name="activityCategory">
            <option value="Huishouden" name="activityCategory">Huishouden</option>
            <option value="Medisch">Medisch</option>
            <option value="Vrije tijd" >Vrije tijd</option>
        </select>
    <input type="submit" value="Bewaar activiteit!">
</form>

</body>
</html>