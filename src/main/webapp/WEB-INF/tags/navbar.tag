<head>
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.12.0/css/all.min.css" rel='stylesheet'>
</head>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
    <a class="navbar-brand"</a>
    <br>
    <br>
    <br>
    <a href="${pageContext.request.contextPath}/home">
    <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="133" height="114"> </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item active">
           <a class="nav-link" href='${pageContext.request.contextPath}/home'><i class="fas fa-home"></i> Home</a>
                <span class="sr-only">(current)</span>
              </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href='${pageContext.request.contextPath}/member/current'><i class="fas fa-user"></i> Mijn profiel</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href='${pageContext.request.contextPath}/team/all'><i class="fas fa-users"></i> Mijn groepen</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fas fa-sign-out-alt"></i> Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>