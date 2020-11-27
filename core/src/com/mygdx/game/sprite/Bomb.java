package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Bomb extends Sprite {

    int count = 0;
    boolean alive = false;

    public Bomb(int x, int y) {
        super("bombs.png");
        alive = true;
        super.setX(x);
        super.setY(y);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Bomb(){
        super("bombs.png");
    }

    @Override
    public void action() {
        count++;
        if(count==100) {
            alive = false;
            count = 0;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y);
    }
}
