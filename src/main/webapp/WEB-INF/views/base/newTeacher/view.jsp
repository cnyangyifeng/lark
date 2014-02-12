<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/settings.css" rel="stylesheet" type="text/css">

</head>

<body>

<div class="fixed-left">
	<div class="container">
		<div class="col-left">
    	<ul class="nav nav-list sidenav">
        	<li class="nav-header">
            	<div class="tit-icon_bg"><i class="icon-gear"></i><i class="icon-sj"></i></div>
                            账号设置
            </li>
    		<li class="active"><a href="${ctx}/register/updateTeacher"><i class="icon-user"></i>个人资料</a></li>
            <li><a href="${ctx}/register/updateIco"><i class="icon-user"></i>修改头像</a></li>
            <li><a href="${ctx}/register/changePwd"><i class="icon-pencil"></i>修改密码</a></li>
    	</ul>
    	</div>
    </div>
</div>
<div class="container">
	<div class="col-right">    	
    	<div class="section">
        <div class="page-header">
        	<div class="tit-icon_bg"><i class="icon-user icon-white"></i><i class="icon-sj"></i></div>
        	<h5>个人资料</h5>
            <a href="${ctx}/course/courseIndex" title="返回主页" class="replyMybk"><i class="icon-home icon-white"></i></a>
        </div> 
       <div class="page-body"> 
          <form method="post" id="inputForm" action="${ctx}/register2/updateTeacher" class="reg_form form-horizontal">
          <input type="hidden" name="fdId" value="${bean.fdId}"/>
          <input type="hidden" id="fdIcoUrl" name="fdIcoUrl" value="${bean.fdIcoUrl}" />
        	<p class="reg_form-intro">以下信息将显示在您的
        	<a href="${ctx}/trainee/welcome">个人主页</a>
        	上，方便大家了解您。</p>
        	<div class="control-group" style="height: 70px;">
        		<label for="face" class="control-label">头像</label>
        		<div class="controls">
        		   
                	<a class="face pull-left">                	
                	 <tags:image href="${bean.fdIcoUrl}" clas="media-object img-face" />
                        <h6 align="left" >
                        </h6>
                    </a>
                </div>
        	</div> 
        	
             <div class="control-group">
        		<label for="name" class="control-label">姓名<span class="text-error">*</span></label>
        		<div class="controls">
                	<input id="notifyEntity.realName" type="text" class="span4" value="<shiro:principal/>"  readOnly>
                </div>
        	</div>
            <div class="control-group">
        		<label for="ID" class="control-label">证件号码<span class="text-error">*</span></label>
        		<div class="controls">
                	<input type="text" id="fdIdentityCard" name="fdIdentityCard"class="span4" value="${bean.fdIdentityCard}" readOnly>
                </div>
        	</div>
             <div class="control-group">
        		<label for="org" class="control-label">机构/部门 <span class="text-error">*</span></label>
        		<div class="controls">
                	<%-- <input id="org" type="text" class="span2" value="${sysParOrg}"  readonly>
                    <input id="department" type="text" class="span2" value="${bean.deptName}"  readonly> --%>
                    <select name="org" id="org" disabled="disabled">
                      <option value="">${sysParOrg}</option>
                	</select>
                    <select name="department" id="department" disabled="disabled">
                	    <option value=''>${bean.deptName}</option>
                	</select> 
                </div>
        	</div>
            <div class="control-group">
        		<label for="tel" class="control-label">电话<span class="text-error">*</span></label>
        		<div class="controls">
                	<input id="tel" type="text" class="span4" name="notifyEntity.fdMobileNo" value="${bean.fdMobileNo}"  readonly>
                </div>
        	</div>
            <div class="control-group">
        		<label for="sex" class="control-label">性别<span class="text-error">*</span></label>
        		<div class="controls">
                 	<label for="male" class="radio inline"><input name="fdSex" id="male" type="radio" value="M" disabled="disabled" <j:if test="${bean.fdSex=='M'}">checked</j:if>> 男</label>
                    <label for="female" class="radio inline"><input name="fdSex" id="female" type="radio" value="F" disabled="disabled" <j:if test="${bean.fdSex=='F'}">checked</j:if>> 女</label>
              </div>
        	</div>
             <div class="control-group">
        		<label for="birthday" class="control-label">出生日期</label>
        		<div class="controls">
					   <input id="birthday" type="text" name="fdBirthDay" class="span4" value="${bean.fdBirthDay}"  readonly>
                </div>
        	</div>
             <div class="control-group">
        		<label for="blood" class="control-label">血型</label>
        		<div class="controls">
                    <label for="a" class="radio inline"><input name="fdBloodType" id="a" type="radio" disabled="disabled" <j:if test="${bean.fdBloodType =='A'}">checked</j:if> > A</label>
                    <label for="b" class="radio inline"><input name="fdBloodType" id="b" type="radio" disabled="disabled" <j:if test="${bean.fdBloodType =='B'}">checked</j:if> > B </label>
                    <label for="ab" class="radio inline"><input name="fdBloodType" id="ab" type="radio" disabled="disabled" <j:if test="${bean.fdBloodType =='AB'}">checked</j:if> > AB</label>
                    <label for="o" class="radio inline"><input name="fdBloodType" id="o" type="radio" disabled="disabled" <j:if test="${bean.fdBloodType =='O'}">checked</j:if> >O </label>
                    <label for="rh" class="radio inline"><input name="fdBloodType" id="rh" type="radio" disabled="disabled" <j:if test="${bean.fdBloodType =='RH'}">checked</j:if> >RH </label>
                    <label for="bld-other" class="radio inline"><input name="fdBloodType" id="bld-other" type="radio" disabled="disabled" <j:if test="${bean.fdBloodType == 'OTHER'}">checked</j:if> >不详 </label>
                </div>
        	</div>
            <div class="control-group">
        		<label for="selfIntro" class="control-label">自我介绍</label>
        		<div class="controls">
                	<textarea id="selfIntro" class="span4" rows="5" readonly></textarea>
                </div>
        	</div>
            <div class="control-group">
            	<div class="controls">
                	<button type="submit" disabled="disabled" class="submit btn btn-primary btn-large" >确认修改</button>
                </div>
            </div>
        </form>
        </div>
      </div>
    </div>
</div>

</body>
</html>
