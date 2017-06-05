package com.lfwer.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

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
	 * @return File 图片
	 */
	public static File resize(File fromFile, String toFilePath, int outputWidth, int outputHeight, boolean proportion)
			throws IOException {

		ImageIO.setUseCache(false);
		BufferedImage bi2 = ImageIO.read(fromFile);
		int width = bi2.getWidth(null);
		int height = bi2.getHeight(null);
		int newWidth;
		int newHeight;
		// 如果图片宽高小于要缩放的宽高，则不启用缩放，直接用原图
		if (width <= outputWidth || height <= outputHeight) {
			newWidth = width;
			newHeight = height;
			// BufferedImage img = ImageIO.read(fromFile);
			// img = img.getSubimage(0, 0, width, height);
			// File toFile = new File(toFilePath + "/" +
			// UUID.randomUUID().toString() + "_" + width + "_" + height
			// +
			// fromFile.getName().substring(fromFile.getName().lastIndexOf(".")));
			// ImageIO.write(img, "JPEG", toFile);
			// return toFile;
		} else {

			// 判断是否是等比缩放
			if (proportion == true) {
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) width) / (double) outputWidth + 0.1;
				double rate2 = ((double) height) / (double) outputHeight + 0.1;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) width) / rate);
				newHeight = (int) (((double) height) / rate);
			} else {
				newWidth = outputWidth; // 输出的图片宽度
				newHeight = outputHeight; // 输出的图片高度
			}
		}
		// BufferedImage to = new BufferedImage(newWidth, newHeight,
		// BufferedImage.TYPE_INT_RGB);
		//
		// Graphics2D g2d = to.createGraphics();
		//
		// to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth,
		// newHeight, Transparency.TRANSLUCENT);
		//
		// g2d.dispose();
		//
		// g2d = to.createGraphics();
		//
		// Image from = bi2.getScaledInstance(newWidth, newHeight,
		// Image.SCALE_SMOOTH);
		// g2d.drawImage(from, 0, 0, null);
		// g2d.dispose();
		//
		// ImageIO.write(to, "JPEG", toFile);

		Image image = bi2.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = outputImage.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		File toFile = new File(toFilePath + "/" + UUID.randomUUID().toString() + "_" + newWidth + "_" + newHeight
				+ fromFile.getName().substring(fromFile.getName().lastIndexOf(".")));
		ImageIO.write(outputImage, "JPEG", toFile);
		return toFile;

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
	public static File cut(File fromFile, String toFilePath, int x, int y, int w, int h) throws IOException {
		ImageIO.setUseCache(false);
		BufferedImage img = ImageIO.read(fromFile);
		img = img.getSubimage(x, y, w, h);

		File toFile = new File(toFilePath + "/" + UUID.randomUUID().toString() + "_cut"
				+ fromFile.getName().substring(fromFile.getName().lastIndexOf(".")));
		ImageIO.write(img, "JPEG", toFile);
		return toFile;
		/*
		 * 裁剪完的图片比原图还大..... FileInputStream is = null; ImageInputStream iis =
		 * null;
		 * 
		 * try {
		 * 
		 * String suffix = fromFile.getName().substring(
		 * fromFile.getName().lastIndexOf(".") + 1); is = new
		 * FileInputStream(fromFile); Iterator<ImageReader> it =
		 * ImageIO.getImageReadersBySuffix(suffix); ImageReader reader =
		 * it.next(); iis = ImageIO.createImageInputStream(is);
		 * reader.setInput(iis, true);
		 * 
		 * ImageReadParam param = reader.getDefaultReadParam(); Rectangle rect =
		 * new Rectangle(x, y, w, h); param.setSourceRegion(rect);
		 * 
		 * BufferedImage to = reader.read(0, param);
		 * 
		 * ImageIO.write(to, "png", toFile); } catch (IOException e) { throw e;
		 * } finally { if (is != null) { try { is.close(); } catch (IOException
		 * e) { e.printStackTrace(); } } if (iis != null) { try { iis.close(); }
		 * catch (IOException e) { e.printStackTrace(); } } }
		 */
	}

}
