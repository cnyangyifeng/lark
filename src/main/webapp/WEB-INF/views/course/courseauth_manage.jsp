<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<%
 String order = request.getParameter("order");
//String fdType = request.getParameter("fdType");

%>
 <html class=""> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>

</head>
<body>
 <!--老师列表模板-->
    <script id="listTeacherTemplate" type="x-dot-template">
        <ul class="listTeacher unstyled">
        {{~it :item:index}}
            <li>
                <div class="hd"><a class="remove" name="delecpa" href="javaScript:void(0)" data-fdid="{{=item.id}}">&times;</a></div>
                <div class="bd">
                    <label class="pull-left" ><input type="checkbox" name="teacherGroup" id="{{=item.id}}"/></label>
                    <div class="clearfix box">
                            <div class="span3">
                            <div class="type">
                            <span>教  师</span>
                    </div>
                    <div class="person">
                            <a href="${ctx}/course/courseIndex?userId={{=item.teacher.tid}}" class="pull-left"><img src="{{?item.teacher.imgUrl.indexOf('http')>-1}}{{=item.teacher.imgUrl}}{{??}}${ctx}/resources/images/default-face-70.png{{?}}" alt=""/></a>
                            <span title="{{=item.teacher.name}}（{{=item.teacher.mail}}）{{=item.teacher.department}}">{{=item.teacher.name}}（{{?item.teacher.mail.length>12}}{{=item.teacher.mail.substr(0,12)+"..."}}{{??}}{{=item.teacher.mail}}{{?}}）<br /> {{?item.teacher.department.length>12}}{{=item.teacher.department.substr(0,12)+"..."}}{{??}}{{=item.teacher.department}}{{?}}</span>
                    </div>
                    </div>
                    <div class="span2">
                        <div class="time">
                            <i class="icon-time"></i>
                            {{=item.time}}
                        </div>
                        <div class="divider">
                                <i class="icon-handshake"></i>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="type">
                            <span>导  师</span>
                        </div>
                        <div class="person">
                            {{?item.mentor}}
                                <a href="${ctx}/course/courseIndex?userId={{=item.mentor.mid}}" class="pull-left"><img src="{{?item.mentor.imgUrl.indexOf('http')>-1}}{{=item.mentor.imgUrl}}{{??}}${ctx}/resources/images/default-face-70.png{{?}}" alt=""/></a>
                                <span title="{{=item.mentor.name}}（{{=item.mentor.mail}}）{{=item.mentor.department}}">{{=item.mentor.name}}（{{?item.mentor.mail.length>12}}{{=item.mentor.mail.substr(0,12)+"..."}}{{??}}{{=item.mentor.mail}}{{?}}）<br /> {{?item.mentor.department.length>12}}{{=item.mentor.department.substr(0,12)+"..."}}{{??}}{{=item.mentor.department}}{{?}}</span>
                            {{??}}
                                <p align="center">本课程不需要导师参与</p>
                            {{?}}
                        </div>
                    </div>
                    </div>
                </div>
            </li>
        {{~}}
        </ul>
    </script>
<script id="listUserKinguserTemplate" type="text/x-dot-template">
    <tr data-fdid="{{=it.id}}">
        <td class="tdTit">
          <div class="pr">
            <div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
            <img src="{{=it.imgUrl || '${ctx}/resources/images/temp-face36.jpg'}}" alt="">{{=it.name}}（{{=it.mail}}），{{=it.org}} {{=it.department}}
          <div>
         </td>
        <td><input type="checkbox" {{?it.tissuePreparation!=false}}checked{{?}} class="tissuePreparation" /></td>
        <td><input type="checkbox" {{?it.editingCourse!=false}}checked{{?}} class="editingCourse" /></td>
        <td><a href="#" class="icon-remove-blue"></a></td>
    </tr>
</script>
<script id="pageheardTemplate" type="text/x-dot-template">
	<div class="pages pull-right">
	<div class="span2">
 	   第&nbsp;<span >{{=it.startNum}} - {{=it.endNum}} / </span><span >{{=it.totalCount}}</span> 条
	</div>
	<div class="btn-group">
    <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage <= 1}} disabled {{?}} onclick='pageNavClick({{=it.currentPage-1}})'><i class="icon-chevron-left icon-white"></i></button>
    <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage == it.totalPage}} disabled {{?}} onclick='pageNavClick({{=it.currentPage+1}})'><i class="icon-chevron-right icon-white"></i></button>
	/div>
