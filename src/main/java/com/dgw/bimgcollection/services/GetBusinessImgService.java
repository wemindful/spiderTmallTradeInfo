package com.dgw.bimgcollection.services;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;

import com.dgw.bimgcollection.utils.Base64Image2String;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author DGW-PC
 * @data   2018年7月22日下午1:35:32
 * <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 */
public class GetBusinessImgService {
	
	private static final Logger logger = LoggerFactory.getLogger(GetVerificationImg.class);
	/**
	 * 需要识别的数据
	 */
	 private static BufferedImage buffimg;

	public static void main(String[] args) {
		// return GenerateBImage();
	}
    
	/**
	 * 根据提取页面的地址拿到工商图片
	 * @param filepath 写入图片的路径 格式 Z:\\ +文件名  扩展名 png
	 * @param vcodeaddress  工商图片提取页的地址
	 * @return boolean 是否成功
	 */
	public  boolean GenerateBImage(String filepath,String vcodeaddress) {
		GetVerificationImg verificationImg = new GetVerificationImg();
		 //验证码表单
		 HtmlForm form = verificationImg.getVerfImgForm(vcodeaddress);
		 buffimg = verificationImg.getBuffimg();
		 
		 List<HtmlElement> list = form.getElementsByAttribute("input", "name", "checkCode");
		 HtmlInput inputCode=null;//输入的验证码
		 for (HtmlElement temp : list) {
				if(temp.getAttribute("name").equals("checkCode")) {
					logger.info("获取验证码输入text标签");
					inputCode=(HtmlInput) temp;
				}
		}
	    HtmlButton submit=null;
		DomNodeList<HtmlElement> btnList = form.getElementsByTagName("button");
		for (HtmlElement temp : btnList) {
			if(temp.getAttribute("class").equals("short-btn")) {
				logger.info("获取验证码的提交按钮");
				submit=(HtmlButton) temp;
			}
		}
		String code = new DistinguishVerCode().getProbableCode(buffimg);
		inputCode.setValueAttribute(code);
		try {
			//验证是否拿到工商图片页面
			HtmlPage page = submit.click();
			HtmlElement body = page.getBody();
			DomNodeList<HtmlElement> imglist = body.getElementsByTagName("image");
			//实际页面只有一个img ，没有即为没有通过验证码
			if(imglist.isEmpty()) {
				logger.info("本次尝试失败 ，请求再一次尝试验证");
				return false;
			}else {
				logger.info("破解成功 开始下载图片------>");
				HtmlElement img = imglist.get(0);
				String base64ImgData = img.getAttribute("src");
				base64ImgData = base64ImgData.replace("data:image/png;base64,", "");
				BufferedImage image = Base64Image2String.generateBufferImage(base64ImgData);
				BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(filepath));
				ImageIO.write(image, "png", os);
			}
		} catch (IOException e) {
			logger.info("提交验证码失败");
			e.printStackTrace();
		}
		return true;
	}
}

