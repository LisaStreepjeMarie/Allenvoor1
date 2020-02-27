<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html xmlns:c="" xmlns:mytags="">
<head>
    <title>Kalender</title>
    <meta charset='utf-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">


    <!-- Add icon library -->
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>


    <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
</head>
<body class= "webpage">
<mytags:navbar/>
<div class= "masthead">
    <div class= "row">
        <div class= "col-2">
            <div class="ml-3 mt-3">
        <c:forEach items="${teamList}" var="team">
            <br>
            <div id="card-${team.teamId}" onclick="$('#collapsed-chevron-${team.teamId}').toggleClass('fa-rotate-90')" class="card list-group-item-action">
                <div id="card-header" class="card-header" aria-expanded="false" data-toggle="collapse" data-target="#id-${team.teamId}">
                    <i class="icon-action fa fa-chevron-right" id="collapsed-chevron-${team.teamId}"></i>
                    <span class="title">&emsp;${team.teamName}</span>
                </div>
            </div>
            <div id="id-${team.teamId}" class="collapse">
                <div class="card card-body">
                    <tr>
                        <a class="dropdown-item" href='${pageContext.request.contextPath}/calendar/${team.teamId}'> <i class="fa fa-calendar" ></i>&emsp;Kalender  </a>
                    </tr>
                    <tr>
                        <a class="dropdown-item" href='${pageContext.request.contextPath}/medication/${team.teamId}'><i class=" fa fa-medkit"></i>&emsp;Medicatie  </a>
                    </tr>
                    <tr>
                        <a class="dropdown-item" href='${pageContext.request.contextPath}/grocerylist/${team.teamId}'><i class="fa fa-shopping-basket"></i>&emsp;Boodschappenlijst  </a>
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
        <br />
        <br />
        <br>
        <div class="col-8">
            <br />
            <br />
            <div id="calendar" style="width: 45rem;"></div>
            <div id='datepicker'></div>
        </div>

    </div>
</div>
</body>
</html>