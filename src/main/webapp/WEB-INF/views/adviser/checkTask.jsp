<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新东方在线教师备课平台</title>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/iAmMentor.css" rel="stylesheet" type="text/css">


    <!--老师列表模板-->
    <script id="listTeacherTemplate" type="x-dot-template">
            {{~it.list :item:index}}
            <li class="media" data-fdid="{{=item.id}}">
               <input type="checkbox" name="noteIds" value="{{=item.id}}" style="display: none;"/>
                <div class="pull-left">
                    <a href="${ctx}/course/courseIndex?userId={{=item.user.link}}" class="face" target="_blank">
                        <img src="{{?item.user.imgUrl.indexOf('http')>-1}}{{=item.user.imgUrl}}{{??}}${ctx}/{{=item.user.imgUrl}}{{?}}" class="media-object img-polaroid" alt="头像"/>
                    </a>
                    <a href="#" class="send msg" data-flag="{{=item.user.flag}}" data-fdId="{{=item.user.link}}" ><i class="icon-msg"></i>发私信</a>
                    <a href="mailto:{{=item.user.mail}}" class="send" ><i class="icon-envelope"></i>发邮件</a>
                </div>
                <div class="media-body">
                    <div class="media-heading">
                        <span class="name">{{=item.user.name}}</span>
                        <span class="muted">{{=item.user.department}}</span>
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
						<dt>当前作业包：</dt>
                        <dd>{{=item.taskPaper}}</dd>
                    </dl>
                </div>
                <div class="media-foot">
                    {{?it.status == 'unchecked'}}
                        <div class="toolbar">
                            <a href="#" class="onlineCorrecting"><i class="icon-checkbox"></i>在线批改</a>
                            <em>|</em>
                            <a href="javascript:void(0)" onclick="downloadAtt('{{=item.downloadBoxUrl}}','{{=item.zipname}}');"  class="downloadBox"><i class="icon-downloadbox"></i>打包下载</a>
                        </div>
                    {{??}}
                        <div class="statebar">作业包总分 <strong>{{=item.scoreTotal}}</strong>  分<em>|</em>及格分 <strong>{{=item.scorePass}}</strong>  分<em>|</em>当前批改的总得分 <strong>{{=item.score}}</strong>  分</div>
                        <span class="isPass{{?item.isPass}} pass{{?}}">{{?item.isPass}}通过{{??}}未通过{{?}}</span>
                    {{?}}
                </div>
            </li>
            {{~}}
    </script>
<script id="pageheardTemplate" type="text/x-dot-template">
    <input id="pageIndex" type="hidden" value="{{=it.totalPage}}"/>
	<div class="span2">
 	   第<span > {{=it.startNum}} - {{=it.endNum}}</span> / <span >{{=it.totalCount}}</span> 条
	</div>
	<div class="btn-group">
    <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage <= 1}} disabled {{?}} onclick='pageNavClick({{=it.currentPage-1}})'><i class="icon-chevron-left icon-white"></i></button>
    <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage == it.totalPage}} disabled {{?}} onclick='pageNavClick({{=it.currentPage+1}})'><i class="icon-chevron-right icon-white"></i></button>
	</div>
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
    <script src="${ctx}/resources/js/doT.min.js" type="text/javascript"></script>
</head>

<body>


