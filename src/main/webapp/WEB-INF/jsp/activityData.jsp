
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <title>Wijzig de gegevens van een activiteit</title>
    </head>
    <body class="webpage">
        <div id="container">
            <p>
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='${pageContext.request.contextPath}/logout';" />
            </p>
            <h1>Wijzig gegevens groep</h1>
            <form:form action="${pageContext.request.contextPath}/activity/change" modelAttribute="activity">
                <form:input path="activityId" type="hidden" />
                <table>
                    <tr>
                        <td>Activity naam</td>
                        <td>
                            <form:input path="activityName" value="${activity.activityName}" />
                                    <select name="activityCategory">
                                        <option value="Huishouden" name="activityCategory">Huishouden</option>
                                        <option value="Medisch">Medisch</option>
                                        <option value="Vrije tijd" >Vrije tijd</option>
                                    </select>
                            </form>
                            <input class="btn btn-primary" type="submit" value="Bewaar activiteit" />
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>