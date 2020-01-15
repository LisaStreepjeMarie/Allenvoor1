$(document).ready(function() {

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

        <!-- This function is executed when an already planned event is clicked -->
        eventClick: function(event, element) {
            <!--pass eventId to a simple <a href> link: -->
            <!--$('#deleteEvent').attr('href',"/event/delete/" + event.id);-->

            <!--pass eventId to a <button> onclick action: -->
            $('#delete-event').attr('onclick',"window.location='${pageContext.request.contextPath}/event/delete/" + event.id + "/" + event.activity.id + "'");

            $("#eventId").val(event.id);

            $('#modal-form').attr('action',"/event/change/" + event.activity.id + "/" + event.team.id);
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

        <!-- This function is executed on drop when an event was dragged. (not yet implemented) -->
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

        <!-- This function is executed when an event was resized -->
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

    <!-- This function loads the start & end date calendars (datetimepickers) in the modal (popup). -->
    $("#eventStartDate, #eventEndDate").datetimepicker({
         format: 'MM/DD/YYYY HH:mm',
    });
});