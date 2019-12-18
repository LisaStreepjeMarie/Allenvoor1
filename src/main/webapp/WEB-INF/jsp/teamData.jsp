<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Wijzig de gegevens van een groep</title>
    </head>
    <body class="webpage">
        <div id="content">
            <p>
                <form:form action="/logout" method="post">
                   <input type="submit" value="Logout" />
               </form:form>
            </p>
            <h1>Wijzig gegevens groep</h1>
            <form:form action="/team/change" modelAttribute="team">
            <form:input path="teamId" type="hidden" />
                <table>
                    <tr>
                        <td>Groepsnaam:</td>
                        <td>
                        <form:input path="teamName" value="${team.teamName}" />
                        <input type="submit" value="Sla gewijzigde groep  op" /></form>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>