package cn.test.stateMachine.state.base;

/**
 * Created by chay on 2018/12/13.
 */
public interface BaseState {
//    String getStateName();

    void onHeartBeat();
    void noHeartBeat();

    void onNormalMission();
    void noNormalMission();
    void available();
    void notAvailable();

    //去充电点、去待命点、去装货点
    void goToSpecialPointMissionStart(String pointType);
    void goToSpecialPointMissionFinish(String pointType);

    //装货、卸货
    void specialMissionStart(String missionItemType);
    void specialMissionFinish(String missionItemType);

    //在充电点、待命点、装货点
    void atSpecialPoint(String pointType);
    void notAtSpecialPoint(String pointType);

    void fullPower();
    void notFullPower();

    void onCharge();
    void notCharge();
}
