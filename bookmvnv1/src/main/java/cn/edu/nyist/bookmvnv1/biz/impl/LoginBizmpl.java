package cn.edu.nyist.bookmvnv1.biz.impl;

import cn.edu.nyist.bookmvnv1.biz.LoginBiz;
import cn.edu.nyist.bookmvnv1.dao.LoginDao;
import cn.edu.nyist.bookmvnv1.dao.impl.loginDaoJdbcImpl;

public class LoginBizmpl implements LoginBiz {

	@Override
	public boolean findLoginByNameAndPwd(String name, String pwd) {
		LoginDao loginDao = new loginDaoJdbcImpl();
		return loginDao.get(name , pwd);
	}

}
