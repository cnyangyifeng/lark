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
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery.autocomplete.min.css">
<!--[if lt IE 9]>
<script src="${ctx}/resources/js/html5.js"></script>
<![endif]-->
</head>

<body>
	        <div class="page-header">
                <a href="${ctx}/admin/group/list" class="backParent">
                <span id="back">返回群组列表</span>
               </a>
                <h4>${bean.fdName}</h4>
	        </div>
            <div class="page-body editingBody">
                    <input type="hidden" id="roleName" name="roleName" >
                    <section class="section">
                        <div class="tab-content">
                             <div class="tab-pane active" id="encrypt">
                                <table class="table table-bordered">
                                    <tbody id="list_user">
                                    	<tr data-fdid="${bean.fdId}">
									        <td style="text-align: left;">
									          <j:iter items="${res}" var="bean" status="vstatus">
									          	${bean.fdName}；
									          	</j:iter>
									         </td>
									    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </section>
            </div>
<script type="text/javascript" src="${ctx}/resources/js/jquery.placeholder.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.sortable.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1211"></script>
<script src="${ctx}/resources/js/jquery.jalert.min.js" type="text/javascript"></script>


<script type="text/javascript">
$(function(){
	$("#rightCont .bder2").addClass("hide");
});

</script>
</body>
</html>
