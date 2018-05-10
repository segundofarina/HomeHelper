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
    <link href="<c:url value="/resources/css/signupStyles.css" />" rel="stylesheet"/>
</head>
<body>

<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp"/>

    <!-- main content -->
    <div class="main-content">
        <div class="container">
        <div class="panel searchForm">
            <div class="panel-body">
                <c:url value="/createUser" var="postPath"/>
                <form:form modelAttribute="signUpForm" action="${postPath}" method="Post">
                    <div class="row">
                        <div class="col-xs-12 col-sm-6">
                            <div class="form-group">
                                <form:label path="firstname"><spring:message code="general.firstname"/>:</form:label>
                                <form:input type="text" path="firstname" cssClass="form-control"/>
                                <form:errors path="firstname" element="p" cssClass="form-error"/>
                            </div>

                            <div class="form-group">
                                <form:label path="lastname"><spring:message code="general.lastname"/>:</form:label>
                                <form:input type="text" path="lastname" cssClass="form-control"/>
                                <form:errors path="lastname" element="p" cssClass="form-error"/>
                            </div>
                            <div class="form-group">
                                <form:label path="email"><spring:message code="signup.email"/>:</form:label>
                                <form:input type="text" path="email" cssClass="form-control"/>
                                <form:errors path="email" element="p" cssClass="form-error"/>
                            </div>
                            <div class="form-group">
                                <form:label path="phone"><spring:message code="signup.phone"/>:</form:label>
                                <form:input type="text" path="phone" cssClass="form-control"/>
                                <form:errors path="phone" element="p" cssClass="form-error"/>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-6">
                            <div class="form-group">
                                <form:label path="username"><spring:message code="signup.username"/>:</form:label>
                                <form:input type="text" path="username" cssClass="form-control"/>
                                <form:errors path="username" element="p" cssClass="form-error"/>
                            </div>
                            <div class="form-group">
                                <form:label path="password"><spring:message code="signup.password"/>:</form:label>
                                <form:input type="password" path="password" cssClass="form-control"/>
                                <form:errors path="password" element="p" cssClass="form-error"/>
                            </div>
                            <div class="form-group">
                                <form:label path="repeatPassword"><spring:message code="signup.repeatPassword"/>:</form:label>
                                <form:input type="password" path="repeatPassword" cssClass="form-control"/>
                                <form:errors path="repeatPassword" element="p" cssClass="form-error"/>
                            </div>

                        </div>
                    </div>




                    <form:button type="submit" class="btn btn-success"><spring:message
                            code="singup.submit"/></form:button>
                </form:form>
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
