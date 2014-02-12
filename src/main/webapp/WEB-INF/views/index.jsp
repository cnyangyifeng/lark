<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>

<head>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/home.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="banner-bg">
  <div class="container banner">
    <a href="${ctx}/course/courseIndex" class="start-btn"></a>
  </div>
</div>
<div class="bar-tools">
	<div class="container">
		<div class="bar-tools-wrap">
			<ul class="counting nav clearfix">
				<li>
                    <i class="icon-school-home"></i>
                    <span>
                       <h2></h2>
                        学校
                    </span>
                </li>
				<li>
                    <i class="icon-teacher-home"></i>
                	<span><h2></h2>
                    教师 </span>
                </li>
                <li>
                    <i class="icon-mentor-home"></i>
                	<span><h2></h2>
                    导师 </span>
                </li>
                <li>
                    <i class="icon-book-home"></i>
                	<span><h2></h2>
                    课程</span>
                </li>
			</ul>
		</div>
	</div>
</div>
<div class="container">
	<div class="page-header">
		<h3>在线课程学习</h3>
	</div>
	<div class="proIntro">
        <ul class="listIntro thumbnails">
            <li class="thumbnail">
                <i class="icon-intro-1"></i>
                <h4>开放在线课程</h4>
                <p>新东方自己的<em>MOOC</em>，大规模开放在线课程平台。像<em><a href="https://www.coursera.org" target="_blank">Coursera</a></em>、<em><a href="https://www.edx.org" target="_blank">edX</a></em>一样，加入这场风靡全球的教育变革吧。</p>
            </li>
            <li class="divider">
                <i class="icon-circle-home"></i>
            </li>
            <li class="thumbnail">
                <i class="icon-intro-2"></i>
                <h4>集团教研分享</h4>
                <p>汇聚集团最优质的<em>教研骨干力量</em>，全面呈现<em>科学</em>、<em>扎实</em>的教研成果。一切尽在这里，广纳百川，倾囊相受。</p>
            </li>
            <li class="divider">
                <i class="icon-circle-home"></i>
            </li>
            <li class="thumbnail">
                <i class="icon-intro-3"></i>
                <h4>教师职业发展</h4>
                <p>为新东方教师的职业生涯规划提供完整的<em>培训课程体系</em>，解决不同的<em>岗位素质要求</em>与教师能力成长的匹配问题。</p>
            </li>
            <li class="divider">
                <i class="icon-circle-home"></i>
            </li>
            <li class="thumbnail">
                <i class="icon-intro-4"></i>
                <h4>名师公开课堂</h4>
                <p>海内存知己，天涯若比邻。新东方<em>最优秀</em>教师们的绝代风华<em>近在咫尺</em>，<em>震撼登场</em>。让我们一起来领略名师风采吧。</p>
            </li>
            <li class="divider">
                <i class="icon-circle-home"></i>
            </li>
            <li class="thumbnail">
                <i class="icon-intro-5"></i>
                <h4>学校师训跟踪</h4>
                <p>学校教师培训工作需要<em>量化指标</em>考核？如何<em>实时跟踪</em>每位老师的学习效果？如何<em>科学改进</em>培训课程的内容逻辑？让我们一起做得更好。</p>
            </li>
            <li class="divider">
                <i class="icon-circle-home"></i>
            </li>
            <li class="thumbnail">
                <i class="icon-intro-6"></i>
                <h4>学习通关证书</h4>
                <p>你来<em>学习</em>，我们来<em>认证</em>。想要证明自己？来晒晒拿到了多少高含金量的课程结业证书吧。也许你就是下一颗璀璨的<em>明星</em>。</p>
            </li>
        </ul>
    </div>
	<div class="page-header">
		<h3>倾听一线心声</h3>
	</div>
  <div class="tabbable tabs-below">
   	<div class="tab-content">
			<div class="tab-pane active" id="tab1">
    			<div><p><i class="icon-shyhl"></i><span name="intro">初入职场时，尤其感觉到了职场技能的欠缺。工作之外的时间可以来这里看看，随时都可以学习，感觉真心不错！</span><i class="icon-shyhr"></i></p></div>
    		</div>
    		<div class="tab-pane" id="tab2">
   				<div><p><i class="icon-shyhl"></i><span name="intro">下班后会来看看有没有更新，然后学习一下，很受用。</span><i class="icon-shyhr"></i></p></div>
    		</div>
    		<div class="tab-pane" id="tab3">
    			<div><p><i class="icon-shyhl"></i><span name="intro">在这里发现很多熟悉的培训师视频教程，真心不错！</span><i class="icon-shyhr"></i></p></div>
    		</div>
    		<div class="tab-pane" id="tab4">
    			<div><p><i class="icon-shyhl"></i><span name="intro">手头的资料大多是零散知识片段，真想学点什么的时候总缺乏系统性的知识体系。在线备课平台很不错，把知识有机的组织在一起，让人耳目一新。足见信息的组织精炼同样非常重要！</span><i class="icon-shyhr"></i></p></div>
    		</div>
   	  </div>
   	  <ul class="nav nav-tabs" id="myTabs">
   		  <li class="active"><a href="#tab1" data-toggle="tab">
       	  	<img src="${ctx}/resources/images/temp-face70.jpg" width="70" height="70">
            <h5>Grace</h5>
            <p>雅思教师</p>
          </a></li>
   		  <li><a href="#tab2" data-toggle="tab">
       	  	<img src="${ctx}/resources/images/temp-face70.jpg" width="70" height="70">
            <h5>Eric</h5>
            <p>托福教师</p>
          </a></li>
   		  <li><a href="#tab3" data-toggle="tab">
       	  	<img src="${ctx}/resources/images/temp-face70.jpg" width="70" height="70">
            <h5>Daniel</h5>
            <p>考研教师</p>
          </a></li>
   		  <li><a href="#tab4" data-toggle="tab">
       	  	<img src="${ctx}/resources/images/temp-face70.jpg" width="70" height="70">
            <h5>Felix</h5>
            <p>SAT教师</p>
          </a></li>
          
          <li class="caret"></li>
   	  </ul>
    </div>
     <div class="page-header">
		<h3>新东方与你同在</h3>
	</div>
	<div class="grid clearfix" id="grid">
        <div class="pull-left">
            <div class="media w369">
                <a class="pull-left" href="javascript:void(0)">
                    <img src="${ctx}/resources/images/hoverfold-01.jpg"  class="media-object" alt=""/>
                </a>
                <div class="media-body">
                    <div class="hd">北京学校</div>
                    <div class="bd">
                        <i class="icon-shyhl"></i>
                        <h4>不要只懂生存
                            不懂生活</h4>
                        <i class="icon-shyhr"></i>
                    </div>
                    <div class="ft">教师&nbsp;0 <span>|</span>导师&nbsp;0
                    </div>
                </div>
            </div>
            <div class="media w369">
                <a class="pull-left" href="javascript:void(0)">
                    <img src="${ctx}/resources/images/hoverfold-01.jpg" class="media-object" alt=""/>
                </a>
                <div class="media-body">
                    <div class="hd">北京学校</div>
                    <div class="bd">
                        <i class="icon-shyhl"></i>
                        <h4>不要只懂生存
                            不懂生活</h4>
                        <i class="icon-shyhr"></i>
                    </div>
                    <div class="ft">教师&nbsp;0 <span>|</span>导师&nbsp;0
                    </div>
                </div>
            </div>
        </div>
        <div class="media">
            <a class="" href="javascript:void(0)">
                <img src="${ctx}/resources/images/hoverfold-01.jpg" class="media-object" alt=""/>
            </a>
            <div class="media-body">
                <div class="hd">北京学校</div>
                <div class="bd">
                    <i class="icon-shyhl"></i>
                    <h4>不要只懂生存
                        不懂生活</h4>
                    <i class="icon-shyhr"></i>
                </div>
                <div class="ft">教师&nbsp;0 <span>|</span>导师&nbsp;0
                </div>
            </div>
        </div>
        <div class="media w369">
            <a class="pull-left" href="javascript:void(0)">
                <img src="${ctx}/resources/images/hoverfold-01.jpg" class="media-object" alt=""/>
            </a>
            <div class="media-body">
                <div class="hd">北京学校</div>
                <div class="bd">
                    <i class="icon-shyhl"></i>
                    <h4>不要只懂生存
                        不懂生活</h4>
                    <i class="icon-shyhr"></i>
                </div>
                <div class="ft">教师&nbsp;0<span>|</span>导师&nbsp;0
                </div>
            </div>
        </div>
        <div class="media w369">
            <a class="pull-left" href="javascript:void(0)">
                <img src="${ctx}/resources/images/hoverfold-01.jpg" class="media-object" alt=""/>
            </a>
            <div class="media-body">
                <div class="hd">北京学校</div>
                <div class="bd">
                    <i class="icon-shyhl"></i>
                    <h4>不要只懂生存
                        不懂生活</h4>
                    <i class="icon-shyhr"></i>
                </div>
                <div class="ft">教师&nbsp;0<span>|</span>导师&nbsp;0</div>
            </div>
        </div>
    </div>
    <div class="grid-bottom">
         	<div class="pages">            	
                <div class="btn-group">
                	<button class="btn btn-primary"  id="afterBtn"><i class="icon-chevron-left icon-white"></i></button>
                    <button class="btn btn-primary"  id="nextBtn"><i class="icon-chevron-right icon-white"></i></button>
                </div>
            </div>
     </div>