<!--主体 S-->
<section class="container">	
		<div class="clearfix mt20">
	        <div class="pull-right w760">
	           <div class="page-header" data-spy="affix" data-offset-top="20">
                    <span class="muted">我正在看：</span>批改作业 -
                    <span id="nowSee">
        			<c:if test="${param.fdType!='checked'}">
                        		未批改的作业
                    </c:if>
                    <c:if test="${param.fdType=='checked'}">
                            	已批改的作业
                    </c:if>            
                    </span>
                </div>
                <div class="page-body">

                    <section class="section box-control">
                        <ul class="nav nav-tabs" id="navTabs">
                        	<c:if test="${param.fdType!='checked'}">
                        		<li class="active"><a href="#unchecked" >未批改的</a></li>
                            	<li><a href="#checked" >批改过的</a></li>
                        	</c:if>
                            <c:if test="${param.fdType=='checked'}">
                            	<li><a href="#unchecked" >未批改的</a></li>
                            	<li class="active"><a href="#checked" >批改过的</a></li>
                            </c:if>
                        </ul>
                        <div class="hd">
                            <div class="btn-toolbar">
                                <div class="btn-group">
                                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                                     		   操作
                                        <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#rightCont" onclick="exportData();">导出列表</a></li>
                                        <li><a href="#rightCont" onclick="batchDownload();">打包下载</a></li>
                                    </ul>
                                </div>
                                <form class="toolbar-search">
                                    <input type="text" class="search" id="search" onkeydown="showSearch();" onkeyup="showSearch();">
                                    <i class="icon-search" onclick="pageNavClick('1');"></i>
                                </form>
                            <span class="showState">
                                <span class="muted">当前显示：</span>
                                <span id="markshow"><a id="containkey"href="#">全部条目</a></span>
                            </span>
                                <a class="btn btn-link" href="#rightCont" href="javaScript:void(0);" onclick="clearserach();">清空搜索结果</a>
                            </div>
                        </div>
                        <div class="bd">
                            <div class="btn-toolbar">
                                <label class="muted">排序</label>
                                <div class="btn-group btns-radio" data-toggle="buttons-radio">
                                <a onclick="getDataOrderBy('1','fdcreatetime');">
                                  <c:if test="${param.order=='fdcreatetime'}">
								    <button class="btn btn-large active" type="button">时间</button>
							      </c:if>
							      <c:if test="${param.order!='fdcreatetime'}">
								    <button class="btn btn-large" type="button">时间</button>
							      </c:if>
							     </a>
							     <a onclick="getDataOrderBy('1','fdname');">
							      <c:if test="${param.order=='fdname'}">
							        <button class="btn btn-large active" type="button">课程</button>
							      </c:if>
							      <c:if test="${param.order!='fdname'}">
							        <button class="btn btn-large" type="button">课程</button>
							      </c:if>
							     </a>
							     <a onclick="getDataOrderBy('1','user');">
							      <c:if test="${param.order=='user'}">
							        <button class="btn btn-large active" type="button">教师</button>
							      </c:if>
							      <c:if test="${param.order!='user'}">
							        <button class="btn btn-large" type="button">教师</button>
							      </c:if>
							     </a>
                                </div>
                                <label class="radio inline" for="selectCurrPage">
                                <input type="radio" id="selectCurrPage" checked name="selectCheckbox"/>选中本页</label>
                                <label class="radio inline" for="selectAll">
                                <input type="radio" id="selectAll"  name="selectCheckbox" />选中全部</label>
                                <div class="pages pull-right" id="pageheard">
                                    
                                </div>
                            </div>
                        </div>
                    </section>
                    <section class="listWrap">
                        <ul class="listTeacher media-list" id="listTeacher">

                        </ul>
                    </section>
                    <div class="pages" id="pageEnd">
                       
                    </div>
                </div>
			</div>
			<div class="pull-left w225">
                <%-- <!--用户页面 -->
			   <c:import url="/WEB-INF/views/studyTrack/divuserimg.jsp"></c:import> --%>
			<!-- 图片列表页面 -->
			<c:import url="/WEB-INF/views/studyTrack/divimglist.jsp">
				<c:param name="type" value="tutor"></c:param>
			</c:import>
              
	        </div>
        </div>
<input type="hidden" id="fdType" value="${param.fdType}"/>
<input type="hidden" id="fdOrder" value="${param.order}"/>
<!--底部 S-->
	
<!--底部 E-->
</section>
<!--主体 E-->
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
var unCheckedData = {},checkedData= {} ;

