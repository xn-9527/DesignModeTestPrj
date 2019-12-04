package cn.chay.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

public class PreRequestLogFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreRequestLogFilter.class);

    @Override
    public String filterType() {
        //pre类型过滤器
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 返回int值来指定过滤器的执行顺序，不同的过滤器允许相同的数字
     *
     * @return
     */
    @Override
    public int filterOrder() {
        // 在org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter之前执行
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 返回一个boolean判断该过滤器是否要执行，true表示执行，false表示不执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        PreRequestLogFilter.LOGGER.info(String.format("send %s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }
}
