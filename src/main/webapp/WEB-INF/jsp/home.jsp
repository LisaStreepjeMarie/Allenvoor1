<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
<head>
    <meta charset='utf-8' />
    <title>Kalender</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <!-- Add icon library -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <link href="../css/style.css" rel="stylesheet" type="text/css"/>

<body class="webpage">
<div class="container-fluid" >
<nav class="navbar navbar-expand-lg navbar-dark bg-secondary">
  <a class="navbar-brand" href="#">
   <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="230" height="178"> </a>
 <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
        <a class="nav-link" href='${pageContext.request.contextPath}/home'><i class="fa fa-home"></i> Home</a>
        <a class="nav-link" href='${pageContext.request.contextPath}/member/current'><i class="fa fa-user"></i> Mijn profiel</a>
        <a class="nav-link" href='${pageContext.request.contextPath}/team/all'><i class="fa fa-users"></i> Mijn groepen</a>
        <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
   </ul>
</nav>
<br>
<div class= "row">
    <div class= "col-2">
  <h2 class="dropdown-header"><strong>Welkom </strong></h2>
    <h2 class="dropdown-header"><strong>Kalender</strong> </h2>
       <c:forEach items="${teamList}" var="team">
          <tr>
             <a class="dropdown-header" href='${pageContext.request.contextPath}/calendar/${team.teamId}'> Kalender ${team.teamName} </a>
           </c:forEach>
          </tr>
           <h2 class="dropdown-header"><strong> Medicatie </strong></h2>
           <c:forEach items="${teamList}" var="team">
              <tr>
                 <a class="dropdown-header" href='${pageContext.request.contextPath}/medication/${team.teamId}'> Medicatie ${team.teamName} </a>
               </c:forEach>
              </tr>
       </div>
       <br>
       <div class="col-10">
           <div id="calendar" style="width: 45rem;"></div>
           <div id='datepicker'></div>
       </div>
    </div>
</div>
</body>
</html>
