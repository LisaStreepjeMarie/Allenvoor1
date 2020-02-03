<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
<head>
    <title>Wijzig de gegevens van een groep</title>
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
                    <h3 class="font-weight-light">Wijzig gegevens groep ${team.teamName}</h3>
                    <table>
                        <tr>
                             <td colspan="2"><h5 class="font-weight-light">Groepsleden:</h5></td>
                        </tr>
                         <c:forEach items="${team.allMembersInThisTeamSet}" var="member">
                         <tr>
                             <td><c:out value="${member.memberName}" /></td>
                             <td><input class="btn btn-primary" type="submit" value="Verwijder"
                                  onclick="window.location='${pageContext.request.contextPath}/team${team.teamId}/deleteMember/${member.memberId}'" /></td>
                         </tr>
                        </c:forEach>
                     </table>

                     <form:form action="${pageContext.request.contextPath}/team/addMember" modelAttribute="teamMemberDTO">
                         <form:input path="teamId" type="hidden" />
                            <table>
                            <tr>
                                <td><h5 class="font-weight-light">Nieuw groepslid:</h5></td>
                                <td>
                                   <form:input path="teamMemberName" /></form>
                                   <input class="btn btn-primary" type="submit" value="Voeg toe" />
                                </td>
                            </tr>
                        </table>
                    </form:form>

                    <form:form action="${pageContext.request.contextPath}/team/change" modelAttribute="team">
                    <form:input path="teamId" type="hidden" />
                    <form:input path="allMembersInThisTeamSet" type="hidden" />
                        <table>
                            <tr>
                                <td><h5 class="font-weight-light">Groepsnaam:</h5></td>
                                <td>
                                    <form:input size="30" path="teamName" value="${team.teamName}" /></form>
                                    <input class="btn btn-primary" type="submit" value="Wijzig" />
                                </td>
                            </tr>
                        </table>
                     </form:form>
                </div>
            </div>
        </div>
    </body>
</html>