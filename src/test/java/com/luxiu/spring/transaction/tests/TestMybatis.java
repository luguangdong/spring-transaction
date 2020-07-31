package com.luxiu.spring.transaction.tests;
import java.util.Date;

import com.luxiu.spring.conf.datasource.DatabaseContextHolder;
import com.luxiu.spring.transaction.domain.Person;

import com.luxiu.spring.transaction.domain.TbContent;
import com.luxiu.spring.transaction.readmapper.PersonMapper;
import com.luxiu.spring.transaction.mapper.TbContentCategoryMapper;
import com.luxiu.spring.transaction.mapper.TbContentMapper;
import com.luxiu.spring.transaction.service.TbContentService;
import com.luxiu.spring.transaction.readservice.TbPersonService;
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

    @Autowired
    private TbPersonService personService;

    @Autowired
    private TbContentService tbContentService;

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
        person.setPid(123458L);
        person.setPname("哈哈");
        person.setGender("1");
        personMapper.insert(person);

    }

    /**
     * 如果事务的拦截器配置在service层，那么切换数据源的拦截器DataSourceInterceptor就应该在上一层（三层的话就是controller层）
     *
     * 首先要确认事务拦截的地方，事务如果拦截service层，再service方法内部切换数据源是不起作用的，因为进入service后数据源和事务是绑定的。
     * 其次，使用注解时，要注意spring的applicationContext.xml与spring mvc的注解context:component-scan标签配置，会有冲突导致事务无法生效，
     * 即service层要排除注解@Controller
     *
     * 多数据源的配置要点就是一个动态数据源dynamicDataSource，对应的代码是DynamicDataSource、DatabaseContextHolder；
     * 拦截器dataSourceAspect只是为了实现自动化切换，并不是必要的，可以自己根据业务逻辑的需要在代码中手动切换。其余的部分与单数据源是一样的，无论是代码还是配置。
     */
    @Test
    public void testTbContentInsertBackId3() {

        for(long i = 1; i<=100; i++){
            if(i % 2 == 0){
                DatabaseContextHolder.setCustomerType("dataSource");//設置數據源
                TbContent tbContent = new TbContent();
                tbContent.setCategoryId(i);
                tbContent.setTitle("主题");
                tbContent.setContent("内容");
                tbContent.setCreated(new Date());
                tbContent.setUpdated(new Date());
                tbContentService.save(tbContent);
            }else {
                DatabaseContextHolder.setCustomerType("dataSourceTwo");//設置數據源
                Person person = new Person();
                person.setPid(i);
                person.setPname("哈哈");
                person.setGender("1");
                personService.save(person);
            }
        }




    }


}
