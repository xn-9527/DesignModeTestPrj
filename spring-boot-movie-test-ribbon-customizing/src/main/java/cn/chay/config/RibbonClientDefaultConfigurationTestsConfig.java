//package cn.chay.config;
//
//import cn.chay.annotation.ExcludeFromComponentScan;
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.*;
//import org.springframework.cloud.netflix.ribbon.RibbonClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 使用@RibbonClients注解，多个s，为所有RibbonClient提供默认配置
// *
// * @author Created by xiaoni on 2019/11/18.
// */
//@ExcludeFromComponentScan
//@RibbonClients(defaultConfiguration = RibbonClientDefaultConfigurationTestsConfig.DefaultRibbonConfig.class)
//public class RibbonClientDefaultConfigurationTestsConfig {
//    public static class BazServiceList extends ConfigurationBasedServerList {
//        public BazServiceList(IClientConfig config) {
//            super.initWithNiwsConfig(config);
//        }
//    }
//
//    @ExcludeFromComponentScan
//    @Configuration
//    class DefaultRibbonConfig {
//        @Bean
//        public IRule ribbonRule() {
//            return new BestAvailableRule();
//            /**
//             * 使用random规则时，RandomRule的upList为空，但是allServerList里面能看到两个user服务，
//             * 用这个配置ILoadBalancer不知道有什么问题。认为两个服务不可达。
//             * 看了下BestAvailableRule，只取ILoadBalancer的getAllServers，不管是不是可达，这里还是有区别的。
//             */
////            return new RandomRule();
//        }
//
//        @Bean
//        public IPing ribbonPing() {
//            return new PingUrl();
//        }
//
//        /**
//         * 这个bean我自己加的，但是启动项目后，调用接口报错，找不到microservice-user
//         * 2019-11-18 12:42:17.182  WARN 37420 --- [nio-8010-exec-2] com.netflix.loadbalancer.RoundRobinRule  :
//         * No up servers available from load balancer: DynamicServerListLoadBalancer:{NFLoadBalancer:name=null,current list of Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:cn.chay.config.RibbonClientDefaultConfigurationTestsConfig$BazServiceList@45101a3d
//         *
//         * 而旧的配置loadBalancer可以找到数据
//         * 2019-11-18 12:48:34.487  INFO 30880 --- [nio-8010-exec-1] c.n.l.DynamicServerListLoadBalancer      : DynamicServerListLoadBalancer for client microservice-user initialized: DynamicServerListLoadBalancer:{NFLoadBalancer:name=microservice-user,current list of Servers=[192.168.203.1:8001, 192.168.203.1:8000],Load balancer stats=Zone stats: {defaultzone=[Zone:defaultzone;	Instance count:2;	Active connections count: 0;	Circuit breaker tripped count: 0;	Active connections per server: 0.0;]
//         },Server stats: [[Server:192.168.203.1:8000;	Zone:defaultZone;	Total Requests:0;	Successive connection failure:0;	Total blackout seconds:0;	Last connection made:Thu Jan 01 08:00:00 CST 1970;	First connection made: Thu Jan 01 08:00:00 CST 1970;	Active Connections:0;	total failure count in last (1000) msecs:0;	average resp time:0.0;	90 percentile resp time:0.0;	95 percentile resp time:0.0;	min resp time:0.0;	max resp time:0.0;	stddev resp time:0.0]
//         , [Server:192.168.203.1:8001;	Zone:defaultZone;	Total Requests:0;	Successive connection failure:0;	Total blackout seconds:0;	Last connection made:Thu Jan 01 08:00:00 CST 1970;	First connection made: Thu Jan 01 08:00:00 CST 1970;	Active Connections:0;	total failure count in last (1000) msecs:0;	average resp time:0.0;	90 percentile resp time:0.0;	95 percentile resp time:0.0;	min resp time:0.0;	max resp time:0.0;	stddev resp time:0.0]
//         ]}ServerList:org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList@610c68e
//         * @return
//         */
////    @Bean
////    public IClientConfig iClientConfig() {
////        IClientConfig clientConfig = IClientConfig.Builder.newBuilder("microservice-user").build();
////        return clientConfig;
////    }
//
////        /**
////         * 如果不加iClientConfig()，就会报bean clientConfig不存在。而加了配置，又会报上面的错误。
////         *  注释掉这段方法不会报错。而且每次访问都是user客户端8000，说明生效了。
////         *
////         * @param clientConfig
////         * @return
////         */
////        @Bean
////        public ServerList<Server> ribbonServerList(IClientConfig clientConfig) {
////            return new RibbonClientDefaultConfigurationTestsConfig.BazServiceList(clientConfig);
////        }
//
//        @Bean
//        public ServerListSubsetFilter severListFilter() {
//            return new ServerListSubsetFilter();
//        }
//    }
//}
