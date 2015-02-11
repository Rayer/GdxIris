package com.dr.iris.stage;

/**
 * Created by Rayer on 2/12/15.
 */
public class Stage {

    public Update_Status update(double delta) {
        return Update_Status.TERMINATED;
    }

    IScriptInterpreter getScriptInterpreter() {
        return null;
    }

    IActorManager getActorManager() {
        return null;
    }

    ICamera getMainCamera() {
        return null;
    }

    IAudioManager getAudioManager() {
        return null;
    }

    enum Update_Status {
        RUNNING,
        PAUSE,
        TERMINATED
    }

}
