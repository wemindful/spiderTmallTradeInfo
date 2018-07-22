package com.dgw.bimgcollection.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgw.bimgcollection.config.Config;

public class testDownloadImg {
	
	private static final Logger logger = LoggerFactory.getLogger(testDownloadImg.class);

	/**
	 * 
	 * 开发过程中测试是否能下载完整的验证码图片到本地
	 * @throws Exception void
	 */
	@Test
	public void testTest1s() throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(Config.IMGVERSIONURL);
		CloseableHttpResponse response;
		FileOutputStream fos = null;
		BufferedOutputStream os = null;
		try {
			response = client.execute(get);
			InputStream iStream = response.getEntity().getContent();
			//将响应数据转化为输入流数据
			byte[] buf = new byte[2048];
			int len = 0;
			
			fos = new FileOutputStream(new File(Config.FILEPATH+File.separator+1+".jpg"));
			os = new BufferedOutputStream(fos);
			while((len=iStream.read(buf))!=-1) {
				os.write(buf,0,len);
			}
		} catch (IOException e) {
			logger.warn("下载验证码出错");
			e.printStackTrace();
		}finally {
			try {
				os.flush();
				os.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
