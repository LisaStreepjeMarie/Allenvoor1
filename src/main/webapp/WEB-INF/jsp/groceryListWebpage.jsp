<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset='utf-8' />
    <title>Boodschappenlijst</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<style>
    .groceryList {
  padding-top: 50px;
  padding-right: 50px;
  padding-bottom: 50px;
  padding-left: 50px;;
</style>
</head>
<body class= "webpage">
<mytags:navbar/>
<div class= "masthead">
    <div class= "row">
        <div class= "col-2">
            <div class="ml-3 mt-3">

                <c:forEach items="${teamList}" var="team">
                    <br>
                    <div id="card-${team.teamId}" onclick="$('#collapsed-chevron').toggleClass('fa-rotate-90')" class="card list-group-item-action">
                        <div id="card-header" class="card-header" aria-expanded="false" data-toggle="collapse" data-target="#${team.teamName}">
                            <i class="icon-action fa fa-chevron-right" id="collapsed-chevron"></i>
                            <span class="title">&emsp;${team.teamName}</span>
                        </div>
                    </div>
                    <div id=${team.teamName} class="collapse">
                        <div class="card card-body">
                            <tr>
                                <a class="dropdown-item" href='${pageContext.request.contextPath}/calendar/${team.teamId}'> <i class="fa fa-calendar" ></i>&emsp;Kalender</a>
                            </tr>
                            <tr>
                                <a class="dropdown-item" href='${pageContext.request.contextPath}/medication/${team.teamId}'><i class=" fa fa-medkit"></i>&emsp;Medicatie</a>
                            </tr>
                            <tr>
                                <a class="dropdown-item" href='${pageContext.request.contextPath}/grocerylist/${team.teamId}'><i class="fa fa-shopping-basket"></i>&emsp;Boodschappenlijst</a>
                            </tr>
                            <tr>
                                <a class="dropdown-item" href='${pageContext.request.contextPath}/chat/${team.teamId}'><i class="fas fa-comments"></i>&emsp;Chat</a>
                            </tr>
                        </div>
                    </div>

                </c:forEach>
            </div>

        </div>
        <br />
       <div id="container" class="groceryList">
            <jsp:include page="groceryList.jsp" />

    </div>
</div>
</div>
</body>
</html>

