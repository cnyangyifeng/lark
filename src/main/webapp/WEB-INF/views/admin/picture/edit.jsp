<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>

<%-- <link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/template_detail.css" rel="stylesheet" type="text/css">
 --%>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/kindeditor/themes/default/default.css" />


</head>
<body>
    <div class="page-header">
    			<a href="${ctx}/admin/picture/list" class="backParent">
                <span id="back">返回列表</span>
               </a>
                <h4>添加设置</h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" type="button" onclick="saveMater();">保存</button>
               </div>
    </div>
	<div class="page-body editingBody">
	 		
		<form class="form-horizontal" method="post" id="inputForm"
			action="${ctx}/admin/picture/save" name="form">
			<input type="hidden" id="fdId" name="fdId" value="${bean.fdId}">
			<section id="set-exam" class="section">
				   <div class="section">
					<label for="SchoolCover">图片</label> 
					<!--图片剪切-->
					<div class="cutimg-box no" style="display: none;">
						<iframe id="iframeimg" width="100%" height="300" id="win"
							name="win" frameborder="0" scrolling="no" src=""></iframe>
					</div>
					<!--图片预览-->
					<div class="courseCover">
						<c:if test="${attId eq ''}">
						<img id="imgshow" name="imgshow"  src="${ctx}/resources/images/default-cover.png"alt="" />
						</c:if>
						<c:if test="${attId ne ''}">
						<img id="imgshow" name="imgshow"  src="${ctx}/common/file/image/${attId}" alt="" />
						</c:if>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"> 
					名称
					</label>
					<div class="controls controls-txt">
						<input value="${bean.fdName}" required="required"  class="input-block-level" id="fdName" name="fdName" type="text" style="color: rgb(169, 169, 169);">
					</div>
				</div>
			</section>
				<div class="section mt20">
						<label>上传图片（支持JPG\JPEG、PNG、BMP格式的图片，建议小于2M）</label>
						<div class="control-upload">
                         <span class="progress"> <div class="bar" style="width:0;"></div> </span>
					     <span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
						     <button id="upPagePic" class="btn btn-primary btn-large" type="button" >上传</button>
							<input type="hidden"  name="attIdID" id="attIdID">
					</div>	
				</div>
			<button class="btn btn-block btn-submit btn-inverse" type="submit">保存</button>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script src="${ctx }/resources/selectarea/jquery.imgareaselect.js" type="text/javascript"></script>
<script type="text/javascript">
 	jQuery(document).ready(function() {
 		 $("#inputForm").validate({
             submitHandler: function(form){
        		if("${attId}"=="" && ($("#attIdID").val()==null || $("#attIdID").val()=="")){
        			jalert("请上传图片");
        		}else{
        			form.submit();
        		}
             }
 		 })

		
	}); 
	$(function() {
		var $progress, flag = true, pct, interval, countdown = 0, byteUped = 0;
		if ($("#upPagePic")[0]){
			 var $txt = $("#upPagePic").prev(".txt"), 
			 $progress = $txt.prev(".progress").children(".bar"),
			 $pct = $txt.children(".pct"),
             $countdown = $txt.children(".countdown"),
             flag = true,
         	 pct,
         	 interval,
         	 countdown = 0,
         	 byteUped = 0;
             jQuery("#upPagePic").uploadify({
             	'height' : 40,
                 'width' : 68,
                 'multi' : false,
                 'simUploadLimit' : 1,
                 'swf' : '${ctx}/resources/uploadify/uploadify.swf',
                 "buttonClass": "btn btn-primary btn-large",
                 'buttonText' : '上传',
                 'uploader' :'${ctx}/common/file/o_upload',
                 'auto' : true,
                 'fileTypeExts' : '*.jpg;*.png;*.bmp;*.jpeg;',
                 'onInit' : function(){
                 	$("#upMaterial").next(".uploadify-queue").remove();
                 },
                 'onUploadStart' : function (file) {},
                 'onUploadSuccess' : function (file, data, Response) {
                     if (Response) {
                     	$countdown.text("00:00:00");
                     	$progress.width("0");
                     	$pct.text("0%");
                         var objvalue = eval("(" + data + ")");
                         jQuery("#attIdID").val(objvalue.attId);
                         jQuery("#upPagePic").val(objvalue.attId);
                         $("#imgshow").hide();
                         $(".cutimg-box").show();
                         var preImg = '${ctx}/common/file/image/' + objvalue.attId+'?n='+Math.random()*100;
                         var imgSrc = escape(preImg);
                         $.get("${ctx}/common/imageCut/page?imgSrcPath="+imgSrc+"&zoomWidth=584&zoomHeight=258&imgId="+objvalue.attId,function(data){
                         	$(".cutimg-box").html(data);
                         });
                         //$("#iframeimg").attr("src","${ctx}/common/imageCut/page?imgSrcPath="+imgSrc+"&zoomWidth=164&zoomHeight=161&imgId="+objvalue.attId);
                       
                     }
                    
                 },
                 'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                 	 pct = Math.round((bytesUploaded/bytesTotal)*100)+'%';
                 	byteUped = bytesUploaded;
                 	if(flag){
                 		interval = setInterval(uploadSpeed,100);
                 		flag = false;
                 	}
                 	if(bytesUploaded == bytesTotal){
                 		clearInterval(interval);
                 	}
                 	
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
		  }
	});
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
	//保存
	function saveMater(){
		$("#inputForm").trigger("submit");
	} 
</script>
</body>
</html>