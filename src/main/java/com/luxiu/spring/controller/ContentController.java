package com.luxiu.spring.controller;

import cn.hutool.json.JSONUtil;
import com.luxiu.spring.conf.response.ResponseResult;
import com.luxiu.spring.domain.Person;
import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.manager.datasourceone.ContentManager;
import com.luxiu.spring.manager.datasourcetwo.PersonManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	public static final Logger logger = LoggerFactory.getLogger(ContentController.class);

	@Autowired
	private PersonManager personManager;

	@Autowired
	private ContentManager contentManager;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseResult save(@RequestBody TbContent tbContent) {
		logger.info("save方法入参tbContent={}", JSONUtil.parseObj(tbContent, false));
		contentManager.save(tbContent);
		Person person = new Person();
		person.setPid(456L);
		person.setPname("小明");
		person.setGender("1");
		int i = 1 / 0;
		personManager.save(person);
		logger.info("save方法保存成功");
		return ResponseResult.success("创建成功");

	}

}
