<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%@ attribute name="size" type="java.lang.Integer" required="true"%>
<%
	String content = value;
	double index = 0;  
    StringBuffer sBuffer = new StringBuffer();  
    for (int i = 0; i < content.length(); i++) {  
        String retContent = content.substring(i, i + 1);  
        // 生成一个Pattern,同时编译一个正则表达式  
        boolean isChina = retContent.matches("[\u4E00-\u9FA5]");  
        boolean isCapital = retContent.matches("[A-Z]");  
        if (index == size) {  
            sBuffer.append("...");  
            break;  
        }  
        if (isChina) {  
            index = index + 1;  
        } else if(isCapital) {  
            index = index + 0.8;  
        } else{
        	index = index + 0.5;  
        } 
        sBuffer.append(retContent);  
        if (index > size) {  
            sBuffer.append("...");  
            break;  
        }  
    }  
    String title = sBuffer.toString();  
%>
<%= title%>