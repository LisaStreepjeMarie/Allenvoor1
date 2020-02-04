<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset='utf-8' />
    <title>Boodschappenlijst</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.12.0/css/all.min.css" rel='stylesheet'>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
</head>
</head>

<body class= "webpage">
<mytags:navbar/>
    <div class= "masthead">
            <div id="container">
                <jsp:include page="groceryList.jsp" />
            </div>
    </div>
</body>
</html>

