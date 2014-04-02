<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<link href="${ctx }/resources/selectarea/imgareaselect.css?id=232" rel="stylesheet" type="text/css" />
<script src="${ctx }/resources/selectarea/jquery.imgareaselect.js" type="text/javascript"></script>
<style type="text/css">
body{padding-top:60px;}
</style>
</head>

<body>
       <div class="page-body box-control" id="pageBody">  
          <form method="post" id="inputForm" action="${ctx}/register/updateTeacherPoto" class="reg_form form-horizontal">
          <input type="hidden" id="fdIcoUrl" name="fdIcoUrl" value="${fdIcoUrl}" />
          <input type="hidden"  name="attId" id="attIdID">
        	<div class="cutFaceWrap">
        		<!-- <p class="reg_form-intro">请确认您要修改的图像。</p> -->
        		<!--图片剪切-->
                <div id="cutimg-box" class="cutimg-box hide" >
                <!-- 
                <iframe id="iframeimg" width="100%" height="500" frameborder="0" scrolling="no"
                            src=""></iframe>
                 -->
                    
                </div>
				<!--图片预览-->
				<div class="facePreview" id="facePreview2">
					<div class="imgshow img180">
						<tags:image href="${fdIcoUrl}" clas=""></tags:image>
					</div>
					<div class="imgshow img70">
						<tags:image href="${fdIcoUrl}" clas=""></tags:image>
					</div>
					<div class="imgshow img45">
						<tags:image href="${fdIcoUrl}" clas=""></tags:image>
					</div>
					<div class="imgshow img36">
						<tags:image href="${fdIcoUrl}" clas=""></tags:image>
					</div>
					<span>头像缩略图效果</span>
				</div>			
        	
            	<div align="center">
                	<button type="submit" disabled id="submit" class="submit btn btn-primary btn-large" >确认修改</button>
                </div>
            </div> 
            
        </form>
        </div>
        <div class="section pd20 mt20"> 
        		<label>上传图片（支持JPG\JPEG、PNG格式的图片，建议小于2M）</label>
        		<div class="control-upload">
                         <span class="progress"> <div class="bar" style="width:0;"></div> </span>
					     <span class="txt"><span class="pct">0%</span>，剩余时间：<span class="countdown">00:00:00</span></span>
						     <button id="upFace" class="btn btn-primary btn-large" type="button" >上传</button>
							
					</div>	
        </div>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=12"></script>
<script type="text/javascript">
$(function(){
	/* 头像上传 */
	var $txt = $("#upFace").prev(".txt"), 
    $progress = $txt.prev(".progress").children(".bar"),
    $pct = $txt.children(".pct"),
    $countdown = $txt.children(".countdown"),
	flag = true,
	pct,interval,countdown = 0,byteUped = 0;

	jQuery("#upFace").uploadify({
		        'height' : 40,
                'width' : 68,
                'multi' : false,
                'simUploadLimit' : 1,
                'swf' : '${ctx}/resources/uploadify/uploadify.swf',
                "buttonClass": "btn btn-primary btn-large",
                'buttonText' : '上 传',
                'uploader' : '${ctx}/common/file/o_upload',
                'auto' : true,// 选中后自动上传文件
                'fileTypeExts' : '*.jpg;*.png;',
                'fileSizeLimit':2048,// 限制文件大小为2m
                'onInit' : function(){
                	$("#upFace").next(".uploadify-queue").remove();
                },
                'onUploadStart' : function (file) {},
                'onUploadSuccess' : function (file, data, Response) {
                    if (Response) {
                    	$countdown.text("00:00:00");
                    	$progress.width("0");
                    	$pct.text("0%");
                        var objvalue = eval("(" + data + ")");
                        $("#attIdID").val(objvalue.attId);
                        $("#facePreview2").hide();
                        $("#cutimg-box").removeClass("hide");
                        $("#submit").removeAttr("disabled");
                        var preImg = '${ctx}/common/file/image/' + objvalue.attId+'?n='+Math.random()*100;
                        var imgSrc = escape(preImg);
                        $.get("${ctx}/common/imageCut/page?imgSrcPath="+imgSrc+"&zoomWidth=180&zoomHeight=180&imgId="+objvalue.attId,function(data){
                        	$("#cutimg-box").html(data);
                        });
                        //$("#iframeimg").attr("src","${ctx}/common/imageCut/page?imgSrcPath="+imgSrc+"&zoomWidth=180&zoomHeight=180&imgId="+objvalue.attId);
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
});

   /*  function callBackMethod(attId,fileName){
        $("#potoImg").hide();
        $("#upPic").next(".uploadify-queue").remove();
        $(".cutimg-box").show();
        var preImg = '${ctx}/common/file/image/' + attId;
        var imgSrc = escape(preImg);
        $("#iframeimg").attr("src","${ctx}/common/imageCut/page?imgSrcPath="+imgSrc+"&zoomWidth=180&zoomHeight=180&imgId="+attId);
    } */

    //图片剪切成功
    function successSelectArea(imgSrc){
	    var now=new Date();
	    var number = now.getSeconds();
	    $("#cutimg-box").html("");
	    $("#cutimg-box").addClass("hide");
	    $(".imgareaselect-outer").remove();
	    $(".imgareaselect-border1").remove();
	    $(".imgareaselect-border2").remove();
	    $("#facePreview2").show().find(".imgshow>img").attr('src', imgSrc+"?n="+number);
	}
    
</script>
</body>
</html>
