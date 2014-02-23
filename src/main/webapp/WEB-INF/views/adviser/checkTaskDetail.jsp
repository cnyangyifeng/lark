<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="cn.me.xdf.utils.DateUtil"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String time = DateUtil.getCurrentDate();
request.setAttribute("time", time);
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/iAmMentor.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->

    <!--老师介绍模板-->
    <script id="teacherIntroTemplate" type="x-dot-template">
        {{#def.media:it}}
        {{##def.media:item:
        <div class="media" >
            <div class="pull-left">
                <a href="${ctx}/course/courseIndex?userId={{=item.user.link}}" class="face" target="_blank">
                    <img src="{{?item.user.imgUrl.indexOf('http')>-1}}{{=item.user.imgUrl}}{{??}}${ctx}/{{=item.user.imgUrl}}{{?}}" class="media-object img-polaroid" alt="头像"/>
                </a>
                <a href="javascript:void(0)"  class="send msg" onclick="sendLetter('{{=item.user.flag}}','{{=item.user.link}}');"><i class="icon-msg"></i>发私信</a>
                <a href="mailto:{{=item.user.mail}}" class="send" ><i class="icon-envelope"></i>发邮件</a>
            </div>
            <div class="media-body">
                <div class="media-heading">
                    <span class="name">{{=item.user.name}}</span>
                    <span class="muted"> {{=item.user.department}}</span>
                                            <span class="muted right">
                                                <i class="icon-tel"></i>{{=item.user.phone || ''}}
                                                <i class="icon-envelope"></i>{{=item.user.mail}}
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
            </div>
        </div>
        #}}
    </script>

    <!--成绩条模板-->
    <script id="resultsBarTemplate" type="x-dot-template">
        <div class="listTeacher section">
            <div class="media-foot">
                <div class="statebar">作业包总分 <strong>{{=it.fullScore}}</strong>  分<em>|</em>及格分 <strong>{{=it.scorePass}}</strong>  分
                    <em>|</em>当前批改的总得分 <strong id="totalScore">{{=it.score}}</strong>  分</div>
                <span class="isPass{{?it.status == 'pass'}} pass">通过{{??}}">未通过{{?}}</span>
            </div>
        </div>
    </script>

    <!--作业包详情模板-->
    <script id="taskDetailTemplate" type="x-dot-template">
        <div class="taskDetail section">
            <div class="hd">
                <h2>作业包 {{=it.num}} {{=it.name}} 共计 <span class="total"><strong>{{=it.taskCount}}</strong></span> 个作业，满分 <strong>{{=it.fullScore}}</strong> 分，及格分 <strong>{{=it.scorePass}}</strong> 分。</h2>
                <p class="muted">{{=it.intro.replace(new RegExp('\n','g'),'<br>') || ''}}</p>
            </div>
            <form action="{{=it.action || '#'}}" post="post" id="formTask">
                <input name="taskId" id="taskId" value="{{=it.id}}" type="hidden" />
                {{~it.listTask :task1:index2}}
                {{~it.listTask :task}}
                {{?index2 == task.index}}
                <div class="bd" id="task{{=index2+1}}">
                    <div class="pd20">
                        <div>{{=index2+1}}. {{=task.name}}（{{=task.totalScore}}分）</div>
<br/>
                        <div class="intro">{{=task.stem.replace(new RegExp('\n','g'),'<br>') || ''}}</div>
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
                    <div class="dashed-t1 taskAnswer pd20">
                        <label>作业</label>
                        {{?task.status == "null"}}
                            <span class="muted">未作答</span>
                        {{??}}
                            {{?task.type == 'uploadWork'}}
                            <ul class="attachList unstyled" >
                                {{~task.listTaskAttachment :att2}}
                                <li id="attach{{=att2.id}}">
                                    <span><i class="icon-paperClip"></i><span class="name">{{=att2.name}}</span></span>
                                    <div class="pos-r">
                                        {{?att2.type == "onlinePlay"}}
                                        <a class="read" href="{{=att2.mediaUrl}}" playType="{{=att2.mtype}}" {{?att2.mtype=="01"}} playCode="{{=att2.playCode}}"{{??}}fileNetId="{{=att2.fileNetId}}" fname="{{=att2.fName}}" {{?}}><i class="icon-eye blue"></i>在线阅读</a>
                                        <em>|</em>
                                        {{?}}
                                        <a onclick="downloadMater('{{=att2.id}}','{{=att2.fileNetId}}');" href="javascript:void(0)" class="download"><i class="icon-downloadbox blue"></i>下载</a>
                                    </div>
                                </li>
                                {{~}}
                            </ul>
                            {{??task.type == 'onlineAnswer'}}
                               {{=task.answer.replace(new RegExp('\n','g'),'<br>') || ''}}
                            {{?}}
                        {{?}}
                    </div>
                    <div class="dashed-t1 pd20 ratingBox" id="{{=task.id}}">
                        {{?task.status == "unchecked"}}
							<label >作业评分</label>
							<div class="box-score">
                                {{#def.timeLine:task.totalScore}}
                                <input type="hidden" value="0" name="taskScore" />
                            </div>
                            <label >说点什么</label>
                            {{#def.richText}}
                            <div class="clearfix">
                               <button class="btn btn-primary btn-large pull-right"  type="button">确认评分</button>
                            </div>
                        {{??}}
                            <label >作业评分</label>
                            <div class="box-score">
                                <div class="text-info"><span class="num">{{?task.status == "null"}}0{{??}}{{=task.rating.score}}{{?}}</span>分</div>
                            	{{?it.status == "unfinish" && task.status != "null"}}
									{{#def.timeLineOnChecked:task}}
                                	<input type="hidden" value="{{?task.status == "null"}}0{{??}}{{=task.rating.score}}{{?}}" name="taskScore" />
								{{?}}
							</div>
                            <label >说点什么</label>
                            <div class="box-comm">
                                {{?task.status == "null"}}
                                    <span class="muted">无话可说，此题未作答！</span>
                                {{??}}
                                    {{?task.rating.comment}}
                                    <div class="comment">{{=task.rating.comment}}</div>
                                    <div class="time"><i class="icon-time"></i>{{=task.rating.time}}</div>
                                    {{??}}
                                        <span class="muted">无评语...</span>
                                    {{?}}
                                {{?}}
                            </div>
                            {{?it.status == "unfinish" && task.status != "null"}}
                            <div class="clearfix">
                                <button class="btn btn-primary btn-large pull-right" type="button">重新批改</button>
                            </div>
                            {{?}}
                        {{?}}
                    </div>
                </div>
                {{?}}
                {{~}}
                {{~}}
                {{?it.status == "unfinish"}}
                    <div class="ft">
                        <div class="scoreBar">
                            作业包总分 <strong>{{=it.fullScore}}</strong>  分<em>|</em>及格分 <strong>{{=it.scorePass}}</strong>  分<em>|</em>当前批改的总得分 
                         <strong id="nowScore">{{=it.score}}</strong>  分
                        </div>
                        <button class="btn btn-primary btn-large" type="submit">提交全部批改</button>
                    </div>
                {{?}}
            </form>

        </div>
        {{##def.timeLine:total:
        <div class="timeLine" fdStatus="0">
            <div class="num hide">0</div>
			{{?total==1}}
            	{{ for(var i=1; i <= total*10; i++){ }}
            	<a title="{{=i*it.timeLine.span/10}}{{=it.timeLine.unit || ''}}" style="width: {{=(it.timeLine.width-total*10-1)/total/10}}px"
               		class="{{?i*it.timeLine.span==it.timeLine.span}}first {{?}}{{?it.timeLine.curPos && i*it.timeLine.span<=it.timeLine.curPos}}active{{?}}"><span class="num hide">{{=i*it.timeLine.span/10}}</span></a>
            	{{ } }}
			{{??}}
				{{ for(var i=1; i <= total; i++){ }}
            	<a title="{{=i*it.timeLine.span}}{{=it.timeLine.unit || ''}}" style="width: {{=(it.timeLine.width-total-1)/total}}px"
               		class="{{?i*it.timeLine.span==it.timeLine.span}}first {{?}}{{?it.timeLine.curPos && i*it.timeLine.span<=it.timeLine.curPos}}active{{?}}"><span class="num hide">{{=i*it.timeLine.span}}</span></a>
            	{{ } }}
			{{?}}
        </div>
        #}}

		{{##def.timeLineOnChecked:total:
        <div class="timeLine onChecked" fdStatus="1">
            <div class="num hide">0</div>
			{{?total.totalScore==1}}
            	{{ for(var i=1; i <= total.totalScore*10; i++){ }}
            	<a title="{{=i*it.timeLine.span/10}}{{=it.timeLine.unit || ''}}"  style="width: {{=(it.timeLine.width-total.totalScore*10-1)/total.totalScore/10}}px"
               		class="{{?i*it.timeLine.span==it.timeLine.span}}first {{?}}{{?it.timeLine.curPos && i*it.timeLine.span<=it.timeLine.curPos || i*it.timeLine.span<=total.rating.score*10}}active{{?}}"><span class="num hide">{{=i*it.timeLine.span/10}}</span></a>
            	{{ } }}
			{{??}}
				{{ for(var i=1; i <= total.totalScore; i++){ }}
            	<a title="{{=i*it.timeLine.span}}{{=it.timeLine.unit || ''}}"  style="width: {{=(it.timeLine.width-total.totalScore-1)/total.totalScore}}px"
               		class="{{?i*it.timeLine.span==it.timeLine.span}}first {{?}}{{?it.timeLine.curPos && i*it.timeLine.span<=it.timeLine.curPos || i*it.timeLine.span<=total.rating.score}}active{{?}}"><span class="num hide">{{=i*it.timeLine.span}}</span></a>
            	{{ } }}
			{{?}}
        </div>
        #}}
    </script>

    <!-- 富文本输入框 模板-->
    <script id="richTextTemplate" type="x-dot-template">
        <div class="boxRichText">
            <textarea rows="3" class="input-block-level" name="comment" >{{=it.comm || ''}}</textarea>
        </div>
    </script>

    <!-- 导师评语 模板-->
    <script id="boxCommentTemplate" type="x-dot-template">
        <div class="box-comm">
            {{?it.comment}}
                <div class="comment">{{=it.comment}}</div>
                <div class="time"><i class="icon-time"></i>{{=it.time}}</div>
            {{??}}
                <span class="muted">导师很懒，没有写评语。</span>
            {{?}}
        </div>
    </script>

    <!-- 导师评语 模板-->
    <script id="mediaPlayerTemplate" type="x-dot-template">
        <div class="md-media" id="mediaPlayer">
            <div class="bd2">
                <div id="flashcontent">
					<iframe width="100%" height="510" id="iframeVideo" src="" frameBorder="0" scrolling="no"></iframe>
                </div>
            </div>
            <div class="ft2 clearfix">
                <div class="pull-left">
                    当前附件：<span class="name"><i class="icon-paperClip"></i>{{=it.name}}</span>
                </div>
                <div class="pull-right">
                    <button class="btn btn-primary btn-large" type="button">关闭浏览</button>
                </div>
            </div>
        </div>
    </script>

    <!--批改题卡模板-->
    <script id="taskNumListTemplate" type="x-dot-template">
        <div class="section nav-task">
            <div class="hd">
                批改题卡
            </div>
            <div class="bd" id="navTask">
                {{~it :task1:index1}}
                {{~it :task}}
                {{?index1 == task.index}}
                <a class="num {{?task.status == 'checked'}} active" title="已批改"{{??task.status == 'unchecked'}}" title="未批改"{{??task.status == 'null'}} active" title="未作答"{{?}} href="#task{{=index1+1}}">
                {{=index1+1}}
                </a>
                {{?}}
                {{~}}
                {{~}}
            </div>
        </div>
    </script>


    <script src="${ctx}/resources/js/doT.min.js" type="text/javascript"></script>
</head>

<body>
<!--头部 S-->


<!--主体 S-->
<section class="container">	
		<div class="clearfix mt20">
	        <div class="pull-left w760">
                <div class="page-header">
                    <a href="${ctx}/adviser/checkTask?order=fdcreatetime&fdType=${param.fdType}">返回批改作业首页</a>
                    <div class="pos-r">
                        <button class="btn btn-primary btn-large" id="downloadBox" type="button">打包下载</button>
                    </div>
                </div>
                <div class="page-body">
                    <section class="listTeacher" id="teacherIntro">
                    </section>
                    <section id="taskDetail">

                    </section>
                </div>
			</div>
			<div class="pull-right w225">
                <div class="section">
                        <!--指导老师页面 -->
			       <c:import url="/WEB-INF/views/studyTrack/divuserimg.jsp"></c:import>
                </div>
                <div id="affixRight" class="mt20" data-spy="affix" data-offset-top="384">
                </div>
	        </div>
        </div>
<input type="hidden" id="time" value="${time}">
<!--底部 E-->
</section>
<!--主体 E-->
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
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
function sendLetter(flag,fdId){
     if(flag=="true"){
     	jalert("您不能给自己发私信！");	
     }else{
     	window.open("${ctx}/letter/letterDetail?fdId=" + fdId,"_blank");
     } 
}
</script>
<script type="text/javascript">
    $(function(){
        /*作业包介绍模板函数*/
       var teacherIntroFn = doT.template(document.getElementById("teacherIntroTemplate").text);

       var teacherIntroData = {};
       
       var noteId  = "${param.noteId}";
       
       var attIds = {};
       //找出批改作业详情 顶部div信息
       if(noteId!=''){
    	   $.ajax({
   			url : "${ctx}/ajax/adviser/findCourseAndUser",
   			async : false,
   			data : {
   				"noteId" : noteId,
   			},
   			dataType : 'json',
   			success : function(result) {
   				teacherIntroData = result;
   			}
   		});
    	   
       }
       
       $("#downloadBox").bind("click",function(){
    	   downloadAtt();
		});
       function downloadAtt(){
    	   ///找当前作业包的附件信息
      	 $.ajax({
      		url : "${ctx}/ajax/adviser/findAttsBySoureceId/"+noteId,
      		async : true,
      		dataType : 'json',
      		success : function(result) {
      			if(result.attIds==null||result.attIds==''){
      				 jalert("没有数据可以打包！");
      				   return;
      			}else{
      				jalert("您确定下载本作业包作业附件吗？",function(){
      					var course = result.course;
      					var log = result.log;
      					var info = result.info;
      					var person = result.person;
        			    window.location.href= window.location.href="${ctx}/common/file/downloadZipsByArrayIds/"+result.attIds+"/"+course+"-"+log+"-"+info+"-"+person+"-"+$("#time").val();
        				return;
        			});
      			}
      		}
      	  }); 
			
		}
	   
		$("#teacherIntro").html(teacherIntroFn(teacherIntroData));
		var taskDetailFn = doT.template(document
				.getElementById("taskDetailTemplate").text, undefined, {
			richText : document.getElementById("richTextTemplate").text
		});
		var richTextFn = doT.template(document
				.getElementById("richTextTemplate").text);
		var boxCommentFn = doT.template(document
				.getElementById("boxCommentTemplate").text);
		var taskNumListFn = doT.template(document
				.getElementById("taskNumListTemplate").text);
		var resultsBarFn = doT.template(document
				.getElementById("resultsBarTemplate").text);
		var mediaPlayerFn = doT.template(document
				.getElementById("mediaPlayerTemplate").text);

		var $window = $(window);
		var taskData = {};
		loadTaskDetail();
		function loadTaskDetail() {
			//作业详细信息
			 $.ajax({
				    type:"post",
					url : "${ctx}/ajax/adviser/findCheckTaskDetail",
					async : false,
					cache :false,
					data : {
						"noteId" : noteId,
					},
					dataType : 'json',
					success : function(result) {
						taskData = result;
						//alert(JSON.stringify(taskData.listTask[0].rating.score));
					}
				});
			
			taskData.timeLine = {//时间轴控件 配置数据
				width : 678, //时间轴控件 宽度
				curPos : 0, //当前位置
				span : 1, //每格的进制
				unit : "分"
			}
			$("#taskDetail").html(taskDetailFn(taskData)).find(".timeLine>a")
					.click(
							function(e) {
								e.preventDefault();
								$(this).prevAll("a").add(this).addClass(
										"active");
								$(this).nextAll("a").removeClass("active");
								$(this).parent().next(":hidden").val(
										$(this).children(".num").text());
							}).tooltip();
			
			$("#taskDetail").find(".timeLine.onChecked>a").unbind("click");
			
			if (taskData.status != "unfinish") {
				var results = resultsBarFn(taskData);
				$("#taskDetail").append(results).prepend(results);
			}
			$("#affixRight").html(taskNumListFn(taskData.listTask));

			//作业列表序号控制
			$("#navTask>.num").click(function(e) {
				e.preventDefault();
				var $this = $(this);
				var id = $this.attr("href");
				$window.scrollTop($(id).offset().top - 60);
			}).tooltip({
				placement : "top"
			});
			var $form = $("#formTask");
			var validator = $form
					.validate({
						/* rules : {
							taskScore : {
								min : 1
							}
						},
						messages : {
							taskScore : {
								min : "请为此题打分"
							}
						}, 
						ignore : "",*/
						submitHandler : function(form) {
							$("#formTask .timeLine").each(function(){
							  if($(this).attr("fdStatus")=="0"){
								$(this).parent().nextAll(".clearfix").children(".btn-primary").after('<label class="error pull-right">请批改该作业！</label>');
							  }
							});
							var flag = true;
							$("#formTask .timeLine").each(function(){
								 if($(this).attr("fdStatus")=="0"){
									$(this).parent().nextAll(".clearfix").children(".btn-primary").focus(); 
									flag = false;
									return;
								 }
							});
						    if(!flag){
						    	return;
						    }
							taskData.status = taskData.score < taskData.scorePass ? "fail" : "pass";
							//$.post()保存soursenote
						   $.ajax({
								type:"post",
								url : "${ctx}/ajax/adviser/updateSourseNote/"+noteId,
								async : false,
								cache :false,
								success : function(result) {
									$window.scrollTop($("#taskDetail").offset().top - 60);
									loadTaskDetail();
								}
							}); 
							
						}
					});
			$form
					.find(".bd .ratingBox .btn-primary")
					.click(
							function(e) {
								var $this = $(this);
								$(this).text("确认评分");
								var $boxComm = $this.parent().prev();
								var $boxScore = $boxComm.prevAll(".box-score");
								if ($boxComm.hasClass("boxRichText")) {
									var txt = $boxComm.children("textarea")
											.val();
									var score = $boxScore.find(
											".text-info>.num").text();
									var hsiscore = 0;
									if($boxScore.find(".text-info .num").length){
										hsiscore = score;
									 }
									
									if ($boxScore.find(".timeLine").length) {
										score = $boxScore.children(
												"[name='taskScore']").val();
										 if (validator
												.element($boxScore
														.children("[name='taskScore']"))) {
											 $boxScore.find('.text-info').remove();
											 var boxScore = $boxScore.html();
											 $boxScore
													.html('<div class="text-info"><span class="num">'
															+ score
															+ '</span>分</div>'+boxScore); 
															
											taskData.score = score;
											var fdid = $boxScore.parent(
													".ratingBox").attr("id");
											for ( var i = 0 in taskData.listTask) {// for test
												if (taskData.listTask[i].id == fdid) {
													taskData.listTask[i].rating = {
														score : score,
														comment : txt,
														time : "一分钟前"
													}
													taskData.listTask[i].status = "checked";
												}
											}
											//$.post("url",{id: fdid, score: taskData.score, comment: txt});
											//提交每一到题目的情况
											  $.ajax({
												type:"post",
												url : "${ctx}/ajax/adviser/updateTaskRecord/"+fdid,
												async : false,
												cache :false,
												data : {
													"score" : taskData.score,
													"fdComment" : txt,
												},
												success: function(result){
													$this.next(".error").remove();
													$("#navTask>a").eq(result).addClass("active").attr("data-original-title","已批改");
												}
											});  
											 var total = $("#nowScore").text();
											 total = new Number(parseFloat(total) - parseFloat(hsiscore) + parseFloat(taskData.score)).toFixed(1);
											 $("#nowScore").text(total);
										}
										$boxScore.find(".timeLine>a").unbind("click");
										$boxScore.find(".timeLine").attr("fdStatus",1);
									 } 
									if (score) {
										$boxComm.after(boxCommentFn({
											comment : txt,
											time : "刚刚"
										})).remove();
										$(this).text("重新批改");
									}
								} else if ($boxComm.hasClass("box-comm")) {
									$boxScore.find(".timeLine>a").click(
											function(e) {
												e.preventDefault();
												$(this).prevAll("a").add(this).addClass(
														"active");
												$(this).nextAll("a").removeClass("active");
												$(this).parent().next(":hidden").val(
														$(this).children(".num").text());
											}).tooltip();
									$boxScore.find(".timeLine").attr("fdStatus",0);
									$boxComm.after(
											richTextFn({
												comm : $boxComm.children(
														".comment").text()
											})).remove();
								}

							});
			$form.find(".taskAnswer>.attachList>li").each(function() {
				var $this = $(this);
				var $list = $this.parent();
				$this.find(".pos-r>.read").click(function(e) {
					e.preventDefault();
					loadVideo($(this), $list);
				})
			});
		}
		
		function loadVideo($ele, $list) {
			playUrl = $ele.attr("href");
			mtype=$ele.attr("playType");
			if ($("#mediaPlayer").length) {
				$("#mediaPlayer").remove();
			}
			$list.before(mediaPlayerFn({
				name : $ele.parent().prev().children(".name").text()
			}));
			//$("#iframeVideo").attr("src","");
			if(mtype=='04'||mtype=="05"){//文档
          		 $("#iframeVideo").attr("src",'http://me.xdf.cn/iportal/sys/attachment/sys_att_swf/viewer.do;jsessionid=ubFBr_W9GMSBzUvrtu3cqdX?method=viewerOtp&fdId='+$ele.attr("fileNetId")+'&seq=0&type=otp&fileName='+$ele.attr("fileName"));
          	   }else if(mtype=='01'){//视频
          		   $("#iframeVideo").attr("src",'${ctx}/video.jsp?code=' + $ele.attr("playCode"));
          	   }
			var $player = $("#mediaPlayer");
			$window.scrollTop($player.parent().offset().top - 60);
			$player.find(".ft2 .btn-primary").click(function() {
				//$("#iframeVideo").contents().find("object").empty();
				$player.remove();
			})
		}

	});
</script>
</body>
</html>
