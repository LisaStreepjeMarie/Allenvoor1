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

    <script type="text/javascript">
    $(document).ready(function() {
        hideAll();

        <!-- this shows/hides the eventDone input field when the checkbox is toggled -->
        $("#eventDone").change(function () {
            if(document.getElementById("eventDone").checked == true) {
                    $("#eventDoneDateDiv").show()
                    $('#eventDoneDate').attr('required', "true")
                    $('#eventDoneDate').attr('name',"eventDoneDate")
            } else {
                    document.getElementById("eventDoneDate").removeAttribute("required");
                    $("#eventDoneDateDiv").hide()

                    <!-- set name of eventDoneDate to noEventDoneDate -->
                    <!-- so the controller doesn't pick up the value (and will not write an empty value into java.util.Date) -->
                    $('#eventDoneDate').attr('name',"noEventDoneDate");
            }
        });

        <!-- below makes sure that the unwanted fields in the modal are hidden and calls the selection upon change -->
        $("#selectie").change(function () {
            hideAll();
            activitySelection();
        });

        <!-- below cleans the modal upon closing -->
        $('#modal-form').on("hide.bs.modal", function() {
            $('#modal-form').trigger("reset");
            hideAll();
        });

        $('#calendar').fullCalendar({
            themeSystem: 'bootstrap4',
            timeZone: 'Europe/Amsterdam',
            timeFormat: 'H(:mm)',
            locale: 'nl',

            <!-- The buttons and title that are displayed at the top of the calendar -->
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,list'
            },

            weekNumbers: true,
            eventLimit: true, // allow "more" link when too many events
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectHelper: true,

            <!-- This function is executed when an empty date/time is clicked -->
            select: function(start, end) {
                $('#modal-form').attr('action',"${pageContext.request.contextPath}/event/new");
                $('#save-change-event').attr('action',"${pageContext.request.contextPath}/event/new");
                $('.modal').find('#eventStartDate').val(start);
                $('.modal').find('#eventEndDate').val(end);

                document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
                document.getElementById("save-change-event").innerHTML = "Maak afspraak";
                $("#delete-event").hide();
                $('.modal').modal('show');
            },

            <!-- This function is executed when an already planned event is clicked -->
            eventClick: function(event, element) {
                <!-- pass eventId to a simple <a href> link: -->
                <!-- $('#deleteEvent').attr('href',"${pageContext.request.contextPath}/event/delete/" + event.id); -->

                <!-- redefines the onclick action of the delete-event button, so that it will point the browser -->
                <!-- to the /event/delete/{eventId}/{activityId} mapping -->
                $('#delete-event').attr('onclick',"window.location='${pageContext.request.contextPath}/event/delete/" + event.id + "/" + event.activity.id + "'");

                $("#eventId").val(event.id);
                $('#modal-form').attr('action',"${pageContext.request.contextPath}/event/change/" + event.activity.id);
                $('#save-change-event').attr('action',"{pageContext.request.contextPath}/event/change");
                $('.modal').find('#eventComment').val(event.description);
                $('.modal').find('#selectie').val(event.activity.category);
                $('.modal').find('#eventName').val(event.title);

                <!-- below shows the modal based on the event.activity.category -->
                fillingTheModal();
                showMedicationAmount(event, element);

                $('.modal').find('#eventStartDate').val(event.start);
                $('.modal').find('#eventEndDate').val(event.end);

                <!-- redefines the modal (popup) buttons with the appropriate button text -->
                document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
                document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";

                <!-- shows the delete button on the (still hidden) modal -->
                $("#delete-event").show();

                <!-- lastly, the modal (popup) is shown, which by now has been properly configured -->
                $('.modal').modal('show');
            },

            eventDragStop: function(info) {
            },

            <!-- This function is executed when an event was resized, not yet implemented -->
            eventResize: function(event, delta, revertFunc) {
                alert(event.title + " end is now " + event.end.format());

                revertFunc();
            },

            <!-- Remember last view on page reload. -->
            viewRender: function (view, element) {
                localStorage.setItem("fcDefaultView", view.name);
            },
            defaultView: (localStorage.getItem("fcDefaultView") !== null ? localStorage.getItem("fcDefaultView") : "month"),

            editable: true,

            <!-- calendarData is a JSON string with all calendar events -->
            events: ${calendarData},
            eventLimit: true // allow "more" link when too many events
        });

        <!-- These functions load the start, end & done date calendars (datetimepickers) in the modal (popup). -->
        $('#datetimepickerStart').datetimepicker({
            useCurrent: false,
            format: 'MM/DD/YYYY HH:mm'
        });
        $('#datetimepickerEnd').datetimepicker({
            useCurrent: false,
            format: 'MM/DD/YYYY HH:mm'
        });
        $('#datetimepickerDone').datetimepicker({
            useCurrent: false,
            format: 'MM/DD/YYYY HH:mm'
        });
        $("#datetimepickerStart").on("change.datetimepicker", function (e) {
            $('#datetimepickerEnd').datetimepicker('minDate', e.date);
        });
        $("#datetimepickerEnd").on("change.datetimepicker", function (e) {
            $('#datetimepickerStart').datetimepicker('maxDate', e.date);
        });
        $('#datetimepickerDone').datetimepicker();
    });

