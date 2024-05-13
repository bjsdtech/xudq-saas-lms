package com.xudq.j2cache.annotation;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.ibatis.logging.stdout.StdOutImpl;

import java.lang.annotation.*;

/**
 * 缓存注解
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    String region() default "rx";
    String key() default "";
    String params() default "";
}
