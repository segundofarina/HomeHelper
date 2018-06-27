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
    <!-- bootstrap-daterangepicker -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap-daterangepicker/daterangepicker.css"/>" rel="stylesheet">
    <!-- bootstrap-datetimepicker -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css"/>" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/profileStyles.css" />" rel="stylesheet" />
</head>

<body>
<div class="main_container">

    <!-- Fixed navbar -->
    <%--<jsp:include page="clientMenu.jsp" />--%>
    <nav class="navbar navbar-fixed-top navbar-custom">
        <div class="container-fluid">

            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value="/sprovider"/>">
                    <img src="<c:url value="/resources/img/HHLogo.png" />" alt="logo" />
                    <span>Home-Helper</span>
                </a>
            </div>

            <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">

                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="<c:url value="/sprovider" />" class="hvr-underline-from-center"><spring:message code="general.backToDashboard"/></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            <c:choose>
                                <c:when test="${provider.user.image != null}">
                                    <img src="<c:url value="/profile/${provider.id}/profileimage" />" alt="profile picture" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                </c:otherwise>
                            </c:choose>
                            <c:out value="${provider.user.firstname}" />
                            <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/client/settings?sp=1" />"><spring:message code="general.settings"/></a></li>
                            <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i><spring:message code="general.logout"/></a></li>
                        </ul>
                    </li>
                </ul>

            </div><!--/.nav-collapse -->
        </div>
    </nav>





    <div class="main-content">

        <!-- main content -->

        <!-- profile detail -->
        <div class="profileDetails">
            <div class="bgImg"><div class="img"></div></div>
            <div class="container">
                <div class="content">
                    <div class="profileImg">
                        <c:choose>
                            <c:when test="${provider.user.image != null}">
                                <img src="<c:url value="/profile/${provider.id}/profileimage" />" alt="profile picture" />
                            </c:when>
                            <c:otherwise>
                                <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="name">
                        <h3><c:out value="${provider.user.firstname}"/> <c:out value="${provider.user.lastname}"/></h3>
                    </div>
                    <div class="serviceType">
                        <c:forEach items="${provider.aptitudes}" var="aptitude">
                            <em><spring:message code="service-type.${aptitude.service.serviceTypeId}"/></em>
                        </c:forEach>
                    </div>
                    <c:choose>
                        <c:when test="${provider.generalCalification == 0}">
                            <p class="empty-stars"><spring:message code="emptyStars" /></p>
                        </c:when>
                        <c:otherwise>
                            <div class="stars dyn-stars" data-rating="<c:out value="${provider.generalCalification}"/>"></div>
                        </c:otherwise>
                    </c:choose>
                    <div class="hline toLeft hidden-xs"></div>
                    <div class="hline toRight hidden-xs"></div>
                </div>
            </div>
        </div>
        <!-- /profile detail -->

        <!-- page structure -->

        <!-- description and appointment -->
        <div class="container page-content">

            <div class="row row-appointment">
                <div class="col-xs-12">
                    <div class="panel">
                        <div class="panel-body descriptionTxt">
                            <p>
                                <span class="quote openQuote">&#x201C;</span>
                                <span class="text-format">
                                    <c:out value="${provider.description}"/>
                                </span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>


            <!-- Aptitudes -->
            <c:forEach items="${aptitudes}" var="aptitude">

            <div class="row aptitude">
                <div class="col-xs-12">
                    <h3> <spring:message code="service-type.${aptitude.service.serviceTypeId}"/></h3>
                    <div class="row">
                        <c:choose>
                            <c:when test="${aptitude.reviews.size() == 0}" >
                                <div class="col-xs-12 col-md-12">
                            </c:when>
                            <c:otherwise>
                                <div class="col-xs-12 col-md-8">
                            </c:otherwise>
                        </c:choose>
                            <div class="panel">
                                <div class="panel-body descriptionTxt">
                                    <p class="text-format">
                                        <c:out value="${aptitude.description}"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${aptitude.reviews.size() == 0}" >
                                <div class="col-xs-12 col-md-0" style="display: none;">
                            </c:when>
                            <c:otherwise>
                                <div class="col-xs-12 col-md-4">
                            </c:otherwise>
                        </c:choose>
                            <div class="panel">
                                <div class="panel-body aptitude-bars">
                                    <h4><spring:message code="profile.general-reviews"/></h4>
                                    <div class="stars dyn-stars" data-rating="<c:out value="${aptitude.generalCalification}"/>"></div>
                                        <div class="progressBars">
                                            <h5><spring:message code="form.quality"/></h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.qualityCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${currentAptitude.qualityCalification*20}"/>%;">
                                                    <c:out value="${aptitude.qualityCalification}"/>
                                               </div>
                                            </div>
                                            <h5><spring:message code="form.price"/></h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.priceCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${currentAptitude.priceCalification*20}"/>%;">
                                                    <c:out value="${aptitude.priceCalification}"/>
                                                </div>
                                            </div>
                                            <h5><spring:message code="form.punctuality"/></h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.punctualityCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${currentAptitude.punctualityCalification*20}"/>%;">
                                                    <c:out value="${aptitude.punctualityCalification}"/>
                                                </div>
                                            </div>
                                            <h5><spring:message code="form.treatment"/></h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.treatmentCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${currentAptitude.treatmentCalification*20}"/>%;">
                                                    <c:out value="${aptitude.treatmentCalification}"/>
                                                </div>
                                            </div>
                                            <h5><spring:message code="form.cleanness"/></h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="<c:out value="${aptitude.cleannessCalification}"/>" aria-valuemin="0" aria-valuemax="5" style="width: <c:out value="${currentAptitude.cleannessCalification*20}"/>%;">
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
                <div class="col-xs-12">
                    <h4 class="client-reviews"><spring:message code="profile.client-reviews" /></h4>
                    <div class="panel">
                        <div class="panel-body">
                            <c:choose>
                                <c:when test="${aptitude.reviews.size() == 0}" >
                                    <div class="empty-reviews">
                                        <div class="img"></div>
                                        <p><spring:message code="emptyStars" /></p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${aptitude.reviews}" var="review" varStatus="idx">
                                        <c:choose>
                                            <c:when test="${idx.index < 4}">
                                                <div class="line-divider"></div>
                                                <div class="review-item">
                                                    <div class="row">
                                                        <div class="col-xs-6 col-sm-3 col-md-2">
                                                            <div class="profileImg">
                                                                <c:choose>
                                                                    <c:when test="${review.user.image != null}">
                                                                        <img src="<c:url value="/profile/${review.user.id}/profileimage" />" alt="profile picture" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <div class="name hidden-xs">
                                                                    <h5><c:out value="" /><c:out value="${review.user.firstname}" /> <c:out value="${review.user.lastname}" /></h5>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-6 col-sm-9 col-md-10 divider-left">
                                                            <div class="name visible-xs">
                                                                <h5><c:out value="${review.user.username}" /></h5>
                                                            </div>
                                                            <div class="date"><c:out value="${review.date}" /></div>
                                                            <div class="dotDivider hidden-xs">&#x25CF;</div>
                                                            <div class="stars dyn-stars" data-rating="<c:out value="${review.generalCalification}"/>"></div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-0 col-sm-3 col-md-2"></div>
                                                        <div class="col-xs-12 col-sm-9 col-md-10 divider-left">
                                                            <p class="description text-format">
                                                                <c:out value="${review.comment}" />
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${aptitude.reviews.size() > 4}">
                            <!-- Show all reviews -->
                            <h5 class="show-more-reviews" data-toggle="modal" data-target="#modalReviews<c:out value="${aptitude.service.serviceTypeId}"/>">Show all reviews</h5>
                            <div class="modal fade" id="modalReviews<c:out value="${aptitude.service.serviceTypeId}"/>" tabindex="-1" role="dialog">
                                <div class="modal-dialog modal-lg" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title">Client reviews</h4>
                                        </div>
                                        <div class="modal-body modal-reviews-container">
                                            <c:forEach items="${aptitude.reviews}" var="review">
                                                <div class="line-divider"></div>
                                                <div class="review-item">
                                                    <div class="row">
                                                        <div class="col-xs-6 col-sm-3 col-md-2">
                                                            <div class="profileImg">
                                                                <c:choose>
                                                                    <c:when test="${review.user.image != null}">
                                                                        <img src="<c:url value="/profile/${review.user.id}/profileimage" />" alt="profile picture" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <div class="name hidden-xs">
                                                                    <h5><c:out value="" /><c:out value="${review.user.firstname}" /> <c:out value="${review.user.lastname}" /></h5>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-6 col-sm-9 col-md-10 divider-left">
                                                            <div class="name visible-xs">
                                                                <h5><c:out value="${review.user.username}" /></h5>
                                                            </div>
                                                            <div class="date"><c:out value="${review.date}" /></div>
                                                            <div class="dotDivider hidden-xs">&#x25CF;</div>
                                                            <div class="stars dyn-stars" data-rating="<c:out value="${review.generalCalification}"/>"></div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-0 col-sm-3 col-md-2"></div>
                                                        <div class="col-xs-12 col-sm-9 col-md-10 divider-left">
                                                            <p class="description text-format">
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
                        </c:when>
                    </c:choose>
                </div>
            </div>


            </c:forEach>


            <!-- Other aptitudes -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="hline endAllApt"></div>
                </div>
            </div>

            <h3 class="working-zone"><spring:message code="general.working-details"/></h3>
            <div class="row aptitude">
                <div class="col-xs-12">
                    <div class="panel">
                        <div class="panel-body">
                            <div id="map-canvas"></div>
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
<!-- bootstrap-daterangepicker -->
<script src="<c:url value="/resources/adminTemplate/vendors/moment/min/moment.min.js"/>"></script>
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap-daterangepicker/daterangepicker.js"/>"></script>
<!-- bootstrap-datetimepicker -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"/>"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/js/customJs.js"/>"></script>

