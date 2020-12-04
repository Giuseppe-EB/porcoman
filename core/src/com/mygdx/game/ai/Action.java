package com.mygdx.game.ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;


@Id("move")
public class Action {
    @Param(0)
    private int move;

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public Action(int move){
        this.move = move;

    }
    /*

            LEGEND:
                    0 LEFT
                    1 RIGHT
                    2 UP
                    3 DOWN

     */
}
