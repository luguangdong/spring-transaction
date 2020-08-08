package com.luxiu.spring.service;

import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.domain.TbContentCategory;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0
 * @ClassName TbContentCategoryService
 * @date 2020/5/23 22:02
 * @company https://www.singlewindow.cn/
 */
public interface TbContentCategoryService {

	void save(TbContentCategory tbContentCategory, TbContent tbContent);

}
