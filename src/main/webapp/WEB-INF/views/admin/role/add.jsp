<%@page import="com.kuxue.model.base.AttMain"%>
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
            <img src="{{?it.imgUrl.indexOf('http')>-1}}{{=it.imgUrl}}{{??}}${ctx}/{{=it.imgUrl}}{{?}}" />{{=it.name}}（{{=it.mail}}），{{=it.org}} {{=it.department}}
          <div>
         </td>
        <td><a href="#" class="icon-remove-blue"></a></td>
    </tr>
</script>


    <script src="${ctx}/resources/js/doT.min.js"></script>
</head>

<body>
	        <div class="page-header">
                <a href="${ctx}/admin/role/list" class="backParent">
                <span id="back">返回角色列表</span>
               </a>
                <h4>角色设置</h4>
                <div class="btn-group">
                    <button class="btn btn-large btn-primary" type="button" onclick="saveMater();">保存</button>
               </div>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" onkeyup="pressEnter();" method="post">
                    <input type="hidden" id="roleName" name="roleName" value='group'>
                    <section class="section">
                        <label>角色类型</label>
                        <ul class="nav nav-pills">
                            <li><a data-toggle="tab" href="#admin">管理员</a></li>
                            <li class="active"><a data-toggle="tab" href="#group">主管</a></li>
                        </ul>
                        <div class="tab-content">
                             <div class="tab-pane active" id="encrypt">
                                <table class="table table-bordered">
                                    <tbody id="list_user"></tbody>
                                </table>
                                <div class="pr">
                                    <input type="text" id="addUser" class="autoComplete input-block-level" placeholder="请输入人名、邮箱、机构或者部门查找用户">
                                    
                                </div>
                            </div>
                        </div>
                    </section>
                    <button class="btn btn-block btn-submit btn-inverse" type="submit">保存</button>
                </form>
            </div>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>
<script type="text/javascript">
function pressEnter(){
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (keyCode == 13) {
		return false;
	}
}

</script>

<script type="text/javascript">
$(function(){
    $.Placeholder.init();
    
    $("#rightCont .bder2").addClass("hide");
    
    //授权管理 用户列表 模板函数
    var listUserKinguserFn = doT.template(document.getElementById("listUserKinguserTemplate").text);
    
    $("#formEditDTotal").validate({
        submitHandler:saveMaterial
    });
    
    $('#formEditDTotal a[data-toggle="tab"]').on('shown', function (e) {
        var href = 	e.target.href.split("#").pop();
        $("#roleName").val(href);
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
		width:750
    }).result(function(e,item){
		var flag = true;
		$("#addUser").next(".help-block").remove();
		$("#list_user>tr").each(function(){
			if($(this).attr("data-fdid")==item.id){
				$("#addUser").after('<span class="help-block">不能添加重复的用户！</span>');;
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
    	roleName:$("#roleName").val(),
        kingUser: null
    };
        //push人员授权数据
        data.kingUser = [];
        $("#list_user>tr").each(function(){
            data.kingUser.push({
                id: $(this).attr("data-fdid")
            });
        });
        if(data.kingUser.length<1){
        	$("#addUser").after('<span class="help-block">请选择用户！</span>');;
        	return;
        }
        data.kingUser = JSON.stringify(data.kingUser);
    $.ajax({
    	  type:"post",
		  url:"${ctx}/admin/role/saveRoles",
		  async:false,
		  data:data,
		  dataType:'json',
		  success: function(rsult){
			  window.location.href="${ctx}/admin/role/list";
		  }
	});
}
function saveMater(){
	$("#formEditDTotal").trigger("submit");
}
</script>
</body>
</html>
