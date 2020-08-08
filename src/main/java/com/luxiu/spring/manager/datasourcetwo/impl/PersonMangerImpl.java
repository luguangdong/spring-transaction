package com.luxiu.spring.manager.datasourcetwo.impl;

import com.luxiu.spring.domain.Person;
import com.luxiu.spring.manager.datasourcetwo.PersonManager;
import com.luxiu.spring.service.TbPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName PersonMangerImpl
 * @date 2020/8/1 18:06
 * @company https://www.beyond.com/
 */
@Service
public class PersonMangerImpl implements PersonManager {

	@Autowired
	private TbPersonService personService;

	public void save(Person person) {
		personService.save(person);
	}

}
