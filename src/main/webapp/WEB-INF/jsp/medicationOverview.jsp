<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <title>Medicatieoverzicht</title>

        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

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
                                <td><a href="${pageContext.request.contextPath}/medication/select/<c:out value="${medication.medicationId}" />"</a>
                                <td><c:out value="${medication.medicationName}" /></td>
                                <td><c:out value="${medication.medicationAmount}" /></td>
                                <td><c:out value="${medication.medicationComment}" /></td><br>
                                <td><input class="btn btn-primary" type="submit" value="Verwijder medicatie" onclick="window.location='${pageContext.request.contextPath}/medication/delete/${medication.medicationId}';" /></td>
                                <td><input id="groceryListButton" class="btn btn-primary" data-id="${medication.medicationName}" type="submit" value="Zet op boodschappenlijst"  data-toggle="modal" /></td>
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
        <form role="form" id="formID" >
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
                            <label class="col-4" for="takenMedication" control-label>Hoeveelheid</label>
                            <input type="number" name="takenMedication" id="takenMedication" />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Zet op de lijst!</button>
                    </div>
                </div>
            </div>
        </div>
        </form>
    <script>
   $("#groceryListButton").click(function(){ // Click to only happen on announce links
   var url = $(this).attr('data-id');
    console.log(url);
    console.log("hallo stomme lisa");
     $(".modal-body").val(url);
     $(".modal-title").prepend(url);
     $('#refillMedication').modal('show');

   });
    </script>
   </body>
</html>

