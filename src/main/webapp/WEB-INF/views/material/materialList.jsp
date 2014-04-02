<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@page import="com.kuxue.utils.ShiroUtils"%>
<%@page import="com.kuxue.service.ShiroDbRealm.ShiroUser"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<%
 String fdType = request.getParameter("fdType");
 String order = request.getParameter("order");
 boolean isAdmin = ShiroUtils.isAdmin();
 request.setAttribute("isAdmin", isAdmin);
%>
 <html class=""> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
</head>
<body>

<section class="container">
	<section class="clearfix mt20">
	
     <section class="col-left pull-left">
	  <%@ include file="/WEB-INF/views/group/menu.jsp" %>
	  </section>
    	 
		<section class="w790 pull-right" id="rightCont">
	        <div class="page-header bder2" data-spy="affix" data-offset-top="20">
	           <c:if test="${param.fdType!=1000}">
                <span class="muted">我正在看：</span>课程素材库 -
                 <c:if test="${param.fdType=='01'}">视频</c:if>
                 <c:if test="${param.fdType=='02'}">音频</c:if>
                 <c:if test="${param.fdType=='04'}">文档</c:if> 
                 <c:if test="${param.fdType=='05'}">幻灯片</c:if> 
                 <c:if test="${param.fdType=='08'}">测试</c:if> 
                 <c:if test="${param.fdType=='10'}">作业包</c:if> 
                 <c:if test="${param.fdType=='07'}">在线创作</c:if> 
                </c:if> 
              
                <div class="backHome">
                    <a href="${ctx}/studyTrack/getStudyTrackDirector"><span class="muted">返回</span>主管<span class="muted">首页</span> <i class="icon-home icon-white"></i> </a>
                </div>
	        </div>
	        <div class="page-body" id="pageBody">
		          <c:if test="${param.fdType=='08'}"><%@ include file="/WEB-INF/views/material/divMatQuestList.jsp" %></c:if>  
                  <c:if test="${param.fdType=='10'}"><%@ include file="/WEB-INF/views/material/divMatTaskList.jsp" %></c:if> 
	              <c:if test="${param.fdType!='08'&&param.fdType!='10'}"><%@ include file="/WEB-INF/views/material/divMatList.jsp" %></c:if>
            </div>           
	    </section>
	</section>

