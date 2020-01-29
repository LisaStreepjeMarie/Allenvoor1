// Define contextpath
var ctx = $('#contextPathHolder').attr('data-contextPath');

// Get csrf token (needed to post a json through spring boot security)
$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

// functions in this method will only be available after the document (jsp) is completely loaded
$(document).ready(function() {

        // Cleans the modal upon closing
        $('.modal').on("hide.bs.modal", function() {
            $('#formID').trigger("reset");
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

            weekNumbers: true,
            eventLimit: true, // allow "more" link when too many events
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectHelper: true,

            // This function is executed when an empty date/time is clicked
            select: function(start, end) {
                $("#modal-footer").hide();
                /*$("#datetimepickerDone").hide();*/
                $("#activityCategory").change(function () {
                    showModalInputFields();
                });
                $('.modal').modal('show');

                document.getElementById('save-change-event').setAttribute( "onClick", "saveEvent('"+ null + "','" + null + "')" );
                toggleModalNewOrExistingEvent(start, end, "new");
            },

            // This function is executed when an already planned event is clicked
            eventClick: function(event, element) {
                $("#modal-footer").hide();
                $("#activityCategory").change(function () {
                    showModalInputFields();
                });
                $('.modal').modal('show');

                document.getElementById('delete-event').setAttribute( "onClick", "deleteEvent('"+ event.id +"','" +  ctx + "/event/delete/" + "')" );
                document.getElementById('save-change-event').setAttribute( "onClick", "saveEvent('"+ event.id + "','" + event.activity.id + "')" );

                toggleModalNewOrExistingEvent(event.start, event.end, "existing");
            },

            // This function is executed when an event is dragged to another date
            eventDrop: function(event, info) {
                targetUrl = ctx + "/calendar/change/eventdate";
                changeEvent(event, targetUrl);
            },

            // This function is executed when an event is resized
            eventResize: function(event, delta) {
                targetUrl = ctx + "/calendar/change/eventdate";
                changeEvent(event, targetUrl);
            },

            // Distinct event colors based on activity.category
            eventRender: function(event, element) {
                if (event.activity != null) {
                    if ( event.activity.type === 'MedicationActivity') {
                        element.css('background-color', '#98639C');
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
                getCalendarData(start, end, callback);
             },

            // If a date has many dates, show an 'extra' button to
            eventLimit: true
        });
});