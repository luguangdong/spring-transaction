package com.luxiu.spring.tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName TestRedis
 * @date 2020/8/5 20:20
 * @company https://www.beyond.com/
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-context.xml", "classpath:spring-context-druid.xml",
		"classpath:spring-context-mybatis.xml", "classpath:spring-context-redis.xml" })
public class TestRedis {

	@Resource
	private RedisTemplate redisTemplate;

	@Test
	public void testSet() throws UnsupportedEncodingException {
		redisTemplate.opsForValue().set("stock", new String("100".getBytes(), "utf-8"));
	}
}
