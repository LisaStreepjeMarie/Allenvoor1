
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
            integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Overzicht activiteiten</title>
    </head>
    <body class="webpage">
        <div id="container">
            <p>
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
            </p>
            <h1>Overzicht activiteiten</h1>
            <table>
                <c:forEach items="${allActivities}" var="activity">
                    <tr>
                        <td><a href="/activity/select/<c:out value="${activity.activityId}" />"><c:out value="${activity.activityName}" /></a></td>
                        <td><input class="btn btn-primary" type="submit" value="Verwijder activity" onclick="window.location='/activity/delete/${activity.activityId}';" /></td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input class="btn btn-primary" type="submit" value="Voeg activity toe" onclick="window.location='/activity/new';" />
            </p>
        </div>
    <body>
</html>