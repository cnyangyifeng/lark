<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新东方在线教师备课平台</title>
<link rel="stylesheet" href="${ctx}/resources/css/global.min.css" />
<link href="${ctx}/resources/css/datepicker.css" rel="stylesheet" type="text/css">
<link href="${ctx}/resources/css/register.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css"/>
<style>
.uploadify-button {
    background-color:rgb(67,145,187);
	background-image: -webkit-gradient(
		linear,
		left bottom,
		left top,
		color-stop(0, rgb(67,145,187)),
		color-stop(1, rgb(67,145,187))
	);
	max-width:70px;
	max-height:20px;
	border-radius: 1px;
	border: 0px;
	font: bold 12px Arial, Helvetica, sans-serif;
	display: block;
	text-align: center;
	text-shadow: 0 0px 0 rgba(0,0,0,0.25);
    
}
.uploadify:hover .uploadify-button {
    background-color:rgb(67,145,187);
	background-image: -webkit-gradient(
		linear,
		left bottom,
		left top,
		color-stop(0, rgb(67,145,187)),
		color-stop(1, rgb(67,145,187))
	);
}
</style>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=12"></script>
<script type="text/javascript">
    //注册
	function register() {
		//头像
		var img = document.getElementById("fdIcoUrl").value;
		//密码
		var password = document.getElementById("password").value;
		//email
		var email = document.getElementById("user").value;
		//姓名
		var name = document.getElementById("name").value;
		//证件号
		var cradid = $("#idend").html();
		//机构部门
		var depart = document.getElementById("department");
		//附件
		//var attId = document.getElementById("attIdID").value;
		var index = depart.selectedIndex;
		var departid = depart.options[index].value;
		var deptName = depart.options[index].text;
		//电话
		var tel = $("#telend").html();
		//性别
		var sex = "";
		var temp = document.getElementsByName("sex");
		for ( var i = 0; i < temp.length; i++) {
			if (temp[i].checked) {
				if (temp[i].value == "f") {
					sex = "F";
				} else {
					sex = "M";
				}
			}
		}
		
		//出生日期
		var birthday = document.getElementById("birthday").value;
		//血性
		var bloodend = $("#bloodend").html();
		/* var temp1 = document.getElementsByName("blood");
		for ( var i = 0; i < temp1.length; i++) {
			if (temp1[i].checked) {
				if (temp1[i].value == "OTHER") {
					bloodend = "OTHER";
				} else {
					bloodend = temp1[i].value;
				}
			}
		} */
		$.ajax({
			type : "post",
			url : "${ctx}/ajax/register/register",
			data : {
				"password" : password,
				"email" : email,
				"name" : name,
				"cradid" : cradid,
				"departid" : departid,
				"tel" : tel,
				"sex" : sex,
				"birthday" : birthday,
				"bloodend" : bloodend,
				"deptName" : deptName,
				"img" : img,
				"attId":""
			},
			success : function(msg) {
				msg = msg.substr(1, msg.length - 2);
				if(msg=="redirect:/login"){
					window.location.href="${ctx}/";
				}else if(msg=="redirect:/register/list"){
					window.location.href="${ctx}/register/list";
				}else if(msg=="redirect:/register/listerr"){
					window.parent.jalert("注册失败！");
					window.location.href="${ctx}/register/list";
				}else{
					jalert("注册失败！");
				}
			}
		});
	}
	function checkemail(email) {
		var temp = email;
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!myreg.test(temp)) {
			document.getElementById("usererrorMsg").innerHTML = "*&nbsp;邮箱账号格式错误，请重新输入";
		}else{
			$.ajax({
				type : "post",
				dataType : "json",
				url : "${ctx}/ajax/register/checkIdentitymail",
				data : {
					str : temp
				},
				success : function(data) {
					if (data > 0) {
						document.getElementById("usererrorMsg").innerHTML = "*&nbsp;邮箱账号已存在，请重新输入";
					} else {
						document.getElementById("usererrorMsg").innerHTML = "";
					}
				}
			});
		}
	}

	function checkdepart(val) {
		if (val == "0") {
			document.getElementById("departerrorMsg").innerHTML = "*&nbsp;请选择机构/部门";
			return false;
		} else {
			document.getElementById("departerrorMsg").innerHTML = "";
			return true;
		}
	}

	function checktel(val) {
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
		if (val == null || val == "") {
			document.getElementById("telerrorMsg").innerHTML = "*&nbsp;请填写电话号码";
			return false;
		} else if (!myreg.test(val)) {
			document.getElementById("telerrorMsg").innerHTML = "*&nbsp;电话号码格式错误，请重新输入";
			return false;
		} else {
			document.getElementById("telerrorMsg").innerHTML = "";
			return true;
		}
	}

	function checkname(val) {
		var myreg = /^[\u0391-\u9fa5]+$/;
		if (val == null || val == "") {
			document.getElementById("nameerrorMsg").innerHTML = "*&nbsp;姓名不能为空";
			return false;
		} else if (!myreg.test(val)) {
			document.getElementById("nameerrorMsg").innerHTML = "*&nbsp;姓名必须为中文";
			return false;
		} else if(val=="请输入您的姓名"){
			document.getElementById("nameerrorMsg").innerHTML = "*&nbsp;请正确输入姓名";
			return false;
		}else {
			document.getElementById("nameerrorMsg").innerHTML = "";
			return true;
		}
	}

	function changedepart(n) {
		var index = n.selectedIndex;
		var id = n.options[index].value;
		$.ajax({
			type : "post",
			url : "${ctx}/ajax/register/getDeparts",
			data : {
				"id" : id,
			},
			success : function(msg) {
				msg = msg.substr(1, msg.length - 2);
				document.getElementById("department").innerHTML = "";
				var ss = msg.split("=");
				var html = "<option value=0>请输入您的部门</option>";
				for ( var i = 0; i < (ss.length - 1); i++) {
					var s1 = ss[i].split(":");
					html += "<option value="+s1[0]+">" + s1[1] + "</option>";
				}
				$("#department").append(html);
			}
		});

	}
	function checkIdentityCard(val) {
		var IDinput = val;
		if (IDinput == null || IDinput == "") {
			document.getElementById("carderrorMsg").innerHTML = "*&nbsp;证件号码不能为空";
			return false;
		} else if (!isIdCardNo(IDinput)) {
			document.getElementById("carderrorMsg").innerHTML = "*&nbsp;证件号码格式错误，请重新输入";
			return false;
		}
		$.ajax({
					type : "post",
					dataType : "json",
					url : "${ctx}/ajax/register/checkIdentityCard",
					data : {
						str : IDinput
					},
					success : function(data) {
						if (data > 0) {
							document.getElementById("carderrorMsg").innerHTML = "*&nbsp;证件号码已存在，请重新输入";
							return false;
						} else {
							document.getElementById("carderrorMsg").innerHTML = "";
							return true;
						}
					}
				});
	}

	function checkpass1(p1, p2) {
		p1 = document.getElementById("password").value;
		p2 = document.getElementById("confirmPwd").value;
		var myreg = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){7,19}$/;
		if (p1 == "" || !myreg.test(p1)) {
			document.getElementById("userpasserrorMsg1").innerHTML = "*&nbsp;密码格式错误（长度为以字母开头的8-15个字符），请重新输入";
			return false;
		} else {
			document.getElementById("userpasserrorMsg1").innerHTML = "";
		}
	}
	function checkpass2(p1, p2) {
		p1 = document.getElementById("password").value;
		p2 = document.getElementById("confirmPwd").value;
		if (p1 != p2) {
			document.getElementById("userpasserrorMsg").innerHTML = "*&nbsp;密码不一致，请重新输入";
			return false;
		} else {
			document.getElementById("userpasserrorMsg").innerHTML = "";
			return true;
		}
	}
	

	// 增加身份证验证
	function isIdCardNo(num) {
		num = num.toUpperCase(); //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。        
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
			return false;
		} //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
		//下面分别分析出生日期和校验位 
		var len, re;
		len = num.length;
		if (len == 15) {
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re); //检查生日日期是否正确
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3]
					+ '/' + arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				return false;
			} else { //将15位身份证转成18位 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。        
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
						5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5',
						'4', '3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return true;
			}
		}
		if (len == 18) {
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re); //检查生日日期是否正确 
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"
					+ arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				return false;
			} else { //检验18位身份证的校验码是否正确。 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
						5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5',
						'4', '3', '2');
				var nTemp = 0, i;
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1)) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	function clearcarderrorMsg(){
		$("#carderrorMsg").html("");
	}
	function cleartelerrorMsg(){
		$("#telerrorMsg").html("");
	}
