package com.lsq.web.annotation;

import java.lang.annotation.*;

/**
 * Created by liushiquan on 2019/1/7.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";
    RequestMethod[] method() default {RequestMethod.GET,RequestMethod.POST};
}
