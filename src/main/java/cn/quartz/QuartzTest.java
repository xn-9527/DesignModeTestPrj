package cn.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Created by xiaoni on 2018/10/17.
 */
@Configuration
@EnableScheduling  //启用定时任务
@Slf4j
public class QuartzTest {
    private int i = 0;
    /**
     * 基本配置定时任务时间的格式就是“秒  分  时 月 日 星期几 年”，不想设置的字段就? 。
     * 附录：
     * cronExpression的配置说明，具体使用以及参数请百度google
     * 字段   允许值   允许的特殊字符
     * 秒    0-59    , - * /
     * 分    0-59    , - * /
     * 小时    0-23    , - * /
     * 日期    1-31    , - * ? / L W C
     * 月份    1-12 或者 JAN-DEC    , - * /
     * 星期    1-7 或者 SUN-SAT    , - * ? / L C #
     * 年（可选）    留空, 1970-2099    , - * /
     * - 区间
     * 通配符
     * ? 你不想设置那个字段
     * 下面只例出几个式子
     * <p>
     * CRON表达式    含义
     * "0 0 12 * * ?"    每天中午十二点触发
     * "0 15 10 ? * *"    每天早上10：15触发
     * "0 15 10 * * ?"    每天早上10：15触发
     * "0 15 10 * * ? *"    每天早上10：15触发
     * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
     * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
     * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
     * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
     * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
     * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
     * 0 0 *(去掉这个)/1 * * ?    每天隔一小时
     * 0 0 1-8/1 * * ?    从凌晨一点到早上八点每隔一小时
     */
//    @Scheduled(cron = "*/1 * * * * ?")
//    public void test1() {
//        if (i < 10) {
//            log.info("我是test1");
//            i ++;
//        }
//    }

//    public static void main(String[] args) {
//        new QuartzTest().test1();
//    }
}
