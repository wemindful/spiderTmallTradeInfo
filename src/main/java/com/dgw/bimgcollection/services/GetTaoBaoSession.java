package com.dgw.bimgcollection.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgw.bimgcollection.config.Config;
import com.dgw.bimgcollection.config.UserConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
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
	private WebClient client = new WebClient(BrowserVersion.FIREFOX_52);
	
	/**
	 * 设置获取登录会话的次数，
	 */
	private static Integer sessionCount=5;
	
	private static final Logger logger = LoggerFactory.getLogger(GetTaoBaoSession.class);

	private HtmlPage loginPage;

	
	/**
	 * @return WebClient 已经登录tmall的webclient
	 */
	public  WebClient getYetLoginTmallSession() {
		try {
			//设置代理
	    	/*ProxyConfig proxyConfig = client.getOptions().getProxyConfig(); 
	    	proxyConfig.setProxyHost("124.193.85.88");  
	        proxyConfig.setProxyPort(8080);
			client.getOptions().setProxyConfig(proxyConfig);*/
			//设置下载图片
			client.getOptions().setDownloadImages(true);
			//设置css
			client.getOptions().setCssEnabled(false);
			client.getCookieManager().setCookiesEnabled(true);
			client.getOptions().setRedirectEnabled(true);
			// 设置Ajax异步
			client.setAjaxController(new NicelyResynchronizingAjaxController());
			//忽略ssl认证
			client.getOptions().setUseInsecureSSL(true);	
			// 屏蔽掉异常
			client.getOptions().setThrowExceptionOnFailingStatusCode(true);
			client.getOptions().setThrowExceptionOnScriptError(false);
			client.getOptions().setPrintContentOnFailingStatusCode(true);
			// 设置连接超时时间 ，这里是10S。如果为0，则无限期
			client.getOptions().setTimeout(8000);
			client.setJavaScriptTimeout(5000);
			//设置js延迟执行时间			
			client.waitForBackgroundJavaScript(Config.POSTPONE);
			HtmlPage page = client.getPage(Config.TAOBAOURL);
			
			List<HtmlForm> list = page.getForms();
			//第三个form为我们需要解析的数据
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
			//拿到返回页面
			logger.info("=====>>>>>>执行登录操作");
			loginPage = btnSumit.click();
			sessionCount--;
			Thread.sleep(2000);
			if(isLogins(loginPage)) {
				return loginPage.getWebClient();
			}else {
				if(sessionCount==0) {
					logger.info("拉取登录会话失败，稍后请设置代理IP地址再试！");
					System.exit(0);
				}
				getYetLoginTmallSession();
			}
		} catch (FailingHttpStatusCodeException | IOException | InterruptedException e) {
			e.printStackTrace();
			logger.info("网络异常，检查代理IP设置！");
		}
		return loginPage.getWebClient();
	}

	/**
	 * 验证是否已经登录成功
	 * 成功登录返回true,失败返回false
	 * @param loginPage 登录返回的页面
	 * @return boolean 返回结果
	 */
	private static boolean isLogins(Page loginPage) {
		Page destpage=loginPage;
		HtmlPage page=null;
		if(destpage.isHtmlPage()) {
			page=(HtmlPage) destpage;
			List<HtmlForm> list = page.getForms();
			if(list.size()==0)return true;
			//如果新的表单不存在TPL_username，表示成功跳转
			try {
				HtmlForm form;
				try {
					form = list.get(2);
				} catch (Exception e) {
					return true;
				}
				form.getInputByName("TPL_username");
			} catch (ElementNotFoundException e) {
				return true;
			}
		}
		return false;
	}
}