</div>

<script type="text/javascript">	
initSchool(1);		
$("#myTabs>li>a").bind("mouseover",function(e){
	$(this).tab("show");		
})
.on("shown",function(e){
	$("#myTabs>.caret").stop(false,true).animate({left: $(this).parent().index()*250 + 46 + "px"},"slow","swing");
});
//初始化学校 教师 导师 课程数
$.ajax({
	type: "post",
	async:false,
	 url: "${ctx}/ajax/head/getSchoolInfo",
	 dataType : 'json',
	success:function(data){
		$(".icon-school-home").next("span").children("h2").html(data.schoolNum=="0"?0:parseInt(data.schoolNum).toLocaleString().split(".")[0]);
		$(".icon-mentor-home").next("span").children("h2").html(data.mentorNum=="0"?0:parseInt(data.mentorNum).toLocaleString().split(".")[0]);
		$(".icon-teacher-home").next("span").children("h2").html(data.newTeacherNum=="0"?0:parseInt(data.newTeacherNum).toLocaleString().split(".")[0]);
		$(".icon-book-home").next("span").children("h2").html(data.courseNum=="0"?0:parseInt(data.courseNum).toLocaleString().split(".")[0]);
	}
});
//教师寄语
$.ajax({
	type: "post",
	async:false,
	 url: "${ctx}/ajax/head/getTeacherWord",
	 dataType : 'json',
	success:function(data){
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				$("#myTabs li:eq("+i+") h5").html(data[i].tname);
				var img="";
				if(data[i].headimg.indexOf('http')>-1){
					img =data[i].headimg;
				}else{
					img ="${ctx}/"+data[i].headimg;
				}
				$("#myTabs li:eq("+i+") img").attr("src",img);
				var dep = data[i].dep;
				if(data[i].dep.length>8){
					dep = data[i].dep.substr(0,8)+"...";
				}
				
				$("#myTabs li:eq("+i+") p").html(dep);
				$("#myTabs li:eq("+i+") p").attr("title",data[i].dep);
				$("span[name='intro']:eq("+i+")").html(data[i].content);
			}
			
			
		}
		
	}
});

