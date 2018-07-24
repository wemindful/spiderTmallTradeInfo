package com.dgw.bimgcollection.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

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
			ais.close();
			return image;
		} catch (IOException e) {
		}
		return image;
	}

	/**
	 * @Description: 将base64编码字符串转换为bufferimage
	 * @param imgbase64Str base64编码字符串
	 * @return BufferedImage
	 */
	public static BufferedImage generateBuffer(String imgbase64Str) {
		if (imgbase64Str == null || imgbase64Str.equals("")) {
			try {
				throw new Exception("编码为NULL");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		BufferedImage image = null;
		try {
			byte[] base64Binary = DatatypeConverter.parseBase64Binary(imgbase64Str);
			ByteArrayInputStream imgArray = new ByteArrayInputStream(base64Binary);
			image = ImageIO.read(imgArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
		// 解密
	}

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr base64编码字符串
	 * @param path   图片路径-具体到文件
	 * @return
	 */
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null)
			return false;
		// 解密
		try {
			Decoder mimeDecoder = Base64.getMimeDecoder();
			byte[] b = mimeDecoder.decode(imgStr);
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
		Encoder encoder = Base64.getMimeEncoder();
		return encoder.encodeToString(data);
	}

	/**
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String strImg = getImageStr("z://001.jpg");
		String string = strImg.split(",")[1];
		byte[] bs = DatatypeConverter.parseBase64Binary(string);
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(bs));
		ImageIO.write(image, "png", new File("z://java.png"));
	}

}
