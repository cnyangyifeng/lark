<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
    	<ul class="nav nav-list sidenav" id="sideNav">
    	
                <li class="nav-header first"><a href="${ctx}/studyTrack/getStudyTrackDirector">学习跟踪<b class="caret"></b></a></li>
                
                
	            <c:if test="${param.fdType=='13'}">
	            <li class="nav-header active">
	            </c:if>
	            <c:if test="${param.fdType!='13'}">
	            <li class="nav-header">
	            </c:if>
	            <a href="${ctx}/course/getCourseAuthInfos?fdType=13&order=fdcreatetime">授权学习<b class="caret"></b></a>
	            </li> 
                
	            <li class="nav-header">
                    <span>课程管理<b class="caret"></b></span>
	            </li>
	            
	            <tags:shirourl text="系列课程" url="${ctx}/series/findSeriesInfos?fdType=11&order=fdcreatetime" active="11"  iconName="icon-course-series"  para="${param.fdType}"></tags:shirourl>
	            
	            <tags:shirourl text="课程" url="${ctx}/course/findcourseInfos?fdType=12&order=fdcreatetime" active="12"  iconName="icon-course"  para="${param.fdType}"></tags:shirourl>
	           
	             <li class="nav-header">
                     <span>课程素材库<b class="caret"></b></span>
	            </li>
	            
	            <tags:shirourl text="视频" url="${ctx}/material/findList?fdType=01&order=FDCREATETIME" active="01"  iconName="icon-video"  para="${param.fdType}"></tags:shirourl>
	           
               <tags:shirourl text="文档" url="${ctx}/material/findList?fdType=04&order=FDCREATETIME" active="04"  iconName="icon-doc"  para="${param.fdType}"></tags:shirourl>
	           
	           <tags:shirourl text="幻灯片" url="${ctx}/material/findList?fdType=05&order=FDCREATETIME" active="05"  iconName="icon-ppt"  para="${param.fdType}"></tags:shirourl>
	           
	           <tags:shirourl text="测试" url="${ctx}/material/findList?fdType=08&order=FDCREATETIME" active="08"  iconName="icon-exam"  para="${param.fdType}"></tags:shirourl>
	           
               <tags:shirourl text="作业" url="${ctx}/material/findList?fdType=10&order=FDCREATETIME" active="10"  iconName="icon-task"  para="${param.fdType}"></tags:shirourl>
	           
	           <tags:shirourl text="在线创作" url="${ctx}/material/findList?fdType=07&order=FDCREATETIME" active="07"  iconName="icon-txt"  para="${param.fdType}"></tags:shirourl>
          
	    </ul>

