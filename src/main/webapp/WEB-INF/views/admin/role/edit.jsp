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
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="${ctx}/resources/js/html5.js"></script>
<![endif]-->
</head>

<body>
	        <div class="page-header">
                <a href="${ctx}/admin/role/list" class="backParent">
                <span id="back">返回角色列表</span>
               </a>
                <h4>角色设置</h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" type="button" onclick="saveMater();">保存</button>
                    <c:if test="${bean.fdId!='' && bean.fdId!=null}">
                    <button class="btn btn-white btn-large " type="button" onclick="confirmDel();">删除</button>
                    </c:if>
               </div>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" onkeyup="pressEnter();" method="post">
                    <input type="hidden" id="roleName" name="roleName" >
                    <section class="section">
                        <label>角色类型</label>
                        <ul class="nav nav-pills">
                        	<c:if test="${bean.roleEnum=='admin'}">
                        		<li class="active"><a data-toggle="tab" href="#admin">管理员</a></li>
                        		<li><a data-toggle="tab" href="#group">主管</a></li>
                        	</c:if>
                            <c:if test="${bean.roleEnum=='group'}">
                        		<li><a data-toggle="tab" href="#admin">管理员</a></li>
                        		<li class="active"><a data-toggle="tab" href="#group">主管</a></li>
                        	</c:if>
                        </ul>
                        <div class="tab-content">
                             <div class="tab-pane active" id="encrypt">
                                <table class="table table-bordered">
                                    <tbody id="list_user">
                                    <tr data-fdid="${bean.sysOrgPerson.fdId}">
								        <td class="tdTit">
								          <div class="pr">
								          	<tags:image href='${bean.sysOrgPerson.poto}' clas='' ></tags:image>
								          	${bean.sysOrgPerson.fdName}（${bean.sysOrgPerson.fdEmail}），${bean.sysOrgPerson.hbmParent.fdName}
								          <div>
								         </td>
								    </tr>
                                    </tbody>
                                </table>
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
	jalert("您确认要删除该角色吗？",deleteRole);
}
function deleteRole(){
	window.location.href="${ctx}/admin/role/delete/${bean.fdId}";
}

</script>

<script type="text/javascript">
$(function(){
	$("#rightCont .bder2").addClass("hide");
	
    $("#formEditDTotal").validate({
        submitHandler:saveMaterial
    });
    
    $('#formEditDTotal a[data-toggle="tab"]').on('shown', function (e) {
        var href = 	e.target.href.split("#").pop();
        $("#roleName").val(href);
    });
    
});
function saveMaterial(){
	if(!$("#formEditDTotal").valid()){
		return;
	}
    var data = {
        fdId: "${bean.fdId}",
        roleName:$("#roleName").val(),
        kingUser: null
    };
  //push人员授权数据
    data.kingUser = [];
    $("#list_user>tr").each(function(){
        data.kingUser.push({
            id: $(this).attr("data-fdid")
        });
    });
    data.kingUser = JSON.stringify(data.kingUser);
    $.ajax({
    	  type:"post",
    	  url:"${ctx}/admin/role/saveRoles",
		  async:false,
		  data:data,
		  dataType:'json',
		  success: function(rsult){
			  window.location.href="${ctx}/admin/role/list";
		  }
	});
}
function saveMater(){
	$("#formEditDTotal").trigger("submit");
}
</script>
</body>
</html>
