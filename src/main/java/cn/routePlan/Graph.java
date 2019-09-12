package cn.routePlan;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 包内引用，路径规划算法核心
 */
@Slf4j
class Graph {
    private final Map<Long, List<Vertex>> vertices;

    Graph() {
        this.vertices = new HashMap<Long, List<Vertex>>();
    }

    void addVertex(Long id, List<Vertex> vertex) {
        this.vertices.put(id, vertex);
    }

    List<Vertex> getVertex(Long id) {
        return this.vertices.get(id);
    }

    RoutePlanResult getShortestPath(Long start, Long finish) {
        log.info("dijkstra计算开始：起点" + start + "，终点" + finish);
        RoutePlanResult routePlanResult = new RoutePlanResult();
        Long totalWeight = 0L;
        routePlanResult.setTotalWeight(totalWeight);
        routePlanResult.setStartPointId(start);
        routePlanResult.setEndPointId(finish);
        //当起点和终点是同一个点的时候，直接返回这个点序列，权值为0
        if (start.equals(finish)) {
            log.info("起点和终点是同一个点的时候，直接返回这个点序列，权值为0");
            List<Long> ids = new ArrayList<Long>(1);
            ids.add(start);
            routePlanResult.setPointIds(ids);
            return routePlanResult;
        }

        //缓存已遍历到某个点的权值
        final Map<Long, Long> distances = new HashMap<>();
        //缓存已遍历结果点ID及到该点ID的最短边
        final Map<Long, Vertex> previous = new HashMap<Long, Vertex>();
        //带排序的队列，所有未遍历的点集合
        PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();

        for (Long pointId : vertices.keySet()) {
            if (pointId.equals(start)) {
                //如果就是开始点，则直接赋值0
                start = pointId;
                distances.put(pointId, 0L);
                nodes.add(new Vertex(pointId, 0L));
            } else {
                //其他点，初始化为最大值
                distances.put(pointId, Long.MAX_VALUE);
                nodes.add(new Vertex(pointId, Long.MAX_VALUE));
            }
            //初始化已遍历的点集合
            previous.put(pointId, null);
        }

        while (!nodes.isEmpty()) {
            //取一个边,因为node使用的是有序队列，所以每次pop出来的都是权值最小的边。第一次起始点start我们初始化的weight是0.
            Vertex currentMinimumVertex = nodes.poll();
            Long currentPointId = currentMinimumVertex.getId();
            //如果这条边是到结束点的边，输出之前缓存的点序列，并结束while循环
            if (currentPointId.equals(finish)) {
                final List<Long> path = new ArrayList<Long>();
                while (previous.get(currentPointId) != null) {
                    path.add(currentPointId);
                    currentPointId = previous.get(currentPointId).getId();
                }
                path.add(start);

                Collections.reverse(path);
                routePlanResult.setPointIds(path);
                routePlanResult.setTotalWeight(totalWeight);
                return routePlanResult;
            }

            //遍历所有该点的相邻边
            for (Vertex neighbor : vertices.get(currentPointId)) {
                //计算当起点到邻点的总权值，第一次当前点的distance上面已经初始化为0
                Long currentTotalWeight = distances.get(currentPointId) + neighbor.getWeight();
                //第一次后续点的distance上面已经初始化为Long.MAX_VALUE
                Long neighborPointId = neighbor.getId();
                //如果到邻点总权值小于缓存的某点当前总权值，则暂时认为该点最优输入缓存结果
                if (currentTotalWeight < distances.get(neighborPointId)) {
                    //更新已遍历到某个点的权值
                    distances.put(neighborPointId, currentTotalWeight);
                    //缓存最短路径结果集：该点以及到该点的最短边
                    previous.put(neighborPointId, currentMinimumVertex);

                    //设置break锚点，只打断内循环，而不是while循环
                    forLoop:
                    //遍历所有未被遍历的点
                    for (Vertex n : nodes) {
                        //如果点就是下一个邻点
                        if (n.getId().equals(neighborPointId)) {
                            //先从未遍历的点集合中移除该点
                            nodes.remove(n);
                            //更新节点的总权值
                            n.setWeight(currentTotalWeight);
                            //再把新权值的节点增加未遍历的点集合，总权值最小的点会在nodes顶端，等待下一次poll
                            nodes.add(n);
                            //更新总权值
                            totalWeight = currentTotalWeight;
                            break forLoop;
                        }
                    }
                }
            }
        }

        return routePlanResult;
    }
}
