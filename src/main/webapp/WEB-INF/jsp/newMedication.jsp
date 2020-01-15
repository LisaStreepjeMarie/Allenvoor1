<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Medicatieoverzicht</title>
    </head>
    <body class="webpage">
    <div id="container">
        <p>
            <input class="btn btn-primary" type="submit" value="Home" onclick="window.location='/';" />
            <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
            <input class="btn btn-primary" type="submit" value="Overzicht medicatie ${team.teamName}" onclick="window.location='/medication/${team.teamId}';" />
        </p>
        <h1>Voer medicatie voor ${team.teamName} in:</h1>
        <form:form action="/medication/new" modelAttribute="medication">
            <table>
                <tr>
                    <td>Naam medicatie:</td>
                    <td>
                        <form:input path="medicationName" />
                    </td>
                </tr>
                <tr>
                    <td>Hoeveelheid:</td>
                    <td>
                        <form:input path="medicationAmount"/>
                    </td>
                </tr>
                <tr>
                    <td>Beschrijving:</td>
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
    </body>
</html>