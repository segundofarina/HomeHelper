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

            <button type="submit" class="btn btn-custom btn-rounded navbar-right navbar-btn"><spring:message code="general.login"/></button>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="./"><spring:message code="general.signup"/></a></li>
            </ul>

        </div><!--/.nav-collapse -->
    </div>
</nav>