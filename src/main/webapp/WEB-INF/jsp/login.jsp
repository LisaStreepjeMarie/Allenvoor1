<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Log in of registreer</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
</head>
<body>
<div class="container">
    <form class="form-signin" method="post" action="/login">
        <h2 class="form-signin-heading">Log in of registreer:</h2>
        <p>
            <label for="username" class="sr-only">Gebruikersnaam</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Gebruikersnaam" required autofocus>
        </p>
        <p>
            <label for="password" class="sr-only">Wachtwoord</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Wachtwoord" required>
        </p>
        <input name="_csrf" type="hidden" value="${_csrf.token}" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>

</div >

<div class="container">
    <form class="form-signin" action="/member/new">
        <input name="_csrf" type="hidden" value="${_csrf.token}" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Registreer</button>
    </form>
</div >

</body></html>