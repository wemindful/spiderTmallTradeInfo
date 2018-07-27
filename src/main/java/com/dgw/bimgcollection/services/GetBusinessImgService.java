package com.dgw.bimgcollection.services;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;

import com.dgw.bimgcollection.utils.Base64Image2String;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
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
	
	/**
	 * 手动输入验证开关 
	 */
	public static boolean switchOnVerCode=true;

	
	public static void main(String[] args) {
		// return GenerateBImage();
	}
    
	/**
	 * 根据提取页面的地址拿到工商图片
	 * @param filepath 写入图片的路径 格式 Z:\\ +文件名  扩展名 png
	 * @param vcodeaddress  工商图片提取页的地址
	 * @return boolean 是否成功
	 */
	@SuppressWarnings("resource")
	public  boolean GenerateBImage(String filepath,String vcodeaddress) {
		GetVerificationImg verificationImg = new GetVerificationImg();
		 //验证码表单
		 HtmlForm form = verificationImg.getVerfImgForm(vcodeaddress);
		 buffimg = verificationImg.getBuffimg();
		 List<HtmlElement> list = form.getElementsByAttribute("input", "name", "checkCode");
		 HtmlInput inputCode=null;//输入的验证码
		 for (HtmlElement temp : list) {
				if(temp.getAttribute("name").equals("checkCode")) {
					logger.info("======>>获取验证码输入text标签");
					inputCode=(HtmlInput) temp;
				}
		}
		HtmlButton submit = (HtmlButton) form.getElementsByAttribute("button", "class", "short-btn").get(0);
		
		HtmlImage element = (HtmlImage) form.getElementsByTagName("img").get(0);
		try {
			ImageReader imageRea = element.getImageReader();
			buffimg = imageRea.read(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//是否手动或者自动输入验证码
		if(switchOnVerCode) {
			manualVerCode(inputCode);
		}else {
			//识别
			String code = new DistinguishVerCode().getProbableCode(buffimg);
			try {
				inputCode.click();
				inputCode.type(code);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (HtmlElement temp : list) {
			if(temp.getAttribute("name").equals("checkCode")) {
				logger.info("表单验证是否输入：");
				inputCode=(HtmlInput) temp;
				String string = inputCode.getValueAttribute();
				System.out.println(string);
			}
	   }
		try {
			//验证是否拿到工商图片页面
			logger.info("=======>>执行破解验证码操作");
			HtmlPage mainimagepage = submit.click();
			// 查看formh header参数
			/*
			 WebRequest request = form.getWebRequest(submit);
			 List<NameValuePair> parameters = request.getRequestParameters();
			for (NameValuePair nameValuePair : parameters) {
				System.out.println(nameValuePair.getName()+" "+nameValuePair.getValue());
			}*/
			HtmlElement mainimagebody = mainimagepage.getBody();
			logger.debug(mainimagebody.asXml());
			
			List<HtmlElement> codepanel = mainimagebody.getElementsByAttribute("div", "class", "code-panel");
			//实际页面只有一个img ，没有即为没有通过验证码
			if(!codepanel.isEmpty()) {
				logger.info("=======>>本次尝试失败 ，请求再一次尝试验证");
				return false;
			}else {
				logger.info("=======>>破解成功 开始下载图片------>");
				DomNodeList<HtmlElement> imglist = mainimagebody.getElementsByTagName("img");
				HtmlElement img = imglist.get(0);
				logger.info(img.asXml());
				FileOutputStream stream = new FileOutputStream("z://1.txt");
				stream.write(img.asXml().getBytes());
				String base64ImgData = img.getAttribute("src");
				logger.info("=======>>下载成功，开始解析图片------>");
				base64ImgData= base64ImgData.split(",")[1];
				BufferedImage image = Base64Image2String.generateBuffer(base64ImgData);
				FileOutputStream out = new FileOutputStream(filepath);
				BufferedOutputStream os = new BufferedOutputStream(out);
				ImageIO.write(image, "png", os);
				logger.info("=======>>写出图片"+ filepath.substring(filepath.lastIndexOf(File.separator))+"成功");
				logger.info("=======>>开始下一次操作\n\n\n");
			}
			return true;
		} catch (IOException e) {
			logger.info("提交验证码失败");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * pandaun 
	 * @param inputCode void 验证码输入标签
	 */
	private void manualVerCode(HtmlInput inputCode) {
		JFrame f2 = new JFrame("验证码输入验证");
		f2.setAlwaysOnTop(true);
		JLabel l = new JLabel();
		l.setIcon(new ImageIcon(buffimg));
		f2.getContentPane().add(l);
		f2.setSize(100, 100);
		f2.setVisible(true);
		
		//设置破解验证码
		Scanner sc = new Scanner(System.in);
		String  trim= sc.next().trim();
		try {
			String st = new String(trim.getBytes(),"GBK");
			inputCode.click();
			inputCode.type(st);
			f2.setVisible(false);
			f2.dispose();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

