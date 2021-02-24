package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coin extends PowerUp{

    public Coin(int x, int y) {
        super("coin.png");
        this.x = x;
        this.y = y;

        this.setHitbox(new Hitbox(x, y));
    }

    @Override
    public void action() throws Exception {
        return;

    }

    @Override
    public void draw(SpriteBatch batch) {batch.draw(this.texture, this.x, this.y);

    }

    @Override
    public void update(int x, int y) {
        this.x = x;
        this.y = y;

    }
}
