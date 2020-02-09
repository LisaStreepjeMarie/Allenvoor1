<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns:c="" xmlns:mytags="">
    <head>
        <title>Overzicht groepen</title>
        <!-- Add icon library -->
        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/popper.js/1.16.0/popper.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    </head>

    <body class= "webpage">
        <mytags:navbar/>
        <div class= "masthead">
            <div id="container">
                <br>
                <div class="ml-3 mt-3">
                    <br>
                    <div class="ml-3 mt-3">
                        <h3 class="font-weight-light">Jouw groepen:</h3>
                    </div>

                    <div class="ml-3 mt-3">
                        <div class="w-50 p-3" id="accordion">
                            <c:forEach items="${adminTeamList}" var="team">
                                <div class="card">
                                    <div class="card-header"  aria-expanded="false">
                                        <a class="card-link" data-toggle="collapse" href="#collapse<c:out value="${team.teamId}" />">
                                        <c:out value="${team.teamName}" />
                                        &emsp;&emsp;<span class="badge badge-secondary">Groepsbeheerder</span>
                                        </a>
                                    </div>
                                    <div id="collapse<c:out value="${team.teamId}" />" class="collapse" data-parent="#accordion">
                                    <div class="card-body">
                                        <label>Teamleden:</label>
                                        <ul class="list-group">
                                            <c:forEach items="${team.teamMemberships}" var="membership">
                                                <li class="list-group-item list-group-item-action"><c:out value="${membership.member.memberName}" />
                                                    <c:if test="${membership.admin}">
                                                        &emsp;<i class="far fa-star"></i>
                                                    </c:if>
                                            </c:forEach>
                                        </ul>

                                        <br><label>Acties:</label><br>
                                        <td><input class="btn btn-primary" type="submit" value="Schrijf jezelf uit" onclick="window.location='${pageContext.request.contextPath}/team/quit/${team.teamId}'" /></td>
                                        <td><input class="btn btn-primary" type="submit" value="Groepsdetails" onclick="window.location='${pageContext.request.contextPath}/team/select/<c:out value="${team.teamId}" />'" /></td>
                                        <td><input class="btn btn-primary" type="submit" value="Stop beheerderschap" onclick="window.location='${pageContext.request.contextPath}/team/quitadmin/${team.teamId}'" /></td>
                                        <td><input class="btn btn-primary" type="submit" value="Verwijder groep" onclick="window.location='${pageContext.request.contextPath}/team/delete/${team.teamId}'" /></td>
                                    </div>
                                </div>
                        </div>
                        </c:forEach>
                        <div id="accordion">
                            <c:forEach items="${memberTeamList}" var="team">
                                <div class="card">
                                    <div class="card-header">
                                        <a class="card-link" data-toggle="collapse" href="#collapse<c:out value="${team.teamId}" />">
                                        <c:out value="${team.teamName}" />
                                        </a>
                                    </div>
                                    <div id="collapse<c:out value="${team.teamId}" />" class="collapse" data-parent="#accordion">
                                    <div class="card-body">
                                        <label>Teamleden:</label>
                                        <ul class="list-group">
                                        <c:forEach items="${team.teamMemberships}" var="membership">
                                            <li class="list-group-item list-group-item-action"><c:out value="${membership.member.memberName}" />
                                                <c:if test="${membership.admin}">
                                                    &emsp;<i class="far fa-star"></i>
                                                </c:if>
                                        </c:forEach>
                                        </ul>
                                        <br><label>Acties:</label><br>
                                        <td><input class="btn btn-primary" type="submit" value="Schrijf jezelf uit" onclick="window.location='${pageContext.request.contextPath}/team/quit/${team.teamId}'" /></td>
                                    </div>
                                </div>
                        </div>
                        </c:forEach>
                    </div>

                    <br><br>
                </div>
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