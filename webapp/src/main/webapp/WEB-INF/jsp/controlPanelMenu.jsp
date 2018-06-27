<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="top_nav">
    <div class="nav_menu">
        <nav>
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li class="">
                    <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <c:choose>
                            <c:when test="${provider.image != null}">
                                <img src="<c:url value="/profile/${provider.id}/profileimage" />" alt="profile picture" />
                            </c:when>
                            <c:otherwise>
                                <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                            </c:otherwise>
                        </c:choose>
                        <c:out value="${provider.firstname}"/>
                        <span class=" fa fa-angle-down"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                        <li><a href="<c:url value="/" />"><spring:message code="general.switchToClient" /></a></li>
                        <li><a href="<c:url value="/client/settings?sp=1" />"><spring:message code="general.settings"/></a></li>
                        <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i> <spring:message code="general.logout"/></a></li>
                    </ul>
                </li>

                <!--<li role="presentation" class="dropdown">
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
                                    <strong><spring:message code="sprovider.see-alerts"/></strong>
                                    <i class="fa fa-angle-right"></i>
                                </a>
                            </div>
                        </li>
                    </ul>
                </li>-->
            </ul>
        </nav>
    </div>
</div>