package cn.routePlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutePlanMaps {
    private Graph g = new Graph();

    public RoutePlanMaps() {
    }

    private List<Route> routeList;

    public void init() {
        List<Vertex> vertexList = null;
        //初始化开始点的Vertex
        for (Route route : routeList) {
            Long startPointId = route.getStartPointId();
            vertexList = g.getVertex(startPointId);
            if (vertexList == null) {
                vertexList = new ArrayList<>();
                g.addVertex(startPointId, vertexList);
            }
            vertexList.add(new Vertex(route.getEndPointId(), route.getWeight()));
        }
        //初始化结束点的Vertex
        for (Route route : routeList) {
            Long endPointId = route.getEndPointId();
            //已初始化过的点，不需要重复初始化
            if (g.getVertex(endPointId) != null) {
                continue;
            }
            //结束点的边是空
            g.addVertex(endPointId, Collections.emptyList());
        }
    }

    /**
     * 获取最短路线点序列
     *
     * @param startPointId
     * @param finishPointIdId
     * @return
     */
    public RoutePlanResult getShortestPath(Long startPointId, Long finishPointIdId) {
        RoutePlanResult routePlanResult = g.getShortestPath(startPointId, finishPointIdId);
        routePlanResult.setTotalLength(calTotalLength(routePlanResult));
        return routePlanResult;
    }

    /**
     * 计算路径总长度
     *
     * @param routePlanResult
     * @return
     */
    public Long calTotalLength(RoutePlanResult routePlanResult) {
        long totalLength = 0L;
        if (routePlanResult == null || routePlanResult.getPointIds() == null || routePlanResult.getPointIds().isEmpty()) {
            routePlanResult.setTotalLength(totalLength);
            return totalLength;
        }
        List<Long> pointIds = routePlanResult.getPointIds();
        int size = pointIds.size();
        if (size <= 1) {
            //只有一个点，说明就在这个点，返回0
            return totalLength;
        }
        for (int i = 0; i < size - 1; i++) {
            Route route = getRouteByStartAndEndPointId(pointIds.get(i), pointIds.get(i + 1));
            if (route != null) {
                totalLength += route.getLength();
            }
        }
        return totalLength;
    }

    /**
     * 根据起点和终点ID查询路径
     *
     * @param startPointId
     * @param endPointId
     * @return
     */
    private Route getRouteByStartAndEndPointId(Long startPointId, Long endPointId) {
        if (startPointId == null || endPointId == null || routeList == null || routeList.isEmpty()) {
            return null;
        }
        for (Route route : routeList) {
            if (route.getStartPointId().equals(startPointId) && route.getEndPointId().equals(endPointId)) {
                return route;
            }
        }
        return null;
    }

    /**
     * 设置用于规划的路径序列
     *
     * @param routeList
     */
    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
