<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Overzicht gebruiker</title>
    </head>
    <body class="webpage">
            <div id="container">
                <p>
                    <form:form action="/logout" method="post">
                       <input type="submit" value="Logout" />
                   </form:form>
                </p>
                <h1>Overzicht gebruiker</h1>
                <table>
                    <c:forEach items="${currentMember}" var="member">
                        <tr>
                            <td><a href="/member/select/<c:out value="${member.memberId}" />"><c:out value="${principal.username}" /></a></td>
                            <td><a href="/member/delete/${member.memberId}">Verwijder gebruiker</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </body>
    <body>
</html>