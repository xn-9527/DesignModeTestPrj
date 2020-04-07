package cn.chay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Created by xiaoni on 2020/4/7.
 */
@SpringBootApplication
public class DatasourceApplication {
    public static void main(String[] args) {
        System.out.println("dataSourceApplication start success");
        SpringApplication.run(DatasourceApplication.class, args);
    }
}
