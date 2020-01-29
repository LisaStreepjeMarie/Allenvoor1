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
        var ctx = $('#contextPathHolder').attr('data-contextPath');

        hideAllModalInputFields();

        // this shows/hides the eventDone input field when the checkbox is toggled
        $("#eventDone").change(function () {
            if(document.getElementById("eventDone").checked == true) {
                    $("#datetimepickerDone").show()
            } else {
                    document.getElementById("eventDoneDate").removeAttribute("required");
                    $("#datetimepickerDone").hide()
            }
        });
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
                $("#activityCategory").change(function () {
                    showModalInputFields();
                });
                $('.modal').modal('show');

                toggleModalNewOrExistingEvent("new");
            },

            // This function is executed when an already planned event is clicked
            eventClick: function(event, element) {
                $("#modal-footer").hide();
                $("#activityCategory").change(function () {
                    showModalInputFields();
                });
                $('.modal').modal('show');

                toggleModalNewOrExistingEvent("existing");
            },

            // This function is executed when an event is dragged to another date
            eventDrop: function(event, info) {
                // Puts needed fullcalendar event.data attributes into an object to pass to the REST api
                // (for resizing and dragging an event we only need to know: a. which event, and b. what are the new dates.
                currentEvent = {
                    type: "Event",
                    id: event.id,
                    start: event.start,
                    end: event.end,
                }

                // Make AJAX Post to change eventdate
                $.ajax({
                    url: ctx + "/calendar/change/eventdate",
                    method: "POST",
                    contentType: "application/json; charset=UTF-8",
                    data: JSON.stringify(currentEvent),
                    dataType: 'json',
                    async: true,
                    success: function(result) {
                            $('#calendar').fullCalendar('refetchEvents');
                    },
                    error : function(e) {
                        alert("Error sending new event with AJAX!")
                        console.log("ERROR: ", e);
                    }
                });
            },

            // This function is executed when an event is resized
            // TODO: this code (along with eventDrop) is a prime candidate for refactoring as they are duplicates
            eventResize: function(event, delta) {
                currentEvent = {
                    type: "Event",
                    id: event.id,
                    start: event.start,
                    end: event.end,
                }

                $.ajax({
                    url: ctx + "/calendar/change/eventdate",
                    method: "POST",
                    contentType: "application/json; charset=UTF-8",
                    data: JSON.stringify(currentEvent),
                    dataType: 'json',
                    async: true,
                    success: function(result) {
                            $('#calendar').fullCalendar('refetchEvents');
                    },
                    error: function(e) {
                        alert("Error sending new event with AJAX!")
                        console.log("ERROR: ", e);
                    }
                });
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
                $.ajax({
                    type:'GET',
                    url: ctx + '/calendar/get/' + $('#teamId').attr('data-teamId') + '/' + start + '/' + end + '/',
                    dataType: 'json',
                    error: function (xhr, type, exception) { alert("Error fetching calendar data: " + exception); },
                    success: function (response) {
                        var events = [];
                        for (i in response) {
                            events.push({
                                id: response[i].id,
                                title: response[i].title,
                                start: response[i].start,
                                end: response[i].end,
                                description: response[i].description,
                                activity: response[i].activity,
                            });
                        }
                        callback(events);
                    }
                });
             },

            // If a date has many dates, show an 'extra' button to
            eventLimit: true
        });
}); // End of $(document).ready(function)