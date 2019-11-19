package cn.test.fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by chay on 2019/11/19.
 */
@Data
public class JsonMissionDataPoint implements Serializable {

    private static final long serialVersionUID = -8045827656316857554L;

    @JSONField(name = "scene_name")
    String sceneName;
    @JSONField(name = "map_name")
    String mapName;
    @JSONField(name = "point_name")
    String pointName;
    double x;
    double y;
    double th;
}
