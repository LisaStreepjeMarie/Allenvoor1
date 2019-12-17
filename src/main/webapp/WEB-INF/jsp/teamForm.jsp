<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
         integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"> -->

        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <title>Voeg een groep toe</title>
    </head>
    <body>
        <p><a href="/logout">Log uit</a></p>
        <h1>Voeg gegevens groep toe</h1>
        <form:form action="/teams/add" modelAttribute="team">
            <table>
                <tr>
                    <td>Groepsnaam:</td>
                    <td>
                        <form:input path="teamName" value="" /></form>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Sla groep  op" />
                    </td>
                </tr>
            </table>
        </form:form>
    </body>
</html>