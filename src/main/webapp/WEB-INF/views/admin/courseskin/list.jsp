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

</head>
<body>
<form class="toolbar-search" name="filterForm">
		<div class="page-body">
			<section class="section box-control">
				<div class="hd">
					 <div class="btn-toolbar">
						<a class="btn btn-primary" href="${ctx}/admin/course/skin/edit?fdType=${fdType}">添加</a>
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
						<div class="btn-group btns-radio" data-toggle="buttons-radio">
						   <c:if test="${fdType==''|| fdType==null ||  fdType=='01'}">
							<button class="btn btn-large active" type="button" onclick="getSkinList('01');">课程</button>
						   </c:if>
						   <c:if test="${fdType!=''&& fdType!=null && fdType!='01'}">
							<button class="btn btn-large" type="button" onclick="getSkinList('01');">课程</button>
						   </c:if>
						   <c:if test="${fdType=='02'}">
							<button class="btn btn-large active" type="button" onclick="getSkinList('02');">系列课程</button>
						   </c:if>
						   <c:if test="${fdType!='02'}">
							<button class="btn btn-large" type="button" onclick="getSkinList('02');">系列课程</button>
						   </c:if>
					      <c:if test="${fdType=='03'}">
							<button class="btn btn-large active" type="button" onclick="getSkinList('03');">个人主页</button>
						   </c:if>
						   <c:if test="${fdType!='03'}">
							<button class="btn btn-large" type="button" onclick="getSkinList('03');">个人主页</button>
						   </c:if>
						</div>
						<label class="checkbox" for="selectAll">
						<input type="checkbox" id="selectAll" name="selectAll"/>选中全部</label>
					</div>
				</div>
			</section>
			<section class="section listWrap" id="listdata">
				<ul class="nav list" id="materialList">
					<j:iter items="${list}" var="bean" status="vstatus">
						<li data-id="${bean.fdId}">
							<a href="${ctx}/admin/course/skin/edit?fdId=${bean.fdId}"> 
							 <input type="checkbox" name="ids" value="${bean.fdId}"/>
							    <span class="title">${bean.fdName}（${bean.fdSkinPath}）</span>&nbsp;
							    <j:if test="${bean.fdDefaultSkin=='true'}">
							    	<span class="label label-info">默认皮肤</span> 
							    </j:if>
							</a>
						</li>
					</j:iter>
				</ul>
			</section>
		</div>
</form>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.sortable.min.js"></script>
	<script type="text/javascript">
		$(function() {
								
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
			document.filterForm.action = '${ctx}/admin/course/skin/delete?fdType=${fdType}';
			document.filterForm.submit();
			return;
		}

		function getSkinList(fdType){
			window.location.href="${ctx}/admin/course/skin/list?fdType="+fdType;
		}
	</script>
</body>
</html>