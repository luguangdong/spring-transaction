package com.luxiu.spring.conf.page;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName PageUtil
 * @date 2020/8/2 15:11
 * @company https://www.beyond.com/
 */
public class PageUtil {
    private PageUtil() {
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @return
     *
     *  limit [m],n;
     *  m—— [m]为可选，如果填写表示skip步长，即跳过m条。
     *  n—— 显示条数。指从第m+1条记录开始，取n条记录。
     *  初始记录行的偏移量是 0(而不是 1),所以便于理解传入的pagerNumber必须大于等于1。
     *  pageSize
     *  pageNumber = (pageNumber-1) * pageSize
     *  limit pageNumber,pageSize
     */
    public static Pagination getMysqlPagination(int pageNumber, int pageSize) {
        Pagination pagination = new Pagination();
        if(pageNumber < 1){
            pageNumber =1;
        }
        if(pageSize < 0){
            pageSize =0;
        }
        pagination.setPageNumber((pageNumber-1) * pageSize);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @return
     *
     *  rownum
     *  oracle分页参数rownum是从1开始计数的
     *  begin= (pageNumber - 1) * pageSize + 1;
     *  end= (pageNumber * pageSize);
     */
    public static Pagination getOraclePagination(int pageNumber, int pageSize) {
        Pagination pagination = new Pagination();
        if(pageNumber < 1){
            pageNumber =1;
        }
        if(pageSize < 0){
            pageSize =0;
        }
        pagination.setBegin((pageNumber - 1) * pageSize + 1);
        pagination.setEnd(pageNumber * pageSize);
        pagination.setPageNumber(pageNumber);
        pagination.setPageSize(pageSize);
        return pagination;
    }


}
