package com.mygdx.game.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("wall")
public class Around{

    @Param(0)
    private int move;
    @Param(1)
    private int free;

    public Around(int move, int f) {
        this.move = move;
        this.free = f;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int destr) {
        this.free = destr;
    }
}
