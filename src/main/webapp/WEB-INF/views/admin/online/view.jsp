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
</script>
<div class="page-body editingBody">
<div class="page-header">
                
				<a href="${ctx}/admin/online/list" class="backParent">
                <span id="back">返回在线用户列表</span>
              	</a>
                <h4>
                	在线用户
                </h4>
	        </div>
                <form action="#"  class="form-horizontal" method="post" novalidate="novalidate">
                    <section class="section">
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">用户名称</label>
                            <div class="controls">
								<input value="${map.fdUserName}" readonly="readonly"  class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">用户部门</label>
                            <div class="controls">
								<input value="${map.fdUserDep}" readonly="readonly"  class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">登录时间</label>
                            <div class="controls">
								<input value="${map.loginTime}" readonly="readonly"  class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">登录IP</label>
                            <div class="controls">
								<input value="${map.ip}" readonly="readonly"  class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">登录次数</label>
                            <div class="controls">
								<input value="${map.loginNum}" readonly="readonly"  class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">登录天数</label>
                            <div class="controls">
								<input value="${map.loginDay}" readonly="readonly"  class="input-block-level" name="fdLink" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                    </section>

                </form>
            </div>
<form name="filterForm">
</form>
</body>
</html>