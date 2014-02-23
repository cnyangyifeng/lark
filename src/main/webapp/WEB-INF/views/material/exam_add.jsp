<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->

<!-- 授权管理 用户列表 模板 -->
<script id="listUserKinguserTemplate" type="text/x-dot-template">
    <tr data-fdid="{{=it.id}}">
        <td class="tdTit">
            <div class="pr">
                <div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
			<img src="{{?it.imgUrl.indexOf('http')>-1}}{{=it.imgUrl}}{{??}}${ctx}/{{=it.imgUrl}}{{?}}" />{{=it.name}}（{{=it.mail}}），{{=it.org}} {{=it.department}}
            </div>
        </td>
       <td><input type="checkbox" {{?it.tissuePreparation!=false}}checked{{?}}  class="tissuePreparation" /></td>
        <td><input type="checkbox" {{?it.editingCourse!=false}}checked{{?}} class="editingCourse" /></td>
        <td><a href="#" class="icon-remove-blue"></a></td>
    </tr>
</script>

<!-- 试题详情编辑 模板 -->
<script id="examDetailTemplate" type="text/x-dot-template">
    <div class="page-header">
        <a href="${ctx}/material/materialAddFoward?fdId=${param.fdId}&fdType=${param.fdType}" class="backParent">返回当前试卷</a>
        <h4>{{=it.examPaperName}}</h4>
        <div class="btn-group">
            <button class="btn btn-large btn-primary" id="saveExam" type="button">保存</button>
            <button class="btn btn-white btn-large " id="delExam" type="button">删除</button>
        </div>
    </div>
    <div class="page-body editingBody">
        <form action="{{=it.action || '#'}}" id="formEditDTotal" class="form-horizontal" method="post">
            <section class="section">
                <div class="control-group">
                    <label class="control-label" >题型设置</label>
                    <div class="controls">
                        <input name="examType" id="examType" value="{{=it.examType || 'multiple'}}" type="hidden" />
                        <div class="btn-group btns-radio" data-toggle="buttons-radio">
                            <button class="btn btn-large{{?!it.examType || it.examType == 'multiple'}} active{{?}}" id="multiple" type="button">多项选择题</button>
                            <button class="btn btn-large{{?it.examType == 'single'}} active{{?}}" id="single" type="button">单项选择题</button>
                            <button class="btn btn-large{{?it.examType == 'completion'}} active{{?}}" id="completion" type="button">填空题</button>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="examStem">试题题干</label>
                    <div class="controls"><textarea placeholder="{{?!it.examType || it.examType == 'multiple' || it.examType == 'single'}}请详细填写试题内容{{??}}请使用#...#标记填空题的答案，例如：发现相对论的是#爱因斯坦#先生{{?}}" rows="4"
                                                    class="input-block-level" required id="examStem"
                                                    name="examStem">{{=it.examStem || ''}}</textarea><label id="examStemErr" for="examStem" class="error"></label>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" >试题分值 <small>(单位分)</small></label>
                    <div class="controls">
						<input id="examScore" digits="true" max="100" min="0" required="true" value="{{=it.examScore || '1'}}" class="input-block-level" name="timeLine" type="text">
                       <!-- <input name="examScore" id="examScore" value="{{=it.examScore || ''}}" type="hidden"/>
                            <div class="timeLine scoreLine">
                                <div class="num">0</div>
                                {{ for(var i=1; i<=20; i++){ }}
                                <a title="{{=i}}分" href="#" style="width: {{=(670-20-1)/20 + 'px'}}"
                                   class="{{?i==1}}first {{?}}{{?it.examScore && i<=it.examScore}}active{{?}}"><span class="num">{{=i}}</span></a>
                                {{ } }}
                            </div> --> 
					<label id="questionScoreErr" class="error" style="display: none;"></label>
                    </div>
                </div>
            </section>
            <section class="section mt20">
                <label>辅助材料（上传辅助材料，建议小于2G）</label>
                <div class="control-upload">
                   <div class="upload-fileName" id="attName"></div>
                   <div class="pr">
                     <span class="progress"> <div class="bar" style="width:0;"></div> </span>
					 <span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
                     <button id="upMaterial" class="btn btn-primary btn-large" type="button" >上传</button>                   
                   </div>
                   <input type="hidden"  name="attId" id="attId">

                </div>
                <ul class="unstyled list-attachment" id="listAttachment">
                    {{~it.listAttachment :att:index}}
                        {{~it.listAttachment :att2:index2}}
                            {{?index == att2.index}}
                                {{#def.item:att2}}
                            {{?}}
                        {{~}}
                    {{~}}
                </ul>
            </section>
            {{#def.examAnswer}}
            <button class="btn btn-block btn-submit btn-inverse" type="button" id="saveTestQuestions">保存</button>
        </form>
    </div>
</script>

<!-- 试题详情 列表项 模板 -->
<script id="itemExamDetailTemplate" type="text/x-dot-template">
        {{?it.flag === "add"}}
             {{#def.item:it}}
        {{?}}
        {{##def.item:param:
            <li data-fdid="{{=param.id || 'temp'}}">
                <div class="state-dragable"><span class="icon-bar"></span><span
                        class="icon-bar"></span><span class="icon-bar"></span><span
                        class="icon-bar"></span><span class="icon-bar"></span></div>
                <{{?param.url}}a href="${ctx}/common/file/download/{{=param.url}}"{{??}}div{{?}} class="name">{{=param.name}}</{{?param.url}}a{{??}}div{{?}}>
                <div class="item-ctrl">
                    {{?param.isAnswer != undefined}}
                    <label class="{{?it.examType == 'multiple'}}checkbox{{??it.examType == 'single'}}radio{{??}}checkbox{{?}} inline">
                        <input type="{{?it.examType == 'multiple'}}checkbox{{??it.examType == 'single'}}radio{{??}}checkbox{{?}}"
                        {{?param.isAnswer}}checked{{?}} name="isAnswer" onclick="removeMess()" />
                        我是答案</label>
                    <a class="icon-pencil-blue" href="#"></a>
                    {{?}}
                    <a href="#" class="icon-remove-blue"></a>
                </div>
            </li>
        #}}
    </script>

<!-- 试题选项答案 模板 -->
<script id="examAnswerDetailTemplate" type="text/x-dot-template">
        <section class="section mt20 {{?it.examType == 'completion'}}hide{{?}}" id="examAnswer">
            <div class="hd">
                <h5>
                    试题选项答案
                </h5>
<label id="answerErr" class="error" style="display: block;"></label>
                 <button class="btn btn-primary btn-large pos-right" id="addExamItem" type="button">添加选项</button>
            </div>
            <div class="bd">
                <div class="formAddItem form-horizontal hide" id="formAddItem">
                    <div class="control-group">
                        <label class="control-label" for="nameExamItem">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容</label>
                        <div class="controls">
                            <input type="text" id="nameExamItem" required />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="">是否答案</label>
                        <div class="controls">
                            <label class="{{?it.examType == 'multiple'}}checkbox{{??it.examType == 'single'}}radio{{??}}checkbox{{?}}" >
                                <input type="{{?it.examType == 'multiple'}}checkbox{{??it.examType == 'single'}}radio{{??}}checkbox{{?}}" onclick="removeMess()" name="isAnswer"/>我是答案</label>
                            <div class="action-btn">
                                <button class="btn btn-primary btn-large" type="button">确定</button>
                                <button class="btn btn-link btn-large" type="button">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
                <ul class="unstyled list-attachment" id="listExamAnswer">
                    {{~it.listExamAnswer :answer:index}}
                        {{~it.listExamAnswer :answer2:index2}}
                            {{?index == answer2.index}}
                                {{#def.item:answer2}}
                            {{?}}
                        {{~}}
                    {{~}}
                </ul>
            </div>
        </section>
    </script>
<script id="examQuestionTemplate" type="text/x-dot-template">
	    <tr data-fdid="{{=it.id}}" >
		    <td class="tdTit">
		        <div class="pr">
		            <div class="state-dragable"><span class="icon-bar"></span><span
		                    class="icon-bar"></span><span class="icon-bar"></span><span
		                    class="icon-bar"></span><span class="icon-bar"></span></div>
		            <a href="#" >{{=cut_str(it.subject, 30)}}<i class="icon-pencil"></i></a>
		        </div>
		    </td>
		    <td><input id="sore_{{=it.id}}" type="text" digits="true" max="100" min="0" onblur="initScore()"  style="width:30px" value="{{=it.score}}" data-toggle="tooltip" title="输入数字做为整数且不能大于100分" class="itemScore input-mini digits">分
			
			</label>
			</td>
		    <td><a href="#" class="icon-remove-blue"></a></td>
		</tr>
    </script>

<script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>
	<input type='hidden' id='materIalId' value='${param.fdId}' />
	<section class="container">
		<section class="clearfix mt20">
			<section class="col-left pull-left">
				<%@ include file="/WEB-INF/views/group/menu.jsp"%>
			</section>
			<section class="w790 pull-right" id="rightCont">
				<div class="page-header">
					<a href="${ctx}/material/findList?fdType=08&order=FDCREATETIME" class="backParent">返回测试列表</a>
					<h4 id="examMainName"></h4>
					<div class="btn-group">
						<button class="btn btn-large btn-primary" id="saveExamPaper"
							type="button">保存</button>
					<c:if test="${param.fdId!=null}">
					    <button class="btn btn-large btn-primary" id="exportExamPaper" type="button">导出</button>
						<button class="btn btn-white btn-large " id="delExamPaper" type="button">删除</button>
					</c:if>
					</div>
				</div>
				<div class="page-body editingBody">
					<form action="#" id="formEditDTotal" class="form-horizontal"
						method="post">
						<section class="section">
							<div class="control-group">
								<label class="control-label" for="examPaperName">试&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卷</label>
								<div class="controls">
									<input id="examPaperName" required class="span6"
										name="examPaperName" type="text"> <label
										id="examPaperNameErr" for="examPaperName" class="error"
										style="display: none;"></label><span class="date"
										id="createTime"></span>
								</div>

							</div>
							<div class="control-group">
								<label class="control-label" for="examPaperIntro">试卷简介</label>
								<div class="controls">
									<textarea  rows="4" class="input-block-level"
										id="examPaperIntro" name="examPaperIntro"></textarea>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">建议时间 <small>(单位分钟)</small></label>
								<div class="controls">
								<input id="examPaperTime" digits="true"  max="1440" class="input-block-level" name="examPaperTime" type="text" value="1">
									<!-- <input name="examPaperTime" id="examPaperTime" value="0"
										type="hidden" />
									<div id="mainTimeLine" class="timeLine">
										<div class="num">0</div>
										<a title="15分钟" href="#" class="first"><span class="num">15</span></a>
										<a title="30分钟" href="#"><span class="num">30</span></a> <a
											title="45分钟" href="#"><span class="num">45</span></a> <a
											title="60分钟" href="#"><span class="num">60</span></a> <a
											title="75分钟" href="#"><span class="num">75</span></a> <a
											title="90分钟" href="#"><span class="num">90</span></a> <a
											title="105分钟" href="#"><span class="num">105</span></a> <a
											title="120分钟" href="#"><span class="num">120</span></a>
									</div> -->
								</div>
							</div>

						</section>
						<section class="section mt20">
							<div class="hd">
								<label for="passScore" class="miniInput-label"> 试题列表（共计<b><span
									id="questionCount"> 0 </span></b>题，满分<b><span id="questionScore"> 0 </span></b>分，及格
									<input class="input-mini" style="font-weight: bold;color:black;" required number="true" id="passScore"
									name="passScore" type="text" value="0" /> 分）
								</label> <label for="passScore" id="passScoreErr" class="error"></label>
								<button class="btn btn-primary btn-large" id="addExam"
									type="button">添加试题</button>
							</div>
							<div class="bd">
								<table class="table table-bordered" id="list_exam_table" style="display:none">
									<thead>
										<tr>
											<th>试题</th>
											<th>分数</th>
											<th>删除</th>
										</tr>
									</thead>
									<tbody id="list_exam">




									</tbody>
								</table>
							</div>
						</section>
						<section class="section mt20">
							<div class="control-group">
								<label class="control-label" for="author">作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者</label>
								<div class="controls">
									<input value="${person.fdName}" id="author" required class="input-block-level"
										name="author" type="text" >
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="authorIntro">作者简介</label>
								<div class="controls">
									<textarea placeholder="非必填项" rows="4" class="input-block-level"
										id="authorIntro" name="authorIntro">${person.selfIntroduction}</textarea>
								</div>
							</div>
						</section>
						<div class="page-header mt20">
							<h4>权限设置</h4>
						</div>
						<section class="section">
							<label>权限设置<input type="hidden" id="permission"
								name="permission" value="encrypt"></label>
							<ul class="nav nav-pills">
								<li><a id="open1" data-toggle="tab"
									href="#open">公开</a></li>
								<li class="active"><a id="close1" data-toggle="tab" href="#encrypt">加密</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane" id="open">
									提示：“公开”素材将允许所有主管在管理课程的过程中使用，而“加密”素材将允许您手动授权某些主管使用本课程素材。</div>
								<div class="tab-pane active" id="encrypt">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>授权用户</th>
												<th>可使用</th>
												<th>可编辑</th>
												<th>删除</th>
											</tr>
										</thead>
										<tbody id="list_user">

										</tbody>
									</table>
									<div class="pr">
										<input type="text" id="addUser"
											class="autoComplete input-block-level"
											placeholder="请输入人名、邮箱、机构或者部门查找用户"> 
									</div>
								</div>
							</div>
						</section>
						<button class="btn btn-block btn-submit btn-inverse" type="button" id="saveTestPaper">保存</button>
					</form>
				</div>
			</section>
		</section>
	</section>

	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.sortable.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
	<script src="${ctx}/resources/js/jquery.jalert.min.js"
		type="text/javascript"></script>
<script type="text/javascript">
$("#exportExamPaper").click(function(e){
	window.location.href="${ctx}/common/exp/getExpExamPaper/${param.fdId}";
});

</script>

	<script type="text/javascript">
		$(function() {
			jQuery.extend(jQuery.validator.messages, {
				digits : "只能输入整数"
			});
			$.Placeholder.init();
			$('.itemScore[data-toggle="tooltip"]').tooltip({
				trigger : "focus"
			});
			/* $(".timeLine>a").tooltip().click(function(e) {
				e.preventDefault();
				$(this).prevAll("a").add(this).addClass("active");
				$(this).nextAll("a").removeClass("active");
				$("#examPaperTime").val($(this).children(".num").text());
				$("#questionScoreErr").css("display", "none");
			}); */
			//初始化页面
			if ("${param.fdId}" != null && "${param.fdId}" != "") {
				$
						.ajax({
							url : "${ctx}/ajax/material/getMaterial?materialId=${param.fdId}",
							async : false,
							dataType : 'json',
							type: "post",
							success : function(result) {
								$("#examPaperIntro").val(result.description);
								if($("#examPaperIntro").val()!=""){
									$("#examPaperIntro").css("color","");
								}
								$("#passScore").val(result.score);
								$("#author").val(result.fdAuthor);
								$("#authorIntro").val(
										result.fdAuthorDescription);
								$("#createTime").html(result.createTime);

								if (result.isPublish == true) {
									$("#open1").trigger("click");
									$("#permission").val("open");
								} else {
									$("#close1").trigger("click");
									$("#permission").val("encrypt");
								}
								var n = result.time / 15;
								/* $("#mainTimeLine a :lt(" + n + ")").attr(
										"class", "active");*/
								$("#examPaperTime").val(parseInt(result.time)); 
							}
						});
				//初始化试题列表
				initExamQuestions();
				//初始化权限列表
			}
			var creater = "";
			var createrId="";
			var url = "";
			$
					.ajax({
						url : "${ctx}/ajax/material/getCreater?materialId=${param.fdId}",
						async : false,
						type: "post",
						dataType : 'json',
						success : function(result) {
							creater = result.name + "（" + result.email
									+ "），" + result.dept;
							url = result.url;
							createrId = result.fdId;
						}
					});
			var listUserKinguserFn = doT.template(document.getElementById("listUserKinguserTemplate").text);
			$.ajax({url : "${ctx}/ajax/material/getAuthInfoByMaterId?MaterialId=${param.fdId}",
					async : false,
					dataType : 'json',
					type: "post",
					success : function(result) {
							var photo;
							if(url.indexOf("http")>-1){
								photo=url;
							}else{
								photo="${ctx}/"+url;
							}
							var html = "<tr data-fdid='"+createrId+"' draggable='true'> "
									+ " <td class='tdTit'> <div class='pr'> <div class='state-dragable'><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></div> "
									+ "<img src='"+photo+"' alt=''>"
									+ creater
									+ " </div> </td>"
									+ " <td><input type='checkbox' onclick='return false' checked='' class='tissuePreparation'></td> <td>"
									+ "<input type='checkbox' onclick='return false' checked='' class='editingCourse'></td> <td></a>"
									+ "</td> </tr>";
							for ( var i in result.user) {
								html += listUserKinguserFn(result.user[i]);
							}
							$("#list_user").html(html);

						}
					});

			//试题详情编辑页面 模板函数
			var examDetailFn = doT
					.template(
							document.getElementById("examDetailTemplate").text
									+ document
											.getElementById("itemExamDetailTemplate").text,
							undefined,
							{
								examAnswer : document
										.getElementById("examAnswerDetailTemplate").text
							});

			//试题详情 列表项 模板函数
			var itemExamDetailFn = doT.template(document
					.getElementById("itemExamDetailTemplate").text);

			$("#saveTestPaper").bind("click",function(){
				saveTestPaperfn();
			});
			$("#saveExamPaper").click(function(e) {
				$("#saveTestPaper").trigger("click");
			});
			//删除试卷事件
			$("#delExamPaper")
					.click(
							function(e) {
								jalert(
												"您确认要删除当前测试？",
												function() {
													$
															.ajax({
																url : "${ctx}/ajax/material/deleteMaterial?materialId=${param.fdId}",
																async : false,
																type: "post",
																success : function(
																		result) {
																	window.location.href = "${ctx}/material/findList?order=FDCREATETIME&fdType=08";
																}
															});
												});

							});

			/*提交表单函数*/
			function saveTestPaperfn() {
				if(!$("#formEditDTotal").valid()){
	        		return;
	        	}
				if (parseInt($("#passScore").val()) > parseInt($(
						"#questionScore").html())) {
					$("#passScoreErr").css("display", "block");
					$("#passScoreErr").html("及格分数不能大于满分");
					return;
				}
				var data = {
					id : "${param.fdId}",
					examPaperName : $("#examPaperName").val(),
					examPaperIntro : $("#examPaperIntro").val()==""?"作者很懒，没有写测试简介。":$("#examPaperIntro").val(),
					author : $("#author").val(),
					authorIntro : $("#authorIntro").val(),
					permission : $("#permission").val(),
					score : $("#passScore").val(),
					studyTime : $("#examPaperTime").val(),
					listExam : [],
					kingUser : []
				};
				if ($("#list_exam").children("tr").length) {
					//push 试题列表数据
					$("#list_exam>tr").each(function(i) {
						data.listExam.push({
							id : $(this).attr("data-fdid"),
							index : i,
							editingCourse : $(this).find(".itemScore").val()
						});
					});
					data.listExam = JSON.stringify(data.listExam);
				}
				if (data.permission === "encrypt") {
					$("#list_user>tr").each(
							function(i) {
								data.kingUser.push({
									id : $(this).attr("data-fdid"),
									index : i,
									tissuePreparation : $(this).find(
											".tissuePreparation")
											.is(":checked"),
									editingCourse : $(this).find(
											".editingCourse").is(":checked")
								});
							});
					data.kingUser = JSON.stringify(data.kingUser);
				}
				if ($("#list_exam").children("tr").length == 0) {
					$("#passScoreErr").css("display", "block");
					$("#passScoreErr").html("请为本试卷添加试题以后，再保存。");
					return;
				}
				if (data.permission === "encrypt" && data.kingUser.length == 0) {
					jalert("请输入用户");
				}
				$
						.ajax({
							url : "${ctx}/ajax/examquestion/UpdateExamQuestionAndMaterial",
							async : false,
							data : data,
							dataType : 'json',
							type: "post",
							success : function(rsult) {
								window.location.href = "${ctx}/material/findList?order=FDCREATETIME&fdType=08";
							}
						});
			}

			$('#formEditDTotal a[data-toggle="tab"]').on('click', function(e) {
				var href = e.target.href.split("#").pop();
				$("#permission").val(href);
			});
			$("#list_user,#list_exam").sortable({
				handle : '.state-dragable'
			}).find("a.icon-remove-blue").bind("click", function(e) {
				e.preventDefault();
				$(this).closest("tr").remove();
				initScore();
				if($("#list_exam tr").length==0){
					$("#list_exam_table").css("display","none");
				}else{
					$("#list_exam_table").css("display","table");
				}
			});

			$("#addUser").autocomplete("${ctx}/ajax/user/findByName",{
								formatMatch : function(item) {
									return item.name + item.mail + item.org
											+ item.department;
								},
								formatItem : function(item) {
									var photo;
									if(item.imgUrl.indexOf("http")>-1){
										photo=item.imgUrl;
									}else{
										photo="${ctx}/"+item.imgUrl;
									}									
									return '<img src="'
											+ photo
											+ '" alt="">' + item.name + '（'
											+ item.mail + '），' + item.org
											+ '  ' + item.department;
								},
								parse : function(data) {
									var rows = [];
									for ( var i = 0; i < data.length; i++) {
										rows[rows.length] = {
											data : data[i],
											value : data[i].name,
											result : data[i].name
										//显示在输入文本框里的内容 ,
										};
									}
									return rows;
								},
								dataType : 'json',
								matchContains : true,
								max : 10,
								scroll : false,
								width : 748
							})
					.result(
							function(e, item) {
								var flag = true;
								$("#addUser").next(".error").remove();
								$("#list_user>tr")
										.each(
												function() {
													if ($(this).attr(
															"data-fdid") == item.id) {
														$("#addUser")
																.after(
																		'<label class="error">不能添加重复的用户！</label>');
														;
														$("#addUser").val("");
														flag = false;
													}
												});
								if (flag) {
									$(this).val(item.name);
									$("#list_user").append(
											listUserKinguserFn(item)).sortable(
											{
												handle : '.state-dragable',
												forcePlaceholderSize : true
											}).find("a.icon-remove-blue").bind(
											"click", function(e) {
												e.preventDefault();
												$(this).closest("tr").remove();
												initScore();
											});
									$("#addUser").val("");
								}

							});

			//添加试题事件
			$("#addExam").bind("click", function(e) {
				loadExamPage();
			});

			//打开试题列表中的试题详情
			$("#list_exam>tr>.tdTit .pr a").click(function(e) {
				e.preventDefault();
				var id = $(this).closest("tr").attr("data-fdid");
				loadExamPage(id);
				$("#goTop").trigger("click");
				//
			});

			/*加载试题页面*/
			function loadExamPage(fdid) {
				var materialName = $("#examPaperName").val();
				var materialintro1 = $("#examPaperIntro").val();
				var materialScore = $("#examPaperTime").val();
				var author = $("#author").val();
				var authorIntro = $("#authorIntro").val();
				if (materialName == "" || materialName == null) {
					$("#examPaperNameErr").html("请先设置试卷名称");
					$("#examPaperNameErr").css("display", "block");
					return;
				}
				var data = {};
				if (!fdid) {//添加试题数据
					data = {
						examScoreTotal : 20,
						examType : "multiple"
					};
				} else {

					$.ajax({
						url : "${ctx}/ajax/examquestion/getExamsByMaterialId",
						async : false,
						type: "post",
						data : {
							id : fdid
						},
						dataType : 'json',
						success : function(rsult) {
							data = rsult;
						}
					});

				}
				var fdName = $("#examPaperName").val();
				  if(fdName.length>14){
					  fdName = fdName.substring(0, 14)+"...";
				  } 
				data.examPaperName = fdName; //当前试卷名称
				$("#rightCont").html(examDetailFn(data));

				//应用拖放效果
				$(".list-attachment")
						.sortable({
							handle : '.state-dragable'
						})
						//移除和编辑列表项
						.delegate(".item-ctrl>.icon-remove-blue", "click",
								function(e) {
									e.preventDefault();
									$(this).closest("li").remove();
									initScore();
								})
						.delegate(
								".item-ctrl>.icon-pencil-blue",
								"click",
								function(e) {
									e.preventDefault();
									var $this = $(this);
									var $name = $this.hide().parent().prev(
											".name");
									var _txt = $name.text();
									var $inpt = $('<input type="text" required value="' + _txt + '" name="isAnswer" />');
									var $btns = $('<div class="action-btn">\
                            <button class="btn btn-primary btn-large" type="button">确定</button>\
                            <button class="btn btn-link btn-large" type="button">取消</button>\
                            </div>');
									var $checkbox = $this.prev("label")
											.detach();
									$name.html($inpt).append(
											$checkbox.removeClass("inline"))
											.append($btns);
									$btns.find(".btn").click(function(e) {
										var $checkbox = $(this).parent().prev("label").detach();
										if ($(this).hasClass("btn-primary")) {
											if (validator.element($inpt)) {
												$name.html($inpt.val());
												$name.next(".item-ctrl").prepend($checkbox.addClass("inline").detach());
												$this.show();
											}
										} else {
											$name.html($inpt.val());
											$name.next(".item-ctrl").prepend($checkbox.addClass("inline").detach());
											$this.show();
										}
									});
								});

				/* $(".scoreLine>a").tooltip().click(function(e) {//分数控制
					e.preventDefault();
					$(this).prevAll("a").add(this).addClass("active");
					$(this).nextAll("a").removeClass("active");
					$("#examScore").val($(this).children(".num").text());
					$("#questionScoreErr").css("display", "none");
				}); */
				var validator = $("#formEditDTotal").validate({
					
				});
				$("#saveTestQuestions").bind("click",function(){
					saveTestQuestionsfn();
				});
				$("#saveExam").click(function(e) {
					$("#saveTestQuestions").trigger("click");
				});
				var $formAdd = $("#formAddItem");
				$formAdd.find(".action-btn>.btn").click(
					function(e) {
						if ($(this).hasClass("btn-primary")) {
							if (validator.element("#nameExamItem")) {
								$("#listExamAnswer").append(itemExamDetailFn({
									flag : "add",
									name : $("#nameExamItem").val(),
									isAnswer : $formAdd.find("input[name='isAnswer']").is(":checked"),
									examType : $("#examType").val()
								})).sortable({handle : ".state-dragable"});
										$formAdd.addClass("hide");
										$formAdd.find("#nameExamItem").val("");
										$formAdd.find("input[name='isAnswer']").removeAttr("checked");
							}
						} else {
								$formAdd.addClass("hide");
						}
					});

				$("#addExamItem").click(function(e) {
					$formAdd.removeClass("hide");
					$("#answerErr").css("display","none");
				});

				//试题类型选择
				$('[data-toggle="buttons-radio"]>.btn')
						.click(
								function() {
									$("#examType").val(this.id);
									if (this.id == "completion") {
										$("#examAnswer").addClass("hide");
										$("#examStem").attr("placeholder","请使用#...#标记填空题的答案，例如：发现相对论的是#爱因斯坦#先生");
									} else {
										$("#examAnswer").removeClass("hide");
										if (this.id == "single") {
											$("#listExamAnswer>li .checkbox,#formAddItem .checkbox")
													.removeClass("checkbox")
													.addClass("radio")
													.children(":checkbox")
													.after(
															'<input type="radio" name="isAnswer" onclick="removeMess()" />')
													.remove();
										} else if (this.id == "multiple") {
											$(
													"#listExamAnswer>li .radio,#formAddItem .radio")
													.removeClass("radio")
													.addClass("checkbox")
													.children(":radio")
													.after(
															'<input type="checkbox" name="isAnswer" />')
													.remove();
										}
										$("#examStem").attr("placeholder","请详细填写试题内容");
									}
								});

				/*提交试题详情表单函数*/
				function saveTestQuestionsfn() {
					if(!$("#formEditDTotal").valid()){
		        		return;
		        	}
					if ($("#examType").val() == "completion") {
						var examSub = $("#examStem").val();
						if ((examSub.split("#").length % 2 == 0)) {
							$("#examStemErr").html("题干输入有误，请仔细查看修改后再提交");
							$("#examStemErr").css("display", "block");
							return;
						}
						if ((examSub.split("#").length==1)||examSub.indexOf("##")==(examSub.length-2)) {
							$("#examStemErr").html("请为本填空题添加选项以后，再保存。");
							$("#examStemErr").css("display", "block");
							return;
						}
					}
					var data = {
						examType : $("#examType").val(),
						examStem : $("#examStem").val(),
						examScore : $("#examScore").val(),
						listAttachment : [],
						listExamAnswer : []
					};
					if ($("#listAttachment>li").length) {
						//push 附件列表数据
						$("#listAttachment>li").each(function(i) {
							data.listAttachment.push({
								id : $(this).attr("data-fdid"),
								index : i,
								url : $(this).find(".name").attr("href"),
								name : $(this).find(".name").html(),
							});
						});
						data.listAttachment = JSON
								.stringify(data.listAttachment);
					}
					if (data.examType != "completion"
							&& $("#listExamAnswer>li").length) {
						//push 试题答案列表数据
						$("#listExamAnswer>li").each(
								function(i) {
									data.listExamAnswer.push({
										id : $(this).attr("data-fdid"),
										index : i,
										name : $(this).find(".name").text(),
										isAnswer : $(this)
												.find("input:checked").is(
														":checked")
									});
								});
						data.listExamAnswer = JSON
								.stringify(data.listExamAnswer);
					}
					/* if (JSON.stringify(data.examScore) == '""'||parseInt(data.examScore)>100) {
						$("#questionScoreErr").html("试题分数必须在0—100之间");
						$("#questionScoreErr").css("display", "block");
						return;
					} */
					if (JSON.stringify(data.examType) != '"completion"'
							&& JSON.stringify(data.listExamAnswer) == "[]") {
						$("#answerErr").html("请为本试题添加选项以后，再保存。");
						$("#answerErr").css("display", "block");
						return;
					}
					var isanswer=false;
					$("#listExamAnswer>li").each(function(i) {
						if($(this).find("input:checked").is(":checked")){
							isanswer=true;
						};
					});
					if (JSON.stringify(data.examType) != '"completion"'&&!isanswer) {
						$("#answerErr").html("请为本试题添加答案以后，再保存。");
						$("#answerErr").css("display", "block");
						return;
					}
					//ajax
					data.materialName=materialName;
					data.materialintro=materialintro1==""?"作者很懒，没有写试卷简介。":materialintro1;
					data.materialScore=materialScore;
					data.author=author;
					data.authorIntro=authorIntro;
					$.ajax({
								url : "${ctx}/ajax/examquestion/saveOrUpdateExamQuestion?questionId="
										+ fdid
										+ "&materIalId=${param.fdId}",
								async : false,
								data : data,
								type : "POST",
								dataType : 'json',
								success : function(rsult) {
									window.location.href = "${ctx}/material/materialFoward?fdId="
											+ rsult.materIalId + "&fdType=08";
								}
							});
				}
				
				var $txt = $("#upMaterial").prev(".txt"), 
		        $progress = $txt.prev(".progress").children(".bar"),
		        $pct = $txt.children(".pct"),
		        $countdown = $txt.children(".countdown"),
		    	flag = true,
		    	pct,interval,countdown = 0,byteUped = 0;

		    	$("#upMaterial").uploadify({
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
		                $("#listAttachment").append(itemExamDetailFn({
		               	 	flag: "add" ,
		               	 	id: objvalue.attId,
		                    name: objvalue.fileName,
		               })).sortable({
		                       handle: ".state-dragable"
		               });
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
				
				
				
				
				if (fdid == null || fdid == "") {
					$("#delExam").remove();
				} else {
					$("#delExam")
							.bind(
									"click",
									function() {
										jalert(
														"确认删除试题？",
														function() {
															$
																	.ajax({
																		url : "${ctx}/ajax/examquestion/deleQuestionToExam",
																		async : false,
																		dataType : 'json',
																		type: "post",
																		data : {
																			questionId : fdid,
																			examId : "${param.fdId}",
																		},
																		success : function(
																				result) {
																		}
																	});
															window.location.href = "${ctx}/material/materialFoward?fdId=${param.fdId}&fdType=08";
														});

									});
				}
			}
		});

		function initExamQuestions() {
			var id = $("#materIalId").val();
			if (id !== null && id != "") {
				var examQuestionTemplate = doT.template(document.getElementById("examQuestionTemplate").text);
				$.ajax({url : "${ctx}/ajax/material/getExamQuestionByMaterId?materialId=${param.fdId}",
						async : false,
						dataType : 'json',
						type: "post",
						success : function(result) {
								var html = "";
								for ( var i in result.qusetions) {
									html += examQuestionTemplate(result.qusetions[i]);
								}
								if(result.qusetions.length==0){
									$("#list_exam_table").css("display","none");
								}else{
									$("#list_exam_table").css("display","table");
								}
								$("#list_exam").html(html);
								var fdName = result.name;
								  if(fdName.length>14){
									  fdName = fdName.substring(0, 14)+"...";
								  } 
								$("#examPaperName").val(result.name);
								$("#examMainName").html(fdName);
								$("#passScore").val(result.score);
								initScore();

							}
						});
			}

		}

		function initScore() {
			var totalScore = 0;
			var count = 0;
			for ( var i = 0; i < $("#list_exam input").length; i++) {
				count++;
				var score = $("#list_exam input :eq(" + i + ")").val() + "";
				if (!/^\+?[1-9][0-9]*$/.test(score)) {
					score = 0;
				}
				totalScore = totalScore + parseInt(score);
			}
			$("#questionCount").html(" "+count+" ");
			$("#questionScore").html(" "+totalScore+" ");
		}
		function removeMess(){$("#answerErr").css("display","none");};
	</script>
</body>
</html>
