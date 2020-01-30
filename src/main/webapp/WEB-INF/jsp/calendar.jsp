<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns:jsp="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset='utf-8' />
    <title>Kalender</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <script src="${pageContext.request.contextPath}/webjars/moment/2.24.0/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>

    <link href="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.print.min.css" rel="stylesheet" media='print' />
    <script src="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/locale/nl.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/webjars/tempusdominus-bootstrap-4/5.1.2/js/tempusdominus-bootstrap-4.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/tempusdominus-bootstrap-4/5.1.2/css/tempusdominus-bootstrap-4.css" rel='stylesheet'>

    <!-- data attributes which calendar.js uses -->
    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/modal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/events.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar.js"></script>
</head>

<body>
<form role="form" id="formID" >
    <div class="modal fade" tabindex="-1" role="dialog" id="formDiv">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 id="modal-title" class="modal-title col-12">Maak nieuwe afspraak</h4>
                </div>

                <!-- select below decides the input fields for event -->
                <span>
                <div class="modal-body">
                    <div class="modal-body">
                        <div class="row">
                            <label class="col-4" for="activityCategory" control-label>Categorie</label>
                            <select name="activity.activityCategory" id="activityCategory" style="width:13.2em;" >
                            <option disabled selected="activityCategory">Selecteer categorie</option>
                                <option value="Vrije tijd" >Vrije tijd</option>
                                <option value="Medisch">Medisch</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-body" id="eventNameDiv">
                        <div class="row">
                            <label class="col-4" for="eventName">Onderwerp</label>
                            <input type="text" name="eventName" id="eventName" />
                            <input type="hidden" name="eventId" id="eventId" />
                            <input type="hidden" name="activityId" id="activityId" />
                            <input type="hidden" name="teamId" id="team.teamId"/>
                        </div>
                    </div>

                    <!-- event with activity modal input fields -->
                    <div class="modal-body" id="eventCommentDiv">
                        <div class="row">
                            <label class="col-4" for="eventComment">Beschrijving</label>
                            <input type="text" name="eventComment" id="eventComment" />
                        </div>
                    </div>

                    <!-- event with MedicationActivity modal input fields -->
                    <div class="modal-body" id="medicationChoiceDiv">
                        <div class="row">
                           <label class="col-4" for="medicationChoice" control-label>Medicijn</label>
                           <select name="medication.medicationId" id="medicationChoice" >
                               <option disabled selected="selected">Kies een medicijn</option>
                           </select>
                        </div>
                    </div>
                    <div class="modal-body" id="takenMedicationDiv">
                        <div class="row">
                            <label class="col-4" for="takenMedication" control-label>Hoeveelheid</label>
                            <input type="number" name="takenMedication" id="takenMedication" />
                        </div>
                    </div>
                </div>

                <div class="modal-body" id="eventDatesDiv">
                    <div class="form-group">
                        <div class="input-group date" id="datetimepickerStart" data-target-input="nearest">
                            <label class="col-4" for="eventStartDate">Starttijd </label>
                            <input id="eventStartDate" name="eventStartDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerStart"/>
                            <div class="input-group-append" style="width:8.3vw;" data-target="#datetimepickerStart" data-toggle="datetimepicker">
                                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group date" id="datetimepickerEnd" data-target-input="nearest">
                            <label class="col-4" for="eventEndDate">Eindtijd </label>
                            <input id="eventEndDate" name="eventEndDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerEnd"/>
                            <div class="input-group-append" style="width:8.3vw;"  data-target="#datetimepickerEnd" data-toggle="datetimepicker">
                                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-body" >
                        <div class="modal-body" >
                            <div class="row" id="eventDoneDiv">
                                <label class="col-xs-4" for="eventDone">Afspraak al uitgevoerd?&nbsp;</label>
                                <input type="checkbox" id="eventDone" name="eventDone"/>
                            </div>
                            <div class="row input-group date" id="datetimepickerDone" data-target-input="nearest">
                                <label class="col-xs-4" for="eventDoneDate">Op datum</label>
                                <input id="eventDoneDate" name="eventDoneDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerDone"/>
                                <div class="input-group-append" style="width:8.3vw;" data-target="#datetimepickerDone" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                    </span>
                <div class="modal-footer" id="modal-footer">
                    <button type="button" id="delete-event" class="btn btn-danger" data-dismiss="modal">Verwijder Afspraak</button>
                    <button type="button" class="btn btn-light" data-dismiss="modal">Sluiten</button>
                    <button type="submit" class="btn btn-primary" id="save-change-event" data-dismiss="modal">Maak afspraak</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form>
  <jsp:include page="home.jsp" />

</body>
</html>