<%@page import="cn.me.xdf.utils.ShiroUtils"%>
<%@page import="cn.me.xdf.service.ShiroDbRealm.ShiroUser"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%
ShiroUser user = ShiroUtils.getUser();
if(user!=null){
	request.setAttribute("userBean", user);
}
String paths = request.getRequestURI();
String[] path = paths.split("/");
request.setAttribute("path", path[path.length-1]);
%>
 <div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
    	<div class="container pr">
			<a href="${ctx}" class="logo"></a>
	        <ul class="nav" id="topNav">
	          <% if(user!=null){ %>
	          		<li class="itemBg"></li>	
	        	<li class="specific"><a href="${ctx}/course/courseIndexAll">全部课程</a></li>
	          <%} %>
	          <shiro:hasRole name="admin">
	         	<tags:navigation paths="<%=paths%>" checkString="/admin" showString="系统管理" url="${ctx}/admin/user/list"></tags:navigation>
	          </shiro:hasRole>
	          
	          <shiro:hasRole name="guidance">
	          	<tags:navigation paths="<%=paths%>" id="unCheckFlag" checkString="/studyTrack/getStudyTrackTutor:/adviser" showString="我是导师" url="${ctx}/adviser/checkTask?order=fdcreatetime"></tags:navigation>
	          </shiro:hasRole>
	          <shiro:hasRole name="group">
	         	<tags:navigation paths="<%=paths%>" checkString="/studyTrack/getStudyTrackDirector:/material/find:/material/materialFoward:/course/find:/course/add:/series/find:/series/add:/course/get" showString="我是主管" url="${ctx}/studyTrack/getStudyTrackDirector"></tags:navigation>
	          </shiro:hasRole>
	          <% if(user!=null){ %>
	         	 <tags:navigation paths="<%=paths%>" checkString="/course/courseIndex:/passThrough:/successPage:series/studayfoward" showString="个人主页" url="${ctx}/course/courseIndex"></tags:navigation>
	           <%} %>
</ul>
			<shiro:authenticated>
            <ul class="nav pull-right">
            	<shiro:hasRole name="group">
            	<li>
                    <a class="btn-publish" title="发布课程" href="${ctx}/course/add">
                        <i class="icon-book-pencil"></i>
                    </a>
                </li>
                </shiro:hasRole>
              <li class="dropdown">
              	<a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                	<span class="top-face" id="notify" >
                	 <tags:image href="${userBean.poto}" clas="media-object img-face" />
                	 <i class="icon-disc"></i></span>
                    <span class="name"><shiro:principal/></span>
                    <b class="caret"></b>
                </a>
                 <ul class="dropdown-menu">
                   <li><a href="${ctx}/course/courseIndex"><i class="icon-home"></i>个人主页</a></li>
                    <li><a href="${ctx}/letter/findLetterList"><i class="icon-envelope"></i>我的私信
                    <span class="icon-disc-bg" id="msgNum"></span></a></li>
                    <li><a href="${ctx}/register/updateTeacher"><i class="icon-user"></i>账号设置</a></li>
                    <li><a href="${ctx}/logout"><i class="icon-off"></i>退出平台</a></li>
                </ul>
              </li>
              <li><a href="${ctx}/logout" class="btn-off"><span class="divider">|</span>退出</a></li>
            </ul>
            </shiro:authenticated>
             <shiro:notAuthenticated>            
            <ul class="nav pull-right">
            <j:ifelse test="${path == 'login' || path=='add'}">
				<j:then>
					<li><a href="${ctx}/">首页</a></li>
				</j:then>
				<j:else>
					<li><a href="${ctx}/login">登录</a></li>
				</j:else>
			</j:ifelse>
             <j:ifelse test="${path=='add'}">
				<j:then>
					<li><a href="${ctx}/login">登录</a></li>
				</j:then>
				<j:else>
					<li><a href="${ctx }/register/add">注册</a></li>
				</j:else>
			 </j:ifelse>
	        </ul>
	        </shiro:notAuthenticated>
	        <div id="notify_box" class="hide"></div>
		</div>
    </div>
</div>
<script type="text/javascript">
//一个汉字相当于2个字符
  function get_length(s){
      var char_length = 0;
      if(s==null){
      	return char_length;
      }
      for (var i = 0; i < s.length; i++){
          var son_char = s.charAt(i);
          encodeURI(son_char).length > 2 ? char_length += 1 : char_length += 0.8;
      }
      return parseInt(char_length);
  }
  function cut_str(str, len){
      var char_length = 0;
      
      if(get_length(str)>len){
      	for (var i = 0; i < str.length; i++){
              var son_str = str.charAt(i);
              encodeURI(son_str).length > 2 ? char_length += 1 : char_length += 0.8;
              if (char_length >= len){
                  var sub_len = char_length == len ? i+1 : i;
                  return str.substr(0, sub_len)+"...";
                  break;
              }
          }
      }
      return str;
  }
function getUnreadNotifyCount(){
 $.ajax({
    type: "post",
    dataType: "json",
    url: "${ctx}/ajax/letter/getUnReadNum",
    data: {},
    success: function(data){
    	if(data>0){
        	$("#notify").append('<i class="icon-disc"></i>');
        	$("#msgNum").text(data);
    	}else{
    		$("#notify .icon-disc").remove();
    		$("#msgNum").remove();
    	}
    }
 }); 
}
//我是导师 是否有未批改的作业
function getUnCheckTask(){
	 $.ajax({
	    type: "post",
	    dataType: "json",
	    url: "${ctx}/ajax/adviser/getUnCheckNum",
	    data: {},
	    success: function(data){
	    	 if(data>0){
	    		$("#unCheckFlag").append('<i class="icon-disc"></i>');
	    	}else{
	    		$("#unCheckFlag .icon-disc").remove();
	    	} 
	    }
	 }); 
}
getUnreadNotifyCount();
getUnCheckTask();
</script>