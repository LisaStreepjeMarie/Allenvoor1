<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
<head>
    <title>Wijzig de gegevens van een groep</title>
        <!-- Add icon library -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body class="webpage">
    <div id="container" class="ml-4 mt-4">
        <p>
            <input class="btn btn-primary" type="submit" value="Home" onclick="window.location='${pageContext.request.contextPath}/';" />
            <input class="btn btn-primary" type="submit" value="Al je groepen" onclick="window.location='${pageContext.request.contextPath}/team/all';" />
            <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='${pageContext.request.contextPath}/logout';" />
        </p>
        <h1 class="font-weight-light">Wijzig gegevens groep</h1>
        <!-- <form:form action="${pageContext.request.contextPath}/team/change" modelAttribute="team">
        <form:input path="teamId" type="hidden" />
        <form:input path="allMembersInThisTeamSet" type="hidden" /> -->
            <table>
                <tr>
                    <td>Groepsnaam:</td>
                    <td>
                        <form:input size="30" path="teamName" value="${team.teamName}" /></form>
                        <input class="btn btn-primary" type="submit" value="Wijzig" />
                    </td>
                </tr>
            </table>
         <!-- </form:form> -->

        <table>
            <tr>
                 <td colspan="2">Groepsleden:</td>
            </tr>
             <c:forEach items="${team.allMembersInThisTeamSet}" var="member">
             <tr>
                 <td><c:out value="${member.memberName}" />
                    memberId: <c:out value="${member.memberId}" />
                    teamId: <c:out value="${team.teamId}" /></td>
                 <td><input class="btn btn-primary" type="submit" value="Verwijder"
                      onclick="window.location='${pageContext.request.contextPath}/team${team.teamId}/deleteMember/${member.memberId}'" /></td>
             </tr>
            </c:forEach>
         </table>

         <form:form action="${pageContext.request.contextPath}/team/addMember" modelAttribute="teamMemberDTO">
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