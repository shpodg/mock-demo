<%--
  Created by IntelliJ IDEA.
  User: oneday
  Date: 2016/7/25 0025
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>userDtail</title>
</head>
<body>
    <c:if test="${users != null}">
        <table border="1" cellspacing="0">
        <c:forEach items="${users}" var="user">
            <tr>
            <td><a href="user/${user.id}">${user.name}</a> </td><td><a href="user/${user.id}/edit">edit</a> </td>
            </tr>
        </c:forEach>
            <tr><td colspan="2"><a href="/user/new">new</a> </td> </tr>
        </table>
    </c:if>
</body>
</html>
