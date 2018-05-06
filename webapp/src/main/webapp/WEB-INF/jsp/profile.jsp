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

    <title>Home-Helper | Name</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/profileStyles.css" />" rel="stylesheet" />
</head>

<body>
    <div class="main_container">

        <!-- Fixed navbar -->
        <nav class="navbar navbar-fixed-top navbar-custom">
            <div class="container-fluid">

                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img src="<c:url value="/resources/img/HHLogo.png" />" alt="logo" />
                        <span>Home-Helper</span>
                    </a>
                </div>

                <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">

                    <button type="submit" class="btn btn-custom btn-rounded navbar-right navbar-btn">Log in</button>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="../navbar/">Home</a></li>
                        <li><a href="../navbar-static-top/">Static top</a></li>
                        <li><a href="./">Fixed top</a></li>
                    </ul>

                </div><!--/.nav-collapse -->
            </div>
        </nav>

        <div class="main-content">

            <!-- main content -->

            <!-- profile detail -->
            <div class="profileDetails">
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
                        <div class="stars">
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star-half-o"></i>
                            <i class="fa fa-star-o"></i>
                        </div>
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
                                    <span class="quote closeQuote">&#x02EE;</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-4 col-md-3 panel-appointment">
                        <div class="panel panel-shadow">
                            <div class="panel-body">
                                <c:url value="/setAppointment" var="postPath"/>
                                <form:form modelAttribute="appointmentForm" action="${postPath}" method="get">
                                    <div class="form-group">
                                        <form:label path="serviceType">Service Type:</form:label>
                                        <form:select class="form-control" path="serviceType">
                                            <c:forEach items="${provider.aptitudes}" var="aptitude">
                                                <option value="<c:out value="${aptitude.service.serviceTypeId}"/>"> <c:out value="${aptitude.service.name}"/></option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                    <div class="form-group">
                                        <formlabel path="date">Date:</formlabel>
                                        <form:input type="text" name="date" path="date" class="form-control" placeholder="Select a date..." />
                                    </div>
                                    <div class="form-group">
                                        <form:label path="description">Description:</form:label>
                                        <form:textarea path="description" class="form-control" placeholder="Describe your situation"></form:textarea>
                                    </div>
                                    <form:button type="submit" class="btn btn-success btn-full-width">Contact</form:button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>

                <h2>Aptitudes</h2>
                <c:forEach items="${provider.aptitudes}" var="aptitude">

                <div class="row aptitude">
                    <div class="col-xs-12 col-sm-8 col-md-9">

                        <div class="row">
                            <div class="col-xs-12 col-md-8">
                                <div class="panel">
                                    <div class="panel-body descriptionTxt">
                                        <h3><c:out value="${aptitude.service.name}"/></h3>
                                        <p>
                                            <c:out value="${aptitude.description}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-4">
                                <div class="panel">
                                    <div class="panel-body aptitude-bars">
                                        <h4>General reviews</h4>
                                        <div class="stars">
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star-half-o"></i>
                                            <i class="fa fa-star-o"></i>
                                        </div>
                                        <div class="progressBars">
                                            <h5>Algo:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="3.5" aria-valuemin="0" aria-valuemax="5" style="width: 70%;">
                                                    3.5
                                                </div>
                                            </div>
                                            <h5>Algo:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="5" aria-valuemin="0" aria-valuemax="5" style="width: 100%;">
                                                    5
                                                </div>
                                            </div>
                                            <h5>Algo:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="4.5" aria-valuemin="0" aria-valuemax="5" style="width: 90%;">
                                                    4.5
                                                </div>
                                            </div>
                                            <h5>Algo:</h5>
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="4.8" aria-valuemin="0" aria-valuemax="5" style="width: 96%;">
                                                    4.8
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
                        <div class="panel descriptionTxt">
                            <div class="panel-body">
                             <c:forEach items="${aptitude.reviews}" var="review">
                                <div class="review-item">

                                    <div class="row">
                                        <div class="col-xs-12 col-sm-3 col-md-2">
                                            <div class="profileImg">
                                                <img src="<c:url value="/resources/img/img.jpg" />" alt="Profile Img" />
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-9 col-md-8">
                                            <blockquote>
                                                <p>
                                                    <c:out value="${review.comment}"/>
                                                </p>
                                                <footer class="visible-xs visible-sm">May 24, 2018</footer>
                                            </blockquote>
                                        </div>
                                        <div class="hidden-sm hidden-xs col-md-2">
                                            <div class="date-panel">
                                                <p class="day">24</p>
                                                <p class="month">May</p>
                                                <p class="year">2018</p>
                                            </div>
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
                    © 2018 All rights reserved Home-Helper.com
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
<script>
    $(document).ready(function () {
        $(window).scroll(function () {
            var panel = $('.panel-appointment');
            var scroll = $(window).scrollTop();

            var offset = 300;
            var offsetBottom = 100;

            //console.log(scroll);
            //console.log(scroll >= offset);

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
