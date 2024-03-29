package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ai.*;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.round;

public class Nemico extends Sprite {

    private static Logger log= Logger.getLogger("log");

    private int goalX1;
    private int goalX2;
    private int goalY1;
    private int goalY2;

    private int direction;
    private int right =0;
    private Texture texture_2;
    private boolean go_ia=false;
    private int ia_count=0;

    private int player_x ;
    @Override
    public void update (int x, int y){
        this.player_x = x/40;
        this.player_y = y/40;
    }

    public int getPlayer_x() {
        return player_x;
    }

    public void setPlayer_x(int player_x) {
        this.player_x = player_x;
    }

    public int getPlayer_y() {
        return player_y;
    }

    public void setPlayer_y(int player_y) {
        this.player_y = player_y;
    }

    private int player_y ;

    private EnemyAI ai;

    private int P2_x ;
    private int P2_y ;

    public Nemico() throws IOException {
        super("nemico_queg.png");
        x = 320;
        y = 200;
        direction = 0;
        texture_2 = new Texture("nemico_queg2.png");
        ai= new EnemyAI();

    }

    public Nemico(int x, int y, int goalX1, int goalX2, int goalY1, int goalY2) throws IOException {
        super("nemico_queg.png");
        this.x = x;
        this.y = y;
        this.goalX1 = goalX1;
        this.goalX2 = goalX2;
        this.goalY1 = goalY1;
        this.goalY2 = goalY2;
        P2_x = goalX1;
        P2_y = goalY1;
        direction = 0;
        texture_2 = new Texture("nemico_queg2.png");
        ai= new EnemyAI();
    }

    @Override
    public void action() throws Exception {

        //check();
        //System.out.println(direction);
       /* switch (direction) {
            case (0):
                if(can_move[direction])
                    x -= 3;
                break;
            case (1):
                if(can_move[direction])
                    x+=3;
                break;
            case (2):
                if(can_move[direction])
                    y+=3;
                break;
            case(3):
                if(can_move[direction])
                    y-=3;
                break;
            default:


            }
            */
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            go_ia=!go_ia;
        }
        if(go_ia){
            ia_count++;
            if(ia_count==44)
                ia_count=0;
        }
        if(go_ia&&ia_count==10) {
            int move = 0;
            boolean trovato = false;
            ArrayList<Action> actions = new ArrayList<>();
            ArrayList<Distance> dists = new ArrayList<>();
            //  ArrayList<BombDistance> bomb_dists = new ArrayList<>();


            int P1_x = this.x / 40;
            int P1_y = this.y / 40;
            int dist = (int) round(10 * sqrt(pow((P1_x - player_x), 2) + pow((P1_y - player_y), 2)));

            //  log.info(this.toString());

            //  log.info("CURRENT POSITION: [" + P1_x + ", " + P1_y + "] ");
            //  log.info("CURRENT GOAL: [" + P2_x + ", " + P2_y + "] ");

            if (P1_x == P2_x && P1_y == P2_y) {
                //  log.info("CURRENT GOAL1: [" + goalX1 + ", " + goalY1 + "] ");
                //  log.info("CURRENT GOAL2: [" + goalX2 + ", " + goalY2 + "] ");
                if(P2_x == goalX2 && P2_y == goalY2 ){
                    P2_x = goalX1;
                    P2_y = goalY1;
                }
                else if(P2_x == goalX1 && P2_y == goalY1){
                    P2_x = goalX2;
                    P2_y = goalY2;
                }
            }


            if (can_move[0]) {
                actions.add(new Action(0));
                dists.add(new Distance(0, (int) round(10 * sqrt(pow(((P1_x - 1) - P2_x), 2) + pow((P1_y - P2_y), 2)))));

            }
            if (can_move[1]) {
                actions.add(new Action(1));
                dists.add(new Distance(1, (int) round(10 * sqrt(pow(((P1_x + 1) - P2_x), 2) + pow((P1_y - P2_y), 2)))));

            }
            if (can_move[2]) {
                actions.add(new Action(2));
                dists.add(new Distance(2, (int) round(10 * sqrt(pow((P1_x - P2_x), 2) + pow(((P1_y + 1) - P2_y), 2)))));

            }
            if (can_move[3]) {
                actions.add(new Action(3));
                dists.add(new Distance(3, (int) round(10 * sqrt(pow((P1_x - P2_x), 2) + pow(((P1_y - 1) - P2_y), 2)))));

            }
            dists.add(new Distance(4, (int) round(10 * sqrt(pow((P1_x - P2_x), 2) + pow((P1_y - P2_y), 2)))));
            actions.add(new Action(4));
            ai.load_fact(new Position(P1_x, P1_y),actions, dists);
            AnswerSets answers = ai.getAnswerSets();

            while(answers.getAnswersets().get(0).getAnswerSet().isEmpty()){
                ai.load_fact(new Position(P1_x, P1_y),actions, dists);
                answers = ai.getAnswerSets();
            }

            for (AnswerSet an : answers.getAnswersets()) {
                Pattern pattern = Pattern.compile("^choice\\((\\d+)\\)");
                Matcher matcher;
                for (String atom : an.getAnswerSet()) {
                    //System.out.println(atom);
                    matcher = pattern.matcher(atom);

                    if (!trovato&&matcher.find()) {
                    //    System.out.println(matcher.group(1));
                        move = Integer.parseInt(matcher.group(1));
                    }


                }
            }
            if (move == 1) {
                set_allCan_move(0, true);
                this.x += 40;
            }
            else if (move == 2) {
                set_allCan_move(0, true);
                this.y += 40;
            }
            else if (move == 3) {
                set_allCan_move(0, true);
                this.y -= 40;
            }
            else if (move == 0) {
                set_allCan_move(0, true);
                this.x -= 40;
            }
            else if (move == 4) {
                if(P2_x == goalX2 && P2_y == goalY2 ){
                    P2_x = goalX1;
                    P2_y = goalY1;
                }
                else if(P2_x == goalX1 && P2_y == goalY1){
                    P2_x = goalX2;
                    P2_y = goalY2;
                }
            }
            ai.clear();


        }
    }

    private void check(){
        if(!can_move[0]&&direction==0){
            this.direction = 3;

        }
        else if(!can_move[1]&&direction==1){
            direction = 2;
            //set_allCan_move(0, true);
        }
        else if (!can_move[2]&&direction==2){
            direction = 0;
            //set_allCan_move(0, true);
        }
        else if(!can_move[3]&&direction==3){
            direction = 1;
            //set_allCan_move(0, true);
        }
        set_allCan_move(0, true);



        //return direction;
    }

    @Override
    public void draw(SpriteBatch batch) {
        int x = this.x/40;
        int y = this.y/40;
        x*=40;
        y*=40;
        if(right < 25) {
            right++;
            batch.draw(this.texture, x, y);

        }
        else if(right < 50){
            right++;
            batch.draw(this.texture_2, x, y);
            if(right==50)
                right=0;

        }

    }
}
