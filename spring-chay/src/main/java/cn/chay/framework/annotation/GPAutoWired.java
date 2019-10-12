package cn.chay.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by xiaoni on 2019/10/13.
 */
@Target({ElementType.FIELD}) //应用范围，只能在字段上使用
@Retention(RetentionPolicy.RUNTIME) //作用域，生命周期只能在运行时
@Documented//可以识别内容
public @interface GPAutoWired {
    String value() default "";
}
