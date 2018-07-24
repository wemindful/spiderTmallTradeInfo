package com.dgw.bimgcollection;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgw.bimgcollection.config.Config;
import com.dgw.bimgcollection.config.UserConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author DGW-PC
 * @data 2018年7月24日下午5:08:40 <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 */
public class FInalTestImgesDown {

	private static WebClient client;
	private static final Logger logger = LoggerFactory.getLogger(FInalTestImgesDown.class);

	public FInalTestImgesDown() {
		client = new WebClient(BrowserVersion.FIREFOX_52);
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.waitForBackgroundJavaScript(10000);
	}

	public static void main(String[] args) {
		try {
			new FInalTestImgesDown();
			HtmlPage page = client.getPage("https://login.taobao.com/");

			List<HtmlForm> list = page.getForms();
			// 第三个form为我们需要解析的数据
			HtmlForm form = list.get(2);
			HtmlInput usernmae = form.getInputByName("TPL_username");
			HtmlInput passwd = form.getInputByName("TPL_password");

			DomNodeList<HtmlElement> tagName = form.getElementsByTagName("button");
			HtmlButton btnSumit = null;
			for (HtmlElement temp : tagName) {
				if (temp.getAttribute("id").equals("J_SubmitStatic")) {
					logger.info("=====>>>>>>获取到登录按钮标签");
					btnSumit = (HtmlButton) temp;
					break;
				}
			}
			// 设置登录的用户名 与密码
			usernmae.setValueAttribute(UserConfig.getUsernmae());
			passwd.setValueAttribute(UserConfig.getPasswd());
			// 拿到返回页面
			logger.info("=====>>>>>>执行登录操作");
			HtmlPage loginPage = btnSumit.click();

			WebClient client2 = loginPage.getWebClient();

			HtmlPage page2 = client2.getPage(Config.SRCIMGURI);
			System.out.println(page2.asXml());
			//可能获取失败，这里要做处理
			HtmlForm form2 = page2.getForms().get(0);
			DomNodeList<HtmlElement> imgTagList = form2.getElementsByTagName("img");
			HtmlImage img = null;// 验证码图片
			for (HtmlElement temp : imgTagList) {
				if (temp.getAttribute("id").equals("J_CheckCode")) {
					logger.info("=======>>获取验证码图片成功");
					img = (HtmlImage) temp;
					break;
				}
			}
			BufferedImage bufferedImage = img.getImageReader().read(0);
			List<HtmlElement> list12 = form2.getElementsByAttribute("input", "name", "checkCode");
			HtmlInput inputCode = null;// 输入的验证码
			for (HtmlElement temp : list12) {
				if (temp.getAttribute("name").equals("checkCode")) {
					logger.info("======>>获取验证码输入text标签");
					inputCode = (HtmlInput) temp;
				}
			}
			HtmlButton submit = (HtmlButton) form2.getElementsByAttribute("button", "class", "short-btn").get(0);
			JFrame f2 = new JFrame();
			f2.setAlwaysOnTop(true);
			JLabel l = new JLabel();
			l.setIcon(new ImageIcon(bufferedImage));
			f2.getContentPane().add(l);
			f2.setSize(100, 100);
			f2.setVisible(true);
			Scanner scanner = new Scanner(System.in);
			String next = scanner.next();
			inputCode.click();
			inputCode.type(next);
			HtmlPage page3 = submit.click();
			System.out.println(page3.asXml());
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}

	}

}
