package com.simpleWeb.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListFileServlet
 */
public class ListFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListFileServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//获取上传文件的目录
		String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		//定义map集合来存储要下载的文件名
		Map<String,String> fileNameMap = new HashMap<String,String>();
		//递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
		listfile(new File(uploadFilePath),fileNameMap);
		//将Map集合发送到listfile.jsp页面进行显示
		request.getSession().setAttribute("fileNameMap", fileNameMap);
		request.getRequestDispatcher("/listfile.jsp").forward(request, response);
				
	}

	/*
	 * @method:listfile
	 * @description:递归遍历指定目录下的所有文件
	 * @param file 即代表一个文件，也代表一个文件目录
     * @param map 存储文件名的Map集合
	 * */	
	public void listfile(File file, Map<String, String> map) {
		// TODO Auto-generated method stub
		
		//isfile()判断是否是文件，如果是返回true，否则false，说明是目录
		if(!file.isFile()){
			//列出该目录下的所有文件和目录
			File files[] = file.listFiles();
			//遍历数组
			for(File f:files){
				listfile(f,map);
			}			
		}
		else{
			//file.getName().indexOf("_")检索字符串中第一次出现_的位置，此时realname即为文件的名字，然而并不是唯一的
			String realName = file.getName().substring(file.getName().indexOf("_")+1);
			//file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
			map.put(file.getName(), realName);
		}			
	}

	
	
	
	
	
	
}
