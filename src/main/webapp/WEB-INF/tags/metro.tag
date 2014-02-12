<%@tag import="cn.me.xdf.utils.ShiroUtils"%>
<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="href" type="java.lang.String" required="false"%>
<%@ attribute name="imgUrl" type="java.lang.String" required="true"%>
<%@ attribute name="text" type="java.lang.String" required="true"%>
<%@ attribute name="roleNames" type="java.lang.String" required="false"%>
<%@ attribute name="width" type="java.lang.String"%>
<%@ attribute name="height" type="java.lang.String"%>
<%@ attribute name="cssClass" type="java.lang.String"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%
	String css_class = "tile";
	href="href=\""+href+"\"";
	if (StringUtils.isNotBlank(roleNames)) {
		if (!ShiroUtils.hasAnyRoles(roleNames)) {
		css_class = "tile disabled";
		href = "";
		}
	}
	if (StringUtils.isNotBlank(cssClass)) {
		css_class = css_class + " " + cssClass;
	}
	if (StringUtils.isBlank(width)) {
		width = "60";
	}
	if (StringUtils.isBlank(height)) {
		height = "60";
	}
%>
<a <%=href%> class="<%=css_class%>"> <img src="<%=imgUrl%>"
	height="<%=height%>" width="<%=width%>" />
	<h4><%=text%></h4>
</a>