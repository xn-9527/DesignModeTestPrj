package cn.routePlan;

import lombok.Data;

import java.util.List;

/**
 * @author Created by chay on 2017/10/13.
 */
@Data
public class RoutePlanResult {
    /**
     * 最优路径点ID序列
     */
    List<Long> pointIds;
    /**
     * 最优路径总权值
     */
    Long totalWeight;
    /**
     * 最优路径总长度
     */
    Long totalLength;
    /**
     * 起始点
     */
    Long startPointId;
    /**
     * 终点
     */
    Long endPointId;
}
