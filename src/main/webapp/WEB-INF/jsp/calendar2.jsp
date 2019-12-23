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

    <link href='https://unpkg.com/@fullcalendar/core@4.3.1/main.min.css' rel='stylesheet' />
    <link href='https://unpkg.com/@fullcalendar/daygrid@4.3.0/main.min.css' rel='stylesheet' />
    <link href='https://unpkg.com/@fullcalendar/timegrid@4.3.0/main.min.css' rel='stylesheet' />
    <link href='https://unpkg.com/@fullcalendar/list@4.3.0/main.min.css' rel='stylesheet' />
    <link href='https://unpkg.com/@fullcalendar/bootstrap@4.3.0/main.min.css' rel='stylesheet' />

    <script src='https://unpkg.com/@fullcalendar/core@4.3.1/main.min.js'></script>
    <script src='https://unpkg.com/@fullcalendar/daygrid@4.3.0/main.min.js'></script>
    <script src='https://unpkg.com/@fullcalendar/timegrid@4.3.0/main.min.js'></script>
    <script src='https://unpkg.com/@fullcalendar/list@4.3.0/main.min.js'></script>
    <script src='https://unpkg.com/@fullcalendar/bootstrap@4.3.0/main.min.js'></script>
    <script src="https://unpkg.com/@fullcalendar/interaction@4.3.0/main.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/core/locales/nl.js"></script>

    <link href='https://use.fontawesome.com/releases/v5.0.6/css/all.css' rel='stylesheet'>
    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' rel='stylesheet' />
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>

</head>
<body>

<div class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Create new event</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <label class="col-xs-4" for="title">Event title</label>
                        <input type="text" name="title" id="title" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <label class="col-xs-4" for="starts-at">Starts at</label>
                        <input type="text" name="starts_at" id="starts-at" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <label class="col-xs-4" for="ends-at">Ends at</label>
                        <input type="text" name="ends_at" id="ends-at" />
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="save-event">Save changes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

    <div class="container w-80 p-3">
        <div id="calendar"></div>

    </div>
<div class="row">
    <div class="col-xs-1">
    </div>
    <div class="col-xs-2">
        <input type="submit" value="Maak nieuwe afspraak" onclick="window.location='/event/new';" />
    </div>
</div>
</body>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'dayGrid', 'timeGrid', 'list', 'bootstrap', 'interaction' ],
      schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
      navLinks: true, // can click day/week names to navigate views
      selectable: true,
      selectHelper: true,

      select: function(start, end) {
                // Display the modal.
                // You could fill in the start and end fields based on the parameters

                $('.modal').show();

      },

      eventClick: function(event, element) {
        $('.modal').show();
        $('.modal').find('#title').val(event.title);
        $('.modal').find('#starts-at').val(event.start);
        $('.modal').find('#ends-at').val(event.end);
      },

      timeZone: 'Europe/Amsterdam',
      timeFormat: 'H(:mm)',
      locale: 'nl',
      themeSystem: 'bootstrap',
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
      },
      weekNumbers: true,
      eventLimit: true, // allow "more" link when too many events
      events: ${calendarData}
    });
    calendar.render();
  });
</script>
</html>