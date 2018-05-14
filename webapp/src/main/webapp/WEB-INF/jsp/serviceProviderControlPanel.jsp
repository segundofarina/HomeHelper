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
                        <h3>Plain Page</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Plain Page</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <spring:message code="sprovider.add-content"/>
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