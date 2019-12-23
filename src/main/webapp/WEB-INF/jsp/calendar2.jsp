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
                            <label class="col-xs-4" for="title">Onderwerp</label>
                            <input type="text" name="title" id="title" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="starts-at">Starttijd</label>
                            <input type="text" name="starts_at" id="starts-at" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label class="col-xs-4" for="ends-at">Eindtijd</label>
                            <input type="text" name="ends_at" id="ends-at" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Sluiten</button>
                    <button type="button" class="btn btn-primary" id="save-event">Maak afspraak</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

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
                $('.modal').find('#title').val("");
                $('.modal').find('#starts-at').val("");
                $('.modal').find('#ends-at').val("");
            },
            eventClick: function(event, element) {
                $('.modal').modal('show');
                $('.modal').find('#title').val(event.title);
                $('.modal').find('#starts-at').val(event.start);
                $('.modal').find('#ends-at').val(event.end);

            },
            editable: true,
            events: [ ${calendarData} ],
            eventLimit: true // allow "more" link when too many events
        });

        // Bind the dates to datetimepicker.
        // You should pass the options you need
        $("#starts-at, #ends-at").datetimepicker({
             format: 'DD/MM/YYYY HH:mm',
        });

        // Whenever the user clicks on the "save" button om the dialog
        $('#save-event').on('click', function() {
            var title = $('#title').val();
            if (title) {
                var eventData = {
                    title: title,
                    start: $('#starts-at').val(),
                    end: $('#ends-at').val()
                };
                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
            }
            $('#calendar').fullCalendar('unselect');

            // Clear modal inputs
            $('.modal').find('input').val('');

            // hide modal
            $('.modal').modal('hide');
        });
    });
    </script>
</html>