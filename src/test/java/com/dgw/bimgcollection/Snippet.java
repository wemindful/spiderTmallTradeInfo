package com.dgw.bimgcollection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Snippet {
	
	
	public static void main(String[] args) throws Exception {
		
		BufferedImage image = ImageIO.read(new File("z:/2.png"));
		BufferedImage image2 = removeInterference(image);
		ImageIO.write(image2, "png", new FileOutputStream(new File("z:/22.png")));
		
	}
	// 2.去除图像干扰像素（非必须操作，只是可以提高精度而已）。
	public static BufferedImage removeInterference(BufferedImage image) throws Exception {
		int width = image.getWidth();
		int height = image.getHeight();
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isFontColor(image.getRGB(x, y))) {
					// 如果当前像素是字体色，则检查周边是否都为白色，如都是则删除本像素。
					int roundWhiteCount = 0;
					if (isWhiteColor(image, x + 1, y + 1))
						roundWhiteCount++;
					if (isWhiteColor(image, x + 1, y - 1))
						roundWhiteCount++;
					if (isWhiteColor(image, x - 1, y + 1))
						roundWhiteCount++;
					if (isWhiteColor(image, x - 1, y - 1))
						roundWhiteCount++;
					if (roundWhiteCount == 4) {
						image.setRGB(x, y, Color.WHITE.getRGB());
					}
				}
			}
		}
		return image;
	}

	// 4.判断字体的颜色含义：正常可以用rgb三种颜色加起来表示，字与非字应该有显示的区别，找出来。
	private static boolean isFontColor(int colorInt) {
		Color color = new Color(colorInt);

		return color.getRed() + color.getGreen() + color.getBlue() == 340;
	}

	// 取得指定位置的颜色是否为白色，如果超出边界，返回true
	// 本方法是从removeInterference方法中摘取出来的。单独调用本方法无意义。
	private static boolean isWhiteColor(BufferedImage image, int x, int y) throws Exception {
		if (x < 0 || y < 0)
			return true;
		if (x >= image.getWidth() || y >= image.getHeight())
			return true;

		Color color = new Color(image.getRGB(x, y));

		return color.equals(Color.WHITE) ? true : false;
	}
}
