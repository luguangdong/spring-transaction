package com.luxiu.spring.service.impl;

import com.luxiu.spring.domain.Person;
import com.luxiu.spring.mapper.datasourcetwo.PersonMapper;
import com.luxiu.spring.service.TbPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName TbPersonServiceImpl
 * @date 2020/7/31 22:24
 * @company https://www.beyond.com/
 */
@Service
@Transactional
public class TbPersonServiceImpl implements TbPersonService {
    @Autowired
    private PersonMapper personMapper;
    public void save(Person person) {
        personMapper.insertSelective(person);

    }
}
