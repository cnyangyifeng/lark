/**
 * Created by wqh on 14-1-8.
 */
$(function(){
 
    	$(this).keypress( function(e) { 
      	    var key = window.event ? e.keyCode : e.which;  
      	    if(key.toString() == "13"){  
      	    	if($("#submitLetter").length>0){
      	    	$("#submitLetter").trigger("click");
      	    	}
      	    }  
      	  });
});
$(function(){
	var fdCourseId = $("#fdCourseId").val();
	 //侧边栏 头像列表html
    var sideListFaceFn = doT.template(document.getElementById("sideListFaceTemplate").text);
    
    //var	sideListFaceData = {};//学习人员
    //得到课程学习人员
    $.ajax({
     	  type:"get",
  		  url:$("#ctx").val()+"/ajax/passThrough/getLeaningTeacherTop",
  		  async: false,
  		  data:{
  			fdCourseId:fdCourseId, 
  		  },
  		  dataType:'json',
  		  success: function(result){
  			//alert(JSON.stringify(result.admin));
  			//sideListFaceData = result;
  			$("body").append(sideListFaceFn(result));
  		  }
   	});
    
    //聊天窗口 模板函数
    var chatDialogFn = doT.template(document.getElementById("chatDialogTemplate").text);
    var chatDialogData = {};
    //聊天纪录列表 模板函数
    var listChatLogFn = doT.template(document.getElementById("listChatLogTemplate").text);
    
    var listChatData = [];
    
    var $sideListFace = $("#sideListFace");
    var $sideListLearner = $sideListFace.find(".side_listFace");
    var hideHeight = $sideListLearner.height() - $sideListFace.children(".side_listFace_wrap").height();
    $(window).on("resize load",function(){
        $sideListLearner.css("top",0);
        hideHeight = $sideListLearner.height() - $sideListFace.children(".side_listFace_wrap").height();
        if(hideHeight > 0){
            $sideListLearner.nextAll(".next").removeClass("hide");
        } else {
            $sideListLearner.nextAll(".next").addClass("hide");
        }
        $sideListLearner.nextAll(".prev").addClass("hide");
    });
    $sideListFace.find(".prev,.next").bind("click",function(e){
        e.preventDefault();
        var $this = $(this);
        if($(this).hasClass("prev") && $sideListLearner.css("top") != 0 +"px"){
            $sideListLearner.animate({top: parseInt($sideListLearner.css("top"))>-59 ? 0 : "+=59"},"fast","swing",function(){
                $this.next(".next").removeClass("hide");
                if($sideListLearner.css("top") == 0 +"px"){
                    $this.addClass("hide");
                }
            });
        } else if($(this).hasClass("next") && $sideListLearner.css("top") != -hideHeight +"px"){
            $sideListLearner.animate({top: hideHeight+parseInt($sideListLearner.css("top"))<59 ? -hideHeight : "-=59"},"fast","swing",function(){
                $this.prev(".prev").removeClass("hide");
                if($sideListLearner.css("top") == -hideHeight +"px"){
                    $this.addClass("hide");
                }
            });
        }
    }).end().find(".side_support_list>li>a, .side_listFace>li>a").bind("click",function(e){
            e.preventDefault();
           if(!$(this).parent().hasClass("disabled")){
            $(this).parent().addClass("active").siblings().removeClass("active");
            $("#side_webim").remove();
            var fdId = $(this).attr("data-fdId");
            //取出人员信息
            $.ajax({
         	      type:"post",
         	      cache:false,
         	      async:false,
      		      url: $("#ctx").val()+"/ajax/letter/findPersonById",
	      		  data:{
	      			  fdId:fdId, 
	      		  },
	      		  dataType:'json',
	      		  success: function(result){
	      			chatDialogData.user = result;
	      			$sideListFace.after(chatDialogFn(chatDialogData));
	      		  }
       	    });
            //chatDialogData.user.id = fdId;
            //chatDialogData.user.name = $(this).attr("title");
            //chatDialogData.user.imgUrl = $(this).children("img").attr("src");
            //chatDialogData = ajax 取
            //取出人员信息聊天
            var $listChat = $("#listChatLog");
            $.ajax({
           	      type:"post",
           	      cache:false,
        		  url: $("#ctx").val()+"/ajax/letter/findLeftDetailLetter",
        		  async:false,
        		  data:{
        			  fdId:fdId, 
        		  },
        		  dataType:'json',
        		  success: function(result){
        			//alert(JSON.stringify(result.returnlist));
        			  if(result!=null){
        				  listChatData = result.returnlist;
        				  $listChat.html(listChatLogFn(result.returnlist));
        			  }
        		  }
         	});
            //定位到最下边，显示最新私信(yuhz)
            $listChat.parent(".side_webim_chatLog").animate({scrollTop:60000},"slow");
            
            var $icon = $(this).find(".icon-disc");
            
            if($icon.length>0){//移去人员旁边的小点
            	$icon.remove();
            }
            
            $("#submitLetter").click(function(e){
            	if(!$("#formWebim").valid()){
            		return;
            	}
            	var d = new Date();
                var currDate = formatDate(d);
                var item = {
                    id: fdId,
                    isMe: true,
                    msg: $("#webim_input").val(),
                    time: d.getHours()+":"+ d.getMinutes()+" "+ (d.getHours()>12 ? "PM" : "AM")
                };
                listChatData.push({
                    list:[item],
                    date: currDate
                });
                $listChat.html(listChatLogFn(listChatData));
                //form.reset();
                $.ajax({
               	  type:"post",
           		  url:$("#ctx").val()+"/ajax/letter/saveLetter",
           		  data:{
           			fdId:fdId, 
           			body:$("#webim_input").val(),
           		  },
           		  success: function(data){
           			$("#webim_input").val("");
           		  }
             	}); 
                $listChat.parent(".side_webim_chatLog").animate({scrollTop:60000},"slow");
                setTimeout(timeOut,1000);//延迟1秒消失
            });
           }
        });
    function timeOut(){
    	$("#side_webim").remove();
    }
    function formatDate(d){
        return  d.getFullYear()+ "/" + ((d.getMonth()+1)<10 ? "0"+(d.getMonth()+1) : d.getMonth()+1) + "/" + (d.getDate()<10 ? "0"+ d.getDate() : d.getDate());
    }
    
});
