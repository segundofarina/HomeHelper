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

        form {
            display: inline-block;
        }

        .btn-xs {
            padding: 2px 10px;
        }
    </style>
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
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${appointmentsPending}" var="appointment">
                                            <tr>
                                                <th>
                                                    <img class="profileImage" src="<c:url value="/profile/${appointment.client.id}/profileimage" />" alt="profile picture" />
                                                </th>
                                                <td><c:out value="${appointment.client.firstname}" /></td>
                                                <td><c:out value="${appointment.date}" /></td>
                                                <td><c:out value="${appointment.address}" /> </td>
                                                <td><span class="label label-<spring:message code="css.status.${appointment.status.toString()}" />"><spring:message code="status.${appointment.status.toString()}" /></span></td>
                                                <c:choose>
                                                    <c:when test="${appointment.status.numVal == 1}">
                                                        <td>
                                                            <form action="<c:url value="/sprovider/acceptAppointment" />" method="post" class="pull-right">
                                                                <input type="hidden" value="<c:out value="${appointment.appointmentId}" />" name="appointmentId" />
                                                                <button type="submit" class="btn btn-success btn-xs"><spring:message code="general.accept" /></button>
                                                            </form>
                                                            <form action="<c:url value="/sprovider/rejectAppointment" />" method="post" class="pull-right">
                                                                <input type="hidden" value="<c:out value="${appointment.appointmentId}" />" name="appointmentId" />
                                                                <button type="submit" class="btn btn-danger btn-xs"><spring:message code="general.reject" /></button>
                                                            </form>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>
                                                            <form action="<c:url value="/sprovider/completeAppointment" />" method="post" class="pull-right">
                                                                <input type="hidden" value="<c:out value="${appointment.appointmentId}" />" name="appointmentId" />
                                                                <button type="submit" class="btn btn-warning btn-xs"><spring:message code="general.complete" /></button>
                                                            </form>
                                                        </td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>
                                        </c:forEach>
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
                                                <img class="profileImage" src="<c:url value="/profile/${appointment.client.id}/profileimage" />" alt="profile picture" />
                                            </th>
                                            <td><c:out value="${appointment.client.firstname}" /></td>
                                            <td><c:out value="${appointment.date}" /></td>
                                            <td><c:out value="${appointment.address}" /> </td>
                                            <td><span class="label label-<spring:message code="css.status.${appointment.status.toString()}" />"><spring:message code="status.${appointment.status.toString()}" /></span></td>
                                        </tr>
                                    </c:forEach>
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