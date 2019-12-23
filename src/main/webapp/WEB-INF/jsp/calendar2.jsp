<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <meta charset='utf-8' />
    <title>Kalender</title>
    <!--    Using webjars (disabled for now; calendar not loading)
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <link rel="stylesheet" href="webjars/fullcalendar/4.2.0/packages/bootstrap/main.min.css" />
    <link rel="stylesheet" href="webjars/fullcalendar/4.2.0/packages/core/main.css" />
    <link rel="stylesheet" href="webjars/fullcalendar/4.2.0/packages/daygrid/main.min.css" />
    <link rel="stylesheet" href="webjars/fullcalendar/4.2.0/packages/list/main.min.css" />
    <link rel="stylesheet" href="webjars/fullcalendar/4.2.0/packages/timegrid/main.min.css" />

    <script src="webjars/fullcalendar/4.2.0/packages/bootstrap/main.min.js"></script>
    <script src="webjars/fullcalendar/4.2.0/packages/interaction/main.min.js"></script>
    <script src="webjars/fullcalendar/4.2.0/packages/core/main.min.js"></script>
    <script src="webjars/fullcalendar/4.2.0/packages/daygrid/main.min.js"></script>
    <script src="webjars/fullcalendar/4.2.0/packages/list/main.min.js"></script>
    <script src="webjars/fullcalendar/4.2.0/packages/timegrid/main.min.js"></script>
    <script src="webjars/fullcalendar/4.2.0/packages/moment/main.min.js"></script>-->


    <link href='https://unpkg.com/fullcalendar@3.10.1/dist/fullcalendar.min.css' rel='stylesheet' />
    <link href='https://unpkg.com/fullcalendar@3.10.1/dist/fullcalendar.print.css' rel='stylesheet' media='print' />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.42/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />

    <script src='https://unpkg.com/moment@2.24.0/min/moment.min.js'></script>
    <script src='https://unpkg.com/jquery@3.4.1/dist/jquery.min.js'></script>
    <script src='https://unpkg.com/fullcalendar@3.10.1/dist/fullcalendar.min.js'></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.42/js/bootstrap-datetimepicker.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.9.0/locale/nl.js" integrity="sha256-LAjE4KdsUdfZ4yMkV+UMRbHqEyfyvtCeIyD6qRYhTtQ=" crossorigin="anonymous"></script>

    <link href='https://use.fontawesome.com/releases/v5.0.6/css/all.css' rel='stylesheet'>
    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' rel='stylesheet' />
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</head>
<body>
<form:form action="/calendar/new" modelAttribute="event" method="post">
    <div class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Maak nieuwe afspraak</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="eventName">Onderwerp</label>
                            <input type="text" name="eventName" id="eventName" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="eventComment">Beschrijving</label>
                            <input type="text" name="eventComment" id="eventComment" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="activityCategory">Categorie</label>
                            <select name="activityCategory" id="activityCategory">
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
                            <input type="text" name="eventStartDate" id="eventStartDate" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="eventEndDate">Eindtijd</label>
                            <input type="text" name="eventEndDate" id="eventEndDate" />
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Sluiten</button>
                    <button type="submit" class="btn btn-primary" id="save-event">Maak afspraak</button>
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
                right: 'month,agendaWeek,agendaDay'
            },

            weekNumbers: true,
            eventLimit: true, // allow "more" link when too many events
            defaultDate: '2019-12-12',
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectHelper: true,

            select: function(start, end) {
                $('.modal').modal('show');
                $('.modal').find('#eventName').val("");
                $('.modal').find('#eventComment').val("");
                $('.modal').find('#activityCategory').val("Selecteer categorie");
                $('.modal').find('#eventStartDate').val("");
                $('.modal').find('#eventEndDate').val("");
            },

            eventClick: function(event, element) {
                $('.modal').modal('show');
                $('.modal').find('#eventName').val(event.title);
                $('.modal').find('#eventComment').val(event.description);
                $('.modal').find('#activityCategory').val(event.category);
                $('.modal').find('#eventStartDate').val(event.start);
                $('.modal').find('#eventEndDate').val(event.end);
            },

            editable: true,
            events: [ ${calendarData} ],
            eventLimit: true // allow "more" link when too many events
        });

        // Bind the dates to datetimepicker.
        // You should pass the options you need
        $("#eventStartDate, #eventEndDate").datetimepicker({
             format: 'MM/DD/YYYY HH:mm',
        });
    });
    </script>
</html>