package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.Level;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Bomb extends Sprite {

    private static Logger log= Logger.getLogger("log");

    private int bombRange = 1;

    int count = 0;
    boolean explosion = false;
    boolean alive = false;
    ArrayList<Hitbox> hitBoxes;


    @Override
    public boolean collision(Hitbox hitbox) {



        for(Hitbox hit : hitBoxes) {


            if (explosion && hit.collision(hitbox)) {

                return true;
            }
        }
        
        return false;


    }

    public Bomb(int x, int y, int bombRange, Level level) {
        super("bombs.png");
        alive = true;
        this.bombRange = bombRange;
        hitBoxes =  new ArrayList<Hitbox>();
        super.setX(x);
        super.setY(y);
        boolean [] dirClean = {true, true, true, true};
        for(int i = 1; i <= bombRange; i++) {
            if(dirClean[1]) {
                if (level.getCell(super.getX() / 40 + i, super.getY() / 40) != 11 &&
                        level.getCell(super.getX() / 40 + i, super.getY() / 40) != 1)
                    hitBoxes.add(new Hitbox(x + (i * 20 + 20), y));
                else
                    dirClean[1] = false;
            }
            if(dirClean[0]) {
                if (level.getCell(super.getX() / 40 - i, super.getY() / 40) != 11 &&
                        level.getCell(super.getX() / 40 - i, super.getY() / 40) != 1)
                    hitBoxes.add(new Hitbox(x - (i * 20 + 20), y));
                else
                    dirClean[0] = false;
            }
            if(dirClean[2]) {
                if (level.getCell(super.getX() / 40 , super.getY() / 40 + i) != 11 &&
                        level.getCell(super.getX() / 40 , super.getY() / 40 + i) != 1)
                    hitBoxes.add(new Hitbox(x, y + (i * 20 + 20)));
                else
                    dirClean[2] = false;
            }
            if(dirClean[3]) {
                if (level.getCell(super.getX() / 40 , super.getY() / 40 - i) != 11 &&
                        level.getCell(super.getX() / 40 , super.getY() / 40 - i) != 1)
                    hitBoxes.add(new Hitbox(x, y - (i * 20 + 20)));
                else
                    dirClean[3] = false;
            }
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update(Level level) {
        return;
    }

    @Override
    public void update(int x, int y) {
        return;
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
        if(count==80) {
            explosion = true;
            switch (bombRange) {
                case 1:
                    this.texture = new Texture("BombermanEffect1.png");
                    break;
                case 2:
                    this.texture= new  Texture("esplosione2.png");
                    break;
                case 3:
                    this.texture= new  Texture("Esplosione3.png");
                    break;
                case 4:
                    this.texture= new  Texture("esplosione4.png");
                    break;
                default:
                    this.texture = new Texture("BombermanEffect1.png");

            }
        }
        if(count==100) {
            explosion = false;
            alive = false;
            count = 0;
        }
    }

    @Override
    public void update_hitbox() {

        return;

    }

    @Override
    public void draw(SpriteBatch batch) {
        if(!explosion)
            batch.draw(this.texture, this.x, this.y);
        else
            switch (bombRange) {
                case 1:
                    batch.draw(this.texture, this.x-40, this.y-40);
                    break;
                case 2:
                    batch.draw(this.texture, this.x-60, this.y-60);
                    break;
                case 3:
                    batch.draw(this.texture, this.x-80, this.y-80);
                    break;
                case 4:
                    batch.draw(this.texture, this.x-100, this.y-100);
                    break;
                default:
                    batch.draw(this.texture, this.x-40, this.y-40);

            }

    }
}
