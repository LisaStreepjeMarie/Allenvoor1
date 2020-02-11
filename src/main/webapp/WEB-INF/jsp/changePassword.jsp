<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
<style>
.password-verdict{
color:#000;
}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script th:src="${pageContext.request.contextPath}/resources/pwstrength.js"></script>
<title th:text="{message.changePassword}">change password</title>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="" th:text="{label.pages.home.title}">home</a>
    </div>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="${pageContext.request.contextPath}/logout" th:text="{label.pages.logout}">logout</a> </li>
      </ul>
    </div>
</nav>
    <div class="container">
        <div class="row">
        <div id="errormsg" class="alert alert-danger" style="display:none"></div>
            <h1 th:text="{message.changePassword}">change password </h1>
            <form >
                <br/>

                    <label class="col-sm-2" th:text="{label.user.oldPassword}">old</label>
                    <span class="col-sm-5"><input class="form-control" id="oldpass" name="oldPassword" type="password" value="" /></span>
                    <span class="col-sm-5"></span>
<br/><br/>
                    <label class="col-sm-2" th:text="{label.user.newPassword}">new</label>
                    <span class="col-sm-5"><input class="form-control" id="password" name="newPassword" type="password" value="" /></span>
                    <div class="col-sm-12"></div>

<br/><br/>
                    <label class="col-sm-2" th:text="{label.user.confirmPass}">confirm</label>
                    <div class="col-sm-5"><input class="form-control" id="matchPassword" name="matchingPassword" type="password" value="" /></div>
                    <div id="globalError" class="alert alert-danger col-sm-12" style="display:none">error</div>


                <div class="col-sm-12">
                <br/><br/>
                <button class="btn btn-primary" type="submit" onclick="savePass()" th:text="{message.changePassword}">change
                </button>
                </div>
            </form>

        </div>
    </div>


<script th:inline="javascript">
var serverContext = [[${pageContext.request.contextPath}/]];

$(document).ready(function () {
    $('form').submit(function(event) {
    	savePass(event);
    });

    $(":password").keyup(function(){
        if($("password").val() != $("matchPassword").val()){
            $("globalError").show().html(/*[[{PasswordMatches.member}]]*/);
        }else{
            $("globalError").html("").hide();
        }
    });

    options = {
            common: {minChar:8},
            ui: {
                showVerdictsInsideProgressBar:true,
                showErrors:true,
                errorMessages:{
                      wordLength: /*[[{error.wordLength}]]*/,
                      wordNotEmail: /*[[{error.wordNotEmail}]]*/,
                      wordSequences: /*[[{error.wordSequences}]]*/,
                      wordLowercase: /*[[{error.wordLowercase}]]*/,
                      wordUppercase: /*[[{error.wordUppercase}]]*/,
                      wordOneNumber: /*[[{error.wordOneNumber}]]*/,
                      wordOneSpecialChar: /*[[{error.wordOneSpecialChar}]]*/
                    }
                }
        };
     $('password').pwstrength(options);
});

function savePass(event){
    event.preventDefault();
    $(".alert").html("").hide();
    $(".error-list").html("");
    if($("password").val() != $("matchPassword").val()){
        $("globalError").show().html(/*[[#{PasswordMatches.user}]]*/);
        return;
    }
    var formData= $('form').serialize();
    $.post(serverContext + "member/updatePassword",formData ,function(data){
    	window.location.href = serverContext + "console.html" + "?message="+data.message;
    })
    .fail(function(data) {
        if(data.responseJSON.error.indexOf("InvalidOldPassword") > -1) {
            $("errormsg").show().append(data.responseJSON.message);
        }
        else if(data.responseJSON.error.indexOf("InternalError") > -1){
            $("errormsg").show().append(data.responseJSON.message);
        }
        else{
        	var errors = $.parseJSON(data.responseJSON.message);
            $.each( errors, function( index,item ){
                $("globalError").show().html(item.defaultMessage);
            });
            errors = $.parseJSON(data.responseJSON.error);
            $.each( errors, function( index,item ){
                $("globalError").show().append(item.defaultMessage+"<br/>");
            });
        }
    });
}

</script>
</body>

</html>