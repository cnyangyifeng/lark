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
                <a href="${ctx}/material/findList?fdType=07&order=FDCREATETIME" class="backParent">返回在线创作列表
                <span id="back"></span>
               </a>
                <h4><tags:title value="${materialInfo.fdName}" size="25" ></tags:title></h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" type="button" onclick="saveMaterial();">保存</button>
                    <c:if test="${materialInfo.fdId!=null}">
                    <button class="btn btn-white btn-large " type="button" onclick="confirmDel();">删除</button>
                    </c:if> 
               </div>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" method="post">
                    <section class="section">
                        <div class="control-group">
                            <label class="control-label" for="videoName" id="typeTxt">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</label>
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
                                    </button>
                                </span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="videoIntro" id="materialIntro">简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介</label>
                            <div class="controls">
                              <textarea placeholder="非必填项" rows="4"
                                        class="input-block-level" id="videoIntro"
                                        name="fdDescription">${materialInfo.fdDescription}</textarea>
                            </div>
                        </div>
                    </section>
                    <section class="section mt20">
                    <textarea id="richText" name="richText" 
                        style="width:100%;height:400px;visibility:hidden;">
                        ${materialInfo.richContent}
                    </textarea>
                    </section>
                    <section class="section mt20">
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
                               <textarea placeholder="非必填项" rows="4"
                                       class="input-block-level" id="authorIntro"
                                       name="fdAuthorDescription" >${person.selfIntroduction}</textarea>
                             </c:if>
                             <c:if test="${materialInfo!=null&&materialInfo!=''}">
                             <textarea placeholder="非必填项" rows="4"
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
                                    <thead><tr><th>授权用户</th><th>可使用</th>
                                     <th>可编辑</th><th>删除</th></tr></thead>
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
                                    <thead><tr><th>授权用户</th><th>可用</th>
                                     <th>编辑</th><th>删除</th></tr></thead>
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
                                    <thead><tr><th>授权用户</th><th>可用</th>
                                     <th>编辑</th><th>删除</th></tr></thead>
                                    <tbody id="list_user"></tbody>
                                </table>
                                <div class="pr">
                                    <input type="text" id="addUser" class="autoComplete input-block-level" placeholder="请输入人名、邮箱、机构或者部门查找用户">
                                </div>
                            </div>
                          </c:if>
                        </div>
                    </section>
                    <button class="btn btn-block btn-submit btn-inverse" type="button" onclick="saveMaterial()">保存</button>
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
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script charset="utf-8" src="${ctx}/resources/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${ctx}/resources/kindeditor/lang/zh_CN.js"></script>
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
</script>
<script type="text/javascript">
var editor = KindEditor.create('textarea[id="richText"]', {
								resizeType : 1,
								cssData : 'body {font-size:14px;}',
								allowPreviewEmoticons : false,
								allowImageUpload : true,
								uploadJson : '${ctx}/common/file/KEditor_uploadImg',
								items : ['source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
									'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
									'insertunorderedlist', '|', 'undo', 'redo','link','image'],
								afterBlur: function(){this.sync();}
							});
</script>
<script type="text/javascript">
$(function(){
    $.Placeholder.init();
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
		$("#addUser").next(".help-block").remove();
		$("#list_user>tr").each(function(){
			if($(this).attr("data-fdid")==item.id){
				$("#addUser").after('<label class="error" for="addKey">不能添加重复的关键词!');;
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
});
function saveMaterial(){
	if(!$("#formEditDTotal").valid()){
		return;
	}
    var data = {
        videoName: $("#videoName").val(),
        fdId: $("#fdId").val(),
        videoUrl: $("#videoUrl").val(),
        videoIntro: $("#videoIntro").val()==""?"在线创作的作者很懒，没有写简介。":$("#videoIntro").val(),
        author: $("#author").val(),
        authorIntro: $("#authorIntro").val(),
        permission:$("#permission").val(),
        fdType:$("#fdType").val(),
        attId:$("#attId").val(),
        isDownload:$("#isDownload").val(),
        richText:$("#richText").val(),
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
