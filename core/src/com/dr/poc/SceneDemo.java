package com.dr.poc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Rayer on 12/30/14.
 */

class Cell {

    static Texture cellHealthyTexture;
    static Texture cellOldTexture;
    static Texture cellDyingTexture;

    int health = 3;
    boolean cellAlive = false;

    Sprite cellSpriteHealthy;
    Sprite cellSpriteOld;
    Sprite cellSpriteDying;

    int cellX;
    int cellY;

    public Cell(int x, int y, boolean alive) {
        if (cellHealthyTexture == null) {
            //create pixmap
            Pixmap pixmap = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.GREEN);
            pixmap.fill();
            cellHealthyTexture = new Texture(pixmap);

            Pixmap pixmap2 = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
            pixmap2.setColor(Color.YELLOW);
            pixmap2.fill();
            cellOldTexture = new Texture(pixmap2);

            Pixmap pixmap3 = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
            pixmap3.setColor(Color.RED);
            pixmap3.fill();
            cellDyingTexture = new Texture(pixmap3);
        }

        cellX = x;
        cellY = y;
        cellAlive = alive;
        cellSpriteHealthy = new Sprite(cellHealthyTexture);
        cellSpriteOld = new Sprite(cellOldTexture);
        cellSpriteDying = new Sprite(cellDyingTexture);
    }

    int getCellX() {
        return cellX;
    }

    int getCellY() {
        return cellY;
    }

    void setCellAlive(boolean alive) {
        if (cellAlive == true && alive == true)
            health -= 1;
        else
            health = 3;

        cellAlive = alive;
        if (alive == false) health = 0;
        if (health == 0) cellAlive = false;
    }

    public void draw(SpriteBatch sb) {


        Sprite cellSprite = null;
        if (health >= 3) cellSprite = cellSpriteHealthy;
        if (health == 2) cellSprite = cellSpriteOld;
        if (health == 1) cellSprite = cellSpriteDying;

        if (!cellAlive) return;

        CellMapManager mgr = CellMapManager.getInst();
        cellSprite.setPosition(mgr.getCellPosX(this), mgr.getCellPosY(this));
        cellSprite.draw(sb);
    }

}

class CellMapManager {


    static private CellMapManager inst;
    ArrayList<ArrayList<Cell>> cellMap;
    int sizeX;
    int sizeY;
    int factor = 4;
    int baseX = 20;
    int baseY = 20;

    private CellMapManager() {

    }

    static public CellMapManager getInst() {
        if (inst == null) inst = new CellMapManager();

        return inst;
    }

    public void init(int sizeX, int sizeY, int percentage) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        int initAliveCount = 0;

        cellMap = new ArrayList<>(sizeX);
        Random random = new Random();

        for (int x = 0; x < sizeX; ++x) {
            ArrayList<Cell> yRaw = new ArrayList<Cell>(sizeY);
            cellMap.add(x, yRaw);
            for (int y = 0; y < sizeY; ++y) {
                boolean isAlive = random.nextInt(100) < percentage ? true : false;
                Cell cell = new Cell(x, y, isAlive);
                if (isAlive) initAliveCount += 1;
                yRaw.add(y, cell);
            }

        }

        System.out.println("Alive cells : " + initAliveCount + " out of " + sizeX * sizeY);


    }

    int getCellPosX(Cell cell) {
        return baseX + cell.getCellX() * factor;
    }

    int getCellPosY(Cell cell) {
        return baseY + cell.getCellY() * factor;
    }

    int getNearbyCellCount(Cell cell) {

        int count = 0;

        int x = cell.getCellX();
        int y = cell.getCellY();

        //upper row
        try {
            if (x - 1 > 0) {
                if (y - 1 >= 0) count += cellMap.get(x - 1).get(y - 1).cellAlive ? 1 : 0;
                count += cellMap.get(x - 1).get(y).cellAlive ? 1 : 0;
                if (y + 1 < sizeY) count += cellMap.get(x - 1).get(y + 1).cellAlive ? 1 : 0;
            }

            //center row
            if (y - 1 >= 0) count += cellMap.get(x).get(y - 1).cellAlive ? 1 : 0;
            //count += cellMap.get(x).get(y).cellAlive ? 1 : 0;
            if (y + 1 < sizeY) count += cellMap.get(x).get(y + 1).cellAlive ? 1 : 0;

            //lower row
            if (x + 1 < sizeX) {
                if (y - 1 >= 0) count += cellMap.get(x + 1).get(y - 1).cellAlive ? 1 : 0;
                count += cellMap.get(x + 1).get(y).cellAlive ? 1 : 0;
                if (y + 1 < sizeY) count += cellMap.get(x + 1).get(y + 1).cellAlive ? 1 : 0;
            }
        } catch (Exception e) {
            System.out.println("Error at x : " + x + " y : " + y);
            throw e;
        }


        return count;
    }

    public void update(float delta) {
        //update life or death in CellMap
        for (List<Cell> cellsArray : cellMap) {
            for (Cell c : cellsArray) {
                int nearbyCells = getNearbyCellCount(c);
                //System.out.println("NearbyCells : " + nearbyCells);
                if (c.cellAlive == true)
                    c.setCellAlive((nearbyCells < 2 || nearbyCells > 3) ? false : true);

                if (c.cellAlive == false) c.setCellAlive(nearbyCells == 3 ? true : false);
            }
        }
    }

    public void drawCells(SpriteBatch sb) {
        for (List<Cell> cellsArray : cellMap) {
            for (Cell c : cellsArray) {
                c.draw(sb);
            }
        }
    }
}

public class SceneDemo implements ApplicationListener, GestureDetector.GestureListener {


    SpriteBatch sb;
    float updatePeriod = 0.1f; //update rate = 0.5secone/once
    float nextUpdate = 0;
    boolean updateNext = false;

    @Override
    public void create() {
        sb = new SpriteBatch();
        CellMapManager mgr = CellMapManager.getInst();
        mgr.init(150, 150, 50);
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CellMapManager mgr = CellMapManager.getInst();

        sb.begin();
        mgr.drawCells(sb);
        sb.end();

//        if(updateNext == true) {
//            mgr.update(delta);
//            updateNext = false;
//        }
        if (nextUpdate > 0) {
            nextUpdate -= delta;
        } else {
            mgr.update(delta);
            nextUpdate = updatePeriod;
        }
//        mgr.update(delta);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        sb.dispose();
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("Tapped, and will update next!");
        updateNext = true;
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
