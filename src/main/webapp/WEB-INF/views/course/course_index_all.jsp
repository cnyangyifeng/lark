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
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/home-course.min.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
    <script id="thumbnailsTemplate" type="text/x-dot-template">
        {{~it.list :item:index}}
            <li>
                <div class="thumbnail">
					<img src="{{?item.imgUrl!=""}}${ctx}/common/file/image/{{=item.imgUrl}}{{??}}${ctx }/resources/images/default-cover.png{{?}}" alt="">
                    {{?it.type == "series"}}
                        <div class="hd2">
                                    <span>
                                        <strong>{{=item.docNum}}&nbsp;门</strong>课程
                                    </span>
                                     <span>
                                        <strong>{{=item.learnerNum}}&nbsp;位</strong>老师在学习
                                    </span>
                        </div>
                    {{?}}
                    <div class="bd2" title="{{=item.name}}">
                        <h3>
						{{?item.name==null}}
							不详
						{{??}}
							{{=cut_str(item.name, 17)}}
						{{?}}
						</h3>
                        <p>{{?item.issuer==null}}
							不详
						{{??}}
							{{=cut_str(item.issuer, 17)}}
						{{?}}</p>
						{{?it.type == "single"}}
                        <p class="rating">
                                    <span class="rating-view">
                                        <span class="rating-all">
                                            {{ for(var i=1; i<= 5; i++){ }}
                                                <i class="icon-star{{?i<=item.score}} active{{?}}"></i>
                                            {{ } }}
                                         </span>
                                        <b class="text-warning">
										{{?(item.score+"").length==1}}
											{{=item.score}}.0
										{{??}}
											{{=item.score}}
										{{?}}	</b>
                                    </span>
                            ({{=item.raterNum}} 个评分)
                        </p>
						{{?}}
                        {{?it.type == "series"}}
                            <dl>
                                <dt>简介：</dt>
                                <dd>{{=item.intro}}</dd>
                            </dl>
                        {{?}}
                    </div> 
                    <div class="ft2 clearfix{{?it.type == 'single'}} bdt1{{?}}">
						{{?it.type == "series"}}
                        	<a class="btn btn-warning pull-right" href="${ctx}/series/studayfoward?seriesId={{=item.dataId}}">
 						{{?}}
						{{?it.type == "single"}}
                        	<a class="btn btn-warning pull-right" href="${ctx}/passThrough/getCourseHome/{{=item.dataId}}">
 						{{?}}
						<i class="{{?item.isLearning}}icon-progress-shadow"></i>{{?item.isThrough}}再次学习{{??}}继续学习{{?}}{{??}}icon-book-shadow"></i>开始学习{{?}}</a>
                        {{?it.type == 'single'}}
                            <div class="pull-right"><strong>{{=item.learnerNum}}</strong>&nbsp;位老师在学习</div>
                        {{?}}
                    </div>
                </div>
            </li>
        {{~}}
{{?it.list.length<30}}
 			{{?it.type == 'single'}}
			<li>
            	<div class="moreCourse">更多课程，敬请期待</div>
        	</li>
 			{{?}}
{{?}}
{{?it.list.length<3}}
			<li>
			{{?it.type == 'series'}}
            	<div class="moreCourse">更多系列，敬请期待</div>
        	</li>
 			{{?}}
{{?}}

    </script>
<script id="courseCategoryTemplate" type="text/x-dot-template">
    <li class="active"><a href="javascript:void(0)" data-id="all">全部课程</a></li>
	{{~it.list :item:index}}
	<li ><a href="javascript:void(0)" data-id="{{=item.courseCategoryId}}">{{=item.courseCategoryName}}</a></li>
	{{~}}
<li ><a href="javascript:void(0)" data-id="">其他分类</a></li>
</script>
<script id="courseTopTemplate" type="text/x-dot-template">
{{?it.list.length>=3}}
 <div id="myCarousel" class="carousel slide" data-interval="4000">
			<div class="carousel-l">
			<img src="{{?it.list[it.list.length-1].attId!=""}}${ctx}/common/file/image/{{=it.list[it.list.length-1].attId}}{{??}}${ctx}/resources/images/default-cover.png{{?}}" alt="">
			</div>
            <div class="carousel-r">
			<img src="{{?it.list[1].attId!=""}}${ctx}/common/file/image/{{=it.list[1].attId}}{{??}}${ctx}/resources/images/default-cover.png{{?}}" alt="">
			</div>
            <div class="carousel-mask"></div>
            <div class="carousel-inner">
  			{{for(var index=0;index<it.list.length;index++){}}
				{{?index==0}}
				 <div class="item active">
					<img src="{{?it.list[index].attId!=""}}${ctx}/common/file/image/{{=it.list[index].attId}}{{??}}${ctx}/resources/images/default-cover.png{{?}}" alt="">
                </div>
				{{??}}
 				<div class="item">
					<img src="{{?it.list[index].attId!=""}}${ctx}/common/file/image/{{=it.list[index].attId}}{{??}}${ctx}/resources/images/default-cover.png{{?}}" alt="">
                </div>
				{{?}}
               
			{{}}}
            </div>
            <div class="left carousel-btn" data-target="#myCarousel" data-slide="prev"></div>
            <div class="right carousel-btn" data-target="#myCarousel" data-slide="next"></div>
            <ol class="carousel-indicators">
				{{for(var index=0;index<it.list.length;index++){}}
					{{?index==0}}
                	<li data-target="#myCarousel" data-slide-to="{{=index}}" class="active"></li>
					{{??}}
					<li data-target="#myCarousel" data-slide-to="{{=index}}" class=""></li>
					{{?}}
				{{}}}
            </ol>
            
</div>
{{?}}
</script>
    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>

<section class="container">
	<section class="mt20" id="topDiv">
       
	</section>

    <section class="md">
        <div class="hd"><h2>系列课程</h2></div>
        <div class="bd">
            <ul class="thumbnails" id="list-series">
            </ul>
        </div>
        <div class="ft">
            <a href="#"id="loadMoreSeries">点击加载更多 <i class="icon-circle"></i><i class="icon-circle"></i><i class="icon-circle"></i></a>
        </div>
    </section>

    <section class="md">
        <div class="hd navbar">
            <div class="navbar-inner">
                <ul class="nav" id="navCourse">
                </ul>
            </div>
        </div>
        <div class="bd">
            <ul class="thumbnails"  id="list-course">
            </ul>
        </div>
        <div class="ft">
            <a href="#" id="loadMoreCourse">点击加载更多 <i class="icon-circle"></i><i class="icon-circle"></i><i class="icon-circle"></i></a>
        </div>
    </section>

<!--底部 E-->
</section>

<script type="text/javascript">	
$(function(){
	//初始化top
	initCourseTop();
	//初始化课程分类
	initCourseCategory();
	//初始化系列
	initSeries(1);
	//初始化课程
	initCouese("all",1);

	var thumbnailsFn = doT.template(document.getElementById("thumbnailsTemplate").text);
	var courseCategoryFn = doT.template(document.getElementById("courseCategoryTemplate").text);
	var courseTopFn = doT.template(document.getElementById("courseTopTemplate").text);

    $("#loadMoreSeries").click(function(e){//点击加载更多
    	 e.preventDefault();
         var pageNo = $("#list-series li").length/3+1;
         $.ajax({
        	 url : "${ctx}/ajax/series/getSeries",
     		async : false,
     		dataType : 'json',
     		data:{
     			pageNo:pageNo
     		},
     		success : function(result) {
     			$("#list-series").append(thumbnailsFn(result));
     			if(result.hasMore){
   				 	$("#loadMoreSeries").removeClass("hide");
   				}else{
   				 	$("#loadMoreSeries").addClass("hide");
   				}
     		}
     	});
    });
    $("#loadMoreCourse").click(function(e){//点击加载更多
    	e.preventDefault();
        var pageNo = $("#list-course li").length/30+1;
        var type = $("#navCourse .active a").attr("data-id");
        $.ajax({
    		url : "${ctx}/ajax/course/getAllCoursesIndexInfo",
    		async : false,
    		dataType : 'json',
    		data:{
    			userId:"${userId}",
    			type:type,
    			pageNo:pageNo,
    		},
    		success : function(result) {
    			$("#list-course").append(thumbnailsFn(result));
    			if(result.hasMore){
    				 $("#loadMoreCourse").removeClass("hide");
    			}else{
    				 $("#loadMoreCourse").addClass("hide");
    			}
    		}
    	});
    });

    $("#navCourse>li>a").click(function(e){
    	 e.preventDefault();
         var type = $(this).attr("data-id");
         initCouese(type,1);
         $(this).parent().addClass("active").siblings().removeClass("active");
    });
    $("#myCarousel").on("slid",function(e){
        var $this = $(this);
        $this.find(".carousel-inner>.item").each(function(i){
            if($(this).hasClass("active")){
                $this.find(".carousel-l>img").attr("src",$(this).prev().length ? $(this).prev().children("img").attr("src") : $this.find(".carousel-inner>.item:last>img").attr("src"));
                $this.find(".carousel-r>img").attr("src",$(this).next().length ? $(this).next().children("img").attr("src") : $this.find(".carousel-inner>.item:first>img").attr("src"));
            }
        })
    });
});  
var thumbnailsFn = doT.template(document.getElementById("thumbnailsTemplate").text);
var courseCategoryFn = doT.template(document.getElementById("courseCategoryTemplate").text);
var courseTopFn = doT.template(document.getElementById("courseTopTemplate").text);
    function initCourseCategory(){
    	$.ajax({
    		url : "${ctx}/ajax/category/getCourseCategory",
    		async : false,
    		dataType : 'json',
    		success : function(result) {
    			$("#navCourse").html(courseCategoryFn(result));
    		}
    	});
    }
    function initSeries(pageNo){
    	$.ajax({
    		url : "${ctx}/ajax/series/getSeries",
    		async : false,
    		dataType : 'json',
    		data:{
    			pageNo:pageNo
    		},
    		success : function(result) {
    			$("#list-series").html(thumbnailsFn(result));
    			if(result.hasMore){
   				 	$("#loadMoreSeries").removeClass("hide");
   				}else{
   				 	$("#loadMoreSeries").addClass("hide");
   				}
    		}
    	});
    }
    function initCouese(type,pageNo){
    	$.ajax({
    		url : "${ctx}/ajax/course/getAllCoursesIndexInfo",
    		async : false,
    		dataType : 'json',
    		data:{
    			userId:"${userId}",
    			type:type,
    			pageNo:pageNo,
    		},
    		success : function(result) {
    			$("#list-course").html(thumbnailsFn(result));
    			if(result.hasMore){
    				 $("#loadMoreCourse").removeClass("hide");
    			}else{
    				 $("#loadMoreCourse").addClass("hide");
    			}
    		}
    	});
    }
    
    function initCourseTop(){
    	var data={};
    	$.ajax({
    		url : "${ctx}/ajax/course/getCoursesTop5ByScore",
    		async : false,
    		dataType : 'json',
    		success : function(result) {
    			data=result;
    		}
    	});
    	$("#topDiv").html(courseTopFn(data));
    	$("#myCarousel").carousel();
    	$("#myCarousel>.carousel-inner>.item").click(function(e){
            switch ($(this).index()){
                case 0:
                    window.location.href="${ctx}/passThrough/getCourseHome/"+data.list[0].courseId;
                    break;
                case 1:
                	window.location.href="${ctx}/passThrough/getCourseHome/"+data.list[1].courseId;
                    break;
                case 2:
                	window.location.href="${ctx}/passThrough/getCourseHome/"+data.list[2].courseId;
                    break;
                case 3:
                	window.location.href="${ctx}/passThrough/getCourseHome/"+data.list[3].courseId;
                    break;
                case 4:
                	window.location.href="${ctx}/passThrough/getCourseHome/"+data.list[4].courseId;
                    break;
            }
        });
    }
</script>
</body>
</html>
