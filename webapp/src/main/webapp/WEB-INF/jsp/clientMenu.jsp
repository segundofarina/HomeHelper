<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
                <c:when test="${user == null}">
                    <a href="<c:url value="/login" />" class="btn btn-custom btn-rounded navbar-right navbar-btn"><spring:message code="general.login"/></a>
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="<c:url value="/client/messages" />">Messages</a></li>
                        <li><a href="<c:url value="/client/appointments" />">Appointments</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <img src="<c:url value="/resources/img/img.jpg" />" alt="Profile picture" />
                                <c:out value="${user.firstname}" />
                                <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <c:choose>
                                    <c:when test="${userProviderId != -1}">
                                        <li><a href="<c:url value="/sprovider" />">Use as provider</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="<c:url value="/client/createSProvider" />">Create provider profile</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <li><a href="<c:url value="/client/settings" />">Settings</a></li>
                                <li><a href="<c:url value="/logout" />"><i class="fa fa-sign-out pull-right"></i>Log out</a></li>
                            </ul>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>

        </div><!--/.nav-collapse -->
    </div>
</nav>