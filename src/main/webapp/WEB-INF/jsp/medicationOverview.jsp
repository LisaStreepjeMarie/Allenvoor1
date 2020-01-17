<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <title>Medicatieoverzicht</title>
    </head>
    <body class="webpage">
    <div id="container">

    <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
          <div class="container">
            <a class="navbar-brand"</a>
            <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="230" height="178"> </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                  <span class="navbar-toggler-icon"></span>
                </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
              <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                   <a class="nav-link" href='${pageContext.request.contextPath}/home'><i class="fa fa-home"></i> Home</a>
                        <span class="sr-only">(current)</span>
                      </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
        <p>
            <input class="btn btn-primary" type="submit" value="Home" onclick="window.location='${pageContext.request.contextPath}/';" />
            <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='${pageContext.request.contextPath}/logout';" />
        </p>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <h1 class=>Overzicht medicatie ${team.teamName}:</h1>
        <table>
          <tr><th><th>Naam</th><th>Hoeveelheid</th><th>Beschrijving</th></th>
            <c:forEach items="${medicationList}" var="medication">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/medication/select/<c:out value="${medication.medicationId}" />"</a>
                    <td><c:out value="${medication.medicationName}" /></td>
                    <td><c:out value="${medication.medicationAmount}" /></td>
                    <td><c:out value="${medication.medicationComment}" /></td><br>
                    <td><input class="btn btn-primary" type="submit" value="Verwijder medicatie" onclick="window.location='${pageContext.request.contextPath}/medication/delete/${medication.medicationId}';" /></td>
                </tr>
            </c:forEach>
        </table>
        <p>
        <br>
            <input class="btn btn-primary" type="submit" value="Voer medicatie in" onclick="window.location='${pageContext.request.contextPath}/medication/new';" />
        </p>
    </div>
    <body>
</html>

