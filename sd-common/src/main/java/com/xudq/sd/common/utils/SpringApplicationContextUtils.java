package com.xudq.sd.common.utils;

//import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//import javax.annotation.PostConstruct;

/**
 * Spring上下文工具类
 *
 * @author: xudq
 * @date: 2022-06-26 14:34
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
