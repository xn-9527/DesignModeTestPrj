package cn.test.stateMachine.state.impl.offline;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class OffLineState implements BaseState {
    RobotState robotState;

    public OffLineState(RobotState robotState) {
        this.robotState = robotState;
    }

    @Override
    public void onHeartBeat() {
        BaseState lastState = robotState.getCurrentState();
        //如果是从离线转换，则处理成在线的不可用；如果是其他的在线转换，不处理
        if (lastState instanceof OffLineState) {
            robotState.setCurrentState(robotState.getNotAvailableState());
            robotState.setLastState(lastState);
        }
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

    }

    @Override
    public void notAvailable() {

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
