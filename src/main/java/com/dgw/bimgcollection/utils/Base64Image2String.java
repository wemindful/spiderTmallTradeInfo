package com.dgw.bimgcollection.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

public class Base64Image2String {

	/**
	 * @Description: 将base64编码字符串转换为bufferimage
	 * @param imgStr base64编码字符串
	 * @return BufferedImage
	 */
	public static BufferedImage generateBufferImage(String imgStr) {
		if (imgStr == null)
			try {
				throw new Exception("编码为NULL");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		// 解密
		BufferedImage image = null;
		try {
			Decoder decoder = Base64.getDecoder();
			byte[] buffer = decoder.decode(imgStr);
			// 处理数据
			for (int i = 0; i < buffer.length; ++i) {
				if (buffer[i] < 0) {
					buffer[i] += 256;
				}
			}
			ByteArrayInputStream ais = new ByteArrayInputStream(buffer);
			image = ImageIO.read(ais);
			return image;
		} catch (IOException e) {
		}
		return image;
	}

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author: 
	 * @CreateTime: 
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	*/
	public static boolean generateImage(String imgStr, String path) {
			if (imgStr == null)
				return false;
			// 解密
			try {
				Decoder decoder = Base64.getDecoder();
				byte[] b = decoder.decode(imgStr);
				 // 处理数据
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
					b[i] += 256;
					}
				}
				OutputStream out = new FileOutputStream(path);
				out.write(b);
				out.flush();
				out.close();
				return true;
			} catch (IOException e) {
				return false;
			}
	}

	/**
	 * @Description: 根据图片输入流转换为base64编码字符串
	 * @Author:
	 * @CreateTime:
	 * @return
	 */
	public static String getImageStr(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(data);
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		String strImg = getImageStr("Z:\\ˮӡ\\2.bmp");
		System.out.println(strImg);
		File file = new File("z://1.txt");
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		try {
			osw.write(strImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// generateImage(strImg, "Z:\\ˮӡ\\444.bmp");

	}
}
