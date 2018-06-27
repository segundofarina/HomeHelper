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
    <link href="<c:url value="/resources/css/dashboard.css"/>" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <jsp:include page="leftBarMenu.jsp" />

        <!-- top navigation -->
        <jsp:include page="controlPanelMenu.jsp" />
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">

                <!-- top tiles -->
                <div class="row tile_count">
                    <div class="col-md-3 col-sm-3 col-xs-6 tile_stats_count">
                        <span class="count_top"><i class="fa fa-user"></i> <spring:message code="dashboard.tiles.aptitudes" /></span>
                        <div class="count"><c:out value="${totalAp}" /></div>
                    </div>
                    <div class="col-md-3 col-sm-3 col-xs-6 tile_stats_count">
                        <span class="count_top"><i class="fa fa-area-chart"></i>  <spring:message code="dashboard.tiles.generalReview" /></span>
                        <div class="count"><c:out value="${generalCal}" /></div>
                    </div>
                    <div class="col-md-3 col-sm-3 col-xs-6 tile_stats_count">
                        <span class="count_top"><i class="fa fa-archive"></i>  <spring:message code="dashboard.tiles.completedAp" /></span>
                        <div class="count green"><c:out value="${doneAp}" /></div>
                    </div>
                    <div class="col-md-3 col-sm-3 col-xs-6 tile_stats_count">
                        <span class="count_top"><i class="fa fa-book"></i>  <spring:message code="dashboard.tiles.pendingAp" /></span>
                        <div class="count green"><c:out value="${pendingAp}" /></div>
                    </div>
                </div>
                <!-- /top tiles -->


                <!-- appointments, msg, and reviews cols -->

                <div class="row dash-items">
                    <div class="col-md-4">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="dashboard.appointments.title" /></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <c:choose>
                                    <c:when test="${appointments.size() == 0}">
                                        <div class="empty-list ap">
                                            <div class="img"></div>
                                            <p><spring:message code="empty-pendingAppointment" /></p>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${appointments}" var="ap">
                                            <article class="media event">
                                                <a href="<c:url value="/sprovider/appointments" />">
                                                    <div class="pull-left date">
                                                        <p class="month"><spring:message code="month.${ap.month}" /></p>
                                                        <p class="day"><c:out value="${ap.day}" /></p>
                                                    </div>
                                                    <div class="media-body">
                                                        <h5 class="title"><c:out value="${ap.client.firstname}" /></h5>
                                                        <p><spring:message code="service-type.${ap.serviceType.id}"/></p>
                                                    </div>
                                                </a>
                                            </article>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="dashboard.msg.title" /></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <c:choose>
                                    <c:when test="${chats.size() == 0}">
                                        <div class="empty-list msg">
                                            <div class="img"></div>
                                            <p><spring:message code="emptyMsg" /></p>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${chats}" var="chat">
                                            <article class="media event">
                                                <a href="<c:url value="/sprovider/messages/${chat.grey.id}" />">
                                                    <div class="pull-left img">
                                                        <c:choose>
                                                            <c:when test="${chat.grey.image != null}">
                                                                <img src="<c:url value="/profile/${chat.grey.id}/profileimage" />" alt="profile picture" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="media-body">
                                                        <h5 class="title"><c:out value="${chat.grey.firstname}" /></h5>
                                                        <p><c:out value="${chat.preview}" /></p>
                                                    </div>
                                                </a>
                                            </article>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="dashboard.reviews.title" /></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <c:choose>
                                    <c:when test="${reviews.size() == 0}">
                                        <div class="empty-list review">
                                            <div class="img"></div>
                                            <p><spring:message code="emptyStars" /></p>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${reviews}" var="review">
                                            <article class="media event">
                                                <a href="<c:url value="/sprovider/reviews" />">
                                                    <div class="pull-left img">
                                                        <c:choose>
                                                            <c:when test="${review.user.image != null}">
                                                                <img src="<c:url value="/profile/${review.user.id}/profileimage" />" alt="profile picture" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="media-body">
                                                        <h5 class="title"><c:out value="${review.user.firstname}" /></h5>
                                                        <div class="stars dyn-stars" data-rating="<c:out value="${review.generalCalification}"/>"></div>
                                                    </div>
                                                </a>
                                            </article>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
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
<script src="<c:url value="/resources/js/customJs.js"/>"></script>

<script>
    $(document).ready(function () {
        generateStars();
    });
</script>
</body>
</html>