package cn.test.stateMachine.state;

/**
 * Created by chay on 2018/12/12.
 */
public class EventConstant {
    public final static class PointType {
        public final static String CHARGE = "charge_point";
        public final static String STAND_BY = "stand_by_point";
        public final static String LOAD = "load_point";
    }

    public final static class MissionType {
        public final static String REGULAR = "regular_mission";
        public final static String NORMAL = "normal_mission";
    }

    public final static class MissionItemType {
        public final static String load = "load_mission";
        public final static String unload = "unload_mission";
    }
}
