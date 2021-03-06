<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
    <head>
        <title>Allen voor 1</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="webpage">
        <div id="container">
            <h1>Welkom bij de 'Allen voor 1' webinterface</h1>
            <p>
                <input class="btn btn-primary" type="submit" value="Nieuwe gebruiker" onclick="window.location='${pageContext.request.contextPath}/member/new';" /><br />
                <br />
                <input class="btn btn-primary" type="submit" value="Toon groepen" onclick="window.location='${pageContext.request.contextPath}/team/all';" /><br />
                <br />
                <input class="btn btn-primary" type="submit" value="Toon afspraken" onclick="window.location='${pageContext.request.contextPath}/event/all';" /><br />
                <br />
                <input class="btn btn-primary" type="submit" value="Kalender" onclick="window.location='${pageContext.request.contextPath}/calendar';" /><br />
                <br />
                <input class="btn btn-primary" type="submit" value="Uitloggen" onclick="window.location='${pageContext.request.contextPath}/logout';" /><br />
            </p>
        </div>
    </body>
</html>