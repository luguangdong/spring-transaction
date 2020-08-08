package com.luxiu.spring.service.impl;

import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.domain.TbContentCategory;
import com.luxiu.spring.mapper.datasourceone.TbContentCategoryMapper;
import com.luxiu.spring.service.TbContentCategoryService;
import com.luxiu.spring.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * <p>
 * Description: 测试git合并部分代码
 *
 * </p>
 *
 * @author luguangdong
 * @version 1.0
 * @ClassName TbContentCategoryServiceRollbackOnlyImpl
 * @date 2020/5/23 23:11
 * @company https://www.singlewindow.cn/
 */
@Transactional
@Service(value = "tbContentCategoryServiceRollbackOnly")
public class TbContentCategoryServiceRollbackOnlyImpl implements TbContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Autowired
	private TbContentService tbContentService;

	public void save(TbContentCategory tbContentCategory, TbContent tbContent) {
		try {
			tbContentCategoryMapper.insert(tbContentCategory);
			tbContentService.save(tbContent);
		}
		catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}

	}

}
