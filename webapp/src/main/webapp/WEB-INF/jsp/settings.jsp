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
    <link href="<c:url value="/resources/css/signupStyles.css" />" rel="stylesheet"/>
    <style>
        .form-container {
            width: 80%;
            margin: auto;
        }
        .btn-success {
            margin-top: 2em;
            margin-right: 1em;
        }
    </style>
</head>
<body>

<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp"/>

    <!-- main content -->
    <div class="main-content">
        <div class="container">
            <h1>Settings</h1>
            <div class="panel searchForm">
                <div class="panel-body">
                    <c:url value="/client/settings/update" var="postPath"/>
                    <form:form modelAttribute="settingsForm" action="${postPath}" method="post" enctype="multipart/form-data">


                        <div class="profileImgEdit">
                            <c:url value="/tempImg/${img}" var="tempImg" />
                            <div id="image-preview" style="background-image: url('${tempImg}')"></div><!-- si tiene foto de perfil se agrega con un style="background-image: url('');" -->
                                <%--<c:choose>
                                    <c:when test="${profilePicture != null}">
                                        <c:url value="${profilePicture}" var="img" />
                                        <div id="image-preview" style="background-image: url(<c:out value='${img}' />);"></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div id="image-preview"></div>
                                    </c:otherwise>
                                </c:choose>--%>
                            <div class="cover">
                                <p class="coverTxt"><spring:message code="sprovider.change-pic"/></p>
                            </div>
                            <form:input type="file" path="profilePicture" id="image-upload" accept="image/*"/>
                            <form:input type="hidden" path="savedImgId" value="${img}" />

                        </div>

                        <div class="form-container">
                            <div class="row">
                                <div class="col-xs-12 col-sm-6">
                                    <div class="form-group">
                                        <form:label path="firstname"><spring:message code="form.firstname"/></form:label>
                                        <form:input type="text" path="firstname" cssClass="form-control"/>
                                        <form:errors path="firstname" element="p" cssClass="form-error"/>
                                    </div>

                                    <div class="form-group">
                                        <form:label path="lastname"><spring:message code="form.lastname"/></form:label>
                                        <form:input type="text" path="lastname" cssClass="form-control"/>
                                        <form:errors path="lastname" element="p" cssClass="form-error"/>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="email"><spring:message code="form.email"/></form:label>
                                        <form:input type="text" path="email" cssClass="form-control"/>
                                        <form:errors path="email" element="p" cssClass="form-error"/>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="phone"><spring:message code="form.phone"/></form:label>
                                        <form:input type="text" path="phone" cssClass="form-control"/>
                                        <form:errors path="phone" element="p" cssClass="form-error"/>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <div class="form-group">
                                        <label><spring:message code="form.username"/></label>
                                        <input type="text" readonly="readonly" class="form-control" value="<c:out value="${username}" />" />
                                    </div>
                                    <div class="form-group">
                                        <form:label path="passwordForm.password"><spring:message code="form.password"/></form:label>
                                        <form:input type="password" path="passwordForm.password" cssClass="form-control" placeholder="password"/>
                                        <form:errors path="passwordForm.password" element="p" cssClass="form-error"/>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="passwordForm.repeatPassword"><spring:message code="form.repeat-password"/></form:label>
                                        <form:input type="password" path="passwordForm.repeatPassword" cssClass="form-control" placeholder="password"/>
                                        <form:errors path="passwordForm" element="p" cssClass="form-error"/>
                                    </div>

                                </div>
                                <form:button type="submit" class="btn btn-success pull-right">Save</form:button>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>

    </div><!-- /main content -->

    <!-- footer content -->
    <footer class="footer">
        <div class="pull-right">
            <spring:message code="index.rights-reserved"/>
        </div>
        <div class="clearfix"></div>
    </footer><!-- /footer content -->

</div><!-- /container -->

<!-- jQuery -->
<script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/resources/js/jquery.uploadPreview.min.js"/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $.uploadPreview({
            input_field: "#image-upload",
            preview_box: "#image-preview",
            label_field: "#image-label"
        });
    });
</script>

</body>
</html>
