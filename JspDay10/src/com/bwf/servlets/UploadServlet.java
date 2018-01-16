package com.bwf.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 * ��Servlet���ڴ����ļ�����
 * */
@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UploadServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		Part part=request.getPart("aFile");
		
		//���ﷵ�صĲ����ļ�����  ����File��ǩԪ�ص�name����
		System.out.println(">>>:"+part.getName());
		
		//��ȡ�ϴ����ļ���  ����ļ����������ļ�����  ����׺
		System.out.println("***:"+part.getSubmittedFileName());
		
		System.out.println("contentType:"+part.getContentType());
		Collection<String> headerNames = part.getHeaderNames();
		Iterator<String> iterator = headerNames.iterator();
		while(iterator.hasNext()){
			String headerName=iterator.next();
			System.out.println(headerName+"\t"+part.getHeader(headerName));
		}
		
		String fileName=part.getSubmittedFileName();
		String savePath=this.getServletContext().getRealPath("/upload");
		
		/*InputStream inputStream = part.getInputStream();
		String savePath=this.getServletContext().getRealPath("/upload");
		FileOutputStream out=new FileOutputStream(new File(savePath+"/"+fileName));
		byte[] buffer=new byte[1024];
		int len=-1;
		while((len=inputStream.read(buffer))!=-1){
			out.write(buffer, 0, len);
		}
		out.flush();
		out.close();
		inputStream.close();*/
		
		//��part��ʶ���ļ�д��Ŀ���ļ���
		part.write(savePath+"/"+fileName);
		
		
		response.setContentType("text/html");
		response.getWriter().write("<h1>UPLOAD SUCCESS</h1>");
		
	}
	
}
