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
      getMedication()
      /*$('.modal').find('#takenMedication').val(event.activity.takenmedication);*/
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