</section>
<input type="hidden" id="showkey" name="showkey">
<input type="hidden" id="fdOrder" value="${param.order}">
<input type="hidden" id="allFlag" >
<input type="hidden" id="isAdmin" value="${isAdmin}">
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
function pressEnter(){
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (keyCode == 13) {
		pageNavClick('${param.fdType}','1', $("#fdOrder").val()); 
	}
}
function showSearch(){
	$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
	var serach = $("#serach").val();
	if(serach==''){
		$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
	}else{
		if(serach.length>2){
			serach = ($("#serach").val()).substr(0,2);
		}
		serach= serach+"...";
		$("#containkey").html(serach);
	}
	
}
function clearserach(){
	$("#serach").attr("value","");
	$("#containkey").html('<a id="containkey"href="#">全部条目</a>');
	pageNavClick('${param.fdType}',1,'FDCREATETIME');
}
//选中当前页
function checkcurrpage(){
	if(document.getElementById("selectCurrPage").checked){
		document.getElementById("selectAll").checked=false;
		$('input[name="ids"]').each(function(){
			$(this).attr("checked",true);// 
			$(this).attr("disabled",false);
		});
		$("#allFlag").attr("value",false);
	} else {
		$('input[name="ids"]').each(function(){
			$(this).attr("checked",false);// 
		});
		$("#allFlag").attr("value",false);
	}
}
//全部选中
function selectAll(){
	if(document.getElementById("selectAll").checked){
		document.getElementById("selectCurrPage").checked=false;
		$('input[name="ids"]').each(function(){
			$(this).attr("checked",true);// disabled="disabled"
			$(this).attr("disabled",true);
		});
		$("#allFlag").attr("value",true);
	} else {
		$('input[name="ids"]').each(function(){
			$(this).attr("checked",false);// 
			$(this).attr("disabled",false);
		});
		$("#allFlag").attr("value",false);
	}
}
</script>
<script type="text/javascript">	
function pageNavClick(fdType,pageNo,order){
	var fdName = document.getElementById("serach").value;
	if(order==null){
		order = FDCREATETIME;
	}
	$("#fdOrder").attr("value",order);
	$("#showkey").attr("value",fdName);//关键字赋值
	$("#pageBody").html("");
	$.ajax({
		type: "post",
		 url: "${ctx}/ajax/material/findList",
		data : {
			"fdName" : fdName,
			"fdType" : fdType,
			"pageNo" : pageNo,
			"order" : order
		},
		cache: false, 
		dataType: "html",
		success:function(data){	
			$("#pageBody").html(data);
			$("#show").html($("#showkey").val());
			if(fdName!=""&&fdName!=null){
				$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
				if(fdName.length>2){
					fdName = fdName.substr(0,2);
					fdName= fdName+"...";
				}
				$("#containkey").html(fdName);
			}
			else{
				$("#containkey").html('<a id="containkey"href="#">全部条目</a>');
			}
			$("#serach").attr("value",$("#showkey").val());
			if($("#allFlag").val()=='true'){
				document.getElementById("selectAll").checked=true;
				selectAll();
			}
		}
	}); 
}
</script>
<script type="text/javascript">	
//jquery获取复选框值  
function batchDelete() {
	var delekey="";
	$('input[name="ids"]:checked').each(function() {
		delekey+=$(this).val()+",";
	});
	if(delekey==""){
		jalert("当前没有选择要删除的数据!");
		return;
	}
	//是否全部选中
	if($("#allFlag").val()=='true'){
		if($("#isAdmin").val()=='true'){
			jalert("是否删除所有素材？",deleteAllMaterial);
		}else{
			prepareDelete(delekey);
			jalert("是否删除所有素材？",deleteAllMaterial);
		}
	}else{
		if($("#isAdmin").val()=='true'){
			deleteMater();
		}else{
			prepareDelete(delekey);
			setTimeout(deleteMater(),1000);
		}
	}
}
function deleteMater(){
	var delekeyAuth="";
	$('input[name="ids"]:checked').each(function() {
		delekeyAuth+=$(this).val()+",";
	});
	if(delekeyAuth==""){
		jalert("当前没有选择要删除的数据!");
		return;
	}
	jalert("是否删除所选素材？",ajaxDelete);
}
function ajaxDelete(){
	var delekeyAuth="";
	$('input[name="ids"]:checked').each(function() {
		delekeyAuth+=$(this).val()+",";
	});
	$.ajax({
		type: "post",
		async: false,
		url: "${ctx}/ajax/material/batchDelete",
		data : {
			"materialIds":delekeyAuth
		},
		success:function(data){
			window.location.href="${ctx}/material/findList?order=FDCREATETIME&fdType="+$("#fdType").val();
		}
    });
}
function prepareDelete(delekey){
	$.ajax({
		type: "post",
		async: false,
		url: "${ctx}/ajax/material/prepareDelete",
		data : {
			"materialIds":delekey
		},
		success:function(data){
			var res = eval(data);
			if(res.length==0){
				$('input[name="ids"]').each(function(){
					$(this).attr("checked",false);
				});
				
			}else{
			  for (var i = 0; i < res.length; i++) {
				$('input[name="ids"]').each(function(){
					if($(this).val()==res[i]){
						$(this).attr("checked",true);
						$(this).attr("disabled",false);
				    }else{
						$(this).attr("checked",false);
					}
						
				});
			  } 
			}
		}
    });
}
//删除所有
function deleteAllMaterial(){
	var fdName = document.getElementById("serach").value;
	 $.ajax({
		type: "post",
		async: false,
		url: "${ctx}/ajax/material/deleteAllMaterial",
		data : {
			"fdName":fdName,
			"fdType":$("#fdType").val()
		},
		success:function(data){
			window.location.href="${ctx}/material/findList?order=FDCREATETIME&fdType="+$("#fdType").val();
		}
	}); 
}
</script>
</body>
</html>
