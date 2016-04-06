<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload Page</title>
</head>
<body> 
     <form name="uploadForm" method="post" enctype="MULTIPART/FORM-DATA" action="FileUploadServlet">
        User Name:&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="username" size="30"/><br> 
        Upload File1:&nbsp;<input type="text" name="file1" size="30" id="uploadFile1"/>&nbsp;&nbsp;<input type="file" name="file1" size="30" onchange="document.getElementById('uploadFile1').value=this.value"/> <br> 
        Upload File2:&nbsp;<input type="text" name="file2" size="30" id="uploadFile2"/>&nbsp;&nbsp;<input type="file" name="file2" size="30" onchange="document.getElementById('uploadFile2').value=this.value"/> <br>   
        <input type="submit" name="submit" value="上传"> 
        <input type="reset" name="reset" value="重置"> 
      </form> 
      <div id="download">
        <a href="ListFileServlet">下载专区</a>      
      </div>
</body>
</html>