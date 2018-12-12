package cn.test.stateMachine.state;

/**
 * Created by charge on 2018/12/12.
 */
public class State {
    private String currentState;
    private String lastState;

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getLastState() {
        return lastState;
    }

    public void setLastState(String lastState) {
        this.lastState = lastState;
    }

    public void setLastAndCurrentState(String lastState, String currentState) {
        this.lastState = lastState;
        this.currentState = currentState;
    }

    @Override
    public String toString() {
        return "State{" +
                "currentState='" + currentState + '\'' +
                ", lastState='" + lastState + '\'' +
                '}';
    }
}
