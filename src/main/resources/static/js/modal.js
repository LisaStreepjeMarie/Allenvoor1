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
        $("#subscriptionListDiv, #doneByMemberDiv, #datetimepickerDone").show();
        getEventSubscriptions();
        filldoneByMembers(null)
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
            $('#doneByMember').empty()
            $("#datetimepickerDone, #doneByMemberDiv").show()
            $('#newModal').find('#eventDoneDate').val(moment(event.donedate).format('DD-MM-YYYY H:mm'));
            filldoneByMembers(event.doneByMember.name)
            }
        }

function preFillLeisureActivityFields(event){
        $('#newModal').find('#eventComment').val(event.comment);
}

function preFillMedicationActivityFields(event){
        console.log(event.activity.medication.id);
        $('#newModal').find("medicationChoice").val("#event.activity.medication.id");
//        $('#newModal').find('#medicationChoice').val(event.activity.medication.id);
        $('#newModal').find('#takenMedication').val(event.activity.takenmedication);
}

// This function hides all modal options
function hideAllModalInputFields() {
    $("#eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #takenMedicationDiv, #subscribe-event, #subscriptionListDiv").css("display", "none");
    $("#eventDatesDiv, #doneByMemberDiv, #datetimepickerDone, #eventIsPeriodicDiv, #intervalDiv, #maxNumberDiv, #subscriptionListDiv").css("display", "none");
}

// Cleans the modal upon closing
function filldoneByMembers(givenName){

    if(givenName != null){
    var first = '<option value="">'+ givenName + '</option>';
    }else {
    var first = '<option value="">Kies een teamlid</option>';
    }


    $.ajax({
         type:'GET',
         url: ctx + "/calendar/" + $('#teamId').attr('data-teamId') + '/teamMembers',
         dataType: 'json',
         success : function(result) {
                 List = result.data
                 $('#doneByMember').empty()
                 $('#doneByMember').append(first);
                 for (i in List ) {
                    $('#doneByMember').append('<option value="' + List[i].member.id + '">' + List[i].member.name + '</option>');
                 }
             },
             error : function(e) {
             alert("getMedication() error")
             console.log("ERROR: ", e);
         }
    });

}

