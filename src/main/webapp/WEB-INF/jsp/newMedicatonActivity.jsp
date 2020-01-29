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
        <br />
        <div class="row">
            <div class="col-xs-12" id="showMedication" modelAttribute="medicationActivity">
               <label class="col-xs-4" for="medication" control-label>Medicijn</label>
                <select name="medication.medicationId" id="medication.medicationId" >
                    <option disabled selected="selected">Kies een medicijn</option>
                    <c:forEach var="medication" items="${medicationList}">
                        <option value="${medication.medicationId}">${medication.medicationName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12" id="showMedicationAmount" modelAttribute="medicationActivity">
                <label class="col-xs-4" for="takenMedication" control-label>Hoeveelheid</label>
                <input type="number" name="takenMedication" id="takenMedication" />
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12" id="showStartDate">
                <label class="col-xs-4" for="eventStartDate">Datum</label>
                <input type="text" name="eventStartDate" id="eventStartDate" />
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12" id="showEndDate">
                <label class="col-xs-4" for="eventEndDate">EindDatum</label>
                <input type="text" name="eventEndDate" id="eventEndDate" />
            </div>
        </div>
    </body>
</html>