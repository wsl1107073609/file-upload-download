package com.simpleWeb.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/*
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
*/
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//common�İ�
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;




/**
 * Servlet implementation class FileUploadServlet
 */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
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
	public String findFileSavePathByFileName(String filename, String rootPath) {
		// TODO Auto-generated method stub
		int hashcode = filename.hashCode();  //
		int dir1 = hashcode&0xf;  //
		String dir = rootPath + "\\" + dir1;
		
		//int dir2 = hashcode&0xf0 >> 4;  //
		//String dir = rootPath + "\\" + dir1 + "\\" + dir2;
		
		File file = new File(dir);
		if(!file.exists()){
		//����Ŀ¼
		file.mkdirs();
		}
		return dir;
	}
	
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
						
		//�����ļ��ı���Ŀ¼
		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(savePath);
		//�жϱ���Ŀ¼�Ƿ����
		if(!file.exists() && !file.isDirectory()){
			System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
			file.mkdir();
		}
		//��Ϣ��ʾ
		String message = "";
		try{
			//ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
			//1������һ��DiskFileItemFactory����
			FileItemFactory factory = new DiskFileItemFactory();
			//2������һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			//����ϴ��ļ�������������
			upload.setHeaderEncoding("UTF-8"); 
			//3���ж��ύ�����������Ƿ����ϴ���������
			if(!ServletFileUpload.isMultipartContent(request)){
				//���մ�ͳ��ʽ��ȡ����
				return;
		    }
			//4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�
			//ÿһ��FileItem��Ӧһ��Form����������
			List<FileItem> list = upload.parseRequest(request);			
			for(FileItem item : list){
				//���fileitem�з�װ������ͨ�����������
				if(item.isFormField()){
					String name = item.getFieldName();
					//�����ͨ����������ݵ�������������
					String value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				}
				else{
					//���fileitem�з�װ�����ϴ��ļ�
					//�õ��ϴ����ļ����ƣ�
					String filename = item.getName();
					System.out.println(filename);
					if(filename==null || filename.trim().equals("")){
					    continue;
				    }
					//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺 c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					//�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					
					//Ϊ�ϴ����ļ��������Ӧ����Ŀ¼
					String path = findFileSavePathByFileName(filename,savePath);
					
					//��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					//����һ���ļ������
					FileOutputStream out = new FileOutputStream(path + "\\" + filename);
					//����һ��������
					byte buffer[] = new byte[1024];
					//�ж��������е������Ƿ��Ѿ�����ı�ʶ
					int len = 0;
					//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while((len=in.read(buffer))>0){
						//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
						out.write(buffer, 0, len);
					}
					//�ر�������
					in.close();
					//�ر������
					out.close();
					//ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					item.delete();
					message = "�ļ��ϴ��ɹ���";
				}
			  }
			}catch (Exception e) {
				message= "�ļ��ϴ�ʧ�ܣ�";
				e.printStackTrace();
				 
		    }
			request.getSession().setAttribute("message",message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		
	}
		
}
