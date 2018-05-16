<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="<c:url value="/resources/img/favicon.png"/>"/>

    <title>Home-Helper | <spring:message code="sprovider.control-panel"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/adminTemplate/build/css/custom.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/adminTemplateCustomStyles.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/controlPanelEditProfile.css" />" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <jsp:include page="leftBarMenu.jsp" />

        <!-- top navigation -->
        <jsp:include page="controlPanelMenu.jsp" />
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3><spring:message code="general.profile"/></h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="sprovider.general-info"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <c:url value="/sprovider/editProfile/editGeneralInfo" var="postUrl" />
                                <form:form modelAttribute="profileGeneralInfo" method="post" action="${postUrl}" enctype="multipart/form-data">
                                    <form:input path="elemId" type="hidden" value="0" />
                                    <c:choose>
                                        <c:when test="${errorElemId == 0}">
                                            <div class="editable edit">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="editable">
                                        </c:otherwise>
                                    </c:choose>

                                        <div class="row">
                                            <div class="col-md-4 col-sm-4 col-xs-12">
                                                <div class="profileImgEdit">
                                                    <!--<img src="<c:url value="/resources/img/img.jpg"/>" alt="profileImg" />
                                                    <div class="cover">
                                                        <p class="coverTxt"><spring:message code="sprovider.change-pic"/></p>
                                                    </div>-->
                                                    <div id="image-preview"></div><!-- si tiene foto de perfil se agrega con un style="background-image: url('');" -->
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
                                                    <form:input type="file" path="profilePicture" cssClass="edit-visible" id="image-upload" accept="image/*"/>
                                                </div>
                                            </div>
                                            <div class="col-md-8 col-sm-8 col-xs-12">
                                                <div class="form-group">
                                                    <label><spring:message code="sprovider.full-name"/>:</label>
                                                    <p data-ref="fullName"><c:out value="${provider.firstname}" /> <c:out value="${provider.lastname}" /></p>
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="generalDescription"><spring:message code="general.description"/>:</form:label>
                                                    <spring:message code="placeholder.write-description" var="placeholder" />
                                                    <form:textarea path="generalDescription" class="form-control edit-visible" placeholder="${placeholder}" />
                                                    <form:errors path="generalDescription" cssClass="form-error edit-visible" element="p" />
                                                    <p class="edit-hidden" data-ref="generalDescription"><c:out value="${provider.description}" /></p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="divider-dashed"></div>
                                        <div class="form-group">
                                            <button type="button" class="btn btn-danger btn-sm pull-right edit-visible btn-cancel"><i class="fa fa-close"></i> Cancel</button>
                                            <form:button type="submit" class="btn btn-success btn-sm pull-right edit-visible"><i class="fa fa-circle-check"></i> <spring:message code="general.update"/></form:button>
                                            <button type="button" class="btn btn-primary btn-sm pull-right edit-hidden btn-edit"><i class="fa fa-edit"></i> Edit</button>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="general.aptitudes"/></h2>
                                <button type="button" class="btn btn-default btn-sm add-one pull-right"><i class="fa fa-plus"></i> <spring:message code="aptitude.add"/></button>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="dynamic-stuff">

                                    <c:choose>
                                        <c:when test="${errorElemId == 1}">
                                        <div class="dynamic-element new-element">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="dynamic-element new-element new-element-hidden">
                                        </c:otherwise>
                                    </c:choose>
                                        <c:url value="/sprovider/editProfile/addAptitude" var="postUrl" />
                                        <form:form modelAttribute="aptitudeForm" method="post" action="${postUrl}">
                                            <form:input path="elemId" type="hidden" value="1" />
                                            <div class="editable">
                                                <div class="form-group">
                                                    <form:label path="serviceType"><spring:message code="general.service-type"/>:</form:label>
                                                    <form:select path="serviceType" class="form-control">
                                                        <form:option value=""><spring:message code="service-type.select"/></form:option>
                                                        <c:forEach items="${serviceTypes}" var="st">
                                                            <form:option value="${st.serviceTypeId}"><spring:message code="service-type.${st.serviceTypeId}"/></form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="serviceType" cssClass="form-error" element="p" />
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="aptDescription"><spring:message code="general.description"/>:</form:label>
                                                    <spring:message code="placeholder.write-description" var="placeholder" />
                                                    <form:textarea path="aptDescription" class="form-control" placeholder="${placeholder}" />
                                                    <form:errors path="aptDescription" cssClass="form-error edit-visible" element="p" />
                                                </div>
                                                <div class="divider-dashed"></div>
                                                <div class="form-group">
                                                    <button type="button" class="btn btn-danger btn-sm pull-right btn-cancel"><i class="fa fa-close"></i> Cancel</button>
                                                    <form:button type="submit" class="btn btn-success btn-sm pull-right"><i class="fa fa-circle-check"></i> <spring:message code="general.update"/></form:button>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </div>
                                        </form:form>
                                    </div>

                                    <c:forEach items="${provider.aptitudes}" var="aptitude">

                                        <div class="dynamic-element saved-element">
                                            <c:url value="/sprovider/editProfile/updateAptitude" var="postUrl" />
                                            <form:form modelAttribute="updateAptitudeForm" method="post" action="${postUrl}">
                                                <c:choose>
                                                    <c:when test="${editAptitude == aptitude.id}">
                                                        <div class="editable edit-no-dyn edit">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="editable edit-no-dyn">
                                                    </c:otherwise>
                                                    </c:choose>
                                                    <form:input type="hidden" class="action-field" path="action" value="none" />
                                                    <form:input type="hidden" path="aptitutdeId" value="${aptitude.id}" />
                                                    <div class="form-group">
                                                        <label><spring:message code="general.service-type"/>:</label>
                                                        <form:input path="serviceType" type="hidden" value="${aptitude.service.serviceTypeId}" />
                                                        <p><spring:message code="service-type.${aptitude.service.serviceTypeId}"/></p>
                                                    </div>
                                                    <div class="form-group">
                                                        <form:label path="aptDescription"><spring:message code="general.description"/>:</form:label>
                                                        <spring:message code="placeholder.write-description" var="placeholder" />
                                                        <form:textarea path="aptDescription" class="form-control edit-visible" placeholder="${placeholder}" />
                                                        <form:errors path="aptDescription" cssClass="form-error edit-visible" element="p" />
                                                        <p class="edit-hidden"><c:out value="${aptitude.description}"/></p>
                                                    </div>
                                                    <div class="divider-dashed"></div>
                                                    <div class="form-group">
                                                        <button type="button" class="btn btn-danger btn-xs disabled deleteApt pull-left edit-visible"><i class="fa fa-trash"></i> <spring:message code="aptitude.delete"/></button>
                                                        <a href="<c:url value="/sprovider/editProfile" />" class="btn btn-danger btn-sm pull-right edit-visible btn-cancel"><i class="fa fa-close"></i> Cancel</a>
                                                        <form:button type="submit" class="btn btn-success btn-sm pull-right edit-visible"><i class="fa fa-circle-check"></i> <spring:message code="general.update"/></form:button>
                                                        <a href="<c:url value="/sprovider/editProfile/updateAptitude/${aptitude.id}" />" class="btn btn-primary btn-sm pull-right edit-hidden btn-edit"><i class="fa fa-edit"></i> Edit</a>
                                                    </div>
                                                </div>
                                            </form:form>
                                        </div>

                                    </c:forEach>


                                </div>


                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="general.working-details"/></h2>
                                <button type="button" class="btn btn-default btn-sm add-one pull-right"><i class="fa fa-plus"></i> <spring:message code="ng.add"/></button>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">

                                <c:choose>
                                    <c:when test="${errorElemId == 3}">
                                        <div class="dynamic-element new-element">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="dynamic-element new-element new-element-hidden">
                                    </c:otherwise>
                                </c:choose>
                                    <div class="editable">
                                        <c:url value="/sprovider/editProfile/addNg" var="postUrl" />
                                        <form:form modelAttribute="addWZForm" action="${postUrl}" method="post">
                                            <form:input path="elemId" type="hidden" value="3" />
                                            <div class="form-group">
                                                <form:label path="ng"><spring:message code="general.city"/>:</form:label>
                                                <form:select path="ng" id="ng" class="form-control">
                                                    <form:option value=""><spring:message code="service-type.select"/></form:option>
                                                    <c:forEach items="${neightbourhoods}" var="ng">
                                                        <form:option value="${ng.ngId}"><spring:message code="neighborhood.${ng.ngId}" /></form:option>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="ng" element="p" cssClass="form-error" />
                                            </div>
                                            <div class="divider-dashed"></div>
                                            <div class="form-group">
                                                <button type="button" class="btn btn-danger btn-sm pull-right btn-cancel"><i class="fa fa-close"></i> Cancel</button>
                                                <button type="submit" class="btn btn-success btn-sm pull-right"><i class="fa fa-circle-check"></i> <spring:message code="general.update"/></button>
                                                <div class="clearfix"></div>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>


                                <div class="editable">
                                    <div class="form-group">
                                        <label><spring:message code="editWorkingZone.title" /></label>
                                        <c:forEach items="${workingZones}" var="wz" >
                                            <form method="post" action="<c:url value="/sprovider/editProfile/deleteWorkingZone" />">
                                                <input type="hidden" name="ngId" value="<c:out value="${wz.ngId}" />" />
                                                <button type="submit" class="btn btn-danger btn-sm edit-visible wz-btn"><spring:message code="neighborhood.${wz.ngId}" /> <i class="fa fa-close"></i></button>
                                            </form>
                                        </c:forEach>
                                        <ul class="edit-hidden">
                                            <c:forEach items="${workingZones}" var="wz">
                                                <li><spring:message code="neighborhood.${wz.ngId}" /></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <div class="form-group">
                                        <button type="button" class="btn btn-danger btn-sm pull-right edit-visible btn-cancel"><i class="fa fa-close"></i> Cancel</button>
                                        <button type="button" class="btn btn-primary btn-sm pull-right edit-hidden btn-edit"><i class="fa fa-edit"></i> Edit</button>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div class="pull-right">
                <spring:message code="index.rights-reserved"/>
            </div>
            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
