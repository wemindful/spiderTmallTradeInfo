package com.dgw.bimgcollection.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author DGW-PC
 * @data 2018年7月21日下午3:55:42 <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 *       获取 工商执照图片
 */
public class GetVerificationImg {

	private static final Logger logger = LoggerFactory.getLogger(GetVerificationImg.class);

	/**
	 * 已经取得会话的浏览器模型
	 */
	private static WebClient client =new GetTaoBaoSession().getYetLoginTmallSession();
	/**
	 * 每一次网页的验证码数据
	 */
	private BufferedImage buffimg = null;

	/**
	 * 表单获取次数
	 */
	private static Integer FORMGETCOUNT = 3;

	public GetVerificationImg() {
		if (client == null) {
			client =new GetTaoBaoSession().getYetLoginTmallSession();
		}
	}

	/**
	 * @Description 根据传入地址返回验证码
	 * @param imgUrl 传入验证码地址
	 * @return BufferedImage 返回验证码
	 */
	@SuppressWarnings("unused")
	private BufferedImage fileImgDownloadToBufferImg(String imgUrl) {
		CloseableHttpClient clienthttp = HttpClients.createDefault();
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		// HttpGet get = new HttpGet(Config.IMGVERSIONURL);
		HttpGet get = new HttpGet(imgUrl);
		get.setConfig(requestConfig);
		CloseableHttpResponse response;
		try {
			response = clienthttp.execute(get);
			InputStream iStream = response.getEntity().getContent();
			buffimg = ImageIO.read(iStream);
			// ImageIO.write(buffimg, "jpg", new
			// File(Config.FILEPATH+File.separator+1+".jpg"));
			return buffimg;
		} catch (IOException e) {
			logger.warn("下载验证码出错");
			e.printStackTrace();
		} finally {
			try {
				clienthttp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffimg;
	}

	/**
	 * @Description 根据传入page上下文返回验证码
	 * @param valiCodeImg 网页上下文
	 * @return BufferedImage 返回验证码图片
	 */
	private BufferedImage ContextPageDownloadToBufferImg(HtmlImage valiCodeImg) {
		try {
			ImageReader imageRea = valiCodeImg.getImageReader();
			buffimg = imageRea.read(0);
			JFrame f2 = new JFrame();
			f2.setAlwaysOnTop(true);
			JLabel l = new JLabel();
			l.setIcon(new ImageIcon(buffimg));
			f2.getContentPane().add(l);
			f2.setSize(100, 100);
			f2.setTitle("验证码1");
			f2.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffimg;
	}

	public static void main(String[] args) {
		// fileImgDownloadToBufferImg("");
	}

	/**
	 * @Description 根据URL读取一个验证码地址的表单
	 * @param SRCIMGURI 验证码的读取地址
	 * @return HtmlForm 表单
	 */
	public HtmlForm getVerfImgForm(String srcimguri) {
		HtmlForm form = null;
		try {
			// 设置验证码爬取 地址
			// HtmlPage page = client.getPage(Config.SRCIMGURI);
			client.waitForBackgroundJavaScript(3000);
			client.getOptions().setJavaScriptEnabled(false);
			HtmlPage page = client.getPage(srcimguri);
			List<HtmlForm> list = page.getForms();
			if (list.size() == 0 && FORMGETCOUNT >= 0) {
				FORMGETCOUNT--;
				getVerfImgForm(srcimguri);
			}
			try {
				form = list.get(0);
			} catch (Exception e) {
				logger.info("网络异常，检查代理IP设置！");
			}
			DomNodeList<HtmlElement> imgTagList = form.getElementsByTagName("img");
			HtmlImage img = null;// 验证码图片
			for (HtmlElement temp : imgTagList) {
				if (temp.getAttribute("id").equals("J_CheckCode")) {
					logger.info("=======>>获取验证码图片成功");
					img = (HtmlImage) temp;
					break;
				}
			}
			/*
			 * imgSrcUrl = img.getAttribute("src"); //拿到图片
			 * fileImgDownloadToBufferImg("http:"+imgSrcUrl);
			 */
			System.out.println(img.asXml());
			ContextPageDownloadToBufferImg(img);
			logger.info(srcimguri);
			return form;
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
			logger.warn("获取图片失败");
		} finally {
		}
		return form;
	}

	public BufferedImage getBuffimg() {
		return buffimg;
	}

}
