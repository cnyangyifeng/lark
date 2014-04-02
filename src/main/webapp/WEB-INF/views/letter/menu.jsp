<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<ul class="nav nav-list sidenav" id="sideNav">
      <li class="nav-header first">
             <span>私信<b class="caret"></b></span>
      </li>
      <tags:shirourl url="${ctx}/letter/findLetterList" active="letterList" text="我的私信" iconName="icon-msg"  para="${active}"></tags:shirourl>
      <tags:shirourl url="${ctx}/letter/sendLetter" active="sendLetter" text="发私信" iconName="icon-msg-edit"  para="${active}"></tags:shirourl>
      <j:ifelse test="${active eq 'sendEmail'}">
        <j:then>
         <li class="nav-header active"><a href="${ctx}/letter/sendEmail">发邮件 <b class="caret"></b></a></li>
        </j:then>
        <j:else>
         <li class="nav-header"><a href="${ctx}/letter/sendEmail">发邮件 <b class="caret"></b></a></li>
        </j:else>
      </j:ifelse>
</ul>