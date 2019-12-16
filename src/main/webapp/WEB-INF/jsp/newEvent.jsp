<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en" xmlns:c="" xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="UTF-8">
    <title>Event</title>
</head>
<body>
<h1>Voer de event in!</h1>
<br>

<form:form action="/event/new" modelAttribute="event" method="post">
    <label for="eventName" class="sr-only">Eventnaam:</label>
    <input type="text" id="eventName" name="eventName" value="${updateEventName}"><br><br>
    <label for="eventCategory" class="sr-only">Activiteitnaam:</label>
    <select id="eventCategory" name="activityCategory">
        <c:forEach items="${activityList}" var="activity">
            <option><c:out value="${activity.activityName}" /></option>
        </c:forEach>
    </select><br>
    <label for="eventComment" class="sr-only">Beschrijving:</label>
    <input type="text" id="eventComment" name="eventComment" value="${updateEventComment}"><br>
    <label for="eventDate" class="sr-only">Datum:</label>
    <input type="text" id="eventDate" name="eventComment" value="${updateEventComment}"><br>

    <input type="submit" value="Bewaar Event!">
</form:form>

</body>
</html>