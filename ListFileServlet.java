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
		
		//��ȡ�ϴ��ļ���Ŀ¼
		String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		//����map�������洢Ҫ���ص��ļ���
		Map<String,String> fileNameMap = new HashMap<String,String>();
		//�ݹ����filepathĿ¼�µ������ļ���Ŀ¼�����ļ����ļ����洢��map������
		listfile(new File(uploadFilePath),fileNameMap);
		//��Map���Ϸ��͵�listfile.jspҳ�������ʾ
		request.getSession().setAttribute("fileNameMap", fileNameMap);
		request.getRequestDispatcher("/listfile.jsp").forward(request, response);
				
	}

	/*
	 * @method:listfile
	 * @description:�ݹ����ָ��Ŀ¼�µ������ļ�
	 * @param file ������һ���ļ���Ҳ����һ���ļ�Ŀ¼
     * @param map �洢�ļ�����Map����
	 * */	
	public void listfile(File file, Map<String, String> map) {
		// TODO Auto-generated method stub
		
		//isfile()�ж��Ƿ����ļ�������Ƿ���true������false��˵����Ŀ¼
		if(!file.isFile()){
			//�г���Ŀ¼�µ������ļ���Ŀ¼
			File files[] = file.listFiles();
			//��������
			for(File f:files){
				listfile(f,map);
			}			
		}
		else{
			//file.getName().indexOf("_")�����ַ����е�һ�γ���_��λ�ã���ʱrealname��Ϊ�ļ������֣�Ȼ��������Ψһ��
			String realName = file.getName().substring(file.getName().indexOf("_")+1);
			//file.getName()�õ������ļ���ԭʼ���ƣ����������Ψһ�ģ���˿�����Ϊkey��realName�Ǵ����������ƣ��п��ܻ��ظ�
			map.put(file.getName(), realName);
		}			
	}

	
	
	
	
	
	
}
