package cn.test.stateMachine;

import cn.test.stateMachine.machine.RobotState;
import cn.test.stateMachine.state.constant.EventConstant;

/**
 * Created by chay on 2018/12/12.
 */
public class TestStateMachine {
    public static void main(String[] args) {
        RobotState robotState = new RobotState();
        printState(robotState);

        robotState.onHeartBeat();
        printState(robotState);

        robotState.available();
        printState(robotState);

        robotState.noNormalMission();
        printState(robotState);

        robotState.noNormalMission();
        printState(robotState);

        robotState.goToSpecialPointMissionStart(EventConstant.PointType.CHARGE);
        printState(robotState);

        robotState.atSpecialPoint(EventConstant.PointType.CHARGE);
        printState(robotState);

        robotState.onCharge();
        printState(robotState);

        robotState.fullPower();
        printState(robotState);

        robotState.goToSpecialPointMissionStart(EventConstant.PointType.STAND_BY);
        printState(robotState);

        robotState.atSpecialPoint(EventConstant.PointType.STAND_BY);
        printState(robotState);

        robotState.goToSpecialPointMissionFinish(EventConstant.PointType.STAND_BY);
        printState(robotState);

        robotState.notAtSpecialPoint(EventConstant.PointType.STAND_BY);
        printState(robotState);
    }

    public final static void printState(RobotState robotState) {
        System.out.println("currentState:##" + robotState.getCurrentState() + "####,lastState:!!!!" + robotState.getLastState());
    }
}
