<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<nav class="navbar navbar-fixed-top navbar-custom">
    <div class="container-fluid">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">
                <img src="<c:url value="/resources/img/HHLogo.png" />" alt="logo" />
                <span>Home-Helper</span>
            </a>
        </div>

        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">

            <c:choose>
                <c:when test="${userId == -1}">
                    <a href="/login" class="btn btn-custom btn-rounded navbar-right navbar-btn">Log in</a>
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="/client/messages">Messages</a></li>
                        <li><a href="/client/appointments">Appointments</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <img src="<c:url value="/resources/img/img.jpg" />" alt="Profile picture" />
                                Martin Victory
                                <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="/client/settings">Settings</a></li>
                                <li><a href="/logout">Log out</a></li>
                            </ul>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>

        </div><!--/.nav-collapse -->
    </div>
</nav>