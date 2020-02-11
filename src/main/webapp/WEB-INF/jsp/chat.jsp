
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<html>
<head>
    <meta charset='utf-8' />
    <title>Chat!</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>


    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
<style>
    .list-group {
    width: 500px;
    }

</style>
</head>
<body>

<div id="overViewMessages" class="list-group">
    <a href="#" class="list-group-item list-group-item-action active">
        <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">List group item heading</h5>
            <small>3 days ago</small>
        </div>
        <p class="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
        <small>Donec id elit non mi porta.</small>
    </a>
    <a href="#" class="list-group-item list-group-item-action">
        <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">List group item heading</h5>
            <small class="text-muted">3 days ago</small>
        </div>
        <p class="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
        <small class="text-muted">Donec id elit non mi porta.</small>
    </a>
    <a href="#" class="list-group-item list-group-item-action">
        <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">List group item heading</h5>
            <small class="text-muted">3 days ago</small>
        </div>
        <p class="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
        <small class="text-muted">Donec id elit non mi porta.</small>
    </a>
    <br>

</div>

<form id="formNewMessage">
    <div class="form-group">
        <label for="messageBody">Typ hier je bericht!</label>
        <textarea class="form-control" id="messageBody" rows="3"></textarea>
    </div>
    <button type="button" onclick="newMessageForAjax()" class="btn btn-primary float-right">Versturen</button>
</form>
</body>
<script>
getAllMessages();

$(document).ready(function() {
// creating a CSRF token for postmapping ajax stuff
    $.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
    });
});

function newMessageForAjax(){
   // creating a new message to save
   newMessage = {
        message: document.getElementById("messageBody").value,
        }

   $.ajax({
      url: "${pageContext.request.contextPath}/add/message",
      method: "POST",
      contentType: "application/json; charset=UTF-8",
      data: JSON.stringify(newMessage),
      dataType: 'json',
      success: function(result) {
          $('#overViewMessages').append('<a class="list-group-item list-group-item-action"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">' + result.data.member.name + '</h5><small class="text-muted">3 days ago</small></div><p class="mb-1">'
          + result.data.message + '</p><small class="text-muted">Donec id elit non mi porta.</small></a>');
          $('#formNewMessage').trigger("reset");
          },
      error: function(e) {
          alert("ERRRROOOOORRRR")
          console.log("ERROR: ",  e);
          }
      });
}

function getAllMessages(){

   $.ajax({
      url: "${pageContext.request.contextPath}/chat/getAll",
      method: "GET",
      success: function(result) {
      allMessages = result.data.messages;
        for (i in allMessages ) {
          $('#overViewMessages').append('<a class="list-group-item list-group-item-action"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">' + allMessages[i].member.name + '</h5><small class="text-muted">3 days ago</small></div><p class="mb-1">'
          + allMessages[i].message + '</p><small class="text-muted">Donec id elit non mi porta.</small></a>');
          $('#formNewMessage').trigger("reset");
          }
       },
      error: function(e) {
          alert("ERRRROOOOORRRR")
          console.log("ERROR: ",  e);
          }
      });
}

</script>