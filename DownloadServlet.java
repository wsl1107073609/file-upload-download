package com.simpleWeb.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//获得文件名
		String filename = request.getParameter("name");
		filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
		
		//获取存储文件的目录，filename的文件可能在该目录下的某一子目录中
		String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
		
		//通过文件名和存储文件的目录找到文件的所在目录
		String path = findFileSavePathByFileName(filename,fileSaveRootPath);
		
		//得到要下载的文件
		File file = new File(path + "\\" + filename);
		//如果文件不存在
		if(!file.exists()){
			request.getSession().setAttribute("message", "您要下载的资源已被删除！！");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//处理文件名
		String realname = filename.substring(filename.indexOf("_")+1);
		//设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(path + "\\" + filename);
		//创建输出流
		OutputStream out = response.getOutputStream();
		//创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		//循环将输入流中的内容读取到缓冲区当中
		while((len=in.read(buffer))>0){
		//输出缓冲区的内容到浏览器，实现文件下载
		out.write(buffer, 0, len);
		}
		//关闭文件输入流
		in.close();
		//关闭输出流
		out.close();
		
	}

	/*
	 * @method:通过文件名和上传文件保存的根目录寻找文件的地址
	 * @return：要下载的文件的存储目录
	 * 
	 * */
	
	public String findFileSavePathByFileName(String filename, String rootPath) {
		// TODO Auto-generated method stub
		int hashcode = filename.hashCode();  //
		int dir1 = hashcode&0xf;  //
		String dir = rootPath + "\\" + dir1;		
		/*
		int dir2 = hashcode&0xf0 >> 4;  //
		String dir = rootPath + "\\" + dir1 + "\\" + dir2;
		File file = new File(dir);
		if(!file.exists()){
		//创建目录
		file.mkdirs();
		}
		*/
		return dir;
	}

}
