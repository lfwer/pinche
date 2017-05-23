package com.lfwer.common;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

	/**
	 * 缩放图片
	 * 
	 * @param fromFile
	 *            原始图片
	 * @param toFile
	 *            缩放后的图片
	 * @param outputWidth
	 *            缩放宽度
	 * @param outputHeight
	 *            缩放高度
	 * @param proportion
	 *            是否等比缩放
	 * @throws IOException
	 *             文件IO异常
	 */
	public static void resize(File fromFile, File toFile, int outputWidth,
			int outputHeight, boolean proportion) throws IOException {
		
		BufferedImage bi2 = ImageIO.read(fromFile);
		
		int newWidth;
		int newHeight;
		// 判断是否是等比缩放
		if (proportion == true) {
			// 为等比缩放计算输出的图片宽度及高度
			double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth
					+ 0.1;
			double rate2 = ((double) bi2.getHeight(null))
					/ (double) outputHeight + 0.1;
			// 根据缩放比率大的进行缩放控制
			double rate = rate1 < rate2 ? rate1 : rate2;
			newWidth = (int) (((double) bi2.getWidth(null)) / rate);
			newHeight = (int) (((double) bi2.getHeight(null)) / rate);
		} else {
			newWidth = outputWidth; // 输出的图片宽度
			newHeight = outputHeight; // 输出的图片高度
		}
		BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = to.createGraphics();

		to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth,
				newHeight, Transparency.TRANSLUCENT);

		g2d.dispose();

		g2d = to.createGraphics();

		Image from = bi2.getScaledInstance(newWidth, newHeight,
				bi2.SCALE_AREA_AVERAGING);
		g2d.drawImage(from, 0, 0, null);
		g2d.dispose();

		ImageIO.write(to, "png", toFile);
	}

	/**
	 * 裁剪图片
	 * 
	 * @param srcPath
	 *            原始图片
	 * @param toPath
	 *            裁剪后的图片
	 * @param x
	 *            居左
	 * @param y
	 *            居顶
	 * @param w
	 *            裁剪后的宽度
	 * @param h
	 *            裁剪后的高度
	 * @throws IOException
	 *             文件IO异常
	 */
	public static void cut(File fromFile, File toFile, int x, int y, int w,
			int h) throws IOException   {
 
		
		 BufferedImage img = ImageIO.read(fromFile);
		 img = img.getSubimage(x, y, w, h);
		 ImageIO.write(img, "png", toFile);  
		 
 
		/* 裁剪完的图片比原图还大.....
		FileInputStream is = null;
		ImageInputStream iis = null;

		try {

			String suffix = fromFile.getName().substring(
					fromFile.getName().lastIndexOf(".") + 1);
			is = new FileInputStream(fromFile);
			Iterator<ImageReader> it = ImageIO.getImageReadersBySuffix(suffix);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, w, h);
			param.setSourceRegion(rect);

			BufferedImage to = reader.read(0, param);

			ImageIO.write(to, "png", toFile);
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		*/
	}
}
