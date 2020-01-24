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
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
<body class="text-center">
<div class="container">
    <form class="form-signin" method="post" action="${pageContext.request.contextPath}/logout">
        <h2 class="form-signin-heading">Weet je zeker dat je uit wil loggen ?</h2>
        <input name="_csrf" type="hidden" value="${_csrf.token}" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Uitloggen</button>
    </form>
</div>
</body>
</html>