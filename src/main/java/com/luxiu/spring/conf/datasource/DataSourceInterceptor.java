package com.luxiu.spring.conf.datasource;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName DataSourceInterceptor
 * @date 2020/7/31 17:51
 * @company https://www.beyond.com/
 */

@Component
public class DataSourceInterceptor {
    public void setdataSource(JoinPoint jp) {
        DatabaseContextHolder.setCustomerType("dataSource");
    }

    public void setdataSourceTwo(JoinPoint jp) {
        DatabaseContextHolder.setCustomerType("dataSourceTwo");
    }
}
