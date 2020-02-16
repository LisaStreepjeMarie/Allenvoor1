<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:spring="">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
        <title>Maak een nieuwe gebruiker aan</title>

        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>

        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet" type="text/css"/>
    </head>

    <body class="text-center">
        <div class="text-center p-5">
            <h1 class="h3 mb-3 font-weight-light"><strong>Wachtwoord vergeten?</strong><br> Voer hier je emailadres in:</h1>
            <form class="form-signin" method="post" action="${pageContext.request.contextPath}/member/resetPassword">


                <form:form action="${pageContext.request.contextPath}/member/resetPassword" modelAttribute="member">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                   <form:input type="email" name= "email" class="form-control" path= "email" placeholder="Email"></form:input>
                   <form:errors path="email"></form:errors>
               </div>



            <button class="btn btn-lg btn-primary btn-block" type="submit">Stuur email</button>
            </form:form>

            <form action="${pageContext.request.contextPath}/logout" method="post" class="form-signin" >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button class="btn btn-lg btn-primary btn-block" type="submit">Terug naar inloggen</button>
                <p class="mt-5 mb-3 text-muted">&copy; 2020 Allenvooreen</p>
            </form>
    </body>
</html>


















































