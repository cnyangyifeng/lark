package cn.me.xdf.utils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ＨＴＭＬ转换图片的方式
 * 
 * @author longgangbai
 * 
 */
public class GraphUtils {
 private final static Logger logger = LoggerFactory.getLogger(GraphUtils.class);
 public static int DEFAULT_IMAGE_WIDTH = 1024;
 public static int DEFAULT_IMAGE_HEIGHT = 1300;

 /**
  * 将ＢｕｆｆｅｒｅｄＩｍａｇｅ转换为图片的信息
  * 
  * @param image
  * @return
  */
 public static byte[] toJpeg(BufferedImage image) {
  // 获取图片文件的在服务器的路径
  //String imageName = "D:\\"+UUID.randomUUID().toString() + ".jpg";
  byte[] buff = null;
  try {
   ByteArrayOutputStream baos = new ByteArrayOutputStream();
   JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
   JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
   param.setQuality(1.0f, false);
   encoder.setJPEGEncodeParam(param);
   encoder.encode(image);
   buff = baos.toByteArray();
   baos.close();
   // 将字节流写入文件保存为图片
   //FileUtils.writeByteArrayToFile(new File(imageName), buff);
   //System.out.println("保存成功!....");
  } catch (Exception ex) {
   logger.error("保存删除图片失败:" + ex.getMessage());
  }
  return buff;
 }

 /**
  * html转换为ｊｐｅｇ文件
  * 
  * @param bgColor
  *            图片的背景色
  * @param html
  *            html的文本信息
  * @param width
  *            显示图片的Ｔｅｘｔ容器的宽度
  * @param height
  *            显示图片的Ｔｅｘｔ容器的高度
  * @param eb
  *            設置容器的边框
  * @return
  * @throws Exception
  */
 private static ArrayList<byte[]> html2jpeg(Color bgColor, String html,
   int width, int height, EmptyBorder eb) throws Exception {
  ArrayList<byte[]> ret = new ArrayList<byte[]>();
  try {
   JTextPane tp = new JTextPane();
   tp.setSize(width, height);
   if (eb == null) {
    eb = new EmptyBorder(0, 50, 0, 50);
   }
   if (bgColor != null) {
    tp.setBackground(bgColor);
   }
   if (width <= 0) {
    width = DEFAULT_IMAGE_WIDTH;
   }
   if (height <= 0) {
    height = DEFAULT_IMAGE_HEIGHT;
   }
   tp.setBorder(eb);
   tp.setContentType("text/html");
   tp.setText(html);
   PrintView m_printView = new PrintView(tp);
   int pageIndex = 1;
   boolean bcontinue = true;
   while (bcontinue) {
    BufferedImage image = new java.awt.image.BufferedImage(width,
      height, java.awt.image.BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    g.setClip(0, 0, width, height);
    bcontinue = m_printView.paintPage(g, height, pageIndex);
    g.dispose();
    byte[] imgBytes = toJpeg(image);
    ret.add(imgBytes);
    pageIndex++;
   }
  } catch (Exception ex) {
   throw ex;
  }
  return ret;
 }

 /**
  * 
  * @param bgColor
  * @param html
  * @param width
  * @param height
  * @return
  * @throws Exception
  */
 public static ArrayList<byte[]> toImages(Color bgColor, String[] htmls,
   int width, int height) throws Exception {
  ArrayList<byte[]> imglist = new ArrayList<byte[]>();
  for (int i = 0; i < htmls.length; i++) {
   imglist.addAll(html2jpeg(bgColor, htmls[i], width, height,
     new EmptyBorder(0, 0, 0, 0)));
  }
  return imglist;
 }

 /**
  * 
  * @param bgColor
  * @param html
  * @param width
  * @param height
  * @return
  * @throws Exception
  */
 public static ArrayList<byte[]> toImages(Color bgColor, String html,
   int width, int height) throws Exception {
  return html2jpeg(bgColor, html, width, height, new EmptyBorder(0, 0, 0,
    0));
 }

 /**
  * 将一個html转换为图片
  * 
  * @param htmls
  * @return
  * @throws Exception
  */
 public static ArrayList<byte[]> toImages(String html) throws Exception {
  return html2jpeg(null, html, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT,
    new EmptyBorder(0, 0, 0, 0));
 }

 /**
  * 将多个html转换图片
  * 
  * @param htmls
  * @return
  * @throws Exception
  */
 public static ArrayList<byte[]> toImages(String[] htmls) throws Exception {
  ArrayList<byte[]> imglist = new ArrayList<byte[]>();
  for (int i = 0; i < htmls.length; i++) {
   imglist.addAll(html2jpeg(null, htmls[i], DEFAULT_IMAGE_WIDTH,
		   DEFAULT_IMAGE_HEIGHT, new EmptyBorder(0, 0, 0, 0)));
  }
  return imglist;
 }



  
  /**
   * 将一個页面转换为html代码
   * 
   * @param htmls
   * @return
   * @throws Exception
   */
  public static String getWebCon(String domain) {
		// System.out.println("开始读取内容...("+domain+")");
		StringBuffer sb = new StringBuffer();
		try {
			java.net.URL url = new java.net.URL(domain);
			BufferedReader in = new BufferedReader(new InputStreamReader(url
					.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(URLEncoder.encode(line,"utf-8"));
			}
			in.close();
		} catch (Exception e) { // Report any errors that arise
			sb.append(e.toString());
			System.err.println(e);
			System.err
					.println("Usage:   java   HttpClient   <URL>   [<filename>]");
		}
		return sb.toString();
	}


  public static void main(String[] args) {
    try {
// FileReader fr = new FileReader("D:\\DEV\\o\\现金流量表.html");
// BufferedReader bfr = new BufferedReader(fr);
//      String htmlstr = "<p>/**<br />&nbsp;* <br />&nbsp;*/<br />package com.cxsoft.rap.ed.util;</p>";
//      String htmlstr = 	getWebCon("http://localhost:8090/lendCar/open.action?instProcessId=2c9886832dcbf7df012dd489f302001e&roleName=view");
    	String htmlstr = 	getWebCon("http://172.24.5.26/extcomponent/ui/skin/festival/login.jsp");
      String nextLine = null;
//      while ((nextLine = bfr.readLine()) != null) {
//        htmlstr += nextLine;
//      }
      System.out.println(htmlstr);
      GraphUtils.toImages(htmlstr);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}



