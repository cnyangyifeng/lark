<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>

<script src="${ctx}/resources/js/jquery.jalert.min.js"
	type="text/javascript"></script>
<!-- 学校信息 模板 -->
<script id="sectionDirectoryTemplate" type="text/x-dot-template">
 <ul class="nav list" id="sortable"> 
		{{for(var i=0,j=0;i<it.list[it.list.length-1].order;i++){}}
		  {{~it.list:lec:index}}
			{{?lec.order-1==i}}
			{{j=j+1;}}
			<li data-fdid="{{=lec.fdId}}">
            	<a href="${ctx}/admin/category/edit?fdId={{=lec.fdId}}"  title="编辑"> 
			   		<input type="checkbox" name="ids" value="{{=lec.fdId}}" />
						<span class="index">{{=j}}</span>. 
						<span class="title">{{?lec.courseCategoryName.length<20}}{{=lec.courseCategoryName}}{{??}}{{=lec.courseCategoryName.substr(0,20)+"..."}}{{?}}</span>
					<div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
         		</a>
			</li>
			{{?}}
          {{~}}
		{{}}}
 </ul>
</script>

<script src="${ctx}/resources/js/doT.min.js"></script>
</head>
<body>
<form class="toolbar-search" name="filterForm">
		<div class="page-body">
			<section class="section box-control">
				<div class="hd">
					 <div class="btn-toolbar">
						<a class="btn btn-primary" href="${ctx}/admin/category/edit">添加</a>
						<div class="btn-group">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								操作 <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="#" onclick="confirmDelePage()">批量删除</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="bd">
					<div class="btn-toolbar">
						<label class="checkbox" for="selectAll">
						<input type="checkbox" id="selectAll" name="selectAll"/>选中全部</label>
					</div>
				</div>
			</section>
			<section class="section listWrap" id="listdata">
			</section>
		</div>
</form>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.sortable.min.js"></script>
	<script type="text/javascript">
		$(function() {
				// load目录模板函数
				var loadsectionsDirectoryFn = doT.template(document.getElementById("sectionDirectoryTemplate").text);
				//加载信息
				$.ajax({
       				url : "${ctx}/ajax/category/getCourseCategory",
       				async : false,
       				dataType : 'json',
       				success : function(data) {
       					$("#listdata").html(loadsectionsDirectoryFn(data));
       				}
       			});
				var $sections = $('#sortable');
				$sections.sortable({
					 		handle: '.state-dragable',
							forcePlaceholderSize : true
						})
				.bind("sortupdate", changIndex);
				// 绑定拖放元素改变次序事件
				// 更新章节顺序方法
				function changIndex() {
					var $items = $sections.children("li"),i_le = 1, 
						data = {
							lecture : []
						}, $item;
					$items.each(function() {
						$item = $(this);
						$item.find(".index").text(i_le);
						data.lecture.push({
							order:i_le++,
							id : $item.attr("data-fdid")
						});
					});
					$.post(
							"${ctx}/ajax/category/updateOrder", 
							{
								category : JSON.stringify(data.lecture)
							}, 
							function(res) {
							}, 
							'json'
					); 
				};
				
				$("#selectAll").bind("click",function(){
					if(document.getElementById("selectAll").checked){
						$('input[name="ids"]').each(function(){
							$(this).attr("checked",true);
						});
					} else {
						$('input[name="ids"]').each(function(){
							$(this).attr("checked",false);// 
						});
					}
				});
				  
		});
		
		/* ------------------------------------------------删除-- */
		function confirmDelePage(){
			var delekey="";
			$('input[name="ids"]:checked').each(function() {
				delekey+=$(this).val()+",";
			}); 	
			if(delekey==""){
				jalert("当前没有选择要删除的数据!");
				return;
			}
				jalert("您确认要删除所有信息？",deletec);
		}
		function deletec(){
			document.filterForm.method = "post";
			document.filterForm.action = '${ctx}/admin/category/delete';
			document.filterForm.submit();
			return;
		}
	</script>
</body>
</html>