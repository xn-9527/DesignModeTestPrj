package cn.test.stateMachine.state.impl.online.cannotorder.ontransport;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.base.BaseState;
import cn.test.stateMachine.state.base.CanOrderState;
import cn.test.stateMachine.state.base.OnTransportState;
import cn.test.stateMachine.state.constant.EventConstant;
import cn.test.stateMachine.state.impl.offline.OffLineState;

/**
 * Created by xiaoni on 2018/12/13.
 */
public class GoToLoadPointState implements OnTransportState {
    RobotState robotState;

    public GoToLoadPointState(RobotState robotState) {
        this.robotState = robotState;
    }

    public void gotoLoadPointMissionStart() {
        BaseState lastState = robotState.getCurrentState();
        if (lastState instanceof OnTransportState
                || lastState instanceof CanOrderState) {
            robotState.setCurrentState(robotState.getGotoLoadPointState());
            robotState.setLastState(lastState);
        }
    }

    public void gotoLoadPointMissionFinish() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getLoadingState());
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

    }

    @Override
    public void noNormalMission() {
        BaseState lastState = robotState.getCurrentState();
        robotState.setCurrentState(robotState.getNoTransportState());
        robotState.setLastState(lastState);
    }

    @Override
    public void available() {

    }

    @Override
    public void notAvailable() {

    }

    @Override
    public void goToSpecialPointMissionStart(String pointType) {
        if (EventConstant.PointType.LOAD == pointType) {
            gotoLoadPointMissionStart();
        }
    }

    @Override
    public void goToSpecialPointMissionFinish(String pointType) {
        if (EventConstant.PointType.LOAD == pointType) {
            gotoLoadPointMissionFinish();
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
