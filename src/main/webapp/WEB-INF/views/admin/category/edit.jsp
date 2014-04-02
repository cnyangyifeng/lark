<%@page import="com.kuxue.model.base.AttMain"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html class="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="${ctx}/resources/js/html5.js"></script>
<![endif]-->
</head>

<body>
	        <div class="page-header">
                <a href="${ctx}/admin/category/list" class="backParent">
                <span id="back">返回字典列表</span>
               </a>
                <h4>字典设置</h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" type="button" onclick="saveMater();">保存</button>
                    <c:if test="${bean.fdId!='' && bean.fdId!=null}">
                    <button class="btn btn-white btn-large " type="button" onclick="confirmDel();">删除</button>
                    </c:if>
               </div>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" onkeyup="pressEnter();" method="post" name="filterForm">
                    <input type="hidden" id="fdId" name="fdId" value="${bean.fdId}">
                    <section class="section">
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">名称</label>
                            <div class="controls">
								<input value="${bean.fdName}" required="required"  class="input-block-level" id="fdName" name="fdName" type="text" style="color: rgb(169, 169, 169);">
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">描述</label>
                            <div class="controls">
                            	<textarea  rows="4"
                                        class="input-block-level" id="fdDescrip"
                                        name="fdDescrip">${bean.fdDescription}</textarea>
                            </div>
                        </div>
                    </section>
                    <button class="btn btn-block btn-submit btn-inverse" type="submit">保存</button>
                </form>
            </div>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
function pressEnter(){
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (keyCode == 13) {
		return false;
	}
}
function confirmDel(){
	jalert("您确认要删除吗？",deleteRole);
}
function deleteRole(){
	window.location.href="${ctx}/admin/category/delete/${bean.fdId}";
}

</script>

<script type="text/javascript">
$(function(){
	$("#rightCont .bder2").addClass("hide");
	
    $("#formEditDTotal").validate({
        submitHandler:saveMaterial
    });
    
    
    
});
function saveMaterial(){
	if(!$("#formEditDTotal").valid()){
		return;
	}
	document.filterForm.action = '${ctx}/admin/category/save';
	document.filterForm.submit();
}
function saveMater(){
	$("#formEditDTotal").trigger("submit");
}
</script>
</body>
</html>