</script>
<script id="pageEndTemplate" type="text/x-dot-template">
	<div class="btn-group dropup">
	 <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage<=1}}disabled{{?}}onclick='pageNavClick({{=it.currentPage-1}})'>
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
     <input id="currentPage" value="{{=it.currentPage}}" type="hidden">
	 <button class="btn btn-primary btn-ctrl" type="button" {{?it.currentPage == it.totalPage}} disabled {{?}} onclick='pageNavClick({{=it.currentPage+1}})'><i class="icon-chevron-right icon-white"></i></button>
</div>
</script>
     <script src="${ctx}/resources/js/doT.min.js" type="text/javascript"></script>
		<section class="container">
			<section class="clearfix mt20">
			  <section class="col-left pull-left">
		    	 <%@ include file="/WEB-INF/views/group/menu.jsp" %>
			  </section>
				<section class="w790 pull-right" id="rightCont">
			        <div class="page-header">
		               <span class="muted">我正在看：授权学习</span> 
		                <div class="backHome">
		                    <a href="${ctx}/studyTrack/getStudyTrackDirector"><span class="muted">返回</span>主管<span class="muted">首页</span> <i class="icon-home icon-white"></i> </a>
		                </div>
			        </div>
			        <div class="page-body authorizedBody">
			       <section class="section mt20">
                    <div class="media">
                        <div class="pull-left"><img class="media-object" id="coursepage" src="" alt=""/></div>
                        <div class="media-body">
                            <div class="media-heading">
                                <h2 id="courseName"></h2>
                            </div>
                            <p id="courseAuthor"></p>
                            <div class="rating-view">
                                <span class="rating-all" id="cavg">
                                    <i class="icon-star "></i><i class="icon-star "></i><i class="icon-star "></i><i class="icon-star "></i><i class="icon-star"></i>
                                 </span>
                                        <b class="text-warning" id="courseavg">0</b>
                            </div>
                            <div class="media-foot">
                                <a class="btn btn-primary btn-large" target="_blank" id="coursedetail" href="javascript:void(0);">查看课程详情</a>
                            </div>
                        </div>
                    </div>
                </section>
                <section class="section mt20">
                    <form action="#rightCont" id="formAddTeacher">
                        <input type="text" required name="inputTeacher" id="inputTeacher"  class="autoComplete input-block" placeholder="授权某位老师学习本课程">
                        <span id="showerror"></span>
                        <input type="hidden" id="teacher">
                        <div class="divider">
                            <i class="icon-handshake"></i>
                        </div>
                        <input type="text"  name="inputMentor" id="inputMentor" class="autoComplete input-block" placeholder="为该课程指定一位导师"/>
                         <span id="showerror2"></span>
                        <input type="hidden" id="mentor"/>
                        <div class="divider">
                            <button class="btn btn-primary btn-large" type="submit">添加</button>
                            </div>
                     </form> 
                </section>
			          <%-- <div class="page-body" id="page list">
			        	<%@ include file="/WEB-INF/views/course/divcourseauth.jsp" %>
			        </div> --%>
			      <section class="section box-control">
                    <div class="hd">
                        <div class="btn-toolbar">
                            <div class="btn-group">
                                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">操作 <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                     <li><a href="#rightCont" onclick="exportData();">导出列表</a></li>
                                   <!-- <li><a href="#rightCont">打包下载</a></li> -->
                                    <li><a href="#rightCont" onclick="confirmDel();">批量删除</a></li>
                                </ul>
                            </div>
                            <form class="toolbar-search" >
                                <input type="text" class="search" onkeydown="showSearch();" onkeyup="showSearch();" id="search">
                                <i class="icon-search" onclick="pageNavClick('1');" ></i>
                            </form>
                            <span class="showState">
                                <span class="muted">当前显示：</span>
                                <span id="markshow">
							 	<a id="containkey"href="#">全部条目</a>
							 </span>
                            </span>
                            <a class="btn btn-link" href="javaScript:void(0);" onclick="clearserach();">清空搜索结果</a>
                        </div>
                    </div>
                    <div class="bd">
                        <div class="btn-toolbar">
                            <label class="muted">排序</label>
                            <div class="btn-group btns-radio" data-toggle="buttons-radio">
							   <c:if test="${param.order=='createtime'}">
								<button class="btn btn-large active" type="button" onclick="getDataOrderBy('1','createtime')">时间</button>
							   </c:if>
							   <c:if test="${param.order!='createtime'}">
								<button class="btn btn-large" type="button" onclick="getDataOrderBy('1','createtime')">时间</button>
							   </c:if>
							   <c:if test="${param.order=='teacher'}">
								<button class="btn btn-large active" type="button" onclick="getDataOrderBy('1','teacher')">教师</button>
							   </c:if>
							   <c:if test="${param.order!='teacher'}">
								<button class="btn btn-large" type="button" onclick="getDataOrderBy('1','teacher')">教师</button>
							   </c:if>
						      <c:if test="${param.order=='mentor'}">
								<button class="btn btn-large active" type="button" onclick="getDataOrderBy('1','mentor')">导师</button>
							   </c:if>
							   <c:if test="${param.order!='mentor'}">
								<button class="btn btn-large" type="button" onclick="getDataOrderBy('1','mentor')">导师</button>
							   </c:if>
                            </div>
                            <label class="checkbox inline" for="selectCurrPage"><input type="checkbox" id="selectCurrPage" name="selectCheckbox" onclick="checkcurrpage()" value="0" />选中本页</label>
                            <label class="checkbox inline" for="selectAll"><input type="checkbox" id="selectAll" name="selectCheckbox" onclick="selectAll()" value="1"/>选中全部</label>
                            <div id="pageheard">
                            </div>
                        </div>
                    </div>
                </section>
                <section id="listTeacher">
                </section>
                <div class="pages" id="pageEnd">
        
                </div>
            </div>
	    </section>
	</section>
            <!-- 缓存插叙关键字 -->
			<input type="hidden" id="coursekey" name="coursekey">
			<input type="hidden" id="allFlag" >
			<input type="hidden"  id="cousetype" value="${param.fdType}">
            <input type="hidden" id="cachorder"/>
            <input type="hidden" id="currentpage">
            <input type="hidden" id="orderBy" value="${param.order}"/>
            <input type="hidden" id="isOftask"/>
