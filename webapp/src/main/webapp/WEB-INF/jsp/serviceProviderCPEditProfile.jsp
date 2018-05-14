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
                                <form:form modelAttribute="profileGeneralInfo" method="post" action="${postUrl}">
                                    <div class="editable">
                                        <div class="row">
                                            <div class="col-md-4 col-sm-4 col-xs-12">
                                                <div class="profileImgEdit">
                                                    <img src="<c:url value="/resources/img/img.jpg"/>" alt="profileImg" />
                                                    <div class="cover">
                                                        <p class="coverTxt"><spring:message code="sprovider.change-pic"/></p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-8 col-sm-8 col-xs-12">
                                                <div class="form-group">
                                                    <label><spring:message code="sprovider.full-name"/>:</label>
                                                    <!--<input id="fullName" type="text" name="fullName" class="form-control" readonly="readonly" placeholder="<c:out value="${provider.firstname}"/> <c:out value="${provider.lastname}"/> " />-->
                                                    <p data-ref="fullName"><c:out value="${provider.firstname}" /> <c:out value="${provider.lastname}" /></p>
                                                </div>
                                                <div class="form-group">
                                                    <label for="generalDescription"><spring:message code="general.description"/>:</label>
                                                    <textarea id="generalDescription" name="generalDescription" class="form-control edit-visible" placeholder="Write some description..."><c:out value="${provider.description}" /></textarea>
                                                    <p class="edit-hidden" data-ref="generalDescription"><c:out value="${provider.description}" /></p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="divider-dashed"></div>
                                        <div class="form-group">
                                            <button type="button" class="btn btn-danger btn-sm pull-right edit-visible btn-cancel"><i class="fa fa-close"></i> Cancel</button>
                                            <button type="submit" class="btn btn-success btn-sm pull-right edit-visible"><i class="fa fa-circle-check"></i> <spring:message code="general.update"/></button>
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
                                <button type="button" class="btn btn-default btn-sm add-one pull-right"><i class="fa fa-plus"></i><spring:message code="aptitude.add"/></button>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">


                                <div class="dynamic-stuff">

                                    <div class="dynamic-element new-element new-element-hidden">
                                        <form method="post" action="#">
                                            <div class="editable">
                                                <div class="form-group">
                                                    <label for="serviceType"><spring:message code="general.service-type"/>:</label>
                                                    <select id="serviceType" name="serviceType" class="form-control">
                                                        <option value=""><spring:message code="service-type.select"/></option>
                                                        <c:forEach items="${serviceTypes}" var="st">
                                                            <option value="<c:out value="${st.serviceTypeId}"/>"><c:out value="${st.name}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="aptDescription"><spring:message code="general.description"/>:</label>
                                                    <textarea id="aptDescription" name="aptDescription" class="form-control" placeholder="Write some description..."></textarea>
                                                </div>
                                                <div class="divider-dashed"></div>
                                                <div class="form-group">
                                                    <button type="button" class="btn btn-danger btn-sm pull-right btn-cancel"><i class="fa fa-close"></i> Cancel</button>
                                                    <button type="submit" class="btn btn-success btn-sm pull-right"><i class="fa fa-circle-check"></i> <spring:message code="general.update"/></button>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <c:forEach items="${provider.aptitudes}" var="aptitude">

                                        <div class="dynamic-element saved-element">
                                            <form method="post" action="#">
                                                <div class="editable">
                                                    <input type="hidden" class="action-field" name="action" value="none" />
                                                    <div class="form-group">
                                                        <label><spring:message code="general.service-type"/>:</label>
                                                        <input name="serviceType" type="hidden" value="<c:out value="${aptitude.service.serviceTypeId}" />" />
                                                        <p><c:out value="${aptitude.service.name}" /></p>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="aptDescription"><spring:message code="general.description"/>:</label>
                                                        <textarea id="aptDescription" name="aptDescription" class="form-control edit-visible" placeholder="Write some description..."><c:out value="${aptitude.description}"/></textarea>
                                                        <p class="edit-hidden"><c:out value="${aptitude.description}"/></p>
                                                    </div>
                                                    <div class="divider-dashed"></div>
                                                    <div class="form-group">
                                                        <button type="button" class="btn btn-danger btn-xs disabled deleteApt pull-left edit-visible"><i class="fa fa-trash"></i><spring:message code="aptitude.delete"/></button>
                                                        <button type="button" class="btn btn-danger btn-sm pull-right edit-visible btn-cancel"><i class="fa fa-close"></i> Cancel</button>
                                                        <button type="submit" class="btn btn-success btn-sm pull-right edit-visible"><i class="fa fa-circle-check"></i> <spring:message code="general.update"/></button>
                                                        <button type="button" class="btn btn-primary btn-sm pull-right edit-hidden btn-edit"><i class="fa fa-edit"></i> Edit</button>
                                                    </div>
                                                </div>
                                            </form>
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
                                <h2>Working Details</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                Some working details
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

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/adminTemplate/build/js/custom.min.js"/>"></script>

<script type="text/javascript">
    $(document).ready(function () {

        /* A provider cant remove an aptitude if it's de only one */
        if($(document).find('.dynamic-element.saved-element') > 1) {
            $('.btn.deleteApt').removeClass('disabled');
        }

        /* Show edit action btns (Accept, cancel) when pressing edit and disabeling all edit-btn */
        $('.btn-edit').click(function() {
            if($(this).hasClass('disabled')) {
                return;
            }

            $(this).closest('.editable').addClass('edit');
            $('.btn-edit, .btn.add-one').addClass('disabled');
        });

        /* Hide edit action btns (Accept, cancel) when pressing cancel and enabeling all edit-btn */
        $('.btn-cancel').click(function() {
            const editable = $(this).closest('.editable');
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

            $('.new-element').removeClass('new-element-hidden');
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