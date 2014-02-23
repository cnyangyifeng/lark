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
		                <span class="muted">我正在看：</span>授权学习
		                <div class="backHome">
		                    <a href="${ctx}/studyTrack/getStudyTrackDirector"><span class="muted">返回</span>主管<span class="muted">首页</span> <i class="icon-home icon-white"></i> </a>
		                </div>
			        </div>
			     
				<div class="page-body" id="pageBody">
		        <%@ include file="/WEB-INF/views/course/divcourseauthlist.jsp" %>
		       </div>
            <!-- 缓存插叙关键字 -->
			<input type="hidden" id="coursekey" name="coursekey">
			<input type="hidden" id="allFlag" >
			<input type="hidden"  id="cousetype" value="${param.fdType}">
            <input type="hidden" id="cachorder" value="${param.order}"/>
	    </section>
	</section>
</section>
<script type="text/javascript">
//导出列表   
function exportData(){
	var fdTitle = $("#serach").val();
	var order = $("#cachorder").val();
	jalert("您确定要导出全部数据吗？",function(){
		  window.location.href="${ctx}/common/exp/getExpAllCourseAuth?order="+order+"&fdTitle="+fdTitle;
	}); 
}
</script>
<script type="text/javascript">	
function pressEnter(){//回车事件
	if(event.keyCode==13){
		findeCoursesByKey(1,$('#cachorder').val());
	}
}
function clearserach(){//清理搜索栏并显示数据列表
	//alert('ss');
	$("#serach").attr("value","");
	$("#markshow").html('<a id="containkey"href="#">全部条目</a>');
	findeCoursesByKey(1,'fdcreatetime');
}

function showSearch(){
	var fdTitle = document.getElementById("serach").value;
	$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
	if(fdTitle==''){
		$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
	}else if(fdTitle.length>2){
		$("#containkey").html(fdTitle.substr(0,2)+"...");
		}else{
			$("#containkey").html(fdTitle);
		}
}
function findeCoursesByKey(pageNo,order){
	var fdTitle = document.getElementById("serach").value;
	if($('input[name="selectCheckbox"]:checked').val()==1){
		$("#allkey").attr("value",1);
	}
	$('#cachorder').attr('value',order);
	$("#coursekey").attr("value",fdTitle);//关键字赋值
	$("#pageBody").html("");
	$.ajax({
		type: "post",
		 url: "${ctx}/ajax/course/getCoureAuthInfosOrByKey",
		data : {
			"fdTitle" : fdTitle,
			"pageNo" : pageNo,
			"order" : order,
		},
		cache: false, 
		dataType: "html",
		success:function(data){
			//alert(data);
			var serachkey=$("#coursekey").val();
			$("#pageBody").html(data);
			if(fdTitle!=""&&fdTitle!=null){
				$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
				if(fdTitle.length>2){
					$("#containkey").html(fdTitle.substr(0,2)+"...");
					}else{
						$("#containkey").html(fdTitle);
					}
			}
			else{
				$("#containkey").html('<a id="containkey"href="#">全部条目</a>');
				
			}
			
			$("#serach").attr("value",serachkey);
			if($("#allFlag").val()=='true'){
				document.getElementById("selectAll").checked=true;
				selectAll();
			}
		}
	}); 
}
</script>
</body>
</html>
