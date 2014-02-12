<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="active" type="java.lang.String" required="true"%>
<%@ attribute name="para" type="java.lang.String" required="false"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="text" type="java.lang.String" required="true"%>
<%@ attribute name="iconName" type="java.lang.String" required="true"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<j:ifelse test="${active eq para}">
	<j:then>
		<li class="active"><a href="${url}"><i class="${iconName}"></i>${text}</a></li>
	</j:then>
	<j:else>
		<li><a href="${url}"><i class="${iconName}"></i>${text}</a></li>
	</j:else>
</j:ifelse>