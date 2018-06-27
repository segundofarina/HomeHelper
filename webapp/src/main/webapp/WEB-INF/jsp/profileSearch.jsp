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
                            <c:url value="/search?st=${serviceTypeId}&llat=${llat}&llng=${llng}&addr=${b64addr}" var="postPath"/>
                            <form:form modelAttribute="searchForm" action="${postPath}" method="Post">
                                <div class="form-group">
                                    <form:label path="addressField"><spring:message code="form.city"/></form:label>
                                    <div class="googleAutcomplete">
                                        <form:input path="addressField" class="form-control" type="text" placeholder="Enter an address" onfocus="geolocate()" autocomplete="off" />
                                    </div>
                                    <form:errors path="addressField" element="p" cssClass="form-error" />
                                    <form:input path="lat" type="hidden" />
                                    <form:input path="lng" type="hidden" />
                                </div>
                                <div class="form-group">
                                    <form:label path="serviceType"><spring:message code="form.service-type"/></form:label>
                                    <form:select path="serviceType" class="form-control" >
                                        <form:option value=""><spring:message code="index.select-serviceType"/></form:option>
                                        <c:forEach items="${serviceTypes}" var="st">
                                            <form:option value="${st.serviceTypeId}"><spring:message code="service-type.${st.serviceTypeId}"/></form:option>
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
                                            <c:choose>
                                                <c:when test="${provider.image != null}">
                                                    <img src="<c:url value="/profile/${provider.id}/profileimage" />" alt="Profile picture" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                </c:otherwise>
                                            </c:choose>

                                            <div class="profileBtn hidden-xs">

                                                <a href="<c:url value="/profile/${provider.id}?st=${serviceTypeId}" />" class="btn btn-success btn-sm"><spring:message code="profile.view-profile"/></a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-10">
                                        <div class="moveLeft">
                                            <h3 class="profileName"><c:out value="${provider.firstname}"/> <c:out value="${provider.lastname}"/></h3>
                                            <span class="separatorDot">&#x25CF;</span>
                                            <h5 class="serviceTypes">
                                                <c:forEach items="${provider.aptitudes}" var="aptitude">
                                                    <spring:message code="service-type.${aptitude.service.serviceTypeId}"/>
                                                </c:forEach>
                                            </h5>
                                        </div>
                                        <div class="moveRight">
                                            <c:choose>
                                                <c:when test="${provider.generalCalification == 0}">
                                                    <p class="empty-stars"><spring:message code="emptyStars" /></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="stars dyn-stars" data-rating="<c:out value="${provider.generalCalification}"/>"></div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-0 col-sm-2"></div>
                                    <div class="col-xs-12 col-sm-10">
                                        <p class="profileDescription text-format">
                                            <c:out value="${provider.description}"/>
                                        </p>
                                    </div>
                                </div>
                                <div class="profileBtn visible-xs">
                                    <a href="<c:url value="/profile/${provider.id}?st=${serviceTypeId}" />" class="btn btn-success btn-sm btn-full-width"><spring:message code="profile.view-profile"/></a>
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

<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyBqSX1WHUw4OlgMDzYM40uSVPGkV06DR1I&ver=3.exp&libraries=places"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/js/customJs.js"/>"></script>
<script>
    var autocomplete;
    var modifiedAddress = false;

    function initAutocomplete() {
        // Create the autocomplete object, restricting the search to geographical
        // location types.
        autocomplete = new google.maps.places.Autocomplete(
            (document.getElementById('addressField')),
            {types: ['geocode']});

        // When the user selects an address from the dropdown, populate the address
        // fields in the form.
        autocomplete.addListener('place_changed', fillInAddress);
    }

    function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocomplete.getPlace();
        if(!place.geometry) {
            $("#lat").val("");
            $("#lng").val("");
        } else {
            $("#lat").val(place.geometry.location.lat());
            $("#lng").val(place.geometry.location.lng());
        }
        modifiedAddress = false;
    }

    // Bias the autocomplete object to the user's geographical location,
    // as supplied by the browser's 'navigator.geolocation' object.
    function geolocate() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var geolocation = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                var circle = new google.maps.Circle({
                    center: geolocation,
                    radius: position.coords.accuracy
                });
                autocomplete.setBounds(circle.getBounds());
            });
        }
    }


    $(document).ready(function(){
        generateStars();

       google.maps.event.addDomListener(window, 'load', initAutocomplete);

        /* Prevent default enter */
        $(window).keydown(function(event){
            if(event.keyCode == 13) {
                event.preventDefault();
                modifiedAddress = false;
                return false;
            }
        });

        /* clean form on exit without valid option */
        $(document).click(function(event) {
            if (!$(event.target).closest("#addressField").length && !$(event.target).closest(".pac-container").length && modifiedAddress) {
                $("#addressField").val("");
                $("#lat").val("");
                $("#lng").val("");
            }
            modifiedAddress = false;
        });

        $("#addressField").keydown(function () {
            modifiedAddress = true;
        });
    });
</script>

</body>
</html>
