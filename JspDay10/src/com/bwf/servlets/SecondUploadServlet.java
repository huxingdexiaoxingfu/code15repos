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
@WebServlet("/secondupload")
@MultipartConfig
public class SecondUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public SecondUploadServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//ֱ��ʹ��getParameter��ȡ��File�������е�ֵ
		String str=request.getParameter("username");
		System.out.println("username:"+str);
		String savePath=this.getServletContext().getRealPath("/upload");
		//���ļ�����
		Collection<Part> parts = request.getParts();
		Iterator<Part> iterator = parts.iterator();
		while(iterator.hasNext()){
			Part part=iterator.next();
			String fileName=part.getSubmittedFileName();
			//��File�������fileName����null  �����ǲ���д�������ϵ�
			if(fileName!=null){
				part.write(savePath+"/"+fileName);
			}
		}
		
		response.setContentType("text/html");
		response.getWriter().write("<h1>UPLOAD SUCCESS</h1>");
		
	}
	
}
