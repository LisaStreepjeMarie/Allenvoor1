<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
    <head>
        <title>Kalender</title>
        <meta charset='utf-8' />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">

        <!-- Add icon library -->
        <link href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    </head>

    <body class= "webpage">
        <mytags:navbar/>
        <div class= "masthead">
            <div class= "row">
                <div class= "col-2">
                    <br>
                    <h1 class="dropdown-header"><strong>Welkom </h1>
                    <br />
                    <h2 class="dropdown-header"><i class="fa fa-calendar-minus-o" aria-hidden="true"></i> Kalender </h2>
                    <c:forEach items="${teamList}" var="team">
                      <tr>
                         <a class="dropdown-item" href='${pageContext.request.contextPath}/calendar/${team.teamId}'> Kalender ${team.teamName} </a>
                      </tr>
                    </c:forEach>
                    <h2 class="dropdown-header"><i class=" fa fa-medkit"></i> Medicatie </h2>
                    <c:forEach items="${teamList}" var="team">
                       <tr>
                          <a class="dropdown-item" href='${pageContext.request.contextPath}/medication/${team.teamId}'> Medicatie ${team.teamName} </a>
                       </tr>
                    </c:forEach>
                </div>
                <br />
                <br>
                <div class="col-8">
                   <div id="calendar" style="width: 45rem;"></div>
                   <div id='datepicker'></div>
                </div>
            </div>
        </div>
    </body>
</html>