<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyBqSX1WHUw4OlgMDzYM40uSVPGkV06DR1I&ver=3.exp"></script>

<script>
    $(document).ready(function () {
        generateStars();

        initializeMap();

        $('#datepicker').datetimepicker({
            format: 'DD/MM/YYYY',
            minDate: new Date()-1
        });

        $('#date').focus(function () {
            $('#datepicker').data("DateTimePicker").show();
        });

        $('#date').blur(function () {
            $('#datepicker').data("DateTimePicker").hide();
        });


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
        });

        $(".otherAptitdes-hidden h4").click(function () {
            var otherApt = $(".otherAptitudes");
            if(otherApt.hasClass("active")) {
                otherApt.removeClass("active");
            } else {
                otherApt.addClass("active");
            }

        });

    });

    function initializeMap() {
        var coordsList = "";
        coordsList = '<c:out value="${workingZonesCoords}"/>';
        var coordsArr = coordsList.split(";");

        /* Center map */
        var myLatLng = null;
        if(coordsArr.length === 0) {
            myLatLng = new google.maps.LatLng(-34.572902, -58.423161);
        } else {
            console.log("calculate center");
            var maxLat = 0, minLat = 0, maxLng = 0, minLng = 0;
            for(var i = 0; i < coordsArr.length; i++) {
                var coord = coordsArr[i].split(",");

                if(maxLat === 0 || parseFloat(coord[0]) > maxLat) {
                    maxLat = parseFloat(coord[0]);
                }
                if(maxLng === 0 || parseFloat(coord[1]) > maxLng) {
                    maxLng = parseFloat(coord[1]);
                }
                if(minLat === 0 || parseFloat(coord[0]) < minLat) {
                    minLat = parseFloat(coord[0]);
                }
                if(minLng === 0 || parseFloat(coord[1]) < minLng) {
                    minLng = parseFloat(coord[1]);
                }
            }

            myLatLng = new google.maps.LatLng( (((maxLat - minLat) / 2) + minLat), ( ((maxLng - minLng) / 2) + minLng ) );
        }

        // General Options
        var mapOptions = {
            zoom: 14,
            center: myLatLng,
            mapTypeId: google.maps.MapTypeId.RoadMap
        };
        var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
        // Polygon Coordinates
        // read them from #aptMap and crate an array

        var polygonCoords = [];
        for(var i = 0; i < coordsArr.length; i++) {
            var coord = coordsArr[i].split(",");
            polygonCoords.push(new google.maps.LatLng(coord[0], coord[1]));
        }

        if(polygonCoords.length === 0) {
            // set default coords
            polygonCoords.push(new google.maps.LatLng(-34.557176, -58.430436));
            polygonCoords.push(new google.maps.LatLng(-34.588696, -58.431428));
            polygonCoords.push(new google.maps.LatLng(-34.575376, -58.403839));
        }

        // Styling & Controls
        myPolygon = new google.maps.Polygon({
            paths: polygonCoords,
            draggable: false,
            editable: false,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35
        });

        myPolygon.setMap(map);
    }
</script>

</body>
</html>
