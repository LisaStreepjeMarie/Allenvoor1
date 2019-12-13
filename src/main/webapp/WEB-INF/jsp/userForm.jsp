<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <title>Maak een nieuwe gebruiker</title>
</head>
<body>
<h1>Maak een nieuwe gebruiker</h1>

<form:form action="/user/new" modelAttribute="member">
    <table>
        <tr>
            <td>Naam:</td>
            <td>
                <form:input path="username" />
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
                <input type="submit" value="Sla gebruiker op" />
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>