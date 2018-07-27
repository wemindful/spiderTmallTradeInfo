package com.dgw.bimgcollection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.util.ImageHelper;

public class imageTobinary {
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\DGW-PC\\Desktop\\天猫验证码\\验证码");
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			try {
				BufferedImage read = ImageIO.read(files[i]);
				BufferedImage toBinary = ImageHelper.convertImageToBinary(read);
				FileOutputStream stream = new FileOutputStream(files[i]);
				ImageIO.write(toBinary, "jpg", stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
