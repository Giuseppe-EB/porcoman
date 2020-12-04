package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ai.Action;
import com.mygdx.game.ai.Distance;
import com.mygdx.game.ai.PlayerAI;
import com.mygdx.game.ai.Position;
import com.mygdx.game.level.Level;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.round;

public class Player extends Sprite {

    private int count ;
    private boolean can_hit ;
    private PlayerAI ai;
    private int P2_x = 18;
    private int P2_y = 8;
    //private boolean[] can_move ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCan_hit() {
        return can_hit;
    }

    public void setCan_hit(boolean can_hit) {
        this.can_hit = can_hit;
    }

    public Player() throws IOException {
        super("stupid.png");
        this.x = 1;
        this.y = 580;
        can_hit = true;
        count=0;
        System.out.println("porcodio");
        ai = new PlayerAI();
    }
    @Override
    public void update(Level level) {
        super.update(level);
        if(level.getCell((getX()/40), (getY()/40) ) == 10){

            System.out.println("prossimo livello");
            level.setCsvFile("level2.csv");
        }
    }

    /*

            LEGEND:
                    0 LEFT
                    1 RIGHT
                    2 UP
                    3 DOWN

     */
    @Override
    public void action() throws Exception {
        if (!can_hit)
            count++;
        if (count == 80) {
            can_hit = true;
            count = 0;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            int move = 0;
            boolean trovato= false;
            ArrayList<Action> actions = new ArrayList<>();
            ArrayList<Distance> dists = new ArrayList<>();


//          distanza tra due punti : d( P1, P2) = sqrt((x2 - x1)^2+(y2-y1)^2)

            int P1_x = this.x/40;
            int P1_y = this.y/40;


            if(can_move[0]) {
                actions.add(new Action(0));
                dists.add(new Distance(0, (int) round(sqrt(pow(((P1_x-1) - P2_x), 2)+pow((P1_y - P2_y), 2)))));
            }
            if(can_move[1]) {
                actions.add(new Action(1));
                dists.add(new Distance(1, (int) round(sqrt(pow(((P1_x+1) - P2_x), 2)+pow((P1_y - P2_y), 2)))));
            }
            if(can_move[2]) {
                actions.add(new Action(2));
                dists.add(new Distance(2, (int) round(sqrt(pow((P1_x - P2_x), 2)+pow(((P1_y+1) - P2_y), 2)))));
            }
            if(can_move[3]) {
                actions.add(new Action(3));
                dists.add(new Distance(3, (int) round(sqrt(pow((P1_x - P2_x), 2)+pow(((P1_y-1) - P2_y), 2)))));
            }

            ai.load_fact(new Position(P1_x, P1_y),actions, dists);
            AnswerSets answers = ai.getAnswerSets();
            for (AnswerSet an : answers.getAnswersets()) {
                Pattern pattern = Pattern.compile("^choice\\((\\d+)\\)");
                Matcher matcher;
                for (String atom : an.getAnswerSet()) {
                    //System.out.println(atom);
                    matcher = pattern.matcher(atom);

                    if (!trovato&&matcher.find()) {
                        System.out.println(matcher.group(1));
                        move = Integer.parseInt(matcher.group(1));
                    }


                }
            }
            if (move == 1) {
                set_allCan_move(0, true);
                this.x += 40;
            }
            if (move == 2) {
                set_allCan_move(0, true);
                this.y += 40;
            }
            if (move == 3) {
                set_allCan_move(0, true);
                this.y -= 40;
            }
            if (move == 0) {
                set_allCan_move(0, true);
                this.x -= 40;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)&&can_move[0]) {
            set_allCan_move(0, true);
            this.x -= 5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)&&can_move[1]) {
            set_allCan_move(0, true);
            this.x += 5;
        }
        if (Gdx.input.isKeyPressed((Input.Keys.W))&&can_move[2]) {
            this.y += 5;
            set_allCan_move(0, true);
        }
        if (Gdx.input.isKeyPressed((Input.Keys.S))&&can_move[3]){
            set_allCan_move(0, true);
            this.y -= 5;
        }
        if (Gdx.input.isKeyPressed((Input.Keys.Z)) && can_hit) {
            can_hit = false;
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
            int x = this.x/40;
            int y = this.y/40;
            x*=40;
            y*=40;
            batch.draw(this.texture, x, y);
    }
}
