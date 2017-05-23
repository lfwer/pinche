package com.lfwer.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.lfwer.common.CookieUtil;
import com.lfwer.common.ImageUtil;
import com.lfwer.common.RandomUtil;
import com.lfwer.common.SmbUtil;
import com.lfwer.common.Util;
import com.lfwer.common.Valid;
import com.lfwer.model.User;
import com.lfwer.service.DictService;
import com.lfwer.service.LoginService;
import com.lfwer.service.UserService;

@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private DictService dictService;
	@Autowired
	private UserService userService;

	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping("signIn")
	public String signIn() {
		return "/login/signIn";
	}

	/**
	 * 登录提交
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping("signSubmit")
	@ResponseBody
	public String signSubmit(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, HttpServletResponse response) {
		JsonObject result = new JsonObject();
		boolean validUsername = loginService.validateUsername(username);
		boolean validPhone = false;
		if (!validUsername) {
			validPhone = loginService.validatePhone(username);
		}
		if (validUsername || validPhone) {
			boolean valid = loginService.validatePassword(username, password);
			if (!valid) {
				result.addProperty("valid", false);
				result.addProperty("message", "密码不正确");
			} else {
				List<User> list = loginService.find("from User t where (t.username=? or t.phone=?) and t.password=?",
						new String[] { username, username, password });
				if (list != null && !list.isEmpty() && list.size() == 1) {
					CookieUtil.saveCookie(username, password, response);
					result.addProperty("valid", true);
					result.addProperty("message", "登录成功");
				} else {
					result.addProperty("valid", false);
					result.addProperty("message", "获取用户信息失败");

				}
			}
		} else {
			result.addProperty("valid", false);
			result.addProperty("message", "用户名/手机号不存在");
		}
		return result.toString();
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@RequestMapping("register")
	public String register() {
		return "/login/register";
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
	public Valid registerSubmit(User user, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Valid data = null;
		try {
			if (user != null) {
				user.setNickName("用户" + RandomUtil.getRandNum(8));
				loginService.saveUser(user);
				CookieUtil.saveCookie(user.getUsername(), user.getPassword(), response);
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
	 * 跳转到注册成功后完善信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("register2")
	public ModelAndView register2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = null;
		User user = CookieUtil.readCookie(request, response, loginService);
		if (user == null) {
			modelAndView = new ModelAndView("redirect:/login/signIn");
		} else {
			modelAndView = new ModelAndView("/login/register2");
			modelAndView.addObject("user", user);
			modelAndView.addObject("year", Calendar.getInstance().get(Calendar.YEAR));
			modelAndView.addObject("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
			modelAndView.addObject("day", Calendar.getInstance().get(Calendar.DATE));
			modelAndView.addObject("userTypeList", dictService.getDicts("USERTYPE", "-1"));
			modelAndView.addObject("sexList", dictService.getDicts("SEX", "-1"));
			modelAndView.addObject("marryList", dictService.getDicts("MARRY", "-1"));
			modelAndView.addObject("hobbyList", dictService.getDicts("HOBBY", "-1"));
			modelAndView.addObject("zoneList", dictService.getDicts("ZONE", "-1"));
			modelAndView.addObject("industryList", dictService.getDicts("INDUSTRY", "-1"));
		}

		return modelAndView;
	}

	/**
	 * 完善信息页面提交
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("register2Submit")
	public String register2Submit(User user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (user != null) {
			User dbUser = CookieUtil.readCookie(request, response, loginService);

			dbUser.setRealName(user.getRealName());
			dbUser.setIdCard(user.getIdCard());
			dbUser.setZone(user.getZone());
			dbUser.setAddr(user.getAddr());

			dbUser.setType(user.getType());
			dbUser.setNickName(user.getNickName());
			dbUser.setSex(user.getSex());
			dbUser.setBirthday(user.getBirthday());
			dbUser.setAge(user.getAge());
			dbUser.setMarry(user.getMarry());
			dbUser.setHobby(user.getHobby());
			dbUser.setIndustry(user.getIndustry());
			dbUser.setSign(user.getSign());
			loginService.updateUser(dbUser);
		}
		if ("2".equals(user.getType())) {// 完善车主信息
			return "redirect:/login/register3";
		} else {
			return "redirect:/index.jsp";
		}
	}

	/**
	 * 跳转到完善车主信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("register3")
	public ModelAndView register3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = null;
		User user = CookieUtil.readCookie(request, response, loginService);
		if (user == null) {
			modelAndView = new ModelAndView("redirect:/login/signIn");
		} else {
			modelAndView = new ModelAndView("/login/register3");
			if (user.getCarProvince() == null) {
				user.setCarProvince(8);
			}
			modelAndView.addObject("carProvinceName", Util.getCarProvinceMap().get(user.getCarProvince()));
			modelAndView.addObject("user", user);
			modelAndView.addObject("carTypeList", dictService.getDicts("CARTYPE", null));

			modelAndView.addObject("carColorList", dictService.getDicts("CARCOLOR", "-1"));
		}
		return modelAndView;
	}

	/**
	 * 完善车主信息提交
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("register3Submit")
	public String register3Submit(User user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (user != null) {
			User dbUser = CookieUtil.readCookie(request, response, loginService);
			dbUser.setCarType(user.getCarType());
			dbUser.setCarBrand(user.getCarBrand());
			dbUser.setCarStyle(user.getCarStyle());
			dbUser.setCarColor(user.getCarColor());
			dbUser.setCarProvince(user.getCarProvince());
			dbUser.setCarNum(user.getCarNum());
			loginService.updateUser(dbUser);
		}
		return "redirect:/index.jsp";
	}

	/**
	 * 跳转到找回密码页面-验证手机并发送验证码
	 * 
	 * @return
	 */
	@RequestMapping("retrievePwd")
	public String retrievePwd() {
		return "/login/retrievePwd";
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
	public Valid retrievePwdSubmit(HttpSession session, String phone, String smsCode) {
		Valid data = validateSMS(phone, smsCode);
		if (data.isValid()) {
			session.setAttribute("retrievePwd_phone", phone);
		}
		return data;
	}

	/**
	 * 跳转到找回密码页面-重设密码
	 * 
	 * @return
	 */
	@RequestMapping("retrievePwd2")
	public String retrievePwd2() {
		return "/login/retrievePwd2";
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
	public Valid retrievePwd2Submit(HttpSession session, String password) {
		Valid data = null;
		try {
			String phone = String.valueOf(session.getAttribute("retrievePwd_phone"));
			if (phone == null) {
				data = new Valid(false, "您常时间未操作，系统将自动跳转到【找回密码】页面。");
			} else {
				loginService.retrievePwd(phone, password);
				data = new Valid(true, "重设密码成功，系统将自动跳转到【登录】页面。");
			}
			session.removeAttribute("retrievePwd_phone");
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
	public String validatePhone(@RequestParam("type") int type, @RequestParam("phone") String phone) {
		JsonObject result = new JsonObject();
		boolean valid = loginService.validatePhone(phone);
		result.addProperty("valid", type == 1 ? !valid : valid);
		return result.toString();
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
		User user = CookieUtil.readCookie(request, response, loginService);
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
			User user = CookieUtil.readCookie(request, response, loginService);

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
			String smallFilename = name + "_small" + stuff;
			smallFile = new File(tempFile.getPath() + "/" + smallFilename);
			ImageUtil.resize(fromFile, smallFile, 100, 100, true);
			String largeFilename = name + "_large" + stuff;
			largeFile = new File(tempFile.getPath() + "/" + largeFilename);
			ImageUtil.resize(fromFile, largeFile, 400, 400, true);
			// 使用smb协议将文件上传到共享目录
			SmbUtil.smbPut(prop.getProperty("smb.carPhoto") + "/" + user.getId(), smallFile);
			SmbUtil.smbPut(prop.getProperty("smb.carPhoto") + "/" + user.getId(), largeFile);

			loginService.updateCarPhoto(user.getId(), smallFilename, largeFilename, type);
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
			User user = CookieUtil.readCookie(request, response, loginService);
			Properties prop = new Properties();
			in = null;
			in = LoginController.class.getResourceAsStream("/config/upload.properties");
			prop.load(in);
			smallFile = new File(tempFile.getPath() + "/" + filename + "_small" + stuff);
			largeFile = new File(tempFile.getPath() + "/" + filename + "_large" + stuff);

			ImageUtil.cut(f, largeFile, x, y, w, h);

			ImageUtil.resize(largeFile, smallFile, 80, 80, true);
			ImageUtil.resize(largeFile, largeFile, 400, 400, true);

			SmbUtil.smbPut(prop.getProperty("smb.photo") + "/" + user.getId(), smallFile);
			SmbUtil.smbPut(prop.getProperty("smb.photo") + "/" + user.getId(), largeFile);

			loginService.updateUserPhoto(user.getId(), smallFile.getName(), largeFile.getName());

			JsonObject result = new JsonObject();
			result.addProperty("small", smallFile.getName());
			result.addProperty("large", largeFile.getName());

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

	/**
	 * 退出并跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping("signOut")
	public String signOut(HttpServletResponse response) {
		CookieUtil.clearCookie(response);
		return "/login/signIn";
	}

	@RequestMapping("isRegisterDriver")
	@ResponseBody
	public Valid isRegisterDriver(HttpServletRequest request, HttpServletResponse response) {
		User user = null;
		try {
			user = CookieUtil.readCookie(request, response, loginService);
		} catch (Exception e) {
			user = new User();
			e.printStackTrace();
		}
		Valid data = null;
		if (user == null) {
			data = new Valid(false, "1");
		} else if (user.getCarType() == null || user.getCarType().isEmpty()) {
			data = new Valid(false, "2");
		} else {
			data = new Valid(true, null);
		}
		return data;
	}
}
