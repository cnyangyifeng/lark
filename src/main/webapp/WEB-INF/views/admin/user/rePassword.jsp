<%@page import="org.apache.commons.lang3.ArrayUtils"%>
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
<body>

<div class="page-header">
                <a href="${ctx}/admin/user/list" class="backParent">
                <span id="back">返回用户列表</span>
               </a>
                <h4>重置密码</h4>
	        </div>       
<div class="page-body editingBody"> 
       <div class="section">
       		<form id="subForm" onsubmit="return checkSubmit();" action="${ctx}/admin/user/savePassword" method="post" class="reg_form form-horizontal">
           <input type="hidden" id="fdId" name="fdId" value="${fdId}"/>
        	<p class="muted">请确认您要修改的新密码。</p>
        	<div class="control-group">
        		<label for="user" class="control-label">临时账号</label>
        		<div class="controls">
                	<label  class="control-label">${fdEmail}</label>
                </div>
        	</div>   
            <div class="control-group">
        		<label for="newPwd" class="control-label">密码</label>
        		<div class="controls">
                	 <input id="newPwd" type="password" name="fdPassword"  onclick="clearCss(this);" onblur="checkNewPwd();"  class="span4" placeholder="请填写密码">
                    <!-- <span class="help-inline"><b class="icon-disc-bg warning">!</b>请正确填写密码</span> -->
                </div>
        	</div>     
            <div class="control-group" >
            	<div class="controls">
                	<button type="submit" id="submitForm"  class="submit btn btn-primary btn-large" >确认修改</button>
                </div>
            </div>
        </form>
       </div>
          
</div>
<script type="text/javascript">
$.Placeholder.init();

//检查密码格式
function checkNewPwd(){
  var node = document.getElementById("newPwd");
  var newPwd = $('#newPwd').val();
  var pattern = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){7,19}$/;
  if(!pattern.test(newPwd)){
	  var child1 = node.parentNode;
	  var child2 = child1.parentNode;
	  child2.className = "control-group warning";
	  return false;
  } else {
	  return true;
  }
}

//清楚警告显示
function clearCss(node){
  var node1 = node.parentNode.parentNode;	
  node1.className = "control-group";
}

function checkSubmit(){
	if (checkNewPwd()) {
		  return true;// 允许提交
		} else {
		  return false;// 阻止提交
		}
}
</script>
</body>
</html>
