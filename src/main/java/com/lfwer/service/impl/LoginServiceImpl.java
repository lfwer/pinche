package com.lfwer.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.lfwer.common.Valid;
import com.lfwer.common.RandomUtil;
import com.lfwer.controller.LoginController;
import com.lfwer.model.SMS;
import com.lfwer.model.User;
import com.lfwer.service.LoginService;
import com.lfwer.sys.BaseDao;
import com.lfwer.sys.BaseServiceImpl;

@Service("loginService")
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
	@Autowired
	private BaseDao<SMS> registerSMSDao;
	@Autowired
	private BaseDao<User> userDao;

	public Valid genSMS(String phone) {
		// 生成一个验证码
		String code = RandomUtil.getRandNum(6);
		SMS result = new SMS();
		result.setAddTime(new Date());
		result.setCode(code);
		result.setPhone(phone);
		// 保存验证码
		registerSMSDao.save(result);
		// 发送验证码
		Valid data = sendSms(result.getPhone(), result.getCode());
		if (!data.isValid()) {
			registerSMSDao.delete(result);
		}
		return data;
	}

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 *            手机
	 * @param code
	 *            验证码
	 * @return
	 */
	private Valid sendSms(String phone, String code) {
		Valid info = new Valid();
		HashMap<String, Object> result = null;
		try {
			Properties prop = new Properties();
			InputStream in = LoginController.class.getResourceAsStream("/config/sms.properties");
			prop.load(in);
			CCPRestSDK restAPI = new CCPRestSDK();
			restAPI.init(prop.getProperty("serverIP").trim(), prop.getProperty("serverPort").trim());// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
			restAPI.setAccount(prop.getProperty("accountSid").trim(), prop.getProperty("accountToken").trim());// 初始化主帐号和主帐号TOKEN
			restAPI.setAppId(prop.getProperty("appId").trim());// 初始化应用ID
			result = restAPI.sendTemplateSMS(phone, prop.getProperty("templateId").trim(), new String[] { code, "15" });// 短信时效15分钟,如果修改，则修改修改数据库事件：evt_sms
			if ("000000".equals(result.get("statusCode"))) {
				// 正常返回输出data包体信息（map）
				@SuppressWarnings("unchecked")
				HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
				Set<String> keySet = data.keySet();
				for (String key : keySet) {
					Object object = data.get(key);
					System.out.println(key + " = " + object);
				}
				info.setValid(true);
				info.setMessage("短信已发送，请注意查看");
			} else {
				// 异常返回输出错误码和错误信息
				info.setValid(false);
				info.setMessage(String.valueOf(result.get("statusMsg")));
				System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	public boolean validatePhone(String phone) {
		Long result = userDao.count("select count(t.id) from User t where t.phone=?", new String[] { phone });
		return result == null || result == 0 ? false : true;
	}

	public boolean validateUsername(String username) {
		Long result = userDao.count("select count(t.id) from User t where t.username=? or t.phone=?",
				new String[] { username, username });
		return result == null || result == 0 ? false : true;
	}

	public boolean validatePassword(String username, String password) {
		List<User> list = userDao.find("from User t where t.username=? or t.phone=?",
				new String[] { username, username });
		if (list == null || list.isEmpty() || list.size() > 1) {
			return false;
		} else {
			User result = list.get(0);
			if (result.getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		}

	}

	public boolean validateSMS(String phone, String smsCode) {
		Long result = userDao.count("select count(t.id) from SMS t where t.phone=? and t.code=?",
				new String[] { phone, smsCode });
		return result == null || result == 0 ? false : true;
	}

	public Integer saveUser(User user) {
		return (Integer) userDao.save(user);
	}

	public void updateUser(User user) {
		userDao.update(user);
	}

	public List<User> find(String hql, Object[] param) {
		return userDao.find(hql, param);
	}

	public void retrievePwd(String phone, String password) {
		userDao.executeHql("update User t set t.password=? where t.phone=?", new String[] { password, phone });
	}

	public boolean validateNickName(String nickName, Integer id) {
		Long result = userDao.count("select count(t.id) from User t where t.nickName=? and t.id<>?",
				new Object[] { nickName.trim(), id });
		return result == null || result == 0 ? true : false;
	}

	@Override
	public void updateUserPhoto(Integer id, String photoSmall, String photoLarge) {
		userDao.executeHql("update User t set t.photoSmall=?,t.photoLarge=? where t.id=?",
				new Object[] { photoSmall, photoLarge, id });
	}

	@Override
	public void updateCarPhoto(Integer id, String smallFilename, String largeFilename, int type) {
		switch (type) {
		case 1:
			userDao.executeHql("update User t set t.carPhotoSmall1=?,t.carPhotoLarge1=? where t.id=?",
					new Object[] { smallFilename, largeFilename, id });
			break;
		case 2:
			userDao.executeHql("update User t set t.carPhotoSmall2=?,t.carPhotoLarge2=? where t.id=?",
					new Object[] { smallFilename, largeFilename, id });
			break;
		}

	}

	@Override
	public void updateDrivingBookPhoto(Integer id, String smallFilename, String largeFilename) {
		userDao.executeHql("update User t set t.drivingBookPhotoSmall=?,t.drivingBookPhotoLarge=? where t.id=?",
				new Object[] { smallFilename, largeFilename, id });
	}

}