/*老师列表模板函数*/
var listTeacherFn = doT.template(document.getElementById("listTeacherTemplate").text);
//头部翻页
var pageheardFn=doT.template(document.getElementById("pageheardTemplate").text);
//底部翻页
var pageendFn= doT.template(document.getElementById("pageEndTemplate").text);

    $(function(){
       
       if($("#fdType").val()==''){
    		$("#fdType").val("unchecked");
    	}
        initlistTeacher($("#fdType").val());//初始化列表
        
        //loadList("unchecked");//默认加载未批改的

        $("#navTabs>li>a").on("click",function(e){
            e.preventDefault();
            $(this).parent().addClass("active").siblings().removeClass("active");
            var type = $(this).attr("href").slice(1);
            initlistTeacher(type);
            loadList(type);
            if(type=="checked"){
            	$("#nowSee").html("已批改的作业");
            }else{
            	$("#nowSee").html("未批改的作业");
            }
        });
        
        function initlistTeacher(fdType){
        	$.ajax({
          	  	type: "POST",
        		url: "${ctx}/ajax/adviser/findCheckTaskList",
        		async:false,
        		cache: false, 
        		data : {
                  	"fdType":fdType,
                },
        		dataType : 'json',
        		success: function(result){
        			//alert(JSON.stringify(result.checkedData));
        			if(fdType=="checked"){
        				checkedData.list = result.checkedData;
        			}else{
        				unCheckedData.list = result.checkedData;
        			}
        			$("#pageheard").html(pageheardFn(result.paging));
             		$("#pageEnd").html(pageendFn(result.paging));
        			loadList(fdType);
        			$("#fdType").val(fdType);
        		}
        	});
        }

});
//导出列表   
function exportData(){
	 var fdType=$("#fdType").val();
	if(document.getElementById("selectAll").checked){
		 var keyword=$("#search").val();
		 jalert("您确定要导出全部数据吗？",function(){
			  window.location.href="${ctx}/common/exp/getExportAdviserTask?fdType="+fdType+"&fdName="+keyword;
		 }); 
		 return;
	} else if(document.getElementById("selectCurrPage").checked){
		var chk_value = [];
		$("#listTeacher li").each(function() {
			chk_value.push($(this).attr("data-fdid"));
		});
		jalert("您确定导出本页数据吗？",function(){
			window.location.href="${ctx}/common/exp/getExportAdviserTask?modelIds="+chk_value+"&fdType="+fdType+"&isAll=noPage";
		});
		return;
	} else{
		 jalert("您好!您没有选择要导出的数据！");
		  return;
	}
}
//批量下载或者下载全部
function batchDownload(){
	if(document.getElementById("selectAll").checked){
		 var order=$("#fdOrder").val();
		 var keyword=$("#search").val();
		 var fdType=$("#fdType").val();
		 jalert("您确定下载全部数据吗？",function(){
			  window.location.href="${ctx}/common/file/allDownloadTaskZip/"+fdType+"/作业?keyword="+keyword+"&order="+order;
			  return;
		 }); 
	} else if(document.getElementById("selectCurrPage").checked){
		var chk_value = [];
		$("#listTeacher li").each(function() {
			chk_value.push($(this).attr("data-fdid"));
		});
		 $.ajax({
      		url : "${ctx}/ajax/adviser/findAttsBySoureceIds/"+chk_value,
      		async : true,
      		dataType : 'json',
      		success : function(result) {
      			if(result.attIds==null||result.attIds==''){
      				 jalert("您好!当前页没有数据可下载！");
      				   return;
      			}else{
      				jalert("您确定下载本页作业附件吗？",function(){
        				window.location.href="${ctx}/common/file/downloadZipsByArrayIds/"+result.attIds+"/作业";
        				return;
        			});
      			}
      		}
	     }); 
		
		/* jalert("您确定下载本页数据吗？",function(){
			window.open("${ctx}/common/file/batchDownloadTaskZip/"+chk_value+"/作业", _self);
			return;
		}); */
	} else{
		 jalert("您好!您没有选择要下载的数据！");
		  return;
	}
}
//下载
function downloadAtt(attIds,zipname){
	if(attIds==null||attIds==''){
		 jalert("您好!该作业包没有数据可下载！");
		   return;
	}
    window.location.href="${ctx}/common/file/downloadZipsByArrayIds/"+attIds+"/"+zipname;
}
//翻页
function pageNavClick(pageNo){
  var keyword=$("#search").val();
  var fdType=$("#fdType").val();
  var order=$("#fdOrder").val();
  if(order==''){
	  order='FDCREATETIME';
  }
  $.ajax({
        type: "post",
        url: "${ctx}/ajax/adviser/findCheckTaskList",
        data : {
          	"order":order,
          	"pageNo" : pageNo,
          	"keyword":keyword,
          	"fdType":fdType,
        },
        cache: false, 
        dataType: "json",
        success:function(result){
        	$("#goTop").trigger("click");
        	//alert(fdType);
        	if(fdType=="checked"){
				checkedData.list = result.checkedData;
			}else{
				unCheckedData.list = result.checkedData;
			}
			$("#pageheard").html(pageheardFn(result.paging));
     		$("#pageEnd").html(pageendFn(result.paging));
			loadList(fdType);
			$("#fdType").val(fdType);
        }
  }); 
}
function getDataOrderBy(pageNo,order){
	 $("#fdOrder").val(order);
	 pageNavClick(pageNo);
}
function clearserach(){//清理搜索栏并显示数据列表
	$("#search").val("");
	$("#markshow").html('<a id="containkey"href="#">全部条目</a>');
}

function showSearch(){
	var search = $("#search").val();
	$("#markshow").html('含“<a id="containkey"href="#"></a>”的用户');
	if(search==''){
		$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
	}else if(search.length>8){
		$("#containkey").html(search.substr(0,8)+"...");
	}else{
		$("#containkey").html(search);
	}
}

 /*加载列表*/
function loadList(fdType){
    switch (fdType){
        case "checked":
            checkedData.status = "checked";
            $("#listTeacher").html(listTeacherFn(checkedData));
            break;
        case "unchecked":
            unCheckedData.status = "unchecked";
            $("#listTeacher").html(listTeacherFn(unCheckedData));
        default :
            unCheckedData.status = "unchecked";
            $("#listTeacher").html(listTeacherFn(unCheckedData));
        }
        $("#listTeacher>.media").bind({
                        "mouseover": function(e){
                            $(this).addClass("active");
                        },
                        "mouseout": function(e){
                            $(this).removeClass("active");
                        },
                        "click": function(e){
                            var $target = $(e.target)
                            ,$parent = $target.parent();
                            if($target.add($parent).hasClass("send") || $target.add($parent).hasClass("downloadBox")){
                                e.stopPropagation();
                                if($target.add($parent).hasClass("msg")){
                                    e.preventDefault();
                                    //send msg
                                    var fdId = $target.attr("data-fdId");
                                    var flag = $target.attr("data-flag");
                                    if(flag=="true"){
                                    	jalert("您不能给自己发私信！");	
                                    }else{
                                    	window.open("${ctx}/letter/letterDetail?fdId=" + fdId,"_blank");
                                    } 
                                }
                            }  else{
                                window.open("${ctx}/adviser/checkTaskDetail?noteId=" + $(this).attr("data-fdid")+"&fdType="+fdType,"_self");//打开详情页面
                            }
                        }
                    });
        }
</script>
</body>
</html>
