package com.luxiu.spring.transaction.service.impl;

import com.luxiu.spring.transaction.domain.TbContent;
import com.luxiu.spring.transaction.mapper.TbContentMapper;
import com.luxiu.spring.transaction.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TbContentServiceImpl implements TbContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    public void save(TbContent tbContent) {
        tbContentMapper.insert(tbContent);
    }
}
