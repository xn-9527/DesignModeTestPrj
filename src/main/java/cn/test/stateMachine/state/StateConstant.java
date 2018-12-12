package cn.test.stateMachine.state;

/**
 * Created by Chay on 2018/12/12.
 */
public final class StateConstant {
    public final static String STATE_SPLIT = "->";
    public final static class OffLine {
        public final static String OFF_LINE = "offLine";
    }

    public static class OnLine {
        public final static String ON_LINE = "online";
        public static class CanOrder extends OnLine {
            public final static String CHARGE_COMPLETED = "charge_completed";
            public final static String CHARGING = "charging";
            public final static String AT_CHARGE_POINT = "at_charge_point";
            public final static String GO_TO_CHARGE_POINT = "go_to_charge_point";
            public final static String GO_TO_STAND_BY_POINT = "go_to_stand_by_point";
            public final static String AT_STAND_BY_POINT = "at_stand_by_point";
            public final static String CAN_ORDER = "can_order";
        }

        public static class CanNotOrder extends OnLine {
            public final static String CAN_NOT_ORDER = "can_not_order";
            public final static class OnTransport extends CanNotOrder {
                public final static String GO_TO_LOAD = "go_to_load";
                public final static String LOADING = "loading";
                public final static String ON_TRANSPORT = "on_transport";
                public final static String UNLOADING = "unloading";
            }
            public final static String TRANSPORT_PAUSED = "transport_paused";
            public final static String NOT_TRANSPORT = "not_transport";
            public final static String AVAILABLE = "available";
            public final static String NOT_AVAILABLE = "not_available";
        }
    }

}
