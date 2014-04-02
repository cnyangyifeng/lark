<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
</head>
<body>
       <div class="page-body box-control" id="pageBody"> 
          <form  class="reg_form form-horizontal">
            <c:if test="${person.fdIsEmp=='0'}">
           <input type="hidden" id="fdId" name="fdId" value="${person.fdId}"/>
        	<p class="reg_form-intro">请确认您要修改的新密码。</p>
        	<div class="control-group" style="height: 40px;">
        		<label for="user" class="control-label">临时账号</label>
        		<div class="controls">
                	<span class="inp-placeholder">${person.fdEmail}</span>
                </div>
        	</div>    
             <div class="control-group" style="height: 40px;">
        		<label for="oldPwd" class="control-label">原密码 <span class="text-error">*</span></label>
        		<div class="controls">
                	<input id="oldPwd" type="password" onclick="clearCss(this);" onblur="checkOldPwd();"class="span4" placeholder="请填写您的原密码" />
                    <span class="help-inline"><b class="icon-disc-bg warning">!</b>请正确填写原密码</span>
                </div>
        	</div>
            <div class="control-group" style="height: 40px;">
        		<label for="newPwd" class="control-label">新密码<span class="text-error">*</span></label>
        		<div class="controls">
                	 <input id="newPwd" type="password" name="password"  onclick="clearCss(this);" onblur="checkNewPwd();"  class="span4" placeholder="请填写您的新密码">
                    <span class="help-inline"><b class="icon-disc-bg warning">!</b>请正确填写新密码</span>
                </div>
        	</div>     
            <div class="control-group" style="height: 40px;">
        		<label for="confirmPwd" class="control-label">确认密码<span class="text-error">*</span></label>
        		<div class="controls">
                  <input id="confirmPwd" type="password" class="span4" onblur="confirmNewPwd();" onclick="clearCss(this);" value="" placeholder="请再次确认您的新密码 ">         
                  <span class="help-inline"><b class="icon-disc-bg warning">!</b>密码输入不一致，请重新确认</span>
                </div>
        	</div>           
            <div class="control-group" style="height: 40px;">
            	<div class="controls">
                	<button type="button" onclick="submitUpdatePwd();"  class="submit btn btn-primary btn-large" >确认修改</button>
                </div>
            </div>
             </c:if>
        <c:if test="${person.fdIsEmp=='1'}">
            <p class="reg_form-intro">请确认您要修改的新密码。</p>
        	<div class="control-group" >
        		<label for="user" class="control-label">集团账号</label>
        		<div class="controls">
                	<span class="inp-placeholder">${person.fdEmail}</span>
                </div>
        	</div>    
            <div class="control-group">
               <label class="control-label">修改密码<span class="text-error">*</span></label>
            	<div class="controls">
            	  <a href="https://adpassport.xdf.cn/changePassword.aspx">
                	<button type="button" class="btn btn-primary btn-large" >修改密码</button>
                 </a>
                </div>
            </div>
        </c:if>
        </form>
        </div>
        <script type="text/javascript">
$.Placeholder.init();
</script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
function checkOldPwd(){
	var oldPwd=$("#oldPwd").val();
	 $.ajax({
		type : "post",
		cache :false,
		async: false,
		dataType : 'json',
		url : "${ctx}/ajax/register/checkOldPwd",
		data : {
			"str" : oldPwd,
			"fdId": $("#fdId").val()
		},
		success : function(data) {
			var flag = data.flag;
			if (flag=='1') {
				checkFlag.oldPwd=true;
			} else {
				checkFlag.oldPwd=false;
				var node = document.getElementById("oldPwd");
				var child1 = node.parentNode;
				var child2 = child1.parentNode;
			    child2.className = "control-group warning";
			} 
		}
	}); 
}
//检查新密码格式
function checkNewPwd(){
  var node = document.getElementById("newPwd");
  var newPwd = $('#newPwd').val();
  var pattern = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){7,19}$/;
  if(!pattern.test(newPwd)){
	  checkFlag.newPwd=false;
	  var child1 = node.parentNode;
	  var child2 = child1.parentNode;
	  child2.className = "control-group warning";
  } else {
	  checkFlag.newPwd=true;
  }
}
//检查确认密码格式
function confirmNewPwd(){
	var node = document.getElementById("confirmPwd");
	var newPwd = $('#newPwd').val();
	var confirmPwd = $('#confirmPwd').val();
	if(newPwd != confirmPwd){
		checkFlag.confirmPwd=false;
		var child1 = node.parentNode;
		var child2 = child1.parentNode;
		child2.className = "control-group warning";
	} else {
		checkFlag.confirmPwd=true;
	}
}
//清楚警告显示
function clearCss(node){
  var node1 = node.parentNode.parentNode;	
  node1.className = "control-group";
}
//表单项目是否通过检查的标识
var checkFlag = {
	"newPwd" :false,
	"oldPwd" :false,
	"confirmPwd" :false,
};
function checkSubmit(){
	if (checkFlag.newPwd && checkFlag.oldPwd && checkFlag.confirmPwd) {
		  return true;// 允许提交
		} else {
		  if(!checkFlag.newPwd){
			  checkOldPwd();
		  }
		  if(!checkFlag.oldPwd){
			  checkNewPwd();
		  }
		  if(!checkFlag.confirmPwd){
			  confirmNewPwd(); 
		  }
		  return false;// 阻止提交
		}
}
function submitUpdatePwd(){
	if(checkSubmit()){
		$.ajax({
			type : "post",
			cache :false,
			url : "${ctx}/ajax/register/updateTeacherPwd",
			data : {
				"password": $("#confirmPwd").val()
			},
			success : function() {
				jalert("您好，修改密码成功!");
				$("#oldPwd").val("");
				$("#newPwd").val("");
				$("#confirmPwd").val("");
			}
		}); 
	} 
}
</script>
</body>
</html>
