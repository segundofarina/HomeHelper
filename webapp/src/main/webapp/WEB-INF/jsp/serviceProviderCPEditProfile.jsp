<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
                                <form method="post" action="#">
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
                                                <label for="fullName"><spring:message code="form.full-name"/></label>
                                                <input id="fullName" type="text" name="fullName" class="form-control" readonly="readonly" placeholder="<c:out value="${provider.firstname}"/> <c:out value="${provider.lastname}"/> " />
                                            </div>
                                            <div class="form-group">
                                                <label for="generalDescription"><spring:message code="form.description"/></label>
                                                <textarea id="generalDescription" name="generalDescription" class="form-control" placeholder=""><c:out value="${provider.description}" /></textarea>
                                                <%--<textarea id="generalDescription" name="generalDescription" class="form-control" placeholder="<spring:message code="sprovider.write-description"/>"><c:out value="${provider.description}" /></textarea>--%>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="divider-dashed"></div>
                                    <div class="form-group">
                                        <div class="clearfix">
                                            <button type="submit" class="btn btn-success btnAction"><i class="fa fa-edit"></i><spring:message code="general.update"/></button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="general.aptitudes"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">

                                <!-- HIDDEN Aptitudes template -->



                                <div class="dynamic-element" style="display: none">
                                    <div class="form-group">
                                        <label for="serviceType[]"><spring:message code="form.service-type"/></label>
                                        <select id="serviceType[]" name="serviceType[]" class="form-control">
                                            <option value=""><spring:message code="service-type.select"/></option>
                                            <c:forEach items="${serviceTypes}" var="st">
                                                <option value="<c:out value="${st.serviceTypeId}"/>"><c:out value="${st.name}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="aptDescription[]"><spring:message code="form.description"/></label>
                                        <textarea id="aptDescription[]" name="aptDescription[]" class="form-control" placeholder="Write some description..."><c:out value="${st.name}"/></textarea>
                                    </div>
                                    <button type="button" class="btn btn-danger btn-sm disabled deleteApt"><i class="fa fa-trash"></i><spring:message code="aptitude.delete"/></button>
                                </div>

                                <!-- End aptitudes template -->

                                <form method="post" action="#">
                                    <fieldset>
                                        <div class="dynamic-stuff">
                                            <!-- Dynamic element will be cloned here -->

                                            <c:forEach items="${provider.aptitudes}" var="aptitude">



                                            <div class="dynamic-element" >
                                                <div class="form-group">
                                                    <label for="serviceType[]"><spring:message code="form.service-type"/></label>
                                                    <select id="serviceType[]" name="serviceType[]" class="form-control" disabled="disabled">
                                                        <option value="<c:out value="${aptitude.service.serviceTypeId}"/>"><c:out value="${aptitude.service.name}"/></option>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="aptDescription[]"><spring:message code="form.description"/></label>
                                                    <textarea id="aptDescription[]" name="aptDescription[]" class="form-control" placeholder="<spring:message code="placeholder.write-description"/>"><c:out value="${aptitude.description}"/></textarea>
                                                </div>
                                                <button type="button" class="btn btn-danger btn-sm disabled deleteApt"><i class="fa fa-trash"></i><spring:message code="aptitude.delete"/></button>
                                            </div>

                                            </c:forEach>

                                        </div>

                                        <div class="divider-dashed"></div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-xs-12">
                                                    <div class="clearfix">
                                                        <button type="button" class="btn btn-default add-one"><i class="fa fa-plus"></i><spring:message code="aptitude.add"/></button>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-xs-6">
                                                    <div class="clearfix">
                                                        <button type="submit" class="btn btn-success btnAction"><i class="fa fa-edit"></i><spring:message code="general.update"/></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2><spring:message code="general.working-details"/></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <spring:message code="sprovider.write-details"/>
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
        //addElement();

        //Clone the hidden element and shows it
        $('.add-one').click(function(e) {
            e.preventDefault();
            addElement();
            $('.deleteApt').removeClass('disabled');
        });


        $(document).on('click', '.deleteApt', function(e) {
            e.preventDefault();

            var dynamicElementAdded = $('.dynamic-element.added').length;

            if(dynamicElementAdded > 1) {
                $(this).closest('.dynamic-element').remove();
            }
            if(dynamicElementAdded === 2) {
                $('.deleteApt').addClass('disabled');
            }

        });

    });

    function addElement() {
        $('.dynamic-element').first().clone().addClass('added').appendTo('.dynamic-stuff').show();
    }
</script>
</body>
</html>