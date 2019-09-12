package cn.routePlan;

import java.util.ArrayList;
import java.util.List;


public class RoutePlanMaps {
    private final static long DEFAULT_POINT_ID = -1L;

    private Graph g = new Graph();

    public RoutePlanMaps() {
    }

    private List<Route> routeList;

    /**
     * 初始化：载入所有固定路径，且列表里面的路径是根据START_POINT进行排序的
     */
    public void init() {
        Long startPointId = DEFAULT_POINT_ID;
        List<Long> startPointList = new ArrayList<Long>();
        List<Vertex> vertexList = null;
        for (Route route : routeList) {
            //id 变更，更新上一个id的路径到图中，并重新申请相关资源
            if (!startPointId.equals(route.getStartPointId())) {
                //更新上一个id的路径到图中
                if (!startPointId.equals(DEFAULT_POINT_ID)) {
                    g.addVertex(startPointId, vertexList);
                    startPointList.add(startPointId);
                }
                // 重新申请资源
                startPointId = route.getStartPointId();
                vertexList = new ArrayList<Vertex>();
            }

            vertexList.add(new Vertex(route.getEndPointId(), route.getWeight()));
        }

        //最后一个路径ID的相关数据更新到图中
        if (!startPointId.equals(DEFAULT_POINT_ID)) {
            g.addVertex(startPointId, vertexList);
            startPointList.add(startPointId);
        }

        //对于某个没有作为任意一条路径的起始点的点，也需要进行初始化
        for (Route route : routeList) {
            Long endPointId = route.getEndPointId();
            if (startPointList.contains(endPointId)) {
                continue;
            }
            g.addVertex(endPointId, new ArrayList<Vertex>());
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

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }


}
