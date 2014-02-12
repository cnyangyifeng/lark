<%@page import="cn.me.xdf.model.course.CourseInfo"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script id="xingxingTemplate" type="text/x-dot-template">
	<span class="rating-all">
          {{ for(var i=1; i<=5; i++){ }}
                <i class="icon-star{{?i <= it.score}} active{{?}}"></i>
          {{ } }}
    </span>
    <b class="text-warning">{{?(it.score+"").length==1}} {{=it.score}}.0 {{??}} {{=it.score}} {{?}} </b>
</script>
<script src="${ctx}/resources/js/doT.min.js"></script>
<div class="section mt20">
 		<div class="hd">
		<h2 class="h2_r">评分 </h2>
            	<div class="ab_l rating-view" id="ratingDo">
              	<span class="rating-all">
             		<i class="icon-star" ></i><i class="icon-star" ></i><i class="icon-star" ></i><i class="icon-star" ></i><i class="icon-star" ></i>
                  </span>
                  <span class="text-warning" id="myScore">0.0</span>
              </div>
	</div>
          <div class="bd comment-form box-pd15">
          	<textarea name="" onkeydown="return pushMessage();" id="courseMessage" class="input-block-level" placeholder="感谢您的宝贵意见与建议" cols="10" rows="4"></textarea>
              <button class="btn btn-primary btn-block" id="addMessage">我来说两句</button>
          </div>                    
</div>
<script type="text/javascript">
//$.Placeholder.init();
//初始化评论按钮
$("#addMessage").bind("click",function(){
	addMessagemethod();
});

//课程评论
function addMessagemethod(){
	var mess = $("#courseMessage").val();
	if(mess==""||mess==null){
		jalert_tips("请输入评论信息");
		return;
	}
	if(mess.length>200){
		jalert_tips("请输入评论信息过长");
		return;
	}
	$("#courseMessage").val("");
	$.ajax({
		  url: "${ctx}/ajax/message/addCourseMessage",
		  async:false,
		  type: "post",
		  dataType : 'json',
		  data:{
			  courseId:"${param.courseId}",
			  fdContent:mess,
		  },
		  success: function(result){
			//jalert("评论发布成功");
			$("#courseMessage").val("");
			initCommentLines("<%=CourseInfo.class.getName()%>","${param.courseId}",1);
			initCommentPageInfo("<%=CourseInfo.class.getName()%>","${param.courseId}",1);
			var sTop = $("#commentDiv").offset().top - 60;
			$("html,body").animate({scrollTop: sTop},sTop*1,"swing");
			jalert_tips("评论成功");
		  }
	});
	
}
//初始化分数列表
$.ajax({
	  url: "${ctx}/ajax/score/canPushScoreToCourse",
	  async:false,
	  type: "post",
	  dataType : 'json',
	  data:{
		  fdModelId:"${param.courseId}",
	  },
	  success: function(result){
		  $("#ratingDo  i").each(function(index){	
				$(this).bind("mouseover",function(){
					$(this).addClass("active").prevAll().addClass("active");
					$(this).nextAll().removeClass("active");
					$("#myScore").html((index+1)+".0");
				});
				$(this).bind("click",function(){
					scoreing();
				});
				$(this).bind("mouseout",function(){
					  setMyScore(0);
				});
		  });
		  if(result!=-1){
			  $("#ratingDo  i").each(function(index){	
				  $(this).bind("mouseout",function(){
					  setMyScore(result);
				});
			  }); 
			  setMyScore(result);
		  }
	  }
});


//课程打分
function scoreing(){
	 var s = parseInt($("#myScore").html());
	 $.ajax({
		  url: "${ctx}/ajax/score/pushScoreToCourse",
		  async:false,
		  type: "post",
		  dataType : 'json',
		  data:{
			  fdModelId:"${param.courseId}",
			  fdScore:s,
		  },
		  success: function(result){
			  $("#goTop").trigger("click");
			  //$("#ratingDo  i").unbind();
			  var score = ($("#myScore").html());
			  $("#ratingDo  i").each(function(index){	
					$(this).bind("mouseout",function(){
						  setMyScore(score);
					});
			  }); 
			  setMyScore(score);
			  setCourseScore();
			  jalert_tips("评分成功");
		  }
	});
}
//设置当前用户的评分信息
function setMyScore(score){
	var n = score;
	$("#ratingDo  i").each(function(index){	
		if(index<n){
			$(this).addClass("active").prevAll().addClass("active");
		}else{
			$(this).removeClass("active");
		}
		var so=n+"";
		if(so.length==1){
			$("#myScore").html(score+".0");
		}else{
			$("#myScore").html(score);
		}
		
	}); 
}
//重置课程情分信息
function setCourseScore(){
	var courseScore=0;
	 $.ajax({
		  url: "${ctx}/ajax/score/getCourseScoreStatisticsByfdModelId",
		  async:false,
		  type: "post",
		  dataType : 'json',
		  data:{
			  fdModelId:"${param.courseId}",
		  },
		  success: function(result){
			  courseScore=result[0].fdAverage;
		  }
	});
	var xingxingTemplate = doT.template(document.getElementById("xingxingTemplate").text);
	var data={};
	data.score=courseScore;
	$("#courseScore").html(xingxingTemplate(data));
}

function pushMessage(){
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (keyCode == 13) {
		addMessagemethod();
		return false;
	}
}
</script>