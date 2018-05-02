<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="<c:url value="/resources/adminTemplate/images/favicon.png" />">
    <title>Home-Helper - Control Panel</title>
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/resources/adminTemplate/css/lib/bootstrap/bootstrap.min.css" />" rel="stylesheet">
    <!-- Custom CSS -->

    <link href="<c:url value="/resources/adminTemplate/css/lib/calendar2/semantic.ui.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/adminTemplate/css/lib/calendar2/pignose.calendar.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/adminTemplate/css/lib/owl.carousel.min.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/adminTemplate/css/lib/owl.theme.default.min.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/adminTemplate/css/helper.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/adminTemplate/css/style.css" />" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:** -->
    <!--[if lt IE 9]>
    <script src="https:**oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https:**oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href="<c:url value="/resources/css/adminTemplateCustomStyles.css" />" rel="stylesheet">
</head>
<body class="fix-header fix-sidebar">
<!-- Preloader - style you can find in spinners.css -->
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> </svg>
</div>
<!-- Main wrapper  -->
<div id="main-wrapper">
    <!-- header header  -->
    <div class="header">
        <nav class="navbar top-navbar navbar-expand-md navbar-light">
            <!-- Logo -->
            <div class="navbar-header">
                <a class="navbar-brand" href="/">
                    <!-- Logo icon -->
                    <b><img src="<c:url value="/resources/adminTemplate/images/logo.png" />" alt="homepage" class="dark-logo" /></b>
                    <!--End Logo icon -->
                    <!-- Logo text -->
                    <span>Home-Helper</span>
                </a>
            </div>
            <!-- End Logo -->
            <div class="navbar-collapse">
                <!-- toggle and nav items -->
                <ul class="navbar-nav mr-auto mt-md-0">
                    <!-- Main Header Contetnt Empty -->
                </ul>
                <!-- User profile and search -->
                <ul class="navbar-nav my-lg-0">
                    <!-- Comment -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-muted text-muted  " href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fa fa-bell"></i>
                            <div class="notify">
                                <span class="heartbit"></span>
                                <span class="point"></span>
                            </div>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right mailbox animated">
                            <ul>
                                <li>
                                    <div class="drop-title">Notifications</div>
                                </li>
                                <li>
                                    <div class="message-center">
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="btn btn-danger btn-circle m-r-10"><i class="fa fa-link"></i></div>
                                            <div class="mail-contnet">
                                                <h5>This is title</h5> <span class="mail-desc">Just see the my new admin!</span> <span class="time">9:30 AM</span>
                                            </div>
                                        </a>
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="btn btn-success btn-circle m-r-10"><i class="ti-calendar"></i></div>
                                            <div class="mail-contnet">
                                                <h5>This is another title</h5> <span class="mail-desc">Just a reminder that you have event</span> <span class="time">9:10 AM</span>
                                            </div>
                                        </a>
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="btn btn-info btn-circle m-r-10"><i class="ti-settings"></i></div>
                                            <div class="mail-contnet">
                                                <h5>This is title</h5> <span class="mail-desc">You can customize this template as you want</span> <span class="time">9:08 AM</span>
                                            </div>
                                        </a>
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="btn btn-primary btn-circle m-r-10"><i class="ti-user"></i></div>
                                            <div class="mail-contnet">
                                                <h5>This is another title</h5> <span class="mail-desc">Just see the my admin!</span> <span class="time">9:02 AM</span>
                                            </div>
                                        </a>
                                    </div>
                                </li>
                                <li>
                                    <a class="nav-link text-center" href="javascript:void(0);"> <strong>Check all notifications</strong> <i class="fa fa-angle-right"></i> </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <!-- End Comment -->
                    <!-- Messages -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-muted  " href="#" id="2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fa fa-envelope"></i>
                            <div class="notify">
                                <span class="heartbit"></span>
                                <span class="point"></span>
                            </div>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right mailbox animated" aria-labelledby="2">
                            <ul>
                                <li>
                                    <div class="drop-title">You have 4 new messages</div>
                                </li>
                                <li>
                                    <div class="message-center">
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="user-img"> <img src="<c:url value="/resources/adminTemplate/images/users/5.jpg" />" alt="user" class="img-circle"> <span class="profile-status online pull-right"></span> </div>
                                            <div class="mail-contnet">
                                                <h5>Michael Qin</h5> <span class="mail-desc">Just see the my admin!</span> <span class="time">9:30 AM</span>
                                            </div>
                                        </a>
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="user-img"> <img src="<c:url value="/resources/adminTemplate/images/users/2.jpg" />" alt="user" class="img-circle"> <span class="profile-status busy pull-right"></span> </div>
                                            <div class="mail-contnet">
                                                <h5>John Doe</h5> <span class="mail-desc">I've sung a song! See you at</span> <span class="time">9:10 AM</span>
                                            </div>
                                        </a>
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="user-img"> <img src="<c:url value="/resources/adminTemplate/images/users/3.jpg" />" alt="user" class="img-circle"> <span class="profile-status away pull-right"></span> </div>
                                            <div class="mail-contnet">
                                                <h5>Mr. John</h5> <span class="mail-desc">I am a singer!</span> <span class="time">9:08 AM</span>
                                            </div>
                                        </a>
                                        <!-- Message -->
                                        <a href="#">
                                            <div class="user-img"> <img src="<c:url value="/resources/adminTemplate/images/users/4.jpg" />" alt="user" class="img-circle"> <span class="profile-status offline pull-right"></span> </div>
                                            <div class="mail-contnet">
                                                <h5>Michael Qin</h5> <span class="mail-desc">Just see the my admin!</span> <span class="time">9:02 AM</span>
                                            </div>
                                        </a>
                                    </div>
                                </li>
                                <li>
                                    <a class="nav-link text-center" href="javascript:void(0);"> <strong>See all messages</strong> <i class="fa fa-angle-right"></i> </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <!-- End Messages -->
                    <!-- Profile -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-muted  " href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="<c:url value="/resources/adminTemplate/images/users/5.jpg" />" alt="user" class="profile-pic" /></a>
                        <div class="dropdown-menu dropdown-menu-right animated">
                            <ul class="dropdown-user">
                                <li><a href="#"><i class="ti-user"></i> Profile</a></li>
                                <li><a href="#"><i class="ti-settings"></i> Setting</a></li>
                                <li><a href="#"><i class="fa fa-power-off"></i> Logout</a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <!-- End header header -->
    <!-- Left Sidebar  -->
    <div class="left-sidebar">
        <!-- Sidebar scroll-->
        <div class="scroll-sidebar">
            <!-- Sidebar navigation-->
            <nav class="sidebar-nav">
                <ul id="sidebarnav">
                    <li class="nav-devider"></li>
                    <li class="nav-label">Home</li>
                    <li> <a href="/sprovider/<c:out value="${providerId}" />"><i class="fa fa-tachometer"></i><span>Dashboard</span></a></li>
                    <li class="nav-label">Apps</li>
                    <li> <a href="#"><i class="fa fa-comments"></i><span>Messages</span></a></li>
                    <li> <a href="/sprovider/<c:out value="${providerId}" />/posts"><i class="fa fa-th-list"></i><span>Posts</span></a></li>
                </ul>
            </nav>
            <!-- End Sidebar navigation -->
        </div>
        <!-- End Sidebar scroll-->
    </div>
    <!-- End Left Sidebar  -->
    <!-- Page wrapper  -->
    <div class="page-wrapper">
        <!-- Bread crumb -->
        <div class="row page-titles">
            <div class="col-md-5 align-self-center">
                <h3 class="text-primary">Dashboard</h3> </div>
            <div class="col-md-7 align-self-center">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="javascript:void(0)">Home</a></li>
                    <li class="breadcrumb-item active">Dashboard</li>
                </ol>
            </div>
        </div>
        <!-- End Bread crumb -->
        <!-- Container fluid  -->
        <div class="container-fluid">
            <!-- Start Page Content -->
            <div class="row">
                <div class="col-md-3">
                    <div class="card p-30">
                        <div class="media">
                            <div class="media-left meida media-middle">
                                <span><i class="fa fa-usd f-s-40 color-primary"></i></span>
                            </div>
                            <div class="media-body media-text-right">
                                <h2>568120</h2>
                                <p class="m-b-0">Total Revenue</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card p-30">
                        <div class="media">
                            <div class="media-left meida media-middle">
                                <span><i class="fa fa-shopping-cart f-s-40 color-success"></i></span>
                            </div>
                            <div class="media-body media-text-right">
                                <h2>1178</h2>
                                <p class="m-b-0">Sales</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card p-30">
                        <div class="media">
                            <div class="media-left meida media-middle">
                                <span><i class="fa fa-archive f-s-40 color-warning"></i></span>
                            </div>
                            <div class="media-body media-text-right">
                                <h2>25</h2>
                                <p class="m-b-0">Stores</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card p-30">
                        <div class="media">
                            <div class="media-left meida media-middle">
                                <span><i class="fa fa-user f-s-40 color-danger"></i></span>
                            </div>
                            <div class="media-body media-text-right">
                                <h2>847</h2>
                                <p class="m-b-0">Customer</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-title">
                            <h4>Recent Orders </h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Product</th>
                                        <th>quantity</th>
                                        <th>Status</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <tr>
                                        <td>
                                            <div class="round-img">
                                                <a href=""><img src="<c:url value="/resources/adminTemplate/images/users/4.jpg" />" alt=""></a>
                                            </div>
                                        </td>
                                        <td>John Abraham</td>
                                        <td><span>iBook</span></td>
                                        <td><span>456 pcs</span></td>
                                        <td><span class="badge badge-success">Done</span></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="round-img">
                                                <a href=""><img src="<c:url value="/resources/adminTemplate/images/users/2.jpg" />" alt=""></a>
                                            </div>
                                        </td>
                                        <td>John Abraham</td>
                                        <td><span>iPhone</span></td>
                                        <td><span>456 pcs</span></td>
                                        <td><span class="badge badge-success">Done</span></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="round-img">
                                                <a href=""><img src="<c:url value="/resources/adminTemplate/images/users/3.jpg" />" alt=""></a>
                                            </div>
                                        </td>
                                        <td>John Abraham</td>
                                        <td><span>iMac</span></td>
                                        <td><span>456 pcs</span></td>
                                        <td><span class="badge badge-warning">Pending</span></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="round-img">
                                                <a href=""><img src="<c:url value="/resources/adminTemplate/images/users/4.jpg" />" alt=""></a>
                                            </div>
                                        </td>
                                        <td>John Abraham</td>
                                        <td><span>iBook</span></td>
                                        <td><span>456 pcs</span></td>
                                        <td><span class="badge badge-success">Done</span></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="card">
                                <div class="card-title">
                                    <h4>Message </h4>
                                </div>
                                <div class="recent-comment">
                                    <div class="media">
                                        <div class="media-left">
                                            <a href="#"><img alt="..." src="<c:url value="/resources/adminTemplate/images/avatar/1.jpg" />" class="media-object"></a>
                                        </div>
                                        <div class="media-body">
                                            <h4 class="media-heading">john doe</h4>
                                            <p>Cras sit amet nibh libero, in gravida nulla. </p>
                                            <p class="comment-date">October 21, 2018</p>
                                        </div>
                                    </div>
                                    <div class="media">
                                        <div class="media-left">
                                            <a href="#"><img alt="..." src="<c:url value="/resources/adminTemplate/images/avatar/1.jpg" />" class="media-object"></a>
                                        </div>
                                        <div class="media-body">
                                            <h4 class="media-heading">john doe</h4>
                                            <p>Cras sit amet nibh libero, in gravida nulla. </p>
                                            <p class="comment-date">October 21, 2018</p>
                                        </div>
                                    </div>

                                    <div class="media">
                                        <div class="media-left">
                                            <a href="#"><img alt="..." src="<c:url value="/resources/adminTemplate/images/avatar/1.jpg" />" class="media-object"></a>
                                        </div>
                                        <div class="media-body">
                                            <h4 class="media-heading">john doe</h4>
                                            <p>Cras sit amet nibh libero, in gravida nulla. </p>
                                            <p class="comment-date">October 21, 2018</p>
                                        </div>
                                    </div>

                                    <div class="media no-border">
                                        <div class="media-left">
                                            <a href="#"><img alt="..." src="<c:url value="/resources/adminTemplate/images/avatar/1.jpg" />" class="media-object"></a>
                                        </div>
                                        <div class="media-body">
                                            <h4 class="media-heading">Mr. Michael</h4>
                                            <p>Cras sit amet nibh libero, in gravida nulla. </p>
                                            <div class="comment-date">October 21, 2018</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /# card -->
                        </div>
                        <!-- /# column -->
                        <div class="col-lg-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="year-calendar"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>


            <!-- End PAge Content -->
        </div>
        <!-- End Container fluid  -->
        <!-- footer -->
        <footer class="footer"> © 2018 All rights reserved Home-Helper.com</footer>
        <!-- End footer -->
    </div>
    <!-- End Page wrapper  -->
