<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
                        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
            <link href="../css/style.css" rel="stylesheet" type="text/css"/>
             <title>Maak een nieuwe gebruiker</title>
    </head>
    <body class="webpage">
        <div id="container">
        <h1>Registeren</h1>
         <form:form action="/member/new" modelAttribute="member">
            <table>
                <tr>
                    <td>Naam:</td>
                    <td>
                        <form:input path="membername" />
                    </td>
                </tr>
                <tr>
                    <td>Wachtwoord:</td>
                    <td>
                        <form:input path="password" />
                    </td>
                </tr>

                <tr>
                    <td colspan="2">
                        <input type="submit" value="Registreer" />
                    </td>
                </tr>
            </table>
        </form:form>
        <form action="/logout" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="submit" value="Logout" />
        </form>
       </div>
    <body>
</html>



