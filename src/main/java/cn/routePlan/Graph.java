package cn.routePlan;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Graph {
    private final Map<Long, List<Vertex>> vertices;

    public Graph() {
        this.vertices = new HashMap<Long, List<Vertex>>();
    }


    public void addVertex(Long id, List<Vertex> vertex) {
        this.vertices.put(id, vertex);
    }

    public RoutePlanResult getShortestPath(Long start, Long finish) {
        log.info("dijkstra计算开始：起点" + start + "，终点" + finish);
        RoutePlanResult routePlanResult = new RoutePlanResult();
        Long totalWeight = 0L;
        routePlanResult.setTotalWeight(totalWeight);
        routePlanResult.setStartPointId(start);
        routePlanResult.setEndPointId(finish);
        //当起点和终点是同一个点的时候，直接返回这个点序列，权值为0
        if (start.equals(finish)) {
            log.info("起点和终点是同一个点的时候，直接返回这个点序列，权值为0");
            List<Long> ids = new ArrayList<Long>();
            ids.add(start);
            routePlanResult.setPointIds(ids);
            return routePlanResult;
        }

        final Map<Long, Long> distances = new HashMap<>();
        final Map<Long, Vertex> previous = new HashMap<Long, Vertex>();
        PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();

        for (Long vertex : vertices.keySet()) {
            if (vertex.equals(start)) {
                start = vertex;
                distances.put(vertex, 0L);
                nodes.add(new Vertex(vertex, 0L));
            } else {
                distances.put(vertex, Long.MAX_VALUE);
                nodes.add(new Vertex(vertex, Long.MAX_VALUE));
            }
            previous.put(vertex, null);
        }

        while (!nodes.isEmpty()) {
            Vertex smallest = nodes.poll();
            if (smallest.getId().equals(finish)) {
                final List<Long> path = new ArrayList<Long>();
                while (previous.get(smallest.getId()) != null) {
                    path.add(smallest.getId());
                    smallest = previous.get(smallest.getId());
                }
                path.add(start);

                Collections.reverse(path);
                routePlanResult.setPointIds(path);
                routePlanResult.setTotalWeight(totalWeight);
                return routePlanResult;
            }

            if (distances.get(smallest.getId()).equals(Long.MAX_VALUE)) {
                break;
            }

            for (Vertex neighbor : vertices.get(smallest.getId())) {
                Long alt = distances.get(smallest.getId()) + neighbor.getWeight();
                if (alt < distances.get(neighbor.getId())) {
                    distances.put(neighbor.getId(), alt);
                    previous.put(neighbor.getId(), smallest);

                    forloop:
                    for (Vertex n : nodes) {
                        if (n.getId().equals(neighbor.getId())) {
                            nodes.remove(n);
                            n.setWeight(alt);
                            nodes.add(n);
                            totalWeight = alt;
                            break forloop;
                        }
                    }
                }
            }
        }

        return routePlanResult;
    }
}
