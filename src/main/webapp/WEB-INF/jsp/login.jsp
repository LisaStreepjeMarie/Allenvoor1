<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform" xmlns:fmt="" xmlns:spring="" xmlns:beans="">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/docs/4.0/assets/img/favicons/favicon.ico">

    <title>Login pagina</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>

<body class="text-center">
<div class="container">
    <form class="form-signin" method="post" action="/login">
        <img class="mb-4" src="https://upload.wikimedia.org/wikipedia/commons/a/a4/All_for_One_Midmarket_logo.svg" alt="" width="300" height="50">
        <h1 class="h3 mb-3 font-weight-normal">Log in:</h1>

        <c:if test="${param.error}">
        <div class="alert alert-danger" role="alert">
            Foute inloggegevens
        </div>
        </c:if>

        <label for="username" class="sr-only">Gebruikersnaam</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Gebruikersnaam" required autofocus>
        <label for="password" class="sr-only">Wachtwoord</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Wachtwoord" required>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me">Onthoud mij
            </label>
        </div>
        <input name="_csrf" type="hidden" value="${_csrf.token}" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>

    <form class="form-signin" action="/member/new">
        <h1 class="h3 mb-3 font-weight-normal">Of registreer:</h1>
        <input name="_csrf" type="hidden" value="${_csrf.token}" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Registreer</button>
        <p class="mt-5 mb-3 text-muted">&copy; 2019 Allenvooreen</p>
    </form>
</div >

</body>
</html>