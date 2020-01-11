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

    <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
<body>
<div class="container" >
    <div class="row" >
           <div class="col-12">&nbsp;</div>
    </div>
    <div class="row">
       <div class="col-2">
            <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" /><br />
            <br>
            <input class="btn btn-primary" type="submit" value="Gebruikersprofiel" onclick="window.location='/member/current';" /><br />
           <br>
           <input class="btn btn-primary" type="submit" value="Toon groepen" onclick="window.location='/team/all';" />
           <br>
           <br>
           <br>
            <h4>Mijn groepen: </h4>
                   <table>
                       <c:forEach items="${teamList}" var="team">
                           <tr>
                               <button type="button" class="btn btn-info"
                               onclick="window.location='/calendar/${team.teamId}';">${team.teamName}</button> <br>
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