package cn.test.stateMachine.machine;

import cn.test.stateMachine.state.base.*;
import cn.test.stateMachine.state.impl.offline.OffLineState;
import cn.test.stateMachine.state.impl.online.cannotorder.AvailableState;
import cn.test.stateMachine.state.impl.online.cannotorder.NotAvailableState;
import cn.test.stateMachine.state.impl.online.cannotorder.NotOnTransportState;
import cn.test.stateMachine.state.impl.online.cannotorder.TransportPausedState;
import cn.test.stateMachine.state.impl.online.cannotorder.ontransport.GoToLoadPointState;
import cn.test.stateMachine.state.impl.online.cannotorder.ontransport.LoadingState;
import cn.test.stateMachine.state.impl.online.cannotorder.ontransport.OnTransportStateImpl;
import cn.test.stateMachine.state.impl.online.cannotorder.ontransport.UnLoadingState;
import cn.test.stateMachine.state.impl.online.canorder.*;

/**
 * Created by chay on 2018/12/12.
 */
public class RobotState implements BaseState, CanOrderState, CanNotOrderState, OnLineState, OnTransportState {
    BaseState offlineState;
    BaseState chargeCompletedState;
    BaseState chargingState;
    BaseState atChargePointState;
    BaseState gotoChargePointState;
    BaseState atStandByPointState;
    BaseState gotoStandByPointState;
    BaseState canOrderState;

    BaseState gotoLoadPointState;
    BaseState loadingState;
    BaseState unloadingState;
    BaseState onTransportState;

    BaseState transportPausedState;
    BaseState noTransportState;
    BaseState availableState;
    BaseState notAvailableState;

    //--------------------------------
    BaseState lastState = offlineState;
    BaseState currentState = offlineState;

    public RobotState() {
        this.offlineState = new OffLineState(this);
        this.chargeCompletedState = new ChargeCompletedState(this);
        this.chargingState = new ChargingState(this);
        this.atChargePointState = new AtChargePointState(this);
        this.gotoChargePointState = new GoToChargePointState(this);
        this.atStandByPointState = new AtStandbyPointState(this);
        this.gotoStandByPointState = new GoToStandbyPointState(this);
        this.canOrderState = new CanOrderStateImpl(this);
        this.gotoLoadPointState = new GoToLoadPointState(this);
        this.loadingState = new LoadingState(this);
        this.unloadingState = new UnLoadingState(this);
        this.onTransportState = new OnTransportStateImpl(this);
        this.transportPausedState = new TransportPausedState(this);
        this.noTransportState = new NotOnTransportState(this);
        this.availableState = new AvailableState(this);
        this.notAvailableState = new NotAvailableState(this);

        this.currentState = offlineState;
        this.lastState = offlineState;
    }

    @Override
    public void onHeartBeat() {
        currentState.onHeartBeat();
    }

    @Override
    public void noHeartBeat() {
        currentState.noHeartBeat();
    }

    @Override
    public void onNormalMission() {
        currentState.onNormalMission();
    }

    @Override
    public void noNormalMission() {
        currentState.noNormalMission();
    }

    @Override
    public void available() {
        currentState.available();
    }

    @Override
    public void notAvailable() {
        currentState.notAvailable();
    }

    @Override
    public void goToSpecialPointMissionStart(String pointType) {
        currentState.goToSpecialPointMissionStart(pointType);
    }

    @Override
    public void goToSpecialPointMissionFinish(String pointType) {
        currentState.goToSpecialPointMissionFinish(pointType);
    }

    @Override
    public void specialMissionStart(String missionItemType) {
        currentState.specialMissionStart(missionItemType);
    }

    @Override
    public void specialMissionFinish(String missionItemType) {
        currentState.specialMissionFinish(missionItemType);
    }

    @Override
    public void atSpecialPoint(String pointType) {
        currentState.atSpecialPoint(pointType);
    }

    @Override
    public void notAtSpecialPoint(String pointType) {
        currentState.notAtSpecialPoint(pointType);
    }

    @Override
    public void fullPower() {
        currentState.fullPower();
    }

    @Override
    public void notFullPower() {
        currentState.notFullPower();
    }

    @Override
    public void onCharge() {
        currentState.onCharge();
    }

    @Override
    public void notCharge() {
        currentState.notCharge();
    }

    public BaseState getOfflineState() {
        return offlineState;
    }

    public void setOfflineState(BaseState offlineState) {
        this.offlineState = offlineState;
    }

    public BaseState getChargeCompletedState() {
        return chargeCompletedState;
    }

    public void setChargeCompletedState(BaseState chargeCompletedState) {
        this.chargeCompletedState = chargeCompletedState;
    }

    public BaseState getChargingState() {
        return chargingState;
    }

    public void setChargingState(BaseState chargingState) {
        this.chargingState = chargingState;
    }

    public BaseState getAtChargePointState() {
        return atChargePointState;
    }

    public void setAtChargePointState(BaseState atChargePointState) {
        this.atChargePointState = atChargePointState;
    }

    public BaseState getGotoChargePointState() {
        return gotoChargePointState;
    }

    public void setGotoChargePointState(BaseState gotoChargePointState) {
        this.gotoChargePointState = gotoChargePointState;
    }

    public BaseState getAtStandByPointState() {
        return atStandByPointState;
    }

    public void setAtStandByPointState(BaseState atStandByPointState) {
        this.atStandByPointState = atStandByPointState;
    }

    public BaseState getGotoStandByPointState() {
        return gotoStandByPointState;
    }

    public void setGotoStandByPointState(BaseState gotoStandByPointState) {
        this.gotoStandByPointState = gotoStandByPointState;
    }

    public BaseState getCanOrderState() {
        return canOrderState;
    }

    public void setCanOrderState(BaseState canOrderState) {
        this.canOrderState = canOrderState;
    }

    public BaseState getGotoLoadPointState() {
        return gotoLoadPointState;
    }

    public void setGotoLoadPointState(BaseState gotoLoadPointState) {
        this.gotoLoadPointState = gotoLoadPointState;
    }

    public BaseState getLoadingState() {
        return loadingState;
    }

    public void setLoadingState(BaseState loadingState) {
        this.loadingState = loadingState;
    }

    public BaseState getUnloadingState() {
        return unloadingState;
    }

    public void setUnloadingState(BaseState unloadingState) {
        this.unloadingState = unloadingState;
    }

    public BaseState getOnTransportState() {
        return onTransportState;
    }

    public void setOnTransportState(BaseState onTransportState) {
        this.onTransportState = onTransportState;
    }

    public BaseState getTransportPausedState() {
        return transportPausedState;
    }

    public void setTransportPausedState(BaseState transportPausedState) {
        this.transportPausedState = transportPausedState;
    }

    public BaseState getNoTransportState() {
        return noTransportState;
    }

    public void setNoTransportState(BaseState noTransportState) {
        this.noTransportState = noTransportState;
    }

    public BaseState getAvailableState() {
        return availableState;
    }

    public void setAvailableState(BaseState availableState) {
        this.availableState = availableState;
    }

    public BaseState getNotAvailableState() {
        return notAvailableState;
    }

    public void setNotAvailableState(BaseState notAvailableState) {
        this.notAvailableState = notAvailableState;
    }

    public BaseState getLastState() {
        return lastState;
    }

    public void setLastState(BaseState lastState) {
        this.lastState = lastState;
    }

    public BaseState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(BaseState currentState) {
        this.currentState = currentState;
    }
}
