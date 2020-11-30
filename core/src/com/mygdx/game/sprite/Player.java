package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.Level;

import java.util.ArrayList;

public class Player extends Sprite {

    private int count ;
    private boolean can_hit ;
    //private boolean[] can_move ;

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
        this.x = 0;
        this.y = 580;
        can_hit = true;
        count=0;
    }
    @Override
    public void update(Level level) {
        super.update(level);
        if(level.getCell((getX()/40), (getY()/40) ) == 10){

            System.out.println("prossimo livello");
            level.setCsvFile("level2.csv");
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
            int x = this.x/40;
            int y = this.y/40;
            x*=40;
            y*=40;
            batch.draw(this.texture, x, y);
    }
}
