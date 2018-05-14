<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home-Helper | <spring:message code="general.login"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />

    <!-- Custom Styles -->
    <link href="<c:url value="/resources/css/loginStyles.css"/>" rel="stylesheet" />
</head>

<body>
<div class="login-clean">
    <c:url value="/login" var="loginUrl" />
    <form method="post" action="<c:out value="${loginUrl}"/>" enctype="application/x-www-form-urlencoded">
        <div class="illustration">
            <img src="<c:url value="/resources/img/HHLogo.png"/>" alt="Home Helper Logo" />
        </div>
        <h2>Home-Helper</h2>
        <div class="form-group">
            <input class="form-control" type="text" name="username" placeholder="<spring:message code="general.email"/>"/>
        </div>
        <div class="form-group">
            <input class="form-control" type="password" name="password" placeholder="<spring:message code="general.password"/>"/>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="rememberme" /> <spring:message code="user.rememberme"/>
            </label>
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit"><spring:message code="general.login"/></button>
        </div>
        <div class="forgot"><spring:message code="user.new-user"/>?<a href="<c:url value="/signup"/>"><spring:message code="general.signup"/>!</a></div>
    </form>
</div>
<!-- jQuery -->
<script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>
</body>

</html>