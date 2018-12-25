<%--
  Created by IntelliJ IDEA.
  User: huangfan
  Date: 2018/12/22
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("ctx",request.getContextPath());
%>
<html>
<head>
    <title>login success</title>
</head>
<body>
Already login.Login User:<h1>${username}</h1>
</body>
</body>
</html>
