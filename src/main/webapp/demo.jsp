<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="cn.me.xdf.webservice.client.ad.ADApi" %>
<%@ page import="cn.me.xdf.webservice.client.ad.ADApiSoap" %>
<%@ page import="cn.me.xdf.webservice.client.ad.UserInfo" %>
<%@ page import="cn.me.xdf.webservice.client.ad.ExtensionDataObject" %>
<%--
  Created by IntelliJ IDEA.
  User: xiaobin268
  Date: 13-12-11
  Time: 上午9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
        <%
             try{
                 final QName SERVICE_NAME = new QName("http://tempuri.org/", "ADApi");
                 URL wsdlURL = ADApi.WSDL_LOCATION;
                 ADApi ss = new ADApi(wsdlURL, SERVICE_NAME);
                 ADApiSoap port = ss.getADApiSoap12();
                 StringBuilder sign = new StringBuilder();
                 UserInfo user = new UserInfo();
                 ExtensionDataObject _getUserThumbnailphoto_userInfoExtensionData = new ExtensionDataObject();

                 user.setExtensionData(_getUserThumbnailphoto_userInfoExtensionData);
             }  catch (Exception e){
                 e.printStackTrace();
             }
        %>
</body>
</html>