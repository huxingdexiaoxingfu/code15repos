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
 * ��Servlet������ͻ������ͼƬ
 * */
@WebServlet("/valicode")
public class ValiCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final int WIDTH=160;  //ͼƬ���
	private static final int HEIGHT=50;  //ͼƬ���
	private static final int IMAGETYPE=BufferedImage.TYPE_INT_RGB;  //ͼƬ����
	
	private static final String VALICODESTR="QAZqazwsxWSX23EDedcCRFVTGB4rfv56YHtgbNUJtgbM7yhn89ujmKLikpP";
	
    public ValiCodeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * @Param width  ͼƬ�Ŀ��
		 * @Param height ͼƬ�ĸ߶�
		 * @Param imageType ͼƬ����
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
			g.setFont(new Font("����", Font.PLAIN, 40));
			
			//���û��ʵ���ת�Ƕ�
			if((i&1)==0){
				g.rotate(-theta,xBegin,40);
			}else{
				g.rotate(theta,xBegin,40);
			}
			
			g.drawString(s, xBegin, 40);
			
			//�ѻ�������ת��ȥ
			if((i&1)==0){
				g.rotate(theta,xBegin,40);
			}else{
				g.rotate(-theta,xBegin,40);
			}
			xBegin+=30;
		}
		
		request.getSession().setAttribute("realValiCode", sb.toString());
		
		/*
		 * ����20��������
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
		
		//��ʾ��ͻ����������һ��ͼƬ
		response.setContentType("IMAGE/JPEG");
		
		//�����п��ܳ�һ�������⡣
		//�����Ҫ���߿ͻ��˲�Ҫ������֤��ͼƬ
		//�������ǽ�ֹ�ͻ��˻���������Ӧ������
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		
		ServletOutputStream out = response.getOutputStream();
		
		ImageIO.write(image, "jpg", out);
		
		out.flush();
		out.close();
		
	}
	
	/**
	 * �����ȡһ������
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
	 * RGB(255,255,255)  ��ɫ
	 * RGB(0,0,0,) ��ɫ
	 * 
	 * ֵԽ��  ��ɫԽǳ
	 * ֵԽС ��ɫԽ��
	 * 
	 * ��ԭɫ  Red  Green Blue
	 * ʲô����£���ɫ�Ƚ�ǳ?
	 * ʲô����£���ɫ�Ƚ���?
	 * */
	public Color getRandomColor(boolean isdeep){
		
		//�ڸú�������Ҫ�������RED  GREEN BLUE��Ӧ������ֵ
		
		/*
		 * @Param r  RED��ֵ  0-255
		 * @Param g  GREEN��ֵ  0-255
		 * @Param b  BLUE��ֵ  0-255
		 * */
		
		Random rand=new Random();
		
		//Ĭ����ɫΪ��ɫ
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