</div>

<!-- jQuery -->
<script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>
<!-- FastClick -->
<script src="<c:url value="/resources/adminTemplate/vendors/fastclick/lib/fastclick.js"/>"></script>
<!-- NProgress -->
<script src="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.js"/>"></script>

<script type="text/javascript" src="<c:url value="/resources/js/jquery.uploadPreview.min.js"/>"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/adminTemplate/build/js/custom.min.js"/>"></script>

<script type="text/javascript">
    $(document).ready(function () {

        /* A provider cant remove an aptitude if it's de only one */
        if($(document).find('.dynamic-element.saved-element').length > 1) {
            $('.btn.deleteApt').removeClass('disabled');
        }

        /* If i'm in update mode disable btns */
        if($(document).find('.editable.edit').length > 0) {
            $('.btn-edit, .btn.add-one').addClass('disabled');
        }

        $.uploadPreview({
            input_field: "#image-upload",
            preview_box: "#image-preview",
            label_field: "#image-label"
        });

        /* Show edit action btns (Accept, cancel) when pressing edit and disabeling all edit-btn */
        $('.btn-edit').click(function() {
            if($(this).hasClass('disabled')) {
                return;
            }

            const editable = $(this).closest('.editable');

            if(editable.hasClass('edit-no-dyn')) {
                return;
            }

            editable.addClass('edit');
            $('.btn-edit, .btn.add-one').addClass('disabled');
        });

        /* Hide edit action btns (Accept, cancel) when pressing cancel and enabeling all edit-btn */
        $('.btn-cancel').click(function() {
            const editable = $(this).closest('.editable');

            if(editable.hasClass('edit-no-dyn')) {
                return;
            }

            if(editable.closest('.dynamic-element').hasClass('new-element')) {
                $('.new-element').addClass('new-element-hidden');
            }

            $('.btn-edit, .btn.add-one').removeClass('disabled');
            editable.removeClass('edit');
        });

        /* Show new aptitude when pressing  */
        $('.btn.add-one').click(function() {
            if($(this).hasClass('disabled')) {
                return;
            }

            //$('.new-element').removeClass('new-element-hidden');
            $(this).closest('.row').find('.new-element').removeClass('new-element-hidden');
            $('.btn-edit, .btn.add-one').addClass('disabled');
        });

        /* Delete aptitude */
        $('.btn.deleteApt').click(function(){
            if($(this).hasClass('disabled')) {
                return;
            }

            const form = $(this).closest('form');
            form.find('.action-field').val('delete');

            form.submit();
        });


    });

</script>
</body>
</html>