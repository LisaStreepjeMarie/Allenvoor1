<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
<html>
<head>
    <meta charset='utf-8' />
    <title>Boodschappenlijst</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/stylingGroceryList.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/groceryList.js"></script>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<div id="GroceryList" class="header">
    <h2 style="margin:5px">Boodschappenlijst!</h2><br>
    <input type="text" id="newGroceryItem" placeholder="Nieuwe Boodschap...">
    <span onclick="newElement()" id="addButton" class="addBtn">Voeg toe!</span>
</div>


<ul id="allGroceries">
    <div id="allMedications">
        <c:forEach var="medication" items="${allMedications}">
            <li value="${medication.medicationId}">
                <i class="fas fa-pills fa-lg" style="color:#e6b3ff;"></i> ${medication.medicationName}
<!--                <span class="badge badge-primary badge-pill" style="float:right;">${medication.medicationAmount}</span>-->
            </li>
        </c:forEach>
    </div>
    <div id="groceryItem">
        <c:forEach var="groceryItem" items="${groceryList}">
            <li value="${groceryItem.groceryItemId}">${groceryItem.groceryName}</li>
        </c:forEach>
    </div>
</ul>

<div class="footer">
    <span onclick="removeAllCheckedFromList()" id="checkDBtn" class="checkDBtn">verwijder gekochte items</span>&nbsp;
    <span onclick="removeAllFromList()" id="removeBtn" class="removeBtn">verwijder alle items</span>
    <br>
</div>

<script>

$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

var ctx = $('#contextPathHolder').attr('data-contextPath');

// Create a "close" button and append it to each list item
closeButtonOnAll();

// Add a "checked" symbol when clicking on a list item
// Added a boolean ajax call to write it as bought boolean in the database
var list = document.querySelector('ul');
list.addEventListener('click', function(ev) {
  if (ev.target.tagName === 'LI') {
    var type = ev.target.parentNode.id
    boughtBoolean(type, ev.target.value);
    ev.target.classList.toggle('checked');
  }
}, false);

// Create a new list item when clicking on the "Add" button
function newElement() {


// Creating a new groceryItem to save
  newGroceryItem = {
        title: document.getElementById("newGroceryItem").value,
  }

// checking if the input isn't zero
  if (newGroceryItem.title === '') {
    alert("Niks kan je niet kopen bij de supermarkt!");
  } else {
    // sending the new item to the controller through ajax
    addGroceryItem(newGroceryItem);
  }
    document.getElementById("newGroceryItem").value = "";
}

// deletes the chosen  item, isn't expecting anything back (besides success)
function removeGroceryItem(type, itemId){
    $.ajax({
         type:'GET',
         url: ctx + "/delete/" + type + "/" + itemId,
         success : function(result) {
            console.log("woop wo0p")
             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });
}

// making sure the enter button works to add something to the list
var input = document.getElementById("newGroceryItem");
input.addEventListener("keyup", function(event) {
  if (event.keyCode === 13) {
   event.preventDefault();
   document.getElementById("addButton").click();
  }
});

// sending a grocery item through json and adding it to the list
function addGroceryItem(newGroceryItem){
      $.ajax({
        url: ctx + "/add/groceryitem",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(newGroceryItem),
        dataType: 'json',
        async: true,
        success: function(result) {
            $('#groceryItem').append('<li value="' + result.data.id + '">' + result.data.title + '</li>');
            closeButtonOnAll();
            console.log("woop woop");
        },
        error: function(e) {
            alert("changeEvent() error")
            console.log("ERROR: ",  e);
        }
    });
}

// using this atm to add a close button to the newest item in the list. Will look into a cleaner solution later
function closeButtonOnAll(){
var allGroceriesInList = document.getElementsByTagName("LI");
var i;
for (i = 0; i < allGroceriesInList.length; i++) {
      var span = document.createElement("SPAN");
      var txt = document.createTextNode("\u00D7");
      span.className = "close";
      span.appendChild(txt);
      allGroceriesInList[i].appendChild(span);
}

// Click on a close button to delete the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
      close[i].onclick = function()   {
            var div = this.parentElement;
            var type = div.parentElement.id;
            var groceryItemId = div.value;
            div.style.display = "none";
            removeGroceryItem(type, groceryItemId);
      }
   }
}

function boughtBoolean(type, groceryItemId){
    $.ajax({
         type:'GET',
         url: ctx + "/bought/" + type + "/" + groceryItemId,
         success : function(result) {
            console.log("woop woop")
             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });
}

function removeAllCheckedFromList(){
var allChecked = document.querySelectorAll('li.checked');
var i;
for (i = 0; i < allChecked.length; i++) {
      var itemValue = allChecked[i].value;
      var type = allChecked[i].parentNode.id;
      removeGroceryItem(type, itemValue);
      allChecked[i].style.display = "none";
    }
}

function removeAllFromList(){
var allGroceriesInList = document.getElementsByTagName("LI");
var i;
for (i = 0; i < allGroceriesInList.length; i++) {
      var itemValue = allGroceriesInList[i].value;
      var type = allGroceriesInList[i].parentNode.id;
      removeGroceryItem(type, itemValue);
      allGroceriesInList[i].style.display = "none";
    }
}

</script>
</body>
</html>
