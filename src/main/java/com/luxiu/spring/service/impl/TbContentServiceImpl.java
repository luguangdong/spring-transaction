package com.luxiu.spring.service.impl;

import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.mapper.TbContentMapper;
import com.luxiu.spring.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0
 * @ClassName TbContentServiceImpl
 * @date 2020/5/23 22:06
 * @company https://www.singlewindow.cn/
 */
@Service
@Transactional
public class TbContentServiceImpl implements TbContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    public void save(TbContent tbContent) {
        tbContentMapper.insert(tbContent);
        //int i = 1/0;
    }
}
