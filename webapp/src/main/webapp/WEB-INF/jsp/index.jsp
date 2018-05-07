<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="<c:url value="/resources/img/favicon.png"/>"/>

    <title>Home-Helper</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/indexStyles.css" />" rel="stylesheet" />
</head>

<body>
<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp" />

    <!-- main content -->
    <div class="main-content">
        <div class="bgImg">
            <div class="img"></div>
        </div>

        <div class="left-screen">
            <div class="container-fluid">
                <h1>Some Text</h1>
                <div class="searchForm">
                    <c:url value="/search" var="postPath"/>
                    <form:form modelAttribute="searchForm" action="${postPath}" method="Post">
                        <div class="form-group">
                            <form:input type="text" path="cityId" class="form-control" placeholder="Write your city..." />
                        </div>
                        <div class="form-group">
                            <form:select path="serviceTypeId" class="form-control" >
                        <c:forEach items="${serviceTypes}" var="st">
                            <option value="<c:out value="${st.serviceTypeId}"/>"><c:out value="${st.name}"/></option>
                        </c:forEach>
                            </form:select>
                        </div>
                        <button type="submit" class="btn btn-success">Search <i class="fa fa-long-arrow-right"></i></button>
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
<!-- FastClick --
<script src="<c:url value="/resources/adminTemplate/vendors/fastclick/lib/fastclick.js"/>"></script>
<!-- NProgress --
<script src="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.js"/>"></script>-->

<!-- Custom Theme Scripts -->


</body>
</html>