<!-- below function shows the correct modal form based on the activity selection -->
    function activitySelection() {
        if ($("#selectie").val() === "Medisch") {
            $("#ShowDates, #ShowEventName, #medicationActivity").show();
        } else {
            $("#ShowDates, #ShowEventName, #eventActivity").show();
        }
        $("#eventDoneDiv").css("display", "");
    }

<!-- below function hides all modal options -->
    function hideAll() {
        $("#ShowDates, #eventActivity, #medicationActivity, #ShowEventName, #eventDoneDateDiv, #eventDoneDiv").css("display", "none");

    }

    function showMedicationAmount(event, element){
        if ($('.modal').find('#selectie').val() == "Medisch")
          $('.modal').find('#takenMedication').val(event.activity.takenmedication);
    }

<!-- below function fills the modal with event info if it exist -->
    function fillingTheModal() {
        if ($('.modal').find('#selectie').val() == "Medisch")
            $("#ShowDates, #ShowEventName, #medicationActivity, #eventDoneDiv").show();
        else
            $("#ShowDates, #ShowEventName, #eventActivity, #eventDoneDiv").show();
    }
     </script>
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
                    <div class="modal-body">
                        <div class="row">
                            <label class="col-4" for="selectie" control-label>Categorie</label>
                            <select name="activity.activityCategory" id="selectie" >
                            <option disabled selected="selected">Selecteer categorie</option>
                                <option value="Vrije tijd" >Vrije tijd</option>
                                <option value="Medisch">Medisch</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-body" id="ShowEventName">
                        <div class="row">
                            <label class="col-4" for="eventName">Onderwerp</label>
                            <input type="text" name="eventName" id="eventName" />
                            <input type="hidden" name="eventId" id="eventId" />
                            <input type="hidden" name="teamId" id="team.teamId" />
                        </div>
                    </div>

                    <!-- event with activity modal input fields -->
                    <div class="modal-body" id="eventActivity">
                        <div class="row">
                            <label class="col-4" for="eventComment">Beschrijving</label>
                            <input type="text" name="eventComment" id="eventComment" />
                        </div>
                    </div>

                    <!-- event with MedicationActivity modal input fields -->
                    <div class="modal-body" id="medicationActivity">
                        <div class="row">
                           <label class="col-4" for="selectie2" control-label>Medicijn</label>
                           <select name="medication.medicationId" id="selectie2" >
                               <option disabled selected="selected">Kies een medicijn</option>
                               <c:forEach var="medication" items="${medicationList}">
                                   <option value="${medication.medicationId}">${medication.medicationName}</option>
                               </c:forEach>
                           </select>
                        </div>
                    </div>
                    <div class="modal-body" id="medicationActivity">
                        <div class="row">
                            <label class="col-4" for="takenMedication" control-label>Hoeveelheid</label>
                            <input type="number" name="takenMedication" id="takenMedication" />
                        </div>
                    </div>
                </div>

                <div class="modal-body" id="ShowDates">
                    <div class="form-group">
                        <div class="input-group date" id="datetimepickerStart" data-target-input="nearest">
                            <label class="col-4" for="eventStartDate">Starttijd </label>
                            <input id="eventStartDate" name="eventStartDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerStart"/>
                            <div class="input-group-append" data-target="#datetimepickerStart" data-toggle="datetimepicker">
                                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group date" id="datetimepickerEnd" data-target-input="nearest">
                            <label class="col-4" for="eventEndDate">Eindtijd </label>
                            <input id="eventEndDate" name="eventEndDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerEnd"/>
                            <div class="input-group-append" data-target="#datetimepickerEnd" data-toggle="datetimepicker">
                                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-body" >
                        <div class="modal-body" >
                            <div class="row">
                                <label class="col-xs-4" for="eventDone">Afspraak al uitgevoerd?&nbsp;</label>
                                <input type="checkbox" id="eventDone" name="eventDone"/>
                            </div>
                            <div class="row" id="eventDoneDateDiv">
                                <!-- >div class="col-xs-12"> -->
                                        <!-- <span style="margin-left:2em"> -->
                                            <div class="form-group">
                                            <div class="input-group date" id="datetimepickerDone" data-target-input="nearest">
                                                <label class="col-xs-4" for="eventDoneDate">Op datum</label>
                                                <input id="eventDoneDate" name="noEventDoneDate" type="text" class="form-control datetimepicker-input" data-target="#datetimepickerDone"/>
                                                <div class="input-group-append" data-target="#datetimepickerDone" data-toggle="datetimepicker">
                                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- </span> -->
                                <!-- </div> -->
                            </div>
                        </div>
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