<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="left-sidebar">
    <!-- Sidebar scroll-->
    <div class="scroll-sidebar">
        <!-- Sidebar navigation-->
        <nav class="sidebar-nav">
            <ul id="sidebarnav">
                <li class="nav-devider"></li>
                <li class="nav-label">Home</li>
                <li> <a href="/sprovider/<c:out value="${providerId}" />"><i class="fa fa-tachometer"></i><span>Dashboard</span></a></li>
                <li class="nav-label">Apps</li>
                <li> <a href="#"><i class="fa fa-comments"></i><span>Messages</span></a></li>
                <li> <a href="/sprovider/<c:out value="${providerId}" />/posts"><i class="fa fa-th-list"></i><span>Posts</span></a></li>
            </ul>
        </nav>
        <!-- End Sidebar navigation -->
    </div>
    <!-- End Sidebar scroll-->
</div>
