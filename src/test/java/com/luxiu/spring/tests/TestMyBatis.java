package com.luxiu.spring.tests;
import java.util.Date;
import java.util.List;


import com.luxiu.spring.conf.page.Pagination;
import com.luxiu.spring.domain.Person;

import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.mapper.datasourcetwo.PersonMapper;
import com.luxiu.spring.mapper.datasourceone.TbContentCategoryMapper;
import com.luxiu.spring.mapper.datasourceone.TbContentMapper;
import com.luxiu.spring.service.TbContentService;
import com.luxiu.spring.service.TbPersonService;
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
public class TestMyBatis {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private TbPersonService personService;

    @Autowired
    private TbContentService tbContentService;

    /**
     * mysql在mapper中设置主键自增返回时,实体中返回主键
     */
    @Test
    public void testMySqlInsertBackId() {
        TbContent tbContent = new TbContent();
        tbContent.setCategoryId(89L);
        tbContent.setTitle("主题");
        tbContent.setContent("内容");
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insertSelective(tbContent);
        System.out.println(tbContent.getId());

    }

    /**
     *  oracle在mapper中设置主键自增返回时,实体中返回主键
     */
    @Test
    public void testOracleInsertBackId() {
        Person person = new Person();
        person.setPname("哈哈");
        person.setGender("1");
        personMapper.insertSelective(person);
        System.out.println(person.getPid());

    }


    /**
     * 自定义mysql分页查询
     * mysql中分页是从0开始的 SELECT * from tb_content limit 0,2;
     * 所以便于理解当前页 pageNumber = (pageNumber-1)* pageSize
     */
    @Test
    public void testMySqlSelectByPage() {
        TbContent tbContent = new TbContent();
        // 每页条数
        Integer pageSize=3;
        //当前页码从1开始
        Integer pageNumber =1;

        tbContent.setPageSize(pageSize);
        tbContent.setPageNumber((pageNumber-1)*pageSize);
        Long totalCount = tbContentMapper.findTotalCount();
        System.out.println(totalCount);
        List<TbContent> pages = tbContentMapper.findByPage(tbContent);
        System.out.println(pages);
    }

    /**
     * 自定义mysql分页带条件动态查询
     * mysql中分页是从0开始的 SELECT * from tb_content limit 0,2;
     * 所以便于理解当前页 pageNumber = (pageNumber-1)* pageSize
     */
    @Test
    public void testMySqlSelectByPageOnCondition() {
        TbContent tbContent = new TbContent();
        tbContent.setTitle("a");
        // 每页条数
        Integer pageSize=3;
        //当前页码从1开始
        Integer pageNumber =1;
        tbContent.setPageSize(pageSize);
        tbContent.setPageNumber((pageNumber-1)*pageSize);
        Long countOnCondition = tbContentMapper.findTotalCountOnCondition(tbContent);
        System.out.println(countOnCondition);
        List<TbContent> pages = tbContentMapper.findByPageOnCondition(tbContent);
        System.out.println(pages);
    }

    /**
     * 自定义oracle分页查询
     * oracle分页参数rownum是从1开始计数的
     * begin= (pageNumber - 1) * pageSize + 1;
     */

    @Test
    public void testOracleSelectByPage() {
        Person person = new Person();
        // 每页条数
        Integer pageSize=3;
        //当前页码从1开始
        Integer pageNumber =1;

        Integer begin= (pageNumber - 1) * pageSize + 1;
        Integer end= (pageNumber * pageSize);

        person.setPageSize(end);
        person.setPageNumber(begin);
        List<Person> page = personMapper.findByPage(person);
        System.out.println(page);


    }

    /**
     * 自定义oracle分页查询带条件动态查询
     * oracle分页参数rownum是从1开始计数的
     * begin= (pageNumber - 1) * pageSize + 1;
     */
    @Test
    public void testOracleSelectByPageOnCondition() {
        Person person = new Person();
        person.setPname("哈哈1");
        person.setGender("1");
        // 每页条数
        Integer pageSize=3;
        //当前页码从1开始
        Integer pageNumber =1;

        Integer begin= (pageNumber - 1) * pageSize + 1;
        Integer end= (pageNumber * pageSize);

        person.setPageSize(end);
        person.setPageNumber(begin);
        Long countOnCondition = personMapper.findTotalCountOnCondition(person);
        System.out.println(countOnCondition);
        List<Person> page = personMapper.findByPageOnCondition(person);
        System.out.println(page);
    }



}
