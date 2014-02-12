<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<script id="personTemplate" type="x-dot-template">
                    <div class="profile">
                        <a href="${ctx}/course/courseIndex?userId={{=it.userId}}">
				<img src="{{?it.url.indexOf('http')>-1}}{{=it.url}}{{??}}${ctx}/{{=it.url}}{{?}}" class="face" alt="头像" /></a>
                        <h5>{{=it.name}} 
						{{?it.sex=='M'}}
							<i class="icon-male"></i></h5>
						{{??}}
							<i class="icon-female"></i></h5>
						{{?}}
                        <p class="muted">
                            {{=it.dep}} <br/>
                            {{=it.lastTime}}<br/>
                            在线统计    {{=it.onlineDay}} 天
                        </p>
                    </div>
</script>
<div class="section" id="personFace">
    <div class="profile">
        <a href="#"><img src="./images/default-face-180.png" class="face" alt="头像"/></a>
        <h5>杨义锋 <i class="icon-male"></i></h5> <!-- 女人用.icon-female -->
        <p class="muted">
            集团总公司 知识管理中心 <br/>
            最近登录    3 天前<br/>
            在线统计    35 天
        </p>
    </div>
</div>
                
<script type="text/javascript">
var personFn = doT.template(document.getElementById("personTemplate").text);
$.ajax({
	url : "${ctx}/ajax/studyTrack/getPerson",
	async : false,
	dataType : 'json',
	type: "post",
	success : function(result) {
		$("#personFace").html(personFn(result));
	}
});
</script>