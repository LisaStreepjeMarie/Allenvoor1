<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8' />
    <title>Kalender</title>

    <!--    Using webjars (disabled for now; calendar not loading)
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

    <link href='https://use.fontawesome.com/releases/v5.0.6/css/all.css' rel='stylesheet'>
    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' rel='stylesheet' />
</head>
<body>
    <div class="container w-80 p-3">
        <div id="calendar"></div>
    </div>
</body>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'dayGrid', 'timeGrid', 'list', 'bootstrap', 'interaction' ],
      schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
      selectable: true,
      dateClick: function(info) {

      },

      timeZone: 'UTC',
      themeSystem: 'bootstrap',
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
      },
      weekNumbers: true,
      eventLimit: true, // allow "more" link when too many events
      events: 'https://fullcalendar.io/demo-events.json'
    });

    calendar.render();
  });
</script>
</html>