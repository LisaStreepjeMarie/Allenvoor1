<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <title>Maak een nieuwe gebruiker</title>

        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>

    </head>
    <body class="webpage">
        <div class="container">
            <form class="form-signin" method="post" action="/member/new">
                <img class="mb-4" src="https://upload.wikimedia.org/wikipedia/commons/a/a4/All_for_One_Midmarket_logo.svg" alt="" width="300" height="50">
                <h1 class="h3 mb-3 font-weight-normal">Nieuw? Meld je aan:</h1>

        <form:form action="/member/new" modelAttribute="member">
            <table>
                <tr>
                    <td>
                        <form:input path="membername" class="form-control" placeholder= "Gebruikersnaam" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:input path="password" class="form-control" placeholder= "Wachtwoord"/>
                        </br>
                    </td>
                </tr>


                <tr>
                    <td colspan="2">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Registreer</button>
                    </td>
                </tr>
            </table>
        </form:form>
        <table>
            <tr>
                <td>
                    <form action="/logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Inloggen</button>
                        <p class="mt-5 mb-3 text-muted">&copy; 2019 Allenvooreen</p>
                   </form>
               </td>
          </tr>
      </table>
    </body>
</html>



