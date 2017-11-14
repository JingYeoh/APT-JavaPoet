package com.jkb.apt.annimator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * APT实战的注解.用于生成一个Hello类.
 *
 * @author JingYeoh.
 *         Date 17-11-14.
 *         Github https://github.com/justkiddingbaby
 *         Blog http://blog.justkiddingbaby.com
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface HelloAPT {
    String value() default "hello apt";
}
