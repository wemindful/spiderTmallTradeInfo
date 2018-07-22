package com.dgw.bimgcollection.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

/**
 * @author DGW-PC
 * @data   2018年7月22日下午2:04:22
 * <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 */
public class TestBase64Data {
	
	@SuppressWarnings("resource")
	@Test
	public void test() throws Exception {
		FileReader reader = new FileReader(new File("Z:\\1.txt"));
		char [] buff=new char[1024];
		StringBuffer buffer = new StringBuffer();
		int len=0;
		while((len=reader.read(buff))!=-1) {
			String string = new String(buff);
			buffer.append(buff);
		}
		String str = buffer.toString();
		str = str.replace("data:image/png;base64,", "");
		System.out.println(str);
	}

}
