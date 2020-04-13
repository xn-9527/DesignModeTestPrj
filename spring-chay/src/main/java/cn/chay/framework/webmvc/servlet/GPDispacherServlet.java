package cn.chay.framework.webmvc.servlet;

import cn.chay.framework.annotation.GPAutoWired;
import cn.chay.framework.annotation.GPController;
import cn.chay.framework.annotation.GPRequestMapping;
import cn.chay.framework.annotation.GPService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.logging.FileHandler;

/**
 * Created by xiaoni on 2019/10/13.
 */
@Slf4j
public class GPDispacherServlet extends HttpServlet {

    private Properties contextConfig = new Properties();
    private List<String> classNames = new ArrayList<>();
    private Map<String, Object> ioc = new HashMap<String, Object>();
    private Map<String, Method> handlerMapping = new HashMap<String, Method>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.等待请求
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 exception");
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getRequestURI();
        String contexPath = req.getContextPath();
        url = url.replace(contexPath, "").replaceAll("/+", "/");

        if (!handlerMapping.containsKey(url)) {
            resp.getWriter().write("my 404 not found");
            return;
        }
        Method method = handlerMapping.get(url);
        System.out.println(method);

        //实参
        Map<String, String[]> params = req.getParameterMap();
        String beanName = toLowerFirstCase(method.getDeclaringClass().getName());
        String name = null;
        if (params != null && !params.isEmpty()) {
            name = params.get("name")[0];
        }
        Object bean = ioc.get(beanName);
        if (bean == null) {
            log.info("bean not exist");
        } else {
            method.invoke(bean, req, resp, name);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //从这里开始启动


        //1.加载配置文件
        //常量名来自于web.xml里面我们配置的servlet.init-param
        doLoadConfig(config.getInitParameter("contextConfigLocation"));


        //2.扫描所有相关的类
        //属性名来自于我们配置文件application.yml或者application.properties
        doScanner(contextConfig.getProperty("scanPackage"));

        //3.初始化所有相关的类
        doInstance();

        //4.自动注入
        doAutowired();

        //==============spring的核心初始化完成==========================

        //5.初始化HandlerMapping-属于spring mvc的功能
        initHandlerMapping();

        log.info("GP chay spring init finished....");
        System.out.println("GP chay spring init finished....");
    }

    private void initHandlerMapping() {
        //把所以的method和url进行关联
        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(GPController.class)) {
                continue;
            }

            String baseUrl = "";
            if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            //扫描所有方法
            for(Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)){
                    continue;
                }

                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                String methodUrl = ("/" + baseUrl + requestMapping.value()).replaceAll("/+", "/");

                handlerMapping.put(methodUrl, method);

                log.info("Mapping:" + methodUrl + "," + method);
            }
        }

    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        //循环ioc容器中的所有类，对需要自动赋值的属性进行赋值
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //依赖注入，不管是谁，哪怕私有的，都要注入
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                //没加注解的跳过，不需要注入
                if (!field.isAnnotationPresent(GPAutoWired.class)) {
                    continue;
                }
                GPAutoWired autoWired = field.getAnnotation(GPAutoWired.class);
                //如果自定义名称，则取自定义名称
                String beanName = autoWired.value().trim();

                if ("".equals(beanName)) {
                    //如果没有自定义名称，则取类型名称
                    beanName = field.getType().getName();
                }
                //暴力访问
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }

            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                //不是所有的类都要实例化，只认加了注解的类
                if (clazz.isAnnotationPresent(GPController.class)) {
                    //key默认是类名首字母小写
                    System.out.println(clazz.getTypeName());
                    System.out.println(clazz.getSimpleName());
                    String beanName = toLowerFirstCase(clazz.getName());
                    ioc.put(beanName, clazz.newInstance());

                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    GPService service = clazz.getAnnotation(GPService.class);
                    //2.如果自定义名字，优先使用自定义的名字
                    String beanName = service.value();
                    //1.默认采用首字母小写
                    if ("".equals(beanName.trim())) {
                        //用户没有自定义,则默认用类名
                        beanName = toLowerFirstCase(clazz.getName());
                    }

                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);

                    //3.根据接口类型来赋值(实现类实例化)
                    for(Class<?> i : clazz.getInterfaces()) {
                        //如果一个接口有多个实现类，key会重名，这里可以报错
                        String interfaceName = i.getName();
                        if (null != ioc.get(interfaceName)) {
                            throw new Exception(interfaceName + " already exists");
                        }
                        ioc.put(interfaceName, instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        if (null == url) {
            return;
        }
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    private String toLowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        //大写字母和小写字母ascii差32
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
