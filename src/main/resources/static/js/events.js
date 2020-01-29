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
            activity: {
                id: activityId,
                type: activityType,
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
             con
             sole.log("ERROR: ", e);
         }
    });
}

function getCalendarData(start, end, callback) {
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

function deleteEvent(eventId, targetUrl) {
    event = {type: "Event", id: eventId, start: "0", end: "0",},
    changeEvent(event, targetUrl)
}

function changeEvent(event, targetUrl) {
    currentEvent = {
        type: "Event",
        id: event.id,
        start: event.start,
        end: event.end,
    }

    $.ajax({
        url: targetUrl,
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
            console.log("ERROR: ");
        }
    });
}