<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

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
                    <table>
                        <tr>
                            <td colspan="2"><h3 class="font-weight-light">Groepen waar je groepsbeheerder bent: </h3></td>
                        </tr>
                        <tr>
                            <td><c:if test="${empty adminTeamList}">Je bent van geen enkele groep beheerder</c:if></td>
                        </tr>
                        <c:forEach items="${adminTeamList}" var="team">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/team/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a>
                                </td>
                                <td>
                                    <c:forEach items="${team.teamMemberships}" var="membership">
                                        <c:out value="${membership.member.memberName}"  /><br />
                                    </c:forEach>
                                </td>
                                <td><input class="btn btn-primary" type="submit" value="Schrijf jezelf uit" onclick="window.location='${pageContext.request.contextPath}/team/quit/${membership.membershipId}'" /></td>
                                <td><input class="btn btn-primary" type="submit" value="Stop beheerderschap" onclick="window.location='${pageContext.request.contextPath}/team/toggleadmin/${team.teamId}'" /></td>
                                <td><input class="btn btn-primary" type="submit" value="Verwijder groep" onclick="window.location='${pageContext.request.contextPath}/team/delete/${team.teamId}'" /></td>

                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2"><h3 class="font-weight-light">Groepen waar je lid bent: </h3></td>
                        </tr>
                        <tr>
                            <td><c:if test="${empty memberTeamList}">Je bent van geen enkele groep lid</c:if></td>
                        </tr>
                        <c:forEach items="${memberTeamList}" var="team">
                            <tr>
                                <td>
                                    <c:out value="${team.teamName}" />
                                </td>
                                <td>
                                    <c:forEach items="${team.teamMemberships}" var="membership">
                                        <c:out value="${membership.member.memberName}" /><br />
                                    </c:forEach>
                                </td>
                                <td><input class="btn btn-primary" type="submit" value="Schrijf jezelf uit" onclick="window.location='${pageContext.request.contextPath}/team/quit/${team.teamId}'" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <br>
                <div class="ml-3 mt-3">
                    <h3 class="font-weight-light">Word beheerder van nieuwe groep:</h3>
                    <p><input class="btn btn-primary" type="submit" value="Creëer nieuwe groep" onclick="window.location='${pageContext.request.contextPath}/team/new';" /></p>
                    <br>
                </div>
                <div class="ml-3 mt-3">
                    <h3 class="font-weight-light">Word lid van een bestaande groep:</h3>
                    <input type="text">
                    <input class="btn btn-primary" type="submit" value="Meld je aan bij deze groep">
                </div>
            </div>
        </div>
    <body>
</html>