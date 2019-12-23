package cn.chay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerOrinApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConfigServerOrinApplication.class, args);
  }
}
