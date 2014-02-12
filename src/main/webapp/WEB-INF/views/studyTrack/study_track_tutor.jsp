<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新东方在线教师备课平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/iAmMentor.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
<script src="${ctx}/resources/js/doT.min.js" type="text/javascript"></script>
<c:import url="/WEB-INF/views/studyTrack/divtrackListTmp.jsp"></c:import>
</head>
<body>
<section class="container">	
		<div class="clearfix mt20">
	        <div class="pull-right w760">
                <div class="page-header" data-spy="affix" data-offset-top="20">
                    <span class="muted">我正在看：</span>学习跟踪 - <span id="selectMessage">我指导的课程学习情况</span>
                    <div class="pos-r">
                        <a class="btn btn-link dropdown-toggle" href="#" data-toggle="dropdown"><i class="icon-filter"></i>修改筛选条件</a>
                        <ul class="dropdown-menu pull-right" id="selectUl">
                            <li><a href="javascript:void(0)" data-type="myGuidance">我指导的课程学习情况</a></li>
                            <li><a href="javascript:void(0)" data-type="myDepart">本部门的课程学习情况</a></li>
                            <li><a href="javascript:void(0)" data-type="myOrg">本机构的课程学习情况</a></li>
                        </ul>
                    </div>
                </div>

			<!-- 列表页面 -->
			<c:import url="/WEB-INF/views/studyTrack/divtracklist.jsp">
				<c:param name="selectType" value="myGuidance"></c:param>
			</c:import>

			</div>
			<div class="pull-left w225">
            <!--用户页面 -->
			<%-- <c:import url="/WEB-INF/views/studyTrack/divuserimg.jsp">
			</c:import> --%>
			<!-- 图片列表页面 -->
			<c:import url="/WEB-INF/views/studyTrack/divimglist.jsp">
				<c:param name="type" value="tutor"></c:param>
			</c:import>
	        </div>
        </div>
</section>
<script type="text/javascript">
$("#selectUl a").bind("click",function(){
	$this = $(this);
	$("#selectTypeHidden").val($this.attr("data-type"));
	$("#selectMessage").html($this.html());
	refreshTrackList($this.attr("data-type"),1,10,'time');
});
</script>
</body>
</html>

