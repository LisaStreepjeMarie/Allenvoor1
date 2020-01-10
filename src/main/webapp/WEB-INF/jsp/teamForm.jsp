<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <link href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Voeg een groep toe</title>
    </head>
    <body class="webpage">
        <div id="container">
            <p>
                <input class="btn btn-primary" type="submit" value="home" onclick="window.location='/';" />
                <input class="btn btn-primary" type="submit" value="Al je groepen" onclick="window.location='/team/all';" />
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
            </p>
            <h1>Voeg gegevens groep toe</h1>
            <form:form action="/team/new" modelAttribute="team">
                <table>
                    <tr>
                        <td>Groepsnaam:</td>
                        <td>
                            <form:input path="teamName" value="" /></form>
                        </td>
                    </tr>
                    <tr>
                        <td>Groepslid:</td>
                        <td>
                            <form:input path="membername" value="${members.membername}" /></form>
                        </td>
                    </tr>
                    <tr><td colspan="2"><input class="btn btn-primary" type="submit" value="Bewaar" /></td></tr>
                </table>
            </form:form>
        </div>
    </body>
</html>