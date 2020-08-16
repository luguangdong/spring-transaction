package com.luxiu.spring.mapper.datasourcetwo;

import com.luxiu.spring.domain.User;

import java.util.List;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName UserMapper
 * @date 2020/8/15 0:30
 * @company https://www.beyond.com/
 */
public interface UserMapper {

	int deleteByPrimaryKey(Long id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> findByConditions(User record);

}