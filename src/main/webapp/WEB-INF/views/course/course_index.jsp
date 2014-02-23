<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/theme-profile/default/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/theme-profile/default/css/home-course.min.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
    <!--课程列表模板-->
    <script id="thumbnailsTemplate" type="text/x-dot-template">
        <ul class="thumbnails">
        {{~it.list :item:index}}
            <li>
                <div class="thumbnail" data-fdid="{{=item.id}}">
                    <img src="{{?item.imgUrl!=""}}${ctx}/common/file/image/{{=item.imgUrl}}{{??}}${ctx }/resources/images/default-cover.png{{?}}" alt="">
                    {{?it.type == "single"}}
                    <i class="icon-permission-{{?item.permission == 'authorize'}}key{{??item.permission == 'open'}}box{{??item.permission == 'encrypt'}}lock{{?}}"></i>
                    {{?}}
                    {{?it.type == "series"}}
                        <div class="hd2">
                                    <span>
                                        <strong>{{=item.docNum}}&nbsp;门</strong>课程
                                    </span>
                                     <span>
                                        <strong>{{=item.learnerNum}}&nbsp;位</strong>老师在学习
                                    </span>
                        </div>
                    {{??}}
                        <div class="progress">
                            <div class="bar" style="width: {{=item.progress}}"></div>
                        </div>
                    {{?}}
                    <div class="bd2" title="{{=item.name}}">
                        <h3>
						{{=cut_str(item.name, 14)}}
						</h3>
                        <p>
						{{=cut_str(item.issuer, 14)}}
						</p>
                    </div>
                    <div class="ft2">
                        <span class="text-info" >{{?item.statusLearning == "doing"}}继续学习{{??item.statusLearning == "not"}}开始学习{{??item.statusLearning == "pass"}}再次学习{{?}}</span>
                    </div>
                </div>
            </li>
        {{~}}
        </ul>
    </script>

    <!--右侧内容区模板-->
    <script id="rightContentTemplate" type="text/x-dot-template">
        <section class="md" id="rightDiv">
                <div class="hd navbar" id="topSelect">
                <div class="navbar-inner">
                <ul class="nav" id="navCourse">
                <li class="active" name="doing"><a href="#doing">正在学习（<span id="ingCourseSum"></span>）</a></li>
                <li name="finish"><a href="#finish">完成学习（<span id="edCourseSum"></span>）</a></li>
        </ul>
        </div>
        </div>
        <div class="bd" id="listCourse">
        </div>
        <div class="ft" id="pageDiv">
        </div>
        </section>
    </script>
<script id="userTemplate" type="text/x-dot-template"> 
 <div class="thumbnail profile">
       <a {{?it.isme}}href="${ctx}/register/updateIco{{?}}" target="_blank">
         <img src="{{?it.img.indexOf('http')>-1}}{{=it.img}}{{??}}${ctx}/{{=it.img}}{{?}}" class="img-polaroid" alt="头像"/>
       </a>          
                <h3>{{=it.name}}
						{{?it.sex=='M'}}
							<i class="icon-male"></i>
						{{??}}
							<i class="icon-female"></i>
						{{?}}
				</h3>
                <p>完成 <strong>{{=it.finishSum}}</strong> 门 <em>｜</em> 进行 <strong>{{=it.unfinishSum}}</strong> 门</p>
  </div>
</script>
<script id="listTemplate" type="text/x-dot-template"> 

                <div class="pages">
                <div class="btn-group dropup">

		{{?it.firstPage==true}}
			<button class="btn btn-primary btn-ctrl" type="button" disabled>
				<i class="icon-chevron-left icon-white"></i>
			</button>
		{{?}}
		{{?it.firstPage==false}}
			<a onclick="initDivS({{=it.prePage}} ,30)">
				<button class="btn btn-primary btn-ctrl" type="button" >
					<i class="icon-chevron-left icon-white"></i>
				</button>
			</a>
		{{?}}
{{for(var i=it.startPage;i<=it.endPage;i++){}}
{{?it.pageNo == i}}
	<button class="btn btn-primary btn-num active" type="button" onclick="initDivS({{=i}} ,30)" >{{=i}}</button>
{{??}}
 	<a onclick="initDivS({{=i}} ,30)">
       <button class="btn btn-primary btn-num" type="button">{{=i}}</button>
    </a>
{{?}}
{{}}}
        <button class="btn btn-primary btn-num  dropdown-toggle" data-toggle="dropdown" type="button">
                <span class="caret"></span></button>
        <ul class="dropdown-menu pull-right">
			{{for(var i=it.startOperate;i<=it.endOperate;i++){}}
                  <li><a onclick="initDivS({{=i}} ,30)">
                     {{=(i*10-10+1)}} - {{=(i*10)}} 
				  </a></li>
			{{}}}
        </ul>
		{{?it.lastPage==true}}
			<button class="btn btn-primary btn-ctrl" type="button" disabled>
				<i class="icon-chevron-right icon-white"></i>
			</button>
		{{?}}
		{{?it.lastPage!=true}}
			<a onclick="initDivS({{=it.nextPage}} ,30)">
				<button class="btn btn-primary btn-ctrl" type="button">
					<i class="icon-chevron-right icon-white"></i>
				</button>
			</a>
		{{?}}
        </div>
        </div>

</script>
    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>

