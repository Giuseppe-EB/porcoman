package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ai.*;
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

    private boolean[] can_destroy;
    private int count ;
    private boolean can_hit ;
    private PlayerAI ai;
    private int P2_x = 18;
    private int P2_y = 8;
    private int P3_x= 10;
    private int P3_y=10;
    private int nemico_x=0;
    private int nemico_y=0;
    private int Pb_x;
    private int Pb_y;
    private boolean go_ia =false;
    private int ia_count = 0;


    public boolean isAi_hit() {
        boolean temp = ai_hit;
        ai_hit = false;
        return temp;
    }

    public void setAi_hit(boolean ai_hit) {
        this.ai_hit = ai_hit;
    }

    private boolean ai_hit = false;
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

    public int getNemico_x() {
        return nemico_x;
    }

    public void setNemico_x(int nemico_x) {
        this.nemico_x = nemico_x;
    }

    public int getNemico_y() {
        return nemico_y;
    }

    public void setNemico_y(int nemico_y) {
        this.nemico_y = nemico_y;
    }

    public void setCan_hit(boolean can_hit) {
        this.can_hit = can_hit;
    }

    public Player() throws IOException {
        super("stupid.png");
        this.x = 1;
        this.y = 580;
        this.nemico_x = x/40;
        this.nemico_y = y/40;
        can_hit = true;
        count=0;
        System.out.println("porcodio");
        ai = new PlayerAI();
        can_destroy = new boolean[]{false, false, false, false};
    }
    @Override
    public void update(Level level) {
        super.update(level);
        if(level.getCell((getX()/40), (getY()/40) ) == 10){

            System.out.println("prossimo livello");
            level.setCsvFile("level2.csv");
        }
        if(level.getCell((getX()/40)+1, getY()/40) == 1){

            //System.out.println(this.getClass() + "bloccato da destra");
            //System.out.println(this.getClass());
            can_destroy[1] = true;
        }
        else
            can_destroy[1] = false;
        if(level.getCell((getX()/40)-1, getY()/40) == 1){
            //System.out.println(this.getClass() + "bloccato da sinistra");
            can_destroy[0] = true;
        }
        else
            can_destroy[0] = false;
        if(level.getCell((getX()/40), (getY()/40) + 1) == 1){
            can_destroy[2] = true;
        }
        else
            can_destroy[2] = false;
        if(level.getCell((getX()/40), (getY()/40) - 1 ) == 1){
            can_destroy[3] = true;
        }
        else
            can_destroy[3] = false;
    }

    @Override
    public void update(int x, int y) {
        this.nemico_x = x/40;
        this.nemico_y = y/40;
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
        if (count == 100) {
            can_hit = true;
            count = 0;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            go_ia=!go_ia;
        }
        if(go_ia){
            ia_count++;
            if(ia_count==11)
                ia_count=0;
        }
        if(go_ia&&ia_count==10) {
            int move = 0;
            boolean trovato= false;
            ArrayList<Action> actions = new ArrayList<>();
            ArrayList<Distance> dists = new ArrayList<>();
            ArrayList<BombDistance> bomb_dists = new ArrayList<>();
            ArrayList<EnemyDistance> enemy_dists= new ArrayList<>();

            ArrayList<Wall> walls = new ArrayList<>();

            for(int i=0; i< can_destroy.length ; i++)
                if(can_destroy[i])
                    walls.add(new Wall(i, 1));
                else
                    walls.add(new Wall(i, 0));

//          distanza tra due punti : d( P1, P2) = sqrt((x2 - x1)^2+(y2-y1)^2)

            int P1_x = this.x/40;
            int P1_y = this.y/40;

            int dist = 101;

            if(nemico_x!=0 && nemico_y !=0)
                dist = (int) round(10 * sqrt(pow((P1_x - nemico_x), 2) + pow((P1_y - nemico_y), 2)));
            if(dist<100){
                P3_x = nemico_x;
                P3_y = nemico_y;
            }
            if(can_move[0]||can_destroy[0]) {
                actions.add(new Action(0));
                dists.add(new Distance(0, (int) round(10*sqrt(pow(((P1_x-1) - P2_x), 2)+pow((P1_y - P2_y), 2)))));
                if(dist<100)
                    enemy_dists.add(new EnemyDistance(0, (int) round(10*sqrt(pow(((P1_x-1) - P3_x), 2)+pow((P1_y - P3_y), 2)))));
                if(!can_hit){
                    bomb_dists.add(new BombDistance(0, (int) round(10*sqrt(pow(((P1_x-1) - Pb_x), 2)+pow((P1_y - Pb_y), 2)))));

                }
            }
            if(can_move[1]||can_destroy[1]) {
                actions.add(new Action(1));
                dists.add(new Distance(1, (int) round(10*sqrt(pow(((P1_x+1) - P2_x), 2)+pow((P1_y - P2_y), 2)))));
                if(dist<100)
                    enemy_dists.add(new EnemyDistance(1, (int) round(10*sqrt(pow(((P1_x+1) - P3_x), 2)+pow((P1_y - P3_y), 2)))));

                if(!can_hit) {
                    bomb_dists.add(new BombDistance(1, (int) round(10 * sqrt(pow(((P1_x + 1) - Pb_x), 2) + pow((P1_y - Pb_y), 2)))));
                }
            }
            if(can_move[2]||can_destroy[2]) {
                actions.add(new Action(2));
                dists.add(new Distance(2, (int) round(10*sqrt(pow((P1_x - P2_x), 2)+pow(((P1_y+1) - P2_y), 2)))));
                if(dist<100)
                    enemy_dists.add(new EnemyDistance(2, (int) round(10*sqrt(pow((P1_x - P3_x), 2)+pow(((P1_y+1) - P3_y), 2)))));

                if(!can_hit) {
                    bomb_dists.add(new BombDistance(2, (int) round(10*sqrt(pow((P1_x - Pb_x), 2)+pow(((P1_y+1) - Pb_y), 2)))));

                }
            }
            if(can_move[3]||can_destroy[3]) {
                actions.add(new Action(3));
                dists.add(new Distance(3, (int) round(10*sqrt(pow((P1_x - P2_x), 2)+pow(((P1_y-1) - P2_y), 2)))));
                if(dist<100)
                    enemy_dists.add(new EnemyDistance(3, (int) round(10*sqrt(pow((P1_x - P3_x), 2)+pow(((P1_y-1) - P3_y), 2)))));
                if(!can_hit){
                    bomb_dists.add(new BombDistance(3, (int) round(10*sqrt(pow((P1_x - Pb_x), 2)+pow(((P1_y-1) - Pb_y), 2)))));

                }
            }
            if(can_hit){
                actions.add(new Action(4));

            }
            else {
                actions.add(new Action(4));
                bomb_dists.add(new BombDistance(4, (int) round(10*sqrt(pow((P1_x - Pb_x), 2)+pow((P1_y - Pb_y), 2)))));

            }
            if(dist<100)
                enemy_dists.add(new EnemyDistance(4, (int) round(10*sqrt(pow((P1_x - P3_x), 2)+pow((P1_y - P3_y), 2)))));
            dists.add(new Distance(4, (int) round(10*sqrt(pow((P1_x - P2_x), 2)+pow((P1_y - P2_y), 2)))));

            ai.load_fact(new Position(P1_x, P1_y),actions, dists, bomb_dists, enemy_dists, walls);
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
            if (move == 4 && can_hit) {
                ai_hit = true;
                can_hit = false;
                this.Pb_x=P1_x;
                this.Pb_y=P1_y;
            }
            nemico_y = 0;
            nemico_x = 0;
            ai.clear();
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
