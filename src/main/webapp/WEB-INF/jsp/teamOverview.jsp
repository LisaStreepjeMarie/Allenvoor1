<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="" xmlns:mytags="">
    <head>
        <title>Overzicht groepen</title>
        <!-- Add icon library -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    </head>

    <body class= "webpage">
        <mytags:navbar/>
        <div class= "masthead">
            <div id="container">
                <br />
                <div class="ml-3 mt-3">
                    <h3 class="font-weight-light">Mijn groepen</h3><br>
                    <table>
                        <tr>
                            <td><h5 class="font-weight-light">Groep</h5></td>
                            <td colspan="2"><h5 class="font-weight-light">Groepslid</h5></td>
                        </tr>
                        <c:forEach items="${teamList}" var="team">
                            <tr>
                                <td><a href="${pageContext.request.contextPath}/team/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a></td>
                                <td>
                                <c:forEach items="${team.teamMemberships}" var="membership">
                                    <c:out value="${membership.member.memberName}" /><br />
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
            </div>
        </div>
    <body>
</html>