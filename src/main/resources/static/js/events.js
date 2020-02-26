// Define contextpath
var ctx = $('#contextPathHolder').attr('data-contextPath');

// Get csrf token (needed to post a json through spring boot security)
$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

function saveEvent(activityType) {
    // Fill the object currentEvent with values from input fields in the modal
    eventId = document.getElementById("eventId").value;
    activityId = document.getElementById("activityId").value;

    eventToSave = {
            id: eventId,
            type: "Event",
            title: document.getElementById("eventName").value,
            start: moment(document.getElementById("eventStartDate").value, "DD-MM-YYYY H:mm").toDate(),
            end: moment(document.getElementById("eventEndDate").value, "DD-MM-YYYY H:mm").toDate(),
            comment: document.getElementById("eventComment").value,
            interval: document.getElementById("eventInterval").value,
            maxNumber: document.getElementById("eventMaxNumber").value,
            donedate: moment(document.getElementById("eventDoneDate").value, "DD-MM-YYYY H:mm").toDate(),
            doneByMember: {
                id: document.getElementById("doneByMember").value,
            },
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
function getMedication(){
    $.ajax({
         type:'GET',
         url: ctx + "/calendar/" + $('#teamId').attr('data-teamId') + '/medications',
         dataType: 'json',
         success : function(result) {
                 List = result.data
                 $('#medicationChoice').empty();
                 $('#medicationChoice').append('<option value="">Kies een medicatie</option>');
                 for (i in List) {
                    $('#medicationChoice').append('<option value="' + List[i].id + '">' + List[i].name + '</option>');
                 }

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

// this function gives an event to the correct endpoint for either changing the dates or deleting an event (through the targetUrl)
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

function getEventSubscriptions(){
    var eventId = document.getElementById("eventId").value;
    $.ajax({
         type:'GET',
         url: ctx + "/event/" + eventId + "/getsubscriptionlist",
         success : function(result) {
            subscriptionList = result.data;
            var alreadySubscribed = false;
            $('#subscriptionList').empty();

            for (i in subscriptionList){
                if ($('#principalUsername').attr('data-principalUsername') === subscriptionList[i].member.name) {
                    $('#subscriptionList').append('<li class="list-group-item" id="subscription-id-'+ subscriptionList[i].id + '" value="' +
                        subscriptionList[i].member.id + '">' + subscriptionList[i].member.name + '<input class="btn btn-primary float-right"' +
                        'style="width: auto;padding:0px;" type="button" value="   Schrijf uit   "' +
                        'onClick="unsubscribeFromEvent(' + subscriptionList[i].id + ')"></li>');
                    alreadySubscribed = true;
                } else {
                    $('#subscriptionList').append('<li class="list-group-item" value="' +
                        subscriptionList[i].member.id + '">' + subscriptionList[i].member.name + '</li>');
                }
            }
            if (alreadySubscribed === false) {
                document.getElementById('subscribe-button').setAttribute( "onClick", "addEventSubscription(" + eventId + ")" );
            } else {
                $('#subscribeDiv').hide();
            }
         },
         error : function(e) {
         console.log("ERROR: ", e);
         }
    });
}

// Adds members' subscription to event
function addEventSubscription(eventId){
    subscribeEvent = {
        type: "EventSubscription",
        event: {
            type: "Event",
            id: eventId,
        },
    }

    $.ajax({
    url: ctx + "/event/subscribe",
    method: "POST",
    contentType: "application/json; charset=UTF-8",
    data: JSON.stringify(subscribeEvent),
    dataType: 'json',
    async: true,
    success: function(result) {
        $('#subscriptionList').append('<li class="list-group-item" id="subscription-id-'+
            result.data.id + '" value="' + result.data.member.id + '">' + result.data.member.name +
            '<input class="btn btn-primary float-right" style="width: auto;padding:0px;" type="button"' +
            'value="   Schrijf uit   " onClick="unsubscribeFromEvent(' + result.data.id + ')"></li>');
        $('#subscribeDiv').hide();
    },
    error: function(e) {
        alert("addEventSubscription() error")
        console.log("ERROR: ",  e);
    }
    });
}

// Removes members' event subscription
function unsubscribeFromEvent(eventSubscriptionId, eventId) {
    unsubscribeEvent = {
        type: "EventSubscription",
        id: eventSubscriptionId,
        event: {
            type: "Event",
            id: eventId,
        }
    },

    $.ajax({
         url: ctx + "/event/unsubscribe",
         method: "POST",
         contentType: "application/json; charset=UTF-8",
         data: JSON.stringify(unsubscribeEvent),
         dataType: 'json',
         async: true,
         success: function(result) {
            $('#subscription-id-' + eventSubscriptionId).remove();
            $('#subscribe').append('<input class="btn btn-primary" id="subscribe-button" type="button" value="Schrijf je in" onclick="addEventSubscription('+ result.data.event.id + ')">');
            $('#subscribeDiv').show();
         },
         error: function(e) {
             alert("unsubscribeFromEvent() error")
             console.log("ERROR: ",  e);
         }
    });
}