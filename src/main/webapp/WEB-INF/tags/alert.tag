<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="alertId" type="java.lang.String" required="true"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="function" type="java.lang.String"%>
<%@ attribute name="href" type="java.lang.String"%>
<%@ attribute name="tagIndex" type="java.lang.String"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/formtag.tld"%>
<div id="${alertId}" class="modal hide fade" tabindex="${tagIndex}" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">x</button>
    <h5 id="${alertId }-label">删除${name}</h5>
  </div>
  <div class="modal-body">
    <p id="body-msg">您确定要删除该${name}吗？</p>
  </div>
  <div class="modal-footer">
    <j:if test="${href != null}">
      <a class="btn btn-info" href="${href}">确定</a>
    </j:if>
    <j:if test="${href == null}">
      <j:if test="${function != null}">
        <script type="text/javascript">
			function callMethod(method){
				var m = method();
				if( m == true) {
					return true;
				}
				$("#body-msg").html("<font color='red'>" + m + "</font>");
			}
		</script>
        <button onclick="callMethod(${function})" class="btn btn-info">确定</button>
      </j:if>
      <j:if test="${function == null}">
        <button class="btn btn-info">确定</button>
      </j:if>
    </j:if>
    <button class="btn" data-dismiss="modal">取消</button>
  </div>
</div>