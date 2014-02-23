<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/webim.css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->

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
                   {{?dia.isMe}}
                     <img src="" name="send" class="dia_col webim_dia_face" alt=""/>
                   {{??}}
					 <img src="" name="accept" class="dia_col webim_dia_face" alt=""/>
                   {{?}}
                    <div class="dia_col webim_dia_bg">
                        <div class="dia_caret"><b></b></div>
                        <div class="webim_dia_cont">
                            {{=dia.msg}}
                        </div>
                        <div class="va_bottm">
                            <span class="webim_date dia_col">{{=dia.time}}</span>
                            <a href="#" data-fdId="{{=dia.id}}" class="remove dia_col">删除</a>
                        </div>
                    </div>
                </div>
                {{~}}
            </div>
        </div>
        {{~}}
    </script>
<script id="pageEndTemplate" type="text/x-dot-template">
	<div class="btn-group dropup">
	 <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage<=1}}disabled{{?}} onclick='pageNavClick({{=it.currentPage-1}})'>
	 <i class="icon-chevron-left icon-white"></i></button>
	{{ for(var i=it.StartPage;i<=it.EndPage;i++){ }}
			{{?i==it.currentPage}}
				<button class="btn btn-primary btn-num active" type="button">{{=i}}</button>
			{{??}}
				<a  onclick="pageNavClick({{=i}})">
				<button class="btn btn-primary btn-num" type="button">{{=i}}</button>
				</a>
			{{?}}
	{{}}}
	 <button class="btn btn-primary btn-num  dropdown-toggle"  data-toggle="dropdown" type="button">
                            <span class="caret"></span></button>
	     <ul class="dropdown-menu pull-right">
		{{ for(var j=it.StartOperate;j<=it.EndOperate;j++){ }}
			<li><a href="javascript:void(0)" onclick="pageNavClick({{=j}})">{{=j*10-10+1}}-{{=j*10}}</a></li>
		{{}}}
		</ul>
	 <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage == it.totalPage}} disabled {{?}} onclick='pageNavClick({{=it.currentPage+1}})'><i class="icon-chevron-right icon-white"></i></button>
</div>
</script>
    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>


<section class="container">
	<section class="clearfix mt20">
	  <section class="col-left pull-left">
    	<%@ include file="/WEB-INF/views/letter/menu.jsp" %>
	  </section>
		<section class="w790 pull-right" id="rightCont">
            <div class="page-header bder2" data-spy="affix" data-offset-top="20">
                <span class="muted">我正在看：</span>我与<a href="${ctx}/course/courseIndex?userId=${person.fdId}"> ${person.fdName} </a>的对话
                <div class="backHome">
                    <a href="${ctx}/letter/findLetterList"><span class="muted">返回</span>私信<span class="muted">首页</span> <i class="icon-home icon-white"></i> </a>
                </div>
            </div>
            <div class="page-body">
                <section class="section pd20 box-control">
                    <form action="#" class="formMsg" id="formMsg">
                        <div class="rowLabel">
                            <label for="chatMsg">
                                <span class="muted">给</span> ${person.fdName}（${person.fdEmail}）    ${org} ${deptName}
                            </label>
                            <div class="btns">
                                <a href="mailto:${person.fdEmail}"><i class="icon-envelope"></i>给TA发邮件</a>
                                <span class="divider">|</span>
                                <a onclick="deleteAll()" href="javascript:void(0)" class="empty">清空所有对话</a>
                            </div>
                        </div>
                        <textarea required  rows="3"
                                  class="input-block-level" id="body"
                                  name="body" style="width: 750px"></textarea>
                        <div class="rowLabel">
                            <span class="dt">TA的电话</span> <i class="icon-tel"></i><span class="muted">${person.fdWorkPhone}</span>
                            <div class="btns">
                                <button class="btn btn-primary btn-large" type="submit">发送</button>
                            </div>
                        </div>
                    </form>
                    <div class="webim_chat_list" id="listtChatLog">
                    </div>
                    <div class="pages" id="pages">
                        
                    </div>
                </section>
            </div>
	    </section>
	</section>
