<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="" xmlns:mytags="" xmlns:form="http://www.w3.org/1999/xhtml">
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
                <div class="w-50 p-3">
                    <h3 class="font-weight-light">Wijzig gegevens groep ${teamName}</h3>
                        <tr>
                             <td colspan="2"><h5 class="font-weight-light">Groepsleden:</h5></td>
                        </tr>
                        <ul class="list-group">
                         <c:forEach items="${teamMemberList}" var="membership">
                             <tr>
                                 <td><li class="list-group-item"><c:out value="${membership.member.memberName}" />
                                     <input class="btn btn-primary float-right" style="width: auto;padding:5px;margin-left:5px;" type="submit" value="Verwijder"
                                                    onclick="window.location='${pageContext.request.contextPath}/team/${membership.team.teamId}/delete/membership/${membership.membershipId}'" /></td>
                                 &emsp;
                                 <input class="btn btn-primary float-right" style="width: auto;padding:5px;margin-left:5px;" type="submit" value="Geef beheerderrechten"
                                            onclick="window.location='${pageContext.request.contextPath}/team/grantadmin/${membership.team.teamId}/${membership.member.memberId}'" />
                                     </tr>
                                 </li>
                        </c:forEach>
                        <c:forEach items="${teamAdminList}" var="membership">
                            <li class="list-group-item"><c:out value="${membership.member.memberName}" />
                            </li>
                        </c:forEach>
                        </ul>

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

                </div>
            </div>
        </div>
    </body>
</html>