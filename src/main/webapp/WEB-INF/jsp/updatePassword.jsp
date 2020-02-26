<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:spring="">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
        <title>Wijzig je wachtwoord</title>

        <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>

        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet" type="text/css"/>
    </head>

    <body class="text-center">
        <div class="container">
                <form class="form-signin" method="post" action="${pageContext.request.contextPath}/member/changePassword">
                <h1 class="h3 mb-3 font-weight-light"><br> Voer hier je nieuwe wachtwoord in:</h1>


        <form:form action="${pageContext.request.contextPath}/member/changePassword" type= "post">
                <input id="password" name="newPassword" type="password" value="" placeholder="Wachtwoord" class="form-control" />
                <form:errors path="password"></form:errors>



            <input id="matchPassword" type="password" value=""  placeholder= "Bevestig wachtwoord" class="form-control"/>
            <form:errors path="passwordConfirm"></form:errors>
          </div>

         <br>
         <div class="container">
         <div id="globalError" style="display:none" text="{PasswordMatches.user}">error</div>
                 <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="savePass()"
                   text="{message.updatePassword}">Voer in</button>
                   </div>
            </form:form>

            <form action="${pageContext.request.contextPath}/logout" method="post" class="form-signin" >
                <div class="container">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button class="btn btn-lg btn-primary btn-block" type="submit">Inloggen</button>
                </div>
                <p class="mt-5 mb-3 text-muted">&copy; 2020 Allenvooreen</p>
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
                $.post("allenvooreen/member/changePassword",formData ,function(data){
                    window.location.href = "allenvooreen/login?message=" + data.message;
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
    </body>
</html>




























