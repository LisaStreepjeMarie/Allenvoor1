<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <title>Maak een nieuwe gebruiker aan</title>
        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

        <!-- Custom styles for this template -->
        <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="text-center">
        <div class="container">
            <form class="form-signin" method="post" action="${pageContext.request.contextPath}/member/new">
            <img class="mb-4" src="${pageContext.request.contextPath}/images/LogoAllenVoorEen.png" alt="" width="300" height="228">
                <h1 class="h3 mb-3 font-weight-normal">Nieuw? Meld je aan:</h1>

        <form:form action="${pageContext.request.contextPath}/member/new" modelAttribute="member">
            <spring:bind path="memberName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="memberName" class="form-control" placeholder="Gebruikersnaam"
                                autofocus="true"></form:input>
                    <form:errors path="memberName"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="password" class="form-control" placeholder="Wachtwoord"></form:input>
                    <form:errors path="password"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="passwordConfirm">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="passwordConfirm" class="form-control"
                                placeholder="Bevestig je wachtwoord"></form:input>
                    <form:errors path="passwordConfirm"></form:errors>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Registreer</button>
            </form:form>
            <form action="${pageContext.request.contextPath}/logout" method="post" class="form-signin" >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button class="btn btn-lg btn-primary btn-block" type="submit">Inloggen</button>
                <p class="mt-5 mb-3 text-muted">&copy; 2019 Allenvooreen</p>
            </form>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    </body>
</html>

