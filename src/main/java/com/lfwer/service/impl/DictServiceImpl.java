package com.lfwer.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoaderListener;

import com.lfwer.model.Dict;
import com.lfwer.service.DictService;
import com.lfwer.sys.BaseDao;
import com.lfwer.sys.BaseServiceImpl;

@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl implements DictService {
	@Autowired
	private BaseDao<Dict> dictDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<Dict> getDicts(String type, String pid) {
		String code = "DICT_" + type + "_" + pid;
		List<Dict> dicts = (List<Dict>) ContextLoaderListener.getCurrentWebApplicationContext().getServletContext()
				.getAttribute(code);
		if (dicts == null) {
			if (pid != null) {
				dicts = dictDao.find("from Dict where type=? and pid=? order by orders", new Object[] { type, pid });
			} else {
				dicts = dictDao.find("from Dict where type=? order by orders", new Object[] { type });
			}
			ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().setAttribute(code, dicts);
		}
		return dicts;

	}

	@Override
	public Dict getDict(String type, String id) {
		return dictDao.get("from Dict t where t.type=? and id=?", new Object[] { type, id });

	}

	@Override
	public String getName(String type, String id) {
		Dict result = getDict(type, id);
		return result == null ? null : result.getName();
	}

}
