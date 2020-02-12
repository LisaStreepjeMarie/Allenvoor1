// set up to send a CSRF token with ajax for postmapping
$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

// setting the context url
var ctx = $('#contextPathHolder').attr('data-contextPath');

// posting medication via json
function addMedicationToList(){

    // creating a new medication to save
    newMedicationItem = {
        name: document.getElementById("medicationName").value,
        amount: document.getElementById("medicationAmount").value,
        comment: document.getElementById("medicationComment").value,
    }
    console.log(newMedicationItem);

    $.ajax({
        url: ctx + "/medication/new",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(newMedicationItem),
        dataType: 'json',
        async: true,
        success : function(result) {
            document.getElementById("medication").reset();
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });
}
