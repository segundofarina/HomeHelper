<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="<c:url value="/resources/img/favicon.png"/>"/>

    <title>Home-Helper</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>"
          rel="stylesheet"/>
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>"
          rel="stylesheet"/>
    <!-- NProgress --
        <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/createSProviderStyles.css" />" rel="stylesheet"/>
</head>
<body>

<div class="main_container">
    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp"/>

    <!-- main content -->
    <div class="main-content">
        <div class="container">
            <div class="row">
                <section>
                    <div class="wizard">
                        <div class="wizard-inner">
                            <div class="connecting-line"></div>
                            <ul class="nav nav-tabs" role="tablist">

                                <li role="presentation" class="active">
                                    <a href="#step1" data-toggle="tab" aria-controls="step1" role="tab" title="User details">
                                        <span class="round-tab">
                                            <i class="fa fa-user"></i>
                                        </span>
                                    </a>
                                </li>

                                <li role="presentation" class="disabled">
                                    <a href="#step2" data-toggle="tab" aria-controls="step2" role="tab" title="Add Provider Description">
                                        <span class="round-tab">
                                            <i class="fa fa-pencil"></i>
                                        </span>
                                    </a>
                                </li>
                                <li role="presentation" class="disabled">
                                    <a href="#step3" data-toggle="tab" aria-controls="step3" role="tab" title="Add Apptitudes">
                                        <span class="round-tab">
                                            <i class="fa fa-list"></i>
                                        </span>
                                    </a>
                                </li>

                                <li role="presentation" class="disabled">
                                    <a href="#complete" data-toggle="tab" aria-controls="complete" role="tab" title="Done">
                                        <span class="round-tab">
                                            <i class="fa fa-check"></i>
                                        </span>
                                    </a>
                                </li>
                            </ul>
                        </div>

                        <c:url value="/createSProvider" var="postPath"/>
                        <form:form modelAttribute="createSProviderForm" action="${postPath}" method="Post">
                        <div class="tab-content">
                            <div class="tab-pane active" role="tabpanel" id="step1">
                                <div class="step1">
                                    <!--<div class="row">
                                        <div class="col-md-6">
                                            <label for="exampleInputEmail1">First Name</label>
                                            <input type="email" class="form-control" id="exampleInputEmail1"
                                                   placeholder="First Name">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="exampleInputEmail1">Last Name</label>
                                            <input type="email" class="form-control" id="exampleInputEmail1"
                                                   placeholder="Last Name">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="exampleInputEmail1">Email address</label>
                                            <input type="email" class="form-control" id="exampleInputEmail1"
                                                   placeholder="Email">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="exampleInputEmail1">Confirm Email address</label>
                                            <input type="email" class="form-control" id="exampleInputEmail1"
                                                   placeholder="Email">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="exampleInputEmail1">Mobile Number</label>
                                            <input type="email" class="form-control" id="exampleInputEmail1"
                                                   placeholder="Email">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="exampleInputEmail1">Email address</label>
                                            <div class="row">
                                                <div class="col-md-3 col-xs-3">
                                                    <input type="email" class="form-control" id="exampleInputEmail1"
                                                           placeholder="Email">
                                                </div>
                                                <div class="col-md-9 col-xs-9">
                                                    <input type="email" class="form-control" id="exampleInputEmail1"
                                                           placeholder="Email">
                                                </div>
                                            </div>
                                        </div>
                                    </div>-->
                                </div>
                                <ul class="list-inline pull-right">
                                    <li>
                                        <button type="button" class="btn btn-primary next-step">Save and continue
                                        </button>
                                    </li>
                                </ul>
                            </div>
                            <div class="tab-pane" role="tabpanel" id="step2">
                                <div class="step2">
                                        <div class="row">
                                            <div class="col-md-6 col-xs12">
                                                <div class="form-group">
                                                    <label>Descripcion</label>
                                                    <textarea class="form-control"></textarea>
                                                </div>

                                            </div>
                                    </div>
                            </div>
                            <ul class="list-inline pull-right">
                                <li>
                                    <button type="button" class="btn btn-default prev-step">Previous</button>
                                </li>
                                <li>
                                    <button type="button" class="btn btn-primary next-step">Save and continue
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <div class="tab-pane" role="tabpanel" id="step3">
                            <div class="step3">
                                <div class="row">
                                    <div class="col-md-6 col-xs-12">
                                        <h1>Aptitudes</h1>
                                        <%--<div class="form-group">--%>
                                            <%--<form:label path="serviceType"><spring:message code="general.service-type"/>:</form:label>--%>
                                            <%--<form:select path="serviceType" class="form-control" >--%>
                                                <%--<form:option value=""><spring:message code="index.select-serviceType"/>...</form:option>--%>
                                                <%--<c:forEach items="${serviceTypes}" var="st">--%>
                                                    <%--<form:option value="${st.serviceTypeId}"></form:option>--%>
                                                <%--</c:forEach>--%>
                                            <%--</form:select>--%>
                                            <%--<form:errors path="serviceType" element="p" cssClass="form-error" />--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label>Description</label>--%>
                                            <%--<form:textarea path="aptDesc" class="form-control"></form:textarea>--%>
                                        <%--</div>--%>
                                    </div>
                                </div>

                            </div>
                            <ul class="list-inline pull-right">
                                <li>
                                    <button type="button" class="btn btn-default prev-step">Previous</button>
                                </li>
                                <li>
                                    <button type="button" class="btn btn-default next-step">Skip</button>
                                </li>
                                <li>
                                    <button type="button" class="btn btn-primary btn-info-full next-step">Save
                                        and continue
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <div class="tab-pane" role="tabpanel" id="complete">
                            <div class="step44">
                                <h5>Completed</h5>


                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    </form:form>
            </div>
            </section>
        </div>
    </div>
</div>
<footer class="footer">
    <div class="pull-right">
        © 2018 All rights reserved Home-Helper.com
    </div>
    <div class="clearfix"></div>
</footer><!-- /footer content -->
</div>

<!-- jQuery -->
<script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>

<script src="<c:url value="/resources/js/createSProviderJs.js"/>"></script>


</body>
</html>