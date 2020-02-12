<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en" xmlns:spring="" xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <title>Medicatieoverzicht</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Add icon library -->
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>.

        <link id="csrfToken" data-csrfToken="${_csrf.token}"/>

        <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    </head>
    <body class = "webpage">
        <mytags:navbar/>
        <div class= "masthead">
            <div id="container">
                <br />
                <div class="ml-4 mt-4">
                <h3 class="font-weight-light">Voer medicatie voor ${team.teamName} in</h3>
                <form:form action="addMedicationToList()" modelAttribute="medication">
                <form:errors path = "*" cssClass = "alert alert-danger alert-dismissible fade show" role="alert" element = "div" />
                    <table>
                        <tr>
                            <spring:bind path="medicationName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                            <td><h5 class="font-weight-light">Naam medicatie:</h5>
                            <td>
                                <form:input id="medicationName" path="medicationName"  />
                            </td>
                             </div>
                           </spring:bind>
                        </tr>
                        <tr>
                            <spring:bind path="medicationAmount">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <td><h5 class="font-weight-light">Hoeveelheid:</h5></td>
                                    <td><form:input id="medicationAmount" path="medicationAmount" /></td>
                                </div>
                            </spring:bind>
                        </tr>
                        <tr>
                            <td><h5 class="font-weight-light">Beschrijving:</h5></td>
                            <td><form:input id="medicationComment" path="medicationComment"/></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input name="_csrf" type="hidden" value="${_csrf.token}" />
                                <input class="btn btn-primary" type="button" onClick="addMedicationToList()" value="Sla medicatie op" />
                            </td>
                        </tr>
                    </table>
                </form:form>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/medication.js"></script>
    </body>
</html>

