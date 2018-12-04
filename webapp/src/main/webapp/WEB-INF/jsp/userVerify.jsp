<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="<c:url value="/resources/img/favicon.png"/>"/>

    <title>Home-Helper</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>"
          rel="stylesheet"/>
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>"
          rel="stylesheet"/>
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/userVerifyStyles.css" />" rel="stylesheet"/>
</head>
<body>

<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp"/>

    <!-- main content -->
    <div class="main-content">
        <div class="container">
            <div class="panel">
                <div class="panel-body">


                        <div class="row">
                            <div class="col-xs-12 result">
                                <c:choose>
                                    <c:when test="${client!= null}">
                                        <h1><spring:message code="verify.success.title"/></h1>
                                        <p> <spring:message code="verify.success.detail" arguments="${client.firstname}" /></p>

                                        <div class="bgImg">
                                            <div class="img successImg"></div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <h1><spring:message code="verify.fail.title"/></h1>
                                        <p><spring:message code="verify.fail.detail"/></p>
                                        <div class="bgImg ">
                                            <div class="img failedImg"></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <a href="<c:url value="/"/>" class="btn btn-success"><spring:message code="error.backBtnMsg" /></a>
                            </div>
                        </div>

                </div>
            </div>

        </div>

    </div><!-- /main content -->

    <!-- footer content -->
    <footer class="footer">
        <div class="pull-right">
            © 2018 All rights reserved Home-Helper.com
        </div>
        <div class="clearfix"></div>
    </footer><!-- /footer content -->

</div><!-- /container -->

<!-- jQuery -->
<script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>



</body>
</html>
