function saveNewEvent() {
    // If eventId is null, make a new event. Otherwise change it
    if ( document.getElementById("eventId").value === "" ) {
        urlVariable = ctx + "/calendar/new/event"
    } else {
        urlVariable = ctx + "/calendar/change/event/" + document.getElementById("eventId").value
    }

    // Fill the object currentEvent with values from input fields in the modal

    if ($("#activityCategory").val() === "Medisch") {
        activityType = "MedicationActivity"
    } else if ($("#activityCategory").val() === "Vrije tijd") {
        activityType = "LeisureActivity"
    }

    eventToSave = {
        type: "Event",
        title: document.getElementById("eventName").value,
        start: moment(document.getElementById("eventStartDate").value, "DD-MM-YYYY H:mm").toDate(),
        end: moment(document.getElementById("eventEndDate").value, "DD-MM-YYYY H:mm").toDate(),
        comment: document.getElementById("eventComment").value,
        activity: {
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
        url: urlVariable,
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

function saveChangedEvent(eventId, activityId) {
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

function deleteEvent(eventId) {
    urlDeleteEvent = ctx + "/event/delete/";

    eventToDelete = {
        type: "Event",
        id: eventId,
    }

    $.ajax({
            url: urlDeleteEvent,
            method: "POST",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(eventToDelete),
            dataType : 'json',
            async: true,
            success: function(result) {
                // Reload events on calendar if new event is written to the database successfully
                $('#calendar').fullCalendar('refetchEvents');
            },
            error : function(e) {
                alert("Error sending new event with AJAX!")
                console.log(eventToDelete)
                console.log("ERROR: ", e);
            }
        });
}
