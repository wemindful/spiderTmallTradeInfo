package com.dgw.bimgcollection.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgw.bimgcollection.config.Config;
import com.dgw.bimgcollection.config.UserConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author DGW-PC
 * @data 2018年7月21日 取得淘宝会话 cookie session
 */
public class GetTaoBaoSession {

	/**
	 * 全局web browser
	 */
	private static WebClient client = new WebClient(BrowserVersion.FIREFOX_52);
	private static final Logger logger = LoggerFactory.getLogger(GetTaoBaoSession.class);
	public static void main(String[] args) {
		getYetLoginTmallSession();
	}

	/**
	 * @return WebClient 已经登录tmall的webclient
	 */
	public static WebClient getYetLoginTmallSession() {
		try {
			HtmlPage page = client.getPage(Config.TAOBAOURL);
			client.getOptions().setCssEnabled(false);
			// 屏蔽掉异常
			client.getOptions().setThrowExceptionOnFailingStatusCode(true);
			client.getOptions().setThrowExceptionOnScriptError(true);
			client.getOptions().setPrintContentOnFailingStatusCode(true);
			// 设置连接超时时间 ，这里是10S。如果为0，则无限期
			client.getOptions().setTimeout(8000);
			client.setJavaScriptTimeout(5000);

			client.waitForBackgroundJavaScript(Config.POSTPONE);
			List<HtmlForm> list = page.getForms();
			HtmlForm form = list.get(2);
			HtmlInput usernmae = form.getInputByName("TPL_username");
			HtmlInput passwd = form.getInputByName("TPL_password");

			DomNodeList<HtmlElement> tagName = form.getElementsByTagName("button");
			HtmlButton btnSumit = null;
			for (HtmlElement temp : tagName) {
				if (temp.getAttribute("id").equals("J_SubmitStatic")) {
					logger.info("=====>>>>>>获取到登录按钮");
					btnSumit = (HtmlButton) temp;
					break;
				}
			}
			// 设置登录的用户名 与密码
			usernmae.setValueAttribute(UserConfig.getUsernmae());
			passwd.setValueAttribute(UserConfig.getPasswd());
			//拿到返回页面
			Page loginPage = btnSumit.click();
			Thread.sleep(5000);
			/*
			 * HtmlPage page2 = client.getPage(Config.gongshang);
			 * System.out.println(page2.asXml());
			 */
			if(isLogins(loginPage)) {
				return client;
			}else {
				getYetLoginTmallSession();
			}
		} catch (FailingHttpStatusCodeException | IOException | InterruptedException e) {
			e.printStackTrace();
			logger.info("获取登录会话失败！");
		} finally {
			client.close();
		}
		return client;
	}

	/**
	 * 
	 * 成功登录返回true,失败返回false
	 * @param loginPage 登录返回的页面
	 * @return boolean 返回结果
	 */
	private static boolean isLogins(Page loginPage) {
		return false;
	}
}
