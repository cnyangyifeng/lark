<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.min.js"></script>
<script type="text/javascript">
	$(function(){
		showSearch();
	});
	
	function showSearch(){
		/* var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
		if (keyCode == 13) {
			goSearch();
			return false;
		}else{ */
			$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
			var serach = $("#fdKey").val();
			if(serach==''){
				$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
			}else{
				if(serach.length>2){
					serach = ($("#fdKey").val()).substr(0,2);
				}
				serach= serach+"...";
				$("#containkey").html(serach);
			}
		/* } */
	}
	
	function clearserach(){
		$("#fdKey").val("");
		$("#containkey").html('<a id="containkey"href="#">全部条目</a>');
		pageNavClick(1);
	}

	
	function pageNavClick(pageNo){
		var key = $('#fdKey').val();
		window.location.href=('${ctx}/admin/picture/list?pageNo='+pageNo+"&fdKey="+key);
	}
	
	//选中当前页
	function checkcurrpage(){
		if(document.getElementById("selectCurrPage").checked){
			document.getElementById("selectAll").checked=false;
			$('input[name="ids"]').each(function(){
				$(this).attr("checked",true);// 
				$(this).attr("disabled",false);
			});
		} else {
			$('input[name="ids"]').each(function(){
				$(this).attr("checked",false);// 
			});
		}
	}
	//全部选中
	function selectAlls(){
		if(document.getElementById("selectAll").checked){
			document.getElementById("selectCurrPage").checked=false;
			$('input[name="ids"]').each(function(){
				$(this).attr("checked",true);// disabled="disabled"
				//$(this).attr("disabled",true);
			});
		} else {
			$('input[name="ids"]').each(function(){
				$(this).attr("checked",false);// 
			});
		}
	}
	
	function goSearch() {
		window.location.href = '${ctx}/admin/picture/list?fdKey='+$("#fdKey").val();
		return;
	}
	
	function deleteTag(){
		var str=$("input[name='ids']:checked").length;
	    if(str==0){
	    	jalert("请选择图片");
	    }else{
	    	jalert("您确定要删除该图片？",function (){
	    		checkDelete();
	    		});
	    }
	}
	function checkDelete(){
		document.filterForm.method = "post";
		document.filterForm.action = '${ctx}/admin/picture/delete?fdKey='+$("#fdKey").val();
		document.filterForm.submit();
	}
</script>
</head>
<body>
    <form class="toolbar-search" name="filterForm">
    <div class="page-body" id="pageBody">
	<section class="section box-control">
		<div class="hd">
			<div class="btn-toolbar">
			<a class="btn btn-primary" href="${ctx }/admin/picture/edit">添加</a>
				<div class="btn-group">
				
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						操作 <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="javascript:void(0)" onclick="deleteTag()">批量删除</a></li>
					</ul>
				</div>
				<div class="toolbar-search">
					<input type="text" id="fdKey" name="fdKey" value='${fdKey }' class="search" onkeydown="showSearch();" onkeyup="showSearch();"> 
					<i class="icon-search" onclick="goSearch();"></i>
				</div>
				<span class="showState"> <span class="muted">当前显示：</span>
					<span id="markshow">
							 	<a id="containkey"href="#">全部条目</a>
					</span>
				</span> <a class="btn btn-link" href="#rightCont"  onclick="clearserach();">清空搜索结果</a>
			</div>
		</div>
		<div class="bd">
			<div class="btn-toolbar">
				<label class="checkbox inline" for="selectCurrPage">
				   <input type="checkbox" id="selectCurrPage" value="notAll" name="selectCheckbox" onclick="checkcurrpage();"/>选中本页</label>
				<label class="checkbox inline" for="selectAll">
				   <input type="checkbox" id="selectAll" value="all" name="selectCheckbox"  onclick="selectAlls();"/>选中全部</label>
				<div class="pages pull-right">
					<div class="span2">
						 第<span> 
						 ${page.startNum} - ${page.endNum}
					   </span> 
						 / <span>${page.totalCount}</span> 条 
					</div>
					<div class="btn-group">

					<c:if test="${page.pageNo <= 1}">
						<button class="btn btn-primary btn-ctrl" type="button" disabled>
							<i class="icon-chevron-left icon-white"></i>
						</button>
					</c:if>
					<c:if test="${page.pageNo > 1}">
							<button class="btn btn-primary btn-ctrl" type="button" onclick="pageNavClick(${page.prePage})">
								<i class="icon-chevron-left icon-white"></i>
							</button>
					</c:if>
					<c:if test="${page.pageNo >= page.totalPage}">
						<button class="btn btn-primary btn-ctrl" type="button" disabled>
							<i class="icon-chevron-right icon-white"></i>
						</button>
					</c:if>
					<c:if test="${page.pageNo < page.totalPage}">
							<button class="btn btn-primary btn-ctrl" type="button" onclick="pageNavClick(${page.nextPage})">
								<i class="icon-chevron-right icon-white"></i>
							</button>
					</c:if>
				</div>
				</div>
			</div>
		</div>
	</section>
	<section class="section listWrap">
		<ul class="nav list" id="materialList">
			 <j:iter items="${page.list}" var="bean" status="vstatus">
				<li data-id="${bean.fdId}">
					<a href="${ctx}/admin/picture/edit?fdId=${bean.fdId}"> 
					 <input type="checkbox" name="ids" value="${bean.fdId}"/>
					    <span class="title">${bean.fdName}</span>
					</a>
				</li>
			</j:iter> 
		</ul>
	</section>
    <div class="pages">
	  <div class="btn-group dropup">
		<c:if test="${page.firstPage==true}">
			<button class="btn btn-primary btn-ctrl" type="button" disabled>
				<i class="icon-chevron-left icon-white"></i>
			</button>
		</c:if>
		<c:if test="${page.firstPage==false}">
			<a onclick="pageNavClick(${page.prePage})">
				<button class="btn btn-primary btn-ctrl" type="button">
					<i class="icon-chevron-left icon-white"></i>
				</button>
			</a>
		</c:if>
		
          <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
             <c:choose>
                <c:when test="${page.pageNo == i}">
                    <button class="btn btn-primary btn-num active" type="button" >${i}</button>
                </c:when>
                <c:otherwise>
                <a onclick="pageNavClick(${i})">
                    <button class="btn btn-primary btn-num" type="button">${i}</button>
                 </a>
                </c:otherwise>
             </c:choose>
           </c:forEach>
		
			<button class="btn btn-primary btn-num  dropdown-toggle"
				data-toggle="dropdown" type="button">
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu pull-right">
			  
			 <c:forEach var="i" begin="${page.startOperate}" end="${page.endOperate}"> 
                  <li><a onclick="pageNavClick(${i})">
                     ${i*10-10+1} - ${i*10} 
				  </a></li>
			 </c:forEach> 
			
			</ul>

		<c:if test="${page.lastPage==true}">
			<button class="btn btn-primary btn-ctrl" type="button" disabled>
				<i class="icon-chevron-right icon-white"></i>
			</button>
		</c:if>
		<c:if test="${page.lastPage!=true}">
			<a onclick="pageNavClick(${page.nextPage})">
				<button class="btn btn-primary btn-ctrl" type="button">
					<i class="icon-chevron-right icon-white"></i>
				</button>
			</a>
		</c:if>
	  </div>
	</div>
</div>
</form>
</body>
</html>