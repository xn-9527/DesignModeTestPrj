package cn.chay.movie.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiaoni on 2019/11/21.
 */
@Configuration
public class FeignLogConfiguration {
    /**
     * No logging.
     * NONE,
     * Log only the request method and URL and the response status code and execution time.
     * BASIC,
     * Log the basic information along with request and response headers.
     * HEADERS,
     * Log the headers, body, and metadata for both requests and responses.
     * FULL
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
