<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<!-- 评论条目模板 -->
<script id="commentLineTemplate" type="text/x-dot-template">
  <li class="media" dataId="{{=it.fdId}}">
            <a href="${ctx}/course/courseIndex?userId={{=it.userId}}" class="pull-left">
			<img src="{{?it.fdUserURL.indexOf('http')>-1}}{{=it.fdUserURL}}{{??}}${ctx}/{{=it.fdUserURL}}{{?}}" />
			</a>
           	 <div class="media-body">
				
				{{?!it.isAnonymous}}
					<div class="media-heading"><span class="name"> {{=it.no}}# {{=it.fdUserName}}</span>（{{=it.fdUserEmail}}）    来自 {{=it.fdUserDept}}
				{{?}}
				{{?it.isAnonymous}}
					<div class="media-heading"><span class="name"> {{=it.no}}# 匿名用户</span>
				{{?}}
				{{?it.isShowScore}}
                        <div class="rating-view">
                                    <span class="rating-all">
                                        {{ for(var i=1; i<=5; i++){ }}
                                            <i class="icon-star{{?i <= it.score}} active{{?}}"></i>
                                        {{ } }}
                                    </span>
                            <b class="text-warning">
							{{?(it.score+"").length==1}}
								{{=it.score}}.0
							{{??}}
								{{=it.score}}
							{{?}}
							</b>
                        </div>
                 {{?}}
              </div>
              <p class="comt-content">
					{{=it.content}}
			  </p>
              <div class="media-footing">
				<div class="clearfix">
              		<span class="pull-left"><i class="icon-time"></i>{{=it.fdCreateTime}}</span>
              		 <div class="pull-right btns-comt">
						<a href="javascript:void(0)" class="btnPraise{{?!it.canSport}} active{{?}}"><i class="icon-thumbs-up"></i><span class="num">{{=it.supportCount}}</span></a>
                        <a href="javascript:void(0)" class="btnWeak{{?!it.canOppose}} active{{?}}"><i class="icon-thumbs-down"></i><span class="num">{{=it.opposeCount}}</span></a>
                		<a href="javascript:void(0)" class="btnComment"><i class="icon-dialog"></i><span class="num">{{=it.replyCount}}</span></a>
						{{?it.canDelete}}
						<a href="javascript:void(0)" class="btndeleteM" >删除</a>
						{{?}}
               	</div>
              </div>
            </div>
       </li> 
</script>
<script id="formReplyCommentTemplate" type="x-dot-template">
        <div class="form-reply">
            <form id="formReply">
					<input type="hidden" id="replyCommHide" value="回复（{{=it.name}}）："/>
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
<script src="${ctx}/resources/js/doT.min.js"></script>
    <div class="section mt20" id="commentDiv">
       <div class="hd">
                <div class="tit-icon_bg"><i class="icon-white-info"></i></div>
                <h4>全部评论</h4>
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
        <ul class="media-list comment-list" id="commentListUl">
        </ul>
         <div class="comment-list-bottom clearfix">
         	<div class="pages pull-right">
            	<div class="span2">
                	第<span id="pageLine2"></span> / <span id="pageTotal2"></span> 条
                </div>
                <div class="btn-group">
                	<button id="gotoBefore2" class="btn btn-primary"><i class="icon-chevron-left icon-white"></i></button>
                    <button id="gotoNext2" class="btn btn-primary"><i class="icon-chevron-right icon-white"></i></button>
                    
                </div>
            </div>
        </div>
    </div>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
