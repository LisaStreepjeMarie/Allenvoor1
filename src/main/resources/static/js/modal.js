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
    $("#doneByMemberDiv, #datetimepickerDone, #intervalDiv, #maxNumberDiv, #eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #eventDatesDiv, #takenMedicationDiv").css("display", "none");
}

// This function fills the modal with event info if it exist
function showModalInputFields() {
    hideAllModalInputFields();
    if ($('.modal').find('#activityCategory').val() == "Medisch") {
        $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv, #modal-footer").show();
    } else {
        $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv, #modal-footer").show();
    }
    $("#eventInterval").css("display", "");
    $("#eventMaxNumber").css("display", "");
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
        $('.modal').find('#eventInterval').val(event.interval);
        $('.modal').find('#eventMaxNumber').val(event.maxNumber);
        if(event.interval != null) {
            document.getElementById("eventPeriodic").checked;
            //todo
            //alert($("#intervalDiv"));
            $("#intervalDiv").show();
            $("#maxNumberDiv").show();
            /* $("#eventMaxNumberLabel").show();
            $("#eventNumberToGoLabel").hide(); */
        }
        if(event.doneByMember){
            console.log(event.donedate)
            $('#doneByMember').empty()
            $("#datetimepickerDone, #doneByMemberDiv").show()
            $("#eventDone").prop("checked", true);
            filldoneByMembers(event.doneByMember.name)
//            $('.modal').find('#doneByMember').val(event.doneByMember.name)
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
                /* $("#eventNumberToGoLabel").hide();
                $("#eventMaxNumberLabel").show(); */

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

    $('.modal').find('#eventStartDate').val(moment(event.start).format('DD-MM-YYYY H:mm'));
    $('.modal').find('#eventEndDate').val(moment(event.end).format('DD-MM-YYYY H:mm'));
    $('.modal').modal('show');
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

