package com.luxiu.spring.tests;

import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.domain.TbContentCategory;
import com.luxiu.spring.service.TbContentCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * <p>
 * Description: 123
 * </p>
 *
 * @author luguangdong
 * @version 1.0
 * @ClassName TestSpringTransaction
 * @date 2020/5/23 22:12
 * @company https://www.singlewindow.cn/
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-context.xml", "classpath:spring-context-druid.xml",
		"classpath:spring-context-mybatis.xml" })
public class TestSpringTransaction {

	@Resource(name = "TbContentCategoryServiceConf")
	private TbContentCategoryService TbContentCategoryServiceConf;

	@Resource(name = "TbContentCategoryServiceAnnotation")
	private TbContentCategoryService TbContentCategoryServiceAnnotation;

	@Resource(name = "tbContentCategoryServiceRollbackOnly")
	private TbContentCategoryService tbContentCategoryServiceRollbackOnly;

	/**
	 * 测试说明: 使用配置文件的方式来测试spring的事物 <ur>
	 * <li>将spring-context.xml配置文件中关于事务的配置开启</li>
	 * <p>
	 * 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启 tbContent.setTitle("测试事务内容"); 测试结果:
	 * tb_content_category 表中的数据回滚,数据没有插入表中,事物生效
	 *
	 * </p>
	 * <li>将spring-context.xml配置文件中关于事务的配置关闭</li>
	 * <p>
	 * 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启 tbContent.setTitle("测试事务内容"); 测试结果:
	 * tb_content_category 表中的数据没有回滚,数据插入表中
	 * </p>
	 * </ur>
	 */
	@Test
	public void testTbContentCategoryServiceConf() {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setId(1L);
		tbContentCategory.setName("测试事务分类123");

		TbContent tbContent = new TbContent();
		tbContent.setCategoryId(45L);
		tbContent.setTbContentCategory(tbContentCategory);
		// 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启
		tbContent.setTitle("测试事务内容");

		TbContentCategoryServiceConf.save(tbContentCategory, tbContent);
	}

	/**
	 * 测试说明: 使用注解的方式来测试spring的事物 <ur>
	 * <li>将spring-context.xml配置文件中关于事务的配置开启</li>
	 * <p>
	 * 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启 tbContent.setTitle("测试事务内容"); 测试结果:
	 * tb_content_category 表中的数据回滚,数据没有插入表中,事物生效
	 *
	 * </p>
	 * <li>将spring-context.xml配置文件中关于事务的配置关闭</li>
	 * <p>
	 * 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启 tbContent.setTitle("测试事务内容"); 测试结果:
	 * tb_content_category 表中的数据没有回滚,数据插入表中
	 * </p>
	 * </ur>
	 */
	@Test
	public void testTbContentCategoryServiceAnnotation() {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setId(1L);
		tbContentCategory.setName("测试事务分类");

		TbContent tbContent = new TbContent();
		tbContent.setCategoryId(48L);
		tbContent.setTbContentCategory(tbContentCategory);
		// 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启
		tbContent.setTitle("测试事务内容");

		TbContentCategoryServiceAnnotation.save(tbContentCategory, tbContent);
	}

	/**
	 * Spring事务嵌套引发的血案测试 Transaction rolled back because it has been marked as
	 * rollback-only
	 */
	@Test
	public void testTbContentCategoryServiceByRollbackOnly() {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setId(1L);
		tbContentCategory.setName("测试事务分类20200706");

		TbContent tbContent = new TbContent();
		tbContent.setCategoryId(48L);
		tbContent.setTbContentCategory(tbContentCategory);
		// 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启
		tbContent.setTitle("测试事务内容20200706");
		tbContentCategoryServiceRollbackOnly.save(tbContentCategory, tbContent);
	}

}
