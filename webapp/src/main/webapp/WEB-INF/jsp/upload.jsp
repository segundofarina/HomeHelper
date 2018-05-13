<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>

<h3> Upload File Form
    <h3>
        <br/>
        <c:url value="/upload" var="postPath"/>
        <form:form modelAttribute="imageForm" action="${postPath}" method="Post" enctype="multipart/form-data">
            <form:input type="file" path="profilePicture" accept="image/*"/>
        <button type="submit">Upload</button>
        </form:form>
</body>
</html>
