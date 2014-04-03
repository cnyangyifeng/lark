<%@page import="java.util.Random"%>
<%@page import="com.kuxue.model.course.CourseInfo"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/resources${skinPath}/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources${skinPath}/css/home_zht.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources${skinPath}/css/comment.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
</head>
<body>
<!--头部 E-->

<!--主体 S-->
<section class="container">	
		<div class="section mt20">
        	<div class="media box-pd20 media-main">
        	 <!--
		                根据类型选择性 为div.permission增加class
		                默认： 公开
		                授权： + authorize
		                加密： + encrypt 
		    -->
		    <c:if test="${course.isPublish==true}">
		      <div class="permission"></div>
		    </c:if>
		    <c:if test="${course.isPublish==false}">
		      <c:if test="${course.fdPassword!=''&&course.fdPassword!=null}">
                <div class="permission encrypt"></div>
              </c:if>
              <c:if test="${course.fdPassword==''||course.fdPassword==null}">
                <div class="permission authorize"></div>
              </c:if>
            </c:if>
        	
            <a  class="pull-left">
            <c:if test="${courseAtt==''}">
            	<img src="${ctx}/resources/images/default-cover.png" alt="" class="media-object">
            </c:if>
            <c:if test="${courseAtt!=null &&  courseAtt!=''}">
            	<img src="${ctx}/common/file/image/${courseAtt}?n=<%=new Random().nextInt(100) %>" alt="" class="media-object">
            </c:if>
            </a>
       	    <div class="media-body">
       	    
        		<div class="media-heading">
        		  <h2><tags:title size="20" value="${course.fdTitle}"></tags:title></h2>
        		</div>
        		<p><tags:title size="32" value="${course.fdSubTitle}"></tags:title></p>
              	<div class="rating-view" id="courseScore">
                		<span class="rating-all">
                		<c:if test="${courseScore!=null}">
                		  <c:forEach var="i" begin="1" end="5">
                		  	<c:if test="${courseScore >= i}">
                		  	<i class="icon-star active"></i>
                		  	</c:if>
                		  	<c:if test="${courseScore < i}">
                		  	<i class="icon-star"></i>
                		  	</c:if>
                		  </c:forEach>
                		</c:if>
                		<c:if test="${courseScore==null}">
                		 <c:forEach var="i" begin="1" end="5">
                		  	<i class="icon-star"></i>
                		  </c:forEach>
                		</c:if>
                         </span>
                         <b class="text-warning">${courseScore}</b>
                 </div>
              <c:if test="${course.isPublish==false}">
		        <c:if test="${course.fdPassword!=''&&course.fdPassword!=null}">
                 <!--加密类型时 start-->
                 <div id="formPassword" action="#" >
                     <input onkeydown="return pushS();" type="password" placeholder="输入授权密码" class="password" name="password" id="password" />
                     <button class="btn btn-link" type="button" id="verifyPwd">点击解锁</button>
                 </div>
                </c:if>
               </c:if>
                <!--加密类型时 end-->
                <div class="media-foot">
                <form id="studyBeginForm" class="hide" method="post" action="${ctx}/passThrough/getStudyContent"> 
                <input type="hidden" name="courseId" value="${course.fdId}">
                <input type="hidden" name="fdPassword" id="fdPassword">
                <input type="hidden" name="catalogId" id="catalogId">
                <input type="hidden" name="fdMtype" id="fdMtype">
                </form>
                <a id="studyBegin" class="btn btn-warning">
                   <i class="icon-book icon-white"></i>
      <c:if test="${isLearning==true}">
	      <c:if test="${isThrough==true}">
	      再次学习
	      </c:if>
	      <c:if test="${isThrough==false}">
	      	继续学习
	      </c:if>
      </c:if>
      <c:if test="${isLearning==false}">
      	 开始学习
      </c:if>              
                </a>
                    <span class="text-warning">${studayTotalNo}</span>&nbsp;位老师在学习
                    <a href="${ctx}/course/courseIndex" title="课程列表" data-toggle="tooltip" class="btn-next icon-disc-lg-bg"><i class="icon-home icon-white"></i></a>
                    <a href="${ctx}/passThrough/getCourseFeeling?courseId=${course.fdId}" title="课程跟踪" data-toggle="tooltip" class="btn-home icon-disc-lg-bg"><i class="icon-tracking"></i></a>
                </div>
        	</div>
            </div>
        </div>
		<div class="clearfix mt20">
	        <div class="pull-left w760">
	        <c:if test="${(course.fdSummary!=null && course.fdSummary!='')||(course.fdLearnAim!=null && course.fdLearnAim!='')||(course.fdProposalsGroup!=null && course.fdProposalsGroup!='')||(course.fdDemand!=null&& course.fdDemand!='') }">
	        	<div class="section">
	       	  		<div class="hd">
		        		<h2>作者  <tags:title size="35" value="${course.fdAuthor}"></tags:title></h2>
                        <div class="ab_r">
                        	<span class="pub_time"><i class="icon-time"></i><tags:datetime value="${course.fdCreateTime}" pattern="yyyy-MM-dd hh:mm aa"/></span>
                        </div>
		        	</div>
                    <div class="bd">
                    	<div class="box-txt">
                            <dl>
                            	<j:if test="${course.fdSummary!=null && course.fdSummary!=''}">
                                	<dt>课程摘要</dt>
                                	<dd>${course.fdSummary}</dd>
                                </j:if>
                                <j:if test="${course.fdLearnAim!=null && course.fdLearnAim!=''}">
                                	<dt>学习目标</dt>
                                	<dd>
										<ol><tags:stringli value="${course.fdLearnAim}" sign="#"/></ol>
                                	</dd>
                                </j:if>
                                <j:if test="${course.fdProposalsGroup!=null && course.fdProposalsGroup!=''}">
	                                <dt>建议群体</dt>
	                                <dd>
										<ol><tags:stringli value="${course.fdProposalsGroup}" sign="#"/></ol>
	                                </dd>
                                </j:if>
                                <j:if test="${course.fdDemand!=null && course.fdDemand!=''}">
	                                <dt>课程要求</dt>
	                                <dd class="last">
										<ol><tags:stringli value="${course.fdDemand}" sign="#"/></ol>
	                                </dd>
                                </j:if>
                            </dl>
                        </div>
                    </div>
		        </div>
		        </c:if>
                <div class="section mt20">
                 <j:ifelse test="${(course.fdSummary!=null && course.fdSummary!='')||(course.fdLearnAim!=null && course.fdLearnAim!='')||(course.fdProposalsGroup!=null && course.fdProposalsGroup!='')||(course.fdDemand!=null&& course.fdDemand!='')}">
                <j:then>
                <div class="box-pd20">
                </j:then>
                <j:else>
                 <div class="box-pd20" style="margin-top:-20px;">
                </j:else>
                </j:ifelse>

                    	<div class="larning-sections">
                    		<c:set var="isorder" value="true"/>
                    		<c:set var="i" value="1"/>
                    		<j:iter items="${catalog}" var="bean" status="vstatus">
                    		<c:if test="${bean.fdType==0}">
                    		<dl>
                    			<dt>
                                	<span class="icon-disc-lg-bg disc-bd">${bean.fdNo}</span>
                                    <b class="caret"></b>
                                    <div class="span5"><span class="tit">${bean.fdName}</span></div>
                                    <div class="span2">建议时长</div>
                                    <div class="span1">学习进度</div>
                                </dt>
                                <j:iter items="${catalog}" var="lecture" status="stauts">
                                	<c:if test="${lecture.fdType==1 && lecture.hbmParent!=null && lecture.hbmParent.fdId==bean.fdId}">
		                    			<dd>
		                                	<div class="span5">
		                                    	<span class="tit"><i class="icon-${lecture.materialType}"></i>&nbsp;第 ${i} 节&nbsp;${lecture.fdName}</span>
		                                        	<c:if test="${lecture.through==null}">
		                                        	  <c:if test="${course.isOrder==false}">
		                                        	  	<button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        	  	  	开始学习
		                                        	    </button>
		                                        	  </c:if>
		                                        	  <c:if test="${isorder==false && course.isOrder==true}">
		                                        	  	<button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn" data-show="false">
		                                        	  	  	开始学习
		                                        	    </button>
		                                        	  </c:if>
		                                        	  <c:if test="${isorder==true && course.isOrder==true}">
		                                        	    <c:set var="isorder" value="false"/>
		                                        	  	<button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        	  	  	开始学习
		                                        	    </button>
		                                        	  </c:if>
		                                        	</c:if>
		                                        	<c:if test="${lecture.through==true}">
		                                        	  <button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        		再次学习
		                                        	  </button>		                                        		
		                                        	</c:if>
		                                        	<c:if test="${lecture.through==false}">
		                                        	  <c:if test="${course.isOrder==true}">
		                                        	    <c:set var="isorder" value="false"/>
		                                        	  </c:if>
		                                        	  <button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        		继续学习
		                                        	  </button>		
		                                        	</c:if>
		                                    </div>
		                                    <div class="span2"><tags:title size="4" value="${lecture.fdStudyTime}"></tags:title></div>
		                                    <div class="span1">
		                                    	<i class="icon-circle-progress">
		                                        	<c:if test="${lecture.through==true}">
		                                        		<i class="icon-progress"></i>
		                                        	</c:if>
		                                        	<c:if test="${lecture.through==false}">
		                                        		<i class="icon-progress half"></i>
		                                        	</c:if>
		                                    	</i>
		                                    </div>
		                                </dd>
		                                <c:set var="i" value="${i+1}"/>
		                            </c:if>
                                </j:iter>
                                </dl>
                            </c:if>
                            <c:if test="${bean.fdType==1 && bean.hbmParent==null}">
                             <dl>
                    			<dd>
                                	<div class="span5">
                                    	<span class="tit"><i class="icon-${bean.materialType}"></i>&nbsp;${bean.fdNo}&nbsp;${bean.fdName}</span>
                                        	<c:if test="${bean.through==null}">
		                                      <c:if test="${course.isOrder==false}">
		                                       	<button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        	开始学习
		                                       	</button>
		                                      </c:if>
		                                      <c:if test="${isorder==false && course.isOrder==true}">
		                                        <button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn" data-show="false">
		                                        	开始学习
		                                        </button>
		                                      </c:if>
		                                      <c:if test="${isorder==true && course.isOrder==true}">
		                                        <c:set var="isorder" value="false"/>
		                                        <button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        	开始学习
		                                        </button>
		                                      </c:if>
		                                    </c:if>
		                                    <c:if test="${bean.through==true}">
		                                        <button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        	再次学习
		                                        </button>		                                        		
		                                    </c:if>
		                                    <c:if test="${bean.through==false}">
		                                    	<c:if test="${course.isOrder==true}">
		                                        	<c:set var="isorder" value="false"/>
		                                        </c:if>
		                                        <button name="doButton" data-fdid="${lecture.fdId}" data-fdMtype="${lecture.fdMaterialType}" class="btn  btn-primary" data-show="true">
		                                        	继续学习
		                                        </button>		
		                                    </c:if>
                                    </div>
                                    <div class="span2">${bean.fdStudyTime}</div>
                                    <div class="span1">
                                    	<i class="icon-circle-progress">
                                    		<c:if test="${bean.through==true}">
		                                        <i class="icon-progress"></i>
		                                    </c:if>
		                                    <c:if test="${bean.through==false}">
		                                        <i class="icon-progress half"></i>
		                                    </c:if>
                                    	</i>
                                    </div>
                                </dd>
                             </dl>
                            </c:if>
                    		</j:iter>
                    	</div>
                    </div>
                </div>               
               <c:import url="/WEB-INF/views/base/comment/comment.jsp">
                 	<c:param name="modelName" value="<%=CourseInfo.class.getName()%>" />
                 	<c:param name="modelId" value="${course.fdId}" />
                </c:import>
			</div>
			<div class="pull-right w225">
			      <div class="section profile">
                    <div class="hd">
                    </div>
                    <div class="bd">
                        <div class="faceWrap">
                          <tags:image href="${imgUrl}" clas="face img-polaroid"/>
                        </div>
                        <p>作者</p>
                        <p class="muted">
                          	 ${course.fdAuthor}
                        </p>
                    </div>
                    <div class="ft">
                        <span>${course.fdAuthorDescription}</span>
                    </div>
                </div>
				 <!-- 评分评论页面 -->
	        	 <c:import url="/WEB-INF/views/passThrough/score_course.jsp">
                	<c:param name="courseId" value="${course.fdId}" />
                </c:import>
		        <!-- 学习当前课程的教师列表 -->
                <c:import url="/WEB-INF/views/passThrough/learning_teacher_list.jsp">
                	<c:param name="courseId" value="${course.fdId}" />
                </c:import>
                <!-- 发现课程之最新课程列表 -->
                <c:import url="/WEB-INF/views/passThrough/new_course_list.jsp"></c:import>
	        </div>
        </div>