</div>
<!-- End Wrapper -->
<!-- All Jquery -->
<script src="<c:url value="/resources/adminTemplate/js/lib/jquery/jquery.min.js" />"></script>
<!-- Bootstrap tether Core JavaScript -->
<script src="<c:url value="/resources/adminTemplate/js/lib/bootstrap/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/adminTemplate/js/lib/bootstrap/js/bootstrap.min.js" />"></script>
<!-- slimscrollbar scrollbar JavaScript -->
<script src="<c:url value="/resources/adminTemplate/js/jquery.slimscroll.js" />"></script>
<!--Menu sidebar -->
<script src="<c:url value="/resources/adminTemplate/js/sidebarmenu.js" />"></script>
<!--stickey kit -->
<script src="<c:url value="/resources/adminTemplate/js/lib/sticky-kit-master/dist/sticky-kit.min.js" />"></script>
<!--Custom JavaScript -->


<!-- Amchart -->
<script src="<c:url value="/resources/adminTemplate/js/lib/morris-chart/raphael-min.js" />"></script>
<script src="<c:url value="/resources/adminTemplate/js/lib/morris-chart/morris.js" />"></script>
<script src="<c:url value="/resources/adminTemplate/js/lib/morris-chart/dashboard1-init.js" />"></script>


<script src="<c:url value="/resources/adminTemplate/js/lib/calendar-2/moment.latest.min.js" />"></script>
<!-- scripit init-->
<script src="<c:url value="/resources/adminTemplate/js/lib/calendar-2/semantic.ui.min.js" />"></script>
<!-- scripit init-->
<script src="<c:url value="/resources/adminTemplate/js/lib/calendar-2/prism.min.js" />"></script>
<!-- scripit init-->
<script src="<c:url value="/resources/adminTemplate/js/lib/calendar-2/pignose.calendar.min.js" />"></script>
<!-- scripit init-->
<script src="<c:url value="/resources/adminTemplate/js/lib/calendar-2/pignose.init.js" />"></script>

<script src="<c:url value="/resources/adminTemplate/js/lib/owl-carousel/owl.carousel.min.js" />"></script>
<script src="<c:url value="/resources/adminTemplate/js/lib/owl-carousel/owl.carousel-init.js" />"></script>
<script src="<c:url value="/resources/adminTemplate/js/scripts.js" />"></script>
<!-- scripit init-->

<script src="<c:url value="/resources/adminTemplate/js/custom.min.js" />"></script>

</body>
</html>
