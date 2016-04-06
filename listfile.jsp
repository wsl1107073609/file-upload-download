<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Map" import="java.util.Iterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>listFile Page</title>
</head>
<body>

<%
Map<String,String> map = (Map)request.getSession().getAttribute("fileNameMap");
//遍历Map集合
Iterator it = map.entrySet().iterator();
while(it.hasNext()){
	Map.Entry entry = (Map.Entry)it.next();
	Object key = entry.getKey();
	Object value = entry.getValue();
%>
	<div>
	  <p><%=key%>&nbsp;&nbsp;<a href="DownloadServlet?name=<%=value%>">Download</a></p>
	</div>
<% 
}
%>


</body>
</html>