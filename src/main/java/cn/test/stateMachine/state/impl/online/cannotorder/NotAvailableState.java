package cn.test.stateMachine.state.impl.online.cannotorder;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;
import cn.test.stateMachine.state.base.CanNotOrderState;
import cn.test.stateMachine.state.base.OnTransportState;
import cn.test.stateMachine.state.impl.offline.OffLineState;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class NotAvailableState implements CanNotOrderState {
    RobotState robotState;

    public NotAvailableState(RobotState robotState) {
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

    }

    @Override
    public void noNormalMission() {

    }

    @Override
    public void available() {
        BaseState lastState = robotState.getCurrentState();
        //如果在运输暂停中可用，则设置为运输中
        if (lastState instanceof TransportPausedState) {
            robotState.setCurrentState(robotState.getOnTransportState());
        } else {
            robotState.setCurrentState(robotState.getAvailableState());
        }
        robotState.setLastState(lastState);
    }

    @Override
    public void notAvailable() {
        BaseState lastState = robotState.getCurrentState();
        //如果在运输中不可用，则设置为运输暂停
        if (lastState instanceof OnTransportState) {
            robotState.setCurrentState(robotState.getTransportPausedState());
            robotState.setLastState(lastState);
        }
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
