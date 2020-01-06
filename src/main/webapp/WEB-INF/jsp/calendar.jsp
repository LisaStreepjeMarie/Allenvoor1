<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">

<head>
    <meta charset='utf-8' />
    <title>Kalender</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <!-- webjars are preferable to content delivery networks (CDNs)-->
    <link href='https://unpkg.com/fullcalendar@3.10.1/dist/fullcalendar.min.css' rel='stylesheet' />
    <link href='https://unpkg.com/fullcalendar@3.10.1/dist/fullcalendar.print.css' rel='stylesheet' media='print' />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.42/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
    <script src='https://unpkg.com/moment@2.24.0/min/moment.min.js'></script>
    <script src='https://unpkg.com/jquery@3.4.1/dist/jquery.min.js'></script>
    <script src='https://unpkg.com/fullcalendar@3.10.1/dist/fullcalendar.min.js'></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.42/js/bootstrap-datetimepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.9.0/locale/nl.js" integrity="sha256-LAjE4KdsUdfZ4yMkV+UMRbHqEyfyvtCeIyD6qRYhTtQ=" crossorigin="anonymous"></script>
    <link href='https://use.fontawesome.com/releases/v5.0.6/css/all.css' rel='stylesheet' />
    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' rel='stylesheet' />
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <!--<link href="webjars/fullcalendar/3.9.0/fullcalendar.min.css" rel="stylesheet" />
    <link href="webjars/fullcalendar/3.9.0/fullcalendar.print.min.css" rel="stylesheet" media='print' />
    <link href="webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
    <script src="webjars/moment/2.24.0/min/moment.min.js"></script>
    <script src="webjars/jquery/3.4.1/jquery.slim.min.js"></script>
    <script src="webjars/fullcalendar/3.9.0/fullcalendar.min.js"></script>
    <script src="webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <script src="webjars/fullcalendar/3.9.0/locale/nl.js"></script>
    <link href="webjars/font-awesome/5.0.6/web-fonts-with-css/css/fontawesome-all.min.css" rel='stylesheet'>
    <link href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>-->

    <!-- added the below 2 links and the javascript to make sure the end date is the same/above the start date -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.0/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.0/js/bootstrap-datepicker.min.js"></script>

    <script type="text/javascript">
    $(document).ready(function() {

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

                $('.modal').find('#eventName').val("");
                $('.modal').find('#eventComment').val("");
                $('.modal').find('#activity.activityCategory').val("Selecteer categorie");
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
                $('#delete-event').attr('onclick',"window.location='/event/delete/" + event.id + "'");

                $("#eventId").val(event.id);

                $('#modal-form').attr('action',"/event/change");
                $('#save-change-event').attr('action',"/event/change");

                $('.modal').find('#eventName').val(event.title);
                $('.modal').find('#eventComment').val(event.description);
                $('.modal').find('#event.activityCategory').val("Selecteer categorie");
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

                if (!confirm("Weet je zeker dat je de afspraak wilt verplaatsen?")) {
                    revertFunc();
                }
            },

            eventDragStop: function(info) {
            },

            editable: true,
            events: [ ${calendarData} ],
            eventLimit: true // allow "more" link when too many events
        });

        $("#eventStartDate").datepicker({
            todayBtn: 1,
            autoclose: true,
        }).on('changeDate', function (selected) {
           var minDate = new Date(selected.date.valueOf());
        $('#eventEndDate').datepicker('setStartDate', minDate);
        });
        $("#eventEndDate").datepicker()
           .on('changeDate', function (selected) {
            var minDate = new Date(selected.date.valueOf());
        $('#eventStartDate').datepicker('setEndDate', minDate);
        });

        // Bind the dates to datetimepicker.
        // You should pass the options you need
        $("#eventStartDate, #eventEndDate").datetimepicker({
             format: 'MM/DD/YYYY HH:mm',
        });
    });
    </script>
</head>

<body>
<form:form id="modal-form" action="/event/change" modelAttribute="event" method="post">
    <div class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 id="modal-title" class="modal-title">Maak nieuwe afspraak</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="eventName">Onderwerp</label>
                            <input type="text" name="eventName" path="eventName" id="eventName" required/>
                            <input type="hidden" name="eventId" id="eventId" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="eventComment">Beschrijving</label>
                            <input type="text" name="eventComment" id="eventComment" required/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="activity.activityCategory">Categorie</label>
                            <select name="activity.activityCategory" id="activity.activityCategory" required>
                                <option disabled selected="selected">Selecteer categorie</option>
                                <option value="Huishouden">Huishouden</option>
                                <option value="Medisch">Medisch</option>
                                <option value="Vrije tijd" >Vrije tijd</option>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="eventStartDate">Starttijd</label>
                            <input type="text" name="eventStartDate" id="eventStartDate" required/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="eventEndDate">Eindtijd</label>
                            <input type="text" name="eventEndDate" id="eventEndDate" required/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" id="delete-event" class="btn btn-danger" data-dismiss="modal" >Verwijder Afspraak</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Sluiten</button>
                    <form:errors path="eventName" cssClass="error" />
                    <button type="submit" class="btn btn-primary" id="save-change-event">Maak afspraak</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form:form>

<div class="container w-80 p-3">
    <div id="calendar"></div>
    <div id='datepicker'></div>
</div>
<div class="row">
    <div class="col-xs-1">
    </div>
    <div class="col-xs-2">
        <input type="submit" value="Maak nieuwe afspraak" onclick="window.location='/event/new';" />
    </div>
</div>
</body>
</html>