package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.Level;

import java.util.ArrayList;

public class Bomb extends Sprite {

    int count = 0;
    boolean explosion = false;
    boolean alive = false;
    ArrayList<Hitbox> hitboxes;


    @Override
    public boolean collision(Hitbox hitbox) {

        for(Hitbox hit : hitboxes)
            if(explosion&&hit.collision(hitbox)) {
             System.out.println("collp");
                return true;
            }
        /*this.hitbox.setX(this.x + 40);
        if(this.hitbox.collision(hitbox)){
            System.out.println("colpit sx");
            hitbox.update(this.x, this.y);
            return true;
        }
        else hitbox.update(this.x, this.y);

        this.hitbox.setX(this.x - 40);
        if(this.hitbox.collision(hitbox)){
            System.out.println("colpit dx");
            hitbox.update(this.x, this.y);
            return true;
        }
        else hitbox.update(this.x, this.y);

        this.hitbox.setY(this.y + 40);
        if(this.hitbox.collision(hitbox)){
            System.out.println("colpit su");
            hitbox.update(this.x, this.y);
            return true;
        }
        else hitbox.update(this.x, this.y);

        this.hitbox.setY(this.y - 40);
        if(this.hitbox.collision(hitbox)){
            System.out.println("colpit giu");
            hitbox.update(this.x, this.y);
            return true;
        }
        else hitbox.update(this.x, this.y);

        /*if (super.collision(hitbox.setX(hitbox.getX() + 40))) {
            System.out.println("colpit dx");
            return true;
        } else if (super.collision(hitbox.setX(hitbox.getX() - 40))) {
            System.out.println("colpit sx");
            return true;

        } else if (super.collision(hitbox.setY(hitbox.getY() + 40))) {
            System.out.println("colpit su");
            return true;

        } else if (super.collision(hitbox.setY(hitbox.getY() - 40))){
            System.out.println("colpit giu");
            return true;

        }*/

        return false;


    }

    public Bomb(int x, int y) {
        super("bombs.png");
        alive = true;
        hitboxes =  new ArrayList<Hitbox>();
        hitboxes.add(new Hitbox(x+40, y));
        hitboxes.add(new Hitbox(x-40, y));
        hitboxes.add(new Hitbox(x, y+40));
        hitboxes.add(new Hitbox(x, y-40));

        super.setX(x);
        super.setY(y);
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
            this.texture = new Texture("BombermanEffect1.png");
        }
        if(count==100) {
            explosion = false;
            alive = false;
            count = 0;
        }
    }

    @Override
    public void update_hitbox() {
        hitboxes.get(0).update(x+40, y);
        hitboxes.get(1).update(x-40, y);
        hitboxes.get(2).update(x, y+40);
        hitboxes.get(3).update(x, y-40);

    }

    @Override
    public void draw(SpriteBatch batch) {
        if(!explosion)
            batch.draw(this.texture, this.x, this.y);
        else
            batch.draw(this.texture, this.x-40, this.y-40);
    }
}
