// Define contextpath
var ctx = $('#contextPathHolder').attr('data-contextPath');

// Get csrf token (needed to post a json through spring boot security)
$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

function saveEvent(eventId, activityId) {
    // Fill the object currentEvent with values from input fields in the modal
    if ($("#activityCategory").val() === "Medisch") {
        activityType = "MedicationActivity"
    } else if ($("#activityCategory").val() === "Vrije tijd") {
        activityType = "LeisureActivity"
    }

    eventToSave = {
            id: eventId,
            type: "Event",
            title: document.getElementById("eventName").value,
            start: moment(document.getElementById("eventStartDate").value, "DD-MM-YYYY H:mm").toDate(),
            end: moment(document.getElementById("eventEndDate").value, "DD-MM-YYYY H:mm").toDate(),
            comment: document.getElementById("eventComment").value,
            interval: document.getElementById("eventInterval").value,
            interval: document.getElementById("eventMaxNumber").value,
            activity: {
                type: activityType,
                activityId: document.getElementById("activityId").value,
                name: document.getElementById("eventName").value,
                takenmedication: document.getElementById("takenMedication").value,
                comment: document.getElementById("eventComment").value,
                medication: {
                    id: document.getElementById("medicationChoice").value,
                    amount: document.getElementById("takenMedication").value,
                },
            },
            team: {
                id: parseInt($('#teamId').attr('data-teamId'), 10),
            }
        }

    // Send the currentEvent object to the controller with an AJAX post
    $.ajax({
        url: ctx + "/calendar/new/event",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data:  JSON.stringify(eventToSave),
        dataType : 'json',
        async: true,
        success: function(result) {
            // Reload events on calendar if new event is written to the database successfully
            $('#calendar').fullCalendar('refetchEvents');
        },
        error : function(e) {
            alert("saveEvent() error");
            console.log("ERROR: ", e);
        }
    });
}

// This function gets a medicationlist.
function getMedication(event){
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
                 document.getElementById("medicationChoice").value = event.activity.medication.id;
                 $('.modal').find('#takenMedication').val(event.activity.takenmedication);
             },
             error : function(e) {
             alert("getMedication() error")
             console.log("ERROR: ", e);
         }
    });
}

// this function loads the calendar with all events between start and end
function getEvents(start, end, callback) {
    $.ajax({
        type:'GET',
        url: ctx + '/calendar/get/' + $('#teamId').attr('data-teamId') + '/' + start + '/' + end + '/',
        dataType: 'json',
        error: function (xhr, type, exception) { alert("Error fetching calendar data: " + exception); },
        success : function(result) {
                if (result.status == "success") {
                   var events = result.data;
                   callback(events);
                } else {
                   console.log("ERROR: ", e);
                }
        }
    });
}
// creates an event object to delete and gives this to the function below together with a delete endpoint URL
function deleteEvent(eventId, targetUrl) {
    event = {type: "Event", id: eventId, start: "0", end: "0",},
    dropResizeOrDeleteEvent(event, targetUrl)
}

// this function gives an event to the right end point for either changing the dates or deleting an event (through the targetUrl)
function dropResizeOrDeleteEvent(event, targetUrl) {
    eventToChange = {
        type: "Event",
        id: event.id,
        start: event.start,
        end: event.end,
    }

    $.ajax({
        url: targetUrl,
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(eventToChange),
        dataType: 'json',
        async: true,
        success: function(result) {
                $('#calendar').fullCalendar('refetchEvents');
        },
        error: function(e) {
            alert("changeEvent() error")
            console.log("ERROR: ",  e);
        }
    });
}