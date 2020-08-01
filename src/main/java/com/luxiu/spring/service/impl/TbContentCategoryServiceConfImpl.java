package com.luxiu.spring.service.impl;

import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.domain.TbContentCategory;
import com.luxiu.spring.mapper.TbContentCategoryMapper;
import com.luxiu.spring.service.TbContentCategoryService;
import com.luxiu.spring.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0
 * @ClassName TbContentCategoryServiceImpl
 * @date 2020/5/23 22:03
 * @company https://www.singlewindow.cn/
 */
@Service(value = "TbContentCategoryServiceConf")
class TbContentCategoryServiceConfImpl implements TbContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private TbContentService tbContentService;
    public void save(TbContentCategory tbContentCategory, TbContent tbContent) {
        tbContentCategoryMapper.insert(tbContentCategory);
        tbContentService.save(tbContent);
    }
}
