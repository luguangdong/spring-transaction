package com.luxiu.spring.transaction.readservice.impl;

import com.luxiu.spring.transaction.domain.Person;
import com.luxiu.spring.transaction.readmapper.PersonMapper;
import com.luxiu.spring.transaction.readservice.TbPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TbPersonServiceImpl implements TbPersonService {
    @Autowired
    private PersonMapper personMapper;
    public void save(Person person) {
        personMapper.insertSelective(person);

    }
}