<section class="container">
    <section class="clearfix mt20">
        <section class="pull-left w187" id="leftDiv">
           
            <ul class="nav nav-list sidenav mt20" id="sideNav">
                <li class="active"><a href="#required"><i class="icon-book-open"></i>
             <j:ifelse test="${isMe}">
				<j:then>我的必修课</j:then>
				<j:else>TA的必修课</j:else>
			 </j:ifelse>
                 </a></li>
                <li><a href="#optional"><i class="icon-book-open-checkbox"></i>
             <j:ifelse test="${isMe}">
				<j:then>我的选修课</j:then>
				<j:else>TA的选修课</j:else>
			 </j:ifelse>
                	</a></li>
                <li><a href="#series"><i class="icon-book-two"></i> 系列课程</a></li>
            </ul>
            <c:if test="${isMe}">
            <ul class="nav nav-list sidenav mt20">
                <li><a href="${ctx}/letter/findLetterList"><i class="icon-envelope"></i> 我的私信</a></li>
                <li><a href="${ctx}/register/updateTeacher"><i class="icon-user"></i> 账号设置</a></li>
            </ul>
            </c:if>
        </section>
        <section class="pull-right w790" id="rightCont">

        </section>
    </section>
</section>
<script type="text/javascript">
 $(function(){
	 initUser();
    //右侧内容区模板
    var rightContentFn = doT.template(document.getElementById("rightContentTemplate").text);

    $("#sideNav>li>a").click(function(e){
        e.preventDefault();
        $(this).parent().addClass("active").siblings().removeClass("active");
        $("#rightCont").html(rightContentFn());
        
        $("#navCourse>li>a").click(function(e){
            e.preventDefault();
            $(this).parent().addClass("active").siblings().removeClass("active");
            if($(this).attr("href").substring(1) == "doing"){//进行中的
            	initCourse(1, 30, $("#sideNav .active a").attr("href").substring(1));
            } else if($(this).attr("href").substring(1) == "finish"){//已完成的
            	initCourse(1, 30, $("#sideNav .active a").attr("href").substring(1));
            }
        });
        if($(this).attr("href").substring(1) == "series"){//系列课程
        	$("#topSelect").addClass("hide");
        	initSeries(1, 30);
        } else if($(this).attr("href").substring(1) == "optional"){//选修课程
        	$("#topSelect").removeClass("hide");
        	initCourse(1, 30, "optional");
        } else if($(this).attr("href").substring(1) == "required"){//必修课程
        	$("#topSelect").removeClass("hide");
        	initCourse(1, 30, "required");
        }
    });
    $("#sideNav>li:first>a").trigger("click");
 });
 
 function initCourse(pageNo, pageSize, type){
	 var courseData = {};
	 var studyType = $("#navCourse .active a").attr("href").substring(1);
	 //alert(studyType);
	 var isCompulsoryCourse = type;
	 $.ajax({
 		url : "${ctx}/ajax/course/getMyCoursesIndexInfo",
 		async : false,
 		dataType : 'json',
 		data:{
			userId:"${userId}",
			pageNo:pageNo,
			pageSize:pageSize,
			studyType:studyType,
			isCompulsoryCourse:isCompulsoryCourse
		},
 		success : function(result) {
 			courseData=result;
 		}
 	});
	 var thumbnailsFn = doT.template(document.getElementById("thumbnailsTemplate").text);
	 var listFn = doT.template(document.getElementById("listTemplate").text);
	 $("#listCourse").html(thumbnailsFn(courseData)).find(".thumbnail").click(function(){
		 window.location.href="${ctx}/passThrough/getCourseHome/"+$(this).attr("data-fdid");
	 });
	 if(courseData.list==null||courseData.list.length==0){
		 $("#listCourse").html("<div class='moreCourse'></div>");
	 }
	 $("#ingCourseSum").html(courseData.ingCSum);
	 $("#edCourseSum").html(courseData.edCSum);
	 //alert(courseData.page.totalPage);
	 if(courseData.page.totalPage>1){
		 $("#pageDiv").html(listFn(courseData.page));
	 }
 }
 
 function initDivS(pageNo ,pageSize){
	 var type = $("#sideNav .active a").attr("href").substring(1);
	 if(type=="series"){
		 initSeries(pageNo, pageSize);
	 }else
	 if(type=="optional"){
		 initCourse(pageNo, pageSize, "optional");
	 }else
	 if(type=="required"){
		 initCourse(pageNo, pageSize, "required");
	 }
 }
 
 function initSeries(pageNo, pageSize){
	 var seriesData = {};
	 var thumbnailsFn = doT.template(document.getElementById("thumbnailsTemplate").text);
	 var listFn = doT.template(document.getElementById("listTemplate").text);
	 $.ajax({
	 		url : "${ctx}/ajax/series/getSeries",
	 		async : false,
	 		dataType : 'json',
	 		data:{
				pageNo:pageNo,
				pageSize:pageSize,
			},
	 		success : function(result) {
	 			seriesData=result;
	 		}
	 	});
	 $("#listCourse").html(thumbnailsFn(seriesData))
	 .find(".thumbnail").click(function(){
		 window.location.href="${ctx}/series/studayfoward?seriesId="+$(this).attr("data-fdid");
	 });
	 if(seriesData.list.length==0){
		 $("#listCourse").html("<div class='moreCourse'></div>") 
	 }
	 if(seriesData.page.totalPage>1){
		 $("#pageDiv").html(listFn(seriesData.page));
	 }
	 
 }
 
 function initUser(){
	 var userFn = doT.template(document.getElementById("userTemplate").text);
	 $.ajax({
 		url : "${ctx}/ajax/course/getUserCourseInfo",
 		async : false,
 		dataType : 'json',
 		data:{
 			userId:"${userId}"
 		},
 		success : function(result) {
 			$("#leftDiv").prepend(userFn(result));
 		}
 	});
 }
 
</script>
</body>
</html>
