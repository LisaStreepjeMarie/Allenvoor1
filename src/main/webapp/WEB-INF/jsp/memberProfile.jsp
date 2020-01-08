<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
            integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="../../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Overzicht gebruiker</title>
    </head>
    <body class="webpage">
            <div id="container">
                <p>
                   <input class="btn btn-primary" type="submit" value="home" onclick="window.location='/';" />
                   <input class="btn btn-primary" type="submit" value="Jouw groepen" onclick="window.location='/team/all';" />
                   <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
               </p>
                <h1>Overzicht profiel ${currentmember.membername}</h1>
                <form:form action="/member/change" modelAttribute="currentmember">
                <form:input path="memberId" type="hidden" />
                <table>
                  <tr>
                     <td>
                        <form:input path="membername" value="${members.membername}" /></form>
                     </td>
                  </tr>
                  <tr>
                     <td>
                     <input type="submit" class= "btn btn-primary" value="Wijzig je inlognaam (log opnieuw in)" />
                     </td>
                  </tr>
                </table>
                </form:form>
                <form:form action="/member/delete" modelAttribute="currentmember">
                <form:input path="memberId" type="hidden" />
                <table>
                  <tr>
                     <td>
                        <input type="submit" class= "btn btn-primary" value="Verwijder je profiel" />
                     </td>
                  </tr>
                </table>
                </form:form>
             </div>
        </body>
</html>