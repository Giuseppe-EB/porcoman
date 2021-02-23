package com.mygdx.game.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("enemyPath")
public class EnemyPath {
    @Param(0)
    private int move;

    @Param(1)
    private int free;

    public EnemyPath(int move, int free) {
        this.move = move;
        this.free = free;
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

    public void setFree(int free) {
        this.free = free;
    }

}
