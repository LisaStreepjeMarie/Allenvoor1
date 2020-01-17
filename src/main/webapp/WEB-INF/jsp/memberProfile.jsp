
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

        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.slim.min.js"></script>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script
        <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.0.6/web-fonts-with-css/css/fontawesome-all.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/font-awesome/5.0.6/on-server/js/fontawesome-all.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/font-awesome/5.0.6/on-server/js/fa-solid.min.js"></script>
        <link href="/webjars/font-awesome/5.0.6/web-fonts-with-css/css/fontawesome-all.min.css" rel='stylesheet'>
        <script src="/webjars/font-awesome/5.0.6/on-server/js/fontawesome-all.min.js"></script>
        <script src="/webjars/font-awesome/5.0.6/on-server/js/fa-solid.min.js"></script>
        </head>

     <body class= "webpage">
      <nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
            <a class="navbar-brand"</a>
            <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="230" height="178"> </a>
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
                  <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
                </li>
              </ul>
            </div>
        </nav>
        <title>Overzicht gebruiker</title>
                <div id="container">
                <div class="mt-3 col-12">
                    <h1 class="font-weight-light">Hallo ${currentmember.memberName} !</h1>
                    <h5 class="font-weight-light">Wijzig hieronder je gebruikersnaam of verwijder je profiel</h5>
                </div>
                <div class="mt-3 col-12 form-inline toolbox-top clearfix">
                    <form:form action="${pageContext.request.contextPath}/member/change" modelAttribute="currentmember">
                        <form:input path="memberId" type="hidden" />
                        <table>
                            <tr>
                                <td>
                                    <h7 class= "font-weight-light">Gebruikersnaam:
                                        <form:input path="memberName" value="${members.memberName}" /></form> </h7>
                                    <input type="submit" class= "btn btn-primary" value="Wijzigen" />
                                </td>
                            </tr>
                            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                <strong>Let op!</strong> Bij wijziging van je gebruikersnaam is opnieuw inloggen vereist.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </table>
                    </form:form>
                    <form:form action="${pageContext.request.contextPath}/member/delete" method="get" modelAttribute="currentmember">
                        <form:input path="memberId" type="hidden" />
                        <table>
                            <tr>
                                <td>
                                    <input type="submit" class= "btn btn-primary" value="Verwijder je profiel" />
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
          </div>
       </div>
    </div>
  </body>
</html>