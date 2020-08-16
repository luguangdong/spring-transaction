package com.luxiu.spring.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luxiu.spring.conf.page.Pagination;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.luxiu.spring.mapper.datasourcetwo.UserMapper;
import com.luxiu.spring.domain.User;
import com.luxiu.spring.service.UserService;

import java.util.List;

import static com.luxiu.spring.conf.page.PageUtil.pageInfo2Pagination;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName UserServiceImpl
 * @date 2020/8/15 0:30
 * @company https://www.beyond.com/
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public boolean create(User user) {
		Integer result = userMapper.insertSelective(user);
		return this.retBool(result);
	}

	@Override
	public boolean remove(Long id) {
		Integer result = userMapper.deleteByPrimaryKey(id);
		return this.retBool(result);
	}

	@Override
	public boolean update(User user) {
		Integer result = userMapper.updateByPrimaryKeySelective(user);
		return this.retBool(result);
	}

	@Override
	public User get(Long id) {
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}

	@Override
	public Pagination<User> page(int current, int size, Object value) {
		//执行count(*)操作
		PageHelper.startPage(current,size,true);
		List<User> userList = userMapper.findByConditions((User) value);
		PageInfo pageInfo = new PageInfo(userList);
		Pagination pagination = pageInfo2Pagination(pageInfo);
		return pagination;
	}

	/**
	 * 判断数据库操作是否成功
	 * @param result 数据库操作返回影响条数
	 * @return boolean
	 */
	private static boolean retBool(Integer result) {
		return null != result && result >= 1;
	}

}
