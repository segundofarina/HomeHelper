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

    <title>Home-Helper | <spring:message code="general.search"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/searchStyles.css" />" rel="stylesheet" />
</head>

<body>
<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp" />

    <!-- main content -->
    <div class="main-content">

        <div class="container-fluid">

            <div class="row">
                <div class="col-xs-12 col-sm-4 col-md-3 col-fixed">
                    <div class="panel">
                        <div class="panel-body">
                            <c:url value="/search?st=${serviceTypeId}&cty=${cityId}" var="postPath"/>
                            <form:form modelAttribute="searchForm" action="${postPath}" method="Post">
                                <div class="form-group">
                                    <form:label path="city"><spring:message code="form.city"/></form:label>
                                    <form:select class="form-control" path="city">
                                        <form:option value=""><spring:message code="index.select-city"/></form:option>
                                        <c:forEach items="${neighborhoods}" var="ng">
                                            <form:option value="${ng.ngId}"><spring:message code="neighborhood.${ng.ngId}"/></form:option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="city" element="p" cssClass="form-error" />
                                </div>
                                <div class="form-group">
                                    <form:label path="serviceType"><spring:message code="form.service-type"/></form:label>
                                    <form:select path="serviceType" class="form-control" >
                                        <form:option value=""><spring:message code="index.select-serviceType"/></form:option>
                                        <c:forEach items="${serviceTypes}" var="st">
                                            <form:option value="${st.serviceTypeId}"><c:out value="${st.name}"/></form:option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="serviceType" element="p" cssClass="form-error" />
                                </div>
                                <form:button type="submit" class="btn btn-success btn-full-width"><spring:message code="general.search"/></form:button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-0 col-sm-4 col-md-3">

                </div>
                <div class="col-xs-12 col-sm-8 col-md-9">
                    <c:forEach items="${list}" var="provider">

                        <div class="panel profile-item">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-6 col-sm-2">
                                        <div class="profileImg">
                                            <img src="<c:url value="/resources/img/img.jpg" />" alt="Profile picture" />
                                            <div class="profileBtn hidden-xs">

                                                <a href="<c:url value="/profile/${provider.id}" />" class="btn btn-success btn-sm"><spring:message code="profile.view-profile"/></a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-10">
                                        <div class="moveLeft">
                                            <h3 class="profileName"><c:out value="${provider.firstname}"/> <c:out value="${provider.lastname}"/></h3>
                                            <span class="separatorDot">&#x25CF;</span>
                                            <h5 class="serviceTypes">
                                                <c:forEach items="${provider.aptitudes}" var="aptitude">
                                                    <c:out value="${aptitude.service.name}"/>
                                                </c:forEach>
                                            </h5>
                                        </div>
                                        <div class="moveRight">
                                            <div class="stars dyn-stars" data-rating="<c:out value="${provider.generalCalification}"/>">
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-0 col-sm-2"></div>
                                    <div class="col-xs-12 col-sm-10">
                                        <p class="profileDescription">
                                            <c:out value="${provider.description}"/>
                                        </p>
                                    </div>
                                </div>
                                <div class="profileBtn visible-xs">
                                    <a href="<c:url value="/profile/${provider.id}" />" class="btn btn-success btn-sm btn-full-width"><spring:message code="profile.view-profile"/></a>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                    <c:if test="${list.size() == 0}">
                        <div class="empty-result">
                            <div class="img"></div>
                            <div class="description">
                                <p><spring:message code="emptyResult.description" /></p>
                            </div>
                        </div>
                    </c:if>
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
    $(document).ready(function(){
        generateStars();
    });
</script>

</body>
</html>
