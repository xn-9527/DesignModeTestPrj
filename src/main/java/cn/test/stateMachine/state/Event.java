package cn.test.stateMachine.state;

/**
 * Created by chay on 2018/12/12.
 */
public class Event {
    public final static class HeartBeatEvent {
        public final static State onHeartbeat(State state) {
            String lastState = state.getCurrentState();
            //当且仅当上一个状态是离线，才修改成在线，其他在线的子状态，不用修改
            if (StateConstant.OffLine.OFF_LINE == lastState) {
                state.setLastAndCurrentState(lastState, StateConstant.OnLine.ON_LINE);
            }
            return state;
        }

        public final static State noHeartbeat(State state) {
            String lastState = state.getCurrentState();
            state.setLastAndCurrentState(lastState, lastState + StateConstant.STATE_SPLIT + StateConstant.OffLine.OFF_LINE);
            return state;
        }
    }

    public final static class PowerEvent {
        public final static State fullPower(State state) {
            String lastState = state.getCurrentState();
            if (StateConstant.OnLine.CanOrder.CHARGING == lastState) {
                state.setLastAndCurrentState(lastState, StateConstant.OnLine.CanOrder.CHARGE_COMPLETED);
            }
            return state;
        }

        public final static State notFullPower(State state) {
            String lastState = state.getCurrentState();
            if (StateConstant.OnLine.CanOrder.CHARGE_COMPLETED == lastState) {
                state.setLastAndCurrentState(lastState, StateConstant.OnLine.CanOrder.CHARGING);
            }
            return state;
        }
    }

    public final static class ChargeEvent {
        public final static State onCharge(State state) {
            String lastState = state.getCurrentState();
            if (StateConstant.OnLine.CanOrder.AT_CHARGE_POINT == lastState) {
                state.setLastAndCurrentState(lastState, StateConstant.OnLine.CanOrder.CHARGING);
            }
            return state;
        }

        public final static State notCharge(State state) {
            String lastState = state.getCurrentState();
            if (StateConstant.OnLine.CanOrder.CHARGING == lastState) {
                state.setLastAndCurrentState(lastState, StateConstant.OnLine.CanOrder.AT_CHARGE_POINT);
            }
            return state;
        }
    }

    public final static class PositionEvent {
        public final static State atSpecialPoint(State state, String pointType, String missionType) {
            String lastState = state.getCurrentState();
            String lastStateCondition = null;
            String currentState = null;
            if (EventConstant.PointType.CHARGE == pointType) {
                lastStateCondition = StateConstant.OnLine.CanOrder.GO_TO_CHARGE_POINT;
                currentState = StateConstant.OnLine.CanOrder.AT_CHARGE_POINT;
            } else if (EventConstant.PointType.STAND_BY == pointType) {
                lastStateCondition = StateConstant.OnLine.CanOrder.GO_TO_STAND_BY_POINT;
                currentState = StateConstant.OnLine.CanOrder.AT_STAND_BY_POINT;
            } else if (EventConstant.PointType.LOAD == pointType) {
                lastStateCondition = StateConstant.OnLine.CanNotOrder.OnTransport.GO_TO_LOAD;
                currentState = StateConstant.OnLine.CanNotOrder.OnTransport.LOADING;
            }

            String missionTypeCondition = null;
            if (EventConstant.MissionType.REGULAR == missionType) {
                missionTypeCondition = StateConstant.OnLine.CanOrder.CAN_ORDER;
            } else if () {
                missionTypeCondition = StateConstant.OnLine.CanNotOrder.CAN_NOT_ORDER;
            }

            if (lastStateCondition == lastState
                    || missionTypeCondition == lastState) {
                state.setLastAndCurrentState(lastState, currentState);
            }
            return state;
        }

        public final static State notAtSpecialPoint(State state, String pointType) {

        }
    }

    public final static class MissionEvent {
        public final static State goToTargetPointStart(State state, String pointType, String missionType) {

        }

        public final static String goToTargetPointFinish(State state, String pointType, String missionType) {

        }

        public final static State onMission(State state, String missionType) {

        }

        public final static State noMission(State state, String missionType) {

        }

        public final static State missionItemStart(State state, String missionItemType) {

        }

        public final static State missionItemFinish(State state, String missionItemType) {

        }
    }

    public final static class KeyAvailableEvent {
        public final static State isAvailable(State state) {

        }

        public final static String notAvailable(State state) {

        }
    }
}
