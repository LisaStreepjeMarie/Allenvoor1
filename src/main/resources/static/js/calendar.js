// functions in this method will only be available after the document (jsp) is completely loaded
$(document).ready(function() {
        // Hide model fields by default
        hideAllModalInputFields();

        $('.modal').on("hide.bs.modal", function() {
            hideAllModalInputFields();
        });

        // Loads fullcalendar
        $('#calendar').fullCalendar({
            themeSystem: 'bootstrap4',
            timezone: 'local',
            timeFormat: 'H(:mm)',
            locale: 'nl',

            // The buttons and title that are displayed at the top of the calendar
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,list'
            },
            titleFormat: '[Agenda van: '+  document.getElementById("teamName").value + ']',
            weekNumbers: true,
            eventLimit: true, // allow "more" link when too many events
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectHelper: true,

            // This function is executed when an empty date/time is clicked
            select: function(start, end) {
            $('#newModal').modal('show');
            optionLeisureActivity();
            $('#newModal').find('#eventStartDate').val(moment(start).format('DD-MM-YYYY H:mm'));
            $('#newModal').find('#eventEndDate').val(moment(end).format('DD-MM-YYYY H:mm'));

            },

            // This function is executed when an already planned event is clicked
            eventClick: function(event, element) {
                $('#newModal').modal('show');

                if (event.activity.type === "LeisureActivity"){
                    optionLeisureActivity();
                    preFillLeisureActivityFields(event);
                    } else {
                    optionsMedicationActivity();
                    preFillMedicationActivityFields(event);
                    }

                preFillSharedFields(event);
                preFillDateFields(event);

                document.getElementById('delete-event').setAttribute( "onClick", "deleteEvent('"+ event.id +"','" +  ctx + "/event/delete/" + "')" );
                document.getElementById('save-change-event').setAttribute( "onClick", "saveEvent('"+ event.id + "','" + event.activity.id + "')" );
            },

            // This function is executed when an event is dragged to another date
            eventDrop: function(event, info) {
                dropResizeOrDeleteEvent(event, ctx + "/calendar/change/eventdate");
            },

            // This function is executed when an event is resized
            eventResize: function(event, delta) {
                dropResizeOrDeleteEvent(event, ctx + "/calendar/change/eventdate");
            },

            // Distinct event colors based on event.activity.type
            eventRender: function(event, element) {
                if (event.activity != null) {
                    switch(event.activity.type) {
                        case undefined: console.log("event.activity is undefined"); break;
                        case "MedicationActivity": element.css('background-color', '#98639C'); break;
                        case "LeisureActivity": break;
                    }
                }
            },

            // This function makes the browser remember the last view on a page reload. -->
            viewRender: function (view, element) {
                localStorage.setItem("fcDefaultView", view.name);
            },
            defaultView: (localStorage.getItem("fcDefaultView") !== null ? localStorage.getItem("fcDefaultView") : "month"),

            editable: true,

            // This function gets all calendar events within the view using an AJAX get.
            events: function(start, end, timezone, callback) {
                getEvents(start, end, callback);
             },

            // If a date has many dates, show an 'extra' button to
            eventLimit: true
        });
});