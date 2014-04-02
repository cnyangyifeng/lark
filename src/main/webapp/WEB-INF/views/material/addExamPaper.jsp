<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html class=""> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">


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
        <td><input type="checkbox" {{?it.editingCourse!=false}}checked{{?}}  class="editingCourse" /></td>
        <td><a href="#" class="icon-remove-blue"></a></td>
    </tr>
</script>

<!-- 作业详情编辑 模板 -->
<script id="examDetailTemplate" type="text/x-dot-template">
    <div class="page-header">
        <a href="${ctx}/material/materialAddFoward?fdId=${param.fdId}&fdType=10" class="backParent">返回当前作业包</a>
        <h4>{{=it.examPaperName}}</h4>
        <div class="btn-group">
            <button class="btn btn-large btn-primary" id="saveExam" type="button">保存</button>
            {{?it.taskId!=null}}
            <button class="btn btn-large btn-primary" disabled id="exportExam" type="button">导出</button>
            <button class="btn btn-white btn-large " onclick="deleteTask();" id="delExam" type="button">删除</button>
            {{?}}
        </div>
    </div>
    <div class="page-body editingBody">
        <form action="{{=it.action || '#'}}" id="formEditDTotal" class="form-horizontal" method="post">
            <section class="section">
                <div class="control-group">
                    <label class="control-label" >类型设置</label>
                    <div class="controls">
                        <input name="taskId" id="taskId" value="{{=it.taskId || ''}}" type="hidden" />
                        <input name="examType" id="examType" value="{{=it.examType || 'uploadWork'}}" type="hidden" />
                        <div class="btn-group btns-radio" data-toggle="buttons-radio">
                            <button class="btn btn-large{{?!it.examType || it.examType == 'uploadWork'}} active{{?}}" id="uploadWork" type="button">上传作业</button>
                            <button class="btn btn-large{{?it.examType == 'onlineAnswer'}} active{{?}}" id="onlineAnswer" type="button">在线作答</button>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="examName">作业名称</label>
                    <div class="controls">
                        <input value="{{=it.examName || ''}}" id="examName" placeholder="请输入作业名称" required class="span6"
                               name="examName" type="text"><span class="date"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="examStem">作业简介</label>
                    <div class="controls"><textarea placeholder="作者很懒，没写作业简介。" rows="4"
                                                    class="input-block-level"  id="examStem"
                                                    name="examStem">{{=it.examStem || ''}}</textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" >作业分值 <small>(单位分)</small></label>
                    <div class="controls">
						<input id="examScore" digits="true" required min="0" value="{{=it.examScore || '1'}}" max="100" class="input-block-level" name="examScore" type="text">
                       <!-- <input name="" id="examScore" value="{{=it.examScore || ''}}" type="hidden"/>
                           {{#def.timeLine:it.timeLine}}-->
                    </div>
                </div>
            </section>
            <section class="section mt20">
                <label>辅助材料（上传辅助材料，建议小于2G）</label>
                <div class="control-upload">
					<span class="progress"> <div class="bar" style="width:0;"></div> </span>
					<span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
                     <button id="upMaterial" class="btn btn-primary btn-large" type="button" >上传</button>                   
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

            <button class="btn btn-block btn-submit btn-inverse" id="submitTask" type="button">保存</button>
        </form>
    </div>
</script>

    <!-- 时间轴 模板 -->
    <script id="timeLineTemplate" type="text/x-dot-template">
       <!-- {{?!it.timeLine}}
            {{#def.timeLine:it}}
        {{?}}
        {{##def.timeLine:param:
            <div class="timeLine">
                    <div class="num">0</div>
            {{ for(var i=1; i <= param.total; i++){ }}
            <a title="{{=i*param.span}}{{=it.unit || ''}}" href="#" style="width: {{=(param.width-param.total-1)/param.total}}px"
            class="{{?i*param.span==param.span}}first {{?}}{{?param.curPos && i*param.span<=param.curPos}}active{{?}}"><span class="num">{{=i*param.span}}</span></a>
            {{ } }}
            </div>
           <label id="examScoreErr" class="error" style="display: none;"></label>
        #}} -->
    </script>

    <!-- 作业详情 列表项 模板 -->
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
                    <label class="{{?it.examType == 'uploadWork'}}checkbox{{??it.examType == 'onlineAnswer'}}radio{{??}}checkbox{{?}} inline">
                        <input type="{{?it.examType == 'uploadWork'}}checkbox{{??it.examType == 'onlineAnswer'}}radio{{??}}checkbox{{?}}"
                        {{?param.isAnswer}}checked{{?}} name="isAnswer" />
                        我是答案</label>
                    <a class="icon-pencil-blue" href="#"></a>
                    {{?}}
                    <a href="#" class="icon-remove-blue"></a>
                </div>
            </li>
        #}}
    </script>
    <script id="taskPaperListTemplate" type="text/x-dot-template">
	    <tr data-fdid="{{=it.id}}" >
		    <td class="tdTit">
		        <div class="pr">
		            <div class="state-dragable"><span class="icon-bar"></span><span
		                    class="icon-bar"></span><span class="icon-bar"></span><span
		                    class="icon-bar"></span><span class="icon-bar"></span></div>
		            <a href="#" >{{=cut_str(it.subject, 30)}}<i class="icon-pencil"></i></a>
		        </div>
		    </td>
		    <td><input id="score_{{=it.id}}" type="text" digits="true" max="100" min="0" onblur="initScore()" value="{{=it.score}}" data-toggle="tooltip" title="输入数字做为整数且不能大于100分" class="itemScore input-mini">分
		    </td>
			<td><a href="#" class="icon-remove-blue"></a></td>
		</tr>
    </script>

    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>

<section class="container">
	<section class="clearfix mt20">
	  <section class="col-left pull-left">
	  <%@ include file="/WEB-INF/views/group/menu.jsp" %>
	  </section>
		<section class="w790 pull-right" id="rightCont">
	        <div class="page-header">
                <a href="${ctx}/material/findList?fdType=10&order=FDCREATETIME" class="backParent">返回作业包列表</a>
                <h4 id="fdName"></h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" id="saveExamPaper" type="button">保存</button>
                    <c:if test="${param.fdId!=null}">
                    <button class="btn btn-large btn-primary" id="exportExamPaper" type="button">导出</button>
                    <button class="btn btn-white btn-large " onclick="deleteMaterial();" id="delExamPaper" type="button">删除</button>
                    </c:if>
                </div>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" method="post">
                    <section class="section">
                        <div class="control-group">
                            <label class="control-label" for="examPaperName">作业包</label>
                            <div class="controls">
                                <input  id="examPaperName" required class="span6"
                                       name="examPaperName" type="text">
                                <label id="examPaperNameErr" for="examPaperName" class="error" style="display: none;"></label>
                                <span class="date" id="createTime"></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="examPaperIntro">作业包<div>简介</div></label>
                            <div class="controls">
                            <textarea  rows="4"
                                      class="input-block-level" id="examPaperIntro"
                                      name="examPaperIntro"></textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >建议时间 <small>(单位天)</small></label>
                            <div class="controls">
                            <input id="examPaperTime"  digits="true" min="0" max="30" class="input-block-level" value="{{=it.curPos}}" type="text">
                                <!-- <input name="examPaperTime" id="examPaperTime"  type="hidden"/> -->
                            </div>
                            <label id="examPaperTimeErr" class="error" style="display: none;"></label>
                        </div>

                    </section>
                    <section class="section mt20">
                        <div class="hd">
                            <label for="passScore" class="miniInput-label">
                                                                       作业列表（共计<b> <span id="count">0</span> </b>题，满分<b>  <span id="totalScore">0</span> </b>分，及格 
                                <input class="input-mini" style="font-weight: bold;color: black;" id="passScore"  digits="true"  required="required"  name="passScore"  type="text" value="0"/>      分）
                            </label>
                            <label for="passScore" id="passScoreErr" class="error" ></label>
                            <button class="btn btn-primary btn-large" id="addExam" type="button">添加作业</button>
                        </div>
                        <div class="bd">
                            <table class="table table-bordered" id="list_exam_table" style="display:none">
                                <thead>
                                <tr>
                                    <th>作业</th>
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
                                <input id="author" required class="input-block-level"
                                       name="author" type="text" value="${person.fdName}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="authorIntro">作者简介</label>
                            <div class="controls">
                            <textarea placeholder="非必填项" rows="4"
                                      class="input-block-level" id="authorIntro"
                                      name="authorIntro">${person.selfIntroduction}</textarea>
                            </div>
                        </div>
                    </section>
                    <div class="page-header mt20"> <h4>权限设置</h4> </div>
                    <section class="section">
                        <label>权限设置<input type="hidden" id="permission" name="permission" value="encrypt"></label>
                        <ul class="nav nav-pills">
                            <li><a id="open1" data-toggle="tab" href="#open">公开</a></li>
                            <li class="active"><a id="close1" data-toggle="tab" href="#encrypt">加密</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane" id="open">
                                提示：“公开”素材将允许所有主管在管理课程的过程中使用，而“加密”素材将允许您手动授权某些主管使用本课程素材。
                            </div>
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
                                    <input type="text" id="addUser" class="autoComplete input-block-level" placeholder="请输入人名、邮箱、机构或者部门查找用户">
                                    
                                </div>
                            </div>
                        </div>
                    </section>
                    <button class="btn btn-block btn-submit btn-inverse" id="submitTaskPaper" type="button">保存</button>
                </form>
            </div>
	    </section>
	</section>
<input type="hidden" id="materialId" value="${param.fdId}"/>
<input type="hidden" id="fdType" value="${param.fdType}">
</section>
<script type="text/javascript" src="${ctx}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    $.Placeholder.init();
    $('.itemScore[data-toggle="tooltip"]').tooltip({
        trigger: "focus"
    });
  //初始化页面
  //时间轴 模板函数
    var timeLineFn = doT.template(document.getElementById("timeLineTemplate").text);
    var curTime = 1;
	if("${param.fdId}"!=null&&"${param.fdId}"!=""){
		 $.ajax({
			  type:"post",
			  url: "${ctx}/ajax/material/getMaterial?materialId="+$("#materialId").val(),
			  async:false,
			  dataType : 'json',
			  success: function(result){
				  $("#examPaperIntro").val(result.description);
				  var fdName = result.fdName;
				  if(fdName.length>14){
					  fdName = fdName.substring(0, 14)+"...";
				  } 
				  $("#fdName").html(fdName);
				  $("#examPaperName").val(result.fdName);
				  $("#passScore").val(result.score);
				  $("#examPaperTime").val(result.time);
				  $("#author").val(result.fdAuthor);
				  $("#authorIntro").val(result.fdAuthorDescription);
				  $("#createTime").html(result.createTime);
				  $("#passScore").val(result.score);
				  curTime = result.time;
				  
				  if(result.isPublish==true){
					  $("#open1").trigger("click");
					  $("#permission").val("open");
				  }else{
					  $("#close1").trigger("click");
					  $("#permission").val("encrypt");
				  }
			  }
		});
	}
	
	 $("#examPaperTime").after(timeLineFn({//时间轴控件 配置数据
	        width: 670, //时间轴控件 宽度
	        total: 7, //总格数
	        curPos: curTime, //当前位置
	        span: 1, //每格的进制
	        unit: "天"
	    }));
	 $("#examPaperTime").val(curTime);
	/*  $(".timeLine>a").tooltip().click(function(e){
         e.preventDefault();
         $(this).prevAll("a").add(this).addClass("active");
         $(this).nextAll("a").removeClass("active");
         $("#examPaperTime").val( $(this).children(".num").text());
     }); */
});
function deleteMaterial(){
	jalert("您确认要删除该作业包吗？",confirmDeletePaper);
}
function confirmDeletePaper(){
	$.ajax({
		  url: "${ctx}/ajax/material/deleteMaterial?materialId="+$("#materialId").val(),
		  async:false,
		  success: function(result){
			  window.location.href="${ctx}/material/findList?order=FDCREATETIME&fdType="+$("#fdType").val();
		  }
	}); 
}
function deleteTask(){
	jalert("您确认要删除该作业吗？",confirmDeleteTask);
}
function confirmDeleteTask(){
	$.ajax({
		type: "post",
		url: "${ctx}/ajax/taskPaper/deleteTaskByTaskId",
		data : {
			"taskId":$("#taskId").val()
		},
		success:function(){
			window.location.href="${ctx}/material/findList?order=FDCREATETIME&fdType="+$("#fdType").val();
		}
	});
}
/**
 * Created by wqh on 13-11-1.
 */
$(function(){
    //授权管理 用户列表 模板函数
    var listUserKinguserFn = doT.template(document.getElementById("listUserKinguserTemplate").text);
    //初始化作业列表
    initExamPaperList();
    //初始化授权人员列表
    //初始化权限列表
    var creator="";
    var creatorId="";
	var url="";
	   $.ajax({
		 type:"post",
		 url: "${ctx}/ajax/material/getCreater?materialId="+$("#materialId").val(),
		 async:false,
		 dataType : 'json',
		 success: function(result){
		    creator = result.name+"（"+result.email+"），"+result.dept;
				  url=result.url;
				  creatorId=result.fdId;
		 }
	   });
    $.ajax({
    	type:"post",
    	cache:false,
		url: "${ctx}/ajax/material/getAuthInfoByMaterId?MaterialId="+$("#materialId").val(),
		async:false,
		dataType : 'json',
		success: function(result){
			var photo;
			if(url.indexOf("http")>-1){
				photo=url;
			}else{
				photo="${ctx}/"+url;
			}
			var html = "<tr data-fdid='"+creatorId+"' draggable='true'> "+
			  " <td class='tdTit'> <div class='pr'> <div class='state-dragable'><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></div> "+
			  "<img src='"+photo+"' alt=''>"+creator+" </div> </td>"+
			  " <td><input type='checkbox' onclick='return false' checked='' class='tissuePreparation'></td> <td>"+
			  "<input type='checkbox' onclick='return false' checked='' class='editingCourse'></td> <td></a>"+
			  "</td> </tr>";
			for(var i in result.user){
				  html += listUserKinguserFn(result.user[i]);
			}
			$("#list_user").html(html); 
			
		  }
	});
    $("#list_user,#list_exam").sortable({
        handle: '.state-dragable'
    })
            .find("a.icon-remove-blue").bind("click",function(e){
                e.preventDefault();
                $(this).closest("tr").remove();
                initScore();
                if($("#list_exam tr").length==0){
					$("#list_exam_table").css("display","none");
				}else{
					$("#list_exam_table").css("display","table");
				}
            });

    //作业详情编辑页面 模板函数
    var examDetailFn = doT.template(document.getElementById("examDetailTemplate").text
            + document.getElementById("itemExamDetailTemplate").text
            + document.getElementById("timeLineTemplate").text);

    //作业详情 列表项 模板函数
    var itemExamDetailFn = doT.template(document.getElementById("itemExamDetailTemplate").text);

    $("#submitTaskPaper").click(function(e){
    	submitTaskPaper();
    });
    $("#saveExamPaper").click(function(e){
        $("#submitTaskPaper").trigger("click");
    });
    //删除作业包事件
    $("#delExamPaper").click(function(e){
    	
    });
    $("#exportExamPaper").click(function(e){
    	window.location.href="${ctx}/common/exp/getExpTaskPaper/${param.fdId}";
    });

    /*提交表单函数*/
    function submitTaskPaper(){
    	if(!$("#formEditDTotal").valid()){
    		return;
    	}
    	if ($("#examPaperTime").val()=="") {
			$("#examPaperTimeErr").html("请设置时间");
			$("#examPaperTimeErr").css("display", "block");
			return;
		} 
    	if(parseInt($("#passScore").val())>parseInt($("#totalScore").html())){
    		$("#passScoreErr").css("display","block");
    		$("#passScoreErr").html("及格分数不能大于满分");
    		return;
    	}
        var data = {
            materialId:$("#materialId").val(),
        	examPaperName: $("#examPaperName").val(),
            examPaperIntro: $("#examPaperIntro").val()==""?"作者很懒，没写作业包简介。":$("#examPaperIntro").val(),
            author: $("#author").val(),
            authorIntro: $("#authorIntro").val(),
            permission:$("#permission").val(),
            passScore:$("#passScore").val(),
            studyTime:$("#examPaperTime").val(),
            listExam: [],
            kingUser: []
        };
        if($("#list_exam").children("tr").length){
            //push 作业列表数据
            $("#list_exam>tr").each(function(i){
                data.listExam.push({
                    id: $(this).attr("data-fdid"),
                    index: i,
                    editingCourse: $(this).find(".itemScore").val()
                });
            });
            data.listExam = JSON.stringify(data.listExam);
        }
        if(data.permission === "encrypt"){
            //push人员授权数据
            $("#list_user>tr").each(function(i){
                data.kingUser.push({
                    id: $(this).attr("data-fdid"),
                    index: i,
                    tissuePreparation: $(this).find(".tissuePreparation").is(":checked"),
                    editingCourse: $(this).find(".editingCourse").is(":checked")
                });
            });
            data.kingUser = JSON.stringify(data.kingUser);
        }
        if($("#list_exam").children("tr").length==0){
        	$("#passScoreErr").css("display","block");
    		$("#passScoreErr").html("请为本作业包添加作业后，再保存。");
    		return false;
        }
        ///保存作业包素材的方法
        $.ajax({
        	  type:"post",
			  url:"${ctx}/ajax/taskPaper/updateTaskPaperMaterial",
			  async:false,
			  data:data,
			  dataType:'json',
			  success: function(rsult){
				  window.location.href="${ctx}/material/findList?order=FDCREATETIME&fdType="+$("#fdType").val();
			  }
		}); 
    }
    $('#formEditDTotal a[data-toggle="tab"]').on('click', function (e) {
        var href = 	e.target.href.split("#").pop();
        $("#permission").val(href);
    });
    
    var allUserData ;
    
    $("#addUser").autocomplete("${ctx}/ajax/user/findByName",{
        formatMatch: function(item) {
            return item.name + item.mail + item.org + item.department;
        },
        formatItem: function(item) {
        	var photo;
			if(item.imgUrl.indexOf("http")>-1){
				photo=item.imgUrl;
			}else{
				photo="${ctx}/"+item.imgUrl;
			}		
            return '<img src="'
                    + (photo) + '" alt="">'
                    + item.name + '（' + item.mail + '），'
                    + item.org + '  ' + item.department;
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
		matchContains:true ,
		max: 10,
		scroll: false,
		width:748
    }).result(function(e,item){
		var flag = true;
		$("#addUser").next(".error").remove();
		$("#list_user>tr").each(function(){
			if($(this).attr("data-fdid")==item.id){
				$("#addUser").after('<label class="error" for="addKey">不能添加重复的关键词!');;
				$("#addUser").val("");
				flag = false;
			}
		});
		if(flag){
			$(this).val(item.name);
			$("#list_user").append(listUserKinguserFn(item))
			.sortable({
				handle: '.state-dragable',
				forcePlaceholderSize: true
			})
			.find("a.icon-remove-blue").bind("click",function(e){
				e.preventDefault();
				$(this).closest("tr").remove();
				initScore();
			});
			$("#addUser").val("");
		}
     });


    //添加作业事件
    $("#addExam").bind("click",function(e){
        loadExamPage();
    });

    //打开作业列表中的作业详情
    $("#list_exam>tr>.tdTit .pr a").click(function(e){
        e.preventDefault();
        var id = $(this).closest("tr").attr("data-fdid");
        loadExamPage(id);
        $("#goTop").trigger("click");
        //
    });
    /*加载作业页面*/
    function loadExamPage(fdid){
    	var materialName = $("#examPaperName").val();
    	var examPaperIntro = $("#examPaperIntro").val();
    	var studyTime = $("#examPaperTime").val();
    	
        if(materialName==""||materialName==null){
        	$("#examPaperNameErr").html("请先设置作业包名称");
        	$("#examPaperNameErr").css("display","block");
        	return;
        }
        var data = {};
        if( !fdid){//添加作业数据
            data = {
                examScoreTotal: 20,
                examType: "uploadWork"
            };
        } else {
        	//当点击list页面的作业项时，初始化作业数据
        	$.ajax({
        		  type:"post",
				  url:"${ctx}/ajax/taskPaper/getTaskByTaskId",
				  async:false,
				  data:{id: fdid},
				  dataType:'json',
				  success: function(rsult){
						 data = rsult;
				  }
			});
        }
        var fdName = $("#examPaperName").val();
    	if(fdName.length>14){
			  fdName = fdName.substring(0, 14)+"...";
		  } 
        data.examPaperName = fdName;  //当前作业包名称
        data.timeLine = {//时间轴控件 配置数据
            width: 670, //时间轴控件 宽度
            total: 20, //总格数
            curPos: data.examScore, //当前位置
            span: 1, //每格的进制
            unit: "分"
        }
        $("#rightCont").html(examDetailFn(data));

        //应用拖放效果
        $(".list-attachment").sortable({
            handle: '.state-dragable'
        })
            //移除列表项
                .delegate(".item-ctrl>.icon-remove-blue","click",function(e){
                    e.preventDefault();
                    $(this).closest("li").remove();
                });


        $(".timeLine>a").tooltip()
                .click(function(e){//分数控制
                    e.preventDefault();
                    $(this).prevAll("a").add(this).addClass("active");
                    $(this).nextAll("a").removeClass("active");
                    $("#examScore").val( $(this).children(".num").text());
                });

        $("#submitTask").click(function(e){
        	submitForm();
        });
        $("#saveExam").click(function(e){
            $("#submitTask").trigger("click");
        });

        //作业类型选择
        $('[data-toggle="buttons-radio"]>.btn').click(function(){
            $("#examType").val(this.id);
        });

        /*提交作业详情表单函数*/
        function submitForm(){
        	if(!$("#formEditDTotal").valid()){
        		return;
        	}
            var data = {
            	materialId:$("#materialId").val(),
                examPaperIntro: examPaperIntro==""?"作者很懒，没有写作业包简介。":examPaperIntro,
                studyTime:studyTime,
            	materialName:materialName,
                examType: $("#examType").val(),
                examName: $("#examName").val(),
                examStem: $("#examStem").val()==""?"作者很懒，没有写作业简介。":$("#examStem").val(),
                examScore: $("#examScore").val(),
                listAttachment: []
            };
            if($("#listAttachment>li").length){
                //push 附件列表数据
                $("#listAttachment>li").each(function(i){
                    data.listAttachment.push({
                        id: $(this).attr("data-fdid"),
                        index: i,
                        url: $(this).find(".name").attr("href")
                    });
                });
                data.listAttachment =  JSON.stringify(data.listAttachment);
            }
            if (JSON.stringify(data.examScore) == '""') {
				$("#examScoreErr").html("请设置分数");
				$("#examScoreErr").css("display", "block");
				return;
			}
            //console.log(data);
            //ajax
            $.ajax({
				  url: "${ctx}/ajax/taskPaper/saveOrUpdateTaskPaper?taskId="+fdid+"&materialId="+$("#materialId").val(),
				  async:false,
				  data:data,
				  type: "POST",
				  dataType:'json',
				  success: function(result){
					  window.location.href="${ctx}/material/materialFoward?fdId="+result.materialId+"&fdType="+$("#fdType").val();
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
        'fileSizeLimit':20971520,// 限制文件大小为2G
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
    }
});
//初始化作业列表
function initExamPaperList(){
	var id = $("#materialId").val();
	if(id!==null&&id!=""){
		var itemExamDetailFn = doT.template(document.getElementById("taskPaperListTemplate").text);
	    $.ajax({
	    	  type: "POST",
			  url: "${ctx}/ajax/taskPaper/getTaskByMaterialId?materialId="+id,
			  async:false,
			  dataType : 'json',
			  success: function(result){
				 var html = "";
				 for(var i in result){
					  html += itemExamDetailFn(result[i]);
				 }
				 if(result.length==0){
					$("#list_exam_table").css("display","none");
				 }else{
					$("#list_exam_table").css("display","table");
				 }
				  $("#list_exam").html(html); 
				  initScore();
			  }
		});
	}
}

function initScore(){
	var totalScore = 0;
	var count = 0;
	for(var i=0 ; i<$("#list_exam input").length ;i++){
		count++;
		var isNum =  /^[0-9]*[0-9]$/;  
		var inputValue = $("#list_exam input :eq("+i+")").val();
		if(isNum.test(inputValue)){
			totalScore = totalScore +parseInt(inputValue);
		}
	}
	$("#count").html(count);
	$("#totalScore").html(totalScore);
}
</script>
</body>
</html>
