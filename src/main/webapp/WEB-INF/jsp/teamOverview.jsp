<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <title>Overzicht groepen</title>
        <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    </head>
    <body class="webpage">

        <div id="container" class="ml-4 mt-4">
            <p>
                <input class="btn btn-primary" type="submit" value="home" onclick="window.location='/';" />
                <input class="btn btn-primary" type="submit" value="Al je groepen" onclick="window.location='/team/all';" />
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
            </p>
            <h1>Overzicht groepen</h1>
            <table>
                <tr><th>Groep</th><th>Deelnemer</th><th></th></tr>
                <c:forEach items="${teamList}" var="team">
                    <tr>
                        <td><a href="/team/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a></td>
                        <td>
                        <c:forEach items="${team.allMembersInThisTeamSet}" var="member">
                            <a href="/team/select/<c:out value="${member.memberName}" />"><c:out value="${member.memberName}" /></a><br />
                        </c:forEach>
                        </td>
                        <td><input class="btn btn-primary" type="submit" value="Verwijder groep" onclick="window.location='/team/delete/${team.teamId}'" /></td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input class="btn btn-primary" type="submit" value="Voeg groep toe" onclick="window.location='/team/new';" />
            </p>
        </div>
    <body>
</html>