</section>
<script type="text/javascript">
//导出列表   
function exportData(){
	var courseId="${param.courseId}";//课程id
	var order = $("#orderBy").val();
	if(document.getElementById("selectAll").checked){
		 jalert("您确定要导出全部数据吗？",function(){
			  window.location.href="${ctx}/common/exp/getExpCourseAuth?isAll=all&courseId="+courseId+"&order="+order;
		 }); 
		 return;
	} else if(document.getElementById("selectCurrPage").checked){
		var current = $("#currentPage").val();
		jalert("您确定导出本页数据吗？",function(){
			window.location.href="${ctx}/common/exp/getExpCourseAuth?currentPage="+current+"&courseId="+courseId+"&order="+order;
		});
		return;
	} else{
		 jalert("您好!您没有选择要导出的数据！");
		  return;
	}
}
</script>
<script type="text/javascript">	
/**********************initpage*********************************************/
    $.Placeholder.init();
    //头部翻页
    var pageheardFn=doT.template(document.getElementById("pageheardTemplate").text);
    //底部翻页
    var pageendFn= doT.template(document.getElementById("pageEndTemplate").text);
    /*老师列表模板函数*/
    var listTeacherFn = doT.template(document.getElementById("listTeacherTemplate").text);
   
    $(function(){
    	var courseId="${param.courseId}";//课程id
    	var order="${param.order}";//排序
    	var keyword="${param.keyword}";//搜索关键字
        var result;
        var pageInfo;
    	//添加新授权
        $("#formAddTeacher").validate({
            submitHandler: function(form){
            	if($("#teacher").val()==null||$("#teacher").val()==""){
            		$("#showerror").html("<lable class='error'>输入数据有误,请从下拉菜单中选择数据!</lable>");
            		return;
            	}
				if($("#isOftask").val()=='true'){//是否需要添加导师
	            	if($("#inputMentor").val()!=null&&$("#inputMentor").val()!=""){
	            		if($("#mentor").val()==null||$("#mentor").val()==""){
	            			$("#showerror2").html("<lable class='error'>输入数据有误,请从下拉菜单中选择数据!</lable>");
	            			return;
	            		}
	            	}
				}
            	
            	 $.post("${ctx}/ajax/course/saveCourseParticipateAuth",{
                 	'courseId':"${param.courseId}",//课程id
                 	'teacher':$("#teacher").val(),//教师id//导师id
                 	'mentor':$("#mentor").val()
               		  }).success(function(data){
               			if(data=='true'){
               				window.location.href="${ctx}/course/getSingleCourseAuthInfo?courseId=${param.courseId}&order=createtime&fdType=13";
               			}else{
               				$("#showerror").html("<lable class='error'>当前所选教师已被授权该课程!</lable>");
               				$("#teacher").val("");
               			}
                     });
            }
        });
        $.ajax({
        	type: "post",
        	url: "${ctx}/ajax/course/getSingleCourseAuths",
        	data : {
        		"courseId" : courseId,
        		"order":order,
        		"pageNo": 1
        	},
        	async:false,
        	cache: false, 
        	dataType: "json",
        	success:function(data){
        		//heard翻页:
        		/* $("#datacount").html(data.totalCount);//总数据
        		startnum=10*data.currentPage-9;
        		endnum=10*data.currentPage;
        		$("#datanum").html(''+startnum+'-'+endnum); */
        		 $("#pageheard").html(pageheardFn(data));
        		 $("#pageEnd").html(pageendFn(data));
       		    loadListTeacherMentor(data.list);
       		    result=data.course;
       		    pageInfo=data;
       		    $("#isOftask").val(data.isOftask);
        		 
        	}
        });
        //
        if($("#isOftask").val()=='true'){//必须添加导师
        	$("#inputMentor").attr("required","");
        }else{
        	$("#inputMentor").hide();
        }
        //课程封面
        if(result.coverUrl!=""){
        	$("#coursepage").attr('src','${ctx}/common/file/image/'+result.coverUrl+'?n='+Math.random()*100); 
        }else{
        	$("#coursepage").attr('src','${ctx}/resources/images/default-cover.png');
        }
        //课程评分
        for(var i=1;i<=result.courseScore;i++){
        	$("#cavg i :eq("+(i-1)+")").addClass("active");
        }
        //课程评分
        $("#courseavg").html(result.courseScore);
        //课程名称
        $("#courseName").html(result.courseName);
        //课程作者
        $("#courseAuthor").html(result.courseAuthor);
        //详细课程
        $("#coursedetail").attr('href',"${ctx}/course/previewCourse?courseId="+courseId);
        deleteByhref(pageInfo.currentPage);
        /*******************************选择教师 导师**************************************/
        var listUserKinguserFn = doT.template(document.getElementById("listUserKinguserTemplate").text);
        $("#inputTeacher").autocomplete("${ctx}/ajax/user/findByName",{
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
    		width:750
        }).result(function(e,item){
        	$("#inputTeacher").val(item.name + '（' + item.mail + '），' + item.org + item.department);
        	$("#teacher").val(item.id);
    	});
        /****************************************导师信息***************************/
        $("#inputMentor").autocomplete("${ctx}/ajax/user/findByName",{
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
    		width:750
        }).result(function(e,item){
        	$("#inputMentor").val(item.name + '（' + item.mail + '），' + item.org  + item.department);
        	$("#mentor").val(item.id);
    	});
        /* $(this).keypress( function(e) {  //屏蔽回车事件 由于目前回车会提交两次表单原因找不到 暂时如此处理
               var key = window.event ? e.keyCode : e.which;  
               if(key.toString() == "13"){  
                          //  return false;  
               }  
        });  */
        $("#search").keypress( function(e) {  //回车事件 
            var key = window.event ? e.keyCode : e.which;  
            if(key.toString() == "13"){  
          	  pageNavClick('1');
        	  return false;//此处代码作用防止回车提交两次表单  原因可能为页面存在两个form所致
            }  
     }); 
        $("#inputTeacher").bind("focus",function(){
        	if($("#showerror").html()!=null&&$("#showerror").html()!=""){
        		$("#showerror").html("");
        		$(this).val("");
        	}
        });
        $("#inputMentor").bind("focus",function(){
        	if($("#showerror2").html()!=null&&$("#showerror2").html()!=""){
        		$("#showerror2").html("");
        		$(this).val("");
        	}
        });
    });