</script>
</head>

<body>
<div class="container">
		<div class="main">
			<p class="reg_intro">如果您尚未开通新东方集团邮箱账号，请按照如下步骤注册临时账号。</p>
			<ul class="reg_steps clearfix" id="reg_steps">
				<li><a href="#"><i class="icon-reg-step01 pass"></i>
					<h4>创建账号</h4></a></li>
				<li><div class="line"></div></li>
				<li><a href="#"><i class="icon-reg-step02"></i>
					<h4>填写资料</h4></a></li>
				<li><div class="line"></div></li>
				<li><a href="#"><i class="icon-reg-step03"></i>
					<h4>完成注册</h4></a></li>
			</ul>
			<form action="#" class="reg_form form-horizontal" id="form-step01">
				<p class="reg_form-intro">请填写您的个人邮箱及密码，然后点击进入下一步。</p>

				<div class="control-group">
					<label for="user" class="control-label">个人邮箱<span
						class="text-error">*</span></label>
					<div class="controls">
						<input id="user" onchange="checkemail(this.value)" class="span4"
							type="text" value="" placeholder="请填写您的个人邮箱作为临时账号" /> <br /> <span
							id="usererrorMsg" class="help-block text-warning"></span>
					</div>

				</div>

				<div class="control-group">
					<label for="password" class="control-label">登录密码<span
						class="text-error">*</span></label>
					<div class="controls">
						<input id="password" onchange="checkpass1(this.value,'')"
							class="span4" type="password" value="" placeholder="请填写你的密码" />
						<br /> <span id="userpasserrorMsg1" class="help-block text-warning"></span>
					</div>
				</div>
				<div class="control-group">
					<label for="confirmPwd" class="control-label">确认密码<span
						class="text-error">*</span></label>
					<div class="controls">
						<input id="confirmPwd" class="span4" type="password"
							onchange="checkpass2('','')" value="" placeholder="请确认您的密码" /> <br />
						<span id="userpasserrorMsg" class="help-block text-warning"></span>
					</div>
				</div>

				<div class="control-group">
					<div class="controls">
						<button type="button" class="submit btn btn-primary btn-large"
							id="submit_step01">填写完毕，进入下一步</button>
					</div>
				</div>
			</form>
			<form action="#" class="reg_form form-horizontal hide"
				id="form-step02">
				<p class="reg_form-intro">请确认您填写的个人资料，以便大家更加了解您。</p>
				<div class="control-group">
					<label for="face" class="control-label">头像</label>
					<div class="controls">
					 <table >
        		     <tr>
        		      <td><a class="face pull-left">
                         <img id="imgshow" style="width: 70px; height: 70px" src="${ctx}/resources/images/default-face-70.png"/> <h6></h6>
                         </a>
                         <div class="pull-left support-img">请在成功登陆后，在账号设置中修改您的头像</div>
                      </td>
                    </tr>
                    <tr>
                    <td>
                     <!--<div style="position: relative;top:-20px;">
                 	 <tags:simpleupload filename="fdName"
                         filevalue="" id="registerPic" exts="*.jpg;*.JPEG;*.png;*.bmp;" 
                         imgshow="imgshow" attIdName="attId" attIdID="attIdID">
    			      </tags:simpleupload> 
                     </div> 
                       
                       <div class="cutimg-box no" style="display:none;">
                           <iframe id="iframeimg" width="600" height="500" id="win" name="win" frameborder="0" scrolling="no"
                                   src=""></iframe>
                       </div> -->
                   </td>
                  </tr>
                 </table>
                    <input type="hidden" id="fdIcoUrl" name="fdIcoUrl" value="" />
                    
				</div>
				</div>
				<div class="control-group">
					<label for="name" class="control-label">姓名<span
						class="text-error">*</span></label>
					<div class="controls">
						<input id="name" type="text" onchange="checkname(this.value)"
							class="span4" value="" placeholder="请输入您的姓名"> <br /> <span
							id="nameerrorMsg" class="help-block text-warning"></span>
					</div>
				</div>
				<div class="control-group">
					<label for="org" class="control-label">机构/部门 <span
						class="text-error">*</span></label>
					<div class="controls">
						<select name="org" id="org" onchange="changedepart(this)">
							<option value="">请输入您的机构</option>
							<c:forEach items="${elements }" var="e">
								<option value="${e.fdId }">${e.fdName }</option>
							</c:forEach>
						</select> <select name="department" id="department"
							onchange="checkdepart(this.value)">
							<option value='0'>请输入您的部门</option>
						</select> <br /> <span id="departerrorMsg" class="help-block text-warning"></span>
					</div>
				</div>
				<div class="control-group">
					<label for="ID" class="control-label">证件号码</label>
					<div class="controls">
						<input id="ID" type="text" class="span4" value="" onchange="clearcarderrorMsg();"
							placeholder="请输入您的证件号码"> <br /> <span id="carderrorMsg"
							class="help-block text-warning"></span>
					</div>
				</div>
				<div class="control-group">
					<label for="tel" class="control-label">电话号码</label>
					<div class="controls">
						<input id="tel" type="text" class="span4" value="" onchange="cleartelerrorMsg()" placeholder="请输入您的电话号码"> <br />
						<span id="telerrorMsg" class="help-block text-warning"></span>
					</div>
				</div>
				<div class="control-group">
					<label for="sex" class="control-label">性别<span
						class="text-error">*</span></label>
					<div class="controls">
						<label for="male" class="radio inline"><input name="sex"
							id="male" type="radio" value="m">男</label> <label
							for="female" class="radio inline"><input name="sex"
							id="female" type="radio" checked value="f">女</label>
					</div>
				</div>
				<div class="control-group">
					<label for="birthday" class="control-label">出生日期<span
						class="text-error">*</span></label>
					<div class="controls">
						<div class="input-append date" id="dpYear" data-date="1986/01/10"
							data-date-format="yyyy/mm/dd">
							<input id="birthday" type="text" class="span4" value="1986/01/10"
								placeholder="请输入您的出生日期 " readonly /> <span class="add-on"><i
								class="icon-th"></i></span>
						</div>

					</div>
				</div>
				<div class="control-group">
					<label for="blood" class="control-label">血型<span
						class="text-error">*</span></label>
					<div class="controls">
						<label for="a" class="radio inline"><input name="blood"
							id="a" type="radio" value="A"> A</label> <label for="b"
							class="radio inline"><input name="blood" id="b"
							type="radio" value="B"> B</label> <label for="ab"
							class="radio inline"><input name="blood" id="ab"
							type="radio" value="AB"> AB</label> <label for="o"
							class="radio inline"><input name="blood" id="o"
							type="radio" value="O"> O</label> <label for="rh"
							class="radio inline"><input name="blood" id="rh"
							type="radio" value="RH"> RH</label> <label for="bld-other"
							class="radio inline"><input name="blood" id="bld-other"
							type="radio" checked value="OTHER">不详 </label>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<button type="button" class="submit btn btn-primary btn-large"
							id="submit_step02">填写完毕，进入下一步</button>
					</div>
				</div>
			</form>
			<form action="#" class="reg_form form-horizontal hide"
				id="form-step03">
				<p class="reg_form-intro">请确认您填写的个人资料，完成临时账号注册。</p>
				<div class="control-group">
					<label class="control-label">头像</label>
					<div class="controls">
						<img id="imgend" src="${ctx}/resources/images/default-face-180.png" complete="complete" alt="" class="face">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">个人邮箱<span class="text-error">*</span></label>
					<div class="controls">
						<span class="inp-placeholder" id="emailend"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">姓名<span class="text-error">*</span></label>
					<div class="controls">
						<span class="inp-placeholder" id="nameend"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构/部门 <span class="text-error">*</span></label>
					<div class="controls">
						<span class="inp-placeholder" id="departend"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">证件号码</label>
					<div class="controls">
						<span class="inp-placeholder" id="idend"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">电话</label>
					<div class="controls">
						<span class="inp-placeholder" id="telend"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">性别<span class="text-error">*</span></label>
					<div class="controls">
						<span class="inp-placeholder" id="sexend"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">出生日期</label>
					<div class="controls">
						<span class="inp-placeholder" id="birthdayend"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">血型</label>
					<div class="controls">
						<span class="inp-placeholder" id="bloodend"></span>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<button type="button" class="submit btn btn-primary btn-large"
							onClick="register()">确认完毕，完成注册</button>
					</div>
				</div>
			</form>
		</div>
	</div>

