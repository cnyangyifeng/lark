<%@page import="cn.me.xdf.model.base.AttMain"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html class="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="${ctx}/resources/js/html5.js"></script>
<![endif]-->

<!-- 授权管理 用户列表 模板 -->
<script id="listUserKinguserTemplate" type="text/x-dot-template">
    <tr data-fdid="{{=it.id}}">
        <td class="tdTit">
          <div class="pr">
            <div class="state-dragable"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></div>
            <img src="{{?it.imgUrl.indexOf('http')>-1}}{{=it.imgUrl}}{{??}}${ctx}/{{=it.imgUrl}}{{?}}" />{{=it.name}}（{{=it.mail}}），{{=it.org}} {{=it.department}}
          <div>
         </td>
        <td><input type="checkbox" {{?it.tissuePreparation!=false}}checked{{?}} class="tissuePreparation" /></td>
        <td><input type="checkbox" {{?it.editingCourse!=false}}checked{{?}} class="editingCourse" /></td>
        <td><a href="#" class="icon-remove-blue"></a></td>
    </tr>
</script>


    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>

<section class="container">
	<section class="clearfix mt20">
	  <section class="col-left pull-left">
    	<%@ include file="/WEB-INF/views/group/menu.jsp" %>
	  </section>
		<section class="w790 pull-right" id="rightCont">
	        <div class="page-header">
                <a href="${ctx}/material/findList?fdType=${param.fdType}&order=FDCREATETIME" class="backParent">
                <span id="back"></span>
               </a>
                <h4><tags:title value="${materialInfo.fdName}" size="16" ></tags:title></h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" type="button" onclick="saveMaterial();">保存</button>
                    <c:if test="${materialInfo.fdId!=null}">
                     <button class="btn btn-large btn-primary" type="button" onclick="downloadMater();">下载</button>
                     <button class="btn btn-white btn-large " type="button" onclick="confirmDel();">删除</button>
                    </c:if> 
               </div>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" method="post">
                    <section class="section">
                        <div class="control-group">
                            <label class="control-label" for="videoName" id="typeTxt"></label>
                            <div class="controls">
                                <input value="${materialInfo.fdName}" 
                                    id="videoName" required class="span6" name="videoName" type="text">
                                <span class="date">
                                <fmt:formatDate value="${materialInfo.fdCreateTime}" pattern="yyyy/MM/dd hh:mm aa"/>
                                </span>
                                <input type="hidden" id="fdId" value="${materialInfo.fdId}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >统&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</label>
                            <div class="controls controls-txt">
                                <span class="rating-view">
                                    <span class="rating-all">
                                       <c:if test="${score.fdAverage!=null}">
                        <c:forEach var="i" begin="1" end="5">
					  	<c:if test="${i<=score.fdAverage}">
					  	<i class="icon-star active"></i>
					  	</c:if>
					  	<c:if test="${i>score.fdAverage}">
					  	<i class="icon-star"></i>
					  	</c:if>
					  </c:forEach>                                         
                                       </c:if>
                                       <c:if test="${score.fdAverage==null}">
                                         <c:forEach var="i" begin="1" end="5">
					   					   <i class="icon-star"></i>
					  				     </c:forEach>
                                       </c:if>
                                       
                                     </span>
                                      <b class="text-warning">
                                       <c:if test="${score.fdAverage==null}">
                                          0.0
                                       </c:if>
                                       <c:if test="${score.fdAverage!=null}">
                                          ${score.fdAverage}
                                       </c:if>
                                      </b>
                                </span>
                                <span class="btns-handle">
                                <b>|</b>
                                    <button type="button" class="btn btn-link"><i class="icon-eye"></i>
                                       <c:if test="${materialInfo.fdPlays==null}">
                                          0
                                       </c:if>
                                       <c:if test="${materialInfo.fdPlays!=null}">
                                          ${materialInfo.fdPlays}
                                       </c:if>
                                     </button><b>|</b>
                                    <button type="button" class="btn btn-link"><i class="icon-thumbs-up"></i>
                                      <c:if test="${materialInfo.fdLauds==null}">
                                          0
                                       </c:if>
                                       <c:if test="${materialInfo.fdLauds!=null}">
                                          ${materialInfo.fdLauds}
                                       </c:if>
                                    </button><b>|</b>
                                    <button type="button" class="btn btn-link"><i class="icon-download"></i>0</button>
                                </span>
                            </div>
                        </div>
                        <div class="control-group" id="videoText">
                            <label class="control-label" for="videoUrl">播放地址</label>
                            <div class="controls">
							<j:ifelse test="${materialInfo.fdLink != null && materialInfo.fdLink != '' }">
								<j:then>
									<input value="${materialInfo.fdLink}" placeholder="此处为系统自动生成的视频播放地址，请勿手动填写。"
                                       id="videoUrl" class="input-block-level" name="fdLink" type="text">
								</j:then>
								<j:else>
									<input value="${main.fileUrl}" placeholder="此处为系统自动生成的视频播放地址，请勿手动填写。"
                                       id="videoUrl" class="input-block-level" name="fdLink" type="text">
								</j:else>
							</j:ifelse>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="videoIntro" id="materialIntro"></label>
                            <div class="controls">
                              <textarea rows="4"
                                        class="input-block-level" id="videoIntro"
                                        name="fdDescription">${materialInfo.fdDescription}</textarea>
                            </div>
                        </div>
                        
                        <div class="control-group">
                           <label class="control-label">允许下载</label>
                           <div class="controls">
                             <div class="btn-group btns-radio" data-toggle="buttons-radio">
                              <c:if test="${materialInfo.isDownload==true}">
                               <button class="btn btn-large active" id="yes" type="button">是</button>
                               <button class="btn btn-large" id="no" type="button">否</button>
                               <input type="hidden" value="yes" id="isDownload">
                              </c:if> 
                               <c:if test="${materialInfo.isDownload!=true}">
                               <button class="btn btn-large" id="yes" type="button">是</button>
                               <button class="btn btn-large active" id="no" type="button">否</button>
                               <input type="hidden" value="no" id="isDownload">
                              </c:if> 
                             </div>
                           </div>
                        </div>
                        
                    </section>
                    <section class="section mt20">
                        <label id="uploadIntro"></label>
                        <div class="control-upload">
                          <span class="progress"> 
                             <div class="bar" style="width:0;"></div> 
                          </span>
				    	  <span class="txt">
				    	      <span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span>
				    	  </span>
                     
						  <button id="upMaterial"class="btn btn-primary btn-large" type="button" >上传</button>
						</div>
						
						<ul class="unstyled list-attachment" id="listAttachment">
						  <c:if test="${main != null}">
							<li data-fdid="${main.fdId}" >
								<a class="name" style="padding-left:20px;"><i class="icon-paperClip"></i>
								&nbsp;${main.fdFileName}</a>
								<input type="hidden"  name="attId" id="attId" value="${main.fdId}">
								<div class="item-ctrl">
									<a class="icon-remove-blue" href="#"></a>
								</div>
							</li>
							</c:if>
						 </ul>
						
                    </section>
                    
                    <section class="section mt20">
                        	
                        <c:if test="${materialInfo.fdLink==null||materialInfo.fdLink==''}">
								<c:choose>
									<c:when test="${main.fileUrl!=null&&main.fileUrl!=''&&main.fdFileType=='01'}">
										<script type="text/javascript"
											src="${main.fileUrl}&width=750&height=422"></script>
									</c:when>
									<c:when
										test="${main.fileUrl!=null&&main.fileUrl!=''&&(main.fdFileType=='04'||main.fdFileType=='05')}">
										<iframe width="100%" height="422" id="iframe_ppt"
											src="${main.fileUrl}" frameBorder="0" scrolling="no"></iframe>
									</c:when>
									<c:otherwise>
										<div class="media-placeholder">正在转化中......</div>
									</c:otherwise>
								</c:choose>
						</c:if>
						<c:if test="${materialInfo.fdLink!=null&&materialInfo.fdLink!=''}">
									<c:if test="${materialInfo.fdType=='01'}">
										<script type="text/javascript"
											src="${materialInfo.fdLink}&width=750&height=422"></script>
									</c:if>
									<c:if test="${materialInfo.fdType=='04'||materialInfo.fdType=='05'}">
										<iframe width="100%" height="422" id="iframe_ppt"
											src="${materialInfo.fdLink}" frameBorder="0" scrolling="no"></iframe>
								    </c:if>
						</c:if>
	                               
								
                    </section>
                    <section class="section">
                        <div class="control-group">
                            <label class="control-label" for="author">作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者</label>
                            <div class="controls">
                             <c:if test="${materialInfo==null||materialInfo==''}">
                               <input value="${person.fdName}" id="author" required class="input-block-level"
                                       name="fdAuthor" type="text">
                             </c:if>
                             <c:if test="${materialInfo!=null&&materialInfo!=''}">
                             <input value="${materialInfo.fdAuthor}" id="author" required class="input-block-level"
                                       name="fdAuthor" type="text"> 
                             </c:if>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="authorIntro">作者简介</label>
                            <div class="controls">
                            <c:if test="${materialInfo==null||materialInfo==''}">
                               <textarea  rows="4"
                                       class="input-block-level" id="authorIntro"
                                       name="fdAuthorDescription" >${person.selfIntroduction}</textarea>
                             </c:if>
                             <c:if test="${materialInfo!=null&&materialInfo!=''}">
                             <textarea  rows="4"
                                       class="input-block-level" id="authorIntro"
                                       name="fdAuthorDescription" >${materialInfo.fdAuthorDescription}</textarea>
                             </c:if>
                            </div>
                        </div>
                    </section>
                    <div class="page-header mt20"> <h4>权限设置</h4> </div>
                    <section class="section">
                        <label>权限设置</label>
                        <ul class="nav nav-pills">
                        <c:if test="${materialInfo.isPublish==null}">
                            <li><a data-toggle="tab" href="#open">公开</a></li>
                            <li class="active"><a data-toggle="tab" href="#encrypt">加密</a></li>
                            <input type="hidden" id="permission" name="permission" value="encrypt">
                         </c:if>
                        <c:if test="${materialInfo.isPublish==true}">
                            <li class="active"><a data-toggle="tab" href="#open">公开</a></li>
                            <li><a data-toggle="tab" href="#encrypt">加密</a></li>
                            <input type="hidden" id="permission" name="permission" value="open">
                         </c:if>
                         <c:if test="${materialInfo.isPublish==false}">
                            <li><a data-toggle="tab" href="#open">公开</a></li>
                            <li class="active"><a data-toggle="tab" href="#encrypt">加密</a></li>
                            <input type="hidden" id="permission" name="permission" value="encrypt">
                         </c:if>
                        </ul>
                        <div class="tab-content">
                          <c:if test="${materialInfo.isPublish==true}">
                             <div class="tab-pane active" id="open">
                                	提示：“公开”素材将允许所有主管在管理课程的过程中使用，而“加密”素材将允许您手动授权某些主管使用本课程素材。
                             </div>
                             <div class="tab-pane" id="encrypt">
                                <table class="table table-bordered">
                                    <thead><tr><th>授权用户</th><th>可用</th>
                                     <th>编辑</th><th>删除</th></tr></thead>
                                    <tbody id="list_user"></tbody>
                                </table>
                                <div class="pr">
                                    <input type="text" id="addUser" class="autoComplete input-block-level" placeholder="请输入人名、邮箱、机构或者部门查找用户">
                                    
                                </div>
                            </div>
                          </c:if>
                          <c:if test="${materialInfo.isPublish==false}">
                             <div class="tab-pane" id="open">
                                	提示：“公开”素材将允许所有主管在管理课程的过程中使用，而“加密”素材将允许您手动授权某些主管使用本课程素材。
                             </div>
                             <div class="tab-pane active" id="encrypt">
                                <table class="table table-bordered">
                                    <thead><tr><th>授权用户</th><th>可使用</th>
                                     <th>可编辑</th><th>删除</th></tr></thead>
                                    <tbody id="list_user"></tbody>
                                </table>
                                <div class="pr">
                                    <input type="text" id="addUser" class="autoComplete input-block-level" placeholder="请输入人名、邮箱、机构或者部门查找用户">
                                    
                                </div>
                            </div>
                          </c:if>
                            <c:if test="${materialInfo.isPublish==null}">
                             <div class="tab-pane" id="open">
                                	提示：“公开”素材将允许所有主管在管理课程的过程中使用，而“加密”素材将允许您手动授权某些主管使用本课程素材。
                             </div>
                             <div class="tab-pane active" id="encrypt">
                                <table class="table table-bordered">
                                    <thead><tr><th>授权用户</th><th>可使用</th>
                                     <th>可编辑</th><th>删除</th></tr></thead>
                                    <tbody id="list_user"></tbody>
                                </table>
                                <div class="pr">
                                    <input type="text" id="addUser" class="autoComplete input-block-level" placeholder="请输入人名、邮箱、机构或者部门查找用户">
                                    
                                </div>
                            </div>
                          </c:if>
                        </div>
                    </section>
                    <button class="btn btn-block btn-submit btn-inverse" type="button" onClick="saveMaterial()">保存</button>
                </form>
            </div>
	    </section>
	</section>
