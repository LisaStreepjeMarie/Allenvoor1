<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Maak een nieuwe gebruiker</title>

         <!-- Bootstrap core CSS -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
            <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
            <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
        <h4>Registeren</h4>

        <form:form action="/member/new" modelAttribute="member">
            <table>
                <tr>
                    <td>Naam:</td>
                    <td>
                        <div class="alert alert-success" role="alert">
                        <form:input path="membername" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Wachtwoord:</td>
                    <td>
                        <div class="alert alert-success" role="alert">
                        <form:input path="password" />
                        </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="2">
                        <input type="submit" value="Registreer" />
                    </td>
                </tr>
            </table>
            <tr>
             <td colspan="2">
        </form:form>
</html>


