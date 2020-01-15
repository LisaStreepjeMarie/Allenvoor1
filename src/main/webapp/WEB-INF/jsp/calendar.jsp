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

    <link href="/webjars/fullcalendar/3.9.0/fullcalendar.min.css" rel="stylesheet" />
    <link href="/webjars/fullcalendar/3.9.0/fullcalendar.print.min.css" rel="stylesheet" media='print' />
    <link href="/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
    <script src="/webjars/moment/2.24.0/min/moment.min.js"></script>
    <script src="/webjars/jquery/3.4.1/jquery.slim.min.js"></script>
    <script src="/webjars/fullcalendar/3.9.0/fullcalendar.min.js"></script>
    <script src="/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/webjars/fullcalendar/3.9.0/locale/nl.js"></script>
    <link href="/webjars/font-awesome/5.0.6/web-fonts-with-css/css/fontawesome-all.min.css" rel='stylesheet'>
    <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <script type="text/javascript">
    $(document).ready(function() {

        hideAll();

        <!-- below makes sure that the unwanted fields in the modal are hidden and calls the selection upon change -->
        $("#selectie").change(function () {
            hideAll();
            activitySelection();
        });

        <!-- below cleans the modal upon closing -->
        $('#modal-form').on("hide.bs.modal", function() {
            console.log("closingclosing");
            $("#selectie").val("Selecteer categorie");
            hideAll();
        });

        $('#calendar').fullCalendar({
            themeSystem: 'bootstrap4',
            timeZone: 'Europe/Amsterdam',
            timeFormat: 'H(:mm)',
            locale: 'nl',
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
            select: function(start, end) {
                $('#modal-form').attr('action',"/event/new");
                $('#save-change-event').attr('action',"/event/new");
                $('.modal').find('#eventStartDate').val(start);
                $('.modal').find('#eventEndDate').val(end);
                document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
                document.getElementById("save-change-event").innerHTML = "Maak afspraak";
                $("#delete-event").hide();
                $('.modal').modal('show');
            },
            eventClick: function(event, element) {
                <!--pass eventId to a simple <a href> link: -->
                <!--$('#deleteEvent').attr('href',"/event/delete/" + event.id);-->
                <!--pass eventId to a <button> onclick action: -->
                $('#delete-event').attr('onclick',"window.location='/event/delete/" + event.id + "/" + event.activity.id + "'");
                $("#eventId").val(event.id);
                $('#modal-form').attr('action',"/event/change/" + event.activity.id);
                $('#save-change-event').attr('action',"/event/change");
                $('.modal').find('#eventName').val(event.title);
                $('.modal').find('#eventComment').val(event.description);
                $('.modal').find('#selectie').val(event.activity.category);

                <!-- below shows the modal based on the event.activity.category -->
                fillingTheModal();
                $('.modal').find('#eventStartDate').val(event.start);
                $('.modal').find('#eventEndDate').val(event.end);
                $('.modal').find('#eventStartDate').val(event.start);
                $('.modal').find('#eventEndDate').val(event.end);
                document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
                document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";
                $("#delete-event").show();
                $('.modal').modal('show');
            },
            eventDrop: function( event, delta, revertFunc, jsEvent, ui, view ) {
                console.log(event.title + ' was dragged to ' + event.description);
                console.log("delta is: " + delta);
                console.log("event is: " + event);
                console.log("revert is: " + revertFunc);
                console.log("jsEvent is: " + jsEvent);
                $.ajax({
                  type: "POST",
                  url: "/event/change/2",
                  data: {
                        id: "2",
                        title: "Nieuwe ajax titel",
                        description: "Nieuwe ajax beschrijving",
                        start: "1578355200000",
                        end: "1578441600000"
                  },
                  dataType: "json",
                  data: JSON.stringify({
                     id: "2",
                     title: "Nieuwe ajax titel",
                     description: "Nieuwe ajax beschrijving",
                     start: "1578355200000",
                     end: "1578441600000"
                  }),
                  success: function(response) {
                    console.log("Ajax posted succesful!: ");
                    console.log(response);
                  },
                  error: function(response) {
                    console.log("FAIL: ");
                    console.log(response);
                  }
                });
            },
            eventDragStop: function(info) {
            },
            eventResize: function(event, delta, revertFunc) {
                alert(event.title + " end is now " + event.end.format());
                revertFunc();
            },
            // Remember last view on page reload
            viewRender: function (view, element) {
                localStorage.setItem("fcDefaultView", view.name);
            },
            defaultView: (localStorage.getItem("fcDefaultView") !== null ? localStorage.getItem("fcDefaultView") : "month"),
            editable: true,
            events: ${calendarData},
            eventLimit: true // allow "more" link when too many events
        });
        // Bind the dates to datetimepicker.
        // You should pass the options you need
        $("#eventStartDate, #eventEndDate").datetimepicker({
             format: 'MM/DD/YYYY HH:mm',
        });
    });
<!-- below function shows the correct modal form based on the activity selection -->
    function activitySelection() {
        if ($("#selectie").val() === "Medisch")
            $("#medicationActivity").show();
        else
            $("#eventActivity").show();
    }
<!-- below function hides all modal options -->
    function hideAll() {
        $("#eventActivity, #medicationActivity ").css("display", "none");

    }

<!-- below function fills the modal with event info if it exist -->
    function fillingTheModal() {
        if ($('.modal').find('#selectie').val() == "Medisch")
            console.log("hallo");
        else
            $("#eventActivity").show();
            console.log("heeeey lisa");
    }
     </script>
</head>

<body>
<form:form id="modal-form" action="/event/change" modelAttribute="event" method="post">
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

                 <!-- event with activity modal input fields -->
                   <div class="modal-body" id="eventActivity">
                    <div class="row">
                        <div class="col-12">
                            <label class="col-4" for="eventName">Onderwerp</label>
                            <input type="text" name="eventName" id="eventName" />
                            <input type="hidden" name="eventId" id="eventId" />
                            <input type="hidden" name="teamId" id="team.teamId" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <label class="col-4" for="eventComment">Beschrijving</label>
                            <input type="text" name="eventComment" id="eventComment" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                        <span style="margin-left:2em">
                            <label class="col-xs-4" for="eventStartDate">Datum</label>
                            <input type="text" name="eventStartDate" id="eventStartDate" />
                        </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <span style="margin-left:2em">
                            <label class="col-xs-4" for="eventEndDate">EindDatum</label>
                            <input type="text" name="eventEndDate" id="eventEndDate" />
                            </span>
                        </div>
                    </div>
                </div>

                 <!-- event with MedicationActivity modal input fields -->
                   <div class="modal-body" id="medicationActivity">
                    <div class="row">
                        <div class="col-12">
                            <label class="col-4" for="eventName">Onderwerp</label>
                            <input type="text" name="eventName" id="eventName" />
                            <input type="hidden" name="eventId" id="eventId" />
                            <input type="hidden" name="teamId" id="team.teamId" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12" modelAttribute="medicationActivity">
                           <span style="margin-left:2em">
                           <label class="col-xs-4" for="medication" control-label>Medicijn</label>
                            <select name="medication.medicationId" id="medication.medicationId" >
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
                    <div class="row">
                        <div class="col-xs-12">
                        <span style="margin-left:2em">
                            <label class="col-xs-4" for="eventStartDate">Datum</label>
                            <input type="text" name="eventStartDate" id="eventStartDate" />
                            </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <span style="margin-left:2em">
                            <label class="col-xs-4" for="eventEndDate">EindDatum</label>
                            <input type="text" name="eventEndDate" id="eventEndDate" />
                            </span>
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
</body>
</html>