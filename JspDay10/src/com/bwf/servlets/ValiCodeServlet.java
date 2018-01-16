package com.bwf.servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 该Servlet用于向客户端输出图片
 * */
@WebServlet("/valicode")
public class ValiCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final int WIDTH=160;  //图片宽度
	private static final int HEIGHT=50;  //图片宽度
	private static final int IMAGETYPE=BufferedImage.TYPE_INT_RGB;  //图片类型
	
	private static final String VALICODESTR="QAZqazwsxWSX23EDedcCRFVTGB4rfv56YHtgbNUJtgbM7yhn89ujmKLikpP";
	
    public ValiCodeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * @Param width  图片的宽度
		 * @Param height 图片的高度
		 * @Param imageType 图片类型
		 * */
		BufferedImage image=new BufferedImage(WIDTH, HEIGHT, IMAGETYPE);
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		g.setColor(getRandomColor(false));
		
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		int xBegin=30;
		
		
		StringBuffer sb=new StringBuffer();
		
		float theta=0.5f;
		
		for(int i=0;i<4;i++){
			g.setColor(getRandomColor(true));
			String s=getRandomChar();
			sb.append(s);
			g.setFont(new Font("宋体", Font.PLAIN, 40));
			
			//设置画笔的旋转角度
			if((i&1)==0){
				g.rotate(-theta,xBegin,40);
			}else{
				g.rotate(theta,xBegin,40);
			}
			
			g.drawString(s, xBegin, 40);
			
			//把画笔再旋转回去
			if((i&1)==0){
				g.rotate(theta,xBegin,40);
			}else{
				g.rotate(-theta,xBegin,40);
			}
			xBegin+=30;
		}
		
		request.getSession().setAttribute("realValiCode", sb.toString());
		
		/*
		 * 绘制20条干扰线
		 * */
		Random rand=new Random();
		for(int i=0;i<200;i++){
			g.setColor(getRandomColor(true));
			int x1=rand.nextInt(WIDTH);
			//int x2=rand.nextInt(WIDTH);
			int y1=rand.nextInt(HEIGHT);
			//int y2=rand.nextInt(HEIGHT);
			g.drawLine(x1, y1, x1+1, y1+1);
		}
		
		//表示向客户端输出的是一张图片
		response.setContentType("IMAGE/JPEG");
		
		//这里有可能出一个面试题。
		//服务端要告诉客户端不要缓存验证码图片
		//本质上是禁止客户端缓存服务端响应的内容
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		
		ServletOutputStream out = response.getOutputStream();
		
		ImageIO.write(image, "jpg", out);
		
		out.flush();
		out.close();
		
	}
	
	/**
	 * 随机获取一个数字
	 * */
	public String getRandomNum(){
		Random rand=new Random();
		int num=rand.nextInt(10);
		return num+"";
	}
	
	
	public String getRandomChar(){
		Random rand=new Random();
		int beginIndex=rand.nextInt(VALICODESTR.length()-1);
		String str=VALICODESTR.substring(beginIndex, beginIndex+1);
		return str;
	}
	
	
	/**
	 * RGB(255,255,255)  白色
	 * RGB(0,0,0,) 黑色
	 * 
	 * 值越大  颜色越浅
	 * 值越小 颜色越深
	 * 
	 * 三原色  Red  Green Blue
	 * 什么情况下，颜色比较浅?
	 * 什么情况下，颜色比较深?
	 * */
	public Color getRandomColor(boolean isdeep){
		
		//在该函数中需要随机生成RED  GREEN BLUE对应的三个值
		
		/*
		 * @Param r  RED的值  0-255
		 * @Param g  GREEN的值  0-255
		 * @Param b  BLUE的值  0-255
		 * */
		
		Random rand=new Random();
		
		//默认颜色为白色
		int r=255;
		int g=255;
		int b=255;
		
		if(isdeep){
			r=rand.nextInt(150);
			g=rand.nextInt(150);
			b=rand.nextInt(150);
		}else{
			r=150+rand.nextInt(100);
			g=150+rand.nextInt(100);
			b=150+rand.nextInt(100);
		}
		
		Color color=new Color(r, g, b);
		
		return color;
	}

	
}
