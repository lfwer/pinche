package com.lfwer.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import com.lfwer.model.User;
import com.lfwer.service.LoginService;

/**
 * Cookie.
 * 
 * @author Administrator
 *
 */
public class CookieUtil {
	// 保存cookie时的cookieName
	private final static String cookieDomainName = "handanpinche";
	// 加密cookie时的网站自定码
	private final static String webKey = "lfwer";
	// 设置cookie有效期是365天，根据需要自定义
	private final static long cookieMaxAge = 60 * 60 * 24 * 365;

	/**
	 * 保存Cookie到客户端
	 * 
	 * @param user
	 * @param response
	 */
	public static void saveCookie(String username, String password, HttpServletResponse response) {
		// cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
		// MD5加密用户详细信息
		String cookieValueWithMd5 = getMD5(username + ":" + password + ":" + validTime + ":" + webKey);
		// 将要被保存的完整的Cookie值
		String cookieValue = username + ":" + validTime + ":" + cookieValueWithMd5;
		// 再一次对Cookie的值进行BASE64编码
		String cookieValueBase64 = new String(Base64.encodeBase64(cookieValue.getBytes()));
		// 开始保存Cookie
		Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);
		// 存两年(这个值应该大于或等于validTime)
		cookie.setMaxAge(60 * 60 * 24 * 365 * 1);
		// cookie有效路径是网站根目录
		cookie.setPath("/");
		// 向客户端写入
		response.addCookie(cookie);
	}

	/**
	 * 读取Cookie,自动完成登陆操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static User readCookie(HttpServletRequest request, HttpServletResponse response, LoginService loginService)
			throws Exception {
		// 根据cookieName取cookieValue
		Cookie cookies[] = request.getCookies();
		String cookieValue = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookieDomainName.equals(cookies[i].getName())) {
					cookieValue = cookies[i].getValue();
					break;
				}
			}
		}
		// 如果cookieValue为空,返回,
		if (cookieValue == null) {
			return null;
		}
		// 如果cookieValue不为空,才执行下面的代码
		// 先得到的CookieValue进行Base64解码
		String cookieValueAfterDecode = new String(Base64.decodeBase64(cookieValue), "utf-8");
		// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
		String cookieValues[] = cookieValueAfterDecode.split(":");
		if (cookieValues.length != 3) {
			return null;
		}
		// 判断是否在有效期内,过期就删除Cookie
		long validTimeInCookie = new Long(cookieValues[1]);
		if (validTimeInCookie < System.currentTimeMillis()) {
			// 删除Cookie
			clearCookie(response);
			return null;
		}
		// 取出cookie中的用户名,并到数据库中检查这个用户名,
		String username = cookieValues[0];
		// 根据用户名到数据库中检查用户是否存在
		User user = null;
		List<User> list = loginService.find("from User t where t.username=? or t.phone=?",
				new String[] { username, username });
		if (list != null && list.size() == 1) {
			user = list.get(0);
		}
		// 如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
		if (user != null) {
			String md5ValueInCookie = cookieValues[2];
			String md5ValueFromUser = getMD5(
					user.getUsername() + ":" + user.getPassword() + ":" + validTimeInCookie + ":" + webKey);
			String md5ValudFormUser2 = getMD5(
					user.getPhone() + ":" + user.getPassword() + ":" + validTimeInCookie + ":" + webKey);
			// 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
			if (md5ValueFromUser.equals(md5ValueInCookie) || md5ValudFormUser2.equals(md5ValueInCookie)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * 用户注销时,清除Cookie,在需要时可随时调用
	 * 
	 * @param response
	 */
	public static void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieDomainName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);

	}

	/**
	 * 获取Cookie组合字符串的MD5码的字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String getMD5(String value) {
		String result = null;
		try {
			byte[] valueByte = value.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valueByte);
			result = toHex(md.digest());
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * 将传递进来的字节数组转换成十六进制的字符串形式并返回
	 * 
	 * @param buffer
	 * @return
	 */
	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}

}
