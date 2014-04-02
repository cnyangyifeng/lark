<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx }/resources/selectarea/imgareaselect.css?id=232" rel="stylesheet" type="text/css" />
<div class="main">
	<div id="big-imgDiv" class="big-imgDiv">
		<img id="ferret" src="${imgSrcPath}" border="0" title="image select" alt="loading IMG ......" onload="cutImg('${imgSrcPath}','${zoomWidth}','${zoomHeight}','${imgId}')"/>
	</div>
	<h1>鼠标拖拽，合适后双击，完成封面剪切</h1>
	<c:choose>
		<c:when test="${zoomWidth == zoomHeight }">
			<div class="facePreview" id="facePreview">
				<div class="imgshow img180">
					<img src="${imgSrcPath}" alt="" />
				</div>
				<div class="imgshow img70">
					<img src="${imgSrcPath}" alt="" />
				</div>
				<div class="imgshow img45">
					<img src="${imgSrcPath}" alt="" />
				</div>
				<div class="imgshow img36">
					<img src="${imgSrcPath}" alt="" />
				</div>
				<span>头像缩略图效果</span>
			</div>
		</c:when>
		<c:otherwise>
			<div class="previewImg">
				<div id="imgDiv" class="min-imgDiv" >
					<img id="minImg" src="${imgSrcPath}" border="0"/>
				</div>
				<h1>课程缩略图效果</h1>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript">
function cutImg(imgSrcPath,zoomWidth,zoomHeight,imgId){
	var imgDivW= Math.round($("#big-imgDiv").width());
	var imageW = 100;
	var imageH = 100;
	var cutImageW = 0;
	var cutImageH = 0;
	var cutImageX = 0;
	var cutImageY = 0;
	var minWidth = zoomWidth;
	var minHeight = zoomHeight;
	var scale = 1;
	var uploadImgStatus = 0;
	function submitCut(){
		if(uploadImgStatus==0){
			$.ajax({
				url : "${ctx }/common/imageCut/imageCut",
				type : "POST",
				dataType : "json",
				data : {
					imgSrcPath : imgSrcPath,
					imgWidth: cutImageW,
					imgHeight : cutImageH,
					imgTop : cutImageX,
					imgLeft : cutImageY,
					imgScale : scale,
					reMinWidth: minWidth,
					reMinHeight: minHeight,
                    imgId: imgId
				},
				success : function(result) {
					if(result){
						uploadImgStatus++;
						successSelectArea(imgSrcPath);
					}
				}
			});	
		}else{
			successSelectArea(imgSrcPath);
		}
	}
	function preview(img, selection){
		showCut(selection.width,selection.height,selection.x1,selection.y1);
	}
	function showCut(w,h,x,y){   
		var scaleX = minWidth / w;
		var scaleY = minHeight / h;
		$("#facePreview .imgshow img, #minImg").each(function(){
			scaleX = $(this).parent().width() / w;
			scaleY = $(this).parent().height() / h;
			$(this).css({ width: Math.round(scaleX * imageW * scale) + 'px', height: Math.round(scaleY * imageH * scale) + 'px', marginLeft: '-' + Math.round(scaleX * x ) + 'px', marginTop: '-' + Math.round(scaleY * y) + 'px' });
		});
		cutImageW = w;
		cutImageH = h;
		cutImageX = x;
		cutImageY = y;
	}
	
		imageW = $('#ferret').width();
		imageH = $('#ferret').height();
		if(imageW>imgDivW) {
			scale = imgDivW/imageW;
			$('#ferret').css({width:Math.round(imgDivW)+'px',height:'auto'});
		}
		
		//默认尺寸
		if(imageW<minWidth||imageH<minHeight) {
			alert("源图尺寸小于缩略图，请重新上传一个较大的图片。");
			return;
		}
		/* if(imageW==minWidth&&imageH==minHeight) {
			alert("源图和缩略图尺寸一致，请重设缩略图大小。");
			return;
		} */
		$('#imgDiv').css({'width':minWidth+'px','height':minHeight+'px'});
		var minSelW = Math.round(minWidth*scale);
		var minSelH = Math.round(minHeight*scale);
		$('#ferret').imgAreaSelect({
			selectionOpacity:0,
			outerOpacity:'0.5',
			selectionColor:'#ffffff',
			borderColor1:"#6fa7c6",
			borderColor2:"transparent",
			onSelectChange:preview,
			minWidth:minSelW,
			minHeight:minSelH,
			aspectRatio:minWidth+":"+minHeight,
			x1:0,y1:0,
			x2:parseInt(minWidth),y2:parseInt(minHeight)
			});
		showCut(minWidth,minHeight,0,0);
		$(".imgareaselect-border2").bind("dblclick",function(){
			submitCut();
		});
	
}
</script>
