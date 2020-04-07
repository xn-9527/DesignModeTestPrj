package cn.chay.datasource;

import java.lang.annotation.*;

/**
 * @author Chay
 * @date 2020/4/7 14:12
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
   DataSourceEnum value() default DataSourceEnum.DB1;
}
