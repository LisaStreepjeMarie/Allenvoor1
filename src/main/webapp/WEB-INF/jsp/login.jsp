<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform" xmlns:fmt="" xmlns:spring="" xmlns:beans="">
    <head>
        <title>Allenvooréén - Login pagina</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />

        <!-- Bootstrap core CSS -->
        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.slim.min.js"></script>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>

    <body class="text-center">
    <div class="container">
        <form class="form-signin" method="post" action="${pageContext.request.contextPath}/login">
            <img class="mb-4" src="${pageContext.request.contextPath}/images/LogoAllenVoorEen.png" alt="" width="300" height="228">


            <c:if test="${param.error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Foute inloggegevens</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>

            <label for="username" class="sr-only">Gebruikersnaam</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Gebruikersnaam" required autofocus>
            <br>
            <label for="password" class="sr-only">Wachtwoord</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Wachtwoord" required>
            <br/><br/>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/member/resetPassword">Wachtwoord vergeten?</a>
            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" value="remember-me">Onthoud mij
                </label>
            </div>
            <input name="_csrf" type="hidden" value="${_csrf.token}" />
            <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
        </form>
        <form class="form-signin" action="${pageContext.request.contextPath}/member/new">
            <input name="_csrf" type="hidden" value="${_csrf.token}" />
            <button class="btn btn-lg btn-primary btn-block" type="submit">Maak een account</button>
            </form>

            <p class="mt-5 mb-3 text-muted">&copy; 2019 Allenvooreen</p>
        </form>
      </div>
    </body>
</html>



























