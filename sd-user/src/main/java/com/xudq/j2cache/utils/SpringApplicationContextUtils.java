package com.xudq.j2cache.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
/**
 * Spring上下文工具类
 */
@Primary
@Component
public class SpringApplicationContextUtils {
    private static ApplicationContext springContext;
    @Autowired
    private ApplicationContext applicationContext;
    @PostConstruct
    private void init() {
        springContext = applicationContext;
    }

    /**
     * 获取当前ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return springContext;
    }
}