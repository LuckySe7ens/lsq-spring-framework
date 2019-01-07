package com.lsq.context.annotation;

import java.lang.annotation.*;

/**
 * Created by liushiquan on 2019/1/4.
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

    String value() default "";
}
