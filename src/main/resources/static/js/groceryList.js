// set up to send a CSRF token with ajax for postmapping
$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

var ctx = $('#contextPathHolder').attr('data-contextPath');

// Create a "close" button and append it to each list item
// Also makes sure the item is deleted when clicked
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

// every item on the list gets the delete button with the first part of this function
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

// this part makes sure the item gets deleten when clicked and also the X dissapears
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
var allGroceriesInList = document.getElementsByTagName("LI");
var i;
for (i = 0; i < allGroceriesInList.length; i++) {
      var itemValue = allGroceriesInList[i].value;
      var type = allGroceriesInList[i].parentNode.id;
      removeGroceryItem(type, itemValue);
      allGroceriesInList[i].style.display = "none";
    }
}