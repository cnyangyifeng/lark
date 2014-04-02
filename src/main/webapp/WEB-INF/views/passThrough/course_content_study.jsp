<%@page import="cn.me.xdf.model.material.MaterialInfo"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/theme/default/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/theme/default/css/layout.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/theme/default/css/webimOfSide.min.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
<!-- 侧边栏 头像列表模块  -->
    <script id="sideListFaceTemplate" type="x-dot-template">
        <!-- 侧边栏 头像列表模块 S -->
        <div class="side_listFace_md" id="sideListFace">
            <div class="side_listFace_wrap">
                <ul class="side_listFace">
                    {{~it.listLearner :item}}
                    <li {{?item.isMe}}class="disabled"{{?}} ><a href="#" data-fdId="{{=item.id}}" title="{{=item.name}}">
                        <img src="{{?item.imgUrl.indexOf('http')>-1}}{{=item.imgUrl}}{{??}}${ctx}/{{=item.imgUrl}}{{?}}" alt="{{=item.name}}"/>
                        {{?item.isShow}}<i class="icon-disc"></i>{{?}}
                     </a></li>
                    {{~}}
                </ul>
                <a href="#" class="prev hide"><b class="caret"></b></a>
                <a href="#" class="next hide"><b class="caret"></b></a>
            </div>
            <div class="side_support">
                <h5>支持团队</h5>
                <ul class="side_support_list">
                    <li {{?it.mentor.isMe}} class="disabled"{{?}}>
                        <a href="#" data-fdId="{{=it.mentor.id}}" title="{{?!it.mentor.name}}无导师{{??}}{{=it.mentor.name}}{{?}}">
                            <i class="icon-face-mentor"></i>
                            {{?it.mentor.isShow}}<i class="icon-disc"></i>{{?}}
                        </a>
                        <h6>导师</h6>
                    </li>
                    <li {{?it.author.isMe}} class="disabled"{{?}}>
                        <a href="#" data-fdId="{{=it.author.id}}" title="{{=it.author.name}}">
                            <i class="icon-face-author"></i>
                           {{?it.author.isShow}}<i class="icon-disc"></i>{{?}}
                        </a>
                        <h6>课程作者</h6>
                    </li>
                    <li {{?it.admin.isMe}} class="disabled"{{?}}>
                        <a href="#" data-fdId="{{=it.admin.id}}" title="{{=it.admin.name}}">
                            <i class="icon-face-admin"></i>
                           {{?it.admin.isShow}}<i class="icon-disc"></i>{{?}}
                        </a>
                        <h6>平台运营</h6>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 侧边栏 头像列表模块 D -->
    </script>

    <!-- 侧边栏 聊天窗口模板  -->
    <script id="chatDialogTemplate" type="x-dot-template">
        <!--聊天窗口 start-->
        <div id="side_webim" class="side_webim fade in" data-toggle="alert">
            <a class="close" href="#" data-dismiss="alert">&times;</a>
            <div class="side_webim_hd">
                <img src="{{?it.user.imgUrl.indexOf('http')>-1}}{{=it.user.imgUrl}}{{??}}${ctx}/{{=it.user.imgUrl}}{{?}}" alt="" class="img-polaroid face" />
                <span class="name">{{=it.user.name}}</span>
                <span class="org" title="{{=it.user.org}} {{=it.user.department}}">{{=it.user.org.length>8?it.user.org.substr(0,8)+'...':it.user.org}} {{=it.user.department.length>8?it.user.department.substr(0,8)+'...':it.user.department}}</span>
            </div>
            <div class="side_webim_bd">
                <div class="side_webim_row">
                    <span>TA的电话</span>
                    <span  class="tel">
                        <i class="icon-tel"></i>
                        {{=it.user.tel}}
                    </span>
                </div>
                <div class="side_webim_chatLog">
                    <div class="webim_chat_list" id="listChatLog">
                    </div>
                </div>
                <a class="webim_chatLog_link" href="${ctx}/letter/letterDetail?fdId={{=it.user.id}}" target="_blank">聊天记录</a>
            </div>
            <div class="side_webim_ft">
                <form action="#" id="formWebim" class="formWebim">
                    <input type="hidden" id="userId" name="userId" value="{{=it.user.id}}"/>
                    <textarea name="webim_input" id="webim_input" class="webim_input"  placeholder="200字" required maxlength="200" rows="2"></textarea>
                    <div class="formWebimActs">
                        <i class="icon-envelope"></i>
                        <a href="mailto:{{=it.user.mail}}" class="mailTo">给TA发邮件</a>
                        <button class="btn btn-primary" id="submitLetter" type="button">发送</button>
                    </div>
                </form>
            </div>
        </div>
        <!--聊天窗口 end-->
    </script>

    <!-- 聊天纪录列表 模板 -->
    <script id="listChatLogTemplate" type="text/x-dot-template">
        {{~it :item}}
        <div class="webim_chat_item">
            <div class="webim_chat_item_hd">
                <span class="webim_date">{{=item.date}}</span>
            </div>
            <div class="webim_chat_item_bd">
                {{~item.list :dia}}
                <div class="webim_dia_box {{?dia.isMe}}webim_dia_r{{??}}webim_dia_l{{?}}">
                    <div class="webim_date">{{=dia.time}}</div>
                    <div class="webim_dia_bg">
                        <div class="dia_caret"><b></b></div>
                        <div class="webim_dia_cont">
                            {{=dia.msg}}
                        </div>
                    </div>
                </div>
                {{~}}
            </div>
        </div>
        {{~}}
    </script>

    <!--页面左侧模板-->
    <script id="pageLeftTemplate" type="text/x-dot-template">
            <div id="sidebar" class="sidebar" >
                {{#def.sidenav:it.sidenav}}
                <div class="bdbt2 leftBox mt20" id="cordPage" >
                    <a href="javascript:void(0)" id="courseOverBtn"><i class="icon-disc-lg-bg"><i class="icon-medal"></i></i>
                        结业证书</a>
                </div>
            </div>
            {{##def.sidenav:param:
            <ul class="bdbt2 sidenav" id="sidenav">
                {{ for(var i=0; i < param.chapter.length+param.lecture.length; i++){ }}
                    {{~param.chapter :chapter:index}}
                        {{?chapter.index == i}}
                            <li class="nav-hd" data-toggle="popover" data-content="{{=chapter.name || ''}}" title="{{=chapter.name || ''}}">
                                <span class="dt">章<b class="icon-circle-white-large">{{=chapter.num}}</b></span>
                                <span class="name">
								{{=cut_str(chapter.name, 7)}}
                                </span>
                            </li>
                        {{?}}
                    {{~}}
                    {{~param.lecture :lecture:index}}
                        {{?lecture.index == i}}
                           
                            <li{{?lecture.id == param.currentId}} class="active"{{?}} {{?lecture.fdStatus!='true'}} class="disabled" {{?}}>
                                <a {{?lecture.fdStatus=='true'}}href="#" {{?}}data-fdid="{{=lecture.id}}" data-type="{{=lecture.baseType}}" data-toggle="popover" data-content="{{=lecture.intro || ''}}" title="{{=lecture.name || ''}}">
                                    <span class="dt">节{{=lecture.num}} <b class="icon-circle-progress">
                                        {{?lecture.status != 'untreated'}}<i class="icon-progress{{?lecture.status == 'doing'}} half{{?}}"></i>{{?}}
                                    </b></span>
                                    <span class="name"><i class="icon-{{=lecture.type}}"></i>
										{{=cut_str(lecture.name, 6)}}
                                 </span>
                                </a>
                            </li>
                        {{?}}
                    {{~}}
                {{ } }}
            </ul>
            #}}
    </script>

    <!--页面右侧模板-->
    <script id="pageRightTemplate" type="text/x-dot-template">
            {{#def.pageHeader}}
            <div class="page-body" id="pageBody">
                {{#def.pageIntro}}
                {{?it.type == 'exam' || it.type == 'task'}}
                    {{#def.examContent}}
                {{?}}
            </div>
    </script>



    <!--页面主内容区header-->
    <script id="pageRightHeaderTemplate" type="x-dot-template">
        <div class="page-header" id="pageHeader" data-offset-top="10">
                <div class="hd clearfix">
             <a class="btn {{?!it.pstatus}} disabled{{?}}" {{?it.pstatus}}href="#"{{?}} id="prevLecture" data-fdid="{{=it.prevc}}" data-type="{{=it.prevBaseType || ''}}">
                <i class="icon-chevron-lg-left"></i>
                <span>上一节</span>
                </a>
                <h1>
                        《<a class="tit" href="${ctx}/passThrough/getCourseHome/${courseId}">
						{{=cut_str(it.courseName, 14)}}
						</a>》
                                                           节{{=it.num}} 
						{{=cut_str(it.lectureName, 14)}}						
                <button class="btn{{?it.isOptional || it.status == "pass"}} btn-success{{?}}{{? it.status == "pass" || !it.isOptional}} disabled{{?}}"{{?it.isOptional}} id="btnOptionalLecture"{{?}}>
               <i class="icon-right"></i>
                  <span class="tit">
						{{?it.isOptional}}跳过此节{{??}}学习通过{{?}}
                 </span>
               </button>
                </h1>
				{{?it.isLast}}
				<a class="btn btn-back {{?!it.isThrough}} disabled{{?}}" id="nextOver" > <i class="icon-disc-lg-bg"><i class="icon-medal"></i></i> </a>
				{{??}}
				 <a class="btn {{?!it.nstatus}} disabled{{?}}" {{?it.nstatus}}href="#"{{?}}  id="nextLecture" data-fdid="{{=it.nextc}}" data-type="{{=it.nextBaseType || ''}}">
                        <i class="icon-chevron-lg-right"></i>
                        <span>下一节</span>
                </a>
				{{?}}
                </div>
                <div class="bd" id="headToolsBar">

                </div>
        </div>
    </script>

    <!--页面主内容区简介-->
    <script id="pageRightIntroTemplate" type="x-dot-template">
        <ul class="listIntro bdbt2">
            <li>
                <div class="line">
                    <div class="line">
                        <span class="label-intro" >学习任务</span>
                        <small>学习完成本节以后,请点击右上方的 “下一节” 按钮，继续学习。 </small></div>
                </div>
            </li>
            <li>
                <div class="line">
                    <div class="line">{{=it.lectureIntro || ''}}</div>
                </div>
            </li>
            <li>
                <div class="line">
                    <div class="line">
                        本{{?it.type=='exam'}}测试{{??it.type=='video'}}视频{{??it.type=='txt'}}在线创作{{??it.type=='task'}}作业包{{??it.type=='doc'}}文档{{??it.type=='ppt'}}幻灯片{{?}}环节为
                        <b>{{?it.isOptional}}选{{??}}必{{?}}修</b> 环节，
                        {{?it.isOptional}}
您可以点击右上角的“跳过此节”按钮，直接进入下一节的继续学习。
                        {{??}}
                                                               您必须学习完成{{?it.type=='exam'}}试卷{{??it.type=='video'}}视频{{??it.type=='txt'}}在线创作{{??it.type=='task'}}作业包{{??it.type=='doc'}}文档{{??it.type=='ppt'}}幻灯片{{?}}，
                                                                   才可以进入下一节继续学习。
                        {{?}}
                     </div>
                </div>
            </li>
			{{?it.type!='txt'&&it.type!='exam'&&it.type!='task'}}
			<li>
				<div class="line">
 				<div  class="line">{{?it.type == 'video'}}视频{{??it.type == 'doc'}}文档{{??it.type == 'ppt'}}幻灯片{{?}}名称：<b class="videoName">{{=it.defaultMedia.name}}</b>&nbsp;&nbsp;<span id="fpassStauts" style="font-weight:bold;color:rgb(48, 124, 72);">{{?it.defaultMedia.isPass}}[已学会]{{?}}</span></div>

				</div>
			</li>
			{{?}}
        </ul>
    </script>

    <!--页面主内容区测试类内容--><!-- {{?it.type == 'task' && paper.examPaperStatus == 'finish'}}{{??}}data-toggle="collapse" data-parent="#listExamPaper" {{?}}  -->
    <script id="pageRightExamTemplate" type="x-dot-template">
        <ul class="unstyled listExamPaper" id="listExamPaper">
            {{~it.listExamPaper :paper:index}}
                <li class="accordion-group"> 
                    <a class="titBar" title="点击展开试题列表" examPaperStatus="{{=paper.examPaperStatus}}" data-toggle="collapse" data-parent="#listExamPaper" href="#examPaper{{=index+1}}">
                        <h2>{{?it.type == 'exam'}}试卷{{??it.type == 'task'}}作业包{{?}}{{=index+1}}. {{=paper.name}}</h2>
                        <p class="muted">共计 <b>{{=paper.examCount}}</b>{{?it.type=='exam'}} 题{{??it.type=='task'}} 个作业{{?}}，满分 <b>{{=paper.fullScore}}</b> 分，{{?paper.examPaperTime!=null}}建议{{?it.type=='exam'}}答题{{??it.type=='task'}}完成{{?}}时间为 <b>{{=paper.examPaperTime}}</b> {{?it.type=='exam'}}分钟{{??it.type=='task'}}天{{?}}。{{?}}</p>
                        <span class="icon-state-bg{{?paper.examPaperStatus == 'fail'}} error">未通过
                        {{??paper.examPaperStatus == 'pass'}} success">通过
                        {{??paper.examPaperStatus == 'finish'}} info">答完
                        {{??paper.examPaperStatus == 'unfinish'}}">待答{{?}}</span>
						<i class="icon-pencil"></i>
                    </a>
                    <div id="examPaper{{=index+1}}" examPaperStatus="{{=paper.examPaperStatus}}" data-fdid="{{=paper.id}}" class="accordion-body collapse">

                    </div>
                </li>
            {{~}}
        </ul>
    </script>

    <!--试卷详情模板-->
    <script id="examPaperDetailTemplate" type="x-dot-template">
        <div class="accordion-inner">
                <div class="hd">
                <h2>
		<span class="icon-state-bg
						{{?it.examPaperStatus == 'fail'}} error">未通过
                        {{??it.examPaperStatus == 'pass'}} success">通过
                        {{??it.examPaperStatus == 'finish'}} info">答完
                        {{??it.examPaperStatus == 'unfinish'}}">待答{{?}}</span> {{?it.type=='exam'}}试卷{{??it.type=='task'}}作业包{{?}}{{=it.num}} <span class="examName">{{=it.name}}</span> 共计 <b><span class="total">{{=it.examCount}}</span></b>{{?it.type=='exam'}} 题{{??it.type=='task'}} 个作业{{?}}，满分 <b>{{=it.fullScore}}</b> 分，
		<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;建议{{?it.type=='exam'}}答题{{??it.type=='task'}}完成{{?}}时间为 <b>{{=it.examPaperTime}}</b>{{?it.type=='exam'}} 分钟{{??it.type=='task'}}天{{?}}。</h2>
        <p class="muted">{{=it.examPaperIntro||''}}</p>
        <a class="btn btn-link" data-fdType="{{=it.type}}" data-toggle="collapse" data-parent="#listExamPaper" href="#examPaper{{=it.num}}">收起{{?it.type == 'exam'}}试题{{??it.type == 'task'}}作业包{{?}}<b class="caret"></b></a>
        </div>
<div id="navExams">
                {{~it.listExam :exam1:index1}}
                {{~it.listExam :exam:index}}
                {{?index1 == exam.index}}
                    <a class="num{{?exam.status != 'null'}} active"{{??}}" title="待答"{{?}} {{?exam.status == 'error'}}title="答错"{{??exam.status == 'success'}}title="答对"{{??exam.status == 'finish'}}title="答过"{{?}} href="#examStem{{=index1+1}}">
                    {{=index1+1}}
                    <i class="icon-circle-{{?exam.status == 'error'}}error{{??exam.status == 'success'}}success{{?}}"></i></a>
                {{?}}
                {{~}}
                {{~}}
        </div>
        <form action="{{=it.action || '#'}}" post="post" id="formExam">
			{{?it.listExam.length==0}}
				该素材已被删除
			{{??}}
            <input name="fdid" value="{{=it.id}}" type="hidden" />
			<input name="bamId" value="{{=it.bamId}}" type="hidden" />
			<input name="catalogId" value="{{=it.catalogId}}" type="hidden" />
            <input name="courseId" value="{{=it.courseId}}" type="hidden" />
			<input name="fdMtype" value="{{=it.fdMtype}}" type="hidden" />
			<input name="startTime" value="{{=it.startTime}}" type="hidden" />
            {{?it.type=='exam'}}
                <div class="bd">
                <dl class="listExam">
                {{~it.listExam :exam1:index2}}
                    {{~it.listExam :exam:index}}
                    {{?index2 == exam.index}}
                        <dt class="examStem" id="examStem{{=index2+1}}">
                            {{=index2+1}}. {{=exam.examStem}} （{{=exam.examScore}}分）
                        </dt>
                        <dd>
                            {{?exam.listAttachment!=null}}
                            <ul class="attachList unstyled">
                                {{~exam.listAttachment :att1:index1}}
                                {{~exam.listAttachment :att}}
                                {{?index1 == att.fdOrder}}
                                    <li><a onclick="downloadMater('{{=att.fdId}}','{{=att.fileNetId}}');" href="javascript:void(0)"><i class="icon-paperClip"></i>{{=att.fdFileName}}</a></li>
                                {{?}}
                                {{~}}
                                {{~}}
                            </ul>
                            {{?}}
 							{{?exam.examType == 'completion'}}
                                {{~exam.listExamAnswer :ans:indexno}}
                                <label class="input">
                                    <b class="icon-circle-bg blue">{{=indexno+1}}</b>
                                    <input type="text" class="input-xxlarge" value="{{=ans}}" exam-id="{{=exam.id}}" name="examAnswer_completion{{=exam.id}}{{=indexno+1}}" putId="examAnswer_completion"/>
									<input type="hidden" value=""  name="examAnswer"/>
                                </label>
                                {{~}}
                            {{??}}
                           		{{~exam.listExamAnswer :ans1:index1}}
                            	{{~exam.listExamAnswer :ans}}
                            	{{?index1 == ans.index}}
                                	<label class="{{?exam.examType == 'single'}}radio{{??}}checkbox{{?}}" >
                                    	<input type="{{?exam.examType == 'single'}}radio{{??}}checkbox{{?}}" {{?ans.isChecked}}checked{{?}} exam-id="{{=exam.id}}" value="{{=ans.id}}" name="examAnswer_completion{{=exam.id}}{{=indexno+1}}" putId="examAnswer_s" />
                                    	<input type="hidden" value="" name="examAnswer"/>
										{{=ans.name}}
                                	</label>
                            	{{?}}
                            	{{~}}
                            	{{~}}
							{{?}}
                        </dd>
                    {{?}}
                    {{~}}
                {{~}}
                </dl>
                </div>
            {{??it.type=='task'}}
                {{~it.listExam :task1:index2}}
                {{~it.listExam :task}}
                {{?index2 == task.index}}
                    <div class="bd">
                        <div class="pd20">
                            <div id="examStem{{=index2+1}}">{{=index2+1}}. {{=task.examName}}（{{=task.examScore}}分）</div>
<br/>
                            <div>{{=task.examStem.replace(new RegExp('\n','g'),'<br>') || ''}}</div>
                            <ul class="attachList unstyled">
                                {{~task.listAttachment :att1:index1}}
                                {{~task.listAttachment :att}}
                                {{?index1 == att.fdOrder}}
                                <li><a onclick="downloadMater('{{=att.fdId}}','{{=att.fileNetId}}');" href="javascript:void(0)"><i class="icon-paperClip"></i>{{=att.fdFileName}}</a></li>
                                {{?}}
                                {{~}}
                                {{~}}
                            </ul>
                        </div>
                        <div class="bdt1 pd20">
                            {{?task.examType == 'uploadWork'}}
                              {{?it.examPaperStatus=='finish'}} <label>已上传如下作业：</label>{{?}}
                              {{?it.examPaperStatus!='finish'}}
                                <label>上传作业（建议小于2G）</label>
                                <div class="control-upload">
                                    <span class="progress"> <div class="bar" style="width:0;"></div> </span>
									<span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
						             <button name="answerAtt" id="{{=task.id}}" class="btn btn-primary btn-large" type="button" >上传</button>
								</div>
                               {{?}}
                                <ul class="list-attachment unstyled" id="listTaskAttachment_{{=task.id}}">
                                    {{~task.listTaskAttachment :att2}}
                                        <li id="attach{{=att2.id}}">
                                            <input type="hidden" value='{{=att2.fdId}}' name='attach_{{=task.id}}' id='answerAttId_{{=task.id}}'>
                                            <a class="name" onclick="downloadMater('{{=att2.fdId}}','{{=att2.fileNetId}}');" href="javascript:void(0)">
												<i class="icon-paperClip"></i>&nbsp;{{=att2.fdFileName}}</a>
                                            <div class="item-ctrl"> <a class="icon-remove-blue" href="#"></a> </div>
                                        </li>
                                    {{~}}
                                </ul>
                            {{??task.examType == 'onlineAnswer'}}
                                {{?task.answer}}
                                    {{=task.answer}}
                                {{?}}
                                {{?it.examPaperStatus!='finish'}}
                                <label>答题</label>
                                <textarea name="answer_{{=task.id}}" required class="input-block-level" placeholder="请必务填写" rows="4"></textarea>
                                {{?}}
                            {{?}}
                        </div>
                        {{?task.status == 'success' || task.status == 'error'}}
                            <div class="bdt1 pd20">
                                <div class="media teacherRating">
                                    <div class="pull-left">
                                        <a href="${ctx}/course/courseIndex?userId={{=task.teacherRating.teacher.link}}"><img class="media-object" src="{{=task.teacherRating.teacher.imgUrl}}" alt="指导老师"/></a>
                                        <h3>{{=task.teacherRating.teacher.name}}</h3>
                                    </div>
                                    <div class="media-body">
                                        <div class="media-heading"><label class="label {{?task.status == 'success'}}label-success">通过{{??}}label-important">未通过{{?}}</label>｜ 满分 {{=task.examScore}} 分  ｜ 成绩 {{=task.teacherRating.score}} 分</div>
                                        <p>{{=task.teacherRating.comment || task.teacherRating.teacher.name+'老师很懒，没有写作业评语。'}}</p>
                                    </div>
                                </div>
                            </div>
                        {{?}}
                    </div>
                {{?}}
                {{~}}
                {{~}}
            {{?}}
        <div class="ft">
         {{?it.examPaperStatus!='finish'}}
 {{?it.type=='exam'}}
	<button class="btn btn-primary btn-large" type="submit">提交试卷</button>
 {{??it.type=='task'}}
	<button class="btn btn-primary btn-large" type="submit">提交作业包</button>
 {{?}}
                
        {{?}}
        <button data-fdType="{{=it.type}}" class="btn btn-link" type="button" data-toggle="collapse" data-parent="#listExamPaper" data-target="#examPaper{{=it.num}}">收起{{?it.type == 'exam'}}试题{{??it.type == 'task'}}作业包{{?}}<b class="caret"></b></button>
        </div>
        {{?}}
        </form>

        </div>
    </script>

    <!--试卷状态栏模板-->
    <script id="examPaperStatusBarTemplate" type="x-dot-template">
        <div class="examPaperStat">
            <div class="dt">{{?it.type == 'exam'}}试卷{{??it.type == 'task'}}作业包{{?}}{{=it.num}}</div>
            你已完成<span class="succ">{{=it.successCount}}</span>{{?it.type == 'exam'}}道题目{{??it.type == 'task'}}个作业{{?}}，
            共计<span class="total">{{=it.examCount}}</span>{{?it.type == 'exam'}}题{{??it.type == 'task'}}个作业{{?}}
            <a href="#navExams" data-toggle="collapse" class="showList"><span class="txtShow">展开</span><span class="txtHide">收起</span>{{?it.type == 'exam'}}试题{{??it.type == 'task'}}作业{{?}}列表<b class="caret"></b></a>
        </div>
        <div id="navExams" class="collapse in">
            <div class="collapse-inner">
                {{~it.listExam :exam1:index1}}
                {{~it.listExam :exam:index}}
                {{?index1 == exam.index}}
                    <a class="num{{?exam.status != 'null'}} active"{{??}}" title="待答"{{?}} {{?exam.status == 'error'}}title="答错"{{??exam.status == 'success'}}title="答对"{{??exam.status == 'finish'}}title="答过"{{?}} href="#examStem{{=index1+1}}">
                    {{=index1+1}}
                    <i class="icon-circle-{{?exam.status == 'error'}}error{{??exam.status == 'success'}}success{{?}}"></i></a>
                {{?}}
                {{~}}
                {{~}}
            </div>
        </div>
    </script>

    <!-- 视频、文档等媒体信息模板-->
    <script id="mediaInfoTemplate" type="x-dot-template">
        {{#def.mediaInfo:it.defaultMedia}}
        {{##def.mediaInfo:param:
            <div class="section mt20">
                <div class="mediaWrap bdbt2">
                    <div class="mediaObject">
                        {{?param.txt != null && it.txt!="" }}
                           <div id="richTxt">
                           {{=param.txt}}
                          </div>
                         {{?}}
                       {{?param.txt == null || it.txt=="" }}
						<iframe width="100%" height="416" id="iframeVideo" src="" frameBorder="0" scrolling="no"></iframe>
                       {{?}}
                   </div>
                </div>
                <div class="mediaToolbarWrap">
                    <div class="mediaToolbar" id="mediaToolbar" data-fdid="{{=param.id}}">
                        <div class="btn-group">
                            <a id="btnPraise"  converStatus="" title="赞"  class="btn btn-link{{?!param.mePraised}} active {{?}}"  href="javascript:void(0)"  praisedstatus="{{=param.mePraised}}" ><i class="icon-heart-blue"></i><span class="num">{{=param.praiseCount || 0 }}</span></a>
                           <a id="btnDownload" data-fileNetId="{{=param.fileNetId}}" title="{{?param.canDownload}}点击{{??}}无权{{?}}下载" canDownload="{{=param.canDownload}}"  class="btn btn-link {{?param.canDownload}}active{{?}}" {{?param.canDownload}}href="javascript:void(0)" data-fdid="{{=param.url}}" {{??}} disabled{{?}}><i class="icon-download-blue"></i><span class="num" id="sdowncount">{{=param.downloadCount || 0 }}</span></a>
                        </div>
                        <span class="playCount">{{?it.type == 'video'}}播放{{??}}阅读{{?}}  <strong class="num">{{=param.readCount || 0 }}</strong>  次</span>
                      <button id="btnDoPass" class="btn btn-success"{{?param.isPass}} disabled{{?}} converStatus=""><i class="icon-right"></i>我学会了</button>
                    </div>
                    {{#def.listMedia}}
                </div>
                <div class="hd" id="materialinfo">
                    <div class="tit-icon_bg"><i class="icon-video-intro"></i></div>
                    <h5>{{?it.type == 'video'}}视频{{??it.type == 'txt'}}富文本{{??it.type == 'doc'}}文档{{??it.type == 'ppt'}}幻灯片{{?}}信息</h5>
                    <div class="pos-right" id="ratingTotal">
                        <span>综合评分</span>
                            <span class="rating-all" >
                                {{ for(var i=0; i<5; i++){ }}
                                    <i class="icon-star{{?i < param.rating.average}} active{{?}}"></i>
                                {{ } }}
                             </span>
                        <b class="text-warning">{{=param.rating.average}}.0</b>
                    </div>
                </div>
                <div class="clearfix mt20" id="materialpf">
                    <div class="pull-left video-info">
 						 <h5><span class="name" id="mediaName">{{=param.name}}</span></h5>
                        <p class="mediaIntro" id="mediaIntro">
                            {{=param.intro || ""}}
                        </p>
                    </div>
                    <div class="pull-right" id="pullrightInfo">
                        <dl class="info-rating">

                            <dd id="ratingFive">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(param.rating.five/param.rating.total)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=param.rating.five}}</span>
                            </dd>
                            <dd id="ratingFour">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(param.rating.four/param.rating.total)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=param.rating.four}}</span>
                            </dd>
                            <dd id="ratingThree">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(param.rating.three/param.rating.total)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=param.rating.three}}</span>
                            </dd>
                            <dd id="ratingTwo">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(param.rating.two/param.rating.total)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=param.rating.two}}</span>
                            </dd>
                            <dd id="ratingOne">
                                <div class="rating span2">
                                    <i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(param.rating.one/param.rating.total)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=param.rating.one}}</span>
                            </dd>
                        </dl>
                    </div>
                </div>
            </div>
        #}}
        {{##def.listMedia:
            <ul class="mediaList nav nav-pills" id="listMedia">
                {{~it.listMedia :media:index}}
                <li class="{{?it.defaultMedia.id == media.id}}active{{?}} {{?media.isPass}}pass{{?}}">
                    <a href="{{=media.url}}" data-fdid="{{=media.id}}" title="{{=media.name}}">
                        <i class="icon-circle-success"></i>{{?it.type == 'video'}}视频{{??it.type == 'doc'}}文档{{??it.type == 'ppt'}}幻灯片{{?}}{{=index+1}}
                    </a>
                </li>
                {{~}}
            </ul>
        #}}
    </script>

    <!-- 视频、文档等媒体页面 评论模板-->
    <script id="mediaCommentTemplate" type="x-dot-template">
        <div class="section" id="mediaComment">
            <div class="edit-box">
                    <div class="box-rating">
                        <span>我要评分</span><!--rating-do-->
                        <span class="rating-all" id="ratingDo">
                            {{ for(var i=0; i<5; i++ ){ }}
                                <i class="icon-star{{?i<it.meRating}} active{{?}}"></i>
                            {{ } }}
                         </span>
                        <span class="point" id="ratingDoScore"></span>
                    </div>
                <form action="javascript:void(0)" id="formMakeComments" data-fdid="{{=it.mediaComment.id}}" >
                    <textarea  id="textComment" name="textComment" required maxlength="200" class="input-block-level" placeholder="有什么想吐槽的吗？随便说两句吧~ : )" ></textarea>
                    <div class="comm-rating">
                        <label for="isAnonymity" class="checkbox span1">
                            <input id="isAnonymity" name="isAnonymity" type="checkbox">匿名评论
                        </label>
                        <button class="btn btn-primary" id="commentSub" type="submit">发表</button>
                    </div>
                </form>
            </div>
			<div id="commentDiv">
            <div class="hd" id="hd-comment">
                <div class="tit-icon_bg"><i class="icon-white-info"></i></div>
                <h5>全部评论</h5>
                <div class="pages">
                    <div class="span2">
                        第<span id="pageLine1"> 1 - 10</span> / <span id="pageTotal1"></span> 条
                    </div>
                    <div class="btn-group">
                        <button id="gotoBefore1" class="btn btn-primary"><i class="icon-chevron-left icon-white"></i></button>
                        <button id="gotoNext1" class="btn btn-primary"><i class="icon-chevron-right icon-white"></i></button>
                    </div>
                </div>
            </div>
            <ul class="media-list comment-list" id="listComment">
            </ul>
            <div class="comment-list-bottom clearfix">
                <div class="pages pull-right">
                    <div class="span2" >
                        第<span id="pageLine2"> 1 - 10</span> / <span id="pageTotal2"></span> 条
                    </div>
                    <div class="btn-group">
                        <button id="gotoBefore2" class="btn btn-primary"><i class="icon-chevron-left icon-white"></i></button>
                        <button id="gotoNext2" class="btn btn-primary"><i class="icon-chevron-right icon-white"></i></button>
                    </div>
                </div>
            </div>
			</div>
        </div>
    </script>

    <!--评论列表 模板-->
    <script id="listCommentTemplate" type="x-dot-template">
        {{~it :item:index}}
        <li class="media" data-fdid="{{=item.fdId}}">
			<a href="${ctx}/course/courseIndex?userId={{=item.userId}}" class="pull-left">
				{{?item.isAnonymous}}
				<img class="media-object" src="${ctx}/resources/images/default-face-70.png" />
				{{??}}
				<img class="media-object" src="{{?item.fdUserURL.indexOf('http')>-1}}{{=item.fdUserURL}}{{??}}${ctx}/{{=item.fdUserURL}}{{?}}" />
				{{?}}
			</a>
            <div class="media-body">
                <div class="media-heading">
                    <span class="name">{{=item.no}}# {{?item.isAnonymous}}匿名{{??}}{{=item.fdUserName}}</span>（<span class="mail">{{=item.fdUserEmail}}</span>）    来自 <span class="org">{{=item.fdUserDept}}</span>{{?}}
                    {{?item.isShowScore}}
                        <div class="rating-view">
                                    <span class="rating-all">
                                        {{ for(var i=1; i<=5; i++){ }}
                                            <i class="icon-star{{?i <= item.score}} active{{?}}"></i>
                                        {{ } }}
                                    </span>
                            <b class="text-warning">
{{?(item.score+"").length==1}}
{{=item.score}}.0
{{??}}
{{=item.score}}
{{?}}
							</b>
                        </div>
                    {{?}}
                </div>
                <p class="comt-content">
                    {{=item.content}}
                </p>
                <div class="media-footing">
                    <div class="clearfix">
                        <span class="pull-left"><i class="icon-time"></i>{{=item.fdCreateTime}}
                        </span>
                        <div class="pull-right btns-comt">
                            <a href="#" class="btnPraise{{?!item.canSport}} active{{?}}"><i class="icon-thumbs-up"></i><span class="num">{{=item.supportCount}}</span></a>
                            <a href="#" class="btnWeak{{?!item.canOppose}} active{{?}}"><i class="icon-thumbs-down"></i><span class="num">{{=item.opposeCount}}</span></a>
                            <a href="#" class="btnComment"><i class="icon-dialog"></i><span class="num">{{=item.replyCount}}</span></a>
                        	{{?item.canDelete}}
								<a href="javascript:void(0)" class="btndeleteM" >删除</a>
							{{?}}
						</div>
                    </div>
                </div>
            </div>
        </li>
        {{~}}
    </script>

    <!--回复评论表单 模板-->
    <script id="formReplyCommentTemplate" type="x-dot-template">
        <div class="form-reply">
            <form id="formReply">
					<input type="hidden" id="replyCommHide" value="回复 （{{=it.name}}）："/>
                    <textarea class="input-block-level" required maxlength="200" id="replyComm" name="replyComm" rows="3" ></textarea>
                <div class="form-action">
                     <div class="btn-group">
                        <button class="btn btn-primary" type="submit">回复</button>
                        <button class="btn btn-cancel" type="button">取消</button>
                    </div>
                </div>
            </form>
        </div>
    </script>
				<script id="scoreInfo" type="x-dot-template">
 						<dl class="info-rating">
						 	<dd id="ratingFive">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(it.fdFiveScoreNum/it.fdScoreNum)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=it.fdFiveScoreNum}}</span>
                            </dd>
                            <dd id="ratingFour">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(it.fdFourScoreNum/it.fdScoreNum)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=it.fdFourScoreNum}}</span>
                            </dd>
                            <dd id="ratingThree">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(it.fdThreeScoreNum/it.fdScoreNum)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=it.fdThreeScoreNum}}</span>
                            </dd>
                            <dd id="ratingTwo">
                                <div class="rating span2">
                                    <i class="icon-star"></i><i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(it.fdTwoScoreNum/it.fdScoreNum)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=it.fdTwoScoreNum}}</span>
                            </dd>
                            <dd id="ratingOne">
                                <div class="rating span2">
                                    <i class="icon-star"></i>
                                </div>
                                <div class="progress-gray span1">
                                    <div class="bar" style="width: {{=(it.fdOneScoreNum/it.fdScoreNum)*100}}%;"></div>
                                </div>
                                <span class="fs9">{{=it.fdOneScoreNum}}</span>
                            </dd>
                        </dl>
    		</script>
   <!-- 结业证书页面  -->
   <script id="pageContentTemplate" type="text/x-dot-template">
       <div class="page-header" id="pageHeader" data-offset-top="10">
                <div class="hd clearfix">
             <a class="btn" href="#" id="firstC" >
                <i class="icon-chevron-lg-left"></i>
                <span>第一节</span>
                </a>
                <h1>{{=it.courseName}}
                <span class="labelPass{{?!it.isOptional || it.status == "pass"}} disabled{{?}}"{{?it.isOptional}} id="btnOptionalLecture"{{?}}>
                </span>
                </h1>
				<a class="btn" href="#" id="lastC" >
                <i class="icon-chevron-lg-right"></i>
                <span>最后节</span>
                </a>
                </div>
                <div class="bd" id="headToolsBar">
                </div>
        </div>
        <section class="page-body" >
            <section class="bdbt2 box-certificate" id="cardId">
                <div class="hd"><h2>企业认证教师</h2></div>
                <div class="bd">
                    <div class="media">
                        <a class="pull-left" href="{{=it.user.link}}">
								<img src="{{?it.user.imgUrl.indexOf('http')>-1}}{{=it.user.imgUrl}}{{??}}${ctx}/{{=it.user.imgUrl}}{{?}}" class="media-object img-polaroid" alt=""/>
                            	<i class="icon-medal-lg"></i>
                        </a>
                        <div class="media-body">
                            <div class="media-heading"><em>{{=it.user.name}}</em>老师，</div>
                            <p>已于 {{=it.passTime}} 完成《<em>{{=it.courseName}}</em>》，特此认证。
                                This is to certify MR/MS <span class="upper">{{=it.user.enName}}</span> 's successful completion of the New Oriental Teacher Online Training.</p>
                            <p class="muted">颁发者：{{=it.issuer}}</p>
                        </div>
                    </div>
                </div>
                <div class="ft">
                    <button class="btn btn-primary btn-large" type="button" id="downloadCertificate">下载证书</button>
                </div>
            </section>
        </section>
    </script>
    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body >
