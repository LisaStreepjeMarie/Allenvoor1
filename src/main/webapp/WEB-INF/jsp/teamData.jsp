<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
            integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="../../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Wijzig de gegevens van een groep</title>
    </head>
    <body class="webpage">
        <div id="container">
            <p>
                <input class="btn btn-primary" type="submit" value="home" onclick="window.location='/';" />
                <input class="btn btn-primary" type="submit" value="Al je groepen" onclick="window.location='/team/all';" />
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
            </p>
            <h1>Wijzig gegevens groep</h1>
            <form:form action="/team/new" modelAttribute="team">
            <form:input path="teamId" type="hidden" />
                <table>
                    <tr>
                        <td>Groepsnaam:</td>
                        <td>
                            <form:input size="30" path="teamName" value="${team.teamName}" /></form>
                        </td>
                    </tr>
                    <tr>
                        <td>Groepslid:</td>
                        <td>
                            <c:forEach items="${team.membername}" var="member">
                                <a href="/team/select/<c:out value="${member.membername}" />"><c:out value="${member.membername}" /></a><br />
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>Nieuw groepslid:</td>
                        <td>
                           <form:input path="membername" /></form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                             <input class="btn btn-primary" type="submit" value="Wijzig" />
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>