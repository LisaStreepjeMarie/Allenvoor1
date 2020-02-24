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
        document.getElementById('save-change-event').setAttribute( "onClick", "saveEvent('"+ null + "','" + null + "', 'LeisureActivity')" );
        hideAllModalInputFields();
        $("#eventNameDiv, #eventCommentDiv, #eventDatesDiv").show();
        $("#delete-event").hide();
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

function optionsMedicationActivity(){
        document.getElementById('save-change-event').setAttribute( "onClick", "saveEvent('"+ null + "','" + null + "', 'MedicationActivity')" );
        hideAllModalInputFields();
        $("#eventNameDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
        event = {type: "Event", id: null, };
        getMedication(event);
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
        $("#doneByMemberDiv, #datetimepickerDone").show();
        $("#delete-event").hide();
}

function preFillDateFields(event){
    if(event.maxNumber != null) {
        document.getElementById("eventPeriodic").checked;
        $("#eventIsPeriodicDiv").show();
        $("#eventPeriodicCheckDiv").hide();
        $("#intervalDiv").hide();
        $("#maxNumberDiv").hide();
        }
}

function preFillSharedFields(event){
//        document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
        document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";
        $("#delete-event").show();
        $('#newModal').find('#eventName').val(event.title);
        $('#newModal').find('#eventInterval').val(event.interval);
        $('#newModal').find('#eventMaxNumber').val(event.maxNumber);
        $("#delete-event").show();

        if(event.doneByMember){
            $('#doneByMember').empty()
            $("#datetimepickerDone, #doneByMemberDiv").show()
            $("#eventDone").prop("checked", true);
            $('#formDiv').find('#eventDoneDate').val(moment(event.donedate).format('DD-MM-YYYY H:mm'));
            filldoneByMembers(event.doneByMember.name)
            }

        if (event.activity.type === "LeisureActivity"){
            optionLeisureActivity();
            preFillLeisureActivityFields(event);
            } else {
            optionsMedicationActivity();
            preFillMedicationActivityFields(event);
            }
        }

function preFillLeisureActivityFields(event){
        $('#newModal').find('#eventComment').val(event.comment);
}

function preFillMedicationActivityFields(event){
        getMedication(event);
}
// This function hides all modal options
function hideAllModalInputFields() {
    $("#eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #takenMedicationDiv, #subscribe-event").css("display", "none");
    $("#eventDatesDiv, #doneByMemberDiv, #datetimepickerDone, #eventIsPeriodicDiv, #intervalDiv, #maxNumberDiv").css("display", "none");
}

// This function fills the modal with event info if it exist
function showModalInputFields() {
    hideAllModalInputFields();
    if ($('#formDiv').find('#activityCategory').val() == "Medisch") {
        $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv").show();
        $("#eventDatesDiv, #eventPeriodicCheckDiv, #modal-footer").show();
    } else {
        $("#eventNameDiv, #eventDateStartEndDiv, #eventCommentDiv, #eventDatesDiv, #eventPeriodicCheckDiv").show();
        $("#modal-footer").show();
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
        $('#formDiv').find('#eventName').val(event.title);
        $('#formDiv').find('#eventComment').val(event.comment);
        $('#formDiv').find('#eventInterval').val(event.interval);
        $('#formDiv').find('#eventMaxNumber').val(event.maxNumber);
        if(event.maxNumber != null) {
            document.getElementById("eventPeriodic").checked;
            $("#eventIsPeriodicDiv").show();
            $("#eventPeriodicCheckDiv").hide();
            $("#intervalDiv").hide();
            $("#maxNumberDiv").hide();
        }
        if(event.doneByMember){
            $('#doneByMember').empty()
            $("#datetimepickerDone, #doneByMemberDiv").show()
            $("#eventDone").prop("checked", true);
            $('#formDiv').find('#eventDoneDate').val(moment(event.donedate).format('DD-MM-YYYY H:mm'));
            filldoneByMembers(event.doneByMember.name)
            //$('.modal').find('#doneByMember').val(event.doneByMember.name)
        }
    } else {
        document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
        document.getElementById("save-change-event").innerHTML = "Maak afspraak";
        $("#delete-event").hide();
    }
    getMedication(event);

    // this shows/hides the eventPeriodic input field when the checkbox is toggled
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

    // this shows/hides the eventDone input field when the checkbox is toggled
    $("#eventDone").change(function () {
        if(document.getElementById("eventDone").checked == true) {
                $("#datetimepickerDone, #doneByMemberDiv").show()
                filldoneByMembers(null);
        } else {
                document.getElementById("eventDoneDate").removeAttribute("required")
                $("#datetimepickerDone, #doneByMemberDiv").hide();
        }
    });

    $('#formDiv').find('#eventStartDate').val(moment(event.start).format('DD-MM-YYYY H:mm'));
    $('#formDiv').find('#eventEndDate').val(moment(event.end).format('DD-MM-YYYY H:mm'));
    $('#formDiv').modal('show');
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

