package com.mygdx.game.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("wall")
public class Wall {

    @Param(0)
    private int move;
    @Param(1)
    private int destr;

    public Wall(int move, int destr) {
        this.move = move;
        this.destr = destr;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getDestr() {
        return destr;
    }

    public void setDestr(int destr) {
        this.destr = destr;
    }
}
