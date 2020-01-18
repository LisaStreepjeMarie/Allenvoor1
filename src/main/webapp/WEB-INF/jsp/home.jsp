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
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.css" rel='stylesheet'>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
     <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>


    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
        <a class="navbar-brand"</a>
        <br>
        <br>
        <br>
        <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="83" height="64"> </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
               <a class="nav-link" href='${pageContext.request.contextPath}/home'><i class="fa fa-home"></i> Home</a>
                    <span class="sr-only">(current)</span>
                  </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href='${pageContext.request.contextPath}/member/current'><i class="fa fa-user"></i> Mijn profiel</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href='${pageContext.request.contextPath}/team/all'><i class="fa fa-users"></i> Mijn groepen</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
<header class= "masthead">
 <body class= "webpage">
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
       <div class="col-8">
           <div id="calendar" style="width: 45rem;"></div>
           <div id='datepicker'></div>
       </div>
    </div>
</div>
</body>
</html>