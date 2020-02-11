<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<head>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script>function displayIframe() {document.getElementById("rick").innerHTML = "<iframe id=\"rickIframe\" src=\"https://player.vimeo.com/video/125353511?autoplay=1&loop=1&autopause=0&muted=0\" height=\"640\" width=\"720\" allow=\"autoplay\"></iframe>";}</script>
</head>
<body>
<div class="container">
    <div class="alert alert-danger" role="alert">
        Foutcode: ${statuscode}
    </div>
    <div>
        <input class="btn btn-primary" type="submit" value="Logout" onclick="displayIframe()" />
        <div id="rick"></div>
    </div>
</div>

</body>
