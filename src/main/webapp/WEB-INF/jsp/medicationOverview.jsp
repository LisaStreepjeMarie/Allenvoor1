<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <title>Medicatieoverzicht</title>

        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
        <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.12.0/css/all.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Add icon library -->
            <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    </head>

    <body class= "webpage">
        <mytags:navbar/>
        <div class= "masthead">
            <div id="container">
                <br>
                <div class="mt-3 col-12">
                   <h3 class="font-weight-light"> Overzicht medicatie ${team.teamName}</h3>
                    <table>
                      <th><th><h5 class="font-weight-light">Naam</h5></th><th><h5 class="font-weight-light">Hoeveelheid</h5></th><th><h5 class="font-weight-light">Beschrijving</h5></th></th>
                        <c:forEach items="${medicationList}" var="medication">
                            <tr><h5>
                                <td></td>
                                <td id ="${medication.medicationName}"><c:out value="${medication.medicationName}" /></td>
                                <td><c:out value="${medication.medicationAmount}" /></td>
                                <td><c:out value="${medication.medicationComment}" /></td><br>
                                <td><input class="btn btn-primary" type="button" value="Verwijder medicatie" onclick="window.location='${pageContext.request.contextPath}/medication/delete/${medication.medicationId}';" /></td>
                                <!-- not the best way to pass along value to the modal but it works for now -LM -->
                                <td><input onclick="addToGroceryListButton(${medication.medicationId}, ${medication.medicationName}.innerHTML)" class="btn btn-primary" type="button" value="Zet op boodschappenlijst"  data-toggle="modal" /></td>
                            </h5></tr>
                        </c:forEach>
                    </table>
                    <p>
                    <br>
                    <input class="btn btn-primary" type="button" value="Voer medicatie in" onclick="window.location='${pageContext.request.contextPath}/medication/new';" />
                    </p>
                </div>
            </div>
        </div>
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#refillMedication">
            Launch demo modal
        </button>

        <!-- Modal -->
        <form role="form" id="refillForm" >
        <div class="modal fade" id="refillMedication" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="refillMedicationHeader"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="medicationName">
                        Welke hoeveelheid moet er gekocht worden?
                        <br>
                        <br>
                        <div class="row">
                            <br>
                            <input type="hidden" name="medicationID" id="medicationID" />
                            <label class="col-4" for="refillAmount" control-label>Hoeveelheid</label>
                            <input type="number" name="refillAmount" id="refillAmount" />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" onclick="addMedicationToList()" class="btn btn-primary">Zet op de lijst!</button>
                    </div>
                </div>
            </div>
        </div>
        </form>
    <script>

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
            console.log("woop w00p")
             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });

    $('#refillMedication').modal('toggle');
    $('#refillForm').trigger("reset");
}
    </script>
   </body>
</html>

