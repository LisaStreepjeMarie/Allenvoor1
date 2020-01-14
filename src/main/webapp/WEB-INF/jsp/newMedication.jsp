<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <title>Voer nieuwe medicatie in</title>
    </head>
    <body>
        <h1>Voer medicatie in</h1>

        <form:form action="/medication/new" modelAttribute="medication">
            <table>
                <tr>
                    <td>Naam medicatie:</td>
                    <td>
                        <form:input path="medicationName" />
                    </td>
                </tr>
                <tr>
                    <td>Hoeveelheid:</td>
                    <td>
                        <form:input path="medicationAmount"/>
                    </td>
                </tr>
                <tr>
                    <td>Beschrijving:</td>
                    <td>
                       <form:input path="medicationComment"/>
                     </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Sla medicatie op" />
                    </td>
                </tr>
            </table>
        </form:form>
    </body>
</html>