</section>
<!--主体 E-->
<script type="text/javascript">

var courseId = '${course.fdId}';
$(function(){
	$("#goTop").next(".help").addClass("light").before('<a href="${ctx}/passThrough/getCourseFeeling?courseId=${course.fdId}" title="课程跟踪" ><i class="icon-tracking"></i></a>');
});
$.ajax({
	type: "post",
	url: "${ctx}/ajax/passThrough/checkCoursePwd/"+courseId,
	cache :false,
	async: false,
	dataType : 'json',
	success:function(data){
		var flag = data.flag;
		if(flag=="0"){
			$("button[name='doButton']").each(function(){
				$(this).removeClass("btn-primary");
			});
			$("#studyBegin").unbind("click");
		    $("#studyBegin").attr("disabled",true);
		}else{
			$("button[name='doButton']").each(function(){
				if($(this).hasClass("btn-primary")){
					$(this).bind("click",function(){
						var fdid = $(this).attr("data-fdid");
						
						var fdMtype = $(this).attr("data-fdMtype");
						$("#catalogId").val(fdid);
						$("#fdMtype").val(fdMtype);
						$("#studyBeginForm").trigger("submit");
					 });
				}
			});
		} 
	}
}); 

$("#verifyPwd").bind("click",function(){
	var inputPassword = $("#password").val();
	$.ajax({
		type: "post",
		async: false,
		url: "${ctx}/ajax/passThrough/verifyPwd",
		cache :false,
		data:{
			"courseId":courseId,
			"userPwd" :inputPassword
		},
		dataType : 'json',
		success:function(data){
			var flag = data.flag;
			if(flag=="01"){
				$("#verifyPwd").next(".error").remove();
				$("#verifyPwd").after('<span class="error">输入密码错误，请重新输入！</span>');	
			}else if(flag=="00"){
				$("#verifyPwd").next(".error").remove();
				$("#verifyPwd").after('<span class="error">您没有权限学习该课程！</span>');	
			}else{
				$("#fdPassword").val(inputPassword);
				$("#formPassword").children().remove();
		    	$("button[name='doButton']").each(function(){
		    		if($(this).attr("data-show")=="true"){
		    			$(this).addClass("btn-primary");
		    			$(this).bind("click",function(){
							var fdid = $(this).attr("data-fdid");
							
							var fdMtype = $(this).attr("data-fdMtype");
							$("#catalogId").val(fdid);
							$("#fdMtype").val(fdMtype);
							$("#studyBeginForm").trigger("submit");
						});
		    		}
		    	});
		    	$("#studyBegin").attr("disabled",false);
		    	$("#studyBegin").bind("click");
		    	$("#studyBegin").bind("click",function(){
		    		$("#studyBeginForm").trigger("submit");
		    	});	
			}
		}
	}); 
	
});
if($("#studyBegin").attr("disabled")!="disabled"){
	$("#studyBegin").bind("click",function(){
		$("#studyBeginForm").trigger("submit");
	});	
}
function pushS(){
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (keyCode == 13) {
		$("#verifyPwd").trigger("click");
		return false;
	}
}
</script>
</body>
</html>
