package cn.test.url;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: Blackknight
 * @Date: 2024/8/27 17:48
 * @Description:
 */
public class TestUrl {
    public static void main(String[] args) {
        // 定义你的URL
        try {
            String urlStr = "https://test.com/112233/ui/2kf";
            // 创建URL对象
            URL url = new URL(urlStr);
            // 获取路径部分
            String path = url.getPath();
            System.out.println(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
