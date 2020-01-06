<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
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
                <h1>Profiel pagina</h1>
                <form:form action="/member/change" modelAttribute="currentmember" method="post">
                <form:input path="memberId" type="hidden" />
                <table>
                      <tr>
                        <td>
                           <form:input path="membername" value= "${members.membername}" /></form>
                            </td>
                            <td><a href="/team/select/<c:out value="${members.memberId}" />"><c:out value="${members.membername}" /></a></td>
                           <td><a href="/member/change">Verander je naam/inlog</a></td>
                        </tr>
                </table>
               </form:form>
             </div>
        </body>
</html>