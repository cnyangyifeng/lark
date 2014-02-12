<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="cn.me.xdf.common.page.Pagination" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%@ attribute name="searchParams" type="java.lang.String" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int current = page.getPageNo();
	int begin = Math.max(1, current - paginationSize / 2);
	int end = Math.min(begin + (paginationSize - 1), page.getTotalPage());
	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
%>
<%
	if (page.getTotalPage() > 1) {
%>
<div class="pagination">
  <ul>
    <%
    	if (page.hasPreviousPage()) {
    %>
    <li><a href="?pageNo=1&sortType=${sortType}&${searchParams}">&lt;&lt;</a></li>
    <li><a href="?pageNo=${current-1}&sortType=${sortType}&${searchParams}">&lt;</a></li>
    <%
    	} else {
    %>
    <li class="disabled"><a href="#">&lt;&lt;</a></li>
    <li class="disabled"><a href="#">&lt;</a></li>
    <%
    	}
    %>
  
   <c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                    <li class="active"><a href="?pageNo=${i}&sortType=${sortType}&${searchParams}">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="?pageNo=${i}&sortType=${sortType}&${searchParams}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        
    <%
    	if (page.hasNextPage()) {
    %>
    <li><a href="?pageNo=${current+1}&sortType=${sortType}&${searchParams}">&gt;</a></li>
    <li><a href="?pageNo=${page.totalPage}&sortType=${sortType}&${searchParams}">&gt;&gt;</a></li>
    <%
    	} else {
    %>
    <li class="disabled"><a href="#">&gt;</a></li>
    <li class="disabled"><a href="#">&gt;&gt;</a></li>
    <%
    	}
    %>
  </ul>
</div>
<%
	}
%>