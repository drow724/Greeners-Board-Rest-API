package com.greeners.rest.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.greeners.rest.api.model.ParamsPost;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForbiddenWordCheck {
    String param() default "paramsPost.content";
    Class<?> checkClazz() default ParamsPost.class;
}