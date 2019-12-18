<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Voeg een groep toe</title>
    </head>
    <body class="webpage">
        <div id="content">
            <p>
                <form:form action="/logout" method="post">
                   <input type="submit" value="Logout" />
               </form:form>
            </p>
            <h1>Voeg gegevens groep toe</h1>
            <form:form action="/team/new" modelAttribute="team">
                <table>
                    <tr>
                        <td>Groepsnaam:</td>
                        <td>
                            <form:input path="teamName" value="" /></form> <input type="submit" value="Sla groep  op" />
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>