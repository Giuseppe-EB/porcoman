package com.mygdx.game.ai;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("bomb_dist")
public class BombDistance {

    @Param(0)
    private int move;

    public BombDistance(int move, int dist) {
        this.move = move;
        this.dist = dist;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    @Param(1)
    private int dist;

}
