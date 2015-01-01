package com.dr.iris.character;

import com.dr.iris.action.ActionBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rayer on 1/1/15.
 */
public class ActorSpec {
    List<ActionBase> abilityActionBase = new ArrayList<>();

    private int HP = 120;
    private int MaxHP = 120;
    private int ENG = 80;
    private int MaxENG = 80;
    private int STA = 150;
    private int MaxSTA = 150;
    private int EXPERTISE = 100;
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

    enum Type {Main, Friendly, Enemy}


}
