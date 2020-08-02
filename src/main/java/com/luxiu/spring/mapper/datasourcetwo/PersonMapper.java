package com.luxiu.spring.mapper.datasourcetwo;

import com.luxiu.spring.conf.page.Pagination;
import com.luxiu.spring.domain.Person;

import java.util.List;

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

    Long findTotalCount();

    List<Person> findByPage(Person record);

    Long findTotalCountOnCondition(Person record);

    List<Person> findByPageOnCondition(Person record);

    List<Person> findAll(Person person);

}