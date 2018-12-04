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
            <a class="navbar-brand" href="<c:url value="/"/>">
                <img src="<c:url value="/resources/img/HHLogo.png" />" alt="logo" />
                <span>Home-Helper</span>
            </a>
        </div>

        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">

            <c:choose>
                <c:when test="${client == null}">
                    <a href="<c:url value="/login" />" class="btn btn-custom btn-rounded navbar-right navbar-btn"><spring:message code="general.login"/></a>
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav navbar-right">
                        <%-- Show menu btns only if the client is verified --%>
                        <c:if test="${client.verified == true}" >
                            <li class="active"><a href="<c:url value="/client/messages" />" class="hvr-underline-from-center"><spring:message code="general.messages"/></a></li>
                            <li><a href="<c:url value="/client/appointments" />" class="hvr-underline-from-center"><spring:message code="general.appointments"/></a></li>
                        </c:if>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <c:choose>
                                    <c:when test="${client.image != null}">
                                        <img src="<c:url value="/profile/${client.id}/profileimage" />" alt="profile picture" />
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                    </c:otherwise>
                                </c:choose>
                                <c:out value="${client.firstname}" />
                                <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <%-- Show menu btns only if the client is verified --%>
                                <c:if test="${client.verified == true}" >
                                    <c:choose>
                                        <c:when test="${userProviderId != -1}">
                                            <li><a href="<c:url value="/sprovider"/>"><spring:message code="general.switchToProvider"/></a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="<c:url value="/client/createSProvider"/>"><spring:message code="sprovider.create-profile"/></a></li>
                                        </c:otherwise>
                                    </c:choose>
                                    <li><a href="<c:url value="/client/settings" />"><spring:message code="general.settings"/></a></li>
                                </c:if>
                                <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i><spring:message code="general.logout"/></a></li>
                            </ul>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>

        </div><!--/.nav-collapse -->
    </div>
</nav>