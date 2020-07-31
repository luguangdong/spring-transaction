package com.luxiu.spring.transaction.readservice;

import com.luxiu.spring.transaction.domain.Person;


/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName TbPersionService
 * @date 2020/7/31 22:22
 * @company https://www.beyond.com/
 */
public interface TbPersonService {
    void save(Person person);
}
