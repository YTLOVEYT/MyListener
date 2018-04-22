package com.example.mylistener.injector.ano;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by YinTao on 2018/3/26.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity
{
}
