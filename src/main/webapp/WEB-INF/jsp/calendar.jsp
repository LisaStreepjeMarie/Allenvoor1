<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="" xmlns:jsp="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset='utf-8' />
    <title>Kalender</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <link href="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.print.min.css" rel="stylesheet" media='print' />
    <script src="${pageContext.request.contextPath}/webjars/moment/2.24.0/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/locale/nl.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.0.6/web-fonts-with-css/css/fontawesome-all.min.css" rel='stylesheet'>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/webjars/tempusdominus-bootstrap-4/5.1.2/js/tempusdominus-bootstrap-4.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/tempusdominus-bootstrap-4/5.1.2/css/tempusdominus-bootstrap-4.css" rel='stylesheet'>

    <script src="${pageContext.request.contextPath}/js/calendar.js"></script>
</head>

<body>
<form:form id="modal-form" action="${pageContext.request.contextPath}/event/change" modelAttribute="event" method="post">
    <div class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 id="modal-title" class="modal-title col-12">Maak nieuwe afspraak</h4>
                </div>

                <!-- select below decides the input fields for event -->
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <span style="margin-left:2em">
                            <label class="col-xs-4" for="selectie" control-label>Categorie</label>
                                <select name="activity.activityCategory" id="selectie" >
                                <option disabled selected="selected">Selecteer categorie</option>
                                    <option value="Vrije tijd" >Vrije tijd</option>
                                    <option value="Medisch">Medisch</option>
                                </select>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row" id="ShowEventName">
                    <div class="col-12">
                        <label class="col-4" for="eventName">Onderwerp</label>
                        <input type="text" name="eventName" id="eventName" />
                        <input type="hidden" name="eventId" id="eventId" />
                        <input type="hidden" name="teamId" id="team.teamId" />
                    </div>
                </div>

                <!-- event with activity modal input fields -->
                <div class="modal-body" id="eventActivity">
                    <div class="row">
                        <div class="col-12">
                            <label class="col-4" for="eventComment">Beschrijving</label>
                            <input type="text" name="eventComment" id="eventComment" />
                        </div>
                    </div>
                </div>

                <!-- event with MedicationActivity modal input fields -->
                <div class="modal-body" id="medicationActivity">
                    <div class="row">
                        <div class="col-xs-12" modelAttribute="medicationActivity">
                           <span style="margin-left:2em">
                           <label class="col-xs-4" for="selectie2" control-label>Medicijn</label>
                                <select name="medication.medicationId" id="selectie2" >
                                <option disabled selected="selected">Kies een medicijn</option>
                                    <c:forEach var="medication" items="${medicationList}">
                                        <option value="${medication.medicationId}">${medication.medicationName}</option>
                                    </c:forEach>
                            </select>
                           </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12" modelAttribute="medicationActivity">
                            <span style="margin-left:2em">
                            <label class="col-xs-4" for="takenMedication" control-label>Hoeveelheid</label>
                            <input type="number" name="takenMedication" id="takenMedication" />
                            </span>
                        </div>
                    </div>
                </div>
                <div class="modal-body" id="ShowDates">
                    <div class="container">
                        <div class='col-md-10'>
                            <div class="form-group">
                                <div class="input-group date" id="datetimepickerStart" data-target-input="nearest">
                                    <label class="col-xs-4" for="eventStartDate">Starttijd</label>
                                    <input id="eventStartDate" name="eventStartDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerStart"/>
                                    <div class="input-group-append" data-target="#datetimepickerStart" data-toggle="datetimepicker">
                                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class='col-md-10'>
                            <div class="form-group">
                                <div class="input-group date" id="datetimepickerEnd" data-target-input="nearest">
                                    <label class="col-xs-4" for="eventEndDate">Eindtijd</label>
                                    <input id="eventEndDate" name="eventEndDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerEnd"/>
                                    <div class="input-group-append" data-target="#datetimepickerEnd" data-toggle="datetimepicker">
                                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12" id="eventDoneDiv">
                            <span style="margin-left:2em">
                                <label class="col-xs-4" for="eventDone">Afspraak al uitgevoerd?</label>
                                <input type="checkbox" id="eventDone" name="eventDone"/>
                            </span>
                    </div>
                </div>
                <div class="row" id="eventDoneDateDiv">
                    <div class="col-xs-12">
                            <span style="margin-left:2em">
                                <label class="col-xs-4" id="eventDone" for="eventDoneDate">Op datum</label>
                                <div class="form-group">
                                <div class="input-group date" id="datetimepickerDone" data-target-input="nearest">
                                    <label class="col-xs-4" for="eventDoneDate">Eindtijd</label>
                                    <input id="eventDoneDate" name="noEventDoneDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerDone"/>
                                    <div class="input-group-append" data-target="#datetimepickerDone" data-toggle="datetimepicker">
                                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                    </div>
                                </div>
                            </div>
                            </span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" id="delete-event" class="btn btn-danger" data-dismiss="modal" >Verwijder Afspraak</button>
                    <button type="button" class="btn btn-light" data-dismiss="modal">Sluiten</button>
                    <form:errors path="eventName" cssClass="error" />
                    <button type="submit" class="btn btn-primary" id="save-change-event">Maak afspraak</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form:form>
  <jsp:include page="home.jsp" />
<c:if test="${not empty team.teamName}">
    <div class="badge badge-primary text-wrap" style="width: 45rem; height: 4rem; margin-left:308px;">
        <p>
        <h5>Agenda van de groep: ${team.teamName}</h5>
        </p>
    </div>
</c:if>
</body>
</html>