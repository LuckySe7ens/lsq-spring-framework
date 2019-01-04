package com.lsq.annotation;

import java.lang.annotation.*;

/**
 * Created by liushiquan on 2019/1/4.
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    String value() default "";
}