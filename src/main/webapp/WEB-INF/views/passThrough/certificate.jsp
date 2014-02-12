<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新东方在线教师备课平台</title>
    <style type="text/css">
        body{background-color: #efefef;font-size: 14px;line-height: 20px;}
        body,button{font-family: "Microsoft Yahei", "微软雅黑", arial;}
        body,div,p{padding: 0;margin: 0;}
        .muted{color: #999999;}
        .upper{text-transform: uppercase;}
        .icon-medal-lg{width: 60px;height: 60px;background:url("theme/default/images/icon-png.png") no-repeat 0 -821px;}
        .media, .media-body{overflow: hidden;zoom: 1;}
        .box-certificate{background-color:#ffffff;padding:0 70px;width: 800px;margin:-200px 0 0 -470px;overflow: hidden;height: 400px;position: absolute;top:50%;left: 50%;}
        .box-certificate .hd{overflow: hidden}
        .box-certificate .hd h2{font-weight: normal; font-size: 20px;text-align: center;margin: 55px 0 40px;}
        .box-certificate .bd .media{margin-left: 10px;}
        .box-certificate .bd .media > .pull-left{margin: 0 57px 20px 0;width:184px;float: left;position: relative}
        .box-certificate .bd .media > .pull-left i{position: absolute;right: -20px;bottom: -20px;}
        .box-certificate .bd .media-object{width: 156px;height: 156px;padding: 12px;border: solid #cccccc 1px;}
        .box-certificate .bd .media-body{font-size: 16px;}
        .box-certificate .bd .media-body p{line-height: 32px;}
        .box-certificate .bd .media-body p.muted{text-align: right;font-size: 14px;}
        .box-certificate .bd .media-body em{font-style: normal;color: #4391bb;}
        .box-certificate .bd .media-heading{border-bottom: dashed #b8b8b8 1px;margin-bottom:20px;padding: 10px 0 20px;}
    </style>
</head>

<body>
<div class="box-certificate">
    <div class="hd"><h2>新东方认证教师</h2></div>
    <div class="bd">
        <div class="media">
            <a class="pull-left" href="#">
                <img src="${imgUrl}" class="media-object img-polaroid" alt=""/>
                <i class="icon-medal-lg"></i>
            </a>
            <div class="media-body">
                <div class="media-heading"><em>${userName}</em>老师，</div>
                <p>已于${date} 完成《<em>${til}</em>》，特此认证。
                    This is to certify MR/MS <span class="upper">${eName}</span> 's successful completion of the New Oriental Teacher Online Training.</p>
                <p class="muted">颁发者：${dep}</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
