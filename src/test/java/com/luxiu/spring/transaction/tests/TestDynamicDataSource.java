package com.luxiu.spring.transaction.tests;

import com.luxiu.spring.conf.datasource.DatabaseContextHolder;
import com.luxiu.spring.transaction.domain.Person;
import com.luxiu.spring.transaction.domain.TbContent;
import com.luxiu.spring.transaction.manager.datasourceone.ContentManager;
import com.luxiu.spring.transaction.manager.datasourcetwo.PersonManager;
import com.luxiu.spring.transaction.mapper.TbContentMapper;
import com.luxiu.spring.transaction.mapper.PersonMapper;
import com.luxiu.spring.transaction.service.TbPersonService;
import com.luxiu.spring.transaction.service.TbContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * <p>
 * Description: 测试多数据源
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName TestDynamicDataSource
 * @date 2020/8/1 10:59
 * @company https://www.beyond.com/
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TestDynamicDataSource {


    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private TbPersonService personService;

    @Autowired
    private TbContentService tbContentService;


    @Autowired
    private PersonManager personManager;

    @Autowired
    private ContentManager contentManager;

    /**
     * 使用动态切换数据源,切割点配置在mapper层  <aop:pointcut id="daoOne" expression="execution(* com.luxiu.spring.transaction.mapper.*.*(..))" />
     * DataSourceInterceptor 拦截器切点拦截的方法是 mapper层
     * 测试单个插入没有问题
     */
    @Test
    public void testTransactionMapper() {
        TbContent tbContent = new TbContent();
        tbContent.setCategoryId(89L);
        tbContent.setTitle("主题");
        tbContent.setContent("内容");
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insertSelective(tbContent);


        Person person = new Person();
        person.setPid(123458L);
        person.setPname("哈哈");
        person.setGender("1");
        personMapper.insert(person);

    }

    /**
     * 使用动态切换数据源,切割点配置在mapper层  <aop:pointcut id="daoOne" expression="execution(* com.luxiu.spring.transaction.mapper.*.*(..))" />
     * DataSourceInterceptor 拦截器切点拦截的方法是 mapper层
     * 测试批量插入没有问题
     */
    @Test
    public void testTransactionMapperBatch() {
        for (long i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                TbContent tbContent = new TbContent();
                tbContent.setCategoryId(i);
                tbContent.setTitle("主题");
                tbContent.setContent("内容");
                tbContent.setCreated(new Date());
                tbContent.setUpdated(new Date());
                tbContentMapper.insert(tbContent);
            } else {
                Person person = new Person();
                person.setPid(i);
                person.setPname("哈哈");
                person.setGender("1");
                personMapper.insert(person);
            }
        }
    }


    /**
     * 使用动态切换数据源,切割点配置在service层 <aop:pointcut id="daoOne" expression="execution(* com.luxiu.spring.transaction.service.*.*(..))" />
     * DataSourceInterceptor 拦截器切点拦截的方法是 service层
     * 测试单个插入失败，手动设置数据源则成功
     */
    @Test
    public void testTransactionService() {
        //手动设置数据源
        //DatabaseContextHolder.setCustomerType("dataSourceTwo");
        Person person = new Person();
        person.setPid(123458L);
        person.setPname("哈哈");
        person.setGender("1");
        personService.save(person);

        //手动设置数据源
        //DatabaseContextHolder.setCustomerType("dataSource");
        TbContent tbContent = new TbContent();
        tbContent.setCategoryId(89L);
        tbContent.setTitle("主题");
        tbContent.setContent("内容");
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentService.save(tbContent);
    }

    /**
     * 使用动态切换数据源,切割点配置在service层 <aop:pointcut id="daoOne" expression="execution(* com.luxiu.spring.transaction.service.*.*(..))" />
     * DataSourceInterceptor 拦截器切点拦截的方法是 service层
     * 测试批量插入失败，手动设置数据源则成功
     */
    @Test
    public void testTransactionServiceBatch() {
        for (long i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                //手动设置数据源
                DatabaseContextHolder.setCustomerType("dataSource");
                TbContent tbContent = new TbContent();
                tbContent.setCategoryId(i);
                tbContent.setTitle("主题");
                tbContent.setContent("内容");
                tbContent.setCreated(new Date());
                tbContent.setUpdated(new Date());
                tbContentService.save(tbContent);
            } else {
                //手动设置数据源
                DatabaseContextHolder.setCustomerType("dataSourceTwo");
                Person person = new Person();
                person.setPid(i);
                person.setPname("哈哈");
                person.setGender("1");
                personService.save(person);
            }
        }
    }


    /**
     * 使用动态切换数据源,切割点配置在manager层  <aop:pointcut id="daoOne" expression="execution(* com.luxiu.spring.transaction.manager.datasourceone.*.*(..))" />
     * DataSourceInterceptor 拦截器切点拦截的方法是 manager层
     * 测试单个插入成功
     */
    @Test
    public void testTransactionManager() {
        Person person = new Person();
        person.setPid(123458L);
        person.setPname("哈哈");
        person.setGender("1");
        personManager.save(person);

        TbContent tbContent = new TbContent();
        tbContent.setCategoryId(89L);
        tbContent.setTitle("主题");
        tbContent.setContent("内容");
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        contentManager.save(tbContent);
    }
}