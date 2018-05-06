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
            <a class="navbar-brand" href="#">
                <img src="<c:url value="/resources/img/HHLogo.png" />" alt="logo" />
                <span>Home-Helper</span>
            </a>
        </div>

        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">

            <button type="submit" class="btn btn-custom btn-rounded navbar-right navbar-btn">Log in</button>

            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="../navbar/">Home</a></li>
                <li><a href="../navbar-static-top/">Static top</a></li>
                <li><a href="./">Fixed top</a></li>
            </ul>

        </div><!--/.nav-collapse -->
    </div>
</nav>