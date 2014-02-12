
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<j:set name="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/resources/css/global.min.css?id=12"/>
    <link rel="stylesheet" href="${ctx}/resources/css/admin-default.css?id=12"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css"/>
    <script src="${ctx}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=1211"></script>
</head>
<body>
<form class="form-horizontal" id="inputForm" method="post" action="${ctx}/demo/unit/update" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}"/>
    名称：   <input type="text" id="fdName" name="fdName" value="${bean.fdName}"/>
    <br/> s

    备注：    <input type="text" name="fdContext" value="${bean.fdContext}">
    <br/>
    <tags:simpleupload filename="fdName"
                       filevalue="" id="upMovie" exts="*.jpg;" attIdName="attIds[0].fdId" attIdValue="${attIds[0].fdId}" attIdID="attIdID">
    </tags:simpleupload>
    <br/>
    <input type="submit" value="确定"/>
</form>
<script src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>