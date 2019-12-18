<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Overzicht groepen</title>
    </head>
    <body class="webpage">
            <div id="container">
                <p>
                    <form:form action="/logout" method="post">
                       <input type="submit" value="Logout" />
                   </form:form>
                </p>
                <h1>Overzicht groepen</h1>
                <table>
                    <c:forEach items="${allTeams}" var="team">
                        <tr>
                            <td><a href="/team/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a></td>
                            <td><a href="/team/delete/${team.teamId}">Verwijder groep</a></td>
                        </tr>
                    </c:forEach>
                </table>
                <table>
                    <tr>
                        <td><a href="/team/new">Voeg groep toe</a></td>
                    </tr>
                </table>
            </div>
        </body>
    <body>
</html>