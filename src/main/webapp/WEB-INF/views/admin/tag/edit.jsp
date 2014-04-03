<%@page import="cn.me.xdf.model.base.AttMain"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html class="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新东方在线教师备课平台</title>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="${ctx}/resources/js/html5.js"></script>
<![endif]-->
</head>

<body>
	<!-- 课程标签 -->
	<script id="listTagTemplate" type="x-dot-template">
       <ul class="nav list">
        {{~it :item:index}}
				<li data-id="{{=item.id}}">
					<a href="${ctx}/course/pagefoward?courseId={{=item.id}}"> 
					    <span class="title">{{=item.name}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
 						<span class="dt">发布者</span><em>&nbsp;&nbsp;{{=item.author}}</em></span>
					</a>
				</li>
        {{~}}
        </ul>
    </script>
	<script id="pageheardTemplate" type="text/x-dot-template">
	<div class="pages pull-right">
	<div class="span2">
 	   第&nbsp;<span >{{=it.startNum}} - {{=it.endNum}} / </span><span >{{=it.totalCount}}</span> 条
	</div>
	<div class="btn-group">
    <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage <= 1}} disabled {{?}} onclick='pageNavClick({{=it.currentPage-1}})'><i class="icon-chevron-left icon-white"></i></button>
    <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage == it.totalPage}} disabled {{?}} onclick='pageNavClick({{=it.currentPage+1}})'><i class="icon-chevron-right icon-white"></i></button>
	/div>
</script>
	<script id="pageEndTemplate" type="text/x-dot-template">
	<div class="btn-group dropup">
	 <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage<=1}}disabled{{?}}onclick='pageNavClick({{=it.currentPage-1}})'>
	 <i class="icon-chevron-left icon-white"></i></button>
	{{ for(var i=it.StartPage;i<=it.EndPage;i++){ }}
			{{?i==it.currentPage}}
				<button class="btn btn-primary btn-num active" type="button">{{=i}}</button>
			{{??}}
				<a  onclick="pageNavClick({{=i}})">
				<button class="btn btn-primary btn-num" type="button">{{=i}}</button>
				</a>
			{{?}}
	{{}}}
	 <button class="btn btn-primary btn-num  dropdown-toggle"  data-toggle="dropdown" type="button">
                            <span class="caret"></span></button>
	     <ul class="dropdown-menu pull-right">
		{{ for(var j=it.StartOperate;j<=it.EndOperate;j++){ }}
			<li><a href="javascript:void(0)" onclick="pageNavClick({{=j}})">{{=j*10-10+1}}-{{=j*10}}</a></li>
		{{}}}
		</ul>
     <input id="currentPage" value="{{=it.currentPage}}" type="hidden">
	 <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage == it.totalPage}} disabled {{?}} onclick='pageNavClick({{=it.currentPage+1}})'><i class="icon-chevron-right icon-white"></i></button>
</div>
</script>
	<script src="${ctx}/resources/js/doT.min.js" type="text/javascript"></script>
	<section>
		<section>
			<div class="page-header">
				<a href="${ctx}/admin/tag/list" class="backParent"> <span
					id="back">返回标签列表</span>
				</a>
				<h4>标签设置</h4>
				<div class="btn-group">
					<button class="btn btn-large btn-primary" type="button"
						onclick="saveTag();">保存</button>
					<button class="btn btn-large " type="button" id="cdelete" >删除</button>

				</div>
			</div>

			<div class="page-body editingBody">
				<form action="#" id="formEditDTotal" class="form-horizontal"
					onkeyup="pressEnter();" method="post" name="filterForm">
					<input type="hidden" id="fdId" name="fdId">
					<section class="section">
						<div class="control-group">
							<label class="control-label">名称</label>
							<div class="controls">
								<input value="${bean.fdName}" required="required"
									class="input-block-level" id="fdName" name="fdName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="videoUrl">描述</label>
							<div class="controls">
								<textarea rows="4" class="input-block-level" id="fdDescription"
									name="fdDescription">${bean.fdDescription}</textarea>
							</div>
						</div>
					</section>
					<button class="btn btn-block btn-submit btn-inverse" type="submit">保存</button>
				</form>
			</div>
		</section>
		<div class="page-body">
			<section class="section box-control">
				<div class="hd">
					<div class="btn-toolbar">
						<form class="toolbar-search">
							<input type="text" class="search" onkeydown="showSearch();"
								onkeyup="showSearch();" id="search"> <i
								class="icon-search" onclick="pageNavClick('1');"></i>
						</form>
						<span class="showState"> <span class="muted">当前显示：</span> <span
							id="markshow"> <a id="containkey" href="#">全部条目</a>
						</span>
						</span> <a class="btn btn-link" href="javaScript:void(0);"
							onclick="clearserach();">清空搜索结果</a>
					</div>
				</div>
				<div class="bd">
					<div class="btn-toolbar">
						<div id="pageheard"></div>
					</div>
				</div>
			</section>
			<section class="section listWrap" id="listCourse"></section>
			<div class="pages" id="pageEnd"></div>
		</div>
	</section>
	<input type="hidden" id="coursekey" name="coursekey">
	<input type="hidden" id="deletekey" name="deletekey">
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/messages_zh.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.sortable.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
	<script src="${ctx}/resources/js/jquery.jalert.min.js"
		type="text/javascript"></script>

	<script type="text/javascript">
		/**********************initpage*********************************************/
		$.Placeholder.init();
		//头部翻页
		var pageheardFn = doT.template(document
				.getElementById("pageheardTemplate").text);
		//底部翻页
		var pageendFn = doT
				.template(document.getElementById("pageEndTemplate").text);
		var listcourseFn = doT.template(document
				.getElementById("listTagTemplate").text);
		$(function() {
			var tagId = "${param.fdId}";//标签id
			var keyword = "${param.keyword}";//搜索关键字
			var result;
			var pageInfo;
			$("#formEditDTotal").validate({
				submitHandler : function(form) {
					$.ajax({
						type : "post",
						url : "${ctx}/ajax/tag/saveTagInfo",
						data : {
							fdId : $("#fdId").val(),
							fdName : $("#fdName").val(),
							fdDescription : $("#fdDescription").val()
						},
						async : false,
						cache : false,
						dataType : "json",
						success : function(data) {
							$("#fdId").val(data.id);
							deleStatus();
							if (data.id != "") {
								jalert_tips("保存成功");
							} else {
								jalert_tips("保存失败");
							}
							//window.location.href="${ctx}/admin/tag/edit?fdId="+data.id;  
						}
					});
				}
			});
			$.ajax({
				type : "post",
				url : "${ctx}/ajax/tag/getTagInfoOfcourses",
				data : {
					fdId : tagId,
					keyword : keyword,
					pageNo : 1
				},
				async : false,
				cache : false,
				dataType : "json",
				success : function(data) {
					$("#pageheard").html(pageheardFn(data));
					$("#pageEnd").html(pageendFn(data));
					$("#listCourse").html(listcourseFn(data.list));
					//alert(JSON.stringify(data.list))
					result = data.course;
					pageInfo = data;
					$("#fdId").val(data.fdId);
					deleStatus();
					$("#fdName").val(data.fdName);
					$("#fdDescription").val(data.fdDesc);

				}
			});
			$("#search").keypress(function(e) { //回车事件 
				var key = window.event ? e.keyCode : e.which;
				if (key.toString() == "13") {
					pageNavClick('1');
					return false;//此处代码作用防止回车提交两次表单  原因可能为页面存在两个form所致
				}
			});
		});
		/**********************methods***************************************************/

		function clearserach() {//清理搜索栏并显示数据列表
			$("#search").val("");
			$("#markshow").html('<a id="containkey"href="#">全部条目</a>');
			pageNavClick('1');
		}

		function showSearch() {
			var search = $("#search").val();
			$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
			if (search == '') {
				$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
			} else if (search.length > 10) {
				$("#containkey").html(search.substr(0, 10) + "...");
			} else {
				$("#containkey").html(search);
			}
		}

		//翻页
		function pageNavClick(pageNo) {

			var keyword = $("#search").val();
			if ($('input[name="selectCheckbox"]:checked').val() == 1) {
				$("#allkey").attr("value", 1);
			}
			var order = "${param.order}";
			if ($("#orderBy").val() != "") {
				order = $("#orderBy").val();
			}
			$.ajax({
				type : "post",
				url : "${ctx}/ajax/tag/getTagInfoOfcourses",
				data : {
					fdId : "${param.fdId}",
					order : order,
					pageNo : pageNo,
					keyword : keyword
				},
				cache : false,
				dataType : "json",
				success : function(data) {
					$("#pageheard").html(pageheardFn(data));
					$("#pageEnd").html(pageendFn(data));
					$("#listCourse").html(listcourseFn(data.list));
				}
			});
		}
		function saveTag() {
			$("#formEditDTotal").trigger("submit");
		}
		function deleteTag() {
			
			$.ajax({
    	    	type: "post",
    	    	url: "${ctx}/ajax/tag/isExsitTagOfcourse",
    	    	data : {
    	    		fdKey:$('#fdKey').val(),
    	     		ids:$("#fdId").val()+","
    	    	},
    	    	async : false,
				cache : false,
				dataType : "json",
    	    	success:function(data){	
    	    		if(data){
    	    			jalert("选定标签在课程中有引用,确认删除？",function (){
    	    				window.location.href = "${ctx}/admin/tag/delete?fdId="+$("#fdId").val();
    	    			});
    	    			
    	    		}else{
    	    			window.location.href = "${ctx}/admin/tag/delete?fdId="+$("#fdId").val();
    	    		}
    	    	}
    	    }); 
		}
		function deleStatus() {
			if ($("#fdId").val() == "") {
				$("#cdelete").removeClass("btn-primary");
				$("#cdelete").unbind("click", deleteTag);
			} else {
				$("#cdelete").addClass("btn-primary");
				$("#cdelete").bind("click", deleteTag);
			}
		}
	</script>
</body>
</html>
