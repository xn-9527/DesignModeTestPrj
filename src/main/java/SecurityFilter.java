import org.apache.tomcat.util.IntrospectionUtils;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 去掉包名，将SecurityFilter.java文件编译为SecurityFilter.class，
 * 放入服务器路径为：/var/www/apache-tomcat-7.0.14/webapps/ROOT/WEB-INF/classes/
 * <p>
 * 修改web.xml(/var/www/apache-tomcat-7.0.14/webapps/ROOT/WEB-INF/web.xml)使得添加的拦截器生效
 * <p>
 * 添加代码：
 * <p>
 * <filter>
 * <filter-name>SecurityFilter</filter-name>
 * <filter-class>SecurityFilter</filter-class>
 * </filter>
 <filter-mapping>
 <filter-name>SecurityFilter</filter-name>
 <url-pattern>/*</url-pattern>
 <url-pattern>*.action</url-pattern>
 </filter-mapping>
 *
 * @author Chay
 * @date 2020/3/31 11:55
 */
public class SecurityFilter implements Filter {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public final String www_url_encode = "application/x-www-form-urlencoded";
    public final String mul_data = "multipart/form-data ";
    public final String txt_pla = "text/plain";

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;

        String contenType = request.getHeader("conTent-type");

        System.out.println("#######securityFilter contenType:" + contenType);
        if (contenType != null && !contenType.equals(txt_pla)) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("非法请求Content-Type！");
            return;
        }
        String requestMethod = request.getMethod();
        System.out.println("#######securityFilter requestMethod:" + requestMethod);
        if (!"GET".equals(requestMethod)) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("非法请求");
            return;
        }

        arg2.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("#########init securityFilter");
        Enumeration<String> paramNames = filterConfig.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (!IntrospectionUtils.setProperty(this, paramName,
                    filterConfig.getInitParameter(paramName))) {
                String msg = new StringBuilder().append("securityFilter.noSuchProperty")
                        .append(paramName).append(this.getClass().getName()).toString();
                System.out.println(msg);
            }
        }
    }
}
