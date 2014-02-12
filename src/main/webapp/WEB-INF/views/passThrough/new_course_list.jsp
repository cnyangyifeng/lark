<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Random"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/resources/js/doT.min.js"></script>
<script id="newCourseTemplate" type="text/x-dot-template">
{{~it :item:index}}
	<a href="${ctx}/passThrough/getCourseHome/{{=item.FDID}}">
		{{?item.ATTID == ''}}
			<img src="${ctx}/resources/images/default-cover.png" alt="">
		{{?}}
		{{?item.ATTID != ''}}
			<img src="${ctx}/common/file/image/{{=item.ATTID}}?n=<%=new Random().nextInt(100) %>" alt="">
		{{?}}
        <span class="mask"></span>
        <span class="caption">
     		<h6>{{=cut_str(item.FDTITLE, 10)}}</h6>
            <span class="text-warning">{{=item.FDAUTHOR || ''}}</span>
        </span>
    </a>
{{~}}
</script>
<div class="section newClass mt20">
     <div class="hd">
     	<h5>最新课程</h5>
            <a href="${ctx}/course/courseIndexAll" class="ab_r">全部课程</a>
     </div>
     <div class="bd">
        	<div class="list-class" id="discoverCourses">
            	
            </div>
     </div>
</div>
<script type="text/javascript">	
var newCoursePageFn = doT.template(document.getElementById("newCourseTemplate").text);
	$.ajax({
		type: "post",
		url: "${ctx}/ajax/passThrough/getNewCourseList",
		cache: false, 
		dataType: "json",
		success:function(data){		
			$("#discoverCourses").html(newCoursePageFn(data));
		}
	}); 
</script>