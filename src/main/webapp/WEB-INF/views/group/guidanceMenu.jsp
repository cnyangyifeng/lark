<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
    	<ul class="nav nav-list sidenav" id="sideNav">
    			<c:if test="${param.fdType=='01'}">
                <li class="nav-header first active">
                </c:if>
                <c:if test="${param.fdType!='01'}">
	            <li class="nav-header first">
	            </c:if>
                <a href="${ctx}/adviser/checkTask?fdType=01&order=fdcreatetime">批改作业<b class="caret"></b></a></li>
                
                
	            <c:if test="${param.fdType=='02'}">
	            <li class="nav-header active">
	            </c:if>
	            <c:if test="${param.fdType!='02'}">
	            <li class="nav-header">
	            </c:if>
	            <a href="${ctx}/studyTrack/getStudyTrackTutor?fdType=02">学习跟踪<b class="caret"></b></a>
	            </li> 
                
	            
	    </ul>

