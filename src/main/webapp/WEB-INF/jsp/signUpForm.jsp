<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Maak een nieuwe gebruiker</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" type="text/css">
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
    </body>
</html>



