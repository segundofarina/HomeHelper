<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="<c:url value="/resources/img/favicon.png"/>"/>

    <title>Home-Helper | <spring:message code="sprovider.control-panel"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/adminTemplate/build/css/custom.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/adminTemplateCustomStyles.css"/>" rel="stylesheet">
    <style>
        .profileImage {
            height: 38px;
            width: 38px;
            border-radius: 100%;
        }

        .label {
            font-size: 85%;
        }
    </style>
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <jsp:include page="leftBarMenu.jsp" />

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <img src="<c:url value="/resources/img/img.jpg"/>" alt="">John Doe
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="<c:url value="/" />"><spring:message code="general.switchToClient" /></a></li>
                                <li><a href="javascript:;"><spring:message code="general.settings"/></a></li>
                                <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i> <spring:message code="general.logout"/></a></li>
                            </ul>
                        </li>

                        <li role="presentation" class="dropdown">
                            <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                                <i class="fa fa-envelope-o"></i>
                                <span class="badge bg-green">6</span>
                            </a>
                            <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                                <li>
                                    <a>
                                        <span class="image"><img src="<c:url value="/resources/img/img.jpg"/>" alt=" Profile Image" /></span>
                                        <span>
                                          <span>John Smith</span>
                                          <span class="time">3 mins ago</span>
                                        </span>
                                        <span class="message">
                                          Film festivals used to be do-or-die moments for movie makers. They were where...
                                        </span>
                                    </a>
                                </li>
                                <li>
                                    <a>
                                        <span class="image"><img src="<c:url value="/resources/img/img.jpg"/>" alt="Profile Image"/></span>
                                        <span>
                                            <span>John Smith</span>
                                            <span class="time">3 mins ago</span>
                                        </span>
                                        <span class="message">
                                            Film festivals used to be do-or-die moments for movie makers. They were where...
                                        </span>
                                    </a>
                                </li>
                                <li>
                                    <div class="text-center">
                                        <a>
                                            <strong><spring:message code="sprovider.see-alerts"/></strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3><spring:message code="general.appointments"/></h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="sprovider.pending-appointments"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th><spring:message code="general.name"/></th>
                                        <th><spring:message code="general.date"/></th>
                                        <th><spring:message code="general.address"/></th>
                                        <th><spring:message code="general.status"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${appointmentsPending}" var="appointment">
                                            <tr>
                                                <th>
                                                    <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                                </th>
                                                <td><c:out value="${appointment.client.firstname}" /></td>
                                                <td><c:out value="${appointment.date}" /></td>
                                                <td><c:out value="${appointment.address}" /> </td>
                                                <td><span class="label label-<spring:message code="css.status.${appointment.status.toString()}" />"><spring:message code="status.${appointment.status.toString()}" /></span></td>
                                            </tr>
                                        </c:forEach>

                                        <!--<tr>
                                            <th>
                                                <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                            </th>
                                            <td>Mark</td>
                                            <td>May 20, 2018</td>
                                            <td>Santa Fe 1000, Buenos Aires</td>
                                            <td><span class="label label-info">Confirmed</span></td>
                                        </tr>
                                        <tr>
                                            <th>
                                                <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                            </th>
                                            <td>Mark</td>
                                            <td>May 20, 2018</td>
                                            <td>Santa Fe 1000, Buenos Aires</td>
                                            <td><span class="label label-warning">Pending</span></td>
                                        </tr>
                                        <tr>
                                            <th>
                                                <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                            </th>
                                            <td>Mark</td>
                                            <td>May 20, 2018</td>
                                            <td>Santa Fe 1000, Buenos Aires</td>
                                            <td><span class="label label-info">Confirmed</span></td>
                                        </tr>-->
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="sprovider.completed-appointments"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th><spring:message code="general.name"/></th>
                                        <th><spring:message code="general.date"/></th>
                                        <th><spring:message code="general.address"/></th>
                                        <th><spring:message code="general.status"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach items="${appointmentsDone}" var="appointment">
                                        <tr>
                                            <th>
                                                <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                            </th>
                                            <td><c:out value="${appointment.client.firstname}" /></td>
                                            <td><c:out value="${appointment.date}" /></td>
                                            <td><c:out value="${appointment.address}" /> </td>
                                            <td><span class="label label-<spring:message code="css.status.${appointment.status.toString()}" />"><spring:message code="status.${appointment.status.toString()}" /></span></td>
                                        </tr>
                                    </c:forEach>
<!--
                                    <tr>
                                        <th>
                                            <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                        </th>
                                        <td>Mark</td>
                                        <td>May 20, 2018</td>
                                        <td>Santa Fe 1000, Buenos Aires</td>
                                        <td><span class="label label-success">Done</span></td>
                                    </tr>
                                    <tr>
                                        <th>
                                            <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                        </th>
                                        <td>Mark</td>
                                        <td>May 20, 2018</td>
                                        <td>Santa Fe 1000, Buenos Aires</td>
                                        <td><span class="label label-success">Done</span></td>
                                    </tr>
                                    <tr>
                                        <th>
                                            <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                        </th>
                                        <td>Mark</td>
                                        <td>May 20, 2018</td>
                                        <td>Santa Fe 1000, Buenos Aires</td>
                                        <td><span class="label label-success">Done</span></td>
                                    </tr>
                                    -->
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div class="pull-right">
                <spring:message code="index.rights-reserved"/>
            </div>
            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
</div>

<!-- jQuery -->
<script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>
<!-- FastClick -->
<script src="<c:url value="/resources/adminTemplate/vendors/fastclick/lib/fastclick.js"/>"></script>
<!-- NProgress -->
<script src="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.js"/>"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/adminTemplate/build/js/custom.min.js"/>"></script>
</body>
</html>