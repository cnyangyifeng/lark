<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<body>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.min.js"></script>
<script type="text/javascript">
$("#rightCont .bder2").addClass("hide");
function delById(fdId,fdType){
	jalert("您确定要删除该日志？",function(){
		document.filterForm.method = "post";
		document.filterForm.action = '${ctx}/admin/log/delete?fdId='+fdId+'&fdType='+fdType;
		document.filterForm.submit();
	});
}
</script>
<div class="page-body editingBody">
<div class="page-header">
                
              	<c:if test="${fdType=='LogLogin'}">
				<a href="${ctx}/admin/log/list?fdType=LogLogin" class="backParent">
                <span id="back">返回登录日志列表</span>
              	</a>
                </c:if>
                <c:if test="${fdType=='LogLogout'}">
				<a href="${ctx}/admin/log/list?fdType=LogLogout" class="backParent">
                <span id="back">返回登出日志列表</span>
              	</a>
                </c:if>
                <c:if test="${fdType=='LogApp'}">
				<a href="${ctx}/admin/log/list?fdType=LogApp" class="backParent">
                <span id="back">返回操作日志列表</span>
              	</a>
                </c:if>
              	
                <h4>
                <c:if test="${fdType=='LogLogin'}">
				登录日志
                </c:if>
                <c:if test="${fdType=='LogLogout'}">
				登出日志
                </c:if>
                <c:if test="${fdType=='LogApp'}">
				操作日志
                </c:if>
                </h4>
                <div class="btn-group">
                    <button class="btn btn-white btn-large " type="button" onclick="delById('${map.fdLogId}','${fdType}')">删除</button>
               </div>
	        </div>
                <form action="#"  class="form-horizontal" method="post" novalidate="novalidate">
                    <section class="section">
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">操作人</label>
                            <div class="controls">
									<input value="${map.fdUserName}" readonly="readonly"  class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">部门</label>
                            <div class="controls">
									<input value="${map.fdUserDep}" readonly="readonly" class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">日志类型</label>
                            <div class="controls">
									<input value="${map.logType}" readonly="readonly" class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                         <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">操作时间</label>
                            <div class="controls">
									<input value="${map.time}" readonly="readonly" class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <c:if test="${fdType=='LogLogin'||fdType=='LogLogout'}">
                         <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">登录IP</label>
                            <div class="controls">
									<input value="${map.ip}" readonly="readonly" class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${fdType=='LogApp'}">
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">对应表</label>
                            <div class="controls">
									<input value="${map.modelName}" readonly="readonly" class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">对应ID</label>
                            <div class="controls">
									<input value="${map.modelId}" readonly="readonly" class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">操作详情</label>
                            <div class="controls">
									<textarea  rows="7" readonly="readonly" class="input-block-level" style="color: rgb(169, 169, 169);">${map.content}</textarea>
                            </div>
                        </div>
                        </c:if>
                    </section>

                </form>
            </div>
<form name="filterForm">
</form>
</body>
</html>