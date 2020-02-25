<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns:c="" xmlns:mytags="" xmlns:security="">
<head>
    <title>Overzicht groepen</title>
    <!-- Add icon library -->
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/popper.js/1.16.0/popper.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

    <!-- Setup variables which can be read from external javascript file -->
    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <security:authorize access="isAuthenticated()">
        <c:set var="principalUsername">
            <security:authentication property="principal.username" />
        </c:set>
        <link id="principalUsername" data-principalUsername="${principalUsername}"/>
    </security:authorize>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/teamOverview.js"></script>

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
                        <div id="card-${team.teamId}" class="card list-group-item-action">
                            <div class="card-header" onclick="$('#collapsed-chevron-${team.teamId}').toggleClass('fa-rotate-90')" aria-expanded="false" data-toggle="collapse" data-target="#collapse-${team.teamId}">
                                <i class="icon-action fa fa-chevron-right" id="collapsed-chevron-${team.teamId}"></i>
                                <span class="title">&emsp;${team.teamName}&emsp;</span>
                                <span class="badge badge-secondary">Groepsbeheerder</span>
                            </div>
                            <div id="collapse-${team.teamId}" class="collapse" data-parent="#accordion">
                                <div class="card-body">
                                    <label>Teamleden:</label>
                                    <ul class="list-group">
                                        <c:forEach items="${team.teamMemberships}" var="membership">
                                            <li class="list-group-item list-group-item-action">${membership.member.memberName}
                                                <c:if test="${membership.admin}">
                                                    &emsp;<i id="star-${membership.membershipId}" class="far fa-star"></i>
                                                </c:if>
                                            </li>
                                            <c:if test = "${principalUsername == membership.member.memberName}">
                                                <c:set var="principalMembership" value="${membership}">
                                                </c:set>
                                            </c:if>
                                        </c:forEach>
                                    </ul>

                                    <br><label>Acties:</label><br>
                                    <td><input class="btn btn-primary" type="submit" value="Schrijf jezelf uit" onclick="quitTeam(${principalMembership.membershipId}, '${principalMembership.member.memberName}', ${principalMembership.team.teamId}, false, false)" /></td>
                                    <td><input class="btn btn-primary" id="teamDetailsButton" type="submit" value="Groepsdetails" onclick="window.location='${pageContext.request.contextPath}/team/select/${team.teamId}'" /></td>
                                    <td><input class="btn btn-primary" id="quitAdminButton"  type="submit" value="Stop beheerderschap" onclick="quitAdmin(${principalMembership.membershipId}, '${principalMembership.member.memberName}', ${principalMembership.team.teamId}, false)" /></td>
                                    <td><input class="btn btn-primary" id="deleteTeamButton"  type="submit" value="Verwijder groep" onclick="deleteTeam(${team.teamId}, false)" /></td>
                                </div>
                            </div>
                        </div>
                </c:forEach>
                <div id="accordion">
                    <c:forEach items="${memberTeamList}" var="team">
                        <div class="card list-group-item-action">
                            <div class="card-header" onclick="$('#collapsed-chevron-${team.teamId}').toggleClass('fa-rotate-90')" data-toggle="collapse" data-target="#collapse-${team.teamId}">
                                <i class="icon-action fa fa-chevron-right" id="collapsed-chevron-${team.teamId}"></i>
                                <span class="title">&emsp;${team.teamName}&emsp;</span>
                            </div>
                            <div id="collapse-${team.teamId}" class="collapse" data-parent="#accordion">
                            <div class="card-body">
                                <label>Teamleden:</label>
                                <ul class="list-group">
                                    <c:forEach items="${team.teamMemberships}" var="membership">
                                        <li class="list-group-item list-group-item-action">${membership.member.memberName}
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
            <div class="modal fade" id="errorModal" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header" id="errorModalHeader">
                            <!-- Error header message is filled by the error clause in getEventSubscriptions() in the events.js -->
                        </div>
                        <div class="modal-body" id="errorModalBody">
                            <!-- Error body message is filled by the error clause in getEventSubscriptions() in the events.js -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Sluiten</button>
                        </div>
                    </div>
                </div>
            </div>

            <br><br>
        </div>
        <h3 class="font-weight-light">Word beheerder van nieuwe groep:</h3>
        <p><input class="btn btn-primary" type="submit" value="Creëer nieuwe groep" onclick="window.location='${pageContext.request.contextPath}/team/new';" /></p>
        <br>
        </div>
    </div>
</div>
<body>
</html>