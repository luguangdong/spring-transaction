package com.luxiu.spring.mapper.datasourceone;

import com.luxiu.spring.conf.page.Pagination;
import com.luxiu.spring.domain.TbContent;

import java.util.List;


/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0
 * @ClassName TbContentMapper
 * @date 2020/5/23 21:58
 * @company https://www.singlewindow.cn/
 */

public interface TbContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbContent record);

    int insertSelective(TbContent record);

    TbContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbContent record);

    int updateByPrimaryKey(TbContent record);

    Long findTotalCount();

    List<TbContent> findByPage(TbContent record);

    Long findTotalCountOnCondition(TbContent record);

    List<TbContent> findByPageOnCondition(TbContent record);
}