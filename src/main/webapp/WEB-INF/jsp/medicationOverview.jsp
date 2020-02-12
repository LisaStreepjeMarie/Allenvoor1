<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:mytags="">
    <head>
        <meta charset='utf-8' />
        <title>Medicatieoverzicht</title>

        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
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
        <div class= "masthead row">
            <div class= "col-2">
                <div class="ml-3 mt-3">
                    <c:forEach items="${teamList}" var="team">
                        <br>
                        <button type="button" class="btn btn-lg btn-primary btn-block" data-toggle="collapse" data-target=#${team.teamName} >${team.teamName}</button>
                        <div id=${team.teamName} class="collapse">
                            <div class="card card-body">
                                <tr>
                                    <a class="dropdown-item" href='${pageContext.request.contextPath}/calendar/${team.teamId}'> <i class="fa fa-calendar" ></i>&emsp;Kalender  </a>
                                </tr>
                                <tr>
                                    <a class="dropdown-item" href='${pageContext.request.contextPath}/medication/${team.teamId}'><i class=" fa fa-medkit"></i>&emsp;Medicatie  </a>
                                </tr>
                                <tr>
                                    <a class="dropdown-item" href='${pageContext.request.contextPath}/grocerylist/${team.teamId}'><i class="fa fa-shopping-basket"></i>&emsp;Boodschappenlijst  </a>
                                </tr>
                                <tr>
                                    <a class="dropdown-item" href='${pageContext.request.contextPath}/chat/${team.teamId}'><i class="fas fa-comments"></i>&emsp;Chat</a>
                                </tr>
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>

            <div id="container">
                <br>
                <div class="mt-3 col-12">
                   <h3 class="font-weight-light"> Overzicht medicatie ${team.teamName}</h3>
                    <table>
                      <th><h5 class="font-weight-light">Naam</h5></th><th><h5 class="font-weight-light">Hoeveelheid</h5></th><th><h5 class="font-weight-light">Beschrijving</h5></th></th>
                    </table>
                    <p>
                     <div id="allMedications">
                    </div>
                    <br>
                    <input class="btn btn-primary" type="button" value="Voer medicatie in" onclick="window.location='${pageContext.request.contextPath}/medication/new';" />
                    </p>
                </div>
            </div>
        </div>

    </div>
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
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Sluiten</button>
                        <button type="button" onclick="addMedicationToList()" class="btn btn-primary">Zet op de lijst!</button>
                    </div>
                </div>
            </div>
        </div>
        </form>

    <script>
     $.ajax({
            type:'GET',
                  url: "${pageContext.request.contextPath}/medication/getList",
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
        $('#allMedications').append('<table><tr><td id ="' +
             medication.name + '">' + medication.name + '</td><td>' +
             medication.amount + '</td><td>' + medication.comment +
             '</td><td><input class="btn btn-primary deleteButton" onclick="deleteMedication(' + medication.id
             + ')"  type="button" value="Verwijder medicatie" /><input type="hidden" name="medicationID" value="'
             + medication.id + ' id="medicationNumber" /></td><td><input onclick="addToGroceryListButton(' + medication.id
             + ',' + medication.name + '.innerHTML)" class="btn btn-primary" type="button" value="Zet op boodschappenlijst"  data-toggle="modal" /></td></tr></table>');
            }
    </script>
   </body>
</html>
