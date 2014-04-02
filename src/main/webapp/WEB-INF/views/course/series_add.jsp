<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/template_detail.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<style type="text/css"><!--此处样式解决阶段为白色的问题-->
.chapter{margin-left:25px;margin-right:0px;}
.chapter .sortable-bar{background-color:#FFF;color:#000;border-bottom:solid #e5e5e5 1px;}
</style>
<!-- 模板详情_右侧内容区标题 模板 -->
<script id="contHeaderTemplate" type="text/x-dot-template">
 	<div class="page-header">        	
		<h4>{{=it.pageTitle || ''}}</h4>   
	</div>
</script>
<!-- 模板详情_系列目录 模板 -->
<script id="sectionDirectoryTemplate" type="text/x-dot-template">
	{{#def.pageHeader}}
    	<div class="page-body">
	    	<div class="section" id="progress_courses">    
	            	<div class="progress progress-course">
	            		<div class="bar" style="width:0%;"></div>
	            	</div>
	                您设置本系列的 <span class="num_comp">{{=it.courseCount}}</span> 个模块内容，共计 <span class="num_all">{{?it.chapter}}{{=it.chapter.length}}{{??}}0{{?}}</span> 模块。
	        </div>
	      <div class="sortableWrap">
	      	<ul class="sortable" id="sortable">  
				{{?it.chapter}}
			    	{{  for(var i=1; i<(it.chapter.length)+1; i++){  }}
						{{~it.chapter :chp:index}}
							{{?chp.index == i}}	
								<li class="chapter" data-fdid="{{=chp.id}}">		
									{{#def.sectionbar:chp}}
								</li>
							{{?}}
						{{~}}
					{{ } }}
				{{?}}
	      	</ul>
	        <div class="section-add">
			    <button id="addSeries" class="btn btn-block" type="button">添加模块</button>
	        </div>
	      </div>
      </div> 
</script>
<!--添加系列表单模板-->
<script id="addSectionsTemplate" type="text/x-dot-template">
	<li class="chapter" data-fdid="">
		{{#def.edittitle || ''}}		
	</li>
</script>
<!--编辑系列标题表单模板-->
<script id="formEditSectionTitle" type="text/x-dot-template">
	<div class="form-edit-title form-horizontal">		
			<div class="control-group">
				<label class="control-label">第<span class="index">{{?it.chapter}}{{=it.chapter.index}}</span>模块{{?}}</label>
				<div class="controls">
                   {{?it.chapter}}
					<input type="text" maxlength="20" class="input-block-level" placeholder="请输入标题内容" value="{{=it.chapter.title|| ''}}" />
                    {{?}}
					<span class="count">20字</span>
				</div>
			</div>
			<div class="controls">
				<button class="btn btn-primary btn-large" type="button">保存</button>
				<button class="btn btn-link" type="button">取消</button>
			</div>		
	</div>
</script>

<!--展示系列模板-->
<script id="sectionsTemplate" type="text/x-dot-template">
		{{?it.chapter}}
			{{#def.sectionbar:it.chapter}}
		{{?}}		              
</script>

<!--系列条模板-->
<script id="sectionBarTemplate" type="text/x-dot-template">
	{{##def.sectionbar:param:
		<div class="sortable-bar">
			<span class="title">
				第<span class="index">{{=param.index}}</span>模块
				<span class="name">{{=param.title || ''}}</span>
			</span>
			<a class="icon-pencil2  btn-ctrls" href="#"></a>
			<a class="icon-remove btn-ctrls" href="#"></a>
			<a href="#course" class="btn-edit">编辑内容</a>
			<div class="state-dragable">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</div>
		</div>
	#}}
</script>

<!-- 系列模板详情_基本信息 模板 -->
<script id="basicInfoTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body">       
	<div class="info-templ-content">
        <form id="formBasicInfo" method="post" action="{{=it.action || '##'}}">  	
	    	<div class="section" >   
	            	<fieldset>
                    	<label for="seriesTitle">系列名称</label>
                        <input type="text" id="seriesTitle" name="seriesTitle" required minlength="6" class="input-block-level" value="{{=it.seriesTitle || ''}}"  />
                        <label for="seriesDesc">系列描述</label>
                        <textarea name="seriesDesc" id="seriesDesc"  class="input-block-level" rows="3">{{=it.fdDescription || ''}}</textarea>   
						<label for="seriesAuthor">作者</label>
						<input type="hidden" id="seriesAuthorId" value="{{=it.seriesAuthorId || ''}}" />
                        <input type="text" name="seriesAuthor" required  id="seriesAuthor"  class="input-block-level" value="{{=it.seriesAuthor || ''}}"/>   
						<label for="authorDesc">作者简介</label>
                        <textarea name="authorDesc" id="authorDesc"  class="input-block-level" rows="3">{{=it.authorDesc || ''}}</textarea>                    
					</fieldset>
						<!--<input name="sectionIsava" id="sectionIsava" value="{{=it.isavailable||true}}" type="hidden">
						<label for="sectionOrder"></label>
						<div class="btn-group btns-radio" data-toggle="buttons-radio">
                            <button class="btn btn-large{{?it.isavailable==null || it.isavailable}} active{{?}}" id="true" type="button">有效</button>
                            <button class="btn btn-large{{?it.isavailable!=null && !it.isavailable}} active{{?}}" id="false" type="button">无效</button>
                        </div>-->
	       </div>
           <button class="btn btn-block btn-submit btn-inverse" type="button" onClick="saveBaseInfo()">保存</button>
       </form>
       </div>	  
	 </div> 
</script>
<!-- 系列推广 模板 -->
<script id="promotionTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body promotion-content">    	
        <form id="formPromotion" method="post" action="{{=it.action || '##'}}">  	
	    	<div class="section" >              	
					<label for="CourseCover">系列封面</label>
					<input id="courseCover" name="courseCover" class="input-block-level" type="hidden" value="{{=it.coverUrl || 'images/default-cover.png'}}" />
					 <!--图片剪切-->
                    <div class="cutimg-box no" style="display:none;">
                         <iframe id="iframeimg" width="100%" height="500" id="win" name="win" frameborder="0" scrolling="no"
                            src=""></iframe>
                    </div>				
                    <!--图片预览-->
					<div class="courseCover"><img id="imgshow" name="imgshow"   src="{{=it.coverUrl || '${ctx}/resources/images/default-cover.png'}}" alt="" /></div>					
	       </div>
		   <div class="section" >              	
					<label>上传图片（支持JPG\JPEG、PNG格式的图片，建议小于2M）</label>
					<div class="control-upload">

                         <span class="progress"> <div class="bar" style="width:0;"></div> </span>
					     <span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
						     <button id="upMovie" class="btn btn-primary btn-large" type="button" >上传</button>
							<input type="hidden"  name="attId" id="attIdID">
					</div>		
	       </div>
	       <div class="courseSkins">
		   		 <label >课程皮肤</label>
				<input type="hidden" id="courseSkin" name="courseSkin" value="{{=it.courseSkin.title || ''}}" />
				<ul class="nav courseSkinList clearfix">
					{{~ it.courseSkinList :skin:index}}
						 <li{{?skin.title == it.courseSkin.title}} class="active"{{?}}><a href="#"><img src="{{=skin.imgUrl}}" alt="{{=skin.title}}" /><i class="icon-right"></i></a><h5>{{=skin.title}}</h5></li>
					{{~}}                    	                  
				</ul>
			</div>		  
           <button class="btn btn-block btn-submit btn-inverse" type="button"  onclick="saveSeriesPic();">保存</button>
       </form>	  
	 </div> 	
</script>
<!-- 删除系列 模板 -->
<script id="deleteSeriesTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body deleteCourse-content">   	        
	    	<div class="section" >
					<div class="well">
						警告：您点击下面的删除系列按钮以后，将会出现以下结果：<br />
1. 本系列将不复存在；<br />
2. 本系列中的课程资料 请前往 <a href="${ctx}/course/findcourseInfos?fdType=12&order=fdcreatetime" target="_blank" >课程信息</a> 进行查阅。
					</div>
	       </div>		 
           <button class="btn btn-block btn-warning btn-submit" id="deleteSeriesCourse" type="button">删除</button>         
	 </div> 	
</script>
<!-- 系列课程 模板 -->
<script id="mediaPageTemplate" type="text/x-dot-template">
    <div class="page-header">
<button class="btn btn-link" onclick="backDirectory();" type="button" style="position:absolute;left:10px;top:20px">返回模块目录</button>

        <h4>第{{=it.lectureIndex}}模块 {{=it.pageTitle || ''}}</h4>
<button class="btn btn-primary btn-large" onclick="saveDirectory();" type="button" style="position:absolute;right:20px;top:15px">保存</button>

    </div>
    <div class="page-body mediaPage-content">
        <form id="formMedia" method="post" class="form-horizontal" action="{{=it.action || '##'}}">
	    	<div class="section" >
				<div class="control-group">
                    <label class="control-label" for="seriesTitle">模块名称</label>
                    <div class="controls"><input value="{{=it.pageTitle || ''}}" id="seriesTitle" required minlength="3" class="input-xlarge" name="seriesTitle" type="text" /></div>
                </div>
				<div class="control-group">
                    <label class="control-label" for="sectionsIntro">模块说明</label>
                    <div class="controls">
                        <textarea placeholder="系列作者很懒，没有写模块说明。" rows="4"  class="input-xxlarge" id="sectionsIntro" name="sectionsIntro" >{{=it.sectionsIntro || ''}}</textarea>
                    </div>
                </div>
	       </div>
            <div class="mediaList">
                <label >课程列表（<span id="mediaCount">{{?it.mediaList}}{{=it.mediaList.length || ''}}{{??}}0{{?}}</span>  个）</label><label id="showError"></label>
                <ul class="unstyled" id="listMedia">
                    {{~it.mediaList :item:index}}
                        {{~it.mediaList :item2:index2}}
                           {{?(index+1) == item2.index}}
                            <li data-fdid="{{=item.id}}"><span class="title">课程 <span class="index">{{=item2.index}}</span>：<span class="name">{{=item2.title}}</span></span>
                                <a class="icon-remove btn-ctrls" href="#"></a>
                                <div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
                            </li>
                            {{?}}
                        {{~}}
                    {{~}}
                </ul>
            </div>
            <div class="section" >
                <label>或者从 <a id="gotoMaterial" href="#">课程列表</a> 中选择课程</label>
                <div class="autoCompleteWrap">
					<input id="addMedia" type="text" />
					<!--
					<button class="btn btn-primary btn-large" type="button" >选择</button>
					-->
                </div>
            </div>
           <button class="btn btn-block btn-submit btn-inverse" type="submit">保存</button>
       </form>
	 </div>
</script>
<!-- 系列课程列表项 模板 -->
<script id="mediaListTemplate" type="text/x-dot-template">
    <li data-fdid="{{=it.id}}"><span class="title">课程 <span class="index">{{=it.index}}</span>：<span class="name">{{=it.name}}</span></span>
        <a class="icon-remove btn-ctrls" href="#"></a>
        <div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
    </li>
</script>
<!--编辑系列课程 标题表单模板-->
<script id="formEditMediaTitle" type="text/x-dot-template">
    <li class="form-edit-title form-horizontal">
        <div class="control-group">
            <label class="control-label">{{=it.typeTxt}}<span class="index">{{=it.index}}</span></label>
            <div class="controls">
                <input type="text" class="input-block-level" placeholder="请输入标题" value="{{=it.name || ''}}" />
                <span class="count">20字</span>
            </div>
        </div>
        <div class="controls">
            <button class="btn btn-primary btn-large" type="button">保存</button>
            <button class="btn btn-link" type="button">取消</button>
        </div>
    </li>
</script>
<script src="${ctx}/resources/js/doT.min.js"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${pageContext.request.contextPath}"/>
<section class="container">
	<div class="clearfix">
		<div class="tit-bar">    	
	        <div class="page-title section" id="page-title">
	        	<input type='hidden' id='seriesId' value='${series.fdId}' />
	        	<a href="${ctx}/series/findSeriesInfos?fdType=11&order=fdcreatetime" class="backParent">返回系列课程列表</a>
	        	<h5>
	        	<tags:title size="33" value="${series.fdName}"></tags:title>
	        	</h5>
	            <div class="btn-group">
		            <button class="btn btn-primary btn-large" disabled  type="button" onclick="previewCourse()">预览</button>
		            <button class="btn btn-primary btn-large" disabled type="button" onclick="releaseCourse()">发布</button>
	            </div>
	        </div>
	        <img src="${ctx}/resources/images/admin-leftImg.png" width="187" height="60">
	    </div>
	  <div class="col-left pull-left">
	<ul class="nav nav-list sidenav" id="sideNav">
	            <li class="nav-header">
	                <i class="icon-content"></i>
	                系列内容
	            </li>
	            <li class="active"><a href="#" data-target='basicInfo'>系列信息</a></li> 
	            <li ><a href="#" data-target='sectionsDirectory'>模块目录</a></li>
	             <li class="nav-header">
	                <i class="icon-setting"></i>
	                系列设置
	            </li>
	            <li><a href="#" data-target='promotion'>系列推广</a></li>  
	            <li><a href="#" data-target='deleteSeries'>系列删除</a></li>       
	    </ul>
	  </div>
		<div class="w790 pull-right" id="rightCont">    
	   
	    </div>
	</div>
</section>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/templSeriesPages.js"></script>
<script src="${ctx }/resources/selectarea/jquery.imgareaselect.js" type="text/javascript"></script>
<script type="text/javascript">	
$(function(){
$.Placeholder.init();
});
//点击左侧菜单事件
$("#sideNav>li>a").bind("click",function(e){	
	if ($('#upMovie').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#upMovie').uploadify('destroy'); 
	}
	urlRouter($(this).attr('data-target'));		
});
function backDirectory(){//返回阶段目录
	if ($('#upMovie').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#upMovie').uploadify('destroy'); 
	}
	urlRouter("sectionsDirectory");
}
function saveDirectory(){//触发一个提交事件
	$("#formMedia").trigger("submit");
}
	//根据URL中‘#’后参数判断加载栏目
	function urlRouter(href,opt){
		setTimeout(function(){
			
			var param = href ? href : location.href.split("#").pop();			
			$("#sideNav>li>a[data-target='" + param + "' ]").parent().addClass("active").siblings().removeClass("active");
			switch(param){			
	  			case "basicInfo":
	  					rightCont.loadBasicInfoPage("系列信息");
	  				break;
	  			case "sectionsDirectory":
					rightCont.loadSectionDirectoryPage("章节目录");
  				    break;
	  			case "promotion":
	  				if($('#seriesId').val()!=null &&  $('#seriesId').val()!=''){
	  					rightCont.loadPromotionPage("系列推广");
	  				}else{
	  					jalert_tips("请先设置系列信息");
	  					$("#sideNav>li>a[data-target='basicInfo']").parent().addClass("active").siblings().removeClass("active");
	  					rightCont.loadBasicInfoPage("基本信息");
	  				}
	  				break;
				case "deleteSeries":
					if($('#seriesId').val()!=null &&  $('#seriesId').val()!=''){
						rightCont.loadDeleteCoursePage("删除系列",$("#seriesId").val());
	  				}else{
	  					jalert_tips("请先设置系列信息");
	  					$("#sideNav>li>a[data-target='basicInfo']").parent().addClass("active").siblings().removeClass("active");
	  					rightCont.loadBasicInfoPage("基本信息");
	  				}
	  				break;
				case "course":
                    if(opt) {
                        rightCont.loadVideoPage(opt);
                        break;
                    }
	  			default:
	  				rightCont.loadSectionDirectoryPage("章节目录");
	  				rightCont.loadBasicInfoPage("系列信息");
	  		}
		},10);
		
		
	}
	urlRouter();

	//ajax保存系列基本信息
	function saveBaseInfo(){
		if(!$("#formBasicInfo").valid()){
			return;
		}
		$.post('${ctx}/ajax/series/saveSeriesBaseInfo',{
			 seriesId:$("#seriesId").val(),
			 seriesTitle: $("#seriesTitle").val(),
			 seriesDesc:  $("#seriesDesc").val()==""?"系列作者很懒，没有写系列描述。":$("#seriesDesc").val(),
			 seriesAuthor: $("#seriesAuthor").val(),
			 seriesAuthorId: $("#seriesAuthorId").val(),
			 authorDesc: $("#authorDesc").val()
			 //isavailable:$("#sectionIsava").val()
			},function(data){
				$("#seriesId").val(data.seriesId);
			},"json")
		    .success(function(){
			//提交成功系列推广
			jalert_tips("保存成功");
			$("#goTop").trigger("click");
       	   // urlRouter("promotion");
		});
	}
	//系列封页图片保存
    function saveSeriesPic(){
    	$.post('${ctx}/ajax/series/saveSeriesPic',{
			seriesId : $("#seriesId").val(),
			attId: $("#attIdID").val()
			})
		.success(function(){
			jalert_tips("保存成功");
			$("#goTop").trigger("click");
		});
    }
  //系列发布
	function releaseCourse(){
		  //发布前验证系列的基本信息是否已完善(防止出现未命名情况);
	  //  if($("#isperfect").val()=='false'){
	  //  	jalert2("请完善系列信息!");
	  //  	urlRouter("basicInfo");
	  //  	return ;
	  //  }
		$.post('${ctx}/ajax/series/releaseSeries',{
			 seriesId:$("#seriesId").val()
			})
		.success(function(){
			window.location.href="${ctx}/series/findSeriesInfos?fdType=11&order=fdcreatetime";
		});
		//window.location.href="${ctx}/course/releaseCourse?courseId="+$("#courseId").val();
	} 
	
	//系列预览
	function previewCourse(){
		saveCourseSigleInfo();
		window.open("${ctx}/series/previewSeries?seriesId="+$("#seriesId").val(),'_blank');
	}
	function saveCourseSigleInfo(){
		if($("#formBasicInfo").length>0){
			saveBaseInfo();//基本信息
		}
		if($("#formMedia").length>0){
			$("#formMedia").trigger("submit");
		}
		if($("#formPromotion").length>0){
			saveSeriesPic();//课程推广
		}
	}
	//图片剪切成功
	function successSelectArea(imgSrc){
	    var now=new Date();
	    var number = now.getSeconds();
	    jQuery("#imgshow").attr('src',  imgSrc+"?n="+number);
	    $(".cutimg-box").html("");
	    $(".cutimg-box").hide();
	    $(".imgareaselect-outer").remove();
	    $(".imgareaselect-border1").remove();
	    $(".imgareaselect-border2").remove();
	    //imgshow
	    $("#imgshow").show();
	}

</script>
</body>
</html>
