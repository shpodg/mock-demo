<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: oneday
  Date: 2016/7/25 0025
  Time: 23:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/user" method="post">
    <input type="hidden" name="id" value="${user.id}"/>
    <table border="1" cellspacing="0">
        <tr><td>userName</td><td><input name="name" value="${user.name}"/></td></tr>
        <tr><td>userAge</td><td><input name="age" value="${user.age}"/></td></tr>
        <tr><td>userSex</td><td><input name="sex" value="${user.sex}" /></td></tr>
        <tr><td colspan="2">
            <c:if test="${user.id == null}">
            <input type="hidden" name="_method" value="put">
            <input type="submit" value="create"/>
            </c:if>
            <c:if test="${user.id != null}">
            <input type="submit" value="update"/>
            </c:if>
        </td></tr>
    </table>

</form>
</body>
</html>
