package cn.test.stateMachine.state.impl.online.canorder;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;
import cn.test.stateMachine.state.base.CanOrderState;
import cn.test.stateMachine.state.constant.EventConstant;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class ChargingState implements CanOrderState {
    RobotState robotState;

    public ChargingState(RobotState robotState) {
        this.robotState = robotState;
    }

    public void gotoStandbyPointMissionStart() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getGotoStandByPointState());
        robotState.setLastState(lastState);
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

    }

    @Override
    public void notAtSpecialPoint(String pointType) {

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
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof ChargeCompletedState) {
            robotState.setCurrentState(robotState.getChargingState());
            robotState.setLastState(lastState);
        }
    }

    @Override
    public void onCharge() {
        BaseState lastState = robotState.getCurrentState();
        if (!(lastState instanceof ChargingState)) {
            robotState.setCurrentState(robotState.getChargingState());
            robotState.setLastState(lastState);
        }
    }

    @Override
    public void notCharge() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getAtChargePointState());
        robotState.setLastState(lastState);
    }
}
