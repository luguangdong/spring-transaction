package com.luxiu.spring.conf.datasource;

/**
 * <p>
 * Description: 设置具体数据源数
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName DatabaseContextHolder
 * @date 2020/7/31 17:46
 * @company https://www.beyond.com/
 */
public class DatabaseContextHolder {
    /**
     * 注意：数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     *
     * @param customerType
     * 设置对应的数据源
     * customerType 对应的值就是 spring-context-druid.xml 中  <entry value-ref="dataSource" key="dataSource"></entry> 中的 dataSource
     *
     */
    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    public static String getCustomerType() {
        return contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}
