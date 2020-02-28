
// Creates a time interval to catch the items from the database every second
$(document).ready(function(){
 setInterval(getAllItemsFromDataBase,1000);
});

// set up to send a CSRF token with ajax for postmapping
$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

// setting the context url
var ctx = $('#contextPathHolder').attr('data-contextPath');

// create a new list item when clicking on the "Add" button
function newElement() {

// creating a new groceryItem to save
  newGroceryItem = {
        title: document.getElementById("newGroceryItem").value,
  }

// checking if the input isn't zero otherwise forwarding the item to a save ajax function
  if (newGroceryItem.title === '') {
    alert("Niks kan je niet kopen bij de supermarkt!");
  } else {
    addGroceryItem(newGroceryItem);
  }
  // resetting the field for next grocery item
    document.getElementById("newGroceryItem").value = "";
}

// giving the enter button the addbutton click function
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
        },
        error: function(e) {
            alert("boodschappenerror")
            console.log("ERROR: ",  e);
        }
    });
}

// sets the item to bought in the database
function boughtBoolean(type, itemId){
    $.ajax({
         type:'GET',
         url: ctx + "/bought/" + type + "/" + itemId,
         success : function(result) {
             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });
}

// makes a list of all the checked items and deletes them
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

// deletes all items in list
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

// this gets all the list items from the database. Atm it clears the whole list and then refills it
// could be done cleaner with if result changes or something
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

// adding a an X to all items and giving both the x and the item a click function
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
// every item on the list gets the delete button with the correct className for later function use
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

// deletes the chosen  item, isn't expecting anything back (besides success)
// can be used for both a medical and grocery item (hence the type and Id)
function removeGroceryItem(type, itemId){
    $.ajax({
         type:'GET',
         url: ctx + "/delete/" + type + "/" + itemId,
         success : function(result) {
             },
             error : function(e) {
             console.log("ERROR: ", e);
         }
    });
}