<script type="text/javascript" src="${ctx}/resources/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
$(function(){
	var datepicker = $("#dpYear").datepicker()
		.on('changeDate', function(ev){
			datepicker.datepicker("hide");
		});
	var $regSteps = $("#reg_steps>li>a");
	var $lines = $("#reg_steps>li>.line");
	$("#submit_step01").bind("click",function(){
		var email = document.getElementById("user").value;
		checkemail(email);
		var canGo1=(document.getElementById("usererrorMsg").innerHTML=="");
		var p1 = document.getElementById("password").value;
		var p2 = document.getElementById("confirmPwd").value;
		var canGo2 = checkpass1(p1, p2);
		var canGo3 = checkpass2(p1, p2);
		if (canGo1 == false || canGo2 == false || canGo3==false) {
			return;
		}else{
			$regSteps.eq(1).children("i").add($lines.eq(0)).addClass("pass");
			$("#form-step02").removeClass("hide").siblings("form").addClass("hide");	
		}
		
	});
	$("#submit_step02").bind("click",function(){
		var id = document.getElementById("ID").value;

		var name = document.getElementById("name").value;

		var depart = document
				.getElementById("department");
		var index = depart.selectedIndex;
		var departsele = depart.options[index].value;

		var tel = document.getElementById("tel").value;

		var goto2 = checkname(name);
		if(!(id==""||id=="请输入您的证件号码")){
			checkIdentityCard(id);
		}
		var goto1 =(document.getElementById("carderrorMsg").innerHTML==""); 
		var goto3 = checkdepart(departsele);
		var goto4 = true;
		if(!(tel==""||tel=="请输入您的电话号码")){
			goto4 = checktel(tel);
		}
		if (goto1 == false || goto2 == false
				|| goto3 == false || goto4 == false) {
			return;
		} else {
			//设置email
			document.getElementById("emailend").innerHTML = document
					.getElementById("user").value;
			//设置姓名
			document.getElementById("nameend").innerHTML = document
					.getElementById("name").value;
			//设置证件号
			var cardId_ = document.getElementById("ID").value;
			if(cardId_=="请输入您的证件号码"){
				cardId_="";
			}
			document.getElementById("idend").innerHTML = cardId_;
			//设置机构部门
			var jg = document.getElementById("org");
			var jgindex = jg.selectedIndex;
			var jgname = jg.options[jgindex].text;
			var depart = document
					.getElementById("department");
			var index = depart.selectedIndex;
			var departid = depart.options[index].value;
			var departname = depart.options[index].text;
			document.getElementById("departend").innerHTML = jgname
					+ "&nbsp;/&nbsp;" + departname;
			//设置电话
			var tel_ = document.getElementById("tel").value;
			if(tel_=="请输入您的电话号码"){
				tel_="";
			}
			document.getElementById("telend").innerHTML = tel_;
			//设置性别
			var sex = "";
			var temp = document
					.getElementsByName("sex");
			for ( var i = 0; i < temp.length; i++) {
				if (temp[i].checked) {
					if (temp[i].value == "f") {
						sex = "女";
					} else {
						sex = "男";
					}
				}
			}
			document.getElementById("sexend").innerHTML = sex;
			//设置出生日期
			document.getElementById("birthdayend").innerHTML = document
					.getElementById("birthday").value;
			//设置血性
			var bloodend = "";
			var temp1 = document
					.getElementsByName("blood");
			for ( var i = 0; i < temp1.length; i++) {
				if (temp1[i].checked) {
					if (temp1[i].value == "OTHER") {
						bloodend = "不详";
					} else {
						bloodend = temp1[i].value;
					}
				}
			}
			document.getElementById("bloodend").innerHTML = bloodend;
			$(window).scrollTop(80);
			$regSteps.eq(2).children("i").add(
					$lines.eq(1)).addClass("pass");
			$("#form-step03").removeClass("hide")
					.siblings("form").addClass("hide");	
		}
	});
	$regSteps.each(function(i){
		$(this).bind("click",function(e){
			e.preventDefault();
			if($(this).find("i").hasClass("pass")){console.log(i);
				$regSteps.slice(i+1).children("i").add($lines.slice(i)).removeClass("pass");
				$("#form-step0" + (i+1)).removeClass("hide").siblings("form").addClass("hide");
			}
		});
	});
	$.Placeholder.init();
});

</script>
</body>
</html>
