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
                <form:form action="/member/current" modelAttribute="currentmember">
                <form:input path="memberId" type="hidden" />
                <table>
                      <tr>
                        <td>
                           <form:input path="membername" value= "${members.membername}" /></form>
                            </td>
                           <td><a href="/member/delete/${member_memberId}">Verwijder gebruiker</a></td>
                        </tr>
                </table>
               </form:form>
             </div>
        </body>
</html>