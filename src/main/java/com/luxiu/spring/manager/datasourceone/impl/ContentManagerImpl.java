package com.luxiu.spring.manager.datasourceone.impl;

import com.luxiu.spring.domain.TbContent;
import com.luxiu.spring.manager.datasourceone.ContentManager;
import com.luxiu.spring.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName ContentManagerImpl
 * @date 2020/8/1 18:01
 * @company https://www.beyond.com/
 */
@Service
public class ContentManagerImpl implements ContentManager {
    @Autowired
    private TbContentService contentService;
    public void save(TbContent tbContent) {
        contentService.save(tbContent);
    }
}
