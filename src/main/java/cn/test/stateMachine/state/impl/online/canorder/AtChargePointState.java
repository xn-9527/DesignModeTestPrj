package cn.test.stateMachine.state.impl.online.canorder;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;
import cn.test.stateMachine.state.base.CanOrderState;
import cn.test.stateMachine.state.constant.EventConstant;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class AtChargePointState implements CanOrderState {
    RobotState robotState;

    public AtChargePointState(RobotState robotState) {
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

    @Override
    public void onHeartBeat() {

    }

    @Override
    public void noHeartBeat() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getOfflineState());
        robotState.setLastState(lastState);
    }

    @Override
    public void onNormalMission() {

    }

    @Override
    public void noNormalMission() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getOnTransportState());
        robotState.setLastState(lastState);
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
        if (EventConstant.PointType.STAND_BY == pointType) {
            gotoStandbyPointMissionStart();
        }
    }

    @Override
    public void goToSpecialPointMissionFinish(String pointType) {
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
        if (EventConstant.PointType.CHARGE == pointType) {
            atChargePoint();
        }
    }

    @Override
    public void notAtSpecialPoint(String pointType) {
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
