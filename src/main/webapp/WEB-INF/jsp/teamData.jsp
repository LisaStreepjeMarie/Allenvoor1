<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Wijzig de gegevens van een groep</title>
    </head>
    <body>
        <p><a href="/logout">Log uit</a></p>
        <h1>Wijzig gegevens groep</h1>
        <form:form action="/team/new" modelAttribute="team">
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
                        <input type="submit" value="Sla gewijzigde groep  op" />
                    </td>
                </tr>
            </table>
        </form:form>
    </body>
</html>