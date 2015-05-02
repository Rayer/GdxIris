package com.dr.iris.ui;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 5/2/15.
 */
public class UIObjectsManager {

    private static UIObjectsManager inst;
    List<UIObject> uiObjectList = new ArrayList<>();

    private UIObjectsManager() {

    }

    public static UIObjectsManager getInst() {
        if (inst == null) inst = new UIObjectsManager();
        return inst;
    }

    public void addUIObject(UIObject object) {
        uiObjectList.add(object);
    }

    public void update(float delta) {
        for (UIObject uo : uiObjectList)
            uo.update(delta);
    }

    public void render(Batch batch) {
        for (UIObject uo : uiObjectList)
            uo.draw(batch);
    }


}
