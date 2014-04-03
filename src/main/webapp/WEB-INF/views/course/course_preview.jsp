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
<link href="${ctx}/resources${course.fdSkin.fdSkinPath}/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources${course.fdSkin.fdSkinPath}/css/home_zht.min.css" rel="stylesheet" type="text/css">
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
        	
            <a href="#" class="pull-left">
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
        		<p>${course.fdSubTitle}</p>
              	<div class="rating-view" id="courseScore">
                		<span class="rating-all">
                		  <c:forEach var="i" begin="1" end="5">
                		  	<c:if test="${courseScore >= i}">
                		  	<i class="icon-star active"></i>
                		  	</c:if>
                		  	<c:if test="${courseScore < i}">
                		  	<i class="icon-star"></i>
                		  	</c:if>
                		  </c:forEach>
                         </span>
                         <b class="text-warning">${courseScore}</b>
                 </div>
                
        	</div>
            </div>
        </div>
		<div class="clearfix mt20">
	        <div class="pull-left w760">
	         <c:if test="${(course.fdSummary!=null && course.fdSummary!='')||(course.fdLearnAim!=null && course.fdLearnAim!='')||(course.fdProposalsGroup!=null && course.fdProposalsGroup!='')||(course.fdDemand!=null&& course.fdDemand!='') }">
	        	<div class="section">
	       	  		<div class="hd">
		        		<h2>作者  
		        		<tags:title size="35" value="${course.fdAuthor}"></tags:title>
		        		</h2>
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
                    	<c:set var="i" value="1"/>
                    		<j:iter items="${catalog}" var="bean" status="vstatus">
                    		<c:if test="${bean.fdType==0}">
                    		<dl>
                    			<dt>
                                	<span class="icon-disc-lg-bg disc-bd">${bean.fdNo}</span>
                                    <b class="caret"></b>
                                    <div class="span5"><span class="tit">${bean.fdName}</span></div>
                                    <div class="span2">建议时长</div>
                                </dt>
                                <j:iter items="${catalog}" var="lecture" status="stauts">
                                	<c:if test="${lecture.fdType==1 && lecture.hbmParent!=null && lecture.hbmParent.fdId==bean.fdId}">
		                    			<dd>
		                                	<div class="span5">
		                                    	<%-- <span class="tit">${bean.fdNo}.${i}&nbsp;${lecture.fdName}</span> --%>
		                                    	<span class="tit"><i class="icon-${lecture.materialType}"></i>&nbsp;${i}&nbsp;${lecture.fdName}</span>
		                                    </div>
		                                    <div class="span2">
		                                    <tags:title size="4" value="${lecture.fdStudyTime}"></tags:title>
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
                                    </div>
                                    <div class="span2">${bean.fdStudyTime}</div>
                                </dd>
                             </dl>
                            </c:if>
                    		</j:iter>
                    	</div>
                    </div>
                </div>               
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
                        ${course.fdAuthorDescription}
                    </div>
                </div>
	        </div>
        </div>
</section>

</body>
</html>