<input type="hidden" value="ldfklgsjkdfj">
<section class="container pr">
    <section class="clearfix" >
        <section class="col-left" id="sideBar">
        </section>
        <section class="col-right" id="mainContent">
        </section>
    </section>
</section>
<input type="hidden" id="fdCourseId" value="${courseId}">
<input type="hidden" id="ctx" value="${ctx}">
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/js/webimOfSide.js"></script>
<script type="text/javascript">
//下载素材
function downloadMater(attId,fileNetId){
  if(attId!=null && attId!='null' && attId!="" && fileNetId!=null&&fileNetId!="" && fileNetId!='null'){
	  window.location.href="${ctx}/common/file/download/"+attId;
  } else {
	  jalert("您好！该附件不存在");
  } 
}
</script>
<script type="text/javascript">


    $(function(){
    	$("#goTop").next(".help").addClass("light").before('<a href="${ctx}/passThrough/getCourseFeeling?courseId=${courseId}" title="课程跟踪" ><i class="icon-tracking"></i></a>');
    	$.Placeholder.init();
        //页面左侧模板函数
        var pageLeftBarFn = doT.template(document.getElementById("pageLeftTemplate").text);

        //页面右侧模板函数
        var rightContentFn = doT.template(document.getElementById("pageRightTemplate").text,undefined,{
            pageHeader: document.getElementById("pageRightHeaderTemplate").text,
            pageIntro: document.getElementById("pageRightIntroTemplate").text,
            examContent: document.getElementById("pageRightExamTemplate").text
        });

        /*试卷详情模板函数*/
        var examPaperDetailFn = doT.template(document.getElementById("examPaperDetailTemplate").text);
        /*试卷状态栏模板函数*/
        var examPaperStatusBarFn = doT.template(document.getElementById("examPaperStatusBarTemplate").text);

        /*视频、文档等媒体信息 模板函数*/
        var rightMaterialContentFn = doT.template(document.getElementById("pageRightTemplate").text,undefined,{
           pageHeader: document.getElementById("pageRightHeaderTemplate").text,
            pageIntro: document.getElementById("pageRightIntroTemplate").text,
            examContent: document.getElementById("mediaInfoTemplate").text
        });
        var mediaInfoFn = doT.template(document.getElementById("mediaInfoTemplate").text);
        /*视频、文档等媒体评论 模板函数*/
        var mediaCommentFn = doT.template(document.getElementById("mediaCommentTemplate").text);
        /*视频、文档等媒体评论列表 模板函数*/
        var listCommentFn = doT.template(document.getElementById("listCommentTemplate").text);
        /*视频、文档等媒体回复评论表单 模板函数*/
        var formReplyCommentFn = doT.template(document.getElementById("formReplyCommentTemplate").text);

		//课程进程Id
        var bamId = "${bamId}";
        
        //课程进程中的节Id
        var catalogId = "${catalogId}";
        
        //课程进行中节的内容类型
        var fdMtype = "${fdMtype}";
        
        var courseId = "${courseId}";
        
		var leftData = {};
				//ajax获取左侧章节展示树
				
	     loadLeftData(bamId);//加载左边栏目
				
        //左侧菜单定位
        /* setTimeout(function(){
            $("#sidebar").affix({
                offset: {
                    bottom: 100
                }
            }).parent().height($("#sidebar").height());
        },100); */

 

        $("[data-toggle='popover']").popover({
            trigger: "hover"
        });
        $("#sidenav>li>a").click(function(e){
                	$(".uploadify").each(function(){
                		$(this).uploadify('destroy'); 
                	});
                    e.preventDefault();
                    if($(this).attr("href")){//已通章节可点
                        loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
                        $(this).parent().addClass("active").siblings().removeClass("active");
                    }
                    $("#cordPage").removeClass("hide");
                });
		$("#courseOverBtn").bind("click",function(){
			loadOverCard();
		});
        loadRightCont(catalogId,fdMtype);//默认加载章节 参数：节id
        
        function loadOverCard(){
        	 var pageContentFn = doT.template(document.getElementById("pageContentTemplate").text);
        	 var pageData={};
        	 var passed;
        	 $.ajax({
	  			  url: "${ctx}/ajax/passThrough/getCourseOverByBamId",
	  			  async:false,
	  			  data:{
	  				  bamId:bamId
	  			  },
	  			  dataType:'json',
	  			  type:"post",
	  			  success: function(result){
	  				  if(result=="notThrough"){
	  					passed=false;
	  				  }else{
	  					passed=true; 
	  					 pageData=result;
	  				  }
	  			  }
 			});
        	 if(passed){
        		 $("#mainContent").html(pageContentFn(pageData));
        		 $("#downloadCertificate").bind("click",function(){
                	 window.location.href = "${ctx}/common/file/downloadImg?bamId="+bamId;
                 });
        		 $("#firstC").click(function (e){
        		    loadRightCont(pageData.firstCId,pageData.firstCType);
        		    loadLeftData(bamId);
        		    $("#sidenav>li>a").popover({
          	            trigger: "hover"
          	        }).click(function(e){
          	                	$(".uploadify").each(function(){
                            		$(this).uploadify('destroy'); 
                            	});
          	                    e.preventDefault();
          	                    if($(this).attr("href")){//已通章节可点
          	                        loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
          	                        $(this).parent().addClass("active").siblings().removeClass("active");
          	                    }
          	                  $("#cordPage").removeClass("hide");
          	                });
                  	//window.location.href = "${ctx}/passThrough/getStudyContent?courseId="+courseId+"&catalogId="+pageData.firstCId+"&fdMtype="+pageData.firstCType;
        		    $("#courseOverBtn").bind("click",function(){
            			loadOverCard();
            		}); 
        		 }); 
        		 $("#lastC").click(function (e){
        			loadRightCont(pageData.listCId,pageData.listCType);
        			loadLeftData(bamId);
        			$("#sidenav>li>a").popover({
          	            trigger: "hover"
          	        }).click(function(e){
          	                	$(".uploadify").each(function(){
                            		$(this).uploadify('destroy'); 
                            	});
          	                    e.preventDefault();
          	                    if($(this).attr("href")){//已通章节可点
          	                        loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
          	                        $(this).parent().addClass("active").siblings().removeClass("active");
          	                    }
          	                  $("#cordPage").removeClass("hide");
          	                });
                   	//window.location.href = "${ctx}/passThrough/getStudyContent?courseId="+courseId+"&catalogId="+pageData.listCId+"&fdMtype="+pageData.listCType;
        			$("#courseOverBtn").bind("click",function(){
            			loadOverCard();
            		}); 
        		 }); 
        		 $("#cordPage").addClass("hide");
        	 }
        }
		
		 function loadLeftData(bamId){
			  $.ajax({
				  url: "${ctx}/ajax/passThrough/getBamCatalogTree",
				  async:false,
				  data:{bamId:bamId},
				  dataType:'json',
				  success: function(rsult){
					  leftData = rsult;
					  leftData.sidenav.currentId = catalogId;
					  $("#sideBar").html(pageLeftBarFn(leftData));
					  
				  }
			});
			$("#courseOverBtn").bind("click",function(){
      			loadOverCard();
      		});
		 }
		function loadRightCont(fdid,type){
        	catalogId=fdid;
		    fdMtype=type;
		    var mdata;
	        $.ajax({
		  			  url: "${ctx}/ajax/passThrough/getCourseContent",
		  			  async:false,
		  			  cache:false,
		  			  data:{
		  				  catalogId:fdid,
		  				  bamId:bamId,
		  				  fdMtype:fdMtype
		  			  },
		  			  dataType:'json',
		  			  success: function(result){
		  				  
		  				  
		  				  
		  				if(result.type == "exam" || result.type == "task"){
		  					//$("#test")[0].innerHTML=JSON.stringify(result);
		  					$("#mainContent").html(rightContentFn(result));
		  					afterLoadExamOrTaskPage(result);
		  	            }else if(result.type == "txt"){
		  	            	$("#mainContent").html(rightMaterialContentFn(result));
		  	                afterLoadMediaPage(result);
		  	          		$("#btnPraise").attr("converStatus","true");
		  	          	    $("#btnDoPass").attr("converStatus","true");
		  	          		$("#btnDownload").addClass("hide");
		  	          	    /* initplaceholder(false); */
		  	            } else if(result.type == "video" || result.type == "doc"||result.type == "ppt"){
		  	                // alert($("#iframeVideo"));
		  	                // $("#iframeVideo").contents().find("body").empty();
		  	              // $("#test")[0].innerHTML=JSON.stringify(result);
		  	            
		  	            	$("#mainContent").html(rightMaterialContentFn(result));
		  	            	// alert($("#mainContent").html());
		  	                afterLoadMediaPage(result);
		  	                mdata=result.defaultMedia;
		  	                loadvideoOrDoc(mdata);
			  	            //调用方法时 如果素材为空  则隐藏素材下的 下载赞等信息 、 素材信息、  评分信息 、评分的列表信息 这几个信息分别代表一个div
			  	  			//这块只针对视频和文档
			  	  			
               			  	 if(mdata.code==""||mdata.code.type=="none"){
		  	  					$("#btnDownload").attr("title","当前没有可下载素材");
		  	  					$("#mediaComment").addClass("hide");
		  	  				    $("#mediaToolbar").addClass("hide");
		  	  				} 
			  	                //素材转换中
		  	                 if(mdata.code!=""&&mdata.code.type!='none'){
		  	                	//不能点赞  可以下载 不能学习通过 没有评论; 
		  	                	 if(mdata.code.type=="video"&&mdata.code.playCode==null){
				  	              		$("#btnPraise").attr("converStatus","");
				  	              		$("#btnPraise").attr("data-original-title","当前状态不允许赞");
				  	              	    $("#mediaComment").addClass("hide");
				  	            	 }else if(mdata.code.type=="doc"&&mdata.code.fileNetId==null){
				  	              		$("#btnPraise").attr("converStatus","");
				  	              		$("#btnPraise").attr("data-original-title","当前状态不允许赞");
				  	                    $("#mediaComment").addClass("hide");
						  	         }else{
					  	        	 	$("#btnPraise").attr("converStatus","true");
					  	        	 	$("#btnPraise").attr("data-original-title","赞");
					  	        	 	$("#mediaComment").removeClass("hide");
						  	         }
		  	                }else{
		  	                	$("#btnPraise").attr("converStatus","");
		  	              		$("#btnPraise").attr("data-original-title","当前状态不允许赞");
		  	              		$("#mediaComment").addClass("hide");
		  	                } 
			  	                //学习通过控制
			  	 /*              if(mdata.code==""||mdata.code.type=="none"){
				  	            	$("#btnDoPass").attr("converStatus","");
			  	                  }else{
			  	                	if(mdata.code.type=="video"&&mdata.code.playCode==null){
			  	              			$("#btnDoPass").attr("converStatus","");
				  	            	 }else if(mdata.code.type=="doc"&&mdata.code.fileNetId==null){
				  	            		$("#btnDoPass").attr("converStatus","");
				  	            	 }else{
				  	            		 $("#btnDoPass").attr("converStatus","true");
				  	            	 }
			  	                  } */
			  	                /* initplaceholder(false);  */
			  	  			}
		  	            }
		  				
	  			});
	
			//
            //可选章节按钮
            $("#btnOptionalLecture")
                    .click(function(e){
                        //$(this).removeClass("disabled");
                        //$("#nextLecture").removeClass("disabled");
                     $.ajax({
      		  			  url: "${ctx}/ajax/passThrough/updateCatalogThrough",
      		  			  async:false,
      		  			  data:{
      		  				  catalogId:catalogId,
      		  				  bamId:bamId
      		  			  },
      		  			  dataType:'json',
      		  			  success: function(result){
      		  				loadLeftData(bamId);
      		  				loadRightCont(catalogId,fdMtype);
      		  			$("#sidenav>li>a").popover({
      		              trigger: "hover"
      		            })
      		                  .click(function(e){
      		                	$(".uploadify").each(function(){
                            		$(this).uploadify('destroy'); 
                            	});
      		                      e.preventDefault();
      		                      if($(this).attr("href")){//已通章节可点
      		                          loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
      		                          $(this).parent().addClass("active").siblings().removeClass("active");
      		                      }
      		                    $("#cordPage").removeClass("hide");
      		                  });
      		  			  }
      	  			}); 
                        
          });
            
          //上一节
            $("#prevLecture").click(function (e){
            	if($(this).attr("href")){
            		catalogId = $(this).attr("data-fdid");
            		loadLeftData(bamId);
            		$("#sidenav>li>a").popover({
          	            trigger: "hover"
          	        }).click(function(e){
          	                	$(".uploadify").each(function(){
                            		$(this).uploadify('destroy'); 
                            	});
          	                    e.preventDefault();
          	                    if($(this).attr("href")){//已通章节可点
          	                        loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
          	                        $(this).parent().addClass("active").siblings().removeClass("active");
          	                    }
          	                  $("#cordPage").removeClass("hide");
          	                });
            		loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
            	}
            	$("#courseOverBtn").bind("click",function(){
        			loadOverCard();
        		}); 
            });
            //下一节
            $("#nextLecture").click(function (e){
            	if($(this).attr("href")){
            		catalogId = $(this).attr("data-fdid");
            		loadLeftData(bamId);
            		$("#sidenav>li>a").popover({
          	            trigger: "hover"
          	        }).click(function(e){
          	                	$(".uploadify").each(function(){
                            		$(this).uploadify('destroy'); 
                            	});
          	                    e.preventDefault();
          	                    if($(this).attr("href")){//已通章节可点
          	                        loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
          	                        $(this).parent().addClass("active").siblings().removeClass("active");
          	                    }
          	                  $("#cordPage").removeClass("hide");
          	                });
            		loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
            	}
            	$("#courseOverBtn").bind("click",function(){
        			loadOverCard();
        		});
            }); 
            $("#nextOver").click(function (e){
            	loadOverCard();
            }); 
            $("#pageHeader").affix({
                offset: {
                    top: 10
                }
            });
            $(window).on("scroll",function(){
            	var hdH = 100;
                if($("#pageHeader").hasClass("affix-top")){
                    $("#pageBody").css("margin-top",0);
                    hdH = $("#pageHeader").height();
                } else {
                    $("#pageBody").css("margin-top",hdH);
                }
            });
        }
        /*视频、文档等媒体页加载头部后执行方法*/
        function afterLoadMediaPage(data){
            var $body = $("#pageBody");
            $body.append(mediaInfoFn(data))
                    .append(mediaCommentFn(data.defaultMedia));
            //$("#listComment").html(listCommentFn(data.defaultMedia.mediaComment.listComment));

            /*评分效果*/
            $body.delegate("#ratingDo>i","click",function(e){
                var $this = $(this),
                        index = $this.index();
                    $this.addClass("active").prevAll().addClass("active");
                    $this.nextAll().removeClass("active");
                    $this.parent().nextAll(".point").text((index+1)+".0");  
                    $.ajax({
  		  			  url: "${ctx}/ajax/score/pushMaterialToCourse",
  		  			  async:false,
  		  			  data:{
  		  					fdModelId :$("#formMakeComments").attr("data-fdid"),
  		  					fdScore : index + 1
  		  			  },
  		  			  dataType:'json',
  		  			  success: function(result){
  		  				resetScoreInfo();
  		  				jalert_tips("评分成功");  		  				
  		  			  }
  	  				});
            })  /*评论列表中按钮事件*/
                .delegate("#listComment>.media .btns-comt>a","click",function(e){
                	$("#textComment").next(".error").remove();
                    e.preventDefault();
                        var $this = $(this);
                        var itemId = $this.closest(".media").attr("data-fdid");
                        var $num = $this.children(".num");
                        if(!$this.hasClass("active")){
                            if($this.hasClass("btnPraise")){//赞
                            	var pushok;
                            	$.ajax({
	                          		  url: "${ctx}/ajax/message/supportOrOpposeMessage",
	                          		  async:false,
	                          		  data:{
	                          			messageId :itemId,
	                          			fdType :"01"
	                          		  },
	                          		  success: function(result){
	                          			  if(result=='"cannot"'){
	                          				pushok=false;
	                          			  }else{
	                          				pushok=true;
	                          			  }
	                          		  }
                          		});
                            	if(pushok){
                            		$num.text(parseInt($num.text()) + 1);
                            		$this.addClass("active");
                            	}else{
                            		jalert_tips("不能支持和反对自己的评论");
                            	}
                            } else if($this.hasClass("btnWeak")){//踩
                            	var pushok;
                            	$.ajax({
	                          		  url: "${ctx}/ajax/message/supportOrOpposeMessage",
	                          		  async:false,
	                          		  data:{
	                          			messageId :itemId,
	                          			fdType :"02"
	                          		  },
	                          		  success: function(result){
	                          			  if(result=='"cannot"'){
	                          				pushok=false;
	                          			  }else{
	                          				pushok=true;
	                          			  }
	                          		  }
                          		});
                            	if(pushok){
                            		$num.text(parseInt($num.text()) + 1);
                            		$this.addClass("active");
                            	}else{
                            		jalert_tips("不能支持和反对自己的评论");
                            	}
                            } else if($this.hasClass("btnComment")){//评论
                                //if($("#formReply").length){
                                	//jalert("请先保存其它回复");
                                //} else {
                                	 $("#formReply .btn-cancel").closest(".form-reply").remove();
             						 $this.removeClass("active");
             						 
                                    var $mediaBody = $this.closest(".media-body");
                                    var toName =  $mediaBody.find(".media-heading>.name").text();
                                    var toMail =  $mediaBody.find(".media-heading>.mail").text();
                                    var toOrg =  $mediaBody.find(".media-heading>.org").text();
                                    var toLink = $mediaBody.prev("a").attr("href");
                                    $this.closest(".clearfix").after(formReplyCommentFn({
                                        name: toName
                                    }));
                                    /*评论列表中回复表单验证*/
                                    $("#formReply").validate({
                                        submitHandler: function(form){
                                           $.ajax({
          	                          		  url: "${ctx}/ajax/message/addMaterialMessagesMessage",
          	                          		  async:false,
          	                          		  data:{
          	                          			materialId :$("#formMakeComments").attr("data-fdid"),
          	                          			fdContent : ($("#replyCommHide").val()+$("#replyComm").val()),
          	                          			messageId :itemId
          	                          		  },
          	                          		  success: function(result){
          	                          			resetComment(1,10);
          	                          			jalert_tips("回复成功");
          	                          		  }
                                    		});
                                           $("body").animate({scrollTop:$("#hd-comment").offset().top - $("#pageHeader").height() - 60},400,"swing",function(){
                                           });
                                        }
                                    });
                                    $("#replyComm").focus();
                                    $("#replyComm").bind("keydown",function(){
                                    	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
                                   		if (keyCode == 13) {
                                   			$("#formReply").submit();
                                   			return false;
                                   		}
                                     });
                                    $("#formReply .btn-cancel").click(function(e){
                                        $(this).closest(".form-reply").remove();
                                        $this.removeClass("active");
                                    });

                                //}
                            }else if($this.hasClass("btndeleteM")){
                            	jalert("您确定删除该评论吗？",function(){
                        			$.ajax({
                        	    		  url: "${ctx}/ajax/message/removeMessage",
                        	    		  async:false,
                        	    		  data:{
                        	    			  messageId :itemId
                        	    		  },
                        	    		  success: function(result){
                        	    			  resetComment(1,10);
                        	    		  }
                        			});
                        		});
                            }
                            
                        }
                });
			resetComment(1,10);
			resetScoreInfo();
            /*评分表单*/
            $("#formMakeComments").validate({
                submitHandler: submitFormComment
            });
            
            function resetScoreInfo(){
            	var s=0;
            	$.ajax({
          		  url: "${ctx}/ajax/score/canPushScoreToMaterial",
          		  async:false,
          		  dataType:'json',
          		  data:{
          			  fdModelId:$("#formMakeComments").attr("data-fdid")
          		  },
          		  success: function(result){
          			  var score = (result==-1)?0:result;
          			  $("#ratingDo").find(".icon-star").each(function(i){
  		                    if(i < score){
  		                        $(this).addClass("active");
  		                    } else {
  		                        $(this).removeClass("active");
  		                    }
  		                });
          			  $("#ratingDoScore").text(score+".0");
          			  s=score;
          		  }
      			});
            	$.ajax({
            		  url: "${ctx}/ajax/score/getScoreStatisticsByfdModelId",
            		  async:false,
            		  dataType:'json',
            		  data:{
            			  fdModelId:$("#formMakeComments").attr("data-fdid"),
            			  fdModelName:"<%=MaterialInfo.class.getName()%>"
            		  },
            		  success: function(result){
            			  var ave;
  		  				  if((result[0].fdAverage+"").length==1){
  		  					ave= result[0].fdAverage+".0";
  		  				  }else{
  		  					ave= result[0].fdAverage+""; 
  		  				  }
            			  $("#ratingTotal").find(".rating-all>.icon-star").each(function(i){
    		                    if((i+1) <= result[0].fdAverage){
    		                        $(this).addClass("active");
    		                    } else {
    		                        $(this).removeClass("active");
    		                    }
    		                }).end().children("b.text-warning").text(ave);
    		  				var scoreInfoHtml = doT.template(document.getElementById("scoreInfo").text);
    		  				$("#pullrightInfo").html(scoreInfoHtml(result[0]));
            		  }
        		});
            	$("#ratingDo  i").each(function(index){
    				$(this).bind("mouseover",function(){
    					$(this).addClass("active").prevAll().addClass("active");
    					$(this).nextAll().removeClass("active");
    					$("#ratingDoScore").html((index+1)+".0");
    				});
    				$(this).bind("mouseout",function(){
    					$("#ratingDo").find(".icon-star").each(function(i){
		                    if(i < s){
		                        $(this).addClass("active");
		                    } else {
		                        $(this).removeClass("active");
		                    }
		            });
    					$("#ratingDoScore").html(s+".0");
    				});
    		  });
            	
            }

            function resetComment(pageNo,pageSize){
                $.ajax({
            		  url: "${ctx}/ajax/message/findCommentByModelId",
            		  async:false,
            		  dataType : 'json',
            		  type:"post",
            		  data:{
            			  modelName:"<%=MaterialInfo.class.getName()%>",
            			  modelId:$("#formMakeComments").attr("data-fdid"),
            			  pageNo:pageNo,
            			  pageSize:pageSize
                	  },
            		  success: function(result){
            			  $("#listComment").html(listCommentFn(result.listComments));
            			  if(result.listComments.length==0){
            				  $("#commentDiv").addClass("hide");
            			  }else{
            				  $("#commentDiv").removeClass("hide");
            			  }
            		  }
            	});
              //刷新评论页码
                $.ajax({
          		  url: "${ctx}/ajax/message/getCommentPageInfo",
          		  async:false,
          		  dataType : 'json',
          		  data:{
          			  modelName:"<%=MaterialInfo.class.getName()%>",
          			  modelId:$("#formMakeComments").attr("data-fdid"),
          			  pageNo:pageNo,
          			  pageSize:pageSize
              	  },
          		  success: function(result){
         			 $("#pageLine1").html(" "+result.startLine+" - "+result.endLine+" ");
         			 $("#pageLine2").html(" "+result.startLine+" - "+result.endLine+" ");
         			 $("#pageTotal1").html(result.totalSize);
         			 $("#pageTotal2").html(result.totalSize);
         			 //第一页
         			 if(result.pageNo==1){
         				 $("#gotoBefore1").unbind();
         				 $("#gotoBefore2").unbind();
         				 $("#gotoNext1").bind("click",function (){resetComment(pageNo+1,pageSize);});
         				 $("#gotoNext2").bind("click",function (){resetComment(pageNo+1,pageSize);});
         				 $("#gotoBefore1").attr("disabled",true);
         				 $("#gotoBefore2").attr("disabled",true);
         				 $("#gotoNext1").removeAttr("disabled");
         				 $("#gotoNext2").removeAttr("disabled");
         			 }
         			 //最后一页
         			 if(result.pageNo==result.totalPage){
         				 $("#gotoNext1").unbind();
         				 $("#gotoNext2").unbind();
         				 $("#gotoNext1").attr("disabled",true);
         				 $("#gotoNext2").attr("disabled",true);
         				 $("#gotoBefore1").bind("click",function (){resetComment(pageNo-1,pageSize);});
         				 $("#gotoBefore2").bind("click",function (){resetComment(pageNo-1,pageSize);});
         				 $("#gotoBefore1").removeAttr("disabled");
         				 $("#gotoBefore2").removeAttr("disabled");
         			 }
         			 //只有一页
         			 if(result.pageNo==result.totalPage&&result.pageNo==1){
         				 $("#gotoNext1").unbind();
         				 $("#gotoNext2").unbind();
         				 $("#gotoBefore1").unbind();
         				 $("#gotoBefore2").unbind();
         				 $("#gotoNext1").attr("disabled",true);
         				 $("#gotoNext2").attr("disabled",true);
         				 $("#gotoBefore1").attr("disabled",true);
         				 $("#gotoBefore2").attr("disabled",true);
         			 }
         			 //中间
         			 if(result.pageNo!=result.totalPage&&result.pageNo!=1){
         				 $("#gotoNext1").bind("click",function (){resetComment(pageNo+1,pageSize);});
         				 $("#gotoNext2").bind("click",function (){resetComment(pageNo+1,pageSize);});
         				 $("#gotoBefore1").bind("click",function (){resetComment(pageNo-1,pageSize);});
         				 $("#gotoBefore2").bind("click",function (){resetComment(pageNo-1,pageSize);});
         				 $("#gotoBefore1").removeAttr("disabled");
         				 $("#gotoBefore2").removeAttr("disabled");
         				 $("#gotoNext1").removeAttr("disabled");
         				 $("#gotoNext2").removeAttr("disabled");
         			 }
         			 
          		  }
          		});
            
            }
            
            /*提交评论表单*/
            function submitFormComment(form){
            	/* initplaceholder(true); */
               if($("#textComment").val()==""||$("#textComment").val()==null){
            	   jalert_tips("请输入评论信息！");  
                   return false;
               }
               if($("#textComment").val().length>200){
            	   jalert_tips("评论信息过长！");  
                   return false;
               }
               $.ajax({
          		  	url: "${ctx}/ajax/message/addMaterialMessage",
          		  	async:false,
          		  	dataType : 'json',
          		  	data:{
          				materialId:$("#formMakeComments").attr("data-fdid"),
                    	isAnonymity: $("#isAnonymity").is(":checked"),
                    	fdContent: $("#textComment").val()
              	  	}
          		});
               $("#textComment").val("");
               resetComment(1,10);
               jalert_tips("评论发表成功");  
               return false;
            }
            $("#textComment").bind("keydown",function(){
            	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
            	if (keyCode == 13) {
            		submitFormComment($("#formMakeComments"));
            		return false;
            	}
            });            

            /**************************  如下代码把 videoplayer.swf 嵌入到 id 为 flashcontent 的div中  *********************/
            /* var flashvars =
            {
                //flash接收的参数
                autoPlay:'true',
                skin:"http://me.xdf.cn:80/iportal/sys/attachment/video/videoPlayerSkin.swf",
                video: data.defaultMedia.url,
                fms:"rtmp://video.xdf.cn/V3/"
            };
            var params =
            {
                allowScriptAccess: "always",
                allowFullScreen:"true",
                wmode:"transparent",
                quality:"high"
            };
            var attributes =
            {
                id: "myflash"
            };
            swfobject.embedSWF("http://me.xdf.cn:80/iportal/sys/attachment/video/videoplayer.swf", "flashcontent",
                    "100%", "510", "6.0.0", "expressInstall.swf", flashvars, params, attributes); */
            
            var $mediaToolbar = $("#mediaToolbar");

            $("#listMedia>li>a").bind("click",function(e){
            	
                e.preventDefault();
                var $this = $(this);
                $this.parent("li").addClass("active").siblings().removeClass("active");
                /* flashvars.video = $this.attr("href"); */
                /* swfobject.embedSWF("http://me.xdf.cn:80/iportal/sys/attachment/video/videoplayer.swf", "myflash",
                        "100%", "510", "6.0.0", "expressInstall.swf", flashvars, params, attributes); */
                
                 $.ajax({
                	  cache:false,
		  			  url: "${ctx}/ajax/passThrough/getCourseContent",
		  			  async:false,
		  			  data:{
		  				  materialId:$this.attr("data-fdid"),
		  				  catalogId:catalogId,
		  				  bamId:bamId,
		  				  fdMtype:fdMtype
		  			  },
		  			  dataType:'json',
		  			  success: function(data){
		  					$("#goTop").trigger("click");
		  	                   mdata=data.defaultMedia;
		  	                 //alert(JSON.stringify(mdata))
		  	                 if(data.type == 'txt'){
		  	            		  $("#richTxt").html(mdata.txt);
		  	            	  }
		  	            	  if(data.type!='txt'){
		  	            		//重新定位播放视频
			  	                  loadvideoOrDoc(mdata);
			  	            	  //判断素材状态  空 转换中都不允许点赞
				  	              if(mdata.code==""||mdata.code.type=="none"){
				  	            	$("#btnPraise").attr("converStatus","");
			  	              		$("#btnPraise").attr("data-original-title","当前状态不允许赞");
			  	                  }else{
			  	                	    if(mdata.code.type=="video"&&mdata.code.playCode==null){
					  	              		$("#btnPraise").attr("converStatus","");
					  	              		$("#btnPraise").attr("data-original-title","当前状态不允许赞");
					  	            	 }else if(mdata.code.type=="doc"&&mdata.code.fileNetId==null){
						  	              		$("#btnPraise").attr("converStatus","");
						  	              		$("#btnPraise").attr("data-original-title","当前状态不允许赞");
							  	         }else{
							  	        	 	$("#btnPraise").attr("converStatus","true");
							  	        	 	$("#btnPraise").attr("data-original-title","赞");
							  	         }
			  	                				
			  	                  }
				  	            $(".line > .videoName").html(mdata.name);
			  	            	  if(mdata.isPass){
			  	            	  	$("#fpassStauts").html("[已学会]");
			  	            	  }else{
			  	            		$("#fpassStauts").html("");
			  	            	  }
		  	            	  }
		  	            	//是否允许赞
			  	              if(mdata.mePraised){
			  	              	$("#btnPraise").removeClass("active").children(".num").text(mdata.praiseCount);
			  	                $("#btnPraise").attr("data-original-title","赞");
			  	              }else{
			  	              	 $("#btnPraise").addClass("active").children(".num").text(mdata.praiseCount);
			  	              	$("#btnPraise").attr("data-original-title","已赞");
			  	              }
			  	  			  //通过素材转换状态判断是否可以点赞
			  	            	 
			  	              $("#btnDownload").addClass(mdata.canDownload ? "active" : "disabled").attr("data-original-title",mdata.canDownload ? '点击下载' : '无权下载')
			  	                      .children(".num").text(mdata.downloadCount);
			  	              $("#btnDoPass").attr("disabled",mdata.isPass ? true : false);
			  	              if(data.type!="txt"){
				  	          		//判断素材状态  空 转换中都不允许学习
				  	            /*   if(mdata.code==""||mdata.code.type=="none"){
				  	            	$("#btnDoPass").attr("converStatus","");
			  	                  }else{
			  	                	if(mdata.code.type=="video"&&mdata.code.playCode==null){
			  	              			$("#btnDoPass").attr("converStatus","");
				  	            	 }else if(mdata.code.type=="doc"&&mdata.code.fileNetId==null){
				  	            		$("#btnDoPass").attr("converStatus","");
				  	            	 }else{
				  	            		 $("#btnDoPass").attr("converStatus","true");
				  	            	 }
			  	                  } */
			  	              }else{
			  	            	// $("#btnDoPass").attr("converStatus","true");
			  	            	$("#btnDownload").addClass("hide");
			  	              }
			  	              $mediaToolbar.find(".playCount>.num").text(mdata.readCount);
			  	              $("#mediaToolbar").attr("data-fdid",mdata.id);
			  	              $("#btnDownload").attr("data-fdid",mdata.url);
			  	              $("#btnDownload").attr("canDownload",mdata.canDownload);
			  	              $("#btnPraise").attr("praisedstatus",mdata.mePraised);
			  	              $("#mediaName").text($this.attr("title"));
			  	              $("#mediaIntro").text(mdata.intro==null?"":mdata.intro);
			  	              var ave;
			  	  				  if((mdata.rating.average+"").length==1){
			  	  					ave= mdata.rating.average+".0";
			  	  				  }else{
			  	  					ave= mdata.rating.average+""; 
			  	  				  }
			  	              $("#ratingTotal").find(".rating-all>.icon-star").each(function(i){
			  	              if(i < mdata.rating.average){
			  	                  $(this).addClass("active");
			  	              } else {
			  	                  $(this).removeClass("active");
			  	              }
			  	              }).end().children("b.text-warning").text(ave);
			  	              $("#ratingFive").find(".progress-gray>.bar")
			  	                      .width(mdata.rating.five/mdata.rating.total*100 + "%").end().children(".fs9").text(mdata.rating.five);
			  	              $("#ratingFour").find(".progress-gray>.bar")
			  	                      .width(mdata.rating.four/mdata.rating.total*100 + "%").end().children(".fs9").text(mdata.rating.four);
			  	              $("#ratingThree").find(".progress-gray>.bar")
			  	                      .width(mdata.rating.three/mdata.rating.total*100 + "%").end().children(".fs9").text(mdata.rating.three);
			  	              $("#ratingTwo").find(".progress-gray>.bar")
			  	                      .width(mdata.rating.two/mdata.rating.total*100 + "%").end().children(".fs9").text(mdata.rating.two);
			  	              $("#ratingOne").find(".progress-gray>.bar")
			  	                      .width(mdata.rating.one/mdata.rating.total*100 + "%").end().children(".fs9").text(mdata.rating.one);
			  	              $("#mediaComment").after(mediaCommentFn(mdata)).remove();
			  	              //$("#mediaComment").show(); 
			  	              if(data.type!='txt'){
			  	              if(mdata.code==""||mdata.code.type=='none'){
			  	            		$("#mediaComment").addClass("hide");
			  	            		 $("#mediaToolbar").addClass("hide");
				  	            }else{
				  	            	if(mdata.code.type=="doc"&&mdata.code.fileNetId==null){
										$("#mediaComment").addClass("hide");
			  	                	}else if(mdata.code.type=="video"&&mdata.code.playCode==null){
										$("#mediaComment").addClass("hide");
			  	                	}else{
			  	                		$("#mediaComment").removeClass("hide");
			  	                		$("#mediaToolbar").removeClass("hide");
			  	                	}
				  	            }
			  	              }
			  	              /* initplaceholder(false); */
			  	              //$("#listComment").html(listCommentFn(mdata.mediaComment.listComment));

			  	              /*评分表单*/
			  	              $("#formMakeComments").validate({
			  	                  submitHandler: submitFormComment
			  	              });
			  	              resetComment(1,10);
			  	  			  resetScoreInfo();	
		  	            
		  			  }
                }); 
                 $("#textComment").bind("keydown",function(){
                 	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
                 	if (keyCode == 13) {
                 		submitFormComment($("#formMakeComments"));
                 		return false;
                 	}
                 }); 
                        
            });

            $mediaToolbar.find(".btn-group>.btn").tooltip();

            /*点赞事件*/
            $("#btnPraise").on("click",function(e){
                e.preventDefault();
                var $this = $(this);
               /*  if($this.hasClass("active")){
                    $this.removeClass("active").attr("data-original-title","赞").children(".num").text(parseInt($this.text())-1);
                } else {
                    $this.addClass("active").attr("data-original-title","取消赞").children(".num").text(parseInt($this.text())+1);
                } */
                //$.post("url",{id: $mediaToolbar.attr("data-fdid")})
	            	if($this.attr("converStatus")==""){
	            		return;
	            	} 
	                if($this.attr("praisedstatus")=='true'){
		                $.ajax({
		         			type: "post",
		         			url: "${ctx}/ajax/material/saveLaud",
		         			data : {
		         				"materialId":$mediaToolbar.attr("data-fdid")
		         			},
		         			success:function(data){
		         				 $this.addClass("active").attr("data-original-title","赞").children(".num").text(data); 
		         				 $this.attr("praisedstatus",'false');
		         			}
		         		}); 
	                }else{
	                	jalert("您已经赞过该资源!");
	                }
            });

            /*点下载事件*/
            $("#btnDownload").on("click",function(e){
                var $this = $(this);
                if($this.attr("canDownload")=='false'){
                    e.preventDefault();
                    return;
                } else {
                    //$.post("url",{id: $mediaToolbar.attr("data-fdid")});
                	 if($(this).attr("data-fileNetId")!='null'&&$(this).attr("data-fileNetId")!=null&&$(this).attr("data-fileNetId")!=''
                			 &&$this.attr("data-fdid")!=null&&$this.attr("data-fdid")!=""){
                		 $.ajax({
                  			type: "post",
                  			url: "${ctx}/ajax/material/updateDownloadNum",
                  			data : {
                  				"materialId":$mediaToolbar.attr("data-fdid")
                  			},
                  			success:function(data){
                  				 window.location.href="${ctx}/common/file/download/"+$this.attr("data-fdid");
                  				 $("#btnDownload").find("span").html(data);
                  			}
                  		}); 
                     	 
                       } else {
                     	  jalert("您好！该视频没有对应附件");
                       } 
                }
            });

            $("#btnDoPass").on("click",function(e){
            	var passNo = ($("#listMedia").find("li").length)-($("#listMedia").find(".pass").length)-1;
            	var total = $("#listMedia").find("li").length;
            	if(passNo==0){
            		jalert_tipst("恭喜您通过本节！请点击右上方的 “下一节” 按钮，继续学习",4);
            	}else{
            		var typeV="";
            		if(fdMtype=="01"){
            			typeV="视频";
            		}else if(fdMtype=="04"){
            			typeV="文档";
            		}else if(fdMtype=="05"){
            			typeV="ppt";
            		}else if(fdMtype=="07"){
            			typeV="富文本";
            		}else {
            			typeV="资源";
            		}
            		jalert_tipst("恭喜您学会了“"+$("#mediaName").html()+"”，请继续学习本节中剩余的"+passNo+"/"+total+"个"+typeV,4);
            	}
            	
            	$("#goTop").trigger("click");
            	//alert("222");
            	/* if($(this).attr("converStatus")==""){
            		jalert("该状态不学习!");
            		return ;
            	} */
                $("#listMedia>li.active").addClass("pass");
                $(this).attr("disabled", true);
                $.ajax({
          			type: "post",
          			url: "${ctx}/passThrough/submitExamOrTask",
          			data : {
          				"bamId":bamId,
          				"courseId":courseId,
          				"catalogId":catalogId,
          				"fdMtype":fdMtype,
          				"fdid":$mediaToolbar.attr("data-fdid"),
          				"materialId":$mediaToolbar.attr("data-fdid")
          			},
          			success:function(data){
          				loadRightCont(catalogId,fdMtype);
          				loadLeftData(bamId);
          			  $("#sidenav>li>a").popover({
          	            trigger: "hover"
          	        })
          	                .click(function(e){
          	                	$(".uploadify").each(function(){
                            		$(this).uploadify('destroy'); 
                            	});
          	                    e.preventDefault();
          	                    if($(this).attr("href")){//已通章节可点
          	                        loadRightCont($(this).attr("data-fdid"),$(this).attr("data-type"));
          	                        $(this).parent().addClass("active").siblings().removeClass("active");
          	                    }
          	                  $("#cordPage").removeClass("hide");
          	                });
          				
          			}
          		}); 
                //$.post("url",{id: $mediaToolbar.attr("data-fdid")})
            });
            
        }
       

        /*测试页加载完成后执行方法*/
        function afterLoadExamOrTaskPage(data){

            var $window = $(window);

            $("#listExamPaper>li>a").click(function(e){
            	var $athis = $(this);
                e.preventDefault();
                if($(this).parent().siblings().find(".collapse").hasClass("in")){
                	 var $this2 = $(this);
                 	$this2.attr("data-toggle","");
                 	var fdType = $(this).attr("data-fdType");
                	var title = "确定要关闭未保存的试卷？";
                    if(fdType=="task"){
                    	title = "确定要关闭未保存的作业包？";
                    }
                 	jalert(title,function(){
                 			if($(this).hasClass("in")){
                 				$(this).collapse("hide");
                 			}
                 			var fdType = $(this).attr("data-fdType");
                            alert(fdType);
                 		$this2.attr("data-toggle","collapse");
                 		$athis.trigger("click");
                 		$("#goTop").trigger("click");
                 	});
                }
                
            });
          

            //试卷列表折叠 手风琴事件
            $("#listExamPaper>li>.collapse")
                    .bind("show",function(){
                    	
                        var $this = $(this);
                        var tempData = {};
                        $this.prev(".titBar").addClass("hide");
                        $.ajax({
                        	  type:"post",
         		  			  url: "${ctx}/ajax/passThrough/getSubInfoByMaterialId",
         		  			  async:false,
         		  			  data:{
         		  				materialId: $this.attr("data-fdid"),
         		  				catalogId:catalogId,
         		  				bamId:bamId,
         		  				sourceType:fdMtype
         		  			  },
         		  			  dataType:'json',
         		  			  success: function(result){
         		  				tempData=result;
         		  				//alert(JSON.stringify(result.examPaperStatus));
         		  			  }
         		  			  
         	  			});
                        tempData.type = data.type;
                        tempData.num = $this.parent().index() + 1;
                        tempData.examCount = tempData.listExam.length;
                        var count = 0;
                        for( var j in tempData.listExam){
                          if(tempData.type=="task"){
                        	  if(tempData.listExam[j].status == "success"){
                                  count++;
                              }
                        	  if(tempData.listExam[j].status == "error"){
                        		  count++;
                        	  }
                          }else{
                        	  if(tempData.listExam[j].status == "success"){
                                  count++;
                              }  
                          }
                        }
                        tempData.successCount = count;


                        //$("#headToolsBar").html(examPaperStatusBarFn(tempData));
                        tempData.action = "submitExamOrTask";
                        tempData.bamId=bamId ;
                        tempData.catalogId=catalogId ;
                        tempData.courseId=courseId;
                        tempData.fdMtype=fdMtype ;
                        var date = new Date();
                        //tempData.startTime=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
                        tempData.startTime=date.getTime();
                        $this.html(examPaperDetailFn(tempData));
                        //试题列表序号控制
                        $("#navExams>.num").click(function(e){
                            e.preventDefault();
                            var $this = $(this);
                            var id = $this.attr("href");
                            $("html,body").animate({scrollTop: $(id).offset().top - $("#pageHeader").height() - 60},"fast","swing");
                        });/* 
                                .tooltip({
                                    placement: "bottom"
                                }); */
                        $this.find("[data-toggle='collapse']").click(function(e){
                        	var $this2 = $(this);
                        	$this2.attr("data-toggle","");
                        	var fdType = $(this).attr("data-fdType");
                        	var title = "确定要关闭未保存的试卷？";
                            if(fdType=="task"){
                            	title = "确定要关闭未保存的作业包？";
                            }
                        	jalert(title,function(){
                        		$this2.attr("data-toggle","collapse");
                        		$this.collapse("hide");
                        		$("#goTop").trigger("click");
                        	});
                        	
                        })
                        
				
                        $("button[name='answerAtt']").each(function(){
                        	//var fileid = $(this).attr('id'); 
                        	var temp = "#listTaskAttachment_"+$(this).attr('id')+"";
                        	 $(temp).delegate("li .icon-remove-blue","click",function(e){
                                 e.preventDefault();
                                 $(this).closest("li").remove();
                             });
                        	
                         });

                    })
                    .bind("shown",function(){
                        var $this = $(this);
                        var pos = $this.parent("li").offset();
                        var sTop = 0;
                        //var $pageHead = $("#pageHeader");
                            sTop = pos.top - 60 - 60;
                        $("html,body").animate({scrollTop: sTop},pos.top-sTop,"swing");
                        
                        var flag = true,pct,interval,countdown = 0,byteUped = 0;
                        $("button[name='answerAtt']").each(function(){
                        	var fileid = $(this).attr('id');
                        	var $txt = $('#'+fileid).prev(".txt"),
                        	$progress = $txt.prev(".progress").children(".bar"),
                            $pct = $txt.children(".pct"),
                            $countdown = $txt.children(".countdown");
                        	$('#'+fileid).uploadify({
                        		'height' : 40,
                                'width' : 68,
                                'multi' : false,
                                'simUploadLimit' : 1,
                                'swf' : '${ctx}/resources/uploadify/uploadify.swf',
                                "buttonClass": "btn btn-primary btn-large",
                                'buttonText' : '上传',
                                'uploader' : '${ctx}/common/file/o_upload',
                                'auto' : true,
                                'fileTypeExts' : '*.*',
                                'fileSizeLimit':20971520,// 限制文件大小为2G
                                'onInit' : function(){
                                	$("#upMaterial").next(".uploadify-queue").remove();
                                },
                                'onUploadStart' : function (file) {},
                                'onUploadSuccess' : function (file, data, Response) {
                                    if (Response) {
                                    	$countdown.text("00:00:00");
                                    	$progress.width("0");
                                    	$pct.text("0%");
                                        var objvalue = eval("(" + data + ")");
                                        var html = "<li id='attach"+objvalue.attId+"'><input type='hidden' value='"+objvalue.attId+"' name='attach_"+fileid+"' id='answerAttId_"+fileid+"'><a class='name'><i class='icon-paperClip'></i>"
                                        +objvalue.fileName+"</a><div class='item-ctrl'><a href='#' class='icon-remove-blue'></a></div></li>";
                                        $("#listTaskAttachment_"+fileid).append(html);
                                    }
                                },
                                'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                                	pct = Math.round((bytesUploaded/bytesTotal)*100)+'%';
                                	byteUped = bytesUploaded;
                                	if(flag){
                                		interval = setInterval(uploadSpeed,100);
                                		flag = false;
                                	}
                                	if(bytesUploaded == bytesTotal){
                                		clearInterval(interval);
                                	}
                                	
                                	$progress.width(pct);
                                	$pct.text(pct);
                                	countdown>0 && $countdown.text(secTransform((bytesTotal-bytesUploaded)/countdown*10));
                                }
                            });
        						
        				});
                        function uploadSpeed(){
                    		countdown = byteUped - countdown;
                    	}
                    	function secTransform(s){
                    		if( typeof s == "number"){
                    			s = Math.ceil(s);
                    			var t = "";
                    			if(s>3600){
                    				t= completeZero(Math.ceil(s/3600)) + ":" + completeZero(Math.ceil(s%3600/60)) + ":" + completeZero(s%3600%60) ;
                    			} else if(s>60){
                    				t= "00:" + completeZero(Math.ceil(s/60)) + ":" + completeZero(s%60) ;
                    			} else {
                    				t= "00:00:" + completeZero(s);
                    			}
                    			return t;
                    		}else{
                    			return null;
                    		}		
                    	}
                    	function completeZero(n){
                    		return n<10 ? "0"+n : n;
                    	}
                    	
                        $("#formExam").validate({
                            submitHandler: function(form){
                            	var type=$("#formExam [name='fdMtype']").val();

                            	var undoNo = 0;
                            	for(var i=0;i<$("#listExamPaper li").length;i++){
                            		if($("#listExamPaper li:eq("+i+")").has('form').length==0&&$("#listExamPaper li:eq("+i+") .icon-state-bg:contains('待答')").length==1){
                            			undoNo++;
                            		}
                            	}
                            	var examName=$(".accordion-inner .hd .examName").html();
                            	$("#goTop").trigger("click");
                  				if(type=="08"){
                  					if(undoNo==0){
                  						jalert_tipst("恭喜您完成本节中的全部试卷，请检查每套试卷的批改状态。试卷全部通过以后，请点击右上方的“下一节”按钮继续学习。",4);
                  					}else{
                  						jalert_tipst("您刚刚完成了“"+examName+"”的作答，请继续提交本节中剩余的"+undoNo+"个试卷",4);
                  					}
                  				}else{
                  					if(undoNo==0){
                  						jalert_tipst("恭喜您完成本节中的全部作业，请耐心等待导师批改。导师全部批改通过以后，您将直接进入下一节继续学习。",4);
                  					}else{
                  						jalert_tipst("您刚刚完成了“"+examName+"”的作答，请继续提交本节中剩余的"+undoNo+"个作业",4);
                  					}
                  				}
	                            $("input[putId='examAnswer_completion']").each(function(){
	                            	$(this).nextAll("input:first").val($(this).attr("exam-id")+":"+$(this).attr("value"));
	                           	});
	                            $("input[putId='examAnswer_s']").each(function(){
	                            	if($(this).is(":checked")){
	                            		$(this).nextAll("input:first").val($(this).attr("exam-id")+":"+$(this).attr("value"));
	                            	}else{
	                            		$(this).nextAll("input:first").attr("name","err");
	                            	}
	                            	
	                           	});
	                            form.submit();
                            }
                        });
                    })
                    .bind("hide",function(){
                    	$(".uploadify").each(function(){
                    		$(this).uploadify('destroy'); 
                    	});
                        $(this).empty().prev(".titBar").removeClass("hide");
                        $("#headToolsBar").empty();
                        $("#listExamPaper>li>a").each(function(){
                            if($(this).attr("data-toggle") == ""){
                                $(this).attr("data-toggle","collapse");
                            }
                        });
                    });
        }
        function loadvideoOrDoc(data){
        	if(data.code!=""&&data.code.type!='none'){
	           	   if(data.code.type=='doc'&&data.code.fileNetId!=null){//文档
	           		$("#iframeVideo").removeClass("hide").attr("src",'${ctx}/viewer/index.jsp?fileName=' +  data.code.fName).next().remove();
	           		// $("#iframeVideo").removeClass("hide").attr("src",'http://me.xdf.cn/iportal/sys/attachment/sys_att_swf/viewer.do;jsessionid=ubFBr_W9GMSBzUvrtu3cqdX?method=viewerOtp&fdId='+data.code.fileNetId+'&seq=0&type=otp&fileName='+ data.code.fName).next().remove();;
	           	   }else if(data.code.type=='video'&&data.code.playCode!=null){//视频
	           		   $("#iframeVideo").removeClass("hide").attr("src",'${ctx}/video.jsp?code=' + data.code.playCode).next().remove();
	           	   }else{
	           		   if(!$("#iframeVideo").siblings().hasClass("media-placeholder")){
	           			$("#iframeVideo").attr("src","").addClass("hide").after("<div class='media-placeholder'>正在转换中......</div>"); 
	           		   }
	            			
	               }
              }else{
            	    if(!$("#iframeVideo").siblings().hasClass("media-placeholder")){
           				 $("#iframeVideo").attr("src","").addClass("hide").after("<div class='media-placeholder'>素材不存在......</div>");
            	    }
              } 
        }
        /* //该页面在ie下placeholder不能正常提示
		function initplaceholder(mark){
        	if(!mark){
	        	if($("#textComment").val()==""){
	        		$("#textComment").val("有什么想吐槽的吗？随便说两句吧~ : )");
	        	}
        	}else{
        		if($.trim($("#textComment").val())=="有什么想吐槽的吗？随便说两句吧~ : )"){
	        		$("#textComment").val("");
	        	}
        	}
        } */
    });



</script>

</body>
</html>
