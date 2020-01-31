<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Add icon library -->
        <link href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        </head>

    <title>Medicatieoverzicht</title>
        <body class="webpage">
    <!-- Navigation -->
                <nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
                    <a class="navbar-brand"</a><br><br><br>
                    <a href="${pageContext.request.contextPath}/home">
                    <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="133" height="114"> </a>
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
                        <a class="nav-link" href='${pageContext.request.contextPath}/medication/${team.teamId}'><i class=" fa fa-medkit"></i> Medicatieoverzicht</a>
                        </li>
                        <li class="nav-item">
                          <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
                        </li>
                      </ul>
                    </div>
                </nav>
    <div id="container">
    <header class= "masthead">
    <br>
    <div class="mt-3 col-12">
        <h3 class="font-weight-light">Voer medicatie voor ${team.teamName} in</h3>
        <br>
        <form:form action="${pageContext.request.contextPath}/medication/new" modelAttribute="medication">
            <table>
                <tr>
                    <td><h5 class="font-weight-light">Naam medicatie:</h5>
                    <td>
                        <form:input path="medicationName" />
                    </td>
                </tr>
                <tr>
                    <td><h5 class="font-weight-light">Hoeveelheid:</h5>
                    <td>
                        <form:input type= "number" path="medicationAmount"/>
                    </td>
                </tr>
                <tr>
                    <td><h5 class="font-weight-light">Beschrijving:</h5>
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