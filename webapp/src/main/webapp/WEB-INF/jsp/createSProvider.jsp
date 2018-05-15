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
            <div class="title">
                <h2><spring:message code="sprovider.register"/></h2>
            </div>
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

                        <c:url value="/client/createSProvider" var="postPath"/>
                        <form:form modelAttribute="createSProviderForm" action="${postPath}" method="Post">
                        <div class="tab-content">
                            <div class="tab-pane active" role="tabpanel" id="step1">
                                <div class="step1">
                                    <div class="form-container">
                                        <c:if test="${hasError == 1}">
                                            <div class="alert alert-danger"><spring:message code="form.invalid-fields"/></div>
                                        </c:if>
                                        <div class="subtitle">
                                            <h4><spring:message code="user.details"/></h4>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6">
                                                <div class="form-group">
                                                    <form:label path="firstname"><spring:message code="form.firstname"/></form:label>
                                                    <c:choose>
                                                        <c:when test="${hasError == 0}">
                                                            <form:input path="firstname" value="${user.firstname}" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="firstname" value="${user.firstname}" type="text" cssClass="form-control" placeholder="<spring:message code="general.firstname"/>"/>--%>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:input path="firstname" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="firstname" type="text" cssClass="form-control" placeholder="<spring:message code="general.firstname"/>"/>--%>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <form:errors path="firstname" element="p" cssClass="form-error" />
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="lastname"><spring:message code="form.lastname"/></form:label>
                                                    <c:choose>
                                                        <c:when test="${hasError == 0}">
                                                            <form:input path="lastname" value="${user.lastname}" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="lastname" value="${user.lastname}" type="text" cssClass="form-control" placeholder="<spring:message code="general.lastname"/>"/>--%>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:input path="lastname" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="lastname" type="text" cssClass="form-control" placeholder="<spring:message code="general.lastname"/>"/>--%>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <form:errors path="lastname" element="p" cssClass="form-error" />
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6">
                                                <div class="form-group">
                                                    <form:label path="email"><spring:message code="form.email"/></form:label>
                                                    <c:choose>
                                                        <c:when test="${hasError == 0}">
                                                            <form:input path="email" value="${user.email}" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="email" value="${user.email}" type="text" cssClass="form-control" placeholder="<spring:message code="general.email"/>"/>--%>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:input path="email" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="email" type="text" cssClass="form-control" placeholder="<spring:message code="general.email"/>"/>--%>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <form:errors path="email" element="p" cssClass="form-error" />
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="phone"><spring:message code="form.phone"/></form:label>
                                                    <c:choose>
                                                        <c:when test="${hasError == 0}">
                                                            <form:input path="phone" value="${user.phone}" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="phone" value="${user.phone}" type="text" cssClass="form-control" placeholder="<spring:message code="general.phone"/>"/>--%>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:input path="phone" type="text" cssClass="form-control" placeholder=""/>
                                                            <%--<form:input path="phone" type="text" cssClass="form-control" placeholder="<spring:message code="general.phone"/>"/>--%>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <form:errors path="phone" element="p" cssClass="form-error" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="pull-right btn-container">
                                            <button type="button" class="btn btn-success next-step"><spring:message code="sprovider.save-continue"/></button>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" role="tabpanel" id="step2">
                                <div class="step2">
                                    <div class="form-container">
                                        <div class="subtitle">
                                            <h4><spring:message code="sprovider.provider-description"/></h4>
                                        </div>
                                        <div>
                                            <div class="form-group">
                                                <form:label path="profileDesc"><spring:message code="form.about-yourself"/></form:label>
                                                <form:textarea path="profileDesc" cssClass="form-control resize-vertical" placeholder=""/>
                                                <%--<form:textarea path="profileDesc" cssClass="form-control resize-vertical" placeholder="<spring:message code="sprovider.write-description"/>"/>--%>
                                                <form:errors path="profileDesc" element="p" cssClass="form-error" />
                                            </div>
                                        </div>
                                        <div class="btn-container">
                                            <div class="pull-left">
                                                <button type="button" class="btn btn-default prev-step"><spring:message code="general.previus"/></button>
                                            </div>
                                            <div class="pull-right">
                                                <button type="button" class="btn btn-success next-step"><spring:message code="sprovider.save-continue"/></button>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" role="tabpanel" id="step3">
                                <div class="step3">
                                    <div class="form-container">
                                        <div class="subtitle">
                                            <h4><spring:message code="sprovider.add-aptitude"/></h4><h6><spring:message code="sprovider.add-later"/></h6>
                                        </div>
                                        <div>
                                            <div class="apptitude-row">
                                                <div class="form-group">
                                                    <form:label path="serviceType"><spring:message code="form.service-type"/></form:label>
                                                    <form:select path="serviceType" cssClass="form-control">
                                                        <form:option value=""><spring:message code="sprovider.select-service-type"/></form:option>
                                                        <c:forEach items="${serviceTypes}" var="st">
                                                            <form:option value="${st.serviceTypeId}"><c:out value="${st.name}" /></form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="serviceType" element="p" cssClass="form-error" />
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="aptDesc"><spring:message code="form.description"/></form:label>
                                                    <form:textarea path="aptDesc" cssClass="form-control resize-vertical" placeholder=""/>
                                                    <%--<form:textarea path="aptDesc" cssClass="form-control resize-vertical" placeholder="<spring:message code="sprovider.write-about-skills"/>"/>--%>
                                                    <form:errors path="aptDesc" element="p" cssClass="form-error" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="btn-container">
                                            <div class="pull-left">
                                                <button type="button" class="btn btn-default prev-step"><spring:message code="general.previus"/></button>
                                            </div>
                                            <div class="pull-right">
                                                <button type="button" class="btn btn-success next-step"><spring:message code="sprovider.save-continue"/></button>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" role="tabpanel" id="complete">
                                <div class="step4">
                                    <div class="form-container">
                                        <div class="finish">
                                            <h3><spring:message code="sprovider.ready"/></h3>
                                            <div class="img"></div>
                                        </div>
                                        <div class="btn-container">
                                            <div class="pull-left">
                                                <button type="button" class="btn btn-default prev-step"><spring:message code="general.previus"/></button>
                                            </div>
                                            <div class="pull-right">
                                                <form:button type="submit" class="btn btn-success"><spring:message code="sprovider.save-use"/></form:button>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                    </form:form>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <footer class="footer">
        <div class="pull-right">
            <spring:message code="index.rights-reserved"/>
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