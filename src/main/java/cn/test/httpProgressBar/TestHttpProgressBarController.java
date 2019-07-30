package cn.test.httpProgressBar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoni on 2019/7/30.
 */
@RestController
@RequestMapping("test/http/progress")
@Slf4j
public class TestHttpProgressBarController {

    @GetMapping
    public void test(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers) {
        ServletOutputStream outputStream = null;
        try {
            log.info("taaaaaa");
//            response.setHeader("Content-Length", "192");
            //设置内容类型为下载类型
            response.setContentType("application/x-download");
            //设置请求头 和 文件下载名称
            response.addHeader("Content-Disposition","attachment;filename=test.txt");
//            PrintWriter out = response.getWriter();
            outputStream = response.getOutputStream();
            for (int i = 0;i< 100; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String data = i + "fdsfasdfdsgadfsgdfgjkfdngmnfdgnfdmngfdjhgjkfdhgjkhkjhrwejkrh,";
                log.info("loop : {}" , data);
//                out.print(data);
//                out.flush();
                outputStream.write(String.valueOf(data).getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return "a";
    }

    public static void main(String[] args) {
        try {
            String strURL = "http://localhost:8043/test/http/progress";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            httpConn.disconnect();

            log.info(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
