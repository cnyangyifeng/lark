<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<div class="page-body">
    <section class="section box-control">
        <div class="hd">
            <div class="btn-toolbar">
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        操作
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:void(0)" id="expStudyTrackA">导出列表</a></li>
                    </ul>
                </div>
                <div class="toolbar-search">
                    <input type="text" id="serach" class="search" onkeydown="showSearch();" onkeyup="showSearch();">
                    <i class="icon-search" id="selectButton"></i>
                </div>
            <span class="showState">
                <span class="muted">当前显示：</span>         
            		 <span id="markshow" ><a id="containkey" href="#">全部条目</a></span>   
            </span>
                <a class="btn btn-link" href="#rightCont" id="resetSelect">清空搜索结果</a>
            </div>
        </div>
        <div class="bd">
            <div class="btn-toolbar">
                <label class="muted">排序</label>
                <div class="btn-group btns-radio" data-toggle="buttons-radio" id="divOrder"> 
               		<button  date-orderType="time" class="btn btn-large active" type="button">时间</button>
                    <button  date-orderType="course" class="btn btn-large"  type="button">课程</button>
                    <button  date-orderType="newTeacher" class="btn btn-large" type="button">教师</button>
                    <button  date-orderType="teacher" class="btn btn-large" type="button">导师</button>
                </div>
                <label class="radio inline" for="selectCurrPage"><input value="noPage" type="radio" id="selectCurrPage" name="selectCheckbox" checked />选中本页</label>
                <label class="radio inline" for="selectAll"><input value="all" type="radio" id="selectAll" name="selectCheckbox" />选中全部</label>
                <div class="pages" id="pageTop" >

                </div>
            </div>
        </div>
    </section>    
    <section class="listWrap">
        <ul class="listTeacher media-list" id="listTeacher">

        </ul>
    </section>
    <div class="pages" id="pagebottom">
    </div>
</div>
<input id="selectTypeHidden" type="hidden"  value="${param.selectType}" name="selectType"/>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
refreshTrackList("",1,10,"time");
$("#resetSelect").bind("click",function(){
	$("#serach").val("");
	$("#markshow").html('<a id="containkey"href="#">全部条目</a>');
	refreshTrackList("",1,10,"time");
});

$("#selectButton").bind("click",function(){
	refreshTrackList("",1,10,"time",$("#serach").val());
	
});

$("#divOrder button").bind("click",function(){
	refreshTrackList('',1,10,$(this).attr("date-orderType"),$("#serach").val());
});

