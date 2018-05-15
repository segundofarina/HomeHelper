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
    <link href="<c:url value="/resources/css/clientAppointmentsStyles.css" />" rel="stylesheet" />
</head>

<body>
<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="../clientMenu.jsp" />

    <!-- main content -->
    <div class="main-content">

        <div class="container">

            <div class="panel">
                <div class="panel-title">
                    <h3><spring:message code="client.pending-appointments"/></h3>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th></th>
                                <th><spring:message code="general.service-type"/></th>
                                <th><spring:message code="general.name"/></th>
                                <th><spring:message code="general.date"/></th>
                                <th><spring:message code="general.status"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${appointmentsPending}" var="appointment">
                                <tr>
                                    <th>
                                        <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                    </th>
                                    <td><c:out value="${appointment.serviceType.name}" /></td>
                                    <td><c:out value="${appointment.provider.firstname}" /></td>
                                    <td><c:out value="${appointment.date}" /></td>
                                    <td><span class="label label-<spring:message code="css.status.${appointment.status.toString()}" />"><spring:message code="status.${appointment.status.toString()}" /></span></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel">
                <div class="panel-title">
                    <h3><spring:message code="client.completed-appointments"/></h3>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th></th>
                            <th><spring:message code="general.service-type"/></th>
                            <th><spring:message code="general.name"/></th>
                            <th><spring:message code="general.date"/></th>
                            <th><spring:message code="general.status"/></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${appointmentsDone}" var="appointment">
                            <tr>
                                <th>
                                    <img class="profileImage" src="<c:url value="/resources/img/img.jpg" />" alt="Profile Image" />
                                </th>
                                <td><c:out value="${appointment.serviceType.name}" /></td>
                                <td><c:out value="${appointment.provider.firstname}" /></td>
                                <td><c:out value="${appointment.date}" /></td>
                                <td><span class="label label-<spring:message code="css.status.${appointment.status.toString()}" />"><spring:message code="status.${appointment.status.toString()}" /></span></td>
                                <td>
                                    <a href="<c:url value="/client/writeReview/${appointment.appointmentId}" />" class="btn btn-primary btn-xs">
                                        <i class="fa fa-edit"></i><spring:message code="client.write-review"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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
