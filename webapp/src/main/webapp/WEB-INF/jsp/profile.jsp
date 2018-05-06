<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
                <div class="container">
                    <div class="content">
                        <div class="profileImg">
                            <img src="<c:url value="/resources/img/img.jpg" />" alt="profile picture" />
                        </div>
                        <div class="name">
                            <h3>Martin Victory</h3>
                        </div>
                        <div class="serviceType"><em>Pintor y obrero</em></div>
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
                                    <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt ex eget magna cursus, ut pretium dui imperdiet. Nullam eleifend sem laoreet ultrices condimentum. Vestibulum malesuada viverra quam sed venenatis. Vivamus vel odio non lectus commodo sagittis. Etiam ut porta lectus. Quisque eleifend, nisl non maximus cursus, orci urna ultrices turpis, et fermentum magna ex nec massa. Aenean fermentum porttitor enim in porta. Vivamus mollis massa quis risus suscipit, vehicula malesuada velit dictum.
                                    Etiam iaculis magna ultrices finibus egestas. In vitae lacus nisi. Quisque maximus augue est, ut fringilla libero tempus at. Praesent vulputate fringilla quam ac pretium. Quisque fermentum cursus erat, eu rhoncus leo ultrices id. Suspendisse et sagittis mi. Pellentesque sit amet mauris vitae urna interdum tempus sit amet id justo. Donec fringilla id quam pulvinar consequat. Fusce scelerisque id dolor eu pretium. Ut ut elit pretium, elementum nibh non, tincidunt nisl. Vivamus tristique erat lobortis, rhoncus est suscipit, iaculis sapien. Sed ex orci, lacinia at aliquet quis, cursus eget libero. Suspendisse venenatis ex at nisl lobortis laoreet.
                                    Pellentesque sed eros blandit, rutrum nibh eu, congue nisi. Mauris congue, ex tincidunt facilisis pulvinar, velit diam varius justo, nec viverra est sem sed lacus. Donec vel lectus tristique, faucibus libero non, tincidunt felis. Mauris commodo augue ut scelerisque aliquet. Cras a pretium sem. Nunc accumsan dolor non interdum volutpat. Sed nec dignissim lectus.</span>
                                    <span class="quote closeQuote">&#x02EE;</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-4 col-md-3 panel-appointment">
                        <div class="panel panel-shadow">
                            <div class="panel-body">
                                <form method="post" action="/setAppointment">
                                    <div class="form-group">
                                        <label for="apServiceType">Service Type:</label>
                                        <select class="form-control" id="apServiceType">
                                            <option value="">Select a service type...</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="apDate">Date:</label>
                                        <input type="text" name="date" id="apDate" class="form-control" placeholder="Select a date..." />
                                    </div>
                                    <div class="form-group">
                                        <label for="apDescription">Description:</label>
                                        <textarea name="description" id="apDescription" class="form-control" placeholder="Describe your situation"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-success btn-full-width">Contact</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <h2>Aptitudes</h2>

                <div class="row aptitude">
                    <div class="col-xs-12 col-sm-8 col-md-9">

                        <div class="row">
                            <div class="col-xs-12 col-md-8">
                                <div class="panel">
                                    <div class="panel-body descriptionTxt">
                                        <h3>Pintor</h3>
                                        <p>
                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt ex eget magna cursus, ut pretium dui imperdiet. Nullam eleifend sem laoreet ultrices condimentum. Vestibulum malesuada viverra quam sed venenatis. Vivamus vel odio non lectus commodo sagittis. Etiam ut porta lectus. Quisque eleifend, nisl non maximus cursus, orci urna ultrices turpis, et fermentum magna ex nec massa. Aenean fermentum porttitor enim in porta. Vivamus mollis massa quis risus suscipit, vehicula malesuada velit dictum.
                                            <br />
                                            Etiam iaculis magna ultrices finibus egestas. In vitae lacus nisi. Quisque maximus augue est, ut fringilla libero tempus at. Praesent vulputate fringilla quam ac pretium. Quisque fermentum cursus erat, eu rhoncus leo ultrices id. Suspendisse et sagittis mi. Pellentesque sit amet mauris vitae urna interdum tempus sit amet id justo.
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
                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt ex eget magna cursus, ut pretium dui imperdiet. Nullam eleifend sem laoreet ultrices condimentum. Vestibulum malesuada viverra quam sed venenatis. Vivamus vel odio non lectus commodo sagittis. Etiam ut porta lectus.
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


                                <!---->

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
                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tincidunt ex eget magna cursus, ut pretium dui imperdiet. Nullam eleifend sem laoreet ultrices condimentum. Vestibulum malesuada viverra quam sed venenatis. Vivamus vel odio non lectus commodo sagittis. Etiam ut porta lectus.
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

                                <!---->

                            </div>
                        </div>
                    </div>
                </div>
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
