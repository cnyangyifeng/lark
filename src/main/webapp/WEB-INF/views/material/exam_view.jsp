<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新东方在线教师备课平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
    <script id="examQuestionTemplate" type="text/x-dot-template">
	    <tr data-fdid="{{=it.id}}" >
		    <td class="tdTit">
		        <div class="pr">
		            <div class="state-dragable"><span class="icon-bar"></span><span
		                    class="icon-bar"></span><span class="icon-bar"></span><span
		                    class="icon-bar"></span><span class="icon-bar"></span></div>
		            {{=it.subject}}
		        </div>
		    </td>
		    <td><span class="spans">{{=it.score}}<span>分</td>
		</tr>
    </script>

    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>
<input type='hidden' id='materIalId' value='${param.fdId}' />
<section class="container">
	<section class="clearfix mt20">
	  <section class="col-left pull-left">
    	 <%@ include file="/WEB-INF/views/group/menu.jsp" %>
	  </section>
		<section class="w790 pull-right" id="rightCont">
	        <div class="page-header">
                <a href="${ctx}/material/findList?fdType=08&order=FDCREATETIME" class="backParent">返回测试列表</a>
                <h4 id="examMainName"></h4>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" method="post">
                    <section class="section">
                        <div class="control-group">
                            <label class="control-label" for="examPaperName">试&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卷</label>
                            <div class="controls">
                                <input id="examPaperName" required class="span6" readonly="true"
                                       name="examPaperName" type="text"><span class="date" id="createTime"></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="examPaperIntro">试卷简介</label>
                            <div class="controls"><textarea readonly="true" placeholder="非必填项" rows="4"
                                                            class="input-block-level" id="examPaperIntro"
                                                            name="examPaperIntro"></textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >建议时间 <small>(单位分钟)</small></label>
                            <div class="controls">
                            <input id="mainTimeLine" readonly="true" class="input-block-level" type="text">
                            </div>
                            <!-- <div class="controls">
                                <input name="examPaperTime" id="examPaperTime" value="30" type="hidden"/>
                                    <div id="mainTimeLine" class="timeLine">
                                        <div class="num">0</div>
                                        <a title="15分钟" href="#" class="first"><span class="num">15</span></a>
                                        <a title="30分钟" href="#" ><span class="num">30</span></a>
                                        <a title="45分钟" href="#"><span class="num">45</span></a>
                                        <a title="60分钟" href="#"><span class="num">60</span></a>
                                        <a title="75分钟" href="#"><span class="num">75</span></a>
                                        <a title="90分钟" href="#"><span class="num">90</span></a>
                                        <a title="105分钟" href="#"><span class="num">105</span></a>
                                        <a title="120分钟" href="#"><span class="num">120</span></a>
                                    </div>
                            </div> -->
                        </div>

                    </section>
                    <section class="section mt20">
                        <div class="hd">
                            <label for="passScore" class="miniInput-label">
                                试题列表（共计<span id="questionCount"></span>题，满分<span id="questionScore"></span>分，及格 <span id="passScore"></span>分）
                            </label>
                           
                        </div>
                        <div class="bd">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>试题</th>
                                    <th width="20%">分数</th>
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
                                <input readonly="true" value="" id="author" required class="input-block-level"
                                       name="author" type="text">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="authorIntro">作者简介</label>
                            <div class="controls"><textarea readonly="true" placeholder="非必填项" rows="4"
                                                            class="input-block-level" id="authorIntro"
                                                            name="authorIntro"></textarea>
                            </div>
                        </div>
                    </section>
                </form>
            </div>
	    </section>
	</section>

</section>

<script type="text/javascript" src="${ctx}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css"/>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
$.Placeholder.init();
$.ajax({
	  url: "${ctx}/ajax/material/getMaterial?materialId=${param.fdId}",
	  async:false,
	  dataType : 'json',
	  type: "post",
	  success: function(result){
		  $("#examPaperIntro").val(result.description);
		  $("#passScore").html(result.score);
		  $("#author").val(result.fdAuthor);
		  $("#authorIntro").val(result.fdAuthorDescription);
		  $("#createTime").html(result.createTime);
		  var n = result.time/15;
		  $("#mainTimeLine").val(n);
		  /* $("#mainTimeLine a :lt("+n+")").attr("class","active"); */
	  }
});
//初始化试题列表
initExamQuestions();
function initExamQuestions(){
	var id = $("#materIalId").val();
	if(id!==null&&id!=""){
		var examQuestionTemplate = doT.template(document.getElementById("examQuestionTemplate").text);
	    $.ajax({
			  url: "${ctx}/ajax/material/getExamQuestionSrcByMaterId?materialId=${param.fdId}",
			  async:false,
			  dataType : 'json',
			  type: "post",
			  success: function(result){
				  var html = "";
				  for(var i in result.qusetions){
					  html += examQuestionTemplate(result.qusetions[i]);
				  }
				  $("#list_exam").html(html); 
				  $("#examPaperName").val(result.name);
				  $("#examMainName").html(result.name);
				  initScore();
			  }
		});
	}
}
function initScore(){
	var totalScore = 0;
	var count = 0;
	for(var i=0 ; i<$("#list_exam .spans").length ;i++){
		count++;
		totalScore = totalScore +parseInt($("#list_exam .spans :eq("+i+")").html());
	}
	 $("#questionCount").html(count);
	  $("#questionScore").html(totalScore);
}
</script>
</body>
</html>
