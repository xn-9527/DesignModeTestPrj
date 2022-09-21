package cn.test.ping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Created by chay on 2018/8/25.
 */
@Slf4j
public class PingUtil {

    private static final String SEARCH_PARAMETER_QUESTION_MARK = "?";
    private static final String SEARCH_PARAMETER_QUESTION_EQUAL = "=";
    private static final String SEARCH_PARAMETER_QUESTION_MAC = "mac";
    private static final String NET_TTL = "ttl";
    private static final String WINDOWS_PING_IDENTIFY_STRING = "时间";
    private static final String OS_WINDOWS = "windows";
    private static final String CHARSET_GB2312 = "GB2312";
    private static final String PING_RESULT = "(\\d+ms)(\\s+)(TTL=\\d+)";

    /**
     * 判断网络是不是通
     *
     * @param hostIp
     * @param logName
     * @return
     */
    public static boolean isConnect(String hostIp, String logName) {
        boolean connect = false;
        Runtime runtime = Runtime.getRuntime();
        Process process;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            log.info("[{}]开始ping:{}", logName, hostIp);
            //获取操作系统类型
            String osName = System.getProperty("os.name");
            String pingCommand = "";
            //超时时间ms
            int timeOut = 300;
            int count = 1;
            boolean isWindowsOS = osName.toLowerCase().contains(OS_WINDOWS);
            String identifyString = NET_TTL;
            String charSetName = Charset.defaultCharset().name();
            if (isWindowsOS) {
                //windows超时时间单位是ms,-w 超时时间ms
                pingCommand = "ping " + hostIp + " -n " + count;
                identifyString = WINDOWS_PING_IDENTIFY_STRING;
                charSetName = CHARSET_GB2312;
            } else {
                //linux超时时间是秒, -W 超时时间s
                pingCommand = "ping -c " + count + " " + hostIp;
            }
            process = runtime.exec(pingCommand);
            boolean execFinish = process.waitFor(timeOut, TimeUnit.MILLISECONDS);
            if (execFinish) {
                is = process.getInputStream();
                isr = new InputStreamReader(is, charSetName);
                br = new BufferedReader(isr);
                String line = null;
                StringBuffer sb = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                log.debug("[{}]返回值为:{}", logName, sb);
                //ping的结果一般含ttl表示畅通，但是ping localhost返回结果不含ttl，windows只包含“时间”
                if (!"".equals(sb.toString())) {
                    if (sb.toString().toLowerCase().indexOf(identifyString) > 0) {
                        // 网络畅通
                        log.debug("[{}]ping:{}网络畅通", logName, hostIp);
                        connect = true;
                    } else {
                        // 网络不畅通
                        log.debug("[{}]ping:{}网络异常", logName, hostIp);
                        connect = false;
                    }
                }
            } else {
                log.debug("[{}]ping:{}网络异常,在{}ms内没有回执", logName, hostIp, timeOut);
                connect = false;
            }
            return connect;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }

