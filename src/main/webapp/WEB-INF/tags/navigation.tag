<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@tag import="com.kuxue.utils.ShiroUtils"%>
<%@tag import="com.kuxue.service.ShiroDbRealm.ShiroUser"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="paths" type="java.lang.String" required="true"%>
<%@ attribute name="checkString" type="java.lang.String" required="true"%>
<%@ attribute name="showString" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%
ShiroUser user = ShiroUtils.getUser();
if(user!=null){
	request.setAttribute("userBean", user);
}
boolean ac = false;
String[] path = checkString.split(":");
for(int i=0;i<path.length;i++){
	if(path[i]!=""){
		if(paths.contains(path[i])){
			ac=true;
			break;
		}
	}
}
%>
<% if(ac){ %>
<li class="active">
<%}else{ %>
<li>
<%} %>
<% 
if(StringUtils.isNotBlank(id)){
%>
<a href="<%=url%>" id="<%=id%>"><%=showString%></a>
<%} else {%>
<a href="<%=url%>"><%=showString%></a>
<%}%>
</li>
	          
	         