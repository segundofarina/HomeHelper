<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Home-Helper | Control Panel</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/adminTemplate/build/css/custom.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/adminTemplateCustomStyles.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/controlPanelMessages.css"/>" rel="stylesheet">
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
                                <li><a href="javascript:;">Settings</a></li>
                                <li><a href="/login"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
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
                                            <strong>See All Alerts</strong>
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
                        <h3>Messages</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_content">
                                <!-- Message content -->

                                <div class="row">
                                    <div class="col-sm-3 messageThumbnails">

                                        <c:forEach items="${chats}" var="chat">
                                            <c:url value="/sprovider/${providerId}/messages/${chat.grey.id}" var="chatThreadUrl" />
                                            <a href="${chatThreadUrl}" class="thumbItem">
                                                <div class="row">
                                                    <div class="col-sm-3">
                                                        <div>
                                                            <img class="roundedImg" src="<c:url value="/resources/img/img.jpg"/>" alt="Profile Image" />
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-9">
                                                        <h4><c:out value="${chat.green.firstname}" /> <c:out value="${chat.green.lastname}" /></h4>
                                                        <p><c:out value="${chat.preview}" /></p>
                                                    </div>
                                                </div>
                                            </a>
                                        </c:forEach>

                                    </div>

                                    <div class="col-sm-6 messageContent">
                                        <div class="scrollableContent">

                                            <c:forEach items="${currentChat.messages}" var="msg">
                                                <div class="clearfix">
                                                    <c:choose>
                                                        <c:when test="${msg.from == providerId}">
                                                            <div class="myMsg">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="otherMsg">
                                                        </c:otherwise>
                                                    </c:choose>

                                                        <p><c:out value="${msg.message}" /></p>
                                                    </div>
                                                </div>
                                            </c:forEach>

                                        </div>
                                        <div class="fields">
                                            <c:url value="/sprovider/${providerId}/messages/${currentChat.grey.id}" var="msgPostPath" />
                                            <form action="<c:out value="${msgPostPath}" />" method="POST">
                                                <div>
                                                    <textarea name="msg" placeholder="Write a message..."></textarea>
                                                </div>
                                                <div>
                                                    <input type="submit" value="send" class="btn btn-success" />
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-sm-3 appointmentDetails">
                                        <div class="profilePicture">
                                            <img src="<c:url value="/resources/img/img.jpg"/>" alt="Profile Image" />
                                        </div>
                                        <div class="stars">
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star-half-empty"></i>
                                            <i class="fa fa-star-o"></i>
                                        </div>
                                        <div class="address">Santa Fe 1000, Buenos Aires</div>
                                        <div class="calendar"></div>
                                    </div>
                                </div>

                                <!-- End Message content  -->
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
                © 2018 All rights reserved Home-Helper.com
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

<script type="text/javascript">
    $(document).ready(function () {
        var scrollableContent = $(".scrollableContent");
        scrollableContent.scrollTop(scrollableContent[0].scrollHeight);
    });
</script>
</body>
</html>