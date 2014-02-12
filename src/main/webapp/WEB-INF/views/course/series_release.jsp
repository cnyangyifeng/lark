<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新东方在线教师备课平台</title>
<link href="${ctx}/resources/theme-series/default/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/theme-series/default/css/home_zht.min.css" rel="stylesheet" type="text/css">
<script src="${ctx}/resources/js/jquery.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
<!--阶段列表模板-->
<script id="phasesTemplate" type="text/x-dot-template">
			{{~it.phasesList :phases:index}}
    	    <div class="section section-phase {{?index>0}}mt20{{?}}">
          				<div class="hd">
							 <span class="sign"><i class="icon-phase"></i>模块 {{=phases.phasesNo}}</span>
                        	 <span>{{=phases.phasesName}}</span>
                    	</div>
                		<div class="bd">
                    		<dl>
							 {{~phases.courselist :course:index1}}
								<dt>
                                    <span class="icon-disc-lg-bg disc-bd">{{=course.courseNo}}</span>
                                    <b class="caret"></b>
                                	<div class="span5"><span class="tit">课程{{=course.courseNo}}&nbsp;&nbsp;{{=course.courseName}}</span></div>
                                	<div class="span2">
                                    <div class="rating-view">
                                            <span class="rating-all">
												{{for(var i=1;i<=5;i++){}}
													{{?course.average>=i}}
													<i class="icon-star active"/>
													{{??}}													
                                                	<i class="icon-star "/>
													{{?}}
												{{}}}
                                             </span>
                                        <b class="text-warning">{{?(course.average+"").length==1}} {{=course.average}}.0 {{??}} {{=course.average}} {{?}}</b>
                                    </div>
                                	</div>
                                	<div class="span2"><span class="text-warning">{{=course.countStudy}} </span>&nbsp;位老师在学习</div>
                                </dt>
								{{?phases.courselist.length==(index1+1)}}
 									<dd class="last">
								{{??}}
									<dd>
								{{?}}
                                    <div class="media">
                                        <div class="pull-left">
                                              <img class="media-object" src="{{?course.coverImg!=""}}
														${ctx}/common/file/image/{{=course.coverImg}}
														{{??}}
														${ctx}/resources/images/default-cover.png
														{{?}}"  alt=""/>
										</div>
                                        <div class="media-body">
                                            
                                                	{{=course.fdSummary||""}}
                                            
                                            <div class="media-foot">
												{{?course.isThrough}}
													<button class="btn btn-primary" data-fdid="{{=course.courseId}}">继续学习</button>
												{{??}}
                                                	<button class="btn btn-primary"  data-fdid="{{=course.courseId}}">开始学习</button>
												{{?}}
                                            </div>
                                        </div>
                                    </div>
                                </dd>
						     {{~}}
                          </dl>
                    </div>
                </div>
			{{~}}
		
</script>
<!-- 最新系列 -->
<script id="seriesTemplate" type="text/x-dot-template">
						{{~it.newestSeries:series:index}}
							<a href="javascript:void(0)" data-fdid="{{=series.seriesId}}">
                    			<img src="{{?series.seriesImg!=""}}${ctx}/common/file/image/{{=series.seriesImg}}{{??}}${ctx }/resources/images/default-cover.png{{?}}" alt="">
                    			<span class="mask"></span>
                    			<span class="caption">
                                	<h6 id="seriesName">{{=series.seriesName}}</h6>
                                    <span class="text-warning" id="author">{{=series.author}}</span>
                                </span>
                            </a>
						{{~}}
</script>
<script src="${ctx}/resources/js/doT.min.js"></script>
</head>
<body>
<!--主体 S-->
<section class="container">	
		<div class="section mt20">
        	<div class="media media-main">
                <!--
                根据类型选择性 为div.permission增加class
                默认： 公开
                授权： + authorize
                加密： + encrypt -->
                <div class="permission "></div>

                <a href="#" class="pull-left"><img  id="seriesImg" src="" alt="" class="media-object"></a>
                <div class="media-body">
                    <div class="media-heading">
                      <h2 id="seriesTitle"></h2>
                    </div>
                     <p id="seriesAuthor"></p>
                     <p id="authorDesc"></p>
                    <!--加密类型时 start
                    <form id="formPassword" action="#">
                        <input type="password" placeholder="输入授权密码" class="password" name="password" id="password" />
                        <button class="btn btn-link" type="submit">确定</button>
                    </form>
                    -->
                    <!--加密类型时 end
                    <div class="media-foot">
                        <a href="#" class="btn btn-warning"><i class="icon-eye-open"></i>了解详情</a>
                    </div>
                    -->
                </div>
            </div>
        </div>
        <div class="section mt20">
            <div class="hd">
                <h2>作者  <span id="seriesAuthor2"></span></h2>
                <div class="ab_r">
                    <span class="pub_time" ><i class="icon-time"></i><span id="createTime"></span></span>
                </div>
            </div>
            <div class="bd">
                <div class="box-txt">
                    <dl>
                        <dt>系列摘要</dt>
                        <dd id="seriesDesc" style="border-bottom-style: none;padding-bottom: 0px"></dd>
                    </dl>
                </div>
            </div>
        </div>
		<div class="clearfix mt20">
	        <div class="pull-left w760" id="phaseslistData">

			</div>
			<div class="pull-right w225">
                <div class="section profile">
                    <div class="hd">
                    </div>
                    <div class="bd">
                        <div class="faceWrap"><img id="authorImg" src="" class="face img-polaroid" alt=""/></div>
                        <p>作者</p>
                        <p class="muted" id="seriesAuthor3">
                        </p>
                    </div>
                      <div class="ft">
                    	<span id="authorDesc3"></span>
                    </div>
                </div>

                <div class="section newClass mt20">
                	<div class="hd">
                		<h5>最新系列课程</h5>
                        <a href="javascript:void(0)" class="ab_r" id="findAll">全部课程</a>
                	</div>
                    <div class="bd">
                    	<div class="list-class" id="serieslist">
                    	
                        	
                            
                        </div>
                    </div>
                </div>
	        </div>
        </div>
</section>
<!--主体 E-->
<script>
  //  $("a[data-toggle='tooltip']").tooltip();
    $(function(){
    	 var editMediaTitleFn = doT.template(document.getElementById("phasesTemplate").text);//阶段列表
    	 var serieslistFn=doT.template(document.getElementById("seriesTemplate").text);//最新课程列表
    	 var result;
    	 $.ajax({
         	type: "post",
         	url: "${ctx}/ajax/series/getSeriesHeardPage",
         	data : {
         		"seriesId" : "${param.seriesId}"
         	},
         	async:false,
         	cache: false, 
         	dataType: "json",
         	success:function(data){
         		$("#phaseslistData").html(editMediaTitleFn(data));
         		$("#serieslist").html(serieslistFn(data));
         		result=data;
         	}
         }); 	
         //绑定按钮事件
         $(".btn").bind("click",function(){
        	 window.location.href="${ctx}/passThrough/getCourseHome/"+$(this).attr("data-fdid");
         });
         //初始化系列头部信息
         //系列封面
         if(result.seriesImg!=""){
        	 $("#seriesImg").attr("src","${ctx}/common/file/image/"+result.seriesImg+"?n="+Math.random()*100);
         }else{
        	 $("#seriesImg").attr("src","${ctx }/resources/images/default-cover.png");
         }
         //系列标题
         if(result.seriesName!=""){
        	 $("#seriesTitle").html(result.seriesName);
         }else{
        	 $("#seriesTitle").html("未命名系列");
         }
         //系列作者
         if(result.author!=null){
        	 $("#seriesAuthor").html(result.author.authorName);
        	 $("#authorDesc").html(result.author.authorDesc);
         }
         //系列中部信息
         	//系列作者
         if(result.author!=null){
        	 $("#seriesAuthor2").html(result.author.authorName);
         }
         //系列创建时间
         if(result.createTime!=""){
        	 $("#createTime").html(result.createTime);
         }
         //系列描述
         if(result.seriesDesc!=""){
        	 $("#seriesDesc").html(result.seriesDesc);
         }
         //系列作者信息
         
         if(result.author!=null){
        	 $("#seriesAuthor3").html(result.author.authorName);
       			//头像信息
          	 if(result.author.imgUrl!=""){
             if(result.author.imgUrl.indexOf('http')>-1){
            	 $("#authorImg").attr("src",result.author.imgUrl); 
             }else{
             	 $("#authorImg").attr("src","${ctx }/resources/images/default-face-180.png");
             }
            }else{
           	 $("#authorImg").attr("src","${ctx }/resources/images/default-face-180.png");
            }
       		//作者简介
          	 $("#authorDesc3").html(result.author.authorDesc);
          	
          }
         //最新课程列表点击
         $("#serieslist>a").bind("click",function(){
        	 window.location.href="${ctx}/series/studayfoward?seriesId="+$(this).attr("data-fdid");
         });
         $("#findAll").bind("click",function(){
        	 window.location.href="${ctx}/course/courseIndexAll";
         });
    });
    
</script>

</body>
</html>
