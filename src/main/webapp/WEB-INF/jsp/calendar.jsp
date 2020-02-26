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
    border: 0;
    position: fixed;
    width: 65px;
    height: 65px;
}
@media (min-width: 768px) {
  .tabs {
    top: calc(12px + 9%);
    right: calc(249px + 50%);
    left: auto;
  }

}

.modal-header {
    background: #98639C;
    padding: 0p;
    height: 56px;
    border: 0;
}

.tabs-top {
    margin-bottom: 0px;
    margin-top: 0px;
}

.nav-tabs {
    border: 0;

}

.tabs-4 .nav-tabs > li {
    width: 200px;
    border: 0;

}

.nav-tabs > li > a {
    border: 0;
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

.modal-dialog {
border: 0;
}

.centered {
    margin: auto;
}

.center-block {
  margin: auto;
  display: block;
}

.leftWhite {
padding-left: 40px;
}

.upWhite {
padding-top: 20px;
}

.bottomWhite {
padding-bottom: 20px;
}

</style>
</head>

<body>
<!-- Modal -->
<form role="form" id="formID" >
<div class="modal fade newModal" id="newModal" style="widh: 200px;" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content " id="backgroundModal">
            <!-- tab options on top -->
            <div class="modal-header">
                <div class="tabbable" id="alleTabs">
                    <ul class="nav nav-tabs" role="tablist">
                        <li >
                            <a class="nav-link" id="LeisureTab" onclick="optionLeisureActivity()" data-toggle="tab">
                                Vrije tijd
                            </a>
                        </li>
                        <li>
                            <a class="nav-link"  id="MedicationTab" onclick="optionsMedicationActivity()" data-toggle="tab">
                                Medisch
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- model content divs -->
            <span>
                <div class="upWhite"></div>
                        <!-- event titel starts here, same for leisureActivity and medicationActivity -->
                        <div class="modal-body" id="eventNameDiv">

                            <div class="row leftWhite">
                                <div class="form-group">
                                    <input class="form-control col-15 "  type="text" placeholder="Titel"name="eventName" id="eventName" />
                                    <input type="hidden" name="eventId" id="eventId" />
                                    <input type="hidden" name="activityId" id="activityId" />
                                    <input type="hidden" name="teamId" id="team.teamId"/>
                                    <input type="hidden" name="teamName" value="${team.teamName}" id="teamName"/>
                                </div>
                            </div>

                        </div>

                        <!-- leisureActivity options start here -->
                            <div class="modal-body" id="eventCommentDiv">
                                <div class="row leftWhite">
                                    <textarea class="col-30" style="width: 400px;" placeholder="Beschrijving" name="eventComment" id="eventComment"></textarea>
                                </div>
                            </div>

                        <!-- medicationActivity options start here -->
                        <div class="row leftWhite" id="medicationChoiceDiv">
                                <div class="col">
                                    <select class="browser-default custom-select " style="width: 200px;" name="medication.medicationId" id="medicationChoice" >
                                        <option disabled selected="selected">Medicijn</option>
                                    </select>
                                </div>
                                <div class="col">
                                    <input type="number" style="width: 150px;" class="form-control" placeholder="aantal" name="takenMedication" id="takenMedication" />
                                 </div>
                        </div>
                        <!-- date options start here, leisureActivity and medicationActivity have the same options -->
                    <div class="modal-body" id="eventDatesDiv">
                        <div class="form-group ">
                            <!-- start date -->
                            <div class="input-group date" id="datetimepickerStart" data-target-input="nearest">
                                <label for="eventStartDate">&emsp;&ensp;van&emsp;</label>
                                <input id="eventStartDate" name="eventStartDate" type="text" class="form-control  datetimepicker-input" data-target="#datetimepickerStart"/>
                                <div class="input-group-append" style="width:8.3vw;" data-target="#datetimepickerStart" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>

                        </div>
                        <div class="form-group ">
                            <!-- end date -->
                            <div class="input-group date" id="datetimepickerEnd" data-target-input="nearest">
                                <label for="eventEndDate">&emsp;&ensp;tot&emsp;&thinsp;</label>
                                <input id="eventEndDate" name="eventEndDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerEnd"/>
                                <div class="input-group-append" style="width:8.3vw;"  data-target="#datetimepickerEnd" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-body" id="eventPeriodicDiv">
                            <div class="modal-body">
                                <!-- interval checkbox -->
                                <div class="row" id="eventPeriodicCheckDiv">
                                    <label class="col-xs-4" for="eventPeriodic">Periodieke afspraak?&nbsp;</label>
                                    <input type="checkbox" id="eventPeriodic" name="eventPeriodic"/>
                                </div>
                                <div class="row" id="eventIsPeriodicDiv">
                                    <label class="col-xs-4" for="eventPeriodic">Periodieke afspraak</label>
                                </div>
                            </div>
                            <!-- interval options day/month/week -->
                            <div class="row " id="intervalDiv">
                                <label class="col-4" for="eventInterval" control-label>Frequentie</label>
                                <select class=" browser-default custom-select" style="width: 200px;" name="event.eventInterval" id="eventInterval" >
                                    <option disabled selected="selected" value="">Elke</option>
                                    <option value="day">dag</option>
                                    <option value="week">week</option>
                                    <option value="month">maand</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-body" id="maxNumberDiv">
                            <!-- interval options amount -->
                            <div class="row">
                                <label class="col-4" for="eventMaxNumberLabel" id="eventMaxNumberLabel" control-label>Aantal keer</label>
                                <input type="text" size="1" name="eventMaxNumber" id="eventMaxNumber" />
                            </div>
                        </div>
                    </div>

                        <div class="form-group" >
                            <div class="input-group date" id="datetimepickerDone" data-target-input="nearest">
                                <label class="col-15" for="eventDoneDate">Op datum</label>
                                <input id="eventDoneDate" name="eventDoneDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerDone"/>
                                <div class="input-group-append" style="width:8.3vw;" data-target="#datetimepickerDone" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row" id ="doneByMemberDiv">
                                <label class="col-4" for="doneByMember" control-label>Gedaan door </label>
                                <select name="event.doneByMember" id="doneByMember" style="width:13.2em;" >
                                    <option disabled selected="doneByMember">null</option>
                                </select>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="memberOptions" role="tabpanel" aria-labelledby="list-settings-list">
                        </div>
                </span>
                <div class="modal-footer" id="modal-footer">
                    <button type="button" id="delete-event" class="btn btn-danger" data-dismiss="modal">Verwijder Afspraak</button>
                    <button type="button" class="btn btn-light" data-dismiss="modal">Sluiten</button>
                    <button type="submit" class="btn btn-primary" id="save-change-event" data-dismiss="modal">Maak afspraak</button>
                </div>
            </div><!-- /.modal-content -->
        </div>

        <!-- memberOption side tab -->
        <div class="tabs" role="tablist">
            <a class="list-group-item  list-group-item-action nav-link" style="background-color:#98639C;" id="Memberlist" data-toggle="tab" onclick="optionsMemberTab()" role="tab" aria-controls="settings"><i class="fas fa-user float-center"></i></a>
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form>
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