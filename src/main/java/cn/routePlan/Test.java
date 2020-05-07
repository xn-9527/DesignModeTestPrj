package cn.routePlan;

import cn.test.equals.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by chay on 2019/9/12.
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        User user = new User("a", null, null);
//        String name = Optional.ofNullable(user).map(user::getName).orElse(null);
        String name = Optional.ofNullable(user).map(user1->user1.getName()).orElse(null);
        System.out.println(name);

        List<Route> routeList = new ArrayList<>();
        /**
         * 11     21    31
         * 12     22    32
         * 13     23    33
         */
        routeList.add(new Route(11L, 21L, 100L, 200L));
        routeList.add(new Route(21L, 31L, 50L, 20L));
        routeList.add(new Route(21L, 33L, 300L, 200L));
        routeList.add(new Route(12L, 23L, 50L, 20L));
        routeList.add(new Route(23L, 33L, 50L, 20L));
        routeList.add(new Route(11L, 22L, 50L, 100L));
        routeList.add(new Route(11L, 12L, 200L, 300L));

        RoutePlanMaps routePlanMaps = new RoutePlanMaps();
        routePlanMaps.setRouteList(routeList);
        routePlanMaps.init();
        RoutePlanResult routePlanResult = routePlanMaps.getShortestPath(11L, 33L);
        //{"endPointId":33,"pointIds":[11,12,23,33],"startPointId":11,"totalLength":340,"totalWeight":300}
        log.info(JSON.toJSONString(routePlanResult));
    }
}
