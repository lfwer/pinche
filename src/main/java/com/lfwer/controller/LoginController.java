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
import javax.servlet.http.HttpServletResponse;
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
import com.lfwer.service.DictService;
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
	@Autowired
	private DictService dictService;

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
	public Valid signSubmit(@ApiParam(value = "用户名") String username, @ApiParam(value = "密码") String password) {
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
	@ApiOperation(value = "注册提交", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid registerSubmit(User user) {
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
		return data;
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
	@ApiOperation(value = "提交找回密码页面-验证手机并发送验证码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid retrievePwdSubmit(String phone, String smsCode) {
		Valid data = validateSMS(phone, smsCode);
		return data;
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
	@ApiOperation(value = "提交重设密码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid retrievePwd2Submit(String phoneNo, String password) {
		Valid data = null;
		try {
			loginService.retrievePwd(phoneNo, password);
			data = new Valid(true, "重设密码成功，系统将自动跳转到【登录】页面。");
		} catch (Exception ex) {
			data = new Valid(false, "操作失败。");
			ex.printStackTrace();
		}
		return data;
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
	@ApiOperation(value = "注册时验证“手机号”是否存在", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid validatePhone(@ApiParam("type") int type, @ApiParam("phone") String phone) {
		Valid data = null;
		boolean valid = loginService.validatePhone(phone);
		data = new Valid(type == 1 ? !valid : valid, "");
		return data;
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
	@ApiOperation(value = "注册时验证“用户名”是否存在", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid validateUsername(@RequestParam("type") int type, @RequestParam("username") String username) {
		Valid data = null;
		boolean valid = loginService.validateUsername(username);
		data = new Valid(type == 1 ? !valid : valid, "");
		return data;
	}

	@RequestMapping("validateNickName")
	@ResponseBody
	@ApiOperation(value = "完善信息-验证“昵称”是否存在", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid validateNickName(String cookieName, String nickName) {
		Valid data = null;
		try {
			User user = CookieUtil.readCookie(cookieName, null, null, loginService);
			boolean valid = loginService.validateNickName(nickName, user.getId());
			data = new Valid(valid, "");
		} catch (Exception e) {
			data = new Valid(false, "系统异常");
			e.printStackTrace();
		}
		return data;
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
	@ApiOperation(value = "验证“短信验证码”是否正确", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid validateSMS(String phone, String smsCode) {
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
	@ApiOperation(value = "生成验证码入库，并发送到指定手机", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid genSMS(String phone) {
		return loginService.genSMS(phone);
	}

	@RequestMapping("uploadCardPhoto")
	@ResponseBody
	@ApiOperation(value = "上传身份证图片", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public String uploadCardPhoto(String cookieName, MultipartFile file,Integer cardType) {
		File fromFile = null;
		File largeFile = null;
		File smallFile = null;
		try {
			User user = CookieUtil.readCookie(cookieName, null, null, loginService);
			String filename;
			Properties prop = new Properties();
			InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);
			File tempFile = new File(prop.getProperty("upload.temp.path"));
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
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
			SmbUtil.smbPut(prop.getProperty("smb.cardPhoto") + "/" + user.getId(), smallFile);
			SmbUtil.smbPut(prop.getProperty("smb.cardPhoto") + "/" + user.getId(), largeFile);
			loginService.updateCardPhoto(user.getId(), smallFilename, largeFilename, cardType);

			// session重新赋值
			// if (type == 1) {
			// user.setCardPhotoSmall1(smallFilename);
			// user.setCardPhotoLarge1(largeFilename);
			// } else {
			// user.setCardPhotoSmall2(smallFilename);
			// user.setCardPhotoLarge2(largeFilename);
			// }
			// request.getSession().setAttribute("curUser", user);

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
	@ApiOperation(value = "上传行驶本图片", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public String uploadDrivingBookPhoto(String cookieName, MultipartFile file) {
		File fromFile = null;
		File largeFile = null;
		File smallFile = null;
		try {
			User user = CookieUtil.readCookie(cookieName, null, null, loginService);

			Properties prop = new Properties();
			InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);

			File tempFile = new File(prop.getProperty("upload.temp.path"));
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			String filename;

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
			// user.setDrivingBookPhotoSmall(smallFilename);
			// user.setDrivingBookPhotoLarge(largeFilename);
			// request.getSession().setAttribute("curUser", user);

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

	@RequestMapping("getCardPhoto")
	@ResponseBody
	@ApiOperation(value = "获取身份证图片", httpMethod = "GET")
	public void getCardPhoto(String name, String id, HttpServletResponse response) throws Exception {

		Properties prop = new Properties();
		InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
		prop.load(in);

		SmbUtil.smbGet(prop.getProperty("smb.cardPhoto") + "/" + id + "/" + name, response);
	}

	@RequestMapping("getDrivingBookPhoto")
	@ResponseBody
	@ApiOperation(value = "获取行驶本图片", httpMethod = "GET")
	public void getDrivingBookPhoto(HttpServletResponse response, String name, String id) throws Exception {
		Properties prop = new Properties();
		InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
		prop.load(in);
		SmbUtil.smbGet(prop.getProperty("smb.drivingBookPhoto") + "/" + id + "/" + name, response);
	}

	@RequestMapping("uploadPhoto")
	@ResponseBody
	@ApiOperation(value = "上传头像", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public String uploadPhoto(String cookieName, MultipartFile file, Integer x, Integer y, Integer w, Integer h) {
		String filename = null;
		File f = null;
		File cutFile = null;
		File smallFile = null;
		File largeFile = null;
		InputStream in = null;
		try {
			Properties prop = new Properties();
			in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);
			File tempFile = new File(prop.getProperty("upload.temp.path"));
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			String stuff;
			int index = file.getOriginalFilename().lastIndexOf(".");
			if (index < 0) {
				stuff = ".jpg";
			} else {
				stuff = file.getOriginalFilename().substring(index);
			}
			// String name = file.getOriginalFilename().substring(0,
			// file.getOriginalFilename().lastIndexOf("."))
			// + "_uuid_" + UUID.randomUUID().toString();
			// filename = name;
			filename = UUID.randomUUID().toString();
			f = new File(tempFile.getPath() + "/" + filename + "_tmp" + stuff);
			file.transferTo(f);
			f.createNewFile();
			User user = CookieUtil.readCookie(cookieName, null, null, loginService);

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
	@ApiOperation(value = "获取头像", httpMethod = "GET")
	public void getPhoto(HttpServletResponse response, String name, String id) throws Exception {
		Properties prop = new Properties();
		InputStream in = LoginController.class.getResourceAsStream("/config/upload.properties");
		prop.load(in);
		SmbUtil.smbGet(prop.getProperty("smb.photo") + "/" + id + "/" + name, response);
	}

	@RequestMapping("getCurUser")
	@ResponseBody
	@ApiOperation(value = "获取用户", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getCurUser(String name) {
		User user = null;
		try {
			user = CookieUtil.readCookie(name, null, null, loginService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@RequestMapping("updateUser")
	@ResponseBody
	@ApiOperation(value = "修改用户信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid updateUser(Integer userId, String type, String value) {
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
			case "nickName":
				userService.updateByHql("update User u set u.nickName = ? where id = ?",
						new Object[] { value, userId });
				break;
			case "sign":
				userService.updateByHql("update User u set u.sign = ? where id = ?", new Object[] { value, userId });
				break;
			case "carNum":
				userService.updateByHql("update User u set u.carNum = ? where id = ?", new Object[] { value, userId });
				break;
			case "carStyle":
				userService.updateByHql("update User u set u.carStyle = ? where id = ?",
						new Object[] { value, userId });
				break;
			case "carType":
				String[] arr = value.split("\\|");
				userService.updateByHql("update User u set u.carBrand = ? , u.carType = ? where id = ?",
						new Object[] { arr[0], arr[1], userId });
				break;
			case "carColor":
				userService.updateByHql("update User u set u.carColor = ? where id = ?",
						new Object[] { value, userId });
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
