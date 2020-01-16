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

    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
     <link href="../css/style.css" rel="stylesheet" type="text/css"/>

<body class="webpage">
<div class="container-fluid" >
<nav class="navbar navbar-expand-lg navbar-dark bg-secondary">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="#">
   <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="230" height="178"> </a>


  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      <li class="nav-item active">
        <a class="nav-link" href="home">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href='${pageContext.request.contextPath}/member/current'>Mijn profiel</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href='${pageContext.request.contextPath}/team/all'>Mijn groepen</a>
       </li>
      <li class="nav-item">
        <a class="nav-link" href='${pageContext.request.contextPath}/logout' >Logout</a>
      </li>
    </ul>
  </div>
</nav>
    <div class="row" >
           <div class="col-12">&nbsp;</div>
    </div>
    <div class="row">
       <div class="col-2">
            <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='${pageContext.request.contextPath}/logout';" /><br />
            <br>
            <input class="btn btn-primary" type="submit" value="Gebruikersprofiel" onclick="window.location='${pageContext.request.contextPath}/member/current';" /><br />
           <br>
           <input class="btn btn-primary" type="submit" value="Toon groepen" onclick="window.location='${pageContext.request.contextPath}/team/all';" />
           <br>
           <br>

            <h5>Mijn groepen: </h5>
                   <table>
                       <c:forEach items="${teamList}" var="team">
                           <tr>
                               <button type="button" class="btn btn-primary"
                               onclick="window.location='/calendar/${team.teamId}';">${team.teamName}</button> <br>
                               <button type="button" class="btn btn-info"
                               onclick="window.location='${pageContext.request.contextPath}/calendar/${team.teamId}';">${team.teamName}</button> <br>
                               <br>
                           </tr>
                       </c:forEach>
                   </table>
            <h5> Medicatie: </h5>
             <table>
               <c:forEach items="${teamList}" var="team">
                      <tr>
                         <button type="button" class="btn btn-primary"
                          onclick="window.location='/medication/${team.teamId}';"> Toon medicatie ${team.teamName}</button> <br>
                         <button type="button" class="btn btn-info"
                          onclick="window.location='${pageContext.request.contextPath}/medication/${team.teamId}';"> Toon medicatie ${team.teamName}</button> <br>
                           <br>
                          </tr>
                    </c:forEach>
               </table>
       </div>
       <div class="col-10">
           <div id="calendar" style="width: 45rem;"></div>
           <div id='datepicker'></div>
           <c:if test="${not empty team.teamName}">
               <div class="badge badge-primary text-wrap" style="width: 45rem; height: 4rem;">
                   <p>
                   <h5>Agenda van de groep: ${team.teamName}</h5>
                   </p>
               </div>

           </c:if>
       </div>
    </div>
</div>
</body>
</html>
