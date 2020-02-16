<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<head>
    <meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
	<title>Verify Page</title>
</head>
<body style="background-color: #f5f5f5;">
	<div class="text-center p-5">
	<br>
	   <h1>Je wachtwoord is succesvol gewijzigd!</h1>
	   <p class="lead">Log in met je nieuwe wachtwoord.</p>
	</div>

    <div class="container">
	<form action="${pageContext.request.contextPath}/logout" method="post" class="form-signin">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Terug naar inloggen</button>
    </form>
    </div>

	<!-- FOOTER -->
	<footer class="footer mt-5 text-muted text-center text-small">
      <p>&copy; Allenvooreen 2020</p>
    </footer>
</body>
</html>