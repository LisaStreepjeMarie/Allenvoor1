<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
<html>
<head>
    <meta charset='utf-8' />
    <title>Kalender</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <script src="${pageContext.request.contextPath}/webjars/moment/2.24.0/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>

    <link href="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.print.min.css" rel="stylesheet" media='print' />
    <script src="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/fullcalendar.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/fullcalendar/3.9.0/locale/nl.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/webjars/tempusdominus-bootstrap-4/5.1.2/js/tempusdominus-bootstrap-4.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/tempusdominus-bootstrap-4/5.1.2/css/tempusdominus-bootstrap-4.css" rel='stylesheet'>

    <!-- data attributes which calendar.js uses -->
    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
body {
  margin: 0;
  min-width: 250px;
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
  background: #eee;
  font-size: 18px;
  transition: 0.2s;

  /* make the list items unselectable */
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

/* Set all odd list items to a different color (zebra-stripes) */
ul li:nth-child(odd) {
  background: #f9f9f9;
}

/* Darker background-color on hover */
ul li:hover {
  background: #ddd;
}

/* When clicked on, add a background color and strike out text */
ul li.checked {
  background: #888;
  color: #fff;
  text-decoration: line-through;
}

/* Add a "checked" mark when clicked on */
ul li.checked::before {
  content: '';
  position: absolute;
  border-color: #fff;
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
  background-color: #f44336;
  padding: 30px 40px;
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
  background: #d9d9d9;
  color: #555;
  float: left;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  transition: 0.3s;
  border-radius: 0;
}

.addBtn:hover {
  background-color: #bbb;
}
</style>
</head>
<body>

<div id="myDIV" class="header">
    <h2 style="margin:5px">Boodschappenlijst!</h2>
    <input type="text" id="newGroceryItem" placeholder="Nieuwe Boodschap toevoegen...">
    <span onclick="newElement()" class="addBtn">Voeg toe!</span>
</div>
<ul id="myUL">
    <c:forEach var="groceryItem" items="${groceryList}">
        <input type="hidden" name="groceryItemId" id="groceryItemId" />
        <li>${groceryItem.groceryName}</li>
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
var myNodelist = document.getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length; i++) {
  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  myNodelist[i].appendChild(span);
}

// Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
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
        id: document.getElementById("groceryItemId").value,
        title: document.getElementById("newGroceryItem").value,
    }

  console.log(newGroceryItem);

      $.ajax({
        url: ctx + "/add/groceryitem",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(newGroceryItem),
        dataType: 'json',
        async: true,
        success: function(result) {
            console.log("woop woop");
        },
        error: function(e) {
            alert("changeEvent() error")
            console.log("ERROR: ",  e);
        }
    });

  var t = document.createTextNode(inputValue);
  li.appendChild(t);
  if (inputValue === '') {
    alert("You must write something!");
  } else {
    document.getElementById("myUL").appendChild(li);
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
</script>

</body>
</html>
