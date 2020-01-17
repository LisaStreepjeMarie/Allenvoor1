<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>

          <!-- Add icon library -->
          <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
          <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
          <link href="../css/style.css" rel="stylesheet" type="text/css"/>
          </head>

        <title>Voeg een groep toe</title>
    </head>
    <body class= "webpage">
        <!-- Navigation -->
            <body>
            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
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
                      <a class="nav-link" href='${pageContext.request.contextPath}/team/all'><i class="fa fa-users"></i> Groepsoverzicht</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
                    </li>
                  </ul>
                </div>
              </div>
            </nav>

        <div id="container">
<header class= "masthead">
            <h1>Voeg gegevens groep toe</h1>

            <form:form action="${pageContext.request.contextPath}/team/new" modelAttribute="team">
                <table>
                    <tr>
                        <td>Groepsnaam:</td>
                        <td>
                            <form:input path="teamName" value="" /></form>
                        </td>
                    </tr>
                    <tr>
                        <td>Groepslid:</td>
                        <td>
                            <form:input path="allMembersInThisTeamSet" value="${members.membername}" /></form>
                        </td>
                    </tr>
                    <tr><td colspan="2"><input class="btn btn-primary" type="submit" value="Bewaar" /></td></tr>
                </table>
            </form:form>
        </div>
    </body>
</html>