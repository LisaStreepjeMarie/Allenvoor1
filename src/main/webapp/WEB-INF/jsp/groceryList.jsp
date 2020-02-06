<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<html>
<head>
    <meta charset='utf-8' />
    <title>Boodschappenlijst</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.12.0/css/all.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<!--    <link href="${pageContext.request.contextPath}/css/stylingGroceryList.css" rel="stylesheet" type="text/css"/>-->

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">

<style>

/* Remove margins and padding from the list */
ul {
  margin: 0;
  padding: 0;
}

/* Style the list items */
.listItem {
  cursor: pointer;
  width: 600px;
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
/* not using this since I didn't like it - LM */
<!--ul li:nth-child(odd) {-->
<!--  background: #e6f9ff;-->
<!--}-->

/* Darker background-color on hover */
.listItem:hover {
  background: #e6f9ff;
}

/* When clicked on, add a background color and strike out text */
.listItem.checked {
  background: #fff;
  color: #737373;
  text-decoration: line-through;
}

/* Add a "checked" mark when clicked on */
.listItem.checked::before {
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
  width: 600px;
  background-color: #99c2ff;
  padding: 20px 30px;
  color: white;
  text-align: center;
}

/* Style the footer */
.footer {
  width: 600px;
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

/* Clear floats after the footer */
.footer:after {
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

/* Style the "removeChecked" button */
.checkDBtn {
  padding: 10px;
  width: 40%;
  background: #66a3ff;
  color: #ffffff;
  float: left;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  transition: 0.3s;
  border-radius: 0;
}

/* Style the "removeAll" button */
.removeBtn {
  padding: 10px;
  width: 40%;
  background: #6666ff;
  color: #ffffff;
  float: right;
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
    <div id="WholeGroceryList">
        <div id="GroceryList" class="header">
            <h2 style="margin:5px">Boodschappenlijst!</h2><br>
            <input type="text" id="newGroceryItem" placeholder="Nieuwe Boodschap...">
            <span onclick="newElement()" id="addButton" class="addBtn">Voeg toe!</span>
        </div>


        <ul id="allGroceries">
            <div id="allMedications">
                <!-- this list is filled with ajax in the grocery.js -->
            </div>
            <div id="groceryItem">
                <!-- this list is filled with ajax in the grocery.js -->
            </div>
        </ul>

        <div class="footer">
            <span onclick="removeAllCheckedFromList()" id="checkDBtn" class="checkDBtn">verwijder gekochte items</span>&nbsp;
            <span onclick="removeAllFromList()" id="removeBtn" class="removeBtn">verwijder alle items</span>
            <br>
        </div>
    </div>

    <script>
// set up to send a CSRF token with ajax for postmapping

$(document).ready(function(){
 setInterval(getAllItemsFromDataBase,1000);
});

$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

//setting the context url
var ctx = $('#contextPathHolder').attr('data-contextPath');

<!--getAllItemsFromDataBase();-->

// Create a "X" button and append it to each list item when opening the page
//  full function below in document

// Create a new list item when clicking on the "Add" button
function newElement() {

// Creating a new groceryItem to save
  newGroceryItem = {
        title: document.getElementById("newGroceryItem").value,
  }

// checking if the input isn't zero otherwise adding an item to the DB and list
  if (newGroceryItem.title === '') {
    alert("Niks kan je niet kopen bij de supermarkt!");
  } else {
    // sending the new item to the controller through ajax
    addGroceryItem(newGroceryItem);
  }
    document.getElementById("newGroceryItem").value = "";
}

// deletes the chosen  item, isn't expecting anything back (besides success)
// can be used for both medical and normal item (hence the type and Id)
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
            $('#groceryItem').append('<li class="listItem" value="' + result.data.id + '">' + result.data.title + '</li>');
            addCloseAndListener();
            console.log("woop woop");
        },
        error: function(e) {
            alert("boodschappenerror")
            console.log("ERROR: ",  e);
        }
    });
}

// every item on the list gets the delete button with the first part of this function
function closeButtonOnAll(){
var allGroceriesInList = document.getElementsByClassName("listItem");
var i;
for (i = 0; i < allGroceriesInList.length; i++) {
      allGroceriesInList[i].addEventListener('click', function(ev) {
      // adding the clicked action to each item in the list
      if (ev.target.tagName === 'LI') {
      var type = ev.target.parentNode.id
      boughtBoolean(type, ev.target.value);
      ev.target.classList.toggle('checked');
        }
      }, false);

      var span = document.createElement("SPAN");
      var txt = document.createTextNode("\u00D7");
      span.className = "close";
      span.appendChild(txt);
      allGroceriesInList[i].appendChild(span);
}

// this part makes sure the item gets deleted when clicked and also the X dissapears
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

// sets the item to bought in the database
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
var allGroceriesInList = document.getElementsByClassName("listItem");
var i;
for (i = 0; i < allGroceriesInList.length; i++) {
      var itemValue = allGroceriesInList[i].value;
      var type = allGroceriesInList[i].parentNode.id;
      removeGroceryItem(type, itemValue);
      allGroceriesInList[i].style.display = "none";
    }
}
function getAllItemsFromDataBase(){
    $.ajax({
         type:'GET',
         url: ctx + "/grocerylist/getAll",
         success : function(result) {
                $('#groceryItem').empty();
                $('#allMedications').empty();
                MedicationList = result.data.medications;
                GroceryList = result.data.groceries;
                 for (i in MedicationList ) {
                     if (MedicationList[i].bought){
                     $('#allMedications').append('<li class="checked listItem" value="' + MedicationList[i].id + '"><i class="fas fa-pills fa-lg" style="color:#e6b3ff;"></i>&emsp;' + MedicationList[i].name + '&emsp;<span class="badge badge-primary badge-pill">' + MedicationList[i].refillamount + '</span></li>');
                     } else {
                     $('#allMedications').append('<li class="listItem" value="' + MedicationList[i].id + '"><i class="fas fa-pills fa-lg" style="color:#e6b3ff;"></i>&emsp;' + MedicationList[i].name +'&emsp;<span class="badge badge-primary badge-pill">' + MedicationList[i].refillamount + '</span></li>');
                     }
                 }
                 for (i in GroceryList){
                     if (GroceryList[i].bought){
                        $('#groceryItem').append('<li class="checked listItem" value="' + GroceryList[i].id + '">' + GroceryList[i].title + '</li>');
                     } else {
                        $('#groceryItem').append('<li class="listItem" value="' + GroceryList[i].id + '">' + GroceryList[i].title + '</li>');
                     }
                 }
                 closeButtonOnAll();
             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });
}

function addCloseAndListener(){
var allGroceriesInList = document.getElementsByClassName("listItem");
var i = allGroceriesInList.length - 1;
      allGroceriesInList[i].addEventListener('click', function(ev) {
      // adding the clicked action to each item in the list
      if (ev.target.tagName === 'LI') {
      var type = ev.target.parentNode.id
      boughtBoolean(type, ev.target.value);
      ev.target.classList.toggle('checked');
        }
      }, false);

      var span = document.createElement("SPAN");
      var txt = document.createTextNode("\u00D7");
      span.className = "close";
      span.appendChild(txt);
      allGroceriesInList[i].appendChild(span);

// this part makes sure the item gets deleted when clicked and also the X disapears
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
    </script>

<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/groceryList.js"></script>-->

</body>
</html>
