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

//this shows the medication amount when an excisting event is chosen
function showMedicationAmount(event, element){
    if ($('.modal').find('#activityCategory').val() == "Medisch")
      getMedication()
      /*$('.modal').find('#takenMedication').val(event.activity.takenmedication);*/
}

// This function fills the modal with event info if it exist
function showModalInputFields() {
    if ($('.modal').find('#activityCategory').val() == "Medisch") {
        hideAllModalInputFields();
        $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv, #modal-footer").show();
    } else {
        hideAllModalInputFields();
        $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv, #modal-footer").show();
    }

    $("#eventDoneDiv").css("display", "");
}

function toggleModalNewOrExistingEvent(start, end, newOrExisting) {
    if (newOrExisting === "existing") {
        showModalInputFields();
        document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
        document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";
        $("#delete-event").show();
        getMedication();
    } else {
        document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
        document.getElementById("save-change-event").innerHTML = "Maak afspraak";
        $("#delete-event").hide();
    }

    // this shows/hides the eventDone input field when the checkbox is toggled
    $("#eventDone").change(function () {
        if(document.getElementById("eventDone").checked == true) {
                $("#datetimepickerDone").show()
        } else {
                document.getElementById("eventDoneDate").removeAttribute("required");
                $("#datetimepickerDone").hide()
        }
    });

    $('.modal').find('#eventStartDate').val(moment(start).format('DD-MM-YYYY H:mm'));
    $('.modal').find('#eventEndDate').val(moment(end).format('DD-MM-YYYY H:mm'));
}