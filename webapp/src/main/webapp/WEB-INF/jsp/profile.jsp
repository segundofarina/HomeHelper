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

    <title>Home-Helper | <spring:message code="general.name"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/profileStyles.css" />" rel="stylesheet" />
</head>

<body>
    <div class="main_container">

        <!-- Fixed navbar -->
        <jsp:include page="clientMenu.jsp" />

        <div class="main-content">

            <!-- main content -->

            <!-- profile detail -->
            <div class="profileDetails">
                <div class="bgImg"><div class="img"></div></div>
                <div class="container">
                    <div class="content">
                        <div class="profileImg">
                            <img src="<c:url value="/resources/img/img.jpg" />" alt="profile picture" />
                        </div>
                        <div class="name">
                            <h3><c:out value="${provider.firstname}"/> <c:out value="${provider.lastname}"/></h3>
                        </div>
                        <div class="serviceType">
                            <c:forEach items="${provider.aptitudes}" var="aptitude">
                            <em><c:out value="${aptitude.service.name}"/> </em>
                            </c:forEach>
                        </div>
                        <div class="stars dyn-stars" data-rating="<c:out value="${provider.generalCalification}"/>"></div>
                        <div class="hline toLeft hidden-xs"></div>
                        <div class="hline toRight hidden-xs"></div>
                    </div>
                </div>

            </div>
            <!-- /profile detail -->

            <!-- page structure -->

            <!-- description and appointment -->
            <div class="container-fluid page-content">
                <div class="row row-appointment">
                    <div class="col-xs-12 col-sm-8 col-md-9">
                        <div class="panel">
                            <div class="panel-body descriptionTxt">
                                <p>
                                    <span class="quote openQuote">&#x201C;</span>
                                    <span>
                                        <c:out value="${provider.description}"/>
                                    </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-4 col-md-3 panel-appointment">
                        <div class="panel panel-shadow">
                            <div class="panel-body">
                                <c:url value="/client/setAppointment" var="postPath"/>
                                <form:form modelAttribute="appointmentForm" action="${postPath}" method="post">
                                    <form:input type="hidden" value="${provider.id}" path="providerId" />
                                    <div class="form-group">
                                        <form:label path="serviceType"><spring:message code="general.service-type"/>:</form:label>
                                        <form:select class="form-control" path="serviceType">
                                            <option value="">Select service type...</option>
                                            <c:forEach items="${provider.aptitudes}" var="aptitude">
                                                <option value="<c:out value="${aptitude.service.serviceTypeId}"/>"> <c:out value="${aptitude.service.name}"/></option>
                                            </c:forEach>
                                        </form:select>
                                        <form:errors path="serviceType" element="p" cssClass="form-error" />
                                    </div>
                                    <div class="form-group">
                                        <form:label path="date"><spring:message code="general.date"/>:</form:label>
                                        <form:input type="text" name="date" path="date" class="form-control" placeholder="Select a date..." />
                                        <form:errors path="date" element="p" cssClass="form-error" />
                                    </div>
                                    <div class="form-group">
                                        <form:label path="description"><spring:message code="general.description"/>:</form:label>
                                        <form:textarea path="description" class="form-control" placeholder="Describe your situation"></form:textarea>
                                    </div>
                                    <form:button type="submit" class="btn btn-success btn-full-width"><spring:message code="general.contact"/></form:button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>

                <h1><spring:message code="general.aptitudes"/></h1>
                <c:forEach items="${provider.aptitudes}" var="aptitude">

                <div class="row aptitude">

                    <div class="col-xs-12 col-sm-8 col-md-9">

                        <h3><c:out value="${aptitude.service.name}"/></h3>

                        <div class="row">
                            <div class="col-xs-12 col-md-8">
                                <div class="panel">
                                    <div class="panel-body descriptionTxt">
                                        <p>
                                            <c:out value="${aptitude.description}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-4">
                                <div class="panel">
                                    <div class="panel-body aptitude-bars">
                                        <h4><spring:message code="profile.general-reviews"/></h4>
                                        <div class="stars dyn-stars" data-rating="<c:out value="${aptitude.generalCalification}"/>"></div>
                                        <div class="progressBars">
                                            <h5><spring:message code="general.quality"/>:</h5>
                                            <div class="progress">

                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.qualityCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${aptitude.qualityCalification*20}"/>%;">
                                                    <c:out value="${aptitude.qualityCalification}"/>
                                                </div>
                                            </div>
                                            <h5><spring:message code="general.price"/>:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.priceCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${aptitude.priceCalification*20}"/>%;">
                                                    <c:out value="${aptitude.priceCalification}"/>
                                                </div>
                                            </div>
                                            <h5><spring:message code="general.punctuality"/>:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.punctualityCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${aptitude.punctualityCalification*20}"/>%;">
                                                    <c:out value="${aptitude.punctualityCalification}"/>
                                                </div>
                                            </div>
                                            <h5><spring:message code="general.treatment"/>:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.treatmentCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${aptitude.treatmentCalification*20}"/>%;">
                                                    <c:out value="${aptitude.treatmentCalification}"/>
                                                </div>
                                            </div>
                                            <h5><spring:message code="general.cleanness"/>:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.cleannessCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${aptitude.cleannessCalification*20}"/>%;">
                                                    <c:out value="${aptitude.cleannessCalification}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- reviews -->
                <div class="row aptitude">
                    <div class="col-xs-12 col-sm-8 col-md-9">
                        <div class="panel">
                            <div class="panel-body">
                             <c:forEach items="${aptitude.reviews}" var="review">
                                 <div class="line-divider"></div>
                                 <div class="review-item">
                                    <div class="row">
                                        <div class="col-xs-6 col-sm-3 col-md-2">
                                            <div class="profileImg">
                                                <img src="<c:url value="/resources/img/img.jpg"/>" alt="Profile Img" />
                                                <div class="name hidden-xs">
                                                    <h5><c:out value="" /><c:out value="${review.user.firstname}" /> <c:out value="${review.user.lastname}" /></h5>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-6 col-sm-9 col-md-10 divider-left">
                                            <div class="name visible-xs">
                                                <h5>Martin Victory</h5>
                                            </div>
                                            <div class="date"><c:out value="${review.date}" /></div>
                                            <div class="dotDivider hidden-xs">&#x25CF;</div>
                                            <div class="stars dyn-stars" data-rating="<c:out value="${review.generalCalification}"/>"></div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-0 col-sm-3 col-md-2"></div>
                                        <div class="col-xs-12 col-sm-9 col-md-10 divider-left">
                                            <p class="description">
                                                <c:out value="${review.comment}" />
                                            </p>
                                        </div>
                                    </div>

                                </div>
                             </c:forEach>


                            </div>
                        </div>
                    </div>
                </div>

                </c:forEach>
            </div>

            <!-- /main content -->

            <!-- footer content -->
            <footer class="footer">
                <div class="pull-right">
                    <spring:message code="index.rights-reserved"/>
                </div>
                <div class="clearfix"></div>
            </footer>
            <!-- /footer content -->

        </div>
        <!-- /container -->


    </div>

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
    $(document).ready(function () {
        generateStars();


        $(window).scroll(function () {
            var panel = $('.panel-appointment');
            var scroll = $(window).scrollTop();

            var offset = 300;
            //var offsetBottom = 100;

            if(scroll >= offset) {
                panel.addClass('panel-appointment-fixed');
            } else {
                panel.removeClass('panel-appointment-fixed');
            }
/*
            var documentHeight = $(document).height();
            var distanceBottom = documentHeight - (panel.height() + offsetBottom);
            console.log(distanceBottom);

            console.log(scroll >= distanceBottom);

                if(scroll >= distanceBottom) {
                panel.addClass('panel-appointment-fixed-bottom');
                panel.css("top", (distanceBottom)+"px")
            } else {
                panel.removeClass('panel-appointment-fixed-bottom');
                panel.css("top", "6em");
            }*/
        });
    });
</script>

</body>
</html>
