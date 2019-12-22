<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Allen voor 1</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- webjars worden nog niet geladen -->
    <script src="webjars/fullcalendar/4.3.1/core/main.min.js"></script>
    <script src="webjars/fullcalendar/4.3.1/daygrid/main.min.js"></script>
    <script src="webjars/fullcalendar/4.3.1/moment/main.min.js"></script>
    <script src="webjars/fullcalendar/4.3.1/list/main.min.js"></script>
    <script src="webjars/fullcalendar/4.3.1/timegrid/main.min.js"></script>

    <!-- daarom voor nu maar met cdns -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/bootstrap/main.min.css" integrity="sha256-auNBxJ+1OpvUJfYRsPihqLzJFUM9D3gpb8nOh5v0LiM=" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/core/main.css" integrity="sha256-nJK+Jim06EmZazdCbGddx5ixnqfXA13Wlw3JizKK1GU=" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/daygrid/main.min.css" integrity="sha256-AVsv7CEpB2Y1F7ZjQf0WI8SaEDCycSk4vnDRt0L2MNQ=" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/list/main.min.css" integrity="sha256-saO3mkZVAcyqsfgsGMrmE7Cs/TLN1RgVykZ5dnnJKug=" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/timegrid/main.min.css" integrity="sha256-DOWdbe6a1VwJwFpkimY6z5tW9mmrBNre2jZsAige5PE=" crossorigin="anonymous" />
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/@fullcalendar/interaction@4.2.0/main.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/bootstrap/main.js" integrity="sha256-YLvGa/6UrzsYa6pgPIwxuiXtsS854c/pImjL3kfK+sY=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/core/main.js" integrity="sha256-F4ovzqUMsKm41TQVQO+dWHQA+sshyOUdmnDcTPMIHkM=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/daygrid/main.min.js" integrity="sha256-FT1eN+60LmWX0J8P25UuTjEEE0ZYvpC07nnU6oFKFuI=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/list/main.min.js" integrity="sha256-q57s73NpMCTQ4ZXqb1X5bIywrICySeB6WvYxFGfz/PA=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/timegrid/main.min.js" integrity="sha256-L9T+qE3Ms6Rsuxl+KwLST6a3R/2o6m33zB5mR2KyPjU=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/4.2.0/moment/main.min.js" integrity="sha256-iCYfw93enxd8O5zz/jL4UamQ8bgrUfidO4C5500RSd4=" crossorigin="anonymous"></script>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script>
      document.addEventListener('DOMContentLoaded', function() {

        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {

        plugins: [ 'bootstrap', 'interaction', 'dayGrid' ],

        themeSystem: 'bootstrap4',
        events: [
                <c:forEach items="${activityList}" var="activity">
                			{
                				title: '${activity.activityName}',
                				start: '2019-11-01'
                			},
                </c:forEach>
			{
				title: 'Click for Google',
				url: 'http://google.com/',
				start: '2019-11-28'
			}
		],
        header: { center: 'dayGridWeek,dayGridMonth,dayGridDay,listWeek' },
            plugins: [ 'dayGridPlugin', 'dayGrid', 'list' ],
            contentHeight: 600,
        });
        calendar.render();

      });
    </script>
</head>
<body>
<div class="container w-80 p-3">
    <div id="calendar"></div>
</div>
</body>

</html>