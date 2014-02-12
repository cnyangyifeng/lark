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
                <img src="{{=it.imgUrl || 'images/temp-face36.jpg'}}" alt="">{{=it.name}}（{{=it.mail}}），{{=it.org}} {{=it.department}}
            </div>
        </td>
        <td><input type="checkbox" onclick='return false' {{?it.tissuePreparation!=false}}checked{{?}}  class="tissuePreparation" /></td>
        <td><input type="checkbox" onclick='return false' {{?it.editingCourse!=false}}checked{{?}}  class="editingCourse" /></td>
    </tr>
</script>


    <!-- 时间轴 模板 -->
    <script id="timeLineTemplate" type="text/x-dot-template">
		<!--{{?!it.timeLine}}
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
        #}}-->
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
                <{{?param.url}}a href="{{=param.url}}" target="_blank" {{??}}div{{?}} class="name">{{=param.name}}</{{?param.url}}a{{??}}div{{?}}>
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
		            <a href="#">{{=it.subject}}</a>
		        </div>
		    </td>
		    <td><input type="text" readonly  value="{{=it.score}}" data-toggle="tooltip" title="输入数字做为分值" class="itemScore input-mini">分</td>
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
                <h4 id="fdName">${materialInfo.fdName}</h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" disabled id="exportExamPaper" type="button">导出</button>
                </div>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" method="post">
                    <section class="section">
                        <div class="control-group">
                            <label class="control-label" for="examPaperName">作业包</label>
                            <div class="controls">
                                <input value="${materialInfo.fdName}"  id="examPaperName"class="span6"
                                       name="examPaperName" type="text" readonly>
                                <span class="date" id="createTime">
                                 <fmt:formatDate value="${materialInfo.fdCreateTime}" pattern="yyyy/MM/dd hh:mm aa"/>
                                </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="examPaperIntro">作业包<div>简介</div></label>
                            <div class="controls">
                            <textarea placeholder="非必填项" rows="4"
                                      class="input-block-level" id="examPaperIntro"
                                      name="examPaperIntro" readonly>${materialInfo.fdDescription}</textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >建议时间 <small>(单位天)</small></label>
                            <div class="controls">
                            <input id="examPaperTime" readonly="true"  digits="true" min="0" value="${materialInfo.fdStudyTime}" max="30" class="input-block-level" type="text">
                            </div>
                        </div>

                    </section>
                    <section class="section mt20">
                        <div class="hd">
                            <label for="passScore" class="miniInput-label">
                                                                       作业列表（共计<span id="count"></span>题，满分<span id="totalScore"></span>分，及格 
                                <input class="input-mini" id="passScore" readonly value="${materialInfo.fdScore}" name="passScore"  type="text"/>      分）
                            </label>
                        </div>
                        <div class="bd">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>作业</th>
                                    <th>分数</th>
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
                                     readonly value="${materialInfo.fdAuthor}" name="author" type="text">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="authorIntro">作者简介</label>
                            <div class="controls">
                            <textarea placeholder="非必填项" rows="4"
                                      class="input-block-level" id="authorIntro"
                                      name="authorIntro" readonly >${materialInfo.fdAuthorDescription}</textarea>
                            </div>
                        </div>
                    </section>
                    
                </form>
            </div>
	    </section>
	</section>
<input type="hidden" id="materialId" value="${materialInfo.fdId}"/>
</section>
<script type="text/javascript" src="${ctx}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    $.Placeholder.init();
    $('.itemScore[data-toggle="tooltip"]').tooltip({
        trigger: "focus"
    });
  //时间轴 模板函数
    var timeLineFn = doT.template(document.getElementById("timeLineTemplate").text);
	 $("#examPaperTime").after(timeLineFn({//时间轴控件 配置数据
	        width: 670, //时间轴控件 宽度
	        total: 7, //总格数
	        curPos: '${materialInfo.fdStudyTime}', //当前位置
	        span: 1, //每格的进制
	        unit: "天"
	    }));
});
/**
 * Created by wqh on 13-11-1.
 */
$(function(){
    //授权管理 用户列表 模板函数
    var listUserKinguserFn = doT.template(document.getElementById("listUserKinguserTemplate").text);
    //初始化作业列表
    initExamPaperList();
    //初始化授权人员列表
});
//初始化作业列表
function initExamPaperList(){
	var id = $("#materialId").val();
	if(id!==null&&id!=""){
		var itemExamDetailFn = doT.template(document.getElementById("taskPaperListTemplate").text);
	    $.ajax({
			  url: "${ctx}/ajax/taskPaper/getTaskByMaterialId?materialId="+id,
			  async:false,
			  dataType : 'json',
			  success: function(result){
				  var html = "";
				 for(var i in result){
					  html += itemExamDetailFn(result[i]);
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
		totalScore = totalScore +parseInt($("#list_exam input :eq("+i+")").val());
	}
	$("#count").html(count);
	$("#totalScore").html(totalScore);
}

</script>
</body>
</html>
