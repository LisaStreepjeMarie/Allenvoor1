<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</head>
<body>
<div sec:authorize="hasAuthority('CHANGE_PASSWORD_PRIVILEGE')">
    <h1 text="{message.resetYourPassword}">reset</h1>
    <form action="${pageContext.request.contextPath}/member/savePassword">
        <label text="{label.user.password}">password</label>
        <input id="password" name="newPassword" type="password" value="" />

        <label text="{label.user.confirmPass}">confirm</label>
        <input id="matchPassword" type="password" value="" />

        <div id="globalError" style="display:none"
          text="{PasswordMatches.user}">error</div>
        <button type="submit" onclick="savePass()"
          text="{message.updatePassword}">submit</button>
    </form>
    <script>


$(document).ready(function () {
        savePass(event);
    });

    $(":password").keyup(function(){
        if($("password").val() != $("matchPassword").val()){
            $("globalError").show().html(/*[[{PasswordMatches.user}]]*/);
        }else{
            $("globalError").html("").hide();
        }
    });
});

function savePass(event){
    event.preventDefault();
    if($("password").val() != $("matchPassword").val()){
        $("globalError").show().html(/*[[{PasswordMatches.user}]]*/);
        return;
    }
    var formData= $('form').serialize();
    $.post("allenvooreen/member/savePassword",formData ,function(data){
        window.location.href = serverContext + "login?message="+data.message;
    })
    .fail(function(data) {
        if(data.responseJSON.error.indexOf("InternalError") > -1){
            window.location.href = serverContext + "login?message=" + data.responseJSON.message;
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
</div>
</body>
</html>