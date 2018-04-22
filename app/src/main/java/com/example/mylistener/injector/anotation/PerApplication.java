package com.example.mylistener.injector.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 自定义注解
 * Created by YinTao on 2018/3/22.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication
{
}
