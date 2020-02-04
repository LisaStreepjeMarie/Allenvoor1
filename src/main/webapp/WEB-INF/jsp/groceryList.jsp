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
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<!--    <link href="${pageContext.request.contextPath}/css/stylingGroceryList.css" rel="stylesheet" type="text/css"/>-->

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">

<style>


    /* Remove margins and padding from the list */
    .allGroceries ul {
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
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/groceryList.js"></script>
    </div>
</body>
</html>
