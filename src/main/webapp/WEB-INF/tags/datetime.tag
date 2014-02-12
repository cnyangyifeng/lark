<%@tag import="cn.me.xdf.utils.DateUtil"%>
<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%@ attribute name="pattern" type="java.lang.String" required="true"%>
<%
	String time = "";
	if(StringUtils.isNotBlank(value)){
		time = DateUtil.getInterval(value,pattern);
	}
%>
<%= time%>