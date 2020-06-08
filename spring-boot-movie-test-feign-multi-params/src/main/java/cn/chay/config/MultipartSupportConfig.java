package cn.chay.config;

import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import feign.codec.Encoder;
/**
 * feign上传文件的配置
 *
 * @author Created by xiaoni on 2019/11/21.
 */
public class MultipartSupportConfig {
    @Bean(name = "multipartEncoder")
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
