<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset='utf-8' />
    <title>Chat!</title>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

</head>
<body>
<div class="wholeChat">
    <div id="overViewMessages" class="list-group messagesOverView">
        <a class="list-group-item"><div class="d-flex w-100 justify-content-center"><h5 class="mb-4">Start hier je chat met groep: ${team.teamName}</h5></div></a>
    </div>
    <input type="hidden" name="memberName" value="${member.memberName}" id="givenMemberName"/>
    <form id="formNewMessage">
        <div class="form-group">
            <textarea rows="3"  class="form-control" id="messageBody" placeholder="Typ hier je bericht!"></textarea>
        </div>
        <button type="button" onclick="newMessageForAjax()" class="btn btn-primary float-right">Versturen</button>
    </form>
</div>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>
</body>
</html>





