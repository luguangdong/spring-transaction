package com.luxiu.spring.transaction.manager.datasourcetwo;

import com.luxiu.spring.transaction.domain.Person;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName PersonManager
 * @date 2020/8/1 18:06
 * @company https://www.beyond.com/
 */
public interface PersonManager {
    void save(Person person);
}
