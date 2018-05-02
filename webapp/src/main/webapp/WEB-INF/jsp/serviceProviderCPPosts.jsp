<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="headInclude.jsp" />
</head>
<body class="fix-header fix-sidebar">
<!-- Preloader - style you can find in spinners.css -->
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> </svg>
</div>
<!-- Main wrapper  -->
<div id="main-wrapper">
    <!-- header header  -->

    <jsp:include page="header.jsp" />

    <!-- End header header -->
    <!-- Left Sidebar  -->

    <jsp:include page="leftSidebar.jsp" />

    <!-- End Left Sidebar  -->
    <!-- Page wrapper  -->
    <div class="page-wrapper">
        <!-- Bread crumb -->
        <div class="row page-titles">
            <div class="col-md-5 align-self-center">
                <h3 class="text-primary">Posts</h3> </div>
            <div class="col-md-7 align-self-center">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="javascript:void(0)">Apps</a></li>
                    <li class="breadcrumb-item active">Posts</li>
                </ol>
            </div>
        </div>
        <!-- End Bread crumb -->
        <!-- Container fluid  -->
        <div class="container-fluid">
            <!-- Start Page Content -->

            <div class="row postTable">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-title">
                            <h4>Posts</h4>
                            <a href="#" class="btn btn-primary btn-sm btnFloatRight"><i class="fa fa-plus"></i> Add new post</a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Post Title</th>
                                        <th>Incomings</th>
                                        <th>Status</th>
                                        <th>Edit</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach items="${postList}" var="post">
                                            <tr>
                                                <td><c:out value="${post.title}" /></td>
                                                <td class="color-success">$20.000</td>
                                                <td><span class="badge badge-success">Active</span></td>
                                                <td>
                                                    <a href="#" class="btn btn-info btn-addon btn-sm">
                                                        <i class="fa fa-folder"></i> Preview
                                                    </a>
                                                    <a href="#" class="btn btn-warning btn-addon btn-sm">
                                                        <i class="fa fa-pencil"></i> Edit
                                                    </a>
                                                    <a href="#" class="btn btn-danger btn-addon btn-sm">
                                                        <i class="fa fa-trash-o"></i> Delete
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>




            <!-- End PAge Content -->
        </div>
        <!-- End Container fluid  -->
        <!-- footer -->
        <footer class="footer"> © 2018 All rights reserved Home-Helper.com</footer>
        <!-- End footer -->
    </div>
    <!-- End Page wrapper  -->
</div>
<!-- End Wrapper -->

<jsp:include page="scriptsInclude.jsp" />

</body>
</html>