    /**
     * 发送请求前，先测试网络连接，使用get方法请求
     *
     * @param restTemplate
     * @param serverUrl
     * @param requestUrl
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T connectAndCheckFirstUseGet(RestTemplate restTemplate,
                                                   String serverUrl,
                                                   String logName,
                                                   String requestUrl, Class<T> responseType, Object... uriVariables) {
        if (restTemplate == null) {
            log.info("restTemplate is Null");
            return null;
        }
        try {
            if (!isHostAvailablePingFirst(new URI(serverUrl), logName)) {
                return null;
            }
            return restTemplate.getForObject(requestUrl, responseType, uriVariables);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 发送请求前，先测试网络连接，使用post方法请求
     *
     * @param restTemplate
     * @param serverUrl
     * @param requestUrl
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T connectAndCheckFirstUsePost(RestTemplate restTemplate,
                                                    String serverUrl,
                                                    String logName,
                                                    String requestUrl, Object request, Class<T> responseType, Object... uriVariables) {
        if (restTemplate == null) {
            log.info("restTemplate is Null");
            return null;
        }
        try {
            if (!isHostAvailablePingFirst(new URI(serverUrl), logName)) {
                return null;
            }
            return restTemplate.postForObject(requestUrl, request, responseType, uriVariables);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 发送请求前，先测试网络连接，使用指定的http method方法请求
     *
     * @param restTemplate
     * @param serverUrl
     * @param requestUrl
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T connectAndCheckFirstUseHttpMethod(RestTemplate restTemplate,
                                                          HttpMethod method,
                                                          String serverUrl,
                                                          String logName,
                                                          String requestUrl, Object request, Class<T> responseType, Object... uriVariables) {
        if (restTemplate == null) {
            log.info("restTemplate is Null");
            return null;
        }
        try {
            if (!isHostAvailablePingFirst(new URI(serverUrl), logName)) {
                return null;
            }
            return serverConnectAndCheckFirstUseHttpMethod(restTemplate, method, serverUrl, logName, requestUrl, request, responseType, uriVariables);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * server端使用，使用get方法请求
     *
     * @param restTemplate
     * @param serverUrl
     * @param requestUrl
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T serverConnectAndCheckFirstUseGet(RestTemplate restTemplate,
                                                         String serverUrl,
                                                         String logName,
                                                         String requestUrl, Class<T> responseType, Object... uriVariables) {
        if (restTemplate == null) {
            log.info("restTemplate is Null");
            return null;
        }
        try {
            return restTemplate.getForObject(requestUrl, responseType, uriVariables);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 用于server端访问，使用post方法请求
     *
     * @param restTemplate
     * @param serverUrl
     * @param requestUrl
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T serverConnectAndCheckFirstUseHttpMethod(RestTemplate restTemplate,
                                                                HttpMethod method,
                                                                String serverUrl,
                                                                String logName,
                                                                String requestUrl, Object request, Class<T> responseType, Object... uriVariables) {
        if (restTemplate == null) {
            log.info("restTemplate is Null");
            return null;
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<Object> entity = new HttpEntity<>(request, headers);
            if (method.ordinal() == HttpMethod.DELETE.ordinal() || method.ordinal() == HttpMethod.GET.ordinal()) {
                requestUrl = requestUrl + SEARCH_PARAMETER_QUESTION_MARK + request;
            }
            if (method == HttpMethod.PATCH) {
                //注意：容器undertow.HttpServlet暂时不支持patch请求
                return restTemplate.patchForObject(requestUrl, request, responseType, uriVariables);
            } else {
                ResponseEntity<T> responseEntity = restTemplate.exchange(requestUrl, method, entity, responseType, uriVariables);
                return responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T serverConnectAndCheckFirstUseTokenHttpMethod(RestTemplate restTemplate,
                                                                HttpMethod method,
                                                                String token,
                                                                String requestUrl, Object request, Class<T> responseType, Object... uriVariables) {
        if (restTemplate == null) {
            log.info("restTemplate is Null");
            return null;
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.set("Authorization", "bearer" + token);
            HttpEntity<Object> entity = new HttpEntity<>(request, headers);
            if (method.ordinal() == HttpMethod.DELETE.ordinal() || method.ordinal() == HttpMethod.GET.ordinal()) {
                requestUrl = requestUrl + SEARCH_PARAMETER_QUESTION_MARK + request;
            }
            ResponseEntity<T> responseEntity = restTemplate.exchange(requestUrl, method, entity, responseType, uriVariables);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * server端使用，使用post方法请求
     *
     * @param restTemplate
     * @param serverUrl
     * @param requestUrl
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T serverConnectAndCheckFirstUsePost(RestTemplate restTemplate,
                                                          String serverUrl,
                                                          String logName,
                                                          String requestUrl, Object request, Class<T> responseType, Object... uriVariables) {
        if (restTemplate == null) {
            log.info("restTemplate is Null");
            return null;
        }
        try {
            return restTemplate.postForObject(requestUrl, request, responseType, uriVariables);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 使用jar包自带的ping方法
     *
     * @param ipAddress
     * @param logName
     * @return
     */
    public static boolean ping(String ipAddress, String logName) {
        boolean status = false;
        try {
//            int timeOut = 300;  //超时应该在3钞以上.实测在网络不好的情况下，比如延迟大于1000ms，http访问仍然可能失败，所以应该减小该值。
//            status = isNodeReachable(ipAddress) || InetAddress.getByName(ipAddress).isReachable(timeOut);
            status = isConnect(ipAddress, logName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("[{}]ping:{} {}", logName, ipAddress, status);
        return status;
    }

    /**
     * 使用Runtime方式执行ping命令
     *
     * @param hostname
     * @return
     */
    public static final boolean isNodeReachable(String hostname) {
        try {
            return Runtime.getRuntime().exec("ping -c 1 " + hostname).waitFor(300, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | IOException e) {
            log.error("isNodeReachable error", e);
            return false;
        }
    }

    /**
     * 另一种ping的方法，执行ping命令
     *
     * @param ipAddress
     */
    public static void ping02(String ipAddress) {
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    pro.getInputStream()));
            while ((line = buf.readLine()) != null) {
                log.info(line);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        // 将要执行的ping命令,此命令是windows格式的命令
        Runtime r = Runtime.getRuntime();
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        try {
            // 执行命令并获取输出
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
     *
     * @param line
     * @return
     */
    private static int getCheckResult(String line) {
        // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile(PING_RESULT, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    /**
     * 判断连接是否可用-纯socket
     * 至于是websocket还是http，通过不同的uri前缀来区分
     *
     * @param uri
     * @return
     */
    public static boolean isHostAvailable(URI uri, String hostLogName) {
        log.info("[" + hostLogName + "]isHostAvailable start");
        if (uri == null) {
            return false;
        }
        Socket socket = new Socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(uri.getHost(),
                uri.getPort());
        try {
            socket.connect(inetSocketAddress);
            log.info("[" + hostLogName + "]connect successful, address = " + inetSocketAddress);
            return true;
        } catch (Exception e) {
            log.info("[" + hostLogName + "]connect failed, address = " + inetSocketAddress + ", exception =  " + e);
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.info("[" + hostLogName + "]close failed, address = " + inetSocketAddress + ", exception =  " + e);
            }
        }
    }

    /**
     * 判断连接是否可用，ping和socket同时判断
     * 至于是websocket还是http，通过不同的uri前缀来区分
     *
     * @param uri
     * @return
     */
    public static boolean isHostAvailablePingFirst(URI uri, String hostLogName) {
        if (ping(uri.getHost(), hostLogName)) {
            return isHostAvailable(uri, hostLogName);
        } else {
            return false;
        }
    }
}