/**********************methods***************************************************/




 
function clearserach(){//清理搜索栏并显示数据列表
	$("#search").val("");
	$("#markshow").html('<a id="containkey"href="#">全部条目</a>');
	pageNavClick('1');
}

function showSearch(){
	var search = $("#search").val();
	$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
	if(search==''){
		$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
	}else if(search.length>10){
		$("#containkey").html(search.substr(0,10)+"...");
		}else{
			$("#containkey").html(search);
		}
}
//选中当前页
function checkcurrpage(){
	if(document.getElementById("selectCurrPage").checked){
		document.getElementById("selectAll").checked=false;
		$('input[name="teacherGroup"]').each(function(){
			$(this).attr("checked",true);// 
			$(this).attr("disabled",false);
		});
		$("#allFlag").attr("value",false);
	} else {
		$('input[name="teacherGroup"]').each(function(){
			$(this).attr("checked",false);// 
		});
		$("#allFlag").attr("value",false);
	}
}
//全部选中
function selectAll(){
	if(document.getElementById("selectAll").checked){
		document.getElementById("selectCurrPage").checked=false;
		$('input[name="teacherGroup"]').each(function(){
			$(this).attr("checked",true);// disabled="disabled"
			$(this).attr("disabled",false);
		});
		$("#allFlag").attr("value",true);
	} else {
		$('input[name="teacherGroup"]').each(function(){
			$(this).attr("checked",false);// 
		});
		$("#allFlag").attr("value",false);
	}
}

 /*加载老师列表函数*/
 function loadListTeacherMentor(arrObj){
     $("#listTeacher").html(listTeacherFn(arrObj));
 }
 function getDataOrderBy(pageNo,order){
	 $("#orderBy").val(order);
	 pageNavClick(pageNo);
 }
 //翻页
 function pageNavClick(pageNo){
	 
 	 var keyword=$("#search").val();
 	 if($('input[name="selectCheckbox"]:checked').val()==1){
		$("#allkey").attr("value",1);
	 }
 	 var order="${param.order}";
 	 if($("#orderBy").val()!=""){
 		order=$("#orderBy").val();
 	 }
 	 $.ajax({
      	type: "post",
      	url: "${ctx}/ajax/course/getSingleCourseAuths",
      	data : {
      		"courseId":"${param.courseId}",
       		"order":order,
       		"pageNo" : pageNo,
       		"keyword":keyword
      	},
      	cache: false, 
      	dataType: "json",
      	success:function(data){	
      		
      		 $("#pageheard").html(pageheardFn(data));
      		 $("#pageEnd").html(pageendFn(data));
      		 loadListTeacherMentor(data.list);
      		 if($("#allFlag").val()=='true'){
				document.getElementById("selectAll").checked=true;
				selectAll();
			 }
      		deleteByhref(data.currentPage);
      		 
      	}
      }); 
 }
 //删除 删除某条数据  删除某页数据  删除所有数据
