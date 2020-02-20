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
    <style>

.modal-footer {
    border: 0;
    }

    .tabs {

    position: fixed;
    width: 90px;
    height: 90px;

}

@media (min-width: 768px) {
  .tabs {
    top: calc(12px + 9%);
    right: calc(234.5px + 50%);
    left: auto;
  }

}

.modal-header {
    background: #98639C;
    padding: 0p;
    height: 57px;
    border: 0;
}

.tabs-top {
    margin-bottom: 1px;
    margin-top: 1px;
}

.nav-tabs {
    border: 0;

}

.tabs-4 .nav-tabs > li {
    width: 200px;
    border: 0;

}

.nav-tabs > li > a {
    width: 230px;

    background: #98639C;
    color: #fff;

    text-align: center;
}

.nav-tabs > li > a:hover,
.nav-tabs > li.active > a,
.nav-tabs > li.active > a:hover,
.nav-tabs > li.active > a:focus {
    border: 0;
    background: #98639C;
    color: #ffd800;
}

.nav-tabs > li.active > a {
    border: 0;
    background: #98639C;
    color: #fff;
}

.nav-tabs > li.active > a.active {
    border: 0;
    background: #98639C;
    color: #ffd800;
}


.nav-item {
    vertical-align:top;
}
</style>
</head>

<body>
<!-- Modal -->
<div class="modal fade newModal" id="newModal" style="widh: 200px;" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <!-- tab options on top -->
            <div class="modal-header">
                <div class="tabbable">
                    <ul class="nav nav-tabs" role="tablist">
                        <li >
                            <a class="nav-link" onclick="optionLeisureActivity()" href="#leisureActivity" data-toggle="tab">
                                Vrije tijd
                            </a>
                        </li>
                        <li>
                            <a class="nav-link"  onclick="optionsMedicationActivity()" href="#MedicationActivity" data-toggle="tab">
                                Medisch
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- model content divs -->
            <div class="row mt-4">
                <div class="col-8">
                    <div class="tab-content" id="nav-tabContent">
                        <!-- event titel starts here, same for leisureActivity and medicationActivity -->
                        <div class="modal-body" id="eventNameDiv">
                            <div class="row col-4">
                                <input type="text" placeholder="Titel"name="eventName" id="eventName" />
                                <input type="hidden" name="eventId" id="eventId" />
                                <input type="hidden" name="activityId" id="activityId" />
                                <input type="hidden" name="teamId" id="team.teamId"/>
                                <input type="hidden" name="teamName" value="${team.teamName}" id="teamName"/>
                            </div>
                        </div>

                        <!-- leisureActivity options start here -->
                        <div class="tab-pane fade" id="leisureActivity" role="tabpanel" >
                            <!-- TODO needs to be more then 1 line, maybe a text box -->
                            <div class="modal-body" id="eventCommentDiv">
                                <div class="row col-4">
                                    <input type="text" placeholder="Beschrijving" name="eventComment" id="eventComment" />
                                </div>
                            </div>
                        </div>

                        <!-- medicationActivity options start here -->
                        <div class="tab-pane fade" id="MedicationActivity" role="tabpanel" >
                            <div class="modal-body" id="medicationChoiceDiv">
                                <div class="row col-4">
                                    <select name="medication.medicationId" id="medicationChoice" >
                                        <option disabled selected="selected">Medicijn</option>
                                    </select>
                                </div>
                            </div>
                            <div class="modal-body" id="takenMedicationDiv">
                                <div class="row col-4">
                                    <input type="number" placeholder="aantal" name="takenMedication" id="takenMedication" />
                                </div>
                            </div>
                        </div>

                        <!-- date options start here, leisureActivity and medicationActivity -->
                        <!-- TODO -->

                        <!-- memberOptions start here -->
                        <div class="tab-pane fade" id="memberOptions" role="tabpanel" aria-labelledby="list-settings-list">
                            <div class="modal-body">
                                <div class="row col-4" id ="doneByMemberDiv">
                                    <select name="event.doneByMember" id="doneByMember" style="width:13.2em;" >
                                        <option disabled selected="doneByMember">Gedaan door</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>

        </div>
    </div>
    <!-- memberOption side tab -->
    <div class="tabs" role="tablist">
        <a class="list-group-item  list-group-item-action nav-link" style="background-color:#98639C;" id="Memberlist" data-toggle="tab" href=#memberOptions role="tab" aria-controls="settings"><i class="fas fa-user float-center"></i></a>
    </div>
</div>
<jsp:include page="newHomeWithoutCalendarBreakingStuff.jsp" />


<script>


$(function () {
    $('#datetimepickerStart').datetimepicker({icons: {time: 'far fa-clock',},});
    $('#datetimepickerEnd').datetimepicker({icons: {time: 'far fa-clock',},});
    $('#datetimepickerDone').datetimepicker({icons: {time: 'far fa-clock',},});
});
</script>
</body>
</html>