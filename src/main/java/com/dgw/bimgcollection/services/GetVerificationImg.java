package com.dgw.bimgcollection.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;

import com.dgw.bimgcollection.config.Config;
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
	 * 每一次网页的验证码数据
	 */
	private BufferedImage buffimg = null;

	/**
	 * @Description 根据传入地址返回验证码
	 * @param imgUrl 传入验证码地址
	 * @return BufferedImage 返回验证码
	 */
	private BufferedImage fileImgDownloadToBufferImg(String imgUrl) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(Config.IMGVERSIONURL);
		CloseableHttpResponse response;
		try {
			response = client.execute(get);
			InputStream iStream = response.getEntity().getContent();
			buffimg = ImageIO.read(iStream);
			//ImageIO.write(buffimg, "jpg", new File(Config.FILEPATH+File.separator+1+".jpg"));
			return buffimg;
		} catch (IOException e) {
			logger.warn("下载验证码出错");
			e.printStackTrace();
		}finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffimg;
	}
	
	public static void main(String[] args) {
		//fileImgDownloadToBufferImg("");
	}

	/**
	 * @Description 根据URL读取一个验证码地址的表单
	 * @param  SRCIMGURI 验证码的读取地址
	 * @return HtmlForm  表单
	 */
	public HtmlForm getVerfImgForm(String SRCIMGURI) {
		WebClient client = GetTaoBaoSession.getYetLoginTmallSession();
		String imgSrcUrl = "";
		HtmlForm form=null;
		try {
			//HtmlPage page = client.getPage(Config.SRCIMGURI);
			//设置验证码爬取 地址
			HtmlPage page = client.getPage(Config.SRCIMGURI);
			List<HtmlForm> list = page.getForms();
			form = list.get(0);
			DomNodeList<HtmlElement> imgTagList = form.getElementsByTagName("img");
			HtmlImage img = null;//验证码图片
			for (HtmlElement temp : imgTagList) {
				if (temp.getAttribute("id").equals("J_CheckCode")) {
					logger.info("获取验证码图片地址成功");
					img = (HtmlImage) temp;
					break;
				}
			}
			imgSrcUrl = img.getAttribute("src");
			//拿到图片
			fileImgDownloadToBufferImg(imgSrcUrl);
			return form;
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
			logger.warn("获取图片失败");
		}
		return form;
	}
	public BufferedImage getBuffimg() {
		return buffimg;
	}

}
