
// setting the context for the URL links
var ctx = $('#contextPathHolder').attr('data-contextPath');

$(document).ready(function() {

// loading all messages when you start the page
getAllMessages();

// interval command to check for new messages
setInterval(checkNewMessages,1000);

// creating a CSRF token for postmapping ajax commands
    $.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
    });
});

// function which gets called when you click the new message submit button
function newMessageForAjax(){

   // creating a new message to save
   newMessage = {
        message: document.getElementById("messageBody").value,
        }

// ajax call to send it to the controller side
   $.ajax({
      url: ctx + "/add/message",
      method: "POST",
      contentType: "application/json; charset=UTF-8",
      data: JSON.stringify(newMessage),
      dataType: 'json',
      success: function(result) {

      // resetting the form for the next message
      $('#formNewMessage').trigger("reset");
      },
      error: function(e) {
          alert("message error")
          console.log("ERROR: ",  e);
          }
      });
}

// getting all messages with an ajax call
function getAllMessages(){

   $.ajax({
      url: ctx + "/chat/getAll",
      method: "GET",
      success: function(result) {
      allMessages = result.data.messages;
        for (i in allMessages ) {
        // each message needs to be appended to the list, this is done with the function below
          appendNewMessage(allMessages[i]);
          }
          // after the messages are loaded, scroll to the bottem for the latest message
          $('#overViewMessages').animate({scrollTop: $('#overViewMessages').prop("scrollHeight")}, 500);
       },
      error: function(e) {
          alert("error")
          console.log("ERROR: ",  e);
          }
      });
}

// calculated the right "so many days ago" to put above the chat message using moment
function testData(date){
var givenDate = new Date(Date.parse(date));
var day = givenDate.getDate();

timestamp = moment().toDate();
today = timestamp.getDate();

if (day === today){
return "vandaag ";

} else if (today - day === 1) {
return (today - day) + " dag geleden"

} else {
return (today - day) + " dagen geleden"
}

}

// function compares the list size every so often with ajax. If the list in database is bigger it will get the new messages and append these
function checkNewMessages(){

// getting the list size
var allMessages = document.getElementsByClassName("list-group-item");

$.ajax({
         type:'GET',
         url: ctx + "/chat/checkNewMessages/" + allMessages.length,
         success : function(result) {
                if (result.status == "newMessages") {
                newMessageList = result.data;
                     for (i in newMessageList ) {
                     // each message needs to be appended to the list, this is done with the function below
                     appendNewMessage(newMessageList[i]);
                     }
                     // after the messages are loaded, scroll to the bottem for the latest message
                     $('#overViewMessages').animate({scrollTop: $('#overViewMessages').prop("scrollHeight")}, 500);
                 }

             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });

}

// appending the new items to the list
function appendNewMessage(message){

// making a variable of your own membername
var memberName = document.getElementById("givenMemberName").value;

// changing the way the message looks if it is yours
if (message.member.name === memberName){
      $('#overViewMessages').append('<a class="list-group-item"><div class="d-flex w-100 justify-content-between"><small class="text-muted"><nobr>'
      + testData(message.datePosted) + '</nobr></small><div class="d-flex w-100 justify-content-end"><h5 class="mb-1" style="color:#0077b3;">'
      + message.member.name + '</h5></div></div><div class="d-flex w-100 justify-content-end"><p class="mb-1" >' + message.message + '</p>');
    } else {
    $('#overViewMessages').append('<a class="list-group-item"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1" style="color:#993399;">'
    + message.member.name + '</h5><small class="text-muted">' + testData(message.datePosted) + '</small></div><p class="mb-1" >'
    + message.message + '</p>');
    }
}