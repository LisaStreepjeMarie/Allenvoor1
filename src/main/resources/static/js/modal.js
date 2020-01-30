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

// This function hides all modal options
function hideAllModalInputFields() {
    $("#datetimepickerDone, #eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #eventDatesDiv, #takenMedicationDiv").css("display", "none");
}

// This function fills the modal with event info if it exist
function showModalInputFields() {
    hideAllModalInputFields();
    if ($('.modal').find('#activityCategory').val() == "Medisch") {
        $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv, #modal-footer").show();
    } else {
        $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv, #modal-footer").show();
    }
    $("#eventDoneDiv").css("display", "");
}

// Modify modal fields/buttons/labels with values depending on event/activity type
function fillModal(event) {
    if (event.id != null) {
        showModalInputFields();
        document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
        document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";
        $("#delete-event").show();
        $('.modal').find('#eventName').val(event.title);
        $('.modal').find('#eventComment').val(event.comment);
    } else {
        document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
        document.getElementById("save-change-event").innerHTML = "Maak afspraak";
        $("#delete-event").hide();
    }
    getMedication(event);

    // this shows/hides the eventDone input field when the checkbox is toggled
    $("#eventDone").change(function () {
        if(document.getElementById("eventDone").checked == true) {
                $("#datetimepickerDone").show()
        } else {
                document.getElementById("eventDoneDate").removeAttribute("required");
                $("#datetimepickerDone").hide()
        }
    });

    $('.modal').find('#eventStartDate').val(moment(event.start).format('DD-MM-YYYY H:mm'));
    $('.modal').find('#eventEndDate').val(moment(event.end).format('DD-MM-YYYY H:mm'));
    $('.modal').modal('show');
}