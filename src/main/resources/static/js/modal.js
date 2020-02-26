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

function optionLeisureActivity(){
        document.getElementById('save-change-event').setAttribute( "onClick", "saveEvent('LeisureActivity')" );
        hideAllModalInputFields();
        $("#eventNameDiv, #eventCommentDiv, #eventDatesDiv").show();
        $("#eventPeriodic").show();
        $("#eventPeriodic").change(function () {
            if (document.getElementById("eventPeriodic").checked == true) {
                    $("#intervalDiv").show();
                    $("#maxNumberDiv").show();
            } else {
                    document.getElementById("eventPeriodic").removeAttribute("required");
                    $("#intervalDiv").hide();
                    $("#maxNumberDiv").hide()
            }
        });
}

function optionsMedicationActivity(){
        document.getElementById('save-change-event').setAttribute( "onClick", "saveEvent('MedicationActivity')" );
        hideAllModalInputFields();
        $("#eventNameDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
        getMedication(null);
        $("#eventPeriodic").show();
        $("#eventPeriodic").change(function () {
            if(document.getElementById("eventPeriodic").checked == true) {
                    $("#intervalDiv").show();
                    $("#maxNumberDiv").show();
            } else {
                    document.getElementById("eventPeriodic").removeAttribute("required");
                    $("#intervalDiv").hide();
                    $("#maxNumberDiv").hide()
            }
        });
}

function optionsMemberTab(){
        hideAllModalInputFields();
        $("#subscriptionListDiv, #doneByMemberDiv, #datetimepickerDone, #doneByMemberLabel").show();
        getEventSubscriptions();
}

function preFillDateFields(event){

    $('#newModal').find('#eventStartDate').val(moment(event.start).format('DD-MM-YYYY H:mm'));
    $('#newModal').find('#eventEndDate').val(moment(event.end).format('DD-MM-YYYY H:mm'));

    if(event.maxNumber != null) {
        $("#save-change-event").hide();
        $("#eventIsPeriodicDiv").hide();
        $("#eventPeriodicCheckDiv").hide();
        $("#intervalDiv").hide();
        $("#maxNumberDiv").hide();
        }
    else {
        $("#save-change-event").show();
    }
}

function preFillSharedFields(event){
        $('#newModal').find('#eventName').val(event.title);
        $('#newModal').find('#eventInterval').val(event.interval);
        $('#newModal').find('#eventMaxNumber').val(event.maxNumber);

        if(event.doneByMember){
            $('#doneByMember').empty();
            $('#newModal').find('#eventDoneDate').val(moment(event.donedate).format('DD-MM-YYYY H:mm'));
            }
        }

function preFillLeisureActivityFields(event){
        $('#newModal').find('#eventComment').val(event.comment);
}

function preFillMedicationActivityFields(event){
        $('#newModal').find("medicationChoice").val("#event.activity.medication.id");
        $('#newModal').find('#takenMedication').val(event.activity.takenmedication);
}

// This function hides all modal options
function hideAllModalInputFields() {
    $("#eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #takenMedicationDiv, #subscribe-event, #subscriptionListDiv, #doneByMemberLabel").css("display", "none");
    $("#eventDatesDiv, #doneByMemberDiv, #datetimepickerDone, #eventIsPeriodicDiv, #intervalDiv, #maxNumberDiv, #doneByMemberDiv").css("display", "none");
}