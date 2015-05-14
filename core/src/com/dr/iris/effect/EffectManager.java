package com.dr.iris.effect;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 5/14/15.
 */
public class EffectManager {

    static private EffectManager inst;
    private List<AlphaEffect> currentEffectList = new ArrayList<>();

    private EffectManager() {

    }

    static public EffectManager getInst() {
        if (inst == null) inst = new EffectManager();
        return inst;
    }

    public void addEffect(AlphaEffect effect) {
        currentEffectList.add(effect);
    }

    //TODO : addEffect(EffectSpec effectSpec)
    //TODO : AlphaEffect EffectFactory(float x, float y).setAlphaTime(float time).createEffect();

    public void update(float delta) {
        for (int i = 0; i < currentEffectList.size(); ++i) {
            AlphaEffect spec = currentEffectList.get(i);
            if (spec.update(delta) == false) {
                currentEffectList.remove(i);
            }
        }
    }

    public void render(Batch batch) {
        for (AlphaEffect effect : currentEffectList) {
            effect.draw(batch);
        }
    }
}
