<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
<title th:text="{message.resetPassword}">reset</title>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1 text="{message.resetPassword}">reset</h1>
    <br/>
    <div class="row">
      <form method="post">
       <label th:text="{label.user.email}">email</label>
          <input id="email" name="email" type="email" value="" />
          <button type="submit" onclick="resetPass()"th:text="{message.resetPassword}">reset</button>
      </form>
    </div>

<br/>
<a class="btn btn-default" href="${pageContext.request.contextPath}/member/new" text="{label.form.loginSignUp}">registration</a>
<br/><br/>
<a class="btn btn-default" href="${pageContext.request.contextPath}/login" text="{label.form.loginLink}">login</a>

</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script th:inline="javascript">
var serverContext = [$pageContext.request.contextPath{/}];

$(document).ready(function () {
	$('form').submit(function(event) {
		resetPass(event);
    });
});

function resetPass(event){
    event.preventDefault();
    var email = $("email").val();
    $.post(serverContext + "member/resetPassword",{email: email} ,function(data){
            window.location.href = serverContext + "login?message=" + data.message;
    })
    .fail(function(data) {
    	if(data.responseJSON.error.indexOf("MailError") > -1)
        {
            window.location.href = serverContext + "emailError.jsp";
        }
        else{
            window.location.href = serverContext + "login?message=" + data.responseJSON.message;
        }
    });
}

$(document).ajaxStart(function() {
    $("title").jsp("LOADING ...");
});
</script>
</body>

</html>

































