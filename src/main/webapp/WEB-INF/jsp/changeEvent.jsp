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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
</head>
<body>
<h1>Wijzig afspraak:</h1>
<div class="container-fluid">
    <form:form id="hiddenform" action="/event/change" modelAttribute="event" method="post">
        <div class="row">
            <div class="col-xs-1">
                <p class="font-weight-normal">Kies afspraak: </p>
            </div>
            <div class="col-xs-2">
                <select id="eventCategory" name="activityCategory" onchange="display()">
                    <option disabled selected="selected">Maak keuze</option>
                    <c:forEach items="${activityList}" var="activity">
                        <option><c:out value="${activity.activityName}" /></option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div id="hidden" style="display:none;">
        <div class="row">
            <div class="col-xs-1">
                <p class="font-weight-normal">Eventnaam: </p>
            </div>
            <div class="col-xs-2">
                <input type="text" id="eventName" name="eventName" value="${updateEventName}" required>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-1">
                <p class="font-weight-normal">Beschrijving: </p>
            </div>
            <div class="col-xs-2">
                <input type="text" id="eventComment" name="eventComment" value="${updateEventComment}" required>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-1">
                <p class="font-weight-normal">Categorie: </p>
            </div>
            <div class="col-xs-2">
                <select id="activityCategory" name="activityCategory" required>
                    <option disabled selected="selected">Selecteer categorie</option>
                    <option value="Huishouden">Huishouden</option>
                    <option value="Medisch">Medisch</option>
                    <option value="Vrije tijd" >Vrije tijd</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-1">
                <p class="font-weight-normal">Datum: </p>
            </div>
            <div class="col-xs-2">
                <div class='col-sm'>
                    <div class="form-group">
                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' class="form-control" name="eventDate" required>
                            <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-1">
            </div>
            <div class="col-xs-2">
                <form:input path="eventId" type="hidden" />
                <input type="submit" value="Wijzig afspraak!" />
            </div>
        </div>
    </form:form>
    </div>
</div>

</body>
<script>
    $(function() {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY HH:mm',
        });
    });
</script>
<script>
    function display(){
        document.getElementById("hidden").style.display = "block";
}
</script>
</html>