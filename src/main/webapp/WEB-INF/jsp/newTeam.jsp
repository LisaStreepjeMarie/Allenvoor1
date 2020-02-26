<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:mytags="">
    <head>
        <title>Voeg een groep toe</title>

        <!-- Add icon library -->
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

        <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    </head>

    <body class= "webpage">
        <mytags:navbar/>
        <div class= "masthead">
            <div id="container">
                <br />
                <div class="ml-4 mt-4">
                <!-- <div class="mt-3 col-12"> -->
                    <h3 class="font-weight-light">Voeg gegevens groep toe</h3>

                    <form:form action="${pageContext.request.contextPath}/team/new" modelAttribute="team">
                        <table>
                            <tr>
                                <td><h6 class="font-weight-light">Groepsnaam:</h6></td>
                                <td>
                                    <form:input path="teamName" value="" /></form>
                                </td>
                            </tr>

                            <tr><td colspan="2"><input class="btn btn-primary" type="submit" value="Bewaar" /></td></tr>
                        </table>
                    </form:form>
                    <input class="btn btn-primary" type="button" value="Terug" onclick="window.location='${pageContext.request.contextPath}/team/all';">
                </div>
            </div>
        </div>
    </body>
</html>