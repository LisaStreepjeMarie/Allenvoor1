<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <title>Medicatieoverzicht</title>

        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Add icon library -->
            <link href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.css" rel='stylesheet'>
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
                    <h1 class="font-weight-light">Overzicht medicatie ${team.teamName}</h1>
                    <table>
                      <tr><th><th><h5 class="font-weight-light">Naam</h5></th><th><h5 class="font-weight-light">Hoeveelheid</h5></th><th><h5 class="font-weight-light">Beschrijving</h5></th></th>
                        <c:forEach items="${medicationList}" var="medication">
                            <tr><h5>
                                <td><a href="${pageContext.request.contextPath}/medication/select/<c:out value="${medication.medicationId}" />"</a>
                                <td><c:out value="${medication.medicationName}" /></td>
                                <td><c:out value="${medication.medicationAmount}" /></td>
                                <td><c:out value="${medication.medicationComment}" /></td><br>
                                <td><input class="btn btn-primary" type="submit" value="Verwijder medicatie" onclick="window.location='${pageContext.request.contextPath}/medication/delete/${medication.medicationId}';" /></td>
                            </h5></tr>
                        </c:forEach>
                    </table>
                    <p>
                    <br>
                    <input class="btn btn-primary" type="submit" value="Voer medicatie in" onclick="window.location='${pageContext.request.contextPath}/medication/new';" />
                    </p>
                </div>
            </div>
        </div>
   </body>
</html>