</section>
<input type="hidden" id="fdType" value="${param.fdType}">
<input type="hidden" id="fdattId" value="${main.fdId}">
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
function confirmDel(){
	jalert("您确认要删除该素材吗？",deleteMaterial);
}
function deleteMaterial(){
	 $.ajax({
		type: "post",
		url: "${ctx}/ajax/material/deleteMaterial",
		data : {
			"materialId":$("#fdId").val()
		},
		success:function(){
			window.location.href="${ctx}/material/findList?order=FDCREATETIME&fdType="+$("#fdType").val();
		}
	}); 
}
//下载素材
function downloadMater(){
  var attId = $("#fdattId").val();
  var main = '${main.fileNetId}';
  if(attId!=null&&attId!="" && main!=null&&main!=""){
	  window.location.href="${ctx}/common/file/download/"+attId;
  } else {
	  jalert("您好！该素材没有对应附件");
  } 
}
</script>
<script type="text/javascript">
$(function(){
	var data_uploadIntro;
	var uptype;
	switch($("#fdType").val()){
  	case "01":
  		$("#materialIntro").html("视频简介");
  		$("#back").html("返回视频列表");
  		$("#typeTxt").html("视&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;频");
  		data_uploadIntro = "上传视频（支持绝大多数主流视频格式，建议小于10G）：成功上传的视频将会显示在下面视频列表中。";
  		$("#uploadIntro").html(data_uploadIntro);
  		uptype='*.wmv;*.wm;*.asf;*.asx;*.rm;*.rmvb;*.ra;*.ram;*.mpg;*.mpeg;*.mpe;*.vob;*.dat;*.mov;*.3gp;*.mp4;*.mp4v;*.m4v;*.mkv;*.avi;*.flv;*.f4v;*.mts;';
  		break;
    case "02":
    	$("#materialIntro").html("音频简介");
    	$("#back").html("返回音频列表");
    	$("#typeTxt").html("音&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;频");
    	data_uploadIntro = "上传音频（支持MP3、MV格式的音频，建议小于10G）：成功上传的音频将会显示在下面的音频列表中。";
    	$("#uploadIntro").html(data_uploadIntro);
    	uptype='*.mp3;*.mv;';
        break;
    case "04":
    	$("#materialIntro").html("文档简介");
    	$("#videoText").remove();
    	$("#back").html("返回文档列表");
    	$("#typeTxt").html("文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;档");
    	data_uploadIntro = "上传文档（支持DOC、EXCEL、PDF、PPT格式的文档，建议小于10G）：成功上传的文档将会显示在下面的文档列表中。";
    	$("#uploadIntro").html(data_uploadIntro);
    	uptype='*.doc;*.docx;*.xls;*.xlsx;*.pdf;*.ppt;*.pptx;*.pps;*.ppsx;';
        break;
    case "05":
    	$("#materialIntro").html("幻灯片简介");
    	$("#back").html("返回幻灯片列表");
    	$("#typeTxt").html("幻&nbsp;&nbsp;灯&nbsp;&nbsp;片");
    	data_uploadIntro = "上传幻灯片（建议小于10G）：成功上传的幻灯片将会显示在下面的幻灯片列表中。";
    	$("#uploadIntro").html(data_uploadIntro);
    	uptype='*.ppt;*.pptx;*.pps;*.ppsx;';
    	$("#videoText").remove();
        break;
  }
	$("#listAttachment").find("a.icon-remove-blue").bind("click",function(e){
		e.preventDefault();
		$(this).closest("li").remove();
		if($("#videoUrl").val()!=null && $("#videoUrl").val()!=""){
			  $("#videoUrl").val("");//清空视频链接
		}
	});
	
	var $txt = $("#upMaterial").prev(".txt"), 
    $progress = $txt.prev(".progress").children(".bar"),
    $pct = $txt.children(".pct"),
    $countdown = $txt.children(".countdown"),
    intervalSpeed={},isResponse = false,countdown = 0,byteUped = 0;

	$("#upMaterial").uploadify({
    'height' : 40,
    'width' : 68,
    'multi' : false,
    'simUploadLimit' : 1,
    'swf' : '${ctx}/resources/uploadify/uploadify.swf',
    "buttonClass": "btn btn-primary btn-large",
    'buttonText' : '上传',
    'uploader' : '${ctx}/common/file/o_upload',
    'auto' : true,
    'fileTypeExts' : uptype,
    'onInit' : function(){
    	$("#upMaterial").next(".uploadify-queue").remove();
    },
    'onUploadStart' : function (file) {},
    'onUploadSuccess' : function (file, data, Response) {
        if (Response) {
        	isResponse = true;
        		$countdown.text("00:00:00");
            	$progress.width("0");
            	$pct.text("0%");
                var objvalue = eval("(" + data + ")");
    		    var html="<li data-fdid='"+objvalue.attId+"' '><a class='name' style='padding-left:20px;'><i class='icon-paperClip'></i>&nbsp;"+file.name+" "
    		          +"</a><input type='hidden'  name='attId' id='attId' value='"+objvalue.attId+"'><div class='item-ctrl'> "
    		          +"<a class='icon-remove-blue' href='#'></a> </div></li>";
                $("#listAttachment").html(html);
                $("#listAttachment").find("a.icon-remove-blue").bind("click",function(e){
    				e.preventDefault();
    				$(this).closest("li").remove();
    			});
                if($("#videoUrl").val()!=null && $("#videoUrl").val()!=""){
                	$("#videoUrl").val("");//清空视频链接
                }
        } 
    },
    'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
    	byteUped = bytesUploaded;
    	if(bytesUploaded == 0){
    		var pct="0%";
    		countdown = 0;
    		intervalSpeed = setInterval(uploadSpeed,100);
    		isResponse = false;
    	}
    	if(bytesUploaded == bytesTotal){
    		clearInterval(intervalSpeed);
    		$.fn.jalert({
   	    		content: function(modalBody){
   	    			var timeOutModal = modalBody.html('<p align="center"><i class="icon-loading"></i></p>\
   	     	    		<p align="center">服务器正在存储文件，请耐心等候...</p>')
   	     	    		.parent(".modal");
   	    			var itl = setInterval(function(){
   	    				if(isResponse && timeOutModal.hasClass("in")){ 
   	    					clearInterval(itl);
	            			setTimeout(function(){
	            				timeOutModal.modal("hide");
	            				console.log("hide,"+new Date());
	   	            		},1000);
	            		} 
	            	},500);
   	    		},
   	    		tip: true,
   	    		tipTime: false
    		});    		
    	}
    	pct = Math.round((bytesUploaded/bytesTotal)*100)+'%';
    	$progress.width(pct);
    	$pct.text(pct);
    	countdown>0 && $countdown.text(secTransform((bytesTotal-bytesUploaded)/countdown*10));
    }
  });
	function uploadSpeed(){
		countdown = byteUped - countdown;
	}
	function secTransform(s){
		if( typeof s == "number"){
			s = Math.ceil(s);
			var t = "";
			if(s>3600){
				t= completeZero(Math.ceil(s/3600)) + ":" + completeZero(Math.ceil(s%3600/60)) + ":" + completeZero(s%3600%60) ;
			} else if(s>60){
				t= "00:" + completeZero(Math.ceil(s/60)) + ":" + completeZero(s%60) ;
			} else {
				t= "00:00:" + completeZero(s);
			}
			return t;
		}else{
			return null;
		}		
	}
	function completeZero(n){
		return n<10 ? "0"+n : n;
	}
});