<input type="hidden" id="fdId" value="${person.fdId}">
</section>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript">
function deleteAll(){
	jalert("确定所有对话吗？",function(){
		confirmDelete();
    });
}
function confirmDelete(){
	$.ajax({
   	  type:"get",
		  url:"${ctx}/ajax/letter/deleteLetterByUserId",
		  data:{
			fdId:$("#fdId").val(), 
		  },
		  dataType:'json',
		  success: function(rsult){
			 window.location.href="${ctx}/letter/findLetterList";
		  }
 	}); 
}
</script>
<script type="text/javascript">
var acceptUserId= "${param.fdId}";
var listChatData = [];
var sendPoto;
var acceptPoto;
//底部翻页
var pageendFn= doT.template(document.getElementById("pageEndTemplate").text);
//聊天纪录列表 模板函数
var listChatLogFn = doT.template(document.getElementById("listChatLogTemplate").text);

$(function(){
    $.Placeholder.init();
   
    pageNavClick();
    
    
    $.ajax({
	  	  type:"get",
		  url:"${ctx}/ajax/letter/findPersonPoto",
		  data:{
			  fdId:acceptUserId,
		  },
		  dataType:'json',
		  success: function(result){
			  var temp_send = result.sendPoto;
			  if(!(temp_send.indexOf("http") > -1)){
				  temp_send = "${ctx}/"+temp_send;
			  }
			  var temp_accept = result.acceptPoto;
			  if(!(temp_accept.indexOf("http") > -1)){
				  temp_accept = "${ctx}/"+temp_accept;
			  }
			  sendPoto = temp_send;
			  acceptPoto = temp_accept;
			  getPoto();
		  }
	}); 
    
    $("#formMsg").validate({
        submitHandler: function(form){
        	 $.ajax({
           	  type:"post",
       		  url:"${ctx}/ajax/letter/saveLetter",
       		  data:{
       			fdId:$("#fdId").val(), 
       			body:$("#body").val(),
       		  },
       		  dataType:'json',
       		  success: function(rsult){
       			$("#body").val("");
       			pageNavClick();
       		  }
         	}); 
        }
    });
    
});
//删掉单个消息
function deleteSingleLetter(tempId){
	$.ajax({
     	  type:"post",
 		  url:"${ctx}/ajax/letter/deleteSingleLetter",
 		  data:{
 			fdId:tempId,
 			personId:$("#fdId").val(),
 		  },
 		  dataType:'json',
 		  success: function(result){
 			pageNavClick();
 			//alert("22222222222");
 		  }
   	}); 
}
function getPoto(){
	$("#listtChatLog").find("img").each(function(i){
    	var name = $(this).attr("name");
    	if(name=="send"){
    		$(this).attr("src",sendPoto);
    	}else{
    		$(this).attr("src",acceptPoto);
    	}
    });
}
//找出私信详细内容 带翻页
function pageNavClick(pageNo){
	 $.ajax({
	  	  type:"get",
		  url:"${ctx}/ajax/letter/findDetailLetter",
		  cache:false,
		  data:{
			  fdId:acceptUserId,
			  pageNo:pageNo,
		  },
		  dataType:'json',
		  success: function(result){
			if(result!=null){
				$("#listtChatLog").html(listChatLogFn(result.returnlist));
				$("#pages").html(pageendFn(result.paging));
				getPoto();
			}else{
				$("#listtChatLog").html("");
				$("#pages").html("");
			}
			$("#listtChatLog").find(".remove").click(function(e){//删除当前对话
		         e.preventDefault();
		         var $this = $(this);
		         jalert("确定删除该消息吗？",function(){
		        	 var tempId = $this.attr("data-fdId");
		        	 deleteSingleLetter(tempId);
		             //jalert_tips("删除了ID为"+ tempId +"的消息");
		         });
		     });
		  }
	}); 
}
</script>
</body>
</html>
