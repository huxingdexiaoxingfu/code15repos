package com.bwf.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 该Servlet用于处理文件上载
 * */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public DownloadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String fileName=request.getParameter("filename");
		
		System.out.println("fileName:"+fileName);
		
		String savePath=this.getServletContext().getRealPath("/image");
		
		FileInputStream in = new FileInputStream(new File(savePath+"/"+fileName));
		
		//解决中文乱码问题
		String fName=new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		
		//告诉客户端启用下载
		response.setHeader("content-disposition", "attachment;filename="+fName);
		
		ServletOutputStream out = response.getOutputStream();
		
		byte[] buffer=new byte[1024];
		int len=-1;
		while((len=in.read(buffer))!=-1){
			out.write(buffer, 0, len);
		}
		
		out.flush();
		out.close();
		in.close();
		
		
	}
	
}
