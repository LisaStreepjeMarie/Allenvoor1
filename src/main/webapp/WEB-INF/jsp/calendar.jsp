<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Allen voor 1</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">

        <link href="css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Windows Phone -->
        <meta name="msapplication-navbutton-color" content="#272727">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.6.1/fullcalendar.min.css">
        <link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.6.1/fullcalendar.print.css">

        <script src="https://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.12.0/moment.min.js"></script>
        <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.6.1/fullcalendar.min.js"></script>
    </head>
    <body class="webpage">
        <div class="w-90 p-3">
            <div class="container" id='calendar'></div>
        </div>
    </body>
    <script>
    $(document).ready(function() {

            $('#calendar').fullCalendar({
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay'
                },
                defaultDate: '2019-12-12',
                defaultView: 'month',
                editable: true,
                events: [
                    {
                        title: 'All Day Event',
                        start: '2019-12-17'
                    },
                    {
                        title: 'Long Event',
                        start: '2014-06-07',
                        end: '2019-12-10'
                    },
                    {
                        id: 999,
                        title: 'Repeating Event',
                        start: '2019-12-09T16:00:00'
                    },
                    {
                        id: 999,
                        title: 'Repeating Event',
                        start: '2019-12-16T16:00:00'
                    },
                    {
                        title: 'Meeting',
                        start: '2019-12-12T10:30:00',
                        end: '2019-12-12T12:30:00'
                    },
                    {
                        title: 'Lunch',
                        start: '2019-12-12T12:00:00'
                    },
                    {
                        title: 'Birthday Party',
                        start: '2019-12-13T07:00:00'
                    },
                    {
                        title: 'Click for Google',
                        url: 'http://google.com/',
                        start: '2019-12-28'
                    }
                ]
            });

        });

    </script>
</html>