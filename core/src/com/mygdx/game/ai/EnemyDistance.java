package com.mygdx.game.ai;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("enemy_dist")
public class EnemyDistance {
    public EnemyDistance(int move, int dist) {
        this.move = move;
        this.dist = dist;
    }

    @Param(0)
    private int move;

    @Param(1)
    private int dist;


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

}
