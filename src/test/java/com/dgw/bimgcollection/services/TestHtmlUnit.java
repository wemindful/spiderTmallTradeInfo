package com.dgw.bimgcollection.services;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestHtmlUnit {
	
	
	public static void main(String[] args) {
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
			 try {
		            HtmlPage page=webClient.getPage("https://login.taobao.com/"); // 解析获取页面
		            Thread.sleep(15000);
		            System.out.println("网页html:"+page.asXml()); // 获取Html
		            System.out.println("====================");
		           // System.out.println("网页文本："+page.asText()); // 获取文本
		        } catch (FailingHttpStatusCodeException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (MalformedURLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
		            webClient.close(); // 关闭客户端，释放内存
		        }
	}

}
