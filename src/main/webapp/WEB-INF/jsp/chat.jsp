
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<html>
<head>
    <meta charset='utf-8' />
    <title>Chat!</title>

<!--    <script src="${pageContext.request.contextPath}/webjars/moment/2.24.0/min/moment.min.js"></script>-->
<!--    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>-->
<!--    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>-->
<!--    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>-->

<!--    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>-->

<!--    <link id="teamId" data-teamId="${team.teamId}"/>-->
<!--    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>-->
<!--    <meta name="viewport" content="width=device-width, initial-scale=1">-->
<style>
.messagesOverView{
    max-height: 400px;
    margin-bottom: 10px;
    overflow:scroll;
    -webkit-overflow-scrolling: touch;

}

.wholeChat{
   width: 600px;
}

</style>
</head>
<body>
<div class="wholeChat">
<div id="overViewMessages" class="list-group messagesOverView">

<!--    <a href="#" class="list-group-item list-group-item-action">-->
<!--        <div class="d-flex w-100 justify-content-between">-->
<!--            <h5 class="mb-1">List group item heading</h5>-->
<!--            <small class="text-muted">3 days ago</small>-->
<!--        </div>-->
<!--        <p class="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>-->
<!--        <small class="text-muted">Donec id elit non mi porta.</small>-->
<!--    </a>-->
    <br>
</div>
    <input type="hidden" name="memberName" value="${member.memberName}" id="memberName"/>
    <form id="formNewMessage">
        <div class="form-group">
            <label for="messageBody">Typ hier je bericht!</label>
            <textarea class="form-control" id="messageBody" rows="3"></textarea>
        </div>
        <button type="button" onclick="newMessageForAjax()" class="btn btn-primary float-right">Versturen</button>
    </form>
</div>


</body>
<script>

$(document).ready(function() {

 setInterval(checkNewMessages,1000);

// setting the context url
var ctx = $('#contextPathHolder').attr('data-contextPath');

getAllMessages();

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
      $('#formNewMessage').trigger("reset");
      $('#overViewMessages').append('<a class="list-group-item list-group-item-action"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">' + result.data.member.name + '</h5><small class="text-muted">' + testData(result.data.datePosted) + '</small></div><p class="mb-1">'
          + allMessages[i].message + '</p><small class="text-muted">hier komt een hartje?</small></a>');
          $('#overViewMessages').animate({scrollTop: $('#overViewMessages').prop("scrollHeight")}, 500);
      },
      error: function(e) {
          alert("ERRRROOOOORRRR")
          console.log("ERROR: ",  e);
          }
      });
}

function getAllMessages(){

var memberName = document.getElementById("memberName").value;

   $.ajax({
      url: "${pageContext.request.contextPath}/chat/getAll",
      method: "GET",
      success: function(result) {
      allMessages = result.data.messages;
        for (i in allMessages ) {
          appendNewMessage(allMessages[i]);
          $('#formNewMessage').trigger("reset");
          }
          $('#overViewMessages').animate({scrollTop: $('#overViewMessages').prop("scrollHeight")}, 500);
       },
      error: function(e) {
          alert("ERRRROOOOORRRR")
          console.log("ERROR: ",  e);
          }
      });
}

function testData(date){
var givenDate = new Date(Date.parse(date));
var day = givenDate.getDay();

timestamp = moment().toDate();
today = timestamp.getDay();

if (day === today){
return "vandaag ";

} else if (today - day === 1) {
return (today - day) + " dag geleden"

} else {
return (today - day) + " dagen geleden"
}

}


function checkNewMessages(){

var allMessages = document.getElementsByClassName("list-group-item");
console.log(allMessages.length);

$.ajax({
         type:'GET',
         url: "${pageContext.request.contextPath}/chat/checkNewMessages/" + allMessages.length,
         success : function(result) {
                if (result.status == "newMessages") {
                newMessageList = result.data;
                     for (i in newMessageList ) {
                     appendNewMessage(newMessageList[i]);
                     }
                 }
                 $('#overViewMessages').animate({scrollTop: $('#overViewMessages').prop("scrollHeight")}, 500);
             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });

}

function appendNewMessage(message){
if (message.member.name === "memberName"){
      $('#overViewMessages').append('<a class="list-group-item list-group-item-action" ><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">' + message.member.name + '</h5><small class="text-muted">' + testData(message.datePosted) + '</small></div><p class="mb-1">'
      + message.message + '</p><small class="text-muted">hier komt een hartje?</small></a>');
       } else {
      $('#overViewMessages').append('<a class="list-group-item list-group-item-action"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">' + message.member.name + '</h5><small class="text-muted">' + testData(message.datePosted) + '</small></div><p class="mb-1">'
      + message.message + '</p><small class="text-muted">hier komt een hartje?</small></a>');
       }
}

</script>