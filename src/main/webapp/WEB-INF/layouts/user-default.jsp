<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<j:set name="currentUrl" value="${pageContext.request.requestURI}" />
<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="zh_CN" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="zh_CN" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="zh_CN" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="zh_CN" class="no-js">
<!--<![endif]-->
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Cache-Control" content="no-cache,no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="author" content="" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>企业在线学习平台</title>
<link rel="shortcut icon" href="${ctx}/resources/img/favicon.ico" />
<!--[if lt IE 9]>
	<script src="${ctx}/resources/js/html5.js"></script>
<![endif]-->
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/DTotal.min.css" />
<link href="${ctx}/resources/css/editProfile.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resources/js/jquery.js" type="text/javascript"></script>

<sitemesh:head />
</head>
<body>
	<%@ include file="/WEB-INF/layouts/header.jsp"%>
	<section class="container">
		<section class="clearfix mt20">
			<section class="col-left pull-left">
				<ul class="nav nav-list sidenav">
					<li class="nav-header first"><span>账号设置<b class="caret"></b></span></li>
					<tags:shirourl url="${ctx}/register/updateTeacher" active="user" text="个人资料" iconName="icon-user" para="${active}"></tags:shirourl>
					<tags:shirourl url="${ctx}/register/updateIco" active="photo" text="修改头像" iconName="icon-pencil" para="${active}"></tags:shirourl>
					<tags:shirourl url="${ctx}/register/changePwd" active="pwd" text="修改密码" iconName="icon-pencil" para="${active}"></tags:shirourl>
				</ul>
			</section>

			<section class="w790 pull-right" id="rightCont">
				
					<div class="page-header bder2">
						<span class="muted">我正在看：</span>
						<j:if test="${active=='user'}">个人资料</j:if>
						<j:if test="${active=='photo'}">修改头像</j:if>
						<j:if test="${active=='pwd'}">修改密码</j:if>
						<div class="backHome">
							<a href="${ctx}/course/courseIndex"><span class="muted">返回</span>个人主页<span
								class="muted"></span> <i class="icon-home icon-white"></i> </a>
						</div>
					</div>
				
				<sitemesh:body />
			</section>
		</section>

	</section>
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	<script src="${ctx}/resources/js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
</body>
</html>