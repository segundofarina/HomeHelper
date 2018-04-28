<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <title>Control Panel</title>
</head>
<body>
    <h1>Service Provider Control Panel</h1>
    <p>You are service provider with id: <c:out value="${providerId}">Unknown id</c:out></p>
    <table>
        <tr>
            <th>Id</th>
            <th>Title</th>
            <th>Description</th>
            <th>Service Type</th>
        </tr>
        <c:forEach items="${postList}" var="post">
            <tr>
                <td><c:out value="${post.idPost}" /></td>
                <td><c:out value="${post.title}" /></td>
                <td><c:out value="${post.description}" /></td>
                <td><c:out value="${post.serviceType}" /></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
