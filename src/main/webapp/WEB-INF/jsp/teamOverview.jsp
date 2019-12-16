<html>
    <head>
        <title>Overzicht teams</title>
    </head>
    <body>
        <p><a href="/logout">Log uit</a></p>
        <h1>Overzicht teams</h1>

        <table>
            <c:forEach items="${allTeams}" var="team">
                <tr>
                    <td><a href="/teams/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a></td>
                    <td><a href="/delete/team/${team.teamId}">Verwijder team<a></td>
                </tr>
            </c:forEach>
        </table>
        <table>
            <tr>
                <td><a href="/teams/add">Voeg team toe</a></td>
            </tr>
        </table>
    </body>
</html>