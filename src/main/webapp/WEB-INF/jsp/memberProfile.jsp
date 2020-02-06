<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset='utf-8' />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <title>Kalender</title>

        <!-- Add icon library -->
        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.slim.min.js"></script>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    </head>

    <body class= "webpage">
        <mytags:navbar/>
        <div class= "masthead">
            <div id="container">
                <br />
                <div class="mt-3 col-12">
                    <h3 class="font-weight-light">Hallo ${currentmember.memberName}!</h3><br>
                    <h5 class= "font-weight-light">Wijzig hieronder je gebruikersnaam of verwijder je profiel</h5>
                </div>
                <div class="mt-3 col-12 form-inline toolbox-top clearfix">
                    <form:form action="${pageContext.request.contextPath}/member/change" modelAttribute="currentmember">
                        <form:input path="memberId" type="hidden" />
                        <table>
                            <tr>
                                <td>
                                    <h7 class= "font-weight-light">Gebruikersnaam:</h7>
                                        <form:input path="memberName" value="${members.memberName}" /></form>
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
    </body>
</html>