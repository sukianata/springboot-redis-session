<%--
  Created by IntelliJ IDEA.
  User: huangfan
  Date: 2018/12/22
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>index</title>
</head>
<script type="text/javascript">
    function login(){
        document.getElementById("loginForm").submit()
    }
</script>
<body>
index
<form id="loginForm" action="/doLogin" method="post">
  username:<input type="text" name="username"/>
  password:<input type="text" name="password"/>
</form>
<input type="button" value="确认" id="confirm" onclick="login()">
</body>
</html>
