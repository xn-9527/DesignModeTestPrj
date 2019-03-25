package cn.test.equals;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Chay on 2017/6/7.
 * 导航目标点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class MapPoint extends BaseBean {
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

    public MapPoint(Long pointId) {
        this.setId(pointId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            log.info("引用相同");
            return true;
        }
        if (!(o instanceof MapPoint)) {
            log.info("类型不是MapPoint");
            return false;
        }
        if (!super.equals(o)) {
            log.info("this父类的hashCode方法：" + super.hashCode());
            log.info("比较对象o的hashCode：" + o.hashCode());
            log.info("父类的equls匹配失败");
            return false;
        }

        MapPoint mapPoint = (MapPoint) o;

        if (Double.compare(mapPoint.x, x) != 0) {
            log.info("x不同");
            return false;
        }
        if (Double.compare(mapPoint.y, y) != 0) {
            log.info("y不同");
            return false;
        }
        if (Double.compare(mapPoint.th, th) != 0) {
            log.info("th不同");
            return false;
        }
        if (cloudMapPointTypeId != mapPoint.cloudMapPointTypeId) {
            log.info("cloudMapPointTypeId不同");
            return false;
        }
        if (deleteFlag != mapPoint.deleteFlag) {
            log.info("deleteFlag不同");
            return false;
        }
        if (!pointName.equals(mapPoint.pointName)) {
            log.info("pointName不同");
            return false;
        }
        if (!sceneName.equals(mapPoint.sceneName)) {
            log.info("sceneName不同");
            return false;
        }
        if (!mapName.equals(mapPoint.mapName)) {
            log.info("mapName不同");
            return false;
        }
        if (!mapInfoId.equals(mapPoint.mapInfoId)) {
            log.info("mapInfoId不同");
        }
        return mapInfoId.equals(mapPoint.mapInfoId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + pointName.hashCode();
        result = 31 * result + sceneName.hashCode();
        result = 31 * result + mapName.hashCode();
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(th);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + cloudMapPointTypeId;
        result = 31 * result + deleteFlag;
        result = 31 * result + mapInfoId.hashCode();
        return result;
    }
}
