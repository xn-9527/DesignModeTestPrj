package cn.test.httpProgressBar;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chay on 2019/7/30.
 * 只能用谷歌浏览器看效果
 *
 * 从源码可以发现：
 * 虽然原生的OutputStream.flush()什么都不做，但是response里面的输出流默认是org.apache.catalina.connector.OutputBuffer，也就是带缓存的，所以在调用flush方法的时候，输出会带缓存，刷新是有效的。
 */
@RestController
@RequestMapping("test/http/progress")
@Slf4j
public class TestHttpProgressBarController {

    @GetMapping
    public void test(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers) {
        try {
            /**
             * 方法1 BufferedWriter 可以分块返回
             */
//            response.setContentType("text/html;charset=utf-8");
//            BufferedWriter bw = new BufferedWriter(response.getWriter());

            /**
             * 方法2 BufferedOutputStream 可以分块返回
             */
//            response.setContentType("text/html;charset=utf-8");
//            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream(), 1024);

            /**
             * 方法3 PrintWriter 可以分块返回
             */
//            response.setContentType("text/html;charset=utf-8");
//            PrintWriter out = response.getWriter();

            /**
             * 方法4 ServletOutputStream 可以分块返回
             */
            response.setContentType("text/html;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();

            //可以设置头文件长度，如果达到长度，输出流自动关闭；如果总数据长度达不到，输出流全部完成后关闭；
//            response.setHeader("Content-Length", "192");

            //设置内容类型为下载类型
//            response.setContentType("application/octet-stream");
            //设置请求头 和 文件下载名称
//            response.addHeader("Content-Disposition","attachment;filename=test.txt");

            for (int i = 0; i < 40; i++) {
                String data = i + "aaaa";
                log.info("loop : {}", data);
                /**
                 * 方法1 可以分块返回
                 */
//                bw.write(data);
//                bw.write("\r\n"); //空格符，没有也没关系
//                bw.flush();

                /**
                 * 方法2 可以分块返回
                 */
//                bos.write(String.valueOf(data).getBytes());
//                bos.flush();

                /**
                 * 方法3 可以分块返回
                 */
//                out.println(data);
//                out.flush();

                /**
                 * 方法4 可以分块返回
                 */
                outputStream.write(String.valueOf(data).getBytes());
//                outputStream.flush();

                /**
                 * 方法5——只针对刷新流，刷新流的缓存可以统一用response
                 */
                response.flushBuffer();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    @RequestMapping(value = "/media/", method = RequestMethod.GET)
    public void getDownload(Long id, HttpServletRequest request, HttpServletResponse response) {

        // Get your file stream from wherever.
        String fullPath = "E:/" + id + ".rmvb";
        File downloadFile = new File(fullPath);

        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            System.out.println("context getMimeType is null");
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            InputStream myStream = new FileInputStream(fullPath);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String strURL = "http://localhost:8043/test/http/progress";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
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
