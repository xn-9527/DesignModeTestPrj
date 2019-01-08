package cn;

import com.my.helloworld.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * 测试我们自己的spring-boot-starter
 * pom中加入
 * <dependency>
 *   <groupId>com.my</groupId>
 *   <artifactId>spring-boot-starter-helloworld</artifactId>
 *   <version>1.0-SNAPSHOT</version>
 * </dependency>
 *
 * Created by chay on 2019/1/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MystarterApplicationTests {
    @Autowired
    private PersonService personService;

    @Test
    public void testHelloWorld() {
        System.out.println("test");
        personService.sayHello();
    }
}
