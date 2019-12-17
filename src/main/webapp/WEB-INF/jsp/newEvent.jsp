<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en" xmlns:c="" xmlns:form="http://www.w3.org/1999/xhtml" xmlns:fmt="">

<head>
    <meta charset="UTF-8">
    <title>Event</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
</head>
<body>
<h1>Voer de event in!</h1>
<br>

<form:form action="/event/new" modelAttribute="event" method="post">
    <label for="eventName" class="sr-only">Eventnaam:</label>
    <input type="text" id="eventName" name="eventName" value="${updateEventName}"><br><br>
    <label for="activityCategory" class="sr-only">Activiteitnaam:</label>
    <select id="activityCategory" name="activityCategory">
        <option value="Huishouden">Huishouden</option>
        <option value="Medisch">Medisch</option>
        <option value="Vrije tijd" >Vrije tijd</option>
    </select><br>
    <label for="eventComment" class="sr-only">Beschrijving:</label>
    <input type="text" id="eventComment" name="eventComment" value="${updateEventComment}"><br>
    <label for="eventDate" class="sr-only">Datum:</label>
    <div id="eventDate" class="container">

    </div>
    <br>
    <input type="submit" value="Bewaar Event!" />
</form:form>

</body>
</html>