</script>
<script type="text/javascript">
$(function(){
    
    //授权管理 用户列表 模板函数
    var listUserKinguserFn = doT.template(document.getElementById("listUserKinguserTemplate").text);
    //初始化创建者
    var creator="";
    var creatorId="";
    var url="";
	   $.ajax({
		 cache:false,
		 url: "${ctx}/ajax/material/getCreater?materialId=${materialInfo.fdId}",
		 async:false,
		 dataType : 'json',
		 success: function(result){
		    creator = result.name+"（"+result.email+"），"+result.dept;
				  url=result.url;
			creatorId = result.fdId;
		 }
	   });
	 //初始化权限列表
   $.ajax({
		  url: "${ctx}/ajax/material/getAuthInfoByMaterId?MaterialId=${materialInfo.fdId}",
		  async:false,
		  cache:false,
		  dataType : 'json',
		  success: function(result){
			  var photo;
				if(url.indexOf("http")>-1){
					photo=url;
				}else{
					photo="${ctx}/"+url;
				}
			  var html = "<tr data-fdid='"+creatorId+"' draggable='true'> "+
			  " <td class='tdTit'> <div class='pr'> <div class='state-dragable'><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></div> "+
			  "<img src='"+photo+"' alt=''>"+creator+" </div> </td>"+
			  " <td><input type='checkbox' onclick='return false' checked='' class='tissuePreparation'></td> <td>"+
			  "<input type='checkbox' onclick='return false' checked='' class='editingCourse'></td> <td></a>"+
			  "</td> </tr>";
			  for(var i in result.user){
				  html += listUserKinguserFn(result.user[i]);
			  }
			  $("#list_user").html(html); 
		  }
	  });
    
	if('${materialInfo.fdId}'==''){
		if($("#fdType").val()=='01'){
	  		$("#videoIntro").attr("placeholder","视频作者很懒，没有写视频简介。");
		}
		if($("#fdType").val()=='02'){
			$("#videoIntro").attr("placeholder","音频作者很懒，没有写音频简介。");
		}
		if($("#fdType").val()=='04'){
	    	$("#videoIntro").attr("placeholder","文档作者很懒，没有写文档简介。");
	    }
	    if($("#fdType").val()=='05'){
	    	$("#videoIntro").attr("placeholder","幻灯片作者很懒，没有写幻灯片简介。");
	    }
	}
    
    $('#formEditDTotal a[data-toggle="tab"]').on('shown', function (e) {
        var href = 	e.target.href.split("#").pop();
        $("#permission").val(href);
    });
    
    //附件是否可下载
    $('[data-toggle="buttons-radio"]>.btn').click(function(){
        if(this.id == "yes"){
        	$("#isDownload").val(this.id);
        }
        if(this.id == "no"){
        	$("#isDownload").val(this.id);
        }
    });
    
    
    $("#list_user").sortable({
        handle: '.state-dragable'
    })
            .find("a.icon-remove-blue").bind("click",function(e){
                e.preventDefault();
                $(this).closest("tr").remove();
            });
    var allUserData ;

    $("#addUser").autocomplete("${ctx}/ajax/user/findByName",{
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
		width:748
    }).result(function(e,item){
		var flag = true;
		$("#addUser").next(".error").remove();
		$("#list_user>tr").each(function(){
			if($(this).attr("data-fdid")==item.id){
				$("#addUser").after('<label class="error" >不能添加重复的用户!');
				$("#addUser").val("");
				flag = false;
			}
		});
		if(flag){
			$(this).val(item.name);
			$("#list_user").append(listUserKinguserFn(item))
			.sortable({
				handle: '.state-dragable',
				forcePlaceholderSize: true
			})
			.find("a.icon-remove-blue").bind("click",function(e){
				e.preventDefault();
				$(this).closest("tr").remove();
			});
			$("#addUser").val("");
		}
	});
    $.Placeholder.init();
});
function saveMaterial(){
	if(!$("#formEditDTotal").valid()){
		return;
	}
	if($("#videoIntro").val()==""){
		if($("#fdType").val()=='01'){
	  		$("#videoIntro").val("视频作者很懒，没有写视频简介。");
		}
		if($("#fdType").val()=='02'){
			$("#videoIntro").val("音频作者很懒，没有写音频简介。");
		}
		if($("#fdType").val()=='04'){
	    	$("#videoIntro").val("文档作者很懒，没有写文档简介。");
	    }
	    if($("#fdType").val()=='05'){
	    	$("#videoIntro").val("幻灯片作者很懒，没有写幻灯片简介。");
	    }
	} 
	var vUrl = "";
	if($("#videoUrl").val()!=$("#videoUrl").attr("placeholder")){
		vUrl = $("#videoUrl").val();
	}
	
    var data = {
        videoName: $("#videoName").val(),
        fdId: $("#fdId").val(),
        videoUrl: vUrl,
        videoIntro: $("#videoIntro").val(),
        author: $("#author").val(),
        authorIntro: $("#authorIntro").val(),
        permission:$("#permission").val(),
        fdType:$("#fdType").val(),
        attId:$("#attId").val(),
        isDownload:$("#isDownload").val(),
        kingUser: null
    };
    if(data.permission === "encrypt"){
        //push人员授权数据
        data.kingUser = [];
        $("#list_user>tr").each(function(){
            data.kingUser.push({
                id: $(this).attr("data-fdid"),
                index: $(this).index(),
                tissuePreparation: $(this).find(".tissuePreparation").is(":checked"),
                editingCourse: $(this).find(".editingCourse").is(":checked")
            });
        });
        data.kingUser = JSON.stringify(data.kingUser);
    }
  ///保存作业包素材的方法
    $.ajax({
    	  type:"post",
		  url:"${ctx}/ajax/material/saveOrUpdateVideo",
		  async:false,
		  data:data,
		  dataType:'json',
		  success: function(rsult){
			  window.location.href="${ctx}/material/findList?order=FDCREATETIME&fdType="+$("#fdType").val();
		  }
	});
}
</script>
</body>
</html>
