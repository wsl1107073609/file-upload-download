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
		
		//����ļ���
		String filename = request.getParameter("name");
		filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
		
		//��ȡ�洢�ļ���Ŀ¼��filename���ļ������ڸ�Ŀ¼�µ�ĳһ��Ŀ¼��
		String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
		
		//ͨ���ļ����ʹ洢�ļ���Ŀ¼�ҵ��ļ�������Ŀ¼
		String path = findFileSavePathByFileName(filename,fileSaveRootPath);
		
		//�õ�Ҫ���ص��ļ�
		File file = new File(path + "\\" + filename);
		//����ļ�������
		if(!file.exists()){
			request.getSession().setAttribute("message", "��Ҫ���ص���Դ�ѱ�ɾ������");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//�����ļ���
		String realname = filename.substring(filename.indexOf("_")+1);
		//������Ӧͷ��������������ظ��ļ�
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		//��ȡҪ���ص��ļ������浽�ļ�������
		FileInputStream in = new FileInputStream(path + "\\" + filename);
		//���������
		OutputStream out = response.getOutputStream();
		//����������
		byte buffer[] = new byte[1024];
		int len = 0;
		//ѭ�����������е����ݶ�ȡ������������
		while((len=in.read(buffer))>0){
		//��������������ݵ��������ʵ���ļ�����
		out.write(buffer, 0, len);
		}
		//�ر��ļ�������
		in.close();
		//�ر������
		out.close();
		
	}

	/*
	 * @method:ͨ���ļ������ϴ��ļ�����ĸ�Ŀ¼Ѱ���ļ��ĵ�ַ
	 * @return��Ҫ���ص��ļ��Ĵ洢Ŀ¼
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
		//����Ŀ¼
		file.mkdirs();
		}
		*/
		return dir;
	}

}
