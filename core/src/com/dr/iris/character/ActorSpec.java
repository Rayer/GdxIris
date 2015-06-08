package com.dr.iris.character;

import com.dr.iris.action.ActionBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rayer on 1/1/15.
 */
public class ActorSpec {
    Map<ActionBase.ActionType, List<ActionBase>> abilityAction = new HashMap<>();

    private int HP = 120;
    private int MaxHP = 120;
    private int ENG = 80;
    private int MaxENG = 80;
    private int STA = 150;
    private int MaxSTA = 150;
    private int EXPERTISE = 100;
    private float MoveSpeed = 80.0f;
    private Type type = Type.Friendly;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = this.MaxHP > HP ? HP : this.MaxHP;
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public void setMaxHP(int maxHP) {
        MaxHP = maxHP;
    }

    public int getENG() {
        return ENG;
    }

    public void setENG(int ENG) {
        this.ENG = this.MaxENG > ENG ? ENG : this.MaxENG;
    }

    public int getMaxENG() {
        return MaxENG;
    }

    public void setMaxENG(int maxENG) {
        MaxENG = maxENG;
    }

    public int getSTA() {
        return STA;
    }

    public void setSTA(int STA) {
        this.STA = this.MaxSTA > STA ? STA : this.MaxSTA;
    }

    public int getMaxSTA() {
        return MaxSTA;
    }

    public void setMaxSTA(int maxSTA) {
        MaxSTA = maxSTA;
    }

    public int getEXPERTISE() {
        return EXPERTISE;
    }

    public void setEXPERTISE(int EXPERTISE) {
        this.EXPERTISE = EXPERTISE;
    }

    public List<ActionBase> getMoveActions() {
        return abilityAction.get(ActionBase.ActionType.Move);
    }

    public void setMoveActions(List<ActionBase> actions) {
        abilityAction.put(ActionBase.ActionType.Move, actions);
    }

    public List<ActionBase> getBulletActions() {
        return abilityAction.get(ActionBase.ActionType.Bullet);
    }

    public void setBulletActions(List<ActionBase> actions) {
        abilityAction.put(ActionBase.ActionType.Bullet, actions);
    }

    public float getMoveSpeed() {
        return MoveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        MoveSpeed = moveSpeed;
    }

    enum Type {Main, Friendly, Enemy}


}
