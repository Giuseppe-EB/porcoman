package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Player extends Sprite {

    private int count ;
    private boolean can_hit ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCan_hit() {
        return can_hit;
    }

    public void setCan_hit(boolean can_hit) {
        this.can_hit = can_hit;
    }

    public Player() {
        super("stupid.png");
        can_hit=true;
        count=0;
    }

    @Override
    public void action() {
        if (!can_hit)
            count++;
        if (count == 80) {
            can_hit = true;
            count = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.x -= 5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += 5;
        }
        if (Gdx.input.isKeyPressed((Input.Keys.W)))
            this.y += 5;
        if (Gdx.input.isKeyPressed((Input.Keys.S)))
            this.y -= 5;
        if (Gdx.input.isKeyPressed((Input.Keys.Z)) && can_hit) {
            can_hit = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y);
    }
}