function confirmDel(){
	var delekey="";
	$('input[name="teacherGroup"]:checked').each(function() {
		delekey+=$(this).attr("id")+",";
	}); 	
	if(delekey==""){
		jalert("当前没有选择要删除的数据!");
		return;
	}
	if($('input[name="selectCheckbox"]:checked').val()==1){//删除所有
		jalert("您确认要删除所有数据？",deleteAllCourseParticAuth);
	}else if($('input[name="selectCheckbox"]:checked').val()==0){
		jalert("您确认要删除本页数据？",deleteCourseParticAuth);
	}else{
		jalert("您确认要删除所选数据？",deleteCourseParticAuth);
		
	}
}
 //删除选中项 或者当前页删除
 function deleteCourseParticAuth(){
	 var delekey="";
		$('input[name="teacherGroup"]:checked').each(function() {
			delekey+=$(this).attr("id")+",";
		}); 	
	 $.ajax({
      	type: "post",
      	url: "${ctx}/ajax/course/deleteCouseParticAuthById",
      	data : {
      		"cpaId" : delekey
      	},
      	cache: false, 
      	dataType: "json",
      	success:function(data){
      		pageNavClick($("#currentpage").val());
      	}
      }); 
}
 //删除所有授权信息
  function deleteAllCourseParticAuth(){
	  var keyword=$("#search").val();//搜索关键字 	
	 $.ajax({
      	type: "post",
      	url: "${ctx}/ajax/course/deleteAllCourseParticAuth",
      	data : {
      		courseId:"${param.courseId}",
      		keyword:keyword
      	},
      	cache: false, 
      	dataType: "json",
      	success:function(data){
      		pageNavClick($("#currentpage").val());
      	}
      }); 
}
 //绑定删除事件;
 function deleteByhref(nowpage){
	    $("#currentpage").val(nowpage);
        $("a[name='delecpa']").bind("click",function(e){
        	var checkCpaId=$(this).attr("data-fdid");
        	jalert("确认移除授权（不删除学习记录）？",function(){
        		 $.ajax({
        		      	type: "post",
        		      	url: "${ctx}/ajax/course/deleteCouseParticAuthById",
        		      	data : {
        		      		"cpaId" : checkCpaId
        		      	},
        		      	cache: false, 
        		      	dataType: "json",
        		      	success:function(data){
        		      		pageNavClick(nowpage);
        		      	}
        		      }); 
       		 });
        });
 }
</script>
</body>
</html>
