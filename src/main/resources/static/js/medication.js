// setting the context url
var ctx = $('#contextPathHolder').attr('data-contextPath');

$.ajax({
    type:'GET',
    url: ctx + "/medication/getList",
    success : function(result) {
        medicationList = result.data;
        for (i in medicationList ) {
            addMedication(medicationList[i])
        }
    },
    error : function(e) {
        console.log("ERROR: ", e);
    }
});

<!-- function to open the modal and pass along/fill out some info -->
function addToGroceryListButton(id, name){
    $('#refillMedication').modal('show');
    $('.modal').find('#medicationID').val(id);
    document.getElementById("refillMedicationHeader").innerHTML = name
}

<!-- function to open the modal and pass along/fill out some info -->
function addToGroceryListButton(id, name){
    $('#refillMedication').modal('show');
    $('.modal').find('#medicationID').val(id);
    document.getElementById("refillMedicationHeader").innerHTML = name;
}

<!-- ajax call to pass along a medication to the grocerylist including the needed amount -->
function addMedicationToList(){
   id = document.getElementById("medicationID").value;
   amount = document.getElementById("refillAmount").value;

    $.ajax({
         type:'GET',
         url: "${pageContext.request.contextPath}/medication/grocerylist/" + id + "/" + amount,
         success : function(result) {
         },
         error : function(e) {
             console.log("ERROR: ", e);
         }
    });

    $('#refillMedication').modal('toggle');
    $('#refillForm').trigger("reset");
}

        function deleteMedication(medicationNumber){
            window.location="${pageContext.request.contextPath}/medication/delete/" + medicationNumber;
        }

        function addMedication(medication){
            $('#allMedications').append('<tr><td id ="' + medication.name + '">' + medication.name + '</td><td>' +
                medication.amount + '</td><td>' + medication.comment +
                '</td><td><input class="btn btn-primary deleteButton" onclick="confirmation(' + medication.id +
                ')"  type="button" value="Verwijder medicatie" /><input type="hidden" name="medicationID" value="' +
                medication.id + ' id="medicationNumber" /></td><td><input onclick="addToGroceryListButton(' +
                medication.id + ',' + medication.name +
                '.innerHTML)" class="btn btn-primary" type="button" value="Zet op boodschappenlijst"  data-toggle="modal" /></td></tr>');
        }

        function confirmation(medicationID){
            var result = confirm("Weet je het zeker?");
            if(result){
                // Delete logic goes here
                $('#allMedications').append(deleteMedication(medicationID));
            }
        }