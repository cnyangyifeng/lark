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
<link href="${ctx}/resources/css/template_detail.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/kindeditor/themes/default/default.css" />
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->

<!-- 模板详情_右侧内容区标题 模板 -->
<script id="contHeaderTemplate" type="text/x-dot-template">
 	<div class="page-header">  
		<h4>{{=it.pageTitle || ''}}</h4>  
	</div>
</script>

<!-- 模板详情_章节目录 模板 -->
<script id="sectionDirectoryTemplate" type="text/x-dot-template">
	{{#def.pageHeader}}
    	<div class="page-body">
	    	<div class="section" id="progress_courses">          
	       		
	            	<div class="progress progress-course">
	            		<div class="bar" style="width:0%;"></div>
	            	</div>
	                您已经完成了本课程的 <span class="num_comp"></span> 个节内容，共计 <span class="num_all">

{{=it.lecture.length}}</span> 个节。
	        </div>
	       
	      <div class="sortableWrap">
	      	<ul class="sortable" id="sortable">  
			    	{{  for(var i=0; i<(it.chapter.length+it.lecture.length); i++){  }}
						{{~it.chapter :chp:index}}
							{{?chp.index == i}}	
								<li class="chapter" data-fdid="{{=chp.id}}">		
								{{#def.sectionbar:chp}}	
								</li>			
							{{?}}
						{{~}}
						{{~it.lecture :lec:index}}
							{{?lec.index == i}}
								<li class="lecture" data-fdid="{{=lec.id}}">
								{{#def.sectionbar:lec}}
								{{#def.lecturecontent:lec}}
								</li>
							{{?}}
						{{~}}
					{{ } }}
	      	</ul>
	        <div class="section-add">
                <button id="addChapter" class="btn btn-block" type="button">添加章</button>
	        	<button id="addLecture" class="btn btn-block btn-lecture-add" type="button">添加节</button>
	        </div>
	      </div>
      </div> 
</script>

<!--添加章节表单模板-->
<script id="addSectionsTemplate" type="text/x-dot-template">
	<li class="{{?it.chapter}}chapter{{??it.lecture}}lecture{{?}}" data-fdid="">
		{{#def.edittitle || ''}}		
	</li>
</script>

<!--编辑章节标题表单模板-->
<script id="formEditSectionTitle" type="text/x-dot-template">
	<div class="form-edit-title form-horizontal">		
			<div class="control-group">
				<label class="control-label">第<span class="index">{{?it.chapter}}{{=it.chapter.num}}</span>

章{{??it.lecture}}{{=it.lecture.num}}</span>节{{?}}</label>
				<div class="controls">
					<input type="text" maxlength="20" class="input-block-level" placeholder="请输入课程

标题" value="{{=(it.chapter ? it.chapter.title : it.lecture.title) || ''}}" />
					<span class="count">20字</span>
				</div>
			</div>
			<div class="controls">
				<button class="btn btn-primary btn-large" type="button">保存</button>
				<button class="btn btn-link" type="button">取消</button>
			</div>		
	</div>
</script>

<!--展示章节模板-->
<script id="sectionsTemplate" type="text/x-dot-template">
		{{?it.chapter}}
			{{#def.sectionbar:it.chapter}}
		{{??it.lecture}}
			{{#def.sectionbar:it.lecture}}
			{{#def.lecturecontent:it.lecture}}
		{{?}}		              
</script>

<!--章节条模板-->
<script id="sectionBarTemplate" type="text/x-dot-template">
	{{##def.sectionbar:param:
		<div class="sortable-bar">
			<span class="title">{{?param.type}}<i class="icon-{{=param.type}}"></i>{{?}}
				第<span class="index">{{=param.num}}</span>{{?param.type}}节{{??}}章{{?}}
				<span class="name">{{=param.title || ''}}</span>
			{{?param.type}}<label class="label" >{{?param.isElective!=null && param.isElective=='0'}}选修{{??}}

必修{{?}}</label>{{?}}
			</span>
			<a class="icon-pencil2{{?!param.type}} icon-white{{?}} btn-ctrls" href="#"></a>
			<a class="icon-remove{{?!param.type}} icon-white{{?}} btn-ctrls" href="#"></a>
			{{?param.type}}
			<a href="#" class="btn-edit">编辑内容</a>
			{{?}}
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

<!--节内容模板-->
<script id="lectureContentTemplate" type="text/x-dot-template">
	{{##def.lecturecontent:param:
		<div class="lecture-content hide">
			<div class="hd">
			编辑内容
			<a href="#" class="icon-remove-sign"></a>
			</div>
			<div class="bd">
				<a class="btn-type{{?param.type != 'none' && param.type != 'video'}} disabled{{?}}" href="#video"><i class="icon-video-lg"></i><h5>视频</h5></a>
				<a class="btn-type{{?param.type != 'none' && param.type != 'doc'}} disabled{{?}}" href="#doc"><i class="icon-doc-lg"></i><h5>文档</h5></a>
				<a class="btn-type{{?param.type != 'none' && param.type != 'ppt'}} disabled{{?}}" href="#ppt"><i class="icon-ppt-lg"></i><h5>幻灯片</h5></a>
				<a class="btn-type{{?param.type != 'none' && param.type != 'exam'}} disabled{{?}}" href="#exam"><i class="icon-exam-lg"></i><h5>测试</h5></a>
				<a class="btn-type{{?param.type != 'none' && param.type != 'task'}} disabled{{?}}" href="#task"><i class="icon-task-lg"></i><h5>作业</h5></a>
				<a class="btn-type{{?param.type != 'none' && param.type != 'txt'}} disabled{{?}}" href="#txt"><i class="icon-txt-lg"></i><h5>在线创作</h5></a>
				<a class="btn-type{{?param.type == 'none' || param.type != 'audio'}} disabled{{?}}" href="#audio"><i class="icon-audio-lg"></i><h5>音频</h5></a>
				<a class="btn-type{{?param.type == 'none' || param.type != 'img'}} disabled{{?}}" href="#img"><i class="icon-img-lg"></i><h5>图片</h5></a>
				<a class="btn-type{{?param.type == 'none' || param.type != 'calendar'}} disabled{{?}}" href="#calendar"><i class="icon-calendar-lg"></i><h5>日程安排</h5></a>
            </div>
		</div>
	#}}
</script>

<!-- 模板详情_基本信息 模板 -->
<script id="basicInfoTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body">       
	<div class="info-templ-content">
        <form id="formBasicInfo" method="post" action="{{=it.action || '##'}}">  	
	    	<div class="section" >   
	            	<fieldset>
                    	<label for="courseTitle">标题</label>
                        <input type="text" id="courseTitle" name="courseTitle" required minlength="6" class="input-block-
level" value="{{=it.courseTit || ''}}"  />
                        <label for="subTitle">副标题</label>
                        <textarea name="subTitle" id="subTitle"   class="input-block-level" rows="3">
{{=it.subTit || ''}}</textarea>
<label for="fdPrice">课程定价</label>
<input name="fdPrice" id="fdPrice" value="{{=it.fdPrice||''}}" class="number" decimal="true" type="text">
<label for="isCompulsoryCourse">是否必修课</label>
                        <input name="isCompulsoryCourse" id="isCompulsoryCourse" value="{{=it.isCompulsoryCourse||false}}" type="hidden">
                        <label for="isCompulsoryCourse"></label>
						 <div class="btn-group btns-radio" id="btns-radio-isCompulsoryCourse" data-toggle="buttons-radio">
                            <button class="btn btn-large{{?it.isCompulsoryCourse!=null && it.isCompulsoryCourse}} active{{?}}" id="true" 
type="button">必修</button>
                            <button class="btn btn-large{{?it.isCompulsoryCourse==null || !it.isCompulsoryCourse}} active{{?}}"
id="false" type="button">选修</button>
                        </div>
                        <label for="sectionOrder">章节顺序</label>
                        <input name="sectionOrder" id="sectionOrder" value="{{?it.sectionOrder==null}}true{{??}}{{=it.sectionOrder}}{{?}}" type="hidden">
                        <label for="sectionOrder"></label>
						 <div class="btn-group btns-radio" id="btns-radio-sectionOrder" data-toggle="buttons-radio">
                            <button class="btn btn-large{{?it.sectionOrder==null || it.sectionOrder}} active{{?}}" id="true" 

type="button">顺序学习</button>
                            <button class="btn btn-large{{?it.sectionOrder!=null && !it.sectionOrder}} active{{?}}" 

id="false" type="button">无序学习</button>
                        </div>



                        <label >关键词</label>
                        <div class="keywordWrap">
                        	<input type="hidden" id="keyword" name="keyword" value="{{= it.keyword || '' }}" />
							{{~ it.keyword :key:index}}
                         		 <span class="alert alert-tag"><span>{{=key}}</span><a href="#" data-dismiss="alert" 
class="close">&times;</a></span>
                         	{{~}}
                          <a href="#" class="btn-add">+</a>
                          
                        </div>
                    </fieldset>	
                    <label >分类</label>
                    <input type="hidden" id="courseType" name="courseType" value="{{=it.courseType || ''}}" />
                    <ul class="nav nav-pills courseType">
 						<li{{?it.courseType==""}} class="active" {{?}}><a href="#">默认分类</a>
						<input type='hidden' value=''>
						</li>
						{{~ it.courseTypeList :type:index}}
							 <li{{?type.id == it.courseType}} class="active"{{?}}>
							 <a href="#">{{=type.title}}</a>
                             <input type='hidden' value='{{=type.id}}'>
                             </li>
						{{~}}                    	                  
                    </ul>  
	       </div>
           <button class="btn btn-block btn-submit btn-inverse" type="button" onClick="saveBaseInfo()">保存</button>
       </form>
       </div>	  
	 </div> 
</script>

<!-- 模板详情_基本信息 添加关键词 模板 -->
<script id="addKeywordInfoTemplate" type="text/x-dot-template">
		<div class="inpKeyword form-inline control-group">
			<input type="text" class="span5" id="addKey" onblur="reminder();" placeholder="请输入关键词" />
			<button class="btn btn-large btn-primary" type="button">确定</button>
			<button class="btn btn-large" type="button">取消</button>
		  </div>
</script>

<!-- 模板详情_基本信息 关键词标签 模板 -->
<script id="tagKeywordInfoTemplate" type="text/x-dot-template">
		<span class="alert alert-tag"><span>{{=it.keyword}}</span><a href="#" data-dismiss="alert" 

class="close">&times;</a></span>
</script>

<!-- 模板详情_详细信息 模板 -->
<script id="detailInfoTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body detail-content">    	
        <form id="formDetailInfo" method="post" action="{{=it.action || '##'}}">  	
	    	<div class="section" >   
					<label for="courseAbstract">课程摘要</label>
					<textarea id="courseAbstract" name="courseAbstract" 

style="width:100%;height:200px;visibility:hidden;"></textarea>
					<label for="courseAuthor">作者</label>
					<input type="hidden" id="courseAuthorId" name="courseAuthorId" value="{{=it.courseAuthorId || ''}}" />
					<input type="text" id="courseAuthor" name="courseAuthor" required  class="input-block-level" 

value="{{=it.courseAuthor || ''}}">
					<label for="authorDescrip">作者描述</label>
					<textarea id="authorDescrip" name="authorDescrip"  class="input-block-level" 

rows="4">{{=it.authorDescrip || ''}}</textarea>
					<label for="learnObjectives">学习目标</label>
					<input type="hidden" id="learnObjectives" name="learnObjectives" value="{{= 

it.learnObjectives || '' }}" />
					<ul class="list_alert nav">						
						{{~ it.learnObjectiveslist :obj:index}}
							 {{#def.item:obj}}
						{{~}}					  
					</ul>
					{{#def.formAddItem:"学习目标"}}
					<label for="suggestedGroup">建议群体</label>
					<input type="hidden" id="suggestedGroup" name="suggestedGroup" value="{{= 

it.suggestedGroup || '' }}" />
					<ul class="list_alert nav">						
						{{~ it.suggestedGrouplist :obj:index}}
							 {{#def.item:obj}}
						{{~}}					  
					</ul>
					{{#def.formAddItem:"建议群体"}}
                    <label for="courseRequirements">课程要求</label>
					<input type="hidden" id="courseRequirements" name="courseRequirements" value="{{= 

it.courseRequirements || '' }}" />
					<ul class="list_alert nav">						
						{{~ it.courseRequirementslist :obj:index}}
							 {{#def.item:obj}}
						{{~}}					  
					</ul>
					{{#def.formAddItem:"课程要求"}}
                    
	       </div>
           <button class="btn btn-block btn-submit btn-inverse" type="button" id="saveDetailInfo">保存</button>
       </form>	  
	 </div> 
	 {{##def.formAddItem:param:
	 	<div class="formAdd form-inline control-group"><input type="text" placeholder="请填写{{=param}}" /><button 

class="btn btn-large btn-primary" type="button">添加</button></div>
	 #}}
	 {{##def.item:param:
	 	<li class="alert"><i class="icon-square"></i><span>{{=param}}</span><a href="#" data-dismiss="alert" 

class="close">&times;</a></li>
	 #}}
</script>
<!-- 模板详情_详细信息 列表项 模板 -->
<script id="itemDetailInfoTemplate" type="text/x-dot-template">	
		<li class="alert"><i class="icon-square"></i><span>{{=it}}</span><a href="#" data-dismiss="alert" 

class="close">&times;</a></li>
</script>

<!-- 课程推广 模板 -->
<script id="promotionTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body promotion-content">    	
        <form id="formPromotion" method="post" action="{{=it.action || '##'}}">  	
	    	<div class="section" >              	
					<label for="CourseCover">课程封面</label>
					<input id="courseCover" name="courseCover" class="input-block-level" type="hidden" 

value="{{=it.coverUrl || 'images/default-cover.png'}}" />
                <!--图片剪切-->
                <div class="cutimg-box hide" >
                    <iframe id="iframeimg" width="100%" height="500" frameborder="0" scrolling="no"
                            src=""></iframe>
                </div>
									<!--图片预览-->
					<div class="courseCover"><img id="imgshow" name="imgshow"  src="{{=it.coverUrl || '${ctx}/resources/images/default-cover.png'}}" alt="" /></div>			

		
	       </div>
		   <div class="section" >              	
					<label>上传图片（支持JPG\JPEG、PNG、BMP格式的图片，建议小于2M）</label>
					<div class="control-upload">
                         <span class="progress"> <div class="bar" style="width:0;"></div> </span>
					     <span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
						     <button id="upMovie" class="btn btn-primary btn-large" type="button" >上传</button>

							<input type="hidden"  name="attId" id="attIdID">
					</div>		
	       </div>
			<input type="hidden" id="coursePicture" name="coursePicture" value="{{=it.coursePictureId || ''}}" />
			<div id="coursePicturediv">
	   		{{?it.coursePictureList.totalCount>0}}
			<div class="courseSkins">
		   		 <label >封面图片库</label>
				<div class="btn-group">
				<button class="btn btn-primary btn-ctrl" type="button" {{?it.coursePictureList.pageNo<= 1}}disabled{{??}}onclick="pageNavClick({{=it.coursePictureList.pageNo - 1}})"{{?}}>
					<i class="icon-chevron-left icon-white"></i>
				</button>
				<button class="btn btn-primary btn-ctrl" type="button" {{?it.coursePictureList.pageNo>= it.coursePictureList.pageCount}}disabled{{??}}onclick="pageNavClick({{=it.coursePictureList.pageNo + 1}})"{{?}}>
					<i class="icon-chevron-right icon-white"></i>
				</button>
				</div>
				
				<ul class="nav courseSkinList clearfix coursePicture">
					{{~ it.coursePictureList.pictureList :picture:index}}
						 <li{{?picture.fdId == it.coursePictureId}} class="active"{{?}}><a 

href="#" data-fdid="{{=picture.fdId}}" data-img="${ctx}/common/file/image/{{=picture.imgUrl}}"><img src="${ctx}/common/file/image/{{=picture.imgUrl}}" alt="{{=picture.title}}" /><i class="icon-right"></i></a><h5>{{=picture.title}}</h5></li>
					{{~}}                    	                  
				</ul>
			</div>	
			{{?}}
		   </div>		  
		   <div class="courseSkins">
		   		 <label >课程皮肤</label>
				<input type="hidden" id="courseSkin" name="courseSkin" value="{{=it.courseSkinId ||  

''}}" />
			<ul class="nav courseSkinList clearfix courseSkin">
					{{~ it.courseSkinList :skin:index}}
						 <li{{?skin.fdId == it.courseSkinId}} class="active"{{?}}><a 

href="#" data-fdid="{{=skin.fdId}}"><img src="{{=skin.imgUrl}}" alt="{{=skin.title}}" /><i class="icon-right"></i></a><h5>{{=skin.title}}</h5></li>
					{{~}}                    	                  
				</ul>
			</div>
           <button class="btn btn-block btn-submit btn-inverse" type="button"  onclick="saveCoursePic();">保存</button>
       </form>	  
	 </div> 	
</script>

<!-- 访问权限 模板 -->
<script id="accessRightTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body accessRight-content">    	
        <form id="formAccessRight" method="post" action="{{=it.action || '#accessRight'}}">  	
	    	<div class="section" >              	
					<label>权限设置
						<input type="hidden" id="permission" name="permission" 

value="{{=it.permission || 'open'}}" />						
					</label>
					<ul class="nav nav-pills">
							<li{{?it.permission == 'open' || it.permission == ''}} 

class="active"{{?}}><a data-toggle="tab" href="#open">公开</a></li>
							<li{{?it.permission == 'encrypt'}} class="active"{{?}} id="passLi"><a data-

toggle="tab" href="#encrypt">加密</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane{{?it.permission == 'open' || it.permission == ''}} 

active{{?}}" id="open">
						提示："公开"课程将允许所有NTP用户（企业集团教职员工）访问。而"私密"课程将

提醒课程负责主管手动授权或者密码访问。
						</div>
						<div class="tab-pane{{?it.permission == 'encrypt'}} active{{?}}" 

id="encrypt">
							<label  class="radio" for="authorized"><input type="radio" 

onclick="removePass()" value="authorized" {{?it.encryptType == 'authorized' || it.encryptType == ''}}checked{{?}} 

name="encryptType" id="authorized" /><span class="labelTxt">授权课程学习</span>前往 <a href="#kinguser" onClick="urlRouter

()" >授权管理</a> 本课程的用户列表</label>
							<label  class="radio" id="passRadio" for="passwordProtect"><input 

type="radio" value="passwordProtect" {{?it.encryptType == 'passwordProtect'}}checked{{?}} name="encryptType" 

id="passwordProtect" /><span class="labelTxt">密码保护</span><input type="password" id="coursePwd"  name="coursePwd" {{?

it.encryptType !='passwordProtect'}}disabled{{?}} placeholder="请填写课程访问的密码" value="{{=it.coursePwd || ''}}" 

/></label>
						</div>
					</div>					
	       </div>

<div class="page-body kinguser-content" id="courseGroupDiv">  
<div class="section">
<table class="table table-bordered">
	<thead>
		<tr><th>授权群组</th><th>删除</th></tr>
	</thead>
	<tbody id="list_group">
		{{~ it.list:item:index}}
		<tr draggable="true" data-fdid="{{=item.id}}">
			<td class="tdTit">
				</div>{{=item.gName}}</div> 
			</td>
			<td>
				<a href="#" class="icon-remove-blue"></a>
			</td>
		</tr>
		{{~}}
		{{?it.list.length==0}}
		<tr draggable="true" data-fdid="all">
			<td class="tdTit">
				</div>全体教职员工</div> 
			</td>
			<td>
				<a href="#" class="icon-remove-blue"></a>
			</td>
		</tr>
		{{?}}
	</tbody>
</table>
<input type="text" id="addGroup" class="autoComplete ac_input" autocomplete="off"> 
</div>
</div>

		 
           <button class="btn btn-block btn-submit btn-inverse" type="button" onClick="saveIsPublish()">保存</button>
       </form>	  
	 </div> 	
</script>

<!-- 授权管理 模板 -->
<script id="kinguserTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body kinguser-content">   	        
	    	<div class="section" >
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>授权用户</th>
								<th>可使用</th>
								<th>可编辑</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody id="list_user">
									<tr data-fdid="{{=it.createrid}}" data-creater="creater">
										<td class="tdTit">
                                          <div class="pr">
											<div class="state-dragable"><span 

class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span 

class="icon-bar"></span></div>
											<img src="{{?

it.createrimgUrl.indexOf('http')>-1}}{{=it.createrimgUrl}}{{??}}${ctx}/{{=it.createrimgUrl}}{{?}}" />
											{{=it.creatername}}

（{{=it.creatermail}}）， {{=it.createrdepartment}}
										 </div>
                                          </td>
										<td><input type="checkbox" checked 

onclick="return false" class="tissuePreparation" /></td>
										<td><input type="checkbox" checked 

onclick="return false" class="editingCourse" /></td>
										<td></td>
									</tr>
							{{~it.user :user:index}}
								{{~it.user :user:index2}}
									{{?index == user.index}}
									<tr data-fdid="{{=user.id}}" data-creater="uncreater">
										<td class="tdTit">
                                          <div class="pr">
											<div class="state-dragable"><span 

class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span 

class="icon-bar"></span></div>
												<img src="{{?

user.imgUrl.indexOf('http')>-1}}{{=user.imgUrl}}{{??}}${ctx}/{{=user.imgUrl}}{{?}}" />{{=user.name}}（{{=user.mail}}），

{{=user.org}} {{=user.department}}
										 </div>
                                          </td>
										<td><input type="checkbox" {{?

user.tissuePreparation}}checked{{?}} class="tissuePreparation" /></td>
										<td><input type="checkbox" {{?

user.editingCourse}}checked{{?}} class="editingCourse" /></td>
										<td><a href="#" class="icon-remove-

blue"></a></td>
									</tr>
									{{?}}
								{{~}}
							{{~}}
						</tbody>
					</table>
					<input type="text" id="addUser" class="autoComplete" />
	       </div>		 
           <button class="btn btn-block btn-submit btn-inverse" id="submitUser" type="button">保存</button>         
	 </div> 	
</script>

<!-- 授权管理 用户列表 模板 -->
<script id="listUserKinguserTemplate" type="text/x-dot-template">
<tr data-fdid="{{=it.id}}" data-creater="uncreater">
	<td class="tdTit">
        <div class="pr">
		<div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-

bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
		<img src="{{?it.imgUrl.indexOf('http')>-1}}{{=it.imgUrl}}{{??}}${ctx}/{{=it.imgUrl}}{{?}}" />
		{{=it.name}}（{{=it.mail}}），{{=it.org}} {{=it.department}}
		</div>
    </td>
	<td><input type="checkbox" checked class="tissuePreparation" /></td>
	<td><input type="checkbox" checked class="editingCourse" /></td>
	<td><a href="#" class="icon-remove-blue"></a></td>
</tr>
</script>

<!-- 授权管理 群组列表 模板 -->
<script id="listCourseGroupTemplate" type="text/x-dot-template">

<tr draggable="true" data-fdid="{{=it.groupId}}">
			<td class="tdTit">
				</div>{{=it.groupName}}</div> 
			</td>
			<td>
				<a href="#" class="icon-remove-blue"></a>
			</td>
</tr>

</script>

<!-- 删除课程 模板 -->
<script id="deleteCourseTemplate" type="text/x-dot-template">
	{{#def.pageHeader}} 
    <div class="page-body deleteCourse-content">   	        
	    	<div class="section" >
					<div class="well">
						警告：您点击下面的删除课程按钮以后，将会出现以下结果：<br />
1. 本课程将不复存在；<br />
2. 本课程中的内容资料（包括视频、文档等）请前往 <a href="${ctx}/material/findList?fdType=01&order=FDCREATETIME" target="_blank">课程素材库 </a>进行查阅。
					</div>
	       </div>		 
           <button class="btn btn-block btn-warning btn-submit" id="deleteCourse" type="button">删除</button>         
	 </div> 	
</script>

<!-- 视频,文档。。。页 模板 -->
<script id="mediaPageTemplate" type="text/x-dot-template">
    <div class="page-header">
         <button class="btn btn-link" onclick="backDirectory();" type="button" style="position:absolute;left:10px;top:20px">返回章节目录</button>
    <h4>第{{=it.lectureIndex}}节 {{=it.pageTitle || ''}}</h4>
		<button class="btn btn-primary btn-large" onclick="saveDirectory();" type="button" style="position:absolute;right:20px;top:15px">保存</button>
    </div>
    <div class="page-body mediaPage-content">
        <form id="formMedia" method="post" class="form-horizontal" action="{{=it.action || '##'}}">
	    	<div class="section" >
				<div class="control-group">
 

                    <label class="control-label" >章节设置</label>

                    <div class="controls">
                        <input name="isElective" id="isElective" value="{{=it.isElective || '1'}}" type="hidden">
                        <div class="btn-group btns-radio" data-toggle="buttons-radio">
                            <button class="btn btn-large{{?it.isElective=='1' || it.isElective==null}} active{{?}}" 

id="obligatory" type="button">必修</button>
                            <button class="btn btn-large{{?it.isElective=='0'}} active{{?}}" id="elective" type="button">选

修</button>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="lectureName">章节名称</label>
                    <div class="controls"><input value="{{=it.pageTitle || ''}}" id="lectureName" required class="input-

xlarge" name="lectureName" type="text" /></div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="learnTime">学习时长</label>
                    <div class="controls"><input value="{{=it.learnTime || ''}}"  placeholder="请认真填写该章节的建

议学习时长" id="learnTime" class="input-xlarge" name="learnTime" type="text" /></div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="sectionsIntro">章节说明</label>
                    <div class="controls">
                        <textarea placeholder="课程作者很懒，没有写章节说明。" rows="4"  class="input-

xxlarge" id="sectionsIntro" name="sectionsIntro" >{{=it.sectionsIntro || ''}}</textarea>
                    </div>
                </div>
	       </div>
            <div class="mediaList">
                <label >{{=it.typeTxt}}列表（<span id="mediaCount">{{=it.mediaList.length || '0'}}</span>  个）</label>
				
				<label id="materErr" class="error" style="display: none;"></label>
                <ul class="unstyled" id="listMedia">
                    {{~it.mediaList :item:index}}
                        {{~it.mediaList :item2:index2}}
                            {{?(index+1) == item2.index}}
                            <li data-fdid="{{=item.id}}"><span class="title">
							{{=cut_str(it.typeTxt, 32)}}
                           <span class="index">

{{=item2.index}}</span>：<span class="name">
{{=cut_str(item.title, 38)}}
</span></span>
                                <a class="icon-pencil2 btn-ctrls" href="#"></a>
                                <a class="icon-remove btn-ctrls" href="#"></a>
                                <div class="state-dragable"><span class="icon-bar"></span><span class="icon-

bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
                            </li>
                            {{?}}
                        {{~}}
                    {{~}}
                </ul>
            </div>
			{{?it.type != 'exam' && it.type != 'task' && it.type != 'txt'}}
		    <div class="section" >
					<label>{{=it.uploadIntro || ''}}</label>
					<div class="control-upload">

						<input type="hidden"  name="attId" id="attId">
                        <span class="progress"> <div class="bar" style="width:0;"></div> </span>
					    <span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
                      
                        <button id="upMaterial" class="btn btn-primary btn-large" type="button">上传</button>
					</div>
	        </div>
			{{?}}
            <div class="section" >
                <label>在下面的搜索框中输入<a id="gotoMaterial" href="#">课程素材库</a>中的{{=it.typeTxt}}名称，可以添加到上面的{{=it.typeTxt}}列表中。</label>
                <div class="autoCompleteWrap">
					<input style="width:716px;" id="addMedia" type="text"/>
					<!--
					<button class="btn btn-primary btn-large" type="button" >选择</button>
					-->
                </div>
            </div>
           <button class="btn btn-block btn-submit btn-inverse" type="submit">保存</button>
       </form>
	 </div>
</script>

<!-- 视频,文档。。。列表项 模板 -->
<script id="mediaListTemplate" type="text/x-dot-template">
    <li data-fdid="{{=it.id}}"><span class="title">{{=it.typeTxt}}
        <span class="index">{{=it.index}}</span>：<span class="name">
                         {{?it.name.length>38}}
							{{=it.name.substring(0,38)}}...
						{{??}}
							{{=it.name}}
						{{?}}
        </span></span>
        <a class="icon-pencil2 btn-ctrls" href="#"></a>
        <a class="icon-remove btn-ctrls" href="#"></a>
        <div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-

bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
    </li>
</script>

<!--编辑视频,文档。。。列表项 标题表单模板-->
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
<script charset="utf-8" src="${ctx}/resources/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${ctx}/resources/kindeditor/lang/zh_CN.js"></script>
</head>

<body>

<input type="hidden" id="ctx" value="${pageContext.request.contextPath}"/>
<section class="container">
	<div class="clearfix">
		<div class="tit-bar">    	
	        <div class="page-title section" id="page-title">
	        	<input type='hidden' id='courseId' value='${course.fdId}' />
	        	<a href="${ctx}/course/findcourseInfos?fdType=12&order=fdcreatetime" class="backParent">返回课程列表</a>
	        	<h5>
	        	 <tags:title size="33" value="${course.fdTitle}"></tags:title>
	        	 </h5>
	            <div class="btn-group">
		            <button class="btn btn-primary btn-large" disabled type="button" onclick="previewCourse()">预览

</button>
		            <button class="btn btn-primary btn-large" disabled type="button" onclick="releaseCourse()">发布

</button>
	            </div>
	        </div>
	        <img src="${ctx}/resources/images/admin-leftImg.png" width="187" height="60">
	    </div>
	  <div class="col-left pull-left">
	<ul class="nav nav-list sidenav" id="sideNav">
	            <li class="nav-header">
	                <i class="icon-content"></i>
	                课程内容
	            </li>
	            <li class="active"><a href="#" data-target='basicInfo'>基本信息</a></li>  
	            <li><a href="#" data-target='sectionsDirectory'>章节目录</a></li> 
	             <li class="nav-header">
	                <i class="icon-info"></i>
	                课程信息
	            </li>
	            <li><a href="#" data-target='detailInfo'>详细信息</a></li>
	            <li><a href="#" data-target='promotion'>课程推广</a></li>  
	             <li class="nav-header">
	                <i class="icon-setting"></i>
	                课程设置
	            </li>
	            <li><a href="#" data-target='accessRight'>访问权限</a></li>   
	            <li><a href="#" data-target='kinguser'>授权管理</a></li>
	            <li><a href="#" data-target='deleteCourse'>删除课程</a></li>       
	    </ul>
	  </div>
		<div class="w790 pull-right" id="rightCont">    
	   
	    </div>
	</div>
</section>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/templDetailPages.js"></script>
<script src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script src="${ctx }/resources/selectarea/jquery.imgareaselect.js" type="text/javascript"></script>
<script type="text/javascript">	
function reminder(){
	var key =  $("#addKey").val();
	if(key.length>=10){
		$("#formBasicInfo .keywordWrap>.btn-add").next().after('<label class="error" for="addKey">关键字不能超过10个字符！</label>');
		$("#addKey").val("");
	}
}
</script>
<script type="text/javascript">	
$.Placeholder.init();
	//点击左侧菜单事件
	$("#sideNav>li>a").bind("click",function(e){
		e.preventDefault();
		if ($('#upMovie').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
		     $('#upMovie').uploadify('destroy'); 
		}
		if ($('#upMaterial').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
		     $('#upMaterial').uploadify('destroy'); 
		}
		KindEditor.remove('textarea[name="courseAbstract"]');
		urlRouter($(this).attr('data-target'));		
	});
	function backDirectory(){//返回章节目录
		if ($('#upMovie').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
		     $('#upMovie').uploadify('destroy'); 
		}
		if ($('#upMaterial').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
		     $('#upMaterial').uploadify('destroy'); 
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
	  				rightCont.loadBasicInfoPage("基本信息");
	  				break;
	  			case "sectionsDirectory":
						rightCont.loadSectionDirectoryPage("章节目录");
	  				break;
	  			case "detailInfo":
	  				if($('#courseId').val()!=null &&  $('#courseId').val()!=''){
	  					rightCont.loadDetailInfoPage("详细信息");
	  				}else{
	  					jalert_tips("请先设置基本信息");
	  					$("#sideNav>li>a[data-target='basicInfo']").parent().addClass("active").siblings().removeClass("active");
	  					rightCont.loadBasicInfoPage("基本信息");
	  				}
	  				break;
	  			case "promotion":
	  				if($('#courseId').val()!=null &&  $('#courseId').val()!=''){
	  					rightCont.loadPromotionPage("课程推广");
	  				}else{
	  					jalert_tips("请先设置基本信息");
	  					$("#sideNav>li>a[data-target='basicInfo']").parent().addClass("active").siblings().removeClass("active");
	  					rightCont.loadBasicInfoPage("基本信息");
	  				}
	  				break;
	  			case "accessRight":
	  				if($('#courseId').val()!=null &&  $('#courseId').val()!=''){
	  					rightCont.loadAccessRightPage("权限设置");
	  				}else{
	  					jalert_tips("请先设置基本信息");
	  					$("#sideNav>li>a[data-target='basicInfo']").parent().addClass("active").siblings().removeClass("active");
	  					rightCont.loadBasicInfoPage("基本信息");
	  				}
	  				break;
	  			case "kinguser":
	  				if($('#courseId').val()!=null &&  $('#courseId').val()!=''){
	  					rightCont.loadKinguserPage("授权管理");
	  				}else{
	  					jalert_tips("请先设置基本信息");
	  					$("#sideNav>li>a[data-target='basicInfo']").parent().addClass("active").siblings().removeClass("active");
	  					rightCont.loadBasicInfoPage("基本信息");
	  				}
	  				break;
				case "deleteCourse":
					if($('#courseId').val()!=null &&  $('#courseId').val()!=''){
						rightCont.loadDeleteCoursePage("删除课程",$("#courseId").val());
	  				}else{
	  					jalert_tips("请先设置基本信息");
	  					$("#sideNav>li>a[data-target='basicInfo']").parent().addClass("active").siblings().removeClass("active");
	  					rightCont.loadBasicInfoPage("基本信息");
	  				}
	  				break;
                case "video":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"01");
                        break;
                    }
                case "audio":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"02");
                        break;
                    }
                case "doc":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"04");
                        break;
                    }
                case "ppt":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"05");
                        break;
                    }
                case "img":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"03");
                        break;
                    }
                case "txt":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"07");
                        break;
                    }
                case "exam":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"08");
                        break;
                    }
                case "task":
                    if(opt) {
                        rightCont.loadVideoPage(opt,"10");
                        break;
                    }
	  			default:
	  				rightCont.loadSectionDirectoryPage("章节目录");
	  			    rightCont.loadBasicInfoPage("基本信息");
	  		}
		},10);
		
		
	}
	urlRouter();
	
	//课程发布
	function releaseCourse(){
		saveCourseSigleInfo();
		 //发布前验证课程的基本信息是否已完善(防止出现未命名情况);
		window.location.href="${ctx}/course/releaseCourse?courseId="+$("#courseId").val();
	}
	
	//课程预览
	function previewCourse(){
		saveCourseSigleInfo();
		window.open("${ctx}/course/previewCourse?courseId="+$("#courseId").val(),'_blank');
	}
	function saveCourseSigleInfo(){
		if($("#formBasicInfo").length>0){
			saveBaseInfo();//基本信息
		}
		if($("#formMedia").length>0){
			$("#formMedia").trigger("submit");
		}
		if($("#formDetailInfo").length>0){
			if(!$("#formDetailInfo").valid()){
				return;
			}
			saveDetailInfo();//保存详情
		}
		if($("#formPromotion").length>0){
			saveCoursePic();//课程推广
		}
		if($("#formAccessRight").length>0){
			saveIsPublish();//访问权限
		}
		if($("#submitUser").length>0){
			$("#submitUser").trigger("click");
		}
	}
	//ajax保存课程基本信息
	function saveBaseInfo(){
		if(!$("#formBasicInfo").valid()){
			return;
		}
		$.post('${ctx}/ajax/course/saveBaseInfo',{
			 courseId : $("#courseId").val(),
			 courseTitle: $("#courseTitle").val(),
			 subTitle:  $("#subTitle").val(),
			 keyword: $("#keyword").val(),
			 courseType: $("#courseType").val(),
			 sectionOrder: $("#sectionOrder").val(),
			 fdPrice:$("#fdPrice").val(),
			 isCompulsoryCourse:$("#isCompulsoryCourse").val()
			},
			function(data){
				$("#courseId").val(data.courseid);
			},"json")
		.success(function(){
			//提交成功跳转到详细信息
       	    //urlRouter("detailInfo");
			jalert_tips("保存成功");
			$("#goTop").trigger("click");
		});
	}
	
	
		
	//ajax保存课程详细信息
	function saveIsPublish(){
		if($(':radio[name="encryptType"]:checked').val()=="passwordProtect"&&!$("#formAccessRight").valid()){
			return;
		}
		if($(':radio[name="encryptType"]:checked').val()=="authorized"){
			 $("#coursePwd").val("");
		}
		var groupIds = "";
		$("#list_group tr").each(function(i){
			groupIds=groupIds+":"+$(this).attr("data-fdid");
		 });
		$.post('${ctx}/ajax/course/updateIsPublish',{
			courseId : $("#courseId").val(),
			isPublish: $("#permission").val(),
			fdPassword:  $("#coursePwd").val(),
			groupIds: groupIds
			})
		.success(function(){
			//提交成功跳转到详细信息
       	   // urlRouter("kinguser");
			jalert_tips("保存成功");
			$("#goTop").trigger("click");
		});
	}
	//清空密码input
	function removePass(){
		$("#coursePwd").attr("class", "valid");
		$("#passRadio label").remove();
		
		$("#coursePwd").val("");
	}



//图片剪切成功
function successSelectArea(imgSrc){
    var now=new Date();
    var number = now.getSeconds();
    $(".cutimg-box").html("");
    $(".cutimg-box").addClass("hide");
    $(".imgareaselect-outer").remove();
    $(".imgareaselect-border1").remove();
    $(".imgareaselect-border2").remove();
    $("#imgshow").show().attr('src',  imgSrc+"?n="+number);
}
	
	//课程封页图片保存
    function saveCoursePic(){
    	$.post('${ctx}/ajax/course/cover',{
			courseId : $("#courseId").val(),
			attId: $("#attIdID").val(),
			courseSkin:$("#courseSkin").val(),
			coursePicture:$("#coursePicture").val()
			})
		.success(function(){
       	   // urlRouter("accessRight");
			jalert_tips("保存成功");
			$("#goTop").trigger("click");
		});
    }
 // 验证值小数位数不能超过两位  
    jQuery.validator.addMethod("decimal", function (value, element) {  
        var decimal = /^-?\d+(\.\d{1,2})?$/;  
        return this.optional(element) || (decimal.test(value));  
    }, $.validator.format("小数位数不能超过两位!"));

    function pageNavClick(pageNo){
		$.ajax({
			  url: $('#ctx').val()+"/ajax/course/getPictures?courseId="+$('#courseId').val()+"&pageNo="+pageNo,
			  async:false,
			  dataType:'json',
			  success: function(rsult){
				  var strhtm = "";
				  if(rsult.coursePictures.totalCount>0){
					  
					  strhtm += "<div class='courseSkins'>";
					  strhtm += "<label >封面图片库</label>";
					  strhtm += "<div class='btn-group'>";
					  strhtm += "<button class='btn btn-primary btn-ctrl' type='button' ";
					  if(parseInt(rsult.coursePictures.pageNo)<= 1){
						  strhtm += "disabled";
					  }else{
						  strhtm += "onclick='pageNavClick("+(parseInt(rsult.coursePictures.pageNo) - 1)+")'";
					  }
					  strhtm += "> ";
					  strhtm += " <i class='icon-chevron-left icon-white'></i> ";
					  strhtm += "</button> ";
					  strhtm += "<button class='btn btn-primary btn-ctrl' type='button' ";
					  if(parseInt(rsult.coursePictures.pageNo)>= parseInt(rsult.coursePictures.pageCount)){
						  strhtm += "disabled";
					  }else{
						  strhtm += "onclick='pageNavClick("+(parseInt(rsult.coursePictures.pageNo) + 1)+")'";
					  }
					  strhtm += "> ";
					  strhtm += " <i class='icon-chevron-right icon-white'></i> ";
					  strhtm += "</button> ";
					  strhtm += "</div> ";
					  strhtm += "<ul class='nav courseSkinList clearfix coursePicture'> ";
					  for(var i=0;i<rsult.coursePictures.pictureList.length;i++){
						  strhtm += "<li ";
						  if((rsult.coursePictures.pictureList[i]).fdId==$("#coursePicture").val()){
							  strhtm += " class='active' ";
						  }
						  strhtm += "> ";
						  strhtm += "<a href='#' data-fdid='"+(rsult.coursePictures.pictureList[i]).fdId+"' data-img='${ctx}/common/file/image/"+(rsult.coursePictures.pictureList[i]).imgUrl+"'><img src='${ctx}/common/file/image/"+(rsult.coursePictures.pictureList[i]).imgUrl+"' alt='"+(rsult.coursePictures.pictureList[i]).title+"' /><i class='icon-right'></i></a><h5>"+(rsult.coursePictures.pictureList[i]).title+"</h5></li>";
					  }
					  strhtm += "</ul> ";
					  strhtm += "</div>	 ";
				  }
				  $("#coursePicturediv").html(strhtm);
				// 选择图片库事件
					$("#formPromotion .coursePicture>li>a").bind("click",function(e){
						e.preventDefault();
						$(this).parent().addClass("active").siblings().removeClass("active");
						$("#coursePicture").val($(this).attr("data-fdid"));
						$("#attIdID").val("");
						successSelectArea($(this).attr("data-img"));
					});
			  },
		});
	}
</script>
</body>
</html>