function refreshTrackList(type,pageNo,pageSize,order){
	if(!order){
		order=$("#divOrder .active").attr("date-ordertype");
	}
	resetOrderImg(order);
	type=$("#selectTypeHidden").val();
	/*老师列表模板函数*/
	var listTeacherFn = doT.template(document.getElementById("listTeacherTemplate").text);
	var pageTopFn = doT.template(document.getElementById("pageTopTemplate").text);
	var pageBottomFn = doT.template(document.getElementById("pageBottomTemplate").text);
	var data = {};
	var pageDate={};
	$.ajax({
		url : "${ctx}/ajax/studyTrack/getTracks",
		async : false,
		dataType : 'json',
		type: "post",
		data:{
			selectType:type,
			pageNo:pageNo,
			pageSize:pageSize,
			order:order,
			key:$("#serach").val()
		},
		success : function(result) {
			data = result.list;
			pageDate = result.pageInfo;
		}
	});
	$("#expStudyTrackA").bind("click",function(){
		var isAll=$(':radio[name="selectCheckbox"]:checked').val();
		var chk_value = [];
		$("#listTeacher li").each(function() {
			chk_value.push($(this).attr("data-fdid"));
		});
		var serach = $("#serach").val();
		if(chk_value.length==0){
			jalert_tips("没有要导出的数据");
			return false;
		}
		window.location.href="${ctx}/common/exp/getExpStudyTrack?modelIds="+chk_value+"&selectType="+type+"&key="+serach+"&order="+order+"&isAll="+isAll;
	});
	$("#pageTop").html(pageTopFn(pageDate));
	pageDate.type=type;
	pageDate.order=order;
	$("#pagebottom").html(pageBottomFn(pageDate));
	 if(pageDate.pageNo==1){
		 $("#gotoBefore1").unbind();
		 $("#gotoNext1").bind("click",function (){refreshTrackList(type,pageDate.pageNo+1,pageDate.pageSize);});
		 $("#gotoBefore1").attr("disabled",true);
		 $("#gotoNext1").removeAttr("disabled"); 
		 $("#gotoBefore2").unbind();
		 $("#gotoNext2").bind("click",function (){refreshTrackList(type,pageDate.pageNo+1,pageDate.pageSize);});
		 $("#gotoBefore2").attr("disabled",true);
		 $("#gotoNext2").removeAttr("disabled"); 
	 }
	 //最后一页
	 if(pageDate.pageNo==pageDate.totalPage){
		 $("#gotoNext1").unbind();
		 $("#gotoNext1").attr("disabled",true);
		 $("#gotoBefore1").bind("click",function (){refreshTrackList(type,pageDate.pageNo-1,pageDate.pageSize);});
		 $("#gotoBefore1").removeAttr("disabled");
		 $("#gotoNext2").unbind();
		 $("#gotoNext2").attr("disabled",true);
		 $("#gotoBefore2").bind("click",function (){refreshTrackList(type,pageDate.pageNo-1,pageDate.pageSize);});
		 $("#gotoBefore2").removeAttr("disabled");
	 }
	 //只有一页
	 if(pageDate.pageNo==pageDate.totalPage&&pageDate.pageNo==1){
		 $("#gotoNext1").unbind();
		 $("#gotoBefore1").unbind();
		 $("#gotoNext1").attr("disabled",true);
		 $("#gotoBefore1").attr("disabled",true);
		 $("#gotoNext2").unbind();
		 $("#gotoBefore2").unbind();
		 $("#gotoNext2").attr("disabled",true);
		 $("#gotoBefore2").attr("disabled",true);
	 }
	 //中间
	 if(pageDate.pageNo!=pageDate.totalPage&&pageDate.pageNo!=1){
		 $("#gotoNext1").bind("click",function (){refreshTrackList(type,pageDate.pageNo+1,pageDate.pageSize);});
		 $("#gotoBefore1").bind("click",function (){refreshTrackList(type,pageDate.pageNo-1,pageDate.pageSize);});
		 $("#gotoBefore1").removeAttr("disabled");
		 $("#gotoNext1").removeAttr("disabled");
		 $("#gotoNext2").bind("click",function (){refreshTrackList(type,pageDate.pageNo+1,pageDate.pageSize);});
		 $("#gotoBefore2").bind("click",function (){refreshTrackList(type,pageDate.pageNo-1,pageDate.pageSize);});
		 $("#gotoBefore2").removeAttr("disabled");
		 $("#gotoNext2").removeAttr("disabled");
	 }
	 
	$("#listTeacher").html(listTeacherFn(data))
	.children(".media").bind({
	    "mouseover": function(e){
	        $(this).addClass("active");
	    },
	    "mouseout": function(e){
	        $(this).removeClass("active");
	    },
	    "click": function(e){
	        var $target = $(e.target);
	        if(!$target.hasClass("send")){
	            window.open("${ctx}/passThrough/getCourseFeelingByBamId?bamId="+$(this).attr("data-fdid"));//打开老师页面
	        } 
	    }
	});	
	
	$(".removeA").each(function(i){
		var $this = $(this);
		$this.bind("click",function(){
			jalert("确定删除该学习记录吗？",function(){
				$.ajax({
					url : "${ctx}/ajax/studyTrack/deleBam",
					async : false,
					dataType : 'json',
					type: "post",
					data:{
						bamId:$this.attr("bamId"),
						userId:$this.attr("userId")
					},
					success : function(result) {
						refreshTrackList('',1,10,$(this).attr("date-orderType"),$("#serach").val());
					}
				});
			});
			
		});
		
	});
	$("#goTop").trigger("click");
}

function showSearch(){
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (keyCode == 13) {
		refreshTrackList("",1,10,"time",$("#serach").val());
		return false;
	}else{
		var fdTitle = document.getElementById("serach").value;
		$("#markshow").html('含“<a id="containkey" href="#"></a>”的条目');
		if(fdTitle==''){
			$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
		}else if(fdTitle.length>2){
			$("#containkey").html(fdTitle.substr(0,6)+"...");
		}else{
			$("#containkey").html(fdTitle);
		}
	}
}

function resetOrderImg(order){
	$("#divOrder button").removeClass("active");
	$("#divOrder button").each(function(i){
		if($(this).attr("date-orderType")==order){
			$(this).addClass("active");
		}
	});
}
</script>