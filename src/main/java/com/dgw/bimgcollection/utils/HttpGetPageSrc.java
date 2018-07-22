package com.dgw.bimgcollection.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetPageSrc {
	private String u;
    private String encoding;
    public HttpGetPageSrc(String u, String encoding) {
        this.u = u;
        this.encoding = encoding;
    }

    public void run() throws Exception {

        URL url = new URL(u);// 根据链接（字符串格式），生成一个URL对象

        HttpURLConnection urlConnection = (HttpURLConnection) url
                .openConnection();// 打开URL

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), encoding));// 得到输入流，即获得了网页的内容
        String line; // 读取输入流的数据，并显示
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
