package cn.routePlan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Created by chay on 2019/9/11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route implements Serializable {
    /**
     * 开始点ID
     */
    private Long startPointId;
    /**
     * 结束点ID
     */
    private Long endPointId;
    /**
     * 权值大小
     */
    private Long weight;
    /**
     * 路径长度，单位mm
     */
    private Long length;
}
