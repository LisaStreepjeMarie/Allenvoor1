<html>
<body>
    <h1 th:text="${param.message[0]}>Error Message</h1>
    <a th:href="@{/registration.html}"
      th:text="#{label.form.loginSignUp}">signup</a>
</body>
</html>