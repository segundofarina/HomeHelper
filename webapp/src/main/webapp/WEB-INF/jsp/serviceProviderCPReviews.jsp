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
    <link href="<c:url value="/resources/css/controlPanelReviews.css"/>" rel="stylesheet">
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
                                <c:choose>
                                    <c:when test="${reviews.size() == 0}" >
                                        <div class="empty-reviews">
                                            <div class="img"></div>
                                            <p><spring:message code="emptyStars" /></p>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th><spring:message code="general.name"/></th>
                                                <th><spring:message code="general.date"/></th>
                                                <th><spring:message code="general.review"/></th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${reviews}" var="review">
                                                <tr class="review-header" data-toggle="collapse" data-target="#review<c:out value="${review.user.id}"/>" aria-expanded="false">
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${review.user.image != null}">
                                                                <img class="profileImage" src="<c:url value="/profile/${review.user.id}/profileimage" />" alt="profile picture" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img class="profileImage" src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td><c:out value="${review.user.firstname}" /> <c:out value="${review.user.lastname}" /></td>
                                                    <td><c:out value="${review.date}" /></td>
                                                    <td>
                                                        <div class="stars dyn-stars" data-rating="<c:out value="${review.generalCalification}"/>"></div>
                                                    </td>
                                                    <td>
                                                        <div class="arrow">
                                                            <i class="fa fa-caret-down"></i>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr class="review-detail">
                                                    <td colspan="5">
                                                        <!-- cambiar por review id -->
                                                        <div class="collapse info" id="review<c:out value="${review.user.id}"/>">
                                                            <div class="content">
                                                                <div class="row">
                                                                    <div class="col-xs-12 col-md-6 firstCol">
                                                                        <h4><spring:message code="general.calification" /></h4>
                                                                        <div class="row">
                                                                            <div class="col-xs-12 col-sm-6">
                                                                                <div class="star-item">
                                                                                    <h5><spring:message code="general.genCalification" />:</h5>
                                                                                    <div class="stars dyn-stars" data-rating="<c:out value="${review.generalCalification}"/>"></div>
                                                                                </div>
                                                                                <div class="star-item">
                                                                                    <h5><spring:message code="general.quality" />:</h5>
                                                                                    <div class="stars dyn-stars" data-rating="<c:out value="${review.qualityCalification}"/>"></div>
                                                                                </div>
                                                                                <div class="star-item">
                                                                                    <h5><spring:message code="general.price" />:</h5>
                                                                                    <div class="stars dyn-stars" data-rating="<c:out value="${review.priceCalification}"/>"></div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-xs-12 col-sm-6">
                                                                                <div class="star-item">
                                                                                    <h5><spring:message code="general.punctuality" />:</h5>
                                                                                    <div class="stars dyn-stars" data-rating="<c:out value="${review.punctualityCalification}"/>"></div>
                                                                                </div>
                                                                                <div class="star-item">
                                                                                    <h5><spring:message code="general.treatment" />:</h5>
                                                                                    <div class="stars dyn-stars" data-rating="<c:out value="${review.treatmentCalification}"/>"></div>
                                                                                </div>
                                                                                <div class="star-item">
                                                                                    <h5><spring:message code="general.cleanness" />:</h5>
                                                                                    <div class="stars dyn-stars" data-rating="<c:out value="${review.cleannessCalification}"/>"></div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-12 col-md-6">
                                                                        <h4><spring:message code="general.description" />:</h4>
                                                                        <p><c:out value="${review.comment}" /></p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
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


        $(".review-header").click(function () {
            var arrow = $(this).find(".arrow");

            if(arrow.hasClass("rotate")) {
                arrow.removeClass("rotate");
            } else {
                arrow.addClass("rotate");
            }
        })
    });
</script>
</body>
</html>