package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Key extends PowerUp {


    private static Key instance = null;

    private Key(int x, int y) {
        super("key.png");
        this.x = x;
        this.y = y;
        setHitbox(new Hitbox(x, y));
    }

    public static Key getInstance(int x, int y){
        if(instance == null){
            instance = new Key(x, y);
        }
        return instance;
    }
    public void nextLivello(){
        instance=null;
    }
    @Override
    public void action() throws Exception {

    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(texture, x, y);

    }

    @Override
    public void update(int x, int y) {

    }
}
