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

    <title>Home-Helper | Control Panel</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/adminTemplate/build/css/custom.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/adminTemplateCustomStyles.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/controlPanelEditProfile.css" />" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <jsp:include page="leftBarMenu.jsp" />

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <img src="<c:url value="/resources/img/img.jpg"/>" alt="">John Doe
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="javascript:;">Settings</a></li>
                                <li><a href="/login"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                            </ul>
                        </li>

                        <li role="presentation" class="dropdown">
                            <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                                <i class="fa fa-envelope-o"></i>
                                <span class="badge bg-green">6</span>
                            </a>
                            <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                                <li>
                                    <a>
                                        <span class="image"><img src="<c:url value="/resources/img/img.jpg"/>" alt=" Profile Image" /></span>
                                        <span>
                                          <span>John Smith</span>
                                          <span class="time">3 mins ago</span>
                                        </span>
                                        <span class="message">
                                          Film festivals used to be do-or-die moments for movie makers. They were where...
                                        </span>
                                    </a>
                                </li>
                                <li>
                                    <a>
                                        <span class="image"><img src="<c:url value="/resources/img/img.jpg"/>" alt="Profile Image"/></span>
                                        <span>
                                            <span>John Smith</span>
                                            <span class="time">3 mins ago</span>
                                        </span>
                                        <span class="message">
                                            Film festivals used to be do-or-die moments for movie makers. They were where...
                                        </span>
                                    </a>
                                </li>
                                <li>
                                    <div class="text-center">
                                        <a>
                                            <strong>See All Alerts</strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3>Profile</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>General Info</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <form method="post" action="#">
                                    <div class="row">
                                        <div class="col-md-4 col-sm-4 col-xs-12">
                                            <div class="profileImgEdit">
                                                <img src="<c:url value="/resources/img/img.jpg"/>" alt="profileImg" />
                                                <div class="cover">
                                                    <p class="coverTxt">Change Profile Picture</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-8 col-sm-8 col-xs-12">
                                            <div class="form-group">
                                                <label for="fullName">Full Name:</label>
                                                <input id="fullName" type="text" name="fullName" class="form-control" readonly="readonly" placeholder="Name" />
                                            </div>
                                            <div class="form-group">
                                                <label for="generalDescription">Description:</label>
                                                <textarea id="generalDescription" name="generalDescription" class="form-control" placeholder="Write some description..."></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="divider-dashed"></div>
                                    <div class="form-group">
                                        <div class="clearfix">
                                            <button type="submit" class="btn btn-success btnAction"><i class="fa fa-edit"></i> Update</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Aptitudes</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">

                                <!-- HIDDEN Aptitudes template -->

                                <div class="dynamic-element" style="display: none">
                                    <div class="form-group">
                                        <label for="serviceType[]">Service Type:</label>
                                        <select id="serviceType[]" name="serviceType[]" class="form-control">
                                            <option value="">Select one</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="aptDescription[]">Description:</label>
                                        <textarea id="aptDescription[]" name="aptDescription[]" class="form-control" placeholder="Write some description..."></textarea>
                                    </div>
                                    <button type="button" class="btn btn-danger btn-sm disabled deleteApt"><i class="fa fa-trash"></i> Delete Aptitude</button>
                                    <!-- Modal -->
                                    <!--<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                                        <div class="modal-dialog modal-sm">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel2">Delete aptitude</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <h4>Are you sure you want to delete the aptitude?</h4>
                                                    <p>Warning! This action cant't be undone.</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    <button type="button" class="btn btn-danger deleteApt" data-dismiss="modal"><i class="fa fa-trash"></i> Delete Aptitude</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>-->
                                    <!-- End Modal -->
                                </div>

                                <!-- End aptitudes template -->

                                <form method="post" action="#">
                                    <fieldset>
                                        <div class="dynamic-stuff">
                                            <!-- Dynamic element will be cloned here -->
                                        </div>

                                        <div class="divider-dashed"></div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-xs-12">
                                                    <div class="clearfix">
                                                        <button type="button" class="btn btn-default add-one"><i class="fa fa-plus"></i> Add new aptitude</button>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-xs-6">
                                                    <div class="clearfix">
                                                        <button type="submit" class="btn btn-success btnAction"><i class="fa fa-edit"></i> Update</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Working Details</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                Some working details
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
                © 2018 All rights reserved Home-Helper.com
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

<script type="text/javascript">
    $(document).ready(function () {
        addElement();

        //Clone the hidden element and shows it
        $('.add-one').click(function(e) {
            e.preventDefault();
            addElement();
            $('.deleteApt').removeClass('disabled');
        });


        $(document).on('click', '.deleteApt', function(e) {
            e.preventDefault();

            var dynamicElementAdded = $('.dynamic-element.added').length;

            if(dynamicElementAdded > 1) {
                $(this).closest('.dynamic-element').remove();
            }
            if(dynamicElementAdded === 2) {
                $('.deleteApt').addClass('disabled');
            }

        });

    });

    function addElement() {
        $('.dynamic-element').first().clone().addClass('added').appendTo('.dynamic-stuff').show();
    }
</script>
</body>
</html>