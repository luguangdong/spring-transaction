package com.luxiu.spring.transaction.controller;

import com.luxiu.spring.transaction.domain.Person;
import com.luxiu.spring.transaction.domain.TbContent;
import com.luxiu.spring.transaction.manager.datasourceone.ContentManager;
import com.luxiu.spring.transaction.manager.datasourcetwo.PersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName ContentController
 * @date 2020/8/1 16:07
 * @company https://www.beyond.com/
 */
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private PersonManager personManager;

    @Autowired
    private ContentManager contentManager;
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public void save(@RequestBody TbContent tbContent){
        contentManager.save(tbContent);
        Person person = new Person();
        person.setPid(456L);
        person.setPname("小明");
        person.setGender("1");
        personManager.save(person);
    }
}