////////////////////////初始化开始
var pageSize=10;
initCommentLines("${param.modelName}","${param.modelId}",1);
initCommentPageInfo("${param.modelName}","${param.modelId}",1);
var formReplyCommentFn = doT.template(document.getElementById("formReplyCommentTemplate").text);
////////////////////////初始化结束
//初始评论列表化页面
function initCommentLines(modelName,modelId,pageNo){
	$.ajax({
		  url: "${ctx}/ajax/message/findCommentByModelId",
		  async:false,
		  dataType : 'json',
		  data:{
			  modelName :modelName,
			  modelId :modelId,
			  pageNo:pageNo,
			  pageSize:pageSize
		  },
		  type:"post",
		  success: function(result){
			  var html ="";
			  var commentLineTemplate = doT.template(document.getElementById("commentLineTemplate").text);
			  for(var i=0;i<result.listComments.length;i++){
				  html = html + commentLineTemplate(result.listComments[i]);
			  }
			  $("#commentListUl").html(html);
			  if(result.listComments.length==0){
				  $("#commentDiv").addClass("hide");
			  }else{
				  $("#commentDiv").removeClass("hide");
			  }
		  }
	});
	$(".btnComment").bind("click",function(){
		var $this = $(this);
		 //if($("#formReply").length){
         	//jalert("请先保存其它回复");
         //} else {
        	 $("#formReply .btn-cancel").closest(".form-reply").remove();
             $this.removeClass("active");
             
             var $mediaBody = $this.closest(".media-body");
             var itemId = $this.closest(".media").attr("dataId");
             var toName =  $mediaBody.find(".media-heading>.name").text();
             $this.closest(".clearfix").after(formReplyCommentFn({
                 name: toName
             }));
             $("#formReply").validate({
                 submitHandler: function(form){
                    $.ajax({
                 		  url: "${ctx}/ajax/message/addCourseMessagesMessage",
                 		  async:false,
                 		  data:{
                 			materialId :"${param.modelId}",
                 			fdContent :($("#replyCommHide").val()+$("#replyComm").val()),
                 			messageId :itemId,
                 		  },
                 		  success: function(result){
                 			 initCommentLines("${param.modelName}","${param.modelId}",1);
                 			 initCommentPageInfo("${param.modelName}","${param.modelId}",1);
                 			 var sTop = $("#commentDiv").offset().top - 60;
                			 $("html,body").animate({scrollTop: sTop},sTop*1,"swing");
                			 jalert_tips("回复成功");
                 		  }
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
	});
	$(".btnPraise").bind("click",function(){
		 var $this = $(this);
		 var itemId = $this.closest(".media").attr("dataId");
		 var $num = $this.children(".num");
		 if(!$this.hasClass("active")){//赞
        	var pushok;
        	$.ajax({
          		  url: "${ctx}/ajax/message/supportOrOpposeMessage",
          		  async:false,
          		  data:{
          			messageId :itemId,
          			fdType :"01",
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
        } 
	});
	$(".btnWeak").bind("click",function(){
		var $this = $(this);
		var itemId = $this.closest(".media").attr("dataId");
		var $num = $this.children(".num");
		var pushok;
    	$.ajax({
      		  url: "${ctx}/ajax/message/supportOrOpposeMessage",
      		  async:false,
      		  data:{
      			messageId :itemId,
      			fdType :"02",
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
	});
	$(".btndeleteM").bind("click",function(){
		var $this = $(this);
		var itemId = $this.closest(".media").attr("dataId");
		jalert("您确定删除该评论吗？",function(){
			$.ajax({
	    		  url: "${ctx}/ajax/message/removeMessage",
	    		  async:false,
	    		  data:{
	    			  messageId :itemId,
	    		  },
	    		  success: function(result){
	    			 initCommentLines("${param.modelName}","${param.modelId}",1);
	      			 initCommentPageInfo("${param.modelName}","${param.modelId}",1);
	    		  }
			});
		});
	});
}
//初始化分页信息
function initCommentPageInfo(modelName,modelId,pageNo){
	$.ajax({
		  url: "${ctx}/ajax/message/getCommentPageInfo",
		  async:false,
		  dataType : 'json',
		  data:{
			  modelName:modelName,
			  modelId :modelId,
			  pageNo:pageNo,
			  pageSize:pageSize,
		  },
		  success: function(result){
			 //alert(JSON.stringify(result));
			 $("#pageLine1").html(" "+result.startLine+" - "+result.endLine+" ");
			 $("#pageLine2").html(" "+result.startLine+" - "+result.endLine+" ");
			 $("#pageTotal1").html(result.totalSize);
			 $("#pageTotal2").html(result.totalSize);
			 //第一页
			 if(result.pageNo==1){
				 $("#gotoBefore1").unbind();
				 $("#gotoBefore2").unbind();
				 $("#gotoNext1").bind("click",function (){gotoNext(result.pageNo);});
				 $("#gotoNext2").bind("click",function (){gotoNext(result.pageNo);});
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
				 $("#gotoBefore1").bind("click",function (){gotoBefore(result.pageNo);});
				 $("#gotoBefore2").bind("click",function (){gotoBefore(result.pageNo);});
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
				 $("#gotoNext1").bind("click",function (){gotoNext(result.pageNo);});
				 $("#gotoNext2").bind("click",function (){gotoNext(result.pageNo);});
				 $("#gotoBefore1").bind("click",function (){gotoBefore(result.pageNo);});
				 $("#gotoBefore2").bind("click",function (){gotoBefore(result.pageNo);});
				 $("#gotoBefore1").removeAttr("disabled");
				 $("#gotoBefore2").removeAttr("disabled");
				 $("#gotoNext1").removeAttr("disabled");
				 $("#gotoNext2").removeAttr("disabled");
			 }
			 
		  }
	});
	bindSOEvent();
}
//进入下一页方法
function gotoBefore(pageNo){
	initCommentLines("${param.modelName}","${param.modelId}",pageNo-1);
	initCommentPageInfo("${param.modelName}","${param.modelId}",pageNo-1);
}
//进入上一页方法
function gotoNext(pageNo){
	initCommentLines("${param.modelName}","${param.modelId}",pageNo+1);
	initCommentPageInfo("${param.modelName}","${param.modelId}",pageNo+1);
}
//绑定支持反对事件
function bindSOEvent(){
	
}

</script>

