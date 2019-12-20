<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Wijzig je gegevens gebruiker</title>
    </head>
    <body class="webpage">
        <div id="container">
            <p>
                <form:form action="/logout" method="post">
                   <input type="submit" value="Logout" />
               </form:form>
            </p>
            <h1>Wijzig je gegevens:</h1>
            <form:form action="/member/change" modelAttribute="member">
            <form:input path="memberId" type="hidden" />
                <table>
                    <tr>
                        <td>Gebruikersnaam:</td>
                        <td>
                        <form:input path="membername" value="${members.membername}" /></form>
                        <input type="submit" value="Sla gewijzigde gebruiker  op" />
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>


