package com.luxiu.spring.transaction.tests;
import java.util.Date;

import com.luxiu.spring.transaction.domain.Person;

import com.luxiu.spring.transaction.domain.TbContent;
import com.luxiu.spring.transaction.readmapper.PersonMapper;
import com.luxiu.spring.transaction.mapper.TbContentCategoryMapper;
import com.luxiu.spring.transaction.mapper.TbContentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName TestMybatis
 * @date 2020/7/31 10:02
 * @company https://www.beyond.com/
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TestMybatis {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private PersonMapper personMapper;

    /**
     * 当在mapper中设置主键自增返回时,实体中返回主键
     */
    @Test
    public void testTbContentInsertBackId() {

        TbContent tbContent = new TbContent();
        tbContent.setCategoryId(89L);
        tbContent.setTitle("主题");
        tbContent.setContent("内容");
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insertSelective(tbContent);

        System.out.println(tbContent.getId());

    }


    @Test
    public void testTbContentInsertBackId2() {
        Person person = new Person();
        person.setPid(12345L);
        person.setPname("哈哈");
        person.setGender("1");
        personMapper.insert(person);

    }


}
