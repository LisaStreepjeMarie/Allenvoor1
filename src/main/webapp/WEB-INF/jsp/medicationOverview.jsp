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
            <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
        </p>
        <h1>Overzicht medicatie:</h1>
        <table>
            <c:forEach items="${allMedication}" var="medication">
                <tr>
                    <td><a href="/medication/select/<c:out value="${medication.medicationId}" />"><c:out value="${medication.medicationName}" /></a></td>
                    <td><input class="btn btn-primary" type="submit" value="Verwijder medicatie" onclick="window.location='/medication/delete/${medication.medicationId}';" /></td>
                </tr>
            </c:forEach>
        </table>
        <p>
            <input class="btn btn-primary" type="submit" value="Voer medicatie in" onclick="window.location='/medication/new';" />
        </p>
    </div>
    <body>
</html>

