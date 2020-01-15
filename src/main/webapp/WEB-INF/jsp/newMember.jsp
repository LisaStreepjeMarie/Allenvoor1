<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <title>Maak een nieuwe gebruiker</title>
        <!-- Bootstrap core CSS -->
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

                <!-- Custom styles for this template -->
                <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
            </head>
    </head>
    <body class="webpage">
        <div class="container">
            <form class="form-signin" method="post" action="${pageContext.request.contextPath}/member/new">
             <img class="mb-4" src="https://upload.wikimedia.org/wikipedia/commons/a/a4/All_for_One_Midmarket_logo.svg" alt="" width="240" height="50">
                <h1 class="h3 mb-3 font-weight-normal">Nieuw? Meld je aan:</h1>

        <form:form action="${pageContext.request.contextPath}/member/new" modelAttribute="member">
            <table>
                <tr>
                    <td>
                        <form:input path="memberName" class="form-control" placeholder= "Gebruikersnaam" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:input path="password" class="form-control" placeholder= "Wachtwoord"/>
                        </br>
                     </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Registreer</button>
                    </td>
                </tr>
           </form:form>
           <tr>
            <td colspan="2">
        <form action="${pageContext.request.contextPath}/logout" method="post">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
              <button class="btn btn-lg btn-primary btn-block" type="submit">Inloggen</button>
                <p class="mt-5 mb-3 text-muted">&copy; 2019 Allenvooreen</p>
                </td>
               </tr>
            </form>
          </table>
    </body>
</html>



