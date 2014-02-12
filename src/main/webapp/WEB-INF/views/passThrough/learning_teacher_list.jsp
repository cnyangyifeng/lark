<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/resources/js/doT.min.js"></script>
<script id="leaningTeacherPage" type="text/x-dot-template">
	<ul class="thumbnails" >
	{{~it.list :item:index}}
       <li>
		  <a href="${ctx}/passThrough/getCourseFeeling?courseId=${param.courseId}&userId={{=item.id}}" class="thumbnail">
		    <img src="{{?item.photoUrl.indexOf('http')>-1}}{{=item.photoUrl}}{{??}}${ctx}/{{=item.photoUrl}}{{?}}" />
          </a>
          <h6>{{=item.name}}</h6>
       </li>
	{{~}}
	</ul>
	<div class="page-group clearfix">
	   <a href="javascript:void(0)" class="btn-prev" {{?it.currentPage <= 1}} disabled {{?}} onclick='pageNavClick({{=it.currentPage-1}})'>上一页</a>
	   <a href="javascript:void(0)" class="btn-next" {{?it.currentPage == it.totalPage}} disabled {{?}} onclick='pageNavClick({{=it.currentPage+1}})'>下一页</a>
	</div>
</script>
<div class="section mt20 list-face">
    <div class="hd">
    	<h5><span id='leaningCount'>0</span>&nbsp;位老师在学习</h5>
    </div>
    <div class="bd" id="leaningTeachers">
        
    </div>
</div>
<script type="text/javascript">	
var leaningTeacherPageFn = doT.template(document.getElementById("leaningTeacherPage").text);
$.ajax({
	type: "post",
	url: "${ctx}/ajax/passThrough/getPageByLeaningTeacher",
	data : {
		"courseId" : '${param.courseId}',
		"pageNo" : 1
	},
	cache: false, 
	dataType: "json",
	success:function(data){	
		$("#leaningCount").html(data.totalCount);
		$("#leaningTeachers").html(leaningTeacherPageFn(data));
	}
});  
function pageNavClick(pageNo){
	$.ajax({
		type: "post",
		url: "${ctx}/ajax/passThrough/getPageByLeaningTeacher",
		data : {
			"courseId" : '${param.courseId}',
			"pageNo" : pageNo
		},
		cache: false, 
		dataType: "json",
		success:function(data){	
			$("#leaningCount").html(data.totalCount);
			$("#leaningTeachers").html(leaningTeacherPageFn(data));
		}
	}); 
}
</script>