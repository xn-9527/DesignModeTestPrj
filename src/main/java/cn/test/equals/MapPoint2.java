package cn.test.equals;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Chay on 2017/6/7.
 * 导航目标点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapPoint2 extends BaseBean {
    /**
     * 点名：唯一标识符
     */
    @JSONField(name = "point_name")
    private String pointName;
    /**
     * 点别名：显示名称
     */
    @JSONField(name = "point_alias")
    private String pointAlias;
    /**
     * 地图场景名
     */
    @JSONField(name = "scene_name")
    private String sceneName;
    /**
     * 地图名
     */
    @JSONField(name = "map_name")
    private String mapName;
    /**
     * 坐标x
     */
    @JSONField(name = "x")
    private double x;
    /**
     * 坐标y
     */
    @JSONField(name = "y")
    private double y;
    /**
     * 坐标旋转角度
     */
    @JSONField(name = "th")
    private double th;

    /**
     * 工控点类型索引
     */
    @JSONField(name = "point_type")
    private int mapPointTypeId;

    /**
     * 点类型名称展示
     */
    @JSONField(name = "ic_point_type")
    private String ICPointType;

    /**
     * 云端点类型索引
     */
    @JSONField(name = "cloud_point_type")
    private int cloudMapPointTypeId;

    @JSONField(name = "point_level")
    private int pointLevel;

    private Long mapZipId;

    private int deleteFlag;

    /**
     * 关联地图ID
     */
    private Long mapInfoId;

    public MapPoint2(Long pointId) {
        this.setId(pointId);
    }
}
