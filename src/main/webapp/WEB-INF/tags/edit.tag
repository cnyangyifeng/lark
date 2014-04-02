<%@ tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="标签name,必填"%>
<%@ attribute name="width" type="java.lang.String" description="宽度,非必填"%>
<%@ attribute name="height" type="java.lang.String" description="高度,非必填"%>
<%@ attribute name="value" type="java.lang.String" description="值,非必填"%>
<%@ attribute name="editid" type="java.lang.String" required="true" description="标签唯一标识"%>
<%
	String style = null;
	if (StringUtils.isNotBlank(width) && StringUtils.isNotBlank(height)) {
		style = String.format("width: %s; height: %s; visibility: hidden;", width, height);
	} else if (StringUtils.isNotBlank(width)) {
		style = String.format("width: %s; height: %s; visibility: hidden;", width, "200px");
	} else if (StringUtils.isNotBlank(height)) {
		style = String.format("width: %s; height: %s; visibility: hidden;", "700px", height);
	} else {
		style = String.format("width:700px;height:200px;visibility:hidden;");
	}
	request.setAttribute("style", style);
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[id="${editid}"]', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link','table']
	});
});
</script>
<textarea id="${editid}" name="${name}" style="${style}">${value}</textarea>