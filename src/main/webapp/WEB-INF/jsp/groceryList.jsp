<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
<html>
<head>
    <meta charset='utf-8' />
    <title>Boodschappenlijst</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>

    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
body {
  margin: auto;
  width: 600px;
  padding: 10px;
}

/* Include the padding and border in an element's total width and height */
* {
  box-sizing: border-box;
}

/* Remove margins and padding from the list */
ul {
  margin: 0;
  padding: 0;
}

/* Style the list items */
ul li {
  cursor: pointer;
  position: relative;
  padding: 12px 8px 12px 40px;
  list-style-type: none;
  background: #ffffff;
  font-size: 18px;
  transition: 0.2s;
  border: 1px solid #e6f2ff;

  /* make the list items unselectable */
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

/* Set all odd list items to a different color (zebra-stripes) */
<!--ul li:nth-child(odd) {-->
<!--  background: #e6f9ff;-->
<!--}-->

/* Darker background-color on hover */
ul li:hover {
  background: #e6f9ff;
}

/* When clicked on, add a background color and strike out text */
ul li.checked {
  background: #fff;
  color: #737373;
  text-decoration: line-through;
}

/* Add a "checked" mark when clicked on */
ul li.checked::before {
  content: '';
  position: absolute;
  border-color: #33cc00;
  border-style: solid;
  border-width: 0 2px 2px 0;
  top: 10px;
  left: 16px;
  transform: rotate(45deg);
  height: 15px;
  width: 7px;
}

/* Style the close button */
.close {
  position: absolute;
  right: 0;
  top: 0;
  padding: 12px 16px 12px 16px;
}

.close:hover {
  background-color: #f44336;
  color: white;
}

/* Style the header */
.header {
  background-color: #99c2ff;
  padding: 20px 30px;
  color: white;
  text-align: center;
}

/* Clear floats after the header */
.header:after {
  content: "";
  display: table;
  clear: both;
}

/* Style the input */
input {
  margin: 0;
  border: none;
  border-radius: 0;
  width: 75%;
  padding: 10px;
  float: left;
  font-size: 16px;
}

/* Style the "Add" button */
.addBtn {
  padding: 10px;
  width: 25%;
  background: #66a3ff;
  color: #ffffff;
  float: left;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  transition: 0.3s;
  border-radius: 0;
}

.addBtn:hover {
  background-color: #4d94ff;
}
</style>
</head>
<body>

<div id="GroceryList" class="header">
    <h2 style="margin:5px">Boodschappenlijst!</h2><br>
    <input type="text" id="newGroceryItem" placeholder="Nieuwe Boodschap...">
    <span onclick="newElement()" id="addButton" class="addBtn">Voeg toe!</span>
</div>
<ul id="allGroceries">
    <c:forEach var="groceryItem" items="${groceryList}">
        <li value="${groceryItem.groceryItemId}">${groceryItem.groceryName}</li>
    </c:forEach>
</ul>

<script>

$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

var ctx = $('#contextPathHolder').attr('data-contextPath');

// Create a "close" button and append it to each list item
var allGroceriesInList = document.getElementsByTagName("LI");
var i;
for (i = 0; i < allGroceriesInList.length; i++) {
  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  allGroceriesInList[i].appendChild(span);
  console.log(allGroceriesInList[i].innerHTML);
}

// Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    var groceryItemId = div.value;
<!--    console.log(div.value);-->
<!--    //removing the added X-->
<!--    div.removeChild(div.lastElementChild)-->
<!--    console.log(div.innerHTML);-->
    div.style.display = "none";
    removeGroceryItem(groceryItemId);
  }
}

// Add a "checked" symbol when clicking on a list item
var list = document.querySelector('ul');
list.addEventListener('click', function(ev) {
  if (ev.target.tagName === 'LI') {
    ev.target.classList.toggle('checked');
  }
}, false);

// Create a new list item when clicking on the "Add" button
function newElement() {
  var li = document.createElement("li");
  var inputValue = document.getElementById("newGroceryItem").value;

  newGroceryItem = {
        title: document.getElementById("newGroceryItem").value,
    }

  console.log(newGroceryItem);

  var newInList = document.createTextNode(newGroceryItem.title);
  li.appendChild(newInList);

  if (newGroceryItem.title === '') {
    alert("Niks kan je niet kopen bij de supermarkt!");
  } else {

    addGroceryItem(newGroceryItem);
  }
  document.getElementById("newGroceryItem").value = "";

  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  li.appendChild(span);

  for (i = 0; i < close.length; i++) {
    close[i].onclick = function() {
      var div = this.parentElement;
      div.style.display = "none";
    }
  }
}

// going to the delete groceryitem controller from this
function removeGroceryItem(groceryItemId){
    $.ajax({
         type:'GET',
         url: ctx + "/delete/groceryitem/" + groceryItemId,
         success : function(result) {
            console.log("woop wopp")
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

// sending a grocery item through json, need to append the item to the list next
function addGroceryItem(newGroceryItem){
      $.ajax({
        url: ctx + "/add/groceryitem",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(newGroceryItem),
        dataType: 'json',
        async: true,
        success: function(result) {
            $('#allGroceries').append('<li value="' + result.data.id + '">' + result.data.title + '</li>');
            console.log(result.data.id);
            console.log("woop woop");
        },
        error: function(e) {
            alert("changeEvent() error")
            console.log("ERROR: ",  e);
        }
    });
}

</script>

</body>
</html>
