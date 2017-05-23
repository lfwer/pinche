package com.lfwer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lfwer.model.CarOwnerInfo;
import com.lfwer.model.PinkerInfo;
import com.lfwer.model.User;
import com.lfwer.service.CarOwnerInfoService;
import com.lfwer.sys.BaseDao;
import com.lfwer.sys.BaseServiceImpl;

@Service("carOwnerInfoService")
public class CarOwnerInfoServiceImpl extends BaseServiceImpl implements CarOwnerInfoService {
	@Autowired
	private BaseDao<CarOwnerInfo> carOwnerInfoDao;

	@Override
	public CarOwnerInfo getCarOwnerInfo(Integer id) {
		return carOwnerInfoDao.get(CarOwnerInfo.class, id);
	}

	@Override
	public void saveCarOwnerInfo(CarOwnerInfo result) {
		carOwnerInfoDao.save(result);
	}

	@Override
	public void updateLookCount(Integer id) {
		carOwnerInfoDao.executeHql("update CarOwnerInfo set lookCount=lookCount+1 where id=" + id);
	}

	@Override
	public void removeCarOwnerInfo(Integer id, User user) {
		if (user == null) {
			return;
		}
		CarOwnerInfo result = carOwnerInfoDao.get(CarOwnerInfo.class, id);
		// 只能自己删除自己的发布的拼车信息
		if (result != null && user.getId().equals(result.getAddUser())) {
			result.setDelTime(new Date());
			result.setState(0);
			carOwnerInfoDao.update(result);
		}
	}

	@Override
	public List getPageInfo(CarOwnerInfo result, Integer page, String date) {
		String d = date.split(" ")[0];
		String t = date.split(" ")[1];
		String sql = "select (select d.name from dict d where d.type='ZONE' and d.id=t.fromzone) fromzone,"
				+ "(select d.name from dict d where d.type='ZONE' and d.id=t.tozone) tozone,"
				+ "t.via1,t.via2,t.via3,t.via4,t.via5,date_format(t.pdate,'%m-%d'),t.ptime,"
				+ "(select d.name from dict d where d.type='SEX' and d.id=t.sex) sex," + "t.age,t.pnum,"
				+ "(select d.name from dict d where d.type='CARTYPE' and d.id=t.cartype) cartype,"
				+ "t.carstyle,(select name from dict d where d.type='CARCOLOR' and d.id=t.carcolor) carcolor,"
				+ "t.contactuser,t.id tid,u.id uid,u.photosmall,t.pweek1,t.pweek2,t.pweek3,t.pweek4,t.pweek5,t.pweek6,t.pweek7,t.timeLimit "
				+ "from carownerinfo t,user u where t.adduser=u.id and t.state='1' ";
		if (page > 1) {
			sql += "and t.pdate <= '" + d + "' and t.ptime <= '" + t + "'";
		}
		sql += " order by refreshTime desc";
		// sql += " order by pdate desc,ptime desc";
		List<Object> param = new ArrayList<Object>();
		return carOwnerInfoDao.findBySql(sql, param, page, 20);
	}
}
