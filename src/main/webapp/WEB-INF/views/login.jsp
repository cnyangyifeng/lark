<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/login.css?id=22" rel="stylesheet" type="text/css">
</head>

<body>
<script type="text/javascript">
 function clearErrorMsg(){
	document.getElementById("errorMsg").innerHTML="";
 }
</script>
<div class="container main">
  <form action="" method="post">
	<div class="login_wrap">
		<div class="login_tit"></div>
		<div class="login_form">
        	<input type="text" name="username" onclick="clearErrorMsg()" class="input-block-level inp inp-mail" placeholder="请输入您的邮箱账号">
        	<input type="password" name="password" onclick="clearErrorMsg()" class="input-block-level inp inp-password" placeholder="请输入您的登录密码">
        	 <br>
            	<span id="errorMsg" class="help-block text-warning">
            	<c:if test="${error=='error'}">
            	<b class="icon-disc-bg warning">!</b> 
            	  邮箱账号或密码输入错误，请重新登录
            	  </c:if>
            	  </span>
             
            <p class="bar_link">
           	  <a href="${ctx}/forgotPwd" class="btn btn-link"><i class="icon-qm"></i>忘记密码</a>
            </p>
       	  <p class="login_btns clearfix">
            <button class="btn btn-register" type="button" onClick="location.href='${ctx }/register/add'" >注册</button>
            <button type="submit" id="loginSubmit"  class="btn btn-primary btn-login" type="button">登录</button>
            </p>
        </div>
		<div class="login_intro">请使用您的集团邮箱账号及密码直接登录本平台，或者注册临时账号以保证快速访问。<br />您在登录或者注册过程中遇到任何问题，请直接&nbsp;<a href="mailto:yangyifeng@xdf.cn?subject=<%=java.net.URLEncoder.encode("NTP使用问题","gb2312")%>&body=<%=java.net.URLEncoder.encode("问题描述及截图如下：","gb2312")%>">联系我们</a>&nbsp;。</div>
	</div>
  </form>
</div>
</body>
</html>
