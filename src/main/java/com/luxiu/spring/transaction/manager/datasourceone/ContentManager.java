package com.luxiu.spring.transaction.manager.datasourceone;

import com.luxiu.spring.transaction.domain.TbContent;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName ContentManager
 * @date 2020/8/1 18:01
 * @company https://www.beyond.com/
 */
public interface ContentManager {
    void save(TbContent tbContent);
}
