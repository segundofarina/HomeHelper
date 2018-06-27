<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <title>Home-Helper | <spring:message code="general.appointments"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/appointmentConfirmedStyles.css" />" rel="stylesheet" />
</head>

<body>
<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp" />

    <!-- main content -->
    <div class="main-content">

        <div class="container">

            <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <div class="panel">
                        <div class="panel-body">
                            <h3><spring:message code="appointment.confirmed"/></h3>
                            <div class="apt-content">
                                <div class="apt-group">
                                    <h5><spring:message code="form.service-type"/></h5>
                                    <p><spring:message code="service-type.${appointment.serviceType.id}"/></p>
                                </div>
                                <div class="apt-group">
                                    <h5><spring:message code="form.date"/></h5>
                                    <p><c:out value="${appointment.dateDMY}" /></p>
                                </div>
                                <div class="apt-group">
                                    <h5><spring:message code="form.description"/></h5>
                                    <p class="text-format"><c:out value="${appointment.jobDescripcion}" /></p>
                                </div>
                                <div class="apt-group">
                                    <p class="info"><spring:message code="appointment.remember"/></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <div class="panel">
                        <div class="panel-body">
                            <div class="apt-profile">
                                <div class="apt-section">
                                    <c:choose>
                                        <c:when test="${appointment.provider.user.image != null}">
                                            <img src="<c:url value="/profile/${appointment.provider.user.id}/profileimage" />" alt="profile picture" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="apt-section">
                                    <h3><c:out value="${appointment.provider.user.firstname}" /> <c:out value="${appointment.provider.user.lastname}" /></h3>
                                </div>
                                <div class="apt-section">
                                    <div class="stars dyn-stars" data-rating="<c:out value="${appointment.provider.generalCalification}"/>"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div><!-- /main content -->

        <!-- footer content -->
        <footer class="footer">
            <div class="pull-right">
                <spring:message code="index.rights-reserved"/>
            </div>
            <div class="clearfix"></div>
        </footer><!-- /footer content -->

    </div><!-- /container -->

    <!-- jQuery -->
    <script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
    <!-- Bootstrap -->
    <script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>
    <!-- FastClick --
<script src="<c:url value="/resources/adminTemplate/vendors/fastclick/lib/fastclick.js"/>"></script>
<!-- NProgress --
<script src="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.js"/>"></script>-->

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/js/customJs.js"/>"></script>
<script>
    $(document).ready(function () {
        generateStars();
    });
</script>

</body>
</html>
