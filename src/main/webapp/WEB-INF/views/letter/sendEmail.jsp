<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业在线教师学习平台</title>
<link href="${ctx}/resources/css/global.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/DTotal.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
</head>

<body>


<section class="container">
	<section class="clearfix mt20">
	  <section class="col-left pull-left">
    	<%@ include file="/WEB-INF/views/letter/menu.jsp" %>
	  </section>
		<section class="w790 pull-right" id="rightCont">
	        <div class="page-header">
                <a href="${ctx}/letter/findLetterList" class="backParent">返回</a>
                <h4>发邮件</h4>
	        </div>
            <div class="page-body editingBody">
                <form action="#" id="formEditDTotal" class="form-horizontal" method="post">
                    <section class="section">
                        <div class="control-group">
                            <label class="control-label" for="addUser">发  给</label>
                            <div class="controls">
                                <input type="text" required id="addUser" name="addUser" class="autoComplete span6">
                                <span class="date"><fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy/MM/dd hh:mm aa"/></span>
                            </div>
                        </div>
                        <!-- <div class="control-group">
                            <label class="control-label" for="videoIntro">内  容</label>
                            <div class="controls">
                            <textarea rows="4" class="input-block-level" id="body"
                                      name="body"></textarea>
                            </div>
                        </div> -->
                    </section>
                    <button class="btn btn-block btn-submit btn-inverse" type="submit">发邮件</button>
                </form>
            </div>
	    </section>
	</section>
<input type="hidden" id="fdEamil">
</section>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript">
$(function(){
    $.Placeholder.init();

    $("#formEditDTotal").validate({
        submitHandler: function(form){
        	if($("#fdEamil").val()==null||$("#fdEamil").val()==""){
        		$("#addUser").after('<label class="error" >输入数据有误,请从下拉菜单中选择数据!');
        		return;
        	}else{
        		window.location.href = "mailto:"+$("#fdEamil").val();
        	}
        }
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
		width:502
    }).result(function(e,item){
    	$("#addUser").next(".error").remove();
    	$("#addUser").val(item.name + '（' + item.mail + '）');
    	$("#fdEamil").val(item.mail);
	});
});
</script>
</body>
</html>
