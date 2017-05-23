package com.lfwer.common;

import java.util.Random;

/**
 * 随机数工具类.
 * 
 * @author Administrator
 *
 */
public class RandomUtil {
	/**
	 * 生成随机字符串
	 * @param charCount 个数
	 * @return 随机字符串
	 */
	public static String getRandStr(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 26) + 'a');
			charValue += String.valueOf(c);
		}
		return charValue;
	}
	/**
	 * 生成随机数字
	 * @param numCount 个数
	 * @return 随机数字
	 */
	public static String getRandNum(int numCount) {
		String charValue = "";
		for (int i = 0; i < numCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	private static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
}
