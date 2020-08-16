package com.luxiu.spring.service;

import com.luxiu.spring.conf.page.Pagination;
import com.luxiu.spring.domain.User;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName UserService
 * @date 2020/8/15 0:30
 * @company https://www.beyond.com/
 */
public interface UserService {

	/**
	 * 新增
	 * @param user {@link User}
	 * @return {@code boolean}
	 */
	boolean create(User user);

	/**
	 * 删除
	 * @param id {@code Long} 主键
	 * @return {@code boolean}
	 */
	boolean remove(Long id);

	/**
	 * 更新（全量）
	 * @param user {@link User}
	 * @return {@code boolean}
	 */
	boolean update(User user);

	/**
	 * 获取
	 * @param id {@code Long} 主键
	 * @return {@link User}
	 */
	User get(Long id);

	/**
	 * 分页
	 * @param current {@code int} 页码
	 * @param size {@code int} 笔数
	 * @param value {@code Object} 任意条件
	 * @return {@code IPage} 管理员分页数据
	 */
	Pagination<User> page(int current, int size, Object value);

}
