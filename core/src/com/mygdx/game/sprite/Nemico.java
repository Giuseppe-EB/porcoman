package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nemico extends Sprite {

    private int direction;

    public Nemico() {
        super("nemico_queg.png");
        x = 400;
        y = 400;
        direction = 0;

    }

    @Override
    public void action() {

        check();
        //System.out.println(direction);
        switch (direction) {
            case (0):
                if(can_move[direction])
                    x -= 3;
                break;
            case (1):
                if(can_move[direction])
                    x+=3;
                break;
            case (2):
                if(can_move[direction])
                    y+=3;
                break;
            case(3):
                if(can_move[direction])
                    y-=3;
                break;
            default:

        }


    }

    private void check(){
        if(!can_move[0]&&direction==0){
            this.direction = 3;

        }
        else if(!can_move[1]&&direction==1){
            direction = 2;
            //set_allCan_move(0, true);
        }
        else if (!can_move[2]&&direction==2){
            direction = 0;
            //set_allCan_move(0, true);
        }
        else if(!can_move[3]&&direction==3){
            direction = 1;
            //set_allCan_move(0, true);
        }
        set_allCan_move(0, true);



        //return direction;
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
