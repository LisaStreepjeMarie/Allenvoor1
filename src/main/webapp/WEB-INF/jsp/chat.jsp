
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<html>
<head>
    <meta charset='utf-8' />
    <title>Chat!</title>

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
    <br>
    <br>
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

// getting all messages with an ajax call
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

// calculated the right "so many days ago" to put above the chat message
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


// function compares the list size every so often with ajax. If the list in database is bigger it will get the new messages
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
                     $('#overViewMessages').animate({scrollTop: $('#overViewMessages').prop("scrollHeight")}, 500);
                 }

             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });

}

function appendNewMessage(message){
if (message.member.name === "memberName"){
      $('#overViewMessages').append('<a class="list-group-item list-group-item-action active" ><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">' + message.member.name + '</h5><small class="text-muted">' + testData(message.datePosted) + '</small></div><p class="mb-1">'
      + message.message + '</p><small class="text-muted">hier komt een hartje?</small></a>');
       } else {
      $('#overViewMessages').append('<a class="list-group-item list-group-item-action"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">' + message.member.name + '</h5><small class="text-muted">' + testData(message.datePosted) + '</small></div><p class="mb-1">'
      + message.message + '</p><small class="text-muted">hier komt een hartje?</small></a>');
       }
}

</script>