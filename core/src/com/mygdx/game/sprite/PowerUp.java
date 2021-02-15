package com.mygdx.game.sprite;

import com.mygdx.game.level.Level;
import com.mygdx.game.sprite.Sprite;

public abstract class PowerUp extends Sprite {


    public PowerUp(String path) {
        super(path);
    }

    @Override
    public void update(Level level) {
        return;
    }
}
