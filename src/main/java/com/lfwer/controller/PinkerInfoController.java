package com.lfwer.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSONObject;
import com.google.gson.JsonObject;
import com.lfwer.common.AvoidDuplicateSubmission;
import com.lfwer.common.CookieUtil;
import com.lfwer.common.Valid;
import com.lfwer.model.User;
import com.lfwer.model.PinkerInfo;
import com.lfwer.service.DictService;
import com.lfwer.service.LoginService;
import com.lfwer.service.PinkerInfoService;
import com.lfwer.service.UserService;

@Controller
@RequestMapping("pinkerInfo")
public class PinkerInfoController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private DictService dictService;

	@Autowired
	private PinkerInfoService pinkerInfoService;

	@Autowired
	private UserService userService;

	/**
	 * 跳转到乘客发布页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addPinkerInfo")
	@AvoidDuplicateSubmission(needSaveToken = true)
	public ModelAndView addPinkerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = null;
		User user = CookieUtil.readCookie(request, response, loginService);
		if (user == null) {
			modelAndView = new ModelAndView("redirect:/login/signIn?url=pinkerInfo/addPinkerInfo");
		} else {
			modelAndView = new ModelAndView("/pinkerInfo/addPinkerInfo");
			modelAndView.addObject("user", user);
			modelAndView.addObject("zoneList", dictService.getDicts("ZONE", "-1"));
			modelAndView.addObject("seatingList", dictService.getDicts("SEATING", "-1"));
			modelAndView.addObject("weekList", dictService.getDicts("WEEK", "-1"));
		}
		return modelAndView;
	}

	/**
	 * 乘客发布提交
	 * 
	 * @param user
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping("addPinkerInfoSubmit")
	@AvoidDuplicateSubmission(needRemoveToken = true)
	@ResponseBody
	public Valid addPinkerInfoSubmit(PinkerInfo result, HttpServletRequest request, HttpServletResponse response) {
		Valid valid = null;
		try {
			User user = CookieUtil.readCookie(request, response, loginService);
			if (user != null) {
				result.setAddTime(new Date());
				result.setAddUser(user.getId());
				result.setAge(user.getAge());
				result.setSex(user.getSex());
				result.setContacePhone(user.getPhone());
				result.setContactUser(user.getNickName());
				result.setModyTime(new Date());
				result.setRefreshTime(new Date());
				result.setState(1);
				result.setTop(0);

				if (result.getTimeLimit() == 2) {
					result.setPdate(null);
					String[] arr = result.getPweekName().split(",");
					for (String s : arr) {
						if ("1".equals(s)) {
							result.setPweek1(s);
						} else if ("2".equals(s)) {
							result.setPweek2(s);
						} else if ("3".equals(s)) {
							result.setPweek3(s);
						} else if ("4".equals(s)) {
							result.setPweek4(s);
						} else if ("5".equals(s)) {
							result.setPweek5(s);
						} else if ("6".equals(s)) {
							result.setPweek6(s);
						} else if ("7".equals(s)) {
							result.setPweek7(s);
						}
					}
				}
				pinkerInfoService.savePinkerInfo(result);
				// 查看刚发布的信息
				return valid = new Valid(true, String.valueOf(result.getId()));
			} else {
				valid = new Valid(false, "保存失败。");
			}
		} catch (Exception ex) {
			valid = new Valid(false, "保存失败。");
			ex.printStackTrace();
		}
		return valid;
	}

	/**
	 * 查看乘客发布的信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("viewPinkerInfo")
	public ModelAndView viewPinkerInfo(Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView("/pinkerInfo/viewPinkerInfo");
		PinkerInfo result = pinkerInfoService.getPinkerInfo(id);
		if (result != null) {
			result.setFromZoneName(dictService.getName("ZONE", result.getFromZone()));
			result.setToZoneName(dictService.getName("ZONE", result.getToZone()));
			result.setSex(dictService.getName("SEX", result.getSex()));
			// 拼接周期
			if (result.getTimeLimit() == 2) {
				StringBuilder week = new StringBuilder();

				if (result.getPweek1() != null && result.getPweek2() != null && result.getPweek3() != null
						&& result.getPweek4() != null && result.getPweek5() != null && result.getPweek6() != null
						&& result.getPweek7() != null) {
					week.append("周一至周日");
				} else if (result.getPweek1() != null && result.getPweek2() != null && result.getPweek3() != null
						&& result.getPweek4() != null && result.getPweek5() != null && result.getPweek6() != null) {
					week.append("周一至周六");
				} else if (result.getPweek1() != null && result.getPweek2() != null && result.getPweek3() != null
						&& result.getPweek4() != null && result.getPweek5() != null) {
					week.append("周一至周五");
				} else if (result.getPweek1() != null && result.getPweek2() != null && result.getPweek3() != null
						&& result.getPweek4() != null) {
					week.append("周一至周四");
				} else {
					if (result.getPweek1() != null) {
						week.append("周一");
					}
					if (result.getPweek2() != null) {
						if (week.length() > 0) {
							week.append(",");
						}
						week.append("周二");
					}
					if (result.getPweek3() != null) {
						if (week.length() > 0) {
							week.append(",");
						}
						week.append("周三");
					}
					if (result.getPweek4() != null) {
						if (week.length() > 0) {
							week.append(",");
						}
						week.append("周四");
					}
					if (result.getPweek5() != null) {
						if (week.length() > 0) {
							week.append(",");
						}
						week.append("周五");
					}
					if (result.getPweek6() != null) {
						if (week.length() > 0) {
							week.append(",");
						}
						week.append("周六");
					}
					if (result.getPweek7() != null) {
						if (week.length() > 0) {
							week.append(",");
						}
						week.append("周日");
					}

				}

				if (week.length() > 0) {
					result.setPweekName(week.toString());
				}
			}
			pinkerInfoService.updateLookCount(result.getId());
			modelAndView.addObject("result", result);
			User user = userService.getUser(result.getAddUser());
			modelAndView.addObject("user", user);

			User addUser = CookieUtil.readCookie(request, response, loginService);
			modelAndView.addObject("addUser", addUser);
		}

		return modelAndView;
	}

	@RequestMapping("removePinkerInfo")
	public void removePinkerInfo(Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = CookieUtil.readCookie(request, response, loginService);
		pinkerInfoService.removePinkerInfo(id, user);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("getPageInfo")
	@ResponseBody
	public List getPageInfo(PinkerInfo result, Integer page, String date) {
		return pinkerInfoService.getPageInfo(result, page, date);
	}
}
