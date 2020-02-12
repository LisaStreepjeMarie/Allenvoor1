<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:mytags="" xmlns:c="">
<head>
    <title>Kalender</title>
    <meta charset='utf-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <!-- Add icon library -->
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/events.js"></script>

    <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
</head>
<body class= "webpage">
<mytags:navbar/>
<div class= "masthead">
    <div class="w-25 p-3">
        <ul class="list-group">
            <li  class="list-group-item list-group-item-dark">Ingeschreven gebruikers:</li>
            <c:forEach items="${eventSubscriptionSet}" var="subscription">
                <td><li class="list-group-item list-group-item-action">${subscription.member.memberName}</li></td>
                <c:set var="eventId" value="${subscription.event.eventId}"/>
            </c:forEach>
        </ul>
        <input class="btn btn-primary" type="submit" value="Schrijf je in" onclick="subscribeEvent(${eventId})">
    </div>
</div>

</body>
</html>