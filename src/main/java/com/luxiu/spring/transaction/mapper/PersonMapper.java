package com.luxiu.spring.transaction.mapper;

import com.luxiu.spring.transaction.domain.Person;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName PersonMapper
 * @date 2020/7/31 18:26
 * @company https://www.beyond.com/
 */
public interface PersonMapper {
    int deleteByPrimaryKey(Long pid);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Long pid);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}