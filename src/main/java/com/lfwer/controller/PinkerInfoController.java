package com.lfwer.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lfwer.common.AvoidDuplicateSubmission;
import com.lfwer.common.CookieUtil;
import com.lfwer.common.Valid;
import com.lfwer.model.User;
import com.lfwer.model.CarOwnerInfo;
import com.lfwer.model.PinkerInfo;
import com.lfwer.service.DictService;
import com.lfwer.service.LoginService;
import com.lfwer.service.PinkerInfoService;
import com.lfwer.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("pinkerInfo")
@Api(value = "乘客发布信息管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class PinkerInfoController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private DictService dictService;

	@Autowired
	private PinkerInfoService pinkerInfoService;

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
		User user = CookieUtil.readCookie(null, request, response, loginService);
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
	// @AvoidDuplicateSubmission(needRemoveToken = true)
	@ResponseBody
	@ApiOperation(value = "乘客发布信息提交", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid addPinkerInfoSubmit(PinkerInfo result, String userId) {
		Valid valid = null;
		try {
			User user = CookieUtil.readCookie(userId, null, null, loginService);
			if (user != null) {
				result.setAddTime(new Date());
				result.setAddUser(user);
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
				valid = new Valid(true, String.valueOf(result.getId()));
				// try {
				// // 生成html
				// genHtml(result, user);
				// return valid = new Valid(true,
				// String.valueOf(result.getId()));
				// } catch (Exception e) {
				// pinkerInfoService.removePinkerInfo(result.getId(), user);
				// throw e;
				// }
			} else {
				valid = new Valid(false, "保存失败：验证用户失败。");
			}
		} catch (Exception ex) {
			valid = new Valid(false, "保存失败。");
			ex.printStackTrace();
		}
		return valid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void genHtml(PinkerInfo result, User user) throws Exception {
		Writer writer = null;
		InputStream in = null;
		File htmlFile = null;
		try {

			result.setFromZoneName(dictService.getName("ZONE", result.getFromZone()));
			result.setToZoneName(dictService.getName("ZONE", result.getToZone()));
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

			Properties prop = new Properties();
			in = LoginController.class.getResourceAsStream("/config/path.properties");
			prop.load(in);
			String path = prop.getProperty("html.pinkerInfo.save.path");

			Configuration cfg = new Configuration();

			cfg.setClassForTemplateLoading(CarOwnerInfoController.class, "../templates");
			cfg.setDefaultEncoding("UTF-8");
			Template tmp = cfg.getTemplate("viewPinkerInfo.ftl");
			Map root = new HashMap();
			root.put("result", result);
			root.put("user", user);
			root.put("images", null);
			htmlFile = new File(path + result.getId() + ".html");
			writer = new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8");
			tmp.process(root, writer);
		} catch (Exception ex) {
			if (writer != null)
				writer.close();
			if (htmlFile != null && htmlFile.exists())
				htmlFile.delete();
			throw ex;
		} finally {
			if (in != null)
				in.close();
			if (writer != null)
				writer.close();
		}
	}

	@RequestMapping("updateLookCount")
	@ResponseBody
	@ApiOperation(value = "乘客发布信息浏览次数+1", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid updateLookCount(Integer id) {
		Valid valid = null;
		try {
			Integer result = pinkerInfoService.updateLookCount(id);
			valid = new Valid(true, String.valueOf(result));
		} catch (Exception ex) {
			valid = new Valid(false, "更新浏览数失败");
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
	@ResponseBody
	@ApiOperation(value = "获取乘客发布信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public PinkerInfo viewPinkerInfo(Integer id) throws Exception {
		PinkerInfo result = pinkerInfoService.getPinkerInfo(id);
		if (result != null) {

			if (result.getState() == 0) {
				return null;
			}

			result.setFromZoneName(dictService.getName("ZONE", result.getFromZone()));
			result.setToZoneName(dictService.getName("ZONE", result.getToZone()));
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
		}
		return result;
	}

	@RequestMapping("removePinkerInfo")
	@ResponseBody
	@ApiOperation(value = "删除乘客发布信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid removePinkerInfo(Integer id, String cookieName) {
		Valid valid = null;
		try {
			User user = CookieUtil.readCookie(cookieName, null, null, loginService);
			pinkerInfoService.removePinkerInfo(id, user);
			valid = new Valid(true, "删除成功");
		} catch (Exception ex) {
			valid = new Valid(false, "删除失败");
			ex.printStackTrace();
		}
		return valid;
	}

	@RequestMapping("getPageInfo")
	@ResponseBody
	@ApiOperation(value = "乘客发布信息分页展示", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Object[]> getPageInfo(String callback, PinkerInfo result, Integer page, String date) {
		return pinkerInfoService.getPageInfo(result, page, date);
	}
}
