package cn.test.stateMachine.state.impl.online.canorder;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;
import cn.test.stateMachine.state.base.CanOrderState;
import cn.test.stateMachine.state.constant.EventConstant;
import cn.test.stateMachine.state.impl.offline.OffLineState;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class CanOrderStateImpl implements CanOrderState {
    RobotState robotState;

    public CanOrderStateImpl(RobotState robotState) {
        this.robotState = robotState;
    }

    public void atChargePoint() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof CanOrderState
                && !(lastState instanceof AtChargePointState)) {
            robotState.setCurrentState(robotState.getAtChargePointState());
            robotState.setLastState(lastState);
        }
    }

    public void notAtChargePoint() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof CanOrderState
                && !(lastState instanceof CanOrderStateImpl)) {
            robotState.setCurrentState(robotState.getCanOrderState());
            robotState.setLastState(lastState);
        }
    }

    public void gotoStandbyPointMissionStart() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof AtChargePointState) {
            robotState.setCurrentState(robotState.getGotoStandByPointState());
            robotState.setLastState(lastState);
        }
    }

    public void gotoStandbyPointMissionFinish() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getAtStandByPointState());
        robotState.setLastState(lastState);
    }

    public void atStandbyPoint() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof CanOrderState
                && !(lastState instanceof AtStandbyPointState)) {
            robotState.setCurrentState(robotState.getAtStandByPointState());
            robotState.setLastState(lastState);
        }
    }

    public void notAtStandbyPoint() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof CanOrderState
                && !(lastState instanceof CanOrderStateImpl)) {
            robotState.setCurrentState(robotState.getCanOrderState());
            robotState.setLastState(lastState);
        }
    }

    public void gotoChargePointMissionStart() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof AtStandbyPointState) {
            robotState.setCurrentState(robotState.getGotoChargePointState());
            robotState.setLastState(lastState);
        }
    }

    public void gotoChargePointMissionFinish() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getAtChargePointState());
        robotState.setLastState(lastState);
    }

    @Override
    public void onHeartBeat() {

    }

    @Override
    public void noHeartBeat() {
        BaseState lastState = robotState.getCurrentState();
        if (!(lastState instanceof OffLineState)) {
            robotState.setCurrentState(robotState.getOfflineState());
            robotState.setLastState(lastState);
        }
    }

    @Override
    public void onNormalMission() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getOnTransportState());
        robotState.setLastState(lastState);
    }

    @Override
    public void noNormalMission() {

    }

    @Override
    public void available() {

    }

    @Override
    public void notAvailable() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getNotAvailableState());
        robotState.setLastState(lastState);
    }

    @Override
    public void goToSpecialPointMissionStart(String pointType) {
        if (EventConstant.PointType.CHARGE == pointType) {
            gotoChargePointMissionStart();
        }
        if (EventConstant.PointType.STAND_BY == pointType) {
            gotoStandbyPointMissionStart();
        }
    }

    @Override
    public void goToSpecialPointMissionFinish(String pointType) {
        if (EventConstant.PointType.CHARGE == pointType) {
            gotoChargePointMissionFinish();
        }
        if (EventConstant.PointType.STAND_BY == pointType) {
            gotoStandbyPointMissionFinish();
        }
    }

    @Override
    public void specialMissionStart(String missionItemType) {

    }

    @Override
    public void specialMissionFinish(String missionItemType) {

    }

    @Override
    public void atSpecialPoint(String pointType) {
        if (EventConstant.PointType.STAND_BY == pointType) {
            atStandbyPoint();
        }
        if (EventConstant.PointType.CHARGE == pointType) {
            atChargePoint();
        }
    }

    @Override
    public void notAtSpecialPoint(String pointType) {
        if (EventConstant.PointType.STAND_BY == pointType) {
            notAtStandbyPoint();
        }
        if (EventConstant.PointType.CHARGE == pointType) {
            notAtChargePoint();
        }
    }

    @Override
    public void fullPower() {
        BaseState lastState = robotState.getCurrentState();
        if (!(lastState instanceof ChargeCompletedState)) {
            robotState.setCurrentState(robotState.getChargeCompletedState());
            robotState.setLastState(lastState);
        }
    }

    @Override
    public void notFullPower() {

    }

    @Override
    public void onCharge() {

    }

    @Override
    public void notCharge() {

    }
}
