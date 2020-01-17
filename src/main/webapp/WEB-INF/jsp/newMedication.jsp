<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Add icon library -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        </head>



        <title>Medicatieoverzicht</title>

     <!-- Navigation -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
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
                      <a class="nav-link" href='${pageContext.request.contextPath}/medication/${team.teamId}'> Medicatieoverzicht</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
                    </li>
                  </ul>
                </div>
            </nav>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
         <body class="webpage">
    <header class= "masthead">
        <div id="container">
        <h2>Voer medicatie voor ${team.teamName} in:</h2>
        <form:form action="${pageContext.request.contextPath}/medication/new" modelAttribute="medication">
            <table>
                <tr>
                    <td><h5>Naam medicatie:</h5>
                    <td>
                        <form:input path="medicationName" />
                    </td>
                </tr>
                <tr>
                    <td><h5>Hoeveelheid:</h5>
                    <td>
                        <form:input path="medicationAmount"/>
                    </td>
                </tr>
                <tr>
                    <td><h5>Beschrijving:</h5>
                    <td>
                       <form:input path="medicationComment"/>
                     </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input class="btn btn-primary" type="submit" value="Sla medicatie op" />
                    </td>
                </tr>
            </table>
        </form:form>
        </div>
    </body>
</html>