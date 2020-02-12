<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
<title text="message.resetPassword">reset</title>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1>reset</h1>
    <br/>
    <div class="row">
      <form action="${pageContext.request.contextPath}/member/resetPassword">
        <label class="col-sm-1">email</label>
        <span class="col-sm-5"><input class="form-control" id="email" name="email" type="email" value="" required="required"/></span>
        <button class="btn btn-primary" type="submit">reset</button>
      </form>
    </div>

<br/>
<a class="btn btn-default" href="${pageContext.request.contextPath}/newMember.jsp" >registration</a>
<br/><br/>
<a class="btn btn-default" href="${pageContext.request.contextPath}/login" >login</a>

</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>


$(document).ready(function () {
		resetPass();
    });
});

function resetPass(){

    var email = $("email");
    $.post("allenvooreen/member/resetPassword", {email: email} ,function(data){
            window.location.href = "allenvooreen/login?message=" + data.message;
    })
    .fail(function(data) {
    	if(data.responseJSON.error.indexOf("MailError") > -1)
        {
            window.location.href = serverContext + "emailError.html";
        }
        else{
            window.location.href = serverContext + "login?message=" + data.responseJSON.message;
        }
    });
}

$(document).ajaxStart(function() {
    $("title").html("LOADING ...");
});


</script>
</body>

</html>































