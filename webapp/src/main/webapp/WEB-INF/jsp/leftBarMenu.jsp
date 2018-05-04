<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a href="/" class="site_title"><i class="fa fa-paw"></i> <span>Home-Helper</span></a>
        </div>

        <div class="clearfix"></div>

        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_pic">
                <img src="<c:url value="/resources/img/img.jpg"/>" alt="..." class="img-circle profile_img">
            </div>
            <div class="profile_info">
                <span>Welcome,</span>
                <h2>John Doe</h2>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- /menu profile quick info -->

        <br />

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <h3>General</h3>
                <ul class="nav side-menu">
                    <li><a href="/sprovider/<c:out value="${providerId}"/>"><i class="fa fa-home"></i> Home</span></a></li>
                    <li><a><i class="fa fa-user"></i> Profile <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="/sprovider/<c:out value="${providerId}"/>/editProfile">Edit profile</a></li>
                            <li><a href="/profile/<c:out value="${providerId}"/>?ownerview">Profile preview</a></li>
                        </ul>
                    </li>
                    <li><a href="/sprovider/<c:out value="${providerId}"/>/messages"><i class="fa fa-comments"></i> Messages</span></a></li>
                    <li><a href="/sprovider/<c:out value="${providerId}"/>/appointments"><i class="fa fa-table"></i> Appointments</span></a></li>
                    <li><a href="/sprovider/<c:out value="${providerId}"/>/reviews"><i class="fa fa-edit"></i> Reviews</span></a></li>
                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->

        <!-- /menu footer buttons -->
        <div class="sidebar-footer hidden-small">
            <a href="/">Use as client</a>
        </div>
        <!-- /menu footer buttons -->
    </div>
</div>