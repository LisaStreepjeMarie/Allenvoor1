<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <title>Overzicht groepen</title>
        <!-- Add icon library -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="webpage">

        <div id="container" class="ml-4 mt-4">
            <p>
                <input class="btn btn-primary" type="submit" value="Home" onclick="window.location='${pageContext.request.contextPath}/';" />
                <input class="btn btn-primary" type="submit" value="Al je groepen" onclick="window.location='${pageContext.request.contextPath}/team/all';" />
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='${pageContext.request.contextPath}/logout';" />
            </p>
            <h1 class="font-weight-light">Mijn groepen</h1>
            <table>
                <tr>
                    <td><h2 class="font-weight-light">Groep</h2></td>
                    <td colspan="2"><h2 class="font-weight-light">Groepslid</h2></td>
                </tr>
                <c:forEach items="${teamList}" var="team">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/team/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a></td>
                        <td>
                        <c:forEach items="${team.allMembersInThisTeamSet}" var="member">
                            <a href="${pageContext.request.contextPath}/team/select/<c:out value="${member.memberName}" />"><c:out value="${member.memberName}" /></a><br />
                        </c:forEach>
                        </td>
                        <td><input class="btn btn-primary" type="submit" value="Verwijder groep" onclick="window.location='${pageContext.request.contextPath}/team/delete/${team.teamId}'" /></td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input class="btn btn-primary" type="submit" value="Voeg groep toe" onclick="window.location='${pageContext.request.contextPath}/team/new';" />
            </p>
        </div>
    <body>
</html>