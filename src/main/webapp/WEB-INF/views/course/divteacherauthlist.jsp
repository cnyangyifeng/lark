<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
	 <section class="section box-control">
					     <div class="hd">
						<div class="btn-toolbar">
							<a class="btn btn-primary"  onclick="addAuth();">新增</a>
							<a class="btn btn-primary"  onclick="exportData();">导出列表</a>
							<form class="toolbar-search" onkeydown="pressEnter();">
								<input type="text" id="serach" class="search" placeholder="搜索课程"
								   onblur="" onkeydown="showSearch();" onkeyup="showSearch();" > 
								<i class="icon-search" onclick="findeCoursesByKey('1','${param.order}');"></i>
							</form>
							<span class="showState"> <span class="muted">当前显示：</span>
							 <span id="markshow">
							 	<a id="containkey"href="#">全部条目</a>
							 </span>
							 </span>
							
							 <a class="btn btn-link"   href="#" onclick="clearserach();" >清空搜索结果</a>
						</div>
					</div>
					<div class="bd">
						<div class="btn-toolbar">
							<label class="muted">排序</label>
							<div class="btn-group btns-radio" data-toggle="buttons-radio">
							   <c:if test="${param.order=='fdcreatetime'}">
								<button class="btn btn-large active" type="button" onclick="findeCoursesByKey('1','fdcreatetime')">时间</button>
							   </c:if>
							   <c:if test="${param.order!='fdcreatetime'}">
								<button class="btn btn-large" type="button" onclick="findeCoursesByKey('1','fdcreatetime')">时间</button>
							   </c:if>
							   <c:if test="${param.order=='fdname'}">
								<button class="btn btn-large active" type="button" onclick="findeCoursesByKey('1','fdname')">新教师</button>
							   </c:if>
							   <c:if test="${param.order!='fdname'}">
								<button class="btn btn-large" type="button" onclick="findeCoursesByKey('1','fdname')">新教师</button>
							   </c:if>
						      <c:if test="${param.order=='fddept'}">
								<button class="btn btn-large active" type="button" onclick="findeCoursesByKey('1','fddept')">部门</button>
							   </c:if>
							   <c:if test="${param.order!='fddept'}">
								<button class="btn btn-large" type="button" onclick="findeCoursesByKey('1','fddept')">部门</button>
							   </c:if>
							</div>
						<!-- 	<label class="checkbox inline" for="selectCurrPage">
							    <input type="checkbox" id="selectCurrPage" name="selectCheckbox" onclick="checkcurrpage()" value="0"/>选中本页</label>
							<label class="checkbox inline" for="selectAll">
				  			 <input type="checkbox" id="selectAll" name="selectCheckbox"  onclick="selectAll()" value="1"/>选中全部</label> -->

							<div class="pages pull-right">
								<div class="span2">
									第<span> 
									${page.startNum} - ${page.endNum}
								   </span> 
									 / <span>${page.totalCount}</span> 条 
								</div>
								<div class="btn-group">
			
									<c:if test="${page.pageNo <= 1}">
						<button class="btn btn-primary btn-ctrl" type="button" disabled>
							<i class="icon-chevron-left icon-white"></i>
						</button>
					</c:if>
					<c:if test="${page.pageNo > 1}">
						<a onclick="findeCoursesByKey('${page.prePage}','${param.order}')">
							<button class="btn btn-primary btn-ctrl" type="button">
								<i class="icon-chevron-left icon-white"></i>
							</button>
						</a>
					</c:if>
					<c:if test="${page.pageNo >= page.totalPage}">
						<button class="btn btn-primary btn-ctrl" type="button" disabled>
							<i class="icon-chevron-right icon-white"></i>
						</button>
					</c:if>
					<c:if test="${page.pageNo < page.totalPage}">
						<a onclick="findeCoursesByKey('${page.nextPage}','${param.order}')">
							<button class="btn btn-primary btn-ctrl" type="button">
								<i class="icon-chevron-right icon-white"></i>
							</button>
						</a>
					</c:if>
							</div>
							</div>
						</div>
					</div>
				</section>              
	<section class="section listWrap">
		<ul class="nav list">
			 <j:iter items="${page.list}" var="bean" status="vstatus">
				 <li><a href="${ctx}/course/getSingleCourseAuthInfo?teacherId=${bean.FDID}&fdType=14&order=createtime"><i class="icon-exam-num"></i>
				
				    <span class="title">
					${bean.FDNAME}（${bean.FDEMAIL}）&nbsp;&nbsp;${bean.DEPTNAME}
					（课程：${bean.COURSES}人，导师：${bean.TUTORS}人）
					</span> 
				    <span class="date"><i class="icon-time"></i><fmt:formatDate value="${bean.FDTIME}" pattern="yyyy/MM/dd hh:mm aa"/></span>
				</a></li>
			</j:iter> 
		</ul>
	</section>
 	<div class="pages">
					<div class="btn-group dropup">
					<c:if test="${page.firstPage==true}">
						<button class="btn btn-primary btn-ctrl" type="button" disabled>
							<i class="icon-chevron-left icon-white"></i>
						</button>
					</c:if>
					<c:if test="${page.firstPage==false}">
							<button class="btn btn-primary btn-ctrl" type="button" onclick="findeCoursesByKey('${page.prePage}','${param.order}')">
								<i class="icon-chevron-left icon-white"></i>
							</button>
					</c:if>
					<c:forEach var="i" begin="1" end="${page.totalPage}">
			            <c:choose>
			                <c:when test="${page.pageNo == i}">
			                    <button class="btn btn-primary btn-num active" type="button" >${i}</button>
			                </c:when>
			                <c:otherwise>
			                    <button class="btn btn-primary btn-num" type="button" onclick="findeCoursesByKey('${i}','${param.order}')">${i}</button>
			                </c:otherwise>
			            </c:choose>
			          </c:forEach>
						<button class="btn btn-primary btn-num  dropdown-toggle"
							data-toggle="dropdown" type="button">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu pull-right">
						  <c:forEach var="i" begin="1" end="${page.totalPage}">
							<li><a onclick="findeCoursesByKey('${i}','${param.order}')">
							${i*10-10+1} - ${i*10} 
							</a></li>
						</c:forEach>
						</ul>
			
					<c:if test="${page.lastPage==true}">
						<button class="btn btn-primary btn-ctrl" type="button" disabled>
							<i class="icon-chevron-right icon-white"></i>
						</button>
					</c:if>
					<c:if test="${page.lastPage!=true}">
							<button class="btn btn-primary btn-ctrl" type="button" onclick="findeCoursesByKey('${page.nextPage}','${param.order}')">
								<i class="icon-chevron-right icon-white"></i>
							</button>
					</c:if>
			
				</div>
			</div>               
