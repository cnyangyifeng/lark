<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
   <!--老师列表模板-->
    <script id="listTeacherTemplate" type="x-dot-template">
            {{~it :item:index}}
            <li class="media" data-fdid="{{=item.id}}">
                <div class="pull-left">
                    <a href="javascript:void(0)" class="face">
                        <img src="{{?item.user.imgUrl.indexOf('http')>-1}}{{=item.user.imgUrl}}{{??}}${ctx}/{{=item.user.imgUrl}}{{?}}" class="media-object img-polaroid" alt="头像"/>
                    </a>
					<a href="${ctx}/course/courseIndex?userId={{=item.user.userId}}" class="send home" ><i class="icon-home"></i>主&nbsp;&nbsp;&nbsp;页</a>
                    <a href="${ctx}/letter/letterDetail?fdId={{=item.user.userId}}" target="_blank" class="send msg" ><i class="icon-msg"></i>发私信</a>
                    <a href="mailto:{{=item.user.mail}}" class="send" ><i class="icon-envelope"></i>发邮件</a>
					{{?item.canDel}}
					<a href="javascript:void(0)" class="send removeA" bamId="{{=item.id}}" userId="{{=item.user.userId}}" ><i class="icon-trash"></i>删&nbsp;&nbsp;&nbsp;除</a>
					{{?}}
                </div>
                <div class="media-body">
                    <div class="media-heading">
                        <span class="name">{{=item.user.name}}</span>
                        <span class="muted">{{=item.user.department||'不详'}}</span>
                                            <span class="muted right">
                                                <i class="icon-tel"></i>{{=item.user.phone || '不详'}}
                                                <i class="icon-envelope"></i>{{=item.user.mail||'不详'}}
                                            </span>
                    </div>
                    <dl>
                        <dt>课程：</dt>
                        <dd>{{=item.courseName}}</dd>
                        <dt>导师：</dt>
                        <dd>{{=item.mentor}}</dd>
                        <dt>当前环节：</dt>
                        <dd>{{=item.currLecture}}</dd>
                    </dl>
                    <div class="box">
                        <div class="hd">
                            <div class="caret-group">
                                <b class="caret0"></b>
                                <b class="caret1"></b>
                            </div>
                        </div>
                        <div class="bd">
                            <p>{{=item.passMsg}}</p>
                            <div class="time"><i class="icon-time"></i>{{=item.passTime}}</div>
                        </div>
                    </div>
                </div>
            </li>
            {{~}}
    </script>
    <script id="pageTopTemplate" type="x-dot-template">
        <div class="span2">
           	    第<span> {{=it.startLine}} - {{=it.endLine}}</span> / <span>{{=it.totalSize}}</span> 条
        </div>
        <div class="btn-group">
              <button id="gotoBefore1" class="btn btn-primary btn-ctrl" type="button"><i class="icon-chevron-left icon-white"></i></button>
              <button id="gotoNext1"   class="btn btn-primary btn-ctrl" type="button"><i class="icon-chevron-right icon-white"></i></button>
        </div>            
    </script>

<script id="pageBottomTemplate" type="x-dot-template">
	<div class="btn-group dropup">
            <button id="gotoBefore2" class="btn btn-primary btn-ctrl" type="button" disabled><i class="icon-chevron-left icon-white"></i></button>
            {{ for(var i=it.startPage; i <=it.endPage ; i++){ }}
				{{?it.pageNo == i}}
					<button class="btn btn-primary btn-num active" onclick="refreshTrackList('{{=it.type}}',{{=i}},{{=it.pageSize}},'{{=it.order}}')" type="button">{{=i}}</button>
				{{??}}
					<button class="btn btn-primary btn-num" onclick="refreshTrackList('{{=it.type}}',{{=i}},{{=it.pageSize}},'{{=it.order}}')" type="button">{{=i}}</button>
				{{?}}
				
			{{ } }}
            
            <button class="btn btn-primary btn-num  dropdown-toggle"  data-toggle="dropdown" type="button">
                <span class="caret"></span></button>
            <ul class="dropdown-menu pull-right">
				{{ for(var i=1; i <=it.totalPage ; i++){ }}
					<li><a href="javascript:void(0)" onclick="refreshTrackList('{{=it.type}}',{{=i}},{{=it.pageSize}},'{{=it.order}}')">{{=(i-1)*it.pageSize+1}} - {{=(i-1)*it.pageSize+it.pageSize}}</a></li>
				{{ } }}

            </ul>
            <button id="gotoNext2" class="btn btn-primary btn-ctrl" type="button"><i class="icon-chevron-right icon-white"></i></button>
        </div>
	</script>