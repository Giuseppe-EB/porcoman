package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Player extends Sprite {

    private int count ;
    private boolean can_hit ;
    private boolean[] can_move ;

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
        can_hit = true;
        can_move = new boolean[]{true, true, true, true};
        count=0;
    }
    public void setCan_move(int i, boolean val){

        can_move[i]=val;

    }
    public void set_allCan_move(int i, boolean val){

        if(i<4) {
            can_move[i] = val;
            set_allCan_move(i + 1, val);
        }
    }
/*

        LEGEND:
                0 LEFT
                1 RIGHT
                2 UP
                3 DOWN

 */
    @Override
    public void action() {
        if (!can_hit)
            count++;
        if (count == 80) {
            can_hit = true;
            count = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)&&can_move[0]) {
            set_allCan_move(0, true);
            this.x -= 5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)&&can_move[1]) {
            set_allCan_move(0, true);
            this.x += 5;
        }
        if (Gdx.input.isKeyPressed((Input.Keys.W))&&can_move[2]) {
            this.y += 5;
            set_allCan_move(0, true);
        }
        if (Gdx.input.isKeyPressed((Input.Keys.S))&&can_move[3]){
            set_allCan_move(0, true);
            this.y -= 5;
        }
        if (Gdx.input.isKeyPressed((Input.Keys.Z)) && can_hit) {
            can_hit = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y);
    }
}
