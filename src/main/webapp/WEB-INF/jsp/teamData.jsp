<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
<head>
    <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <title>Wijzig de gegevens van een groep</title>
</head>
<body class="webpage">
    <div class="container-fluid">
        <p>
            <input class="btn btn-primary" type="submit" value="Home" onclick="window.location='/';" />
            <input class="btn btn-primary" type="submit" value="Al je groepen" onclick="window.location='/team/all';" />
            <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
        </p>
        <h1>Wijzig gegevens groep</h1>
        <form:form action="/team/change" modelAttribute="team">
        <form:input path="teamId" type="hidden" />
        <form:input path="membername" type="hidden" />
            <table>
                <tr>
                    <td>Groepsnaam:</td>
                    <td>
                        <form:input size="30" path="teamName" value="${team.teamName}" /></form>
                        <input class="btn btn-primary" type="submit" value="Wijzig" />
                    </td>
                </tr>
                <tr>
                     <td>Groepslid:</td>
                     <td>
                        <c:forEach items="${team.membername}" var="member">
                             <c:out value="${member.membername}" /><br />
                         </c:forEach>
                     </td>
                 </tr>
             </table>
         </form:form>
         <form:form action="/team/addMember" modelAttribute="teamMemberDTO">
             <form:input path="teamId" type="hidden" />
                <table>
                <tr>
                    <td>Nieuw groepslid:</td>
                    <td>
                       <form:input path="teamMemberName" /></form>
                    </td>
                </tr>
                <tr>
                    <td>
                         <input class="btn btn-primary" type="submit" value="Wijzig" />
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</body>
</html>