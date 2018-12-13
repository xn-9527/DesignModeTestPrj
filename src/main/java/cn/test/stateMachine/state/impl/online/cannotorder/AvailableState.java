package cn.test.stateMachine.state.impl.online.cannotorder;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;
import cn.test.stateMachine.state.base.CanNotOrderState;
import cn.test.stateMachine.state.impl.offline.OffLineState;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class AvailableState implements CanNotOrderState {
    RobotState robotState;

    public AvailableState(RobotState robotState) {
        this.robotState = robotState;
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
        if (lastState instanceof AvailableState) {
            robotState.setCurrentState(robotState.getOnTransportState());
            robotState.setLastState(lastState);
        }
    }

    @Override
    public void noNormalMission() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof AvailableState) {
            robotState.setCurrentState(robotState.getCanOrderState());
            robotState.setLastState(lastState);
        }
    }

    @Override
    public void available() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof NotAvailableState) {
            robotState.setCurrentState(robotState.getAvailableState());
            robotState.setLastState(lastState);
        }
    }

    @Override
    public void notAvailable() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getNotAvailableState());
        robotState.setLastState(lastState);
    }

    @Override
    public void goToSpecialPointMissionStart(String pointType) {

    }

    @Override
    public void goToSpecialPointMissionFinish(String pointType) {

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
