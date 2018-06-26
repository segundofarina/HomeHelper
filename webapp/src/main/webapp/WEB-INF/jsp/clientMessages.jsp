<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <title>Home-Helper | <spring:message code="general.messages"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" />
    <!-- NProgress --
    <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/chatStyles.css" />" rel="stylesheet" />
</head>

<body>
<div class="main_container">

    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp" />

    <!-- main content -->
    <c:choose>
        <c:when test="${chats.size() == 0}">
            <div class="main-content">
                <div class="container-fluid">
                    <div class="panel">
                        <div class="panel-body msg-container">
                    <div class="empty-msg">
                        <div class="img"></div>
                        <p><spring:message code="emptyMsg" /></p>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>

            <!-- Chat content -->
            <div class="chat-container">
                <div id="frame">
                    <div id="sidepanel">
                        <div id="search">
                            <label><i class="fa fa-search" aria-hidden="true"></i></label>
                            <input type="text" placeholder="Search contacts..." />
                        </div>
                        <div id="contacts">
                            <ul>
                                <c:forEach items="${chats}" var="chat">
                                    <c:url value="/client/messages/${chat.grey.id}" var="chatThreadUrl" />
                                    <a href="${chatThreadUrl}" class="thumbItem">
                                        <c:choose>
                                            <c:when test="${chat.grey.id == currentChat.grey.id}">
                                                <li class="contact active">
                                            </c:when>
                                            <c:otherwise>
                                                <li class="contact">
                                            </c:otherwise>
                                        </c:choose>
                                            <div class="wrap">
                                                <!-- add when there are unread msg and is not currentChat
                                                <span class="contact-status"></span>
                                                -->
                                                <c:choose>
                                                    <c:when test="${chat.grey.image != null}">
                                                        <img src="<c:url value="/profile/${chat.grey.id}/profileimage" />" alt="Profile picture" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class="meta">
                                                    <p class="name"><c:out value="${chat.grey.firstname}"/> <c:out value="${chat.grey.lastname}" /></p>
                                                    <p class="preview"><c:out value="${chat.preview}" /></p>
                                                </div>
                                            </div>
                                        </li>
                                    </a>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="content">
                        <div class="contact-profile">
                            <c:choose>
                                <c:when test="${currentChat.grey.image != null}">
                                    <img src="<c:url value="/profile/${currentChat.grey.id}/profileimage" />" alt="Profile picture" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                </c:otherwise>
                            </c:choose>
                            <p><c:out value="${currentChat.grey.firstname}"/> <c:out value="${currentChat.grey.lastname}" /></p>
                        </div>
                        <div class="messages">
                            <ul>
                                <c:forEach items="${currentChat.messages}" var="msg">
                                    <c:choose>
                                        <c:when test="${msg.from == currentChat.green.id}">
                                            <li class="sent">
                                            <c:choose>
                                                <c:when test="${currentChat.green.image != null}">
                                                    <img src="<c:url value="/profile/${currentChat.green.id}/profileimage" />" alt="Profile picture" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="replies">
                                            <c:choose>
                                                <c:when test="${currentChat.grey.image != null}">
                                                    <img src="<c:url value="/profile/${currentChat.grey.id}/profileimage" />" alt="Profile picture" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<c:url value="/resources/img/defaultProfile.png" />" alt="Profile picture" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                    <p><c:out value="${msg.message}" /></p>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="message-input">
                            <div class="wrap">
                                <c:url value="/client/messages/${currentChat.grey.id}" var="msgPostPath" />
                                <form action="<c:out value="${msgPostPath}" />" method="POST">
                                    <input type="text" name="msg" id="msgField" autocomplete="off" placeholder="<spring:message code="placeholder.write-message"/>" />
                                    <button class="submit" value="<spring:message code="general.send"/>" >
                                        <i class="fa fa-paper-plane" aria-hidden="true"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </c:otherwise>
    </c:choose>
    <!-- /main content -->

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
<!-- FastClick --
<script src="<c:url value="/resources/adminTemplate/vendors/fastclick/lib/fastclick.js"/>"></script>
<!-- NProgress --
<script src="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.js"/>"></script>-->

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/js/customJs.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var scrollableContent = $(".messages");
        scrollableContent.scrollTop(scrollableContent[0].scrollHeight);

        $("#msgField").focus();

        /* Search filter */
        $("#search input").keyup(function () {
            var filter = $(this).val().toUpperCase();

            var contactsList = $("#contacts ul li");
            for(var i = 0; i < contactsList.length; i++){
                var name = $(contactsList[i]).find(".name")[0].innerHTML.toUpperCase();

                if(name.indexOf(filter) > -1) {
                    contactsList[i].style.display = "";
                } else {
                    contactsList[i].style.display = "none";
                }
            }
        });
    });
</script>

</body>
</html>
