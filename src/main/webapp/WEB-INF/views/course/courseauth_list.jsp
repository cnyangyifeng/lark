<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<%
 String order = request.getParameter("order");
//String fdType = request.getParameter("fdType");

%>
 <html class=""> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
</head>
<body>

		<section class="container">
			<section class="clearfix mt20">
			  <section class="col-left pull-left">
		    	 <%@ include file="/WEB-INF/views/group/menu.jsp" %>
			  </section>
				<section class="w790 pull-right" id="rightCont">
			        <div class="page-header bder2" data-spy="affix" data-offset-top="20">
		                <span class="muted">我正在看：</span>
						<c:if test="${param.fdType=='13'}">
							按课程授权学习
						</c:if>
				        <c:if test="${param.fdType=='14'}">
							按新教师授权学习
						</c:if>
						<c:if test="${param.fdType=='15'}">
							按导师授权学习
						</c:if>
		                <div class="backHome">
		                    <a href="${ctx}/studyTrack/getStudyTrackDirector"><span class="muted">返回</span>主管<span class="muted">首页</span> <i class="icon-home icon-white"></i> </a>
		                </div>
			        </div>
			     
				<div class="page-body" id="pageBody">
				<c:if test="${param.fdType=='13'}">
					<%@ include file="/WEB-INF/views/course/divcourseauthlist.jsp" %>
				</c:if>
		        <c:if test="${param.fdType=='14'}">
					<%@ include file="/WEB-INF/views/course/divteacherauthlist.jsp" %>
				</c:if>
				<c:if test="${param.fdType=='15'}">
					<%@ include file="/WEB-INF/views/course/divtutorauthlist.jsp" %>
				</c:if>
		       </div>
            <!-- 缓存插叙关键字 -->
			<input type="hidden" id="coursekey" name="coursekey">
			<input type="hidden" id="allFlag" >
			<input type="hidden"  id="cousetype" value="${param.fdType}">
            <input type="hidden" id="cachorder" value="${param.order}"/>
	    </section>
	</section>
</section>

</body>
</html>
