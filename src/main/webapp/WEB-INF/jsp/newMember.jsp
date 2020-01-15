<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- <c:set var="contextPath" value="${pageContext.request.contextPath}"/> -->

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="utf-8">
        <title>Maak een nieuwe gebruiker</title>

        <!-- waarschijnlijk niet nodig -->
        <!-- <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/common.css" rel="stylesheet"> -->

        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

        <!-- Custom styles for this template -->
        <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
    </head>
    <body class="webpage">
        <div class="container">
            <!-- <form:form method="post" modelAttribute="member" class="form-signin"> -->
            <form class="form-signin" method="post" action="/member/new">
            <img class="mb-4" src="https://upload.wikimedia.org/wikipedia/commons/a/a4/All_for_One_Midmarket_logo.svg" alt="" width="240" height="50">
              <h1 class="h3 mb-3 font-weight-normal">Nieuw? Meld je aan:</h1>
              <form:form action="/member/new" modelAttribute="member">
              <!-- <h2 class="form-signin-heading">Create your account</h2> -->
              <spring:bind path="username">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                      <!-- <label for="username" class="sr-only">Gebruikersnaam</label> -->
                      <form:input type="text" path="membername" class="form-control" placeholder="Gebruikersnaam"
                                  autofocus="true"></form:input>
                      <form:errors path="username"></form:errors>
                  </div>
              </spring:bind>

              <spring:bind path="password">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                      <!-- <label for="password" class="sr-only">Password</label> -->
                      <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
                      <form:errors path="password"></form:errors>
                  </div>
              </spring:bind>

              <spring:bind path="passwordConfirm">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                      <!-- <label for="passwordConfirm" class="sr-only">Bevestig je password</label> -->
                      <form:input type="password" path="passwordConfirm" class="form-control"
                                  placeholder="Confirm your password"></form:input>
                      <form:errors path="passwordConfirm"></form:errors>
                  </div>
              </spring:bind>

              <button class="btn btn-lg btn-primary btn-block" type="submit">Registreer</button>
            </form:form>
            <table>
            <tr>
                <td colspan="2">
                    <form action="/logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Inloggen</button>
                        <p class="mt-5 mb-3 text-muted">&copy; 2019 Allenvooreen</p>
                    </form>
                </td>
            </tr>
            </table>
        </form:form>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    </body>
</html>
