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
        <jsp:include page="controlPanelMenu.jsp" />
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3><spring:message code="general.reviews"/></h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="sprovider.last-reviews"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th><spring:message code="general.name"/></th>
                                        <th><spring:message code="general.date"/></th>
                                        <th><spring:message code="general.review"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${reviews}" var="review">
                                        <tr>
                                            <th>
                                                <img class="profileImage" src="<c:url value="/profile/${review.user.id}/profileimage" />" alt="profile picture" />
                                            </th>
                                            <td><c:out value="${review.user.firstname}" /> <c:out value="${review.user.lastname}" /></td>
                                            <td><c:out value="${review.date}" /></td>
                                            <td>
                                                <div class="stars dyn-stars" data-rating="<c:out value="${review.generalCalification}"/>"></div>
                                            </td>
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
<script src="<c:url value="/resources/js/customJs.js"/>"></script>
<script>
    $(document).ready(function(){
        generateStars();
    });
</script>
</body>
</html>