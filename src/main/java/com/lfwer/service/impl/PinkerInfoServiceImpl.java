package com.lfwer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lfwer.model.PinkerInfo;
import com.lfwer.model.User;
import com.lfwer.service.PinkerInfoService;
import com.lfwer.sys.BaseDao;
import com.lfwer.sys.BaseServiceImpl;

@Service("pinkerInfoService")
public class PinkerInfoServiceImpl extends BaseServiceImpl implements PinkerInfoService {
	@Autowired
	private BaseDao<PinkerInfo> pinkerInfoDao;

	@Override
	public PinkerInfo getPinkerInfo(Integer id) {
		return pinkerInfoDao.get(PinkerInfo.class, id);
	}

	@Override
	public void savePinkerInfo(PinkerInfo result) {
		pinkerInfoDao.save(result);
	}

	@Override
	public Integer updateLookCount(Integer id) {
		pinkerInfoDao.executeHql("update PinkerInfo set lookCount=lookCount+1 where id=" + id);
		return (Integer)pinkerInfoDao.uniqueResult("select lookCount from PinkerInfo where id = "+id);
	}

	@Override
	public void removePinkerInfo(Integer id, User user) {
		if (user == null) {
			return;
		}
		PinkerInfo result = pinkerInfoDao.get(PinkerInfo.class, id);
		// 只能自己删除自己的发布的拼车信息
		if (result != null && user.getId().equals(result.getAddUser())) {
			result.setDelTime(new Date());
			result.setState(0);
			pinkerInfoDao.update(result);
		}
	}

	@Override
	public List<Object[]> getPageInfo(PinkerInfo result, Integer page,String date) {
		String d = date.split(" ")[0];
		String t = date.split(" ")[1];
		String sql = "select (select d.name from dict d where d.type='ZONE' and d.id=t.fromzone) fromzone,"
				+ "t.onsite,(select d.name from dict d where d.type='ZONE' and d.id=t.tozone) tozone,"
				+ "t.offsite,date_format(t.pdate,'%m-%d'),t.ptime," + "(select name from dict d where d.type='SEX' and d.id=t.sex) sex,"
				+ "t.age,t.pnum,t.contactuser,t.id tid,u.id uid,u.photosmall,t.pweek1,t.pweek2,t.pweek3,t.pweek4,t.pweek5,t.pweek6,t.pweek7,t.timeLimit "
				+ "from pinkerinfo t,user u where t.adduser=u.id and t.state='1'";
		if (page > 1) {
			sql += "and t.pdate <= '" + d + "' and t.ptime <= '" + t + "'";
		}
		sql += " order by refreshTime desc";
		//sql += " order by pdate desc,ptime desc";
		List<Object> param = new ArrayList<Object>();
		return pinkerInfoDao.findBySql(sql, param, page, 20);
	}

}