//初始化学校
function initSchool(pageNo){
	var temp = {};
	$.ajax({
		type: "post",
		async:false,
		url: "${ctx}/ajax/head/getSchoolWord",
		data:{pageNoStr:pageNo},
		dataType : 'json',
		success:function(data){
			$("#grid .media").each(function(i){
				if(data.schools[i]!=undefined){
					$this = $(this);
					var name=data.schools[i].sname;
					$this.find(".media-body .hd").attr("title",name);
					if(name.length>9){
						name=name.substr(0,8)+"...";
					}
					$this.find(".media-body .hd").html(name);
					var content=data.schools[i].content;
					$this.find(".media-body .bd h4").attr("title",content);
					if(content.length>17){
						content=content.substr(0,15)+"...";
					}
					$this.find(".media-body .bd h4").html(content);
					if(data.schools[i].simg!=""){
						$this.find("img").attr("src","${ctx}/common/file/image/"+data.schools[i].simg+"?n="+Math.random()*100);
					}else{
						$this.find("img").attr("src","${ctx}/resources/images/hoverfold-01.jpg");
					}
				}
			});
			temp=data;
		}
	});
	getSchoolStatic(pageNo);
	$("#afterBtn").unbind();
	$("#nextBtn").unbind();
	if(temp.pageNo>1){
		$("#afterBtn").bind("click",function(){
			initSchool(temp.pageNo-1);	
			getSchoolStatic(temp.pageNo-1);
		});
		$("#afterBtn").attr("disabled",false);
	}else{
		$("#afterBtn").attr("disabled",true);
	};
	if(temp.pageNo<temp.pageTotal){
		$("#nextBtn").bind("click",function(){
			initSchool(temp.pageNo+1);	
			getSchoolStatic(temp.pageNo+1)
		});
		$("#nextBtn").attr("disabled",false);
	}else{
		$("#nextBtn").attr("disabled",true);
	};
}
function getSchoolStatic(pageNo){
	$.ajax({
		type: "post",
		async:true,
		url: "${ctx}/ajax/head/getStatistics",
		data:{pageNoStr:pageNo},
		dataType : 'json',
		success:function(data){
			$("#grid .media").each(function(i){
				$this = $(this);
				if(data[i]!=undefined){
					$this.find(".media-body .ft").html('教师&nbsp;'+data[i].tnum+'<span>|</span>导师&nbsp;'+data[i].mnum);
				}
			})
		}
	});
}
</script>
</body>
</html>
