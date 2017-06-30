package com.lfwer.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.JsonObject;
import com.lfwer.common.CookieUtil;
import com.lfwer.common.ImageUtil;
import com.lfwer.common.RandomUtil;
import com.lfwer.common.SmbUtil;
import com.lfwer.common.Valid;
import com.lfwer.model.User;
import com.lfwer.service.LoginService;
import com.lfwer.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("login")
@Api(value = "登录管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;

	/**
	 * 登录提交
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("signSubmit")
	@ResponseBody
	@ApiOperation(value = "登录验证", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid signSubmit(@ApiParam(value = "用户名") @RequestParam("username") String username,
			@ApiParam(value = "密码") @RequestParam("password") String password) {
		Valid valid = null;
		boolean validUsername = loginService.validateUsername(username);
		boolean validPhone = false;
		if (!validUsername) {
			validPhone = loginService.validatePhone(username);
		}
		if (validUsername || validPhone) {
			boolean isOk = loginService.validatePassword(username, password);
			if (!isOk) {
				valid = new Valid(false, "密码不正确");
			} else {
				List<User> list = loginService.find("from User t where (t.username=? or t.phone=?) and t.password=?",
						new String[] { username, username, password });
				if (list != null && !list.isEmpty() && list.size() == 1) {
					Cookie cookie = CookieUtil.genCookie(username, password);
					valid = new Valid(true, cookie.getValue());

				} else {
					valid = new Valid(false, "获取用户信息失败");
				}
			}
		} else {
			valid = new Valid(false, "用户名/手机号不存在");
		}
		return valid;
	}

	/**
	 * 注册提交
	 * 
	 * @param user
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping("registerSubmit")
	@ResponseBody
	public JSONPObject registerSubmit(String callback, User user) {
		Valid data = null;
		try {
			if (user != null) {
				user.setNickName("用户" + RandomUtil.getRandNum(8));// 默认昵称
				user.setType("1");// 默认身份为乘客
				loginService.saveUser(user);
				CookieUtil.genCookie(user.getUsername(), user.getPassword());
				data = new Valid(true, "注册成功，系统将自动跳转到【登录】页面。");
			} else {
				data = new Valid(false, "注册失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			data = new Valid(false, "注册失败。");
		}
		return new JSONPObject(callback, data);
	}

	/**
	 * 提交找回密码页面-验证手机并发送验证码
	 * 
	 * @param session
	 * @param phone
	 * @param smsCode
	 * @return
	 */
	@RequestMapping("retrievePwdSubmit")
	@ResponseBody
	public JSONPObject retrievePwdSubmit(String callback, String phone, String smsCode) {
		Valid data = validateSMS(phone, smsCode);
		return new JSONPObject(callback, data);
	}

	/**
	 * 提交重设密码
	 * 
	 * @param session
	 * @param password
	 * @return
	 */
	@RequestMapping("retrievePwd2Submit")
	@ResponseBody
	public JSONPObject retrievePwd2Submit(String callback, String phoneNo, String password) {
		Valid data = null;
		try {
			loginService.retrievePwd(phoneNo, password);
			data = new Valid(true, "重设密码成功，系统将自动跳转到【登录】页面。");
		} catch (Exception ex) {
			data = new Valid(false, "操作失败。");
			ex.printStackTrace();
		}
		return new JSONPObject(callback, data);
	}

	/**
	 * 验证“手机号”是否存在
	 * 
	 * @param type
	 * @param phone
	 * @return
	 */
	@RequestMapping("validatePhone")
	@ResponseBody
	public JSONPObject validatePhone(String callback, @RequestParam("type") int type,
			@RequestParam("phone") String phone) {
		JsonObject result = new JsonObject();
		boolean valid = loginService.validatePhone(phone);
		result.addProperty("valid", type == 1 ? !valid : valid);
		return new JSONPObject(callback, result.toString());
	}

	/**
	 * 验证“用户名”是否存在
	 * 
	 * @param type
	 * @param username
	 * @return
	 */
	@RequestMapping("validateUsername")
	@ResponseBody
	public String validateUsername(@RequestParam("type") int type, @RequestParam("username") String username) {
		JsonObject result = new JsonObject();
		boolean valid = loginService.validateUsername(username);
		result.addProperty("valid", type == 1 ? !valid : valid);
		return result.toString();
	}

	@RequestMapping("validateNickName")
	@ResponseBody
	public String validateNickName(HttpServletRequest request, HttpServletResponse response, String nickName)
			throws Exception {
		User user = CookieUtil.readCookie(null, request, response, loginService);
		JsonObject result = new JsonObject();
		boolean valid = loginService.validateNickName(nickName, user.getId());
		result.addProperty("valid", valid);
		return result.toString();
	}

	/**
	 * 验证“短信验证码”是否正确
	 * 
	 * @param phone
	 * @param smsCode
	 * @return
	 */
	@RequestMapping("validateSMS")
	@ResponseBody
	public Valid validateSMS(@RequestParam("phone") String phone, @RequestParam("smsCode") String smsCode) {
		Valid data = null;
		if (loginService.validateSMS(phone, smsCode)) {
			data = new Valid(true, "验证码正确");
		} else {
			data = new Valid(false, "验证码不正确");
		}

		return data;

	}

	/**
	 * 生成验证码入库，并发送到指定手机
	 * 
	 * @param Phone
	 * @return
	 */
	@RequestMapping("genSMS")
	@ResponseBody
	public Valid genSMS(@RequestParam("phone") String phone) {
		return loginService.genSMS(phone);
	}

	@RequestMapping("uploadCarPhoto")
	@ResponseBody
	public String uploadCarPhoto(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		File fromFile = null;
		File largeFile = null;
		File smallFile = null;
		try {
			User user = CookieUtil.readCookie(null, request, response, loginService);

			int type = Integer.parseInt(request.getParameter("_type"));
			File tempFile = new File(request.getServletContext().getRealPath("") + "/uploadTemp");
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			String filename;
			Properties prop = new Properties();
			InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);
			String stuff;
			int index = file.getOriginalFilename().lastIndexOf(".");
			if (index < 0) {
				stuff = ".jpg";
			} else {
				stuff = file.getOriginalFilename().substring(index);
			}
			String name = UUID.randomUUID().toString();
			filename = name + stuff;
			fromFile = new File(tempFile.getPath() + "/" + filename);
			file.transferTo(fromFile);
			fromFile.createNewFile();

			// 图片缩略
			smallFile = ImageUtil.resize(fromFile, tempFile.getPath(), 100, 100, true);
			largeFile = ImageUtil.resize(fromFile, tempFile.getPath(), 1600, 1600, true);
			String smallFilename = smallFile.getName();
			String largeFilename = largeFile.getName();

			// 使用smb协议将文件上传到共享目录
			SmbUtil.smbPut(prop.getProperty("smb.carPhoto") + "/" + user.getId(), smallFile);
			SmbUtil.smbPut(prop.getProperty("smb.carPhoto") + "/" + user.getId(), largeFile);

			loginService.updateCarPhoto(user.getId(), smallFilename, largeFilename, type);

			// session重新赋值
			if (type == 1) {
				user.setCarPhotoSmall1(smallFilename);
				user.setCarPhotoLarge1(largeFilename);
			} else {
				user.setCarPhotoSmall2(smallFilename);
				user.setCarPhotoLarge2(largeFilename);
			}
			request.getSession().setAttribute("curUser", user);

			JsonObject result = new JsonObject();
			result.addProperty("small", smallFilename);
			result.addProperty("large", largeFilename);
			return result.toString();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (fromFile.exists()) {
				fromFile.delete();
			}
			if (smallFile.exists()) {
				smallFile.delete();
			}
			if (largeFile.exists()) {
				largeFile.delete();
			}
		}

		return null;
	}

	@RequestMapping("uploadDrivingBookPhoto")
	@ResponseBody
	public String uploadDrivingBookPhoto(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		File fromFile = null;
		File largeFile = null;
		File smallFile = null;
		try {
			User user = CookieUtil.readCookie(null, request, response, loginService);
			File tempFile = new File(request.getServletContext().getRealPath("") + "/uploadTemp");
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			String filename;
			Properties prop = new Properties();
			InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);
			String stuff;
			int index = file.getOriginalFilename().lastIndexOf(".");
			if (index < 0) {
				stuff = ".jpg";
			} else {
				stuff = file.getOriginalFilename().substring(index);
			}
			String name = UUID.randomUUID().toString();
			filename = name + stuff;
			fromFile = new File(tempFile.getPath() + "/" + filename);
			file.transferTo(fromFile);
			fromFile.createNewFile();

			// 图片缩略
			smallFile = ImageUtil.resize(fromFile, tempFile.getPath(), 100, 100, true);
			largeFile = ImageUtil.resize(fromFile, tempFile.getPath(), 1600, 1600, true);
			String smallFilename = smallFile.getName();
			String largeFilename = largeFile.getName();

			// 使用smb协议将文件上传到共享目录
			SmbUtil.smbPut(prop.getProperty("smb.drivingBookPhoto") + "/" + user.getId(), smallFile);
			SmbUtil.smbPut(prop.getProperty("smb.drivingBookPhoto") + "/" + user.getId(), largeFile);

			loginService.updateDrivingBookPhoto(user.getId(), smallFilename, largeFilename);

			// session重新赋值
			user.setDrivingBookPhotoSmall(smallFilename);
			user.setDrivingBookPhotoLarge(largeFilename);
			request.getSession().setAttribute("curUser", user);

			JsonObject result = new JsonObject();
			result.addProperty("small", smallFilename);
			result.addProperty("large", largeFilename);
			return result.toString();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (fromFile.exists()) {
				fromFile.delete();
			}
			if (smallFile.exists()) {
				smallFile.delete();
			}
			if (largeFile.exists()) {
				largeFile.delete();
			}
		}

		return null;
	}

	@RequestMapping("getCarPhoto")
	@ResponseBody
	public void getCarPhoto(HttpServletRequest request, HttpServletResponse response, String name, String id)
			throws Exception {

		Properties prop = new Properties();
		InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
		prop.load(in);

		SmbUtil.smbGet(prop.getProperty("smb.carPhoto") + "/" + id + "/" + name, response);
	}

	@RequestMapping("getDrivingBookPhoto")
	@ResponseBody
	public void getDrivingBookPhoto(HttpServletRequest request, HttpServletResponse response, String name, String id)
			throws Exception {
		Properties prop = new Properties();
		InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
		prop.load(in);
		SmbUtil.smbGet(prop.getProperty("smb.drivingBookPhoto") + "/" + id + "/" + name, response);
	}

	@RequestMapping("getCarPhotoLarge1")
	@ResponseBody
	public void getCarPhotoLarge1(HttpServletRequest request, HttpServletResponse response, Integer id)
			throws Exception {
		User user = userService.getUser(id);
		if (user != null) {
			Properties prop = new Properties();
			InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);
			SmbUtil.smbGet(prop.getProperty("smb.carPhoto") + "/" + id + "/" + user.getCarPhotoLarge1(), response);
		}
	}

	@RequestMapping("getCarPhotoLarge2")
	@ResponseBody
	public void getCarPhotoLarge2(HttpServletRequest request, HttpServletResponse response, Integer id)
			throws Exception {
		User user = userService.getUser(id);
		if (user != null) {
			Properties prop = new Properties();
			InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);
			SmbUtil.smbGet(prop.getProperty("smb.carPhoto") + "/" + id + "/" + user.getCarPhotoLarge2(), response);
		}
	}

	@RequestMapping("uploadPhoto")
	@ResponseBody
	public String uploadPhoto(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		String filename = null;
		File f = null;
		File cutFile = null;
		File smallFile = null;
		File largeFile = null;
		InputStream in = null;
		try {
			int x = Integer.parseInt(request.getParameter("x"));
			int y = Integer.parseInt(request.getParameter("y"));
			int w = Integer.parseInt(request.getParameter("w"));
			int h = Integer.parseInt(request.getParameter("h"));

			File tempFile = new File(request.getServletContext().getRealPath("") + "/uploadTemp");
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			String stuff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

			// String name = file.getOriginalFilename().substring(0,
			// file.getOriginalFilename().lastIndexOf("."))
			// + "_uuid_" + UUID.randomUUID().toString();
			// filename = name;
			filename = UUID.randomUUID().toString();
			f = new File(tempFile.getPath() + "/" + filename + "_tmp" + stuff);
			file.transferTo(f);
			f.createNewFile();
			User user = CookieUtil.readCookie(null, request, response, loginService);
			Properties prop = new Properties();
			in = null;
			in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);

			cutFile = ImageUtil.cut(f, tempFile.getPath(), x, y, w, h);

			smallFile = ImageUtil.resize(cutFile, tempFile.getPath(), 100, 100, true);
			largeFile = ImageUtil.resize(cutFile, tempFile.getPath(), 1600, 1600, true);
			String smallFilename = smallFile.getName();
			String largeFilename = largeFile.getName();

			SmbUtil.smbPut(prop.getProperty("smb.photo") + "/" + user.getId(), smallFile);
			SmbUtil.smbPut(prop.getProperty("smb.photo") + "/" + user.getId(), largeFile);

			loginService.updateUserPhoto(user.getId(), smallFilename, largeFilename);

			JsonObject result = new JsonObject();
			result.addProperty("small", smallFilename);
			result.addProperty("large", largeFilename);

			return result.toString();
		} catch (Exception e) {
			response.setStatus(500);
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (f.exists()) {
				f.delete();
			}
			if (smallFile.exists()) {
				smallFile.delete();
			}
			if (largeFile.exists()) {
				largeFile.delete();
			}
			if (cutFile.exists()) {
				cutFile.delete();
			}
		}
	}

	@RequestMapping("getPhoto")
	@ResponseBody
	public void getPhoto(HttpServletRequest request, HttpServletResponse response, String name, String id)
			throws Exception {
		Properties prop = new Properties();
		InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
		prop.load(in);
		SmbUtil.smbGet(prop.getProperty("smb.photo") + "/" + id + "/" + name, response);
	}

	@RequestMapping("getCurUser")
	@ResponseBody
	public JSONPObject getCurUser(String callback, String name, HttpServletRequest request,
			HttpServletResponse response) {
		User user = null;
		try {
			user = CookieUtil.readCookie(name, request, response, loginService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONPObject(callback, user);
	}

	@RequestMapping("updateUser")
	@ResponseBody
	public Valid updateUser(Integer userId, String type, String value, HttpServletRequest request,
			HttpServletResponse response) {
		Valid valid = null;
		try {

			if (value == null || "".equals(value.trim())) {
				return new Valid(false, "不能为空");
			}

			int age = 0;
			switch (type) {
			case "type":
				userService.updateByHql("update User u set u.type = ? where id = ?", new Object[] { value, userId });

				break;
			case "sex":
				userService.updateByHql("update User u set u.sex = ? where id = ?", new Object[] { value, userId });
				break;
			case "marry":
				userService.updateByHql("update User u set u.marry = ? where id = ?", new Object[] { value, userId });
				break;
			case "industry":
				userService.updateByHql("update User u set u.industry = ? where id = ?",
						new Object[] { value, userId });
				break;
			case "birthday":
				Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(value.split(",")[0]);
				age = Integer.parseInt(value.split(",")[1]);
				userService.updateByHql("update User u set birthday = ? , u.age = ? where id = ?",
						new Object[] { birthday, age, userId });
				break;
			case "age":
				age = Integer.parseInt(value);
				userService.updateByHql("update User u set u.age = ? where id = ?", new Object[] { age, userId });
				break;
			case "hobby":
				userService.updateByHql("update User u set u.hobby = ? where id = ?", new Object[] { value, userId });
				break;
			default:
				return new Valid(false, "保存失败");
			}

			valid = new Valid(true, "保存成功");

		} catch (Exception e) {
			valid = new Valid(false, "保存失败");
			e.printStackTrace();
		}
		return valid;
	}

}
