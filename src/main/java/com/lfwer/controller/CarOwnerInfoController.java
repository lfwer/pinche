package com.lfwer.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.lfwer.model.Image;
import com.lfwer.service.DictService;
import com.lfwer.service.LoginService;
import com.lfwer.service.CarOwnerInfoService;
import com.lfwer.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("carOwnerInfo")
@Api(value = "车主发布信息管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarOwnerInfoController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private DictService dictService;

	@Autowired
	private CarOwnerInfoService carOwnerInfoService;

	@Autowired
	private UserService userService;

	// @Autowired
	// private RedisTemplate<String, String> redisTemplate;

	/**
	 * 跳转到车主发布页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addCarOwnerInfo")
	@AvoidDuplicateSubmission(needSaveToken = true)
	public ModelAndView addCarOwnerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = null;
		User user = CookieUtil.readCookie(null, request, response, loginService);
		if (user == null) {
			modelAndView = new ModelAndView("redirect:/login/signIn");
		} else {
			modelAndView = new ModelAndView("/carOwnerInfo/addCarOwnerInfo");
			modelAndView.addObject("user", user);
			modelAndView.addObject("zoneList", dictService.getDicts("ZONE", "-1"));
			modelAndView.addObject("seatingList", dictService.getDicts("SEATING", "-1"));
			modelAndView.addObject("weekList", dictService.getDicts("WEEK", "-1"));
			modelAndView.addObject("carType", user.getCarType());
			modelAndView.addObject("carStyle", user.getCarStyle());
			modelAndView.addObject("carColor", user.getCarColor());
			modelAndView.addObject("carTypeName", dictService.getName("CARTYPE", user.getCarType()));
			modelAndView.addObject("carColorName", dictService.getName("CARCOLOR", user.getCarColor()));
		}
		return modelAndView;
	}

	/**
	 * 车主发布提交
	 * 
	 * @param user
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addCarOwnerInfoSubmit")
	// @AvoidDuplicateSubmission(needRemoveToken = true)
	@ResponseBody
	@ApiOperation(value = "车主发布信息提交", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid addCarOwnerInfoSubmit(CarOwnerInfo result, String userId) {
		Valid valid = null;
		try {
			User user = CookieUtil.readCookie(userId, null, null, loginService);
			if (user != null) {
				result.setAddTime(new Date());
				result.setAddUser(user.getId());
				result.setAge(user.getAge());
				result.setSex(user.getSex());
				result.setContacePhone(user.getPhone());
				result.setContactUser(user.getNickName());
				result.setCarColor(user.getCarColor());
				result.setCarStyle(user.getCarStyle());
				result.setCarType(user.getCarType());
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
				carOwnerInfoService.saveCarOwnerInfo(result);
				try {
					// 生成html
					genHtml(result, user);
					return valid = new Valid(true, String.valueOf(result.getId()));
				} catch (Exception e) {
					carOwnerInfoService.removeCarOwnerInfo(result.getId(), user);
					throw e;
				}
			} else {
				valid = new Valid(false, "保存失败：验证用户失败");
			}
		} catch (Exception ex) {
			valid = new Valid(false, "保存失败。");
			ex.printStackTrace();
		}
		return valid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void genHtml(CarOwnerInfo result, User user) throws Exception {
		Writer writer = null;
		InputStream in = null;
		File htmlFile = null;
		try {

			result.setFromZoneName(dictService.getName("ZONE", result.getFromZone()));
			result.setToZoneName(dictService.getName("ZONE", result.getToZone()));
			result.setSex(dictService.getName("SEX", result.getSex()));
			result.setCarTypeName(dictService.getName("CARTYPE", result.getCarType()));
			result.setCarColorName(dictService.getName("CARCOLOR", result.getCarColor()));
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
			StringBuilder via = new StringBuilder();
			if (result.getVia1() != null && !"".equals(result.getVia1())) {
				via.append(result.getVia1()).append("，");
			}
			if (result.getVia2() != null && !"".equals(result.getVia2())) {
				via.append(result.getVia2()).append("，");
			}
			if (result.getVia3() != null && !"".equals(result.getVia3())) {
				via.append(result.getVia3()).append("，");
			}
			if (result.getVia4() != null && !"".equals(result.getVia4())) {
				via.append(result.getVia4()).append("，");
			}
			if (result.getVia5() != null && !"".equals(result.getVia5())) {
				via.append(result.getVia5()).append("，");
			}
			if (via.length() > 0) {
				result.setViaName(via.substring(0, via.length() - 1));
			} else {
				result.setViaName("无");
			}

			Properties prop = new Properties();
			in = LoginController.class.getResourceAsStream("/config/path.properties");
			prop.load(in);
			String path = prop.getProperty("html.carOwnerInfo.save.path");

			Configuration cfg = new Configuration();

			cfg.setClassForTemplateLoading(CarOwnerInfoController.class, "../templates");
			cfg.setDefaultEncoding("UTF-8");
			Template tmp = cfg.getTemplate("viewCarOwnerInfo.ftl");
			Map root = new HashMap();
			root.put("result", result);
			root.put("user", user);

			List<Image> images = new ArrayList<Image>();
			Image image = new Image();
			image.setId(result.getId());
			String largeName = user.getCarPhotoLarge1();
			image.setSmallName(user.getCarPhotoSmall1());
			image.setLargeName(largeName);
			image.setWidth(Integer.parseInt(largeName.split("_")[1]));
			image.setHeight(Integer.parseInt(largeName.split("_")[2]));
			images.add(image);

			image = new Image();
			image.setId(result.getId());
			largeName = user.getCarPhotoLarge2();
			image.setSmallName(user.getCarPhotoSmall2());
			image.setLargeName(largeName);
			image.setWidth(Integer.parseInt(largeName.split("_")[1]));
			image.setHeight(Integer.parseInt(largeName.split("_")[2]));
			images.add(image);

			root.put("images", images);

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
	@ApiOperation(value = "车主发布信息浏览次数+1", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	public Valid updateLookCount(Integer id) {
		Valid valid = null;
		try {
			Integer result = carOwnerInfoService.updateLookCount(id);
			valid = new Valid(true, String.valueOf(result));
		} catch (Exception ex) {
			valid = new Valid(false, "更新浏览数失败");
			ex.printStackTrace();
		}
		return valid;
	}

	/**
	 * 查看车主发布的信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("viewCarOwnerInfo")
	public ModelAndView viewCarOwnerInfo(Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView("/carOwnerInfo/viewCarOwnerInfo");
		CarOwnerInfo result = carOwnerInfoService.getCarOwnerInfo(id);
		if (result != null) {
			result.setFromZoneName(dictService.getName("ZONE", result.getFromZone()));
			result.setToZoneName(dictService.getName("ZONE", result.getToZone()));
			result.setSex(dictService.getName("SEX", result.getSex()));
			result.setCarTypeName(dictService.getName("CARTYPE", result.getCarType()));
			result.setCarColorName(dictService.getName("CARCOLOR", result.getCarColor()));
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
			StringBuilder via = new StringBuilder();
			if (result.getVia1() != null && !"".equals(result.getVia1())) {
				via.append(result.getVia1()).append("，");
			}
			if (result.getVia2() != null && !"".equals(result.getVia2())) {
				via.append(result.getVia2()).append("，");
			}
			if (result.getVia3() != null && !"".equals(result.getVia3())) {
				via.append(result.getVia3()).append("，");
			}
			if (result.getVia4() != null && !"".equals(result.getVia4())) {
				via.append(result.getVia4()).append("，");
			}
			if (result.getVia5() != null && !"".equals(result.getVia5())) {
				via.append(result.getVia5()).append("，");
			}
			if (via.length() > 0) {
				result.setViaName(via.substring(0, via.length() - 1));
			} else {
				result.setViaName("无");
			}
			carOwnerInfoService.updateLookCount(result.getId());
			modelAndView.addObject("result", result);
			User user = userService.getUser(result.getAddUser());
			modelAndView.addObject("user", user);

			User curUser = CookieUtil.readCookie(null, request, response, loginService);
			if (curUser != null) {
				// redisTemplate.opsForSet().add("readCarOwner",
				// String.valueOf(curUser.getId()));
			}
			modelAndView.addObject("curUser", curUser);
		}

		return modelAndView;
	}

	@RequestMapping("removeCarOwnerInfo")
	public void removeCarOwnerInfo(Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = CookieUtil.readCookie(null, request, response, loginService);
		carOwnerInfoService.removeCarOwnerInfo(id, user);
	}

	@RequestMapping("getPageInfo")
	@ResponseBody
	@ApiOperation(value = "车主发布信息分页展示", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Object[]> getPageInfo(CarOwnerInfo result, Integer page, String date) {
		return carOwnerInfoService.getPageInfo(result, page, date);
	}
}
