package cn.chay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
//test
public class ConfigServerHealthApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConfigServerHealthApplication.class, args);
  }
}
