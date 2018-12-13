package cn.test.stateMachine.state.impl.online.canorder;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;
import cn.test.stateMachine.state.base.CanOrderState;
import cn.test.stateMachine.state.constant.EventConstant;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class GoToChargePointState implements CanOrderState {
    RobotState robotState;

    public GoToChargePointState(RobotState robotState) {
        this.robotState = robotState;
    }

    public void goToChargePointMissionStart() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getGotoChargePointState());
        robotState.setLastState(lastState);
    }
    public void goToChargePointMissionFinish() {
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
        if (EventConstant.PointType.CHARGE == pointType) {
            goToChargePointMissionStart();
        }
    }

    @Override
    public void goToSpecialPointMissionFinish(String pointType) {
        if (EventConstant.PointType.CHARGE == pointType) {
            goToChargePointMissionFinish();
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
