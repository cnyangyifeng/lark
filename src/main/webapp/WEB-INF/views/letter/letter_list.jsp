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
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->

    <!-- 私信列表 模板 -->
    <script id="listMsgTemplate" type="text/x-dot-template">
        {{~it :item}}
        <li{{?item.hasUnread}} class=""{{?}}><a href="${ctx}/letter/letterDetail?fdId={{=item.id}}">
            <img src="{{?item.user.imgUrl.indexOf('http')>-1}}{{=item.user.imgUrl}}{{??}}${ctx}/{{=item.user.imgUrl}}{{?}}" class="imgFace" alt="{{=item.user.name}}"/>
            <span class="org">{{=item.user.name}}（{{=item.user.mail}}）    
            <span title="{{=item.user.org}}">{{=item.user.org.length>10?item.user.org.substr(0,9)+'...':item.user.org}}</span>  
            <span title="{{=item.user.department}}">{{=item.user.department.length>10?item.user.department.substr(0,9)+'...':item.user.department}}</span></span>
            <span class="divider">|</span>
            <span class="counting">全部 <span class="text-info">{{=item.numTotal}}</span> 条 ，{{?item.hasUnread}}其中未读
            <span class="text-info">{{=item.numUnread}}</span> 条{{??}}没有未读信息{{?}}</span>
            <span class="content">{{?item.hasUnread}}<strong>{{=item.msg}}</strong>{{??}}{{=item.msg}}{{?}}</span>
            <span class="date"><i class="icon-time"></i>{{=item.timeMsg}}</span>
                            <span class="btns">
                                <span class="hide">
                                    <button type="button" id="deleteCurrent" data-fdId="{{=item.id}}" class="btn btn-link empty">清空对话</button>
                                    <b>|</b>
                                </span>
                                <button type="button" class="btn btn-link mailTo" data-target="mailto:{{=item.user.mail}}"><i class="icon-envelope"></i>给TA发邮件</button>
                            </span>
        </a></li>
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
                <span class="muted">我正在看：</span>    我的私信
                <span class="counting">（全部 <span class="text-info" id="totalNum"></span> 条 ，其中未读 <span class="text-info" id="unReadNum"></span> 条）</span>
                <div class="backHome">
                    <a href="${ctx}/course/courseIndex"><span class="muted">返回</span>个人主页 <i class="icon-home icon-white"></i> </a>
                </div>
            </div>
            <div class="page-body">
                <section class="box-control">
                    <div class="hd">
                        <div class="btn-toolbar">
                            <button class="btn btn-primary btn-large" onclick="updateStatus()" type="button">全部标记为已读</button>
                            <div class="btn-group pull-right">
                                <a class="btn btn-link" href="${ctx}/letter/sendLetter"><i class="icon-msg-edit"></i>发私信</a>
                                <span class="divider">|</span>
                                <a class="btn btn-link" id="deleteAll" href="javascript:void(0)">清空所有私信</a>
                            </div>
                        </div>
                    </div>
                </section>
                <section class="section listWrap listMsgWrap">
                    <ul class="nav list" id="listMsg">
                    </ul>
                </section>
                <div class="pages" id="pages">
                    
                </div>
            </div>
	    </section>
	</section>

</section>
<script type="text/javascript" src="${ctx}/resourcesjs/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resourcesjs/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resourcesjs/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#deleteAll").click(function(e){
       e.preventDefault();
       jalert("确定清空所有私信吗？",function(){
    	   confirmDeleteAll();
       });
    }); 
});
function confirmDeleteAll(){
	$.ajax({
   	      type:"get",
		  url:"${ctx}/ajax/letter/deleteAllLetter",
		  dataType:'json',
		  success: function(result){
			  window.location.href="${ctx}/letter/findLetterList";
		  }
 	}); 
}
//改变所有私信状态为已读
function updateStatus(){
	$.ajax({
 	      type:"get",
		  url:"${ctx}/ajax/letter/updateletterStatus",
		  success: function(result){
			  window.location.href="${ctx}/letter/findLetterList";
		  }
	}); 
}
</script>
<script type="text/javascript">
//私信列表 模板函数
var listMsgFn = doT.template(document.getElementById("listMsgTemplate").text);
//底部翻页
var pageendFn= doT.template(document.getElementById("pageEndTemplate").text);

var listMsgData = [];

$(function(){
    $.Placeholder.init();
    //未读私信
    $.ajax({
   	  type:"get",
	  url:"${ctx}/ajax/letter/getUnReadNum",
	  success: function(result){
			  $("#unReadNum").html(result);
	  }
 	}); 
    //已读私信
    $.ajax({
     	  type:"get",
  		  url:"${ctx}/ajax/letter/getTotalNum",
  		  success: function(result){
  			  $("#totalNum").html(result);
  		  }
   	}); 
    pageNavClick(1);//初始化列表
});
//翻页
function pageNavClick(pageNo){
    $.ajax({
    	  type:"get",
		  url:"${ctx}/ajax/letter/findLetterList",
		  data:{
			  pageNo:pageNo
		  },
		  dataType:'json',
		  success: function(result){
			listMsgData = result.listMsgData;
			 $("#listMsg").html(listMsgFn(listMsgData));
			//alert(JSON.stringify(listMsgData));
			$("#pages").html(pageendFn(result.paging));
			$("#listMsg").find(".btns .btn").click(function(e){
		        e.preventDefault();
		        if($(this).hasClass("empty")){//清空当前对话
		            //alert("清空对话"+$(this).attr("data-fdId"));
		        	var tempId = $(this).attr("data-fdId");
		        	jalert("确定清空当前对话？",function(){
			        	 confirmDelete(tempId);
			         });
		        } else if($(this).hasClass("mailTo")){//发送邮件给TA
		            window.location.href = $(this).attr("data-target");
		        }
		    }); 
		  }
  	}); 
}
function confirmDelete(fdId){
	$.ajax({
   	  type:"get",
		  url:"${ctx}/ajax/letter/deleteLetterByUserId",
		  data:{
			fdId:fdId, 
		  },
		  dataType:'json',
		  success: function(rsult){
			 window.location.href="${ctx}/letter/findLetterList";
		  }
 	}); 
}
</script>
</body>
</html>
