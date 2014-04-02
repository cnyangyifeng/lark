<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
	 <section class="section box-control">
					     <div class="hd">
						<div class="btn-toolbar">
							<a class="btn btn-primary" style="padding-left: 10px;padding-right: 10px;" href="${ctx}/series/add">发布新系列</a>
							<div class="btn-group">
								<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
									操作 <span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<!-- <li><a href="#rightCont">导出列表</a></li>
									<li><a href="#rightCont">打包下载</a></li> -->
									<li><a href="#" onclick="confirmDel();">批量删除</a></li>
								</ul>
							</div>
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
							   <c:if test="${param.order=='fdName'}">
								<button class="btn btn-large active" type="button" onclick="findeCoursesByKey('1','fdName')">名称</button>
							   </c:if>
							   <c:if test="${param.order!='fdName'}">
								<button class="btn btn-large" type="button" onclick="findeCoursesByKey('1','fdName')">名称</button>
							   </c:if>
							</div>
							<label class="checkbox inline" for="selectCurrPage">
							   <input type="checkbox" id="selectCurrPage" name="selectCheckbox" onclick="checkcurrpage()" value="0"/>选中本页</label>
							<label class="checkbox inline" for="selectAll">
				  			 <input type="checkbox" id="selectAll" name="selectCheckbox"  onclick="selectAll()" value="1"/>选中全部</label>
								
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
			
				<li><a href="${ctx}/series/pagefoward?seriesId=${bean.FDID}"> 
				<input type="checkbox" name="ids" value="${bean.FDID}"/>
				    <span class="title">
					    <c:if test="${bean.FDNAME!=null && bean.FDNAME!=''}">
							<tags:title size="33" value="${bean.FDNAME}"></tags:title>
						</c:if>
						<c:if test="${bean.FDNAME==null || bean.FDNAME==''}">
							未命名
						</c:if>
					</span> 
					<c:if test="${bean.ISPUBLISH=='N'}">
				         <i class="icon-unpublished" data-toggle="tooltip" data-original-title="未发布"></i>
				      </c:if>
				    <span class="date">
				    	<i class="icon-time"></i>
				    	<fmt:formatDate value="${bean.FDCREATETIME}" pattern="yyyy/MM/dd hh:mm aa"/>
				    	<span class="dt">发布者</span><em>${bean.CREATORNAME}</em>
				    </span>
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
					<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
			            <c:choose>
			                <c:when test="${page.pageNo == i}">
			                    <button class="btn btn-primary btn-num active" type="button" >${i}</button>
			                </c:when>
			                <c:otherwise>
			                    <button class="btn btn-primary btn-num" type="button"  onclick="findeCoursesByKey('${i}','${param.order}')">${i}</button>
			                </c:otherwise>
			            </c:choose>
			          </c:forEach>
						<button class="btn btn-primary btn-num  dropdown-toggle"
							data-toggle="dropdown" type="button">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu pull-right">
						  <c:forEach var="i" begin="${page.startOperate}" end="${page.endOperate}"> 
                 			 <li><a onclick="pageNavClick('${param.fdType}','${i}','${param.order}')">
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
$(function(){
	$('[data-toggle="tooltip"]').tooltip();
});
</script>