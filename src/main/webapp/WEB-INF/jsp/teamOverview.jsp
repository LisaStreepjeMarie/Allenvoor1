<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
            integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Overzicht groepen</title>
    </head>
    <body class="webpage">
        <div id="container">
            <p>
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
            </p>
            <h1>Overzicht groepen</h1>
            <table>
                <tr><th>Groep</th><th>Deelnemer</th><th></th></tr>
                <c:forEach items="${allTeams}" var="team">
                    <tr>
                        <td><a href="/team/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a></td>
                        <td>
                        <c:forEach items="${team.membername}" var="member">
                        <a href="/team/select/<c:out value="${member.membername}" />"><c:out value="${member.membername}" /></a><br />
                        </c:forEach>
                        </td>
                        <td><input class="btn btn-primary" type="submit" value="Verwijder groep" onclick="window.location='/team/delete/${team.teamId}';" /></td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input class="btn btn-primary" type="submit" value="Voeg groep toe" onclick="window.location='/team/new';" />
            </p>
        </div>
    <body>
</html>