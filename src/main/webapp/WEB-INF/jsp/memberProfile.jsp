<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
    <head>
        <script src="/webjars/jquery/3.4.1/jquery.slim.min.js"></script>

        <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <script src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

        <link href="/webjars/font-awesome/5.0.6/web-fonts-with-css/css/fontawesome-all.min.css" rel='stylesheet'>
        <script src="/webjars/font-awesome/5.0.6/on-server/js/fontawesome-all.min.js"></script>
        <script src="/webjars/font-awesome/5.0.6/on-server/js/fa-solid.min.js"></script>
        <title>Overzicht gebruiker</title>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-1 collapse show d-*-flex bg-light pt-2 pl-0 min-vh-100" id="sidebar">
                <ul class="nav flex-column flex-nowrap overflow-hidden">
                    <li class="nav-item">
                        <a class="nav-link text-truncate" href="#"><i class="fa fa-home"></i> <span class="d-none d-sm-inline">Overview</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link collapsed text-truncate" href="#submenu1" data-toggle="collapse" data-target="#submenu1"><i class="fa fa-table"></i> <span class="d-none d-sm-inline">Reports</span></a>
                        <div class="collapse" id="submenu1" aria-expanded="false">
                            <ul class="flex-column pl-2 nav">
                                <li class="nav-item"><a class="nav-link py-0" href="#"><span>Orders</span></a></li>
                                <li class="nav-item">
                                    <a class="nav-link collapsed py-1" href="#submenu1sub1" data-toggle="collapse" data-target="#submenu1sub1"><span>Customers</span></a>
                                    <div class="collapse" id="submenu1sub1" aria-expanded="false">
                                        <ul class="flex-column nav pl-4">
                                            <li class="nav-item">
                                                <a class="nav-link p-1" href="#">
                                                    <i class="fa fa-fw fa-clock-o"></i> Daily </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link p-1" href="#">
                                                    <i class="fa fa-fw fa-dashboard"></i> Dashboard </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link p-1" href="#">
                                                    <i class="fa fa-fw fa-bar-chart"></i> Charts </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link p-1" href="#">
                                                    <i class="fa fa-fw fa-compass"></i> Areas </a>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="nav-item"><a class="nav-link text-truncate" href="#"><i class="fa fa-bar-chart"></i> <span class="d-none d-sm-inline">Analytics</span></a></li>
                    <li class="nav-item"><a class="nav-link text-truncate" href="#"><i class="fa fa-download"></i> <span class="d-none d-sm-inline">Export</span></a></li>
                </ul>
            </div>
            <div class="col pt-2">
                <div id="container" class="mt-3 col-12 form-inline toolbox-top clearfix">
                    <p>
                        <input class="btn btn-primary" type="submit" value="sidebar" data-target="#sidebar" data-toggle="collapse" class="d-*-none" />
                        <input class="btn btn-primary" type="submit" value="Home" onclick="window.location='/';" />
                        <input class="btn btn-primary" type="submit" value="Jouw groepen" onclick="window.location='/team/all';" />
                        <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='/logout';" />
                    </p>
                </div>
                <div class="mt-3 col-12">
                    <h1>Hallo ${currentmember.membername} !</h1>
                    <h5>Wijzig hieronder je gebruikersnaam of verwijder je profiel</h5>
                </div>
                <div class="mt-3 col-12 form-inline toolbox-top clearfix">
                    <form:form action="/member/change" modelAttribute="currentmember">
                        <form:input path="memberId" type="hidden" />
                        <table>
                            <tr>
                                <td>
                                    <h7>Gebruikersnaam:
                                        <form:input path="membername" value="${members.membername}" /></form> </h7>
                                    <input type="submit" class= "btn btn-primary" value="Wijzigen" />
                                </td>
                            </tr>
                            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                <strong>Let op!</strong> Bij wijziging van je gebruikersnaam is opnieuw inloggen vereist.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </table>
                    </form:form>
                    <form:form action="/member/delete" method="get" modelAttribute="currentmember">
                        <form:input path="memberId" type="hidden" />
                        <table>
                            <tr>
                                <td>
                                    <input type="submit" class= "btn btn-primary" value="Verwijder je profiel" />
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
        </div>
    </div>

    </body>
</html>