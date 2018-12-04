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
    <!-- Starrr -->
    <link href="<c:url value="/resources/starrr/starrr.css"/>" rel="stylesheet" />

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/writeReview.css" />" rel="stylesheet" />
</head>

<body>
<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="../clientMenu.jsp" />

    <!-- main content -->
    <div class="main-content">

        <div class="container">

            <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <div class="panel">
                        <div class="panel-body">
                            <div class="app-profile">
                                <div class="app-header">
                                    <div>
                                        <c:choose>
                                            <c:when test="${appointment.provider.client.image != null}">
                                                <img src="<c:url value="/profile/${appointment.provider.client.id}/profileimage" />" alt="profile picture" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div>
                                        <h3><c:out value="${appointment.provider.client.firstname}" /> <c:out value="${appointment.provider.client.lastname}" /></h3>
                                    </div>
                                    <div>
                                        <c:choose>
                                            <c:when test="${appointment.provider.generalCalification == 0}">
                                                <p class="empty-stars"><spring:message code="emptyStars" /></p>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="stars dyn-stars" data-rating="<c:out value="${appointment.provider.generalCalification}"/>"></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="app-section">
                                    <h5><spring:message code="form.service-type"/></h5>
                                    <p><c:out value="${appointment.serviceType.name}" /></p>
                                </div>
                                <div class="app-section">
                                    <h5><spring:message code="form.date"/></h5>
                                    <p><c:out value="${appointment.dateDMY}" /></p>
                                </div>
                                <div class="app-section">
                                    <h5><spring:message code="form.description"/></h5>
                                    <p><c:out value="${appointment.jobDescripcion}" /></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <div class="panel">
                        <div class="panel-body">
                            <div class="review">
                                <div class="review-header">
                                    <h3><spring:message code="client.write-review"/></h3>
                                </div>
                                <div class="review-content">
                                    <h5><spring:message code="client.rate-provider"/></h5>
                                    <c:url value="/client/sendReview" var="postPath"/>
                                    <form:form modelAttribute="reviewForm" action="${postPath}" method="post">
                                        <form:input path="appointmentId" type="hidden" value="${appointment.appointmentId}" />
                                        <form:input path="providerId" type="hidden" value="${appointment.provider.client.id}" />

                                        <div class="star-container">
                                            <div class="row">
                                                <div class="col-xs-12 col-sm-6">
                                                    <div class="star-item">
                                                        <h5><spring:message code="form.quality"/></h5>
                                                        <div class="starrr"></div>
                                                        <form:input path="quality" type="hidden" value="" />
                                                        <form:errors path="quality" cssClass="form-error" element="p" />
                                                    </div>
                                                    <div class="star-item">
                                                        <h5><spring:message code="form.price"/></h5>
                                                        <div class="starrr"></div>
                                                        <form:input path="price" type="hidden" value="" />
                                                        <form:errors path="price" cssClass="form-error" element="p" />
                                                    </div>
                                                    <div class="star-item">
                                                        <h5><spring:message code="form.punctuality"/></h5>
                                                        <div class="starrr"></div>
                                                        <form:input path="punctuality" type="hidden" value="" />
                                                        <form:errors path="punctuality" cssClass="form-error" element="p" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6">
                                                    <div class="star-item">
                                                        <h5><spring:message code="form.treatment"/></h5>
                                                        <div class="starrr"></div>
                                                        <form:input path="treatment" type="hidden" value="" />
                                                        <form:errors path="treatment" cssClass="form-error" element="p" />
                                                    </div>
                                                    <div class="star-item">
                                                        <h5><spring:message code="form.cleanness"/></h5>
                                                        <div class="starrr"></div>
                                                        <form:input path="cleannes" type="hidden" value="" />
                                                        <form:errors path="cleannes" cssClass="form-error" element="p" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <form:label path="msg"><spring:message code="client.write-review"/></form:label>
                                            <spring:message code="placeholder.write-review" var="placeholder" />
                                            <form:textarea path="msg" cssClass="form-control resize-vertical" placeholder="${placeholder}" />
                                            <form:errors path="msg" cssClass="form-error" element="p" />
                                        </div>
                                        <form:button type="submit" class="btn btn-success pull-right"><spring:message code="general.send"/></form:button>
                                        <div class="clearfix"></div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
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
<!-- Bootstrap -->
<script src="<c:url value="/resources/starrr/starrr.js"/>"></script>

    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/resources/js/customJs.js"/>"></script>
    <script>
        $(document).ready(function () {
            generateStars();
        });

        $('.star-item').each(function () {
            const val = $(this).find('input').val();
            $(this).find('.starrr').data('rating', val);
        });

        $('.starrr').starrr({
            change: function(e, value){
                console.log('new rating is ' + value);
                console.log($(e.target).html());
                const elem = $(e.target);
                elem.closest('.star-item').find('input').val(value);
            }

        });

    </script>

</body>
</html>