<script type="text/javascript">
//导出列表   
function exportData(){
	var fdTitle = $("#serach").val();
	var order = $("#cachorder").val();
	jalert("您确定要导出全部数据吗？",function(){
		  window.location.href="${ctx}/common/exp/getExpAllTeacherAuth?order="+order+"&fdTitle="+fdTitle;
	}); 
}

//新增   
function addAuth(){
	window.location.href="${ctx}/course/getSingleCourseAuthInfo?teacherId=&fdType=14&order=createtime";
}
</script>
<script type="text/javascript">	
function pressEnter(){//回车事件
	if(event.keyCode==13){
		findeCoursesByKey(1,$('#cachorder').val());
	}
}
function clearserach(){//清理搜索栏并显示数据列表
	//alert('ss');
	$("#serach").attr("value","");
	$("#markshow").html('<a id="containkey"href="#">全部条目</a>');
	findeCoursesByKey(1,'fdcreatetime');
}

function showSearch(){
	var fdTitle = document.getElementById("serach").value;
	$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
	if(fdTitle==''){
		$("#markshow").html('<a id="containkey" href="#">全部条目</a>');
	}else if(fdTitle.length>2){
		$("#containkey").html(fdTitle.substr(0,2)+"...");
		}else{
			$("#containkey").html(fdTitle);
		}
}
function findeCoursesByKey(pageNo,order){
	var fdTitle = document.getElementById("serach").value;
	if($('input[name="selectCheckbox"]:checked').val()==1){
		$("#allkey").attr("value",1);
	}
	$('#cachorder').attr('value',order);
	$("#coursekey").attr("value",fdTitle);//关键字赋值
	$("#pageBody").html("");
	$.ajax({
		type: "post",
		 url: "${ctx}/ajax/course/getCoureAuthInfosOrByKey",
		data : {
			"fdTitle" : fdTitle,
			"pageNo" : pageNo,
			"order" : order,
			"fdType" : "${param.fdType}"
		},
		cache: false, 
		dataType: "html",
		success:function(data){
			//alert(data);
			var serachkey=$("#coursekey").val();
			$("#pageBody").html(data);
			if(fdTitle!=""&&fdTitle!=null){
				$("#markshow").html('含“<a id="containkey"href="#"></a>”的条目');
				if(fdTitle.length>2){
					$("#containkey").html(fdTitle.substr(0,2)+"...");
					}else{
						$("#containkey").html(fdTitle);
					}
			}
			else{
				$("#containkey").html('<a id="containkey"href="#">全部条目</a>');
				
			}
			
			$("#serach").attr("value",serachkey);
			if($("#allFlag").val()=='true'){
				document.getElementById("selectAll").checked=true;
				selectAll();
			}
		}
	}); 
}
</script>