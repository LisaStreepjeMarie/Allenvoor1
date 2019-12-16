<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <title>VOeg toe of wijzig de gegevens van het team</title>
    </head>
    <body>
        <p><a href="/logout">Log uit</a></p>
        <h1>Voeg toe of wijzig gegevens team</h1>

        <form:form action="/teams/add" modelAttribute="team">
            <form:input path="teamId" type="hidden" />
            <table>
                <tr>
                    <td>Groepsnaam:</td>
                    <td>
                        <form:input path="teamName" value="${team.teamName}" /></form>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Sla (gewijzigd) team  op" />
                    </td>
                </tr>
            </table>
        </form:form>
    </body>
</html>