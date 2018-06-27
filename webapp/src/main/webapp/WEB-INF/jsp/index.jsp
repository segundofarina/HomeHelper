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

    <title>Home-Helper</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet" />
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

                <h1><spring:message code="index.greeting"/></h1>
                <div class="panel searchForm">
                    <div class="panel-body">
                        <c:url value="/search" var="postPath"/>
                        <form:form modelAttribute="searchForm" action="${postPath}" method="Post">
                            <div class="form-group errorTooltip" data-toggle="tooltip" title="Unable to get current loaction">
                                <form:label path="addressField"><spring:message code="form.city"/></form:label>
                                <div class="googleAutcomplete">
                                    <div class="input-group">
                                        <form:input path="addressField" class="form-control" type="text" placeholder="Enter an address" onfocus="geolocate()" autocomplete="off" />
                                        <div class="input-group-addon currentLocation" data-toggle="tooltip" title="Get current location"><i class="fa fa-map-marker"></i></div>
                                    </div>
                                </div>
                                <form:errors path="addressField" element="p" cssClass="form-error" />
                                <form:input path="lat" type="hidden" value="" />
                                <form:input path="lng" type="hidden" value="" />
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
        <c:choose>
            <c:when test="${lastPostProvider != null}">
                <a href="<c:url value="/profile/${lastPostProvider.id}?st=${lastPostServiceType}" />" >
                    <div class="lastPostSuggestion">
                        <h5>Continue Searching:</h5>
                        <div class="pull-right close"><i class="fa fa-times"></i></div>
                        <div class="row">
                            <div class="col-xs-4 img-container">
                                <c:choose>
                                    <c:when test="${lastPostProvider.image == null}">
                                        <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="profile picture"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/profile/${lastPostProvider.id}/profileimage" />" alt="profile picture"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-xs-8">
                                <p><c:out value="${lastPostProvider.firstname}" /></p>
                                <p class="serviceType"><spring:message code="service-type.${lastPostServiceType}"/></p>
                            </div>
                        </div>
                    </div>
                </a>
            </c:when>
        </c:choose>
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

<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyBqSX1WHUw4OlgMDzYM40uSVPGkV06DR1I&ver=3.exp&libraries=places"></script>

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

    $(document).ready(function () {
        google.maps.event.addDomListener(window, 'load', initAutocomplete);

        //var modifiedAddress = false;

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


        var currentAddress = "";
        var loadCurrentAddr = false;
        var currentLat = "", currentLng = "";

        /* Get current location */
        navigator.geolocation.getCurrentPosition(
            function( position ){ // success

                /* Current Coordinate */
                var lat = position.coords.latitude;
                var lng = position.coords.longitude;
                currentLat = lat;
                currentLng = lng;

                var google_map_pos = new google.maps.LatLng( lat, lng );

                var google_maps_geocoder = new google.maps.Geocoder();
                google_maps_geocoder.geocode(
                    { 'latLng': google_map_pos },
                    function( results, status ) {
                        if ( status === google.maps.GeocoderStatus.OK && results[0] ) {
                            //$("#addressField").val(results[0].formatted_address);
                            currentAddress = results[0].formatted_address;
                            console.log(currentAddress);
                            if(loadCurrentAddr) {
                                $("#addressField").val(currentAddress);
                                $(".currentLocation").find("i").removeClass("fa-spinner");
                                $(".currentLocation").find("i").removeClass("fa-spin");
                                $(".currentLocation").find("i").addClass("fa-map-marker");
                                $(".currentLocation").addClass("disabled");
                                $(".currentLocation").tooltip("destroy");
                                $("#lat").val(currentLat);
                                $("#lng").val(currentLng);
                            }
                        } else {
                            if(loadCurrentAddr) {
                                // show error tooltip
                                showLocationError();
                                $(".currentLocation").addClass("disabled");
                                $(".currentLocation").tooltip("destroy");
                            }
                        }
                    }
                );
            },
            function(){ // fail
                if(loadCurrentAddr) {
                    // show error tooltip
                    showLocationError();
                }
                $(".currentLocation").addClass("disabled");
                $(".currentLocation").tooltip("destroy");
            }
        );
        
        $(".currentLocation").click(function () {
            if($(this).hasClass("disabled")) {
                return;
            }
            if(currentAddress === "") {
                $(this).find("i").removeClass("fa-map-marker");
                $(this).find("i").addClass("fa-spinner");
                $(this).find("i").addClass("fa-spin");
                loadCurrentAddr = true;
            } else {
                $("#addressField").val(currentAddress);
                $("#lat").val(currentLat);
                $("#lng").val(currentLng);
            }
        });

        /* currentLocation tooltip */
        $(".currentLocation").tooltip({
            trigger: 'hover'
        });

        $(".errorTooltip").tooltip({
            trigger: 'manual'
        });

        setTimeout(function () {
            $(".lastPostSuggestion").addClass("active");
        }, 3000);

        $(".lastPostSuggestion .close").click(function (e) {
           e.preventDefault();
           $(this).closest(".lastPostSuggestion").removeClass("active");
        });

    });

    function showLocationError() {
        $(".errorTooltip").tooltip("show");
        setTimeout(function(){
            $(".errorTooltip").tooltip("hide");
        }, 3000);
    }
</script>

</body>
</html>
