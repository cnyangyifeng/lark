<%@tag import="cn.me.xdf.utils.ComUtils"%>
<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="clas" type="java.lang.String" required="true"%>
<%@ attribute name="href" type="java.lang.String" required="true"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%
	boolean ishttp = false;
	if (href.contains("http")) {
		ishttp = true;
	}
	
%>
<%
if(StringUtils.isBlank(href)){
	%>
	<img class="<%=clas%>" id="<%=id%>" src="${ctx}/<%=ComUtils.getDefaultPoto()%>" onerror=""/>
	<%
}
else if (ishttp) {
%>
<img class="<%=clas%>" id="<%=id%>" src="<%=href%>" />
<%
	} else {
%>
<img class="<%=clas%>" id="<%=id%>" src="${ctx}/<%=href%>" />
<%
	}
%>
