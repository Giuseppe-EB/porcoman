package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.Level;

import java.util.ArrayList;

public abstract class Sprite {

    Texture texture;
    protected Hitbox hitbox;
    protected int x;
    protected int y;

    protected boolean alive = true;

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean[] getCan_move() {
        return can_move;
    }

    public void setCan_move(boolean[] can_move) {
        this.can_move = can_move;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }
    public void update_hitbox(){
        hitbox.update(x, y);
    }
    protected int width;
    protected int height;
    protected boolean[] can_move ;

    public Sprite(String path){
        can_move = new boolean[]{true, true, true, true};
        this.texture = new Texture(path);
        hitbox = new Hitbox(0,0);
    }
    public abstract void action() throws Exception;
    public abstract void draw(SpriteBatch batch);

    public boolean isAlive(){
        return true;
    }
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getX() {
        int temp = x/40;
        temp*=40;
        return temp;
    }

    public boolean collision(Hitbox hitbox){
        return this.hitbox.collision(hitbox);
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        int temp = y/40;
        temp*=40;
        return temp;
    }
    public void update(Level level){

        if(level.getCell((getX()/40)+1, getY()/40)%10 != 0){

            //System.out.println(this.getClass() + "bloccato da destra");
            //System.out.println(this.getClass());
            setCan_move(1, false);
        }
        if(level.getCell((getX()/40)-1, getY()/40)%10 != 0){
            //System.out.println(this.getClass() + "bloccato da sinistra");
            setCan_move(0, false);
        }
        if(level.getCell((getX()/40), (getY()/40) + 1)%10 != 0){
            setCan_move(2, false);
            //System.out.println(this.getClass() + "bloccato da su");
        }
        if(level.getCell((getX()/40), (getY()/40) - 1 )%10 != 0){
            setCan_move(3, false);
            //System.out.println(this.getClass() + "bloccato da giu");
        }

    }
    public abstract void update(int x, int y);
    public void setCan_move(int i, boolean val){

        can_move[i]=val;

    }
    public void set_allCan_move(int i, boolean val){

        if(i<4) {
            can_move[i] = val;
            set_allCan_move(i + 1, val);
        }
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
