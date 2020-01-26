
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

        // This makes sure that the unwanted fields in the modal are hidden and calls the selection upon change
        $("#activityCategory").change(function () {
            hideAllModalInputFields();
            activitySelection();
        });

        // Cleans the modal upon closing
        $('.modal').on("hide.bs.modal", function() {
            $('#formID').trigger("reset");
            hideAllModalInputFields();
        });

        // Loads fullcalendar
        $('#calendar').fullCalendar({
            themeSystem: 'bootstrap4',
            timeZone: 'Europe/Amsterdam',
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
                $("#eventId").val("");

                $(".fc-highlight").css("background", "purple");
                $('.modal').find('#eventStartDate').val(moment(start).add(61, "minutes").format('DD-MM-YYYY H:mm Z'));
                $('.modal').find('#eventEndDate').val(moment(end).subtract(1, "minutes").format('DD-MM-YYYY H:mm Z'));

                document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
                document.getElementById("save-change-event").innerHTML = "Maak afspraak";
                $("#delete-event").hide();
                $('.modal').modal('show');
            },

            // This function is executed when an already planned event is clicked
            eventClick: function(event, element) {
                // redefines the onclick action of the delete-event button, so that it will point the browser -->
                // to the /event/delete/{eventId}/{activityId} mapping
                $('#delete-event').attr('onclick',"window.location='" + ctx + "/event/delete/" + event.id + "/" + event.activity.id + "'");

                $("#eventId").val(event.id);

                $('.modal').find('#eventComment').val(event.activity.comment);
                $('.modal').find('#activityCategory').val(event.activity.type); //TODO: doesn't show correct value because activityCategory is not an attribute anymore, it's the actual subclass itself
                $('.modal').find('#eventName').val(event.title);

                // Shows modal fields based on the event.activity.category
                fillingTheModal();
                showMedicationAmount(event, element);

                $('.modal').find('#eventStartDate').val(moment(event.start).add(61, "minutes").format('DD-MM-YYYY H:mm Z'));
                $('.modal').find('#eventEndDate').val(moment(event.end).subtract(1, "minutes").format('DD-MM-YYYY H:mm Z'));

                // Redefines the modal (popup) buttons with the appropriate button text
                document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
                document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";

                // Shows the delete button on the (still hidden) modal
                $("#delete-event").show();

                // lastly, the modal (popup) is shown, which by now has been properly configured
                $('.modal').modal('show');
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
                if (typeof event.activity !== 'undefined') {
                    if( event.activity.category == "Medisch") {
                        element.css('background-color', 'green');
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
        // End of fullcalendar configuration

        // These functions load the start, end & done date calendars (datetimepickers) in the modal (popup).
        $('#datetimepickerStart').datetimepicker({
            format: 'DD-MM-YYYY HH:mm'
        });
        $('#datetimepickerEnd').datetimepicker({
            format: 'DD-MM-YYYY HH:mm'
        });
        $('#datetimepickerDone').datetimepicker({
            format: 'DD-MM-YYYY HH:mm'
        });
        $("#datetimepickerStart").on("change.datetimepicker", function (e) {
            $('#datetimepickerEnd').datetimepicker('minDate', e.date);
        });
        $("#datetimepickerEnd").on("change.datetimepicker", function (e) {
            $('#datetimepickerStart').datetimepicker('maxDate', e.date);
        });
        $('#datetimepickerDone').datetimepicker();
    }); // End of $(document).ready(function)

// This function shows the correct modal form based on the activity selection
function activitySelection() {
    if ($("#activityCategory").val() === "Medisch") {
    <!-- below function makes sure the medication is only loaded when the medical activity is chosen -->
        getMedication()
        $('#eventComment').val("")
        $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
    } else {
        $('#takenMedication').val("")
        $('#medicationChoice').empty()
        $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv").show();
    }
    $("#datetimepickerDone").hide();
    $("#eventDoneDiv").css("display", "");
}

// This function hides all modal options
function hideAllModalInputFields() {
    $("#eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #eventDatesDiv, #takenMedicationDiv").css("display", "none");
}

//this shows the medication amount when an excisting event is chosen
function showMedicationAmount(event, element){
    if ($('.modal').find('#activityCategory').val() == "Medisch")
      $('.modal').find('#takenMedication').val(event.activity.takenmedication);
}

// This function fills the modal with event info if it exist
function fillingTheModal() {
    if ($('.modal').find('#activityCategory').val() == "Medisch") {
        $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
    } else {
        $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv").show();
    }
    $("#datetimepickerDone").hide();
    $("#eventDoneDiv").css("display", "");
}

function saveEvent() {
    // If eventId is null, make a new event. Otherwise change it
    console.log(document.getElementById("eventId").value);
    if ( document.getElementById("eventId").value === "" ) {
        url2 = ctx + "/calendar/new/event"
    } else {
        url2 = ctx + "/calendar/change/event/" + document.getElementById("eventId").value
    }

    // Fill the object currentEvent with values from input fields in the modal
    if ($("#activityCategory").val() === "Medisch") {  // TODO: needs to be fetched using instanceof
        eventToSave = {
            type: "Event",
            title: document.getElementById("eventName").value,
            start: moment(document.getElementById("eventStartDate").value, "DD-MM-YYYY H:mm").toDate(),
            end: moment(document.getElementById("eventEndDate").value, "DD-MM-YYYY H:mm").toDate(),
            activity: {
                type: "MedicationActivity",
                name: document.getElementById("eventName").value,
                takenmedication: document.getElementById("takenMedication").value,
                medication: {
                    id: document.getElementById("medicationChoice").value,
                    amount: document.getElementById("takenMedication").value,
                },
            },
            team: {
                id: parseInt($('#teamId').attr('data-teamId'), 10),
            }
        }
    } else if ($("#activityCategory").val() === "Vrije tijd") {
            eventToSave = {
            type: "Event",
            title: document.getElementById("eventName").value,
            start: moment(document.getElementById("eventStartDate").value, "DD-MM-YYYY H:mm").toDate(),
            end: moment(document.getElementById("eventEndDate").value, "DD-MM-YYYY H:mm").toDate(),
            comment: document.getElementById("eventComment").value,
            activity: {
                type: "LeisureActivity",
                name: document.getElementById("eventName").value, //TODO: duplicate data; same as eventName
                comment: document.getElementById("eventComment").value, // TODO: duplicate data; same as eventComment
            },
            team: {
                id: parseInt($('#teamId').attr('data-teamId'), 10),
            }
        }
    }

    // Send the currentEvent object to the controller with an AJAX post
    $.ajax({
        url: url2,
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(eventToSave),
        dataType : 'json',
        async: true,
        success: function(result) {
            // Reload events on calendar if new event is written to the database successfully
            $('#calendar').fullCalendar('refetchEvents');
        },
        error : function(e) {
            alert("Error sending new event with AJAX!")
            console.log(eventToSave)
            console.log("ERROR: ", e);
        }
    });
}

// This function gets a medicationlist.
function getMedication(){
        $.ajax({
             type:'GET',
             url: ctx + "/calendar/" + $('#teamId').attr('data-teamId') + '/medications',
             dataType: 'json',
             success : function(result) {
                     List = result.data
                     $('#medicationChoice').empty();
                     $('#medicationChoice').append('<option value="">Kies een medicatie</option>');
                     for (i in List ) {
                        $('#medicationChoice').append('<option value="' + List[i].id + '">' + List[i].name + '</option>');
                     }
                 },
                 error : function(e) {
                 alert("error Error ERROR!")
                 console.log("ERROR: ", e);
                  }
          });
    }