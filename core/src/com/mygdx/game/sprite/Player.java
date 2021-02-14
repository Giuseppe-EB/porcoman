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

    private static int GOAL = 999;
    private static int BOMB = 998;
    private static int ENEMY = 997;

    private int bombRange = 1;



    private boolean[] can_destroy;
    private int count ;
    private boolean can_hit ;
    private PlayerAI ai;
    private int playerX = 1;
    private int playerY = 10;
    private int goalX = 18;
    private int goalY = 8;
    private int enemyX= 10;
    private int enemyY=10;
    private int bombX;
    private int bombY;
    private boolean go_ia = false;
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
        return enemyX;
    }

    public void setNemico_x(int enemyX) {
        this.enemyX = enemyX;
    }

    public int getNemico_y() {
        return enemyY;
    }

    public void setNemico_y(int enemyY) {
        this.enemyY = enemyY;
    }

    public void setCan_hit(boolean can_hit) {
        this.can_hit = can_hit;
    }

    public Player() throws IOException {
        super("stupid.png");
        this.x = 50;
        this.y = 140;
        this.enemyX = x/40;
        this.enemyY = y/40;
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
        this.enemyX = x/40;
        this.enemyY = y/40;
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
            int mode = 0;

            if (!can_hit)
                mode = 1;

            boolean trovato= false;

            ArrayList<Action> actions = new ArrayList<>();
            ArrayList<Wall> walls = new ArrayList<>();

            playerX = this.x/40;
            playerY = this.y/40;

            for( int i = 0; i < 4; i++ ){
                if (can_move[i]) {
                    actions.add(new Action(i));
                    walls.add(new Wall(i, 0));
                }
                else if (can_destroy[i]){
                    walls.add(new Wall(i, 1));
                    actions.add(new Action(i));
                }
            }
            actions.add(new Action(4));

            ai.load_fact(   new Position(playerX, playerY),
                            actions,
                            makeDistances(buildDistances(goalX, goalY)),
                            makeBombDistances(buildDistances(bombX, bombY)),
                            makeEnemyDistances(buildDistances(enemyX, enemyY)),
                            walls,
                            new Mode(mode)
            );

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
                this.bombX=playerX;
                this.bombY=playerY;
            }
            enemyY = 0;
            enemyX = 0;
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

    private ArrayList<Double> buildDistances(final int goalX, final int goalY){


        ArrayList<Double> distances = new ArrayList<>();

        if(this.can_move[0]||this.can_destroy[0]) {
            distances.add(sqrt(pow(((playerX-1) - goalX), 2) +
                                pow((playerY - goalY), 2)));
        }
        else{
            distances.add(999D);
        }

        if(can_move[1]||can_destroy[1]) {
            distances.add(sqrt(pow(((playerX+1) - goalX), 2) +
                                pow((playerY - goalY), 2)));
        }
        else{
            distances.add(999D);
        }

        if(can_move[2]||can_destroy[2]) {
            distances.add(sqrt(pow((playerX - goalX), 2) +
                                pow(((playerY+1) - goalY), 2)));
        }
        else{
            distances.add(999D);
        }

        if(can_move[3]||can_destroy[3]) {
            distances.add(sqrt(pow((playerX - goalX), 2) +
                                pow(((playerY-1) - goalY), 2)));
        }
        else{
            distances.add(999D);
        }

        distances.add(sqrt(pow((playerX - goalX), 2)+pow((playerY - goalY), 2)));

        return distances;
    }


    private static ArrayList<Distance> makeDistances(final ArrayList<Double> distances) {

        ArrayList<Integer> orderedDistances = makeOrder(distances);

        ArrayList<Distance> result = new ArrayList<>();
        for (int i = 0; i < orderedDistances.size(); i++) {
            result.add(new Distance(i, orderedDistances.get(i)));
        }

        return result;
    }
    private static ArrayList<EnemyDistance> makeEnemyDistances(final ArrayList<Double> distances) {

        ArrayList<Integer> orderedDistances = makeOrder(distances);

        ArrayList<EnemyDistance> result = new ArrayList<>();
        for (int i = 0; i < orderedDistances.size(); i++) {
            result.add(new EnemyDistance(i, orderedDistances.get(i)));
        }

        return result;
    }
    private ArrayList<BombDistance> makeBombDistances(final ArrayList<Double> distances) {

        ArrayList<Integer> orderedDistances = makeOrder(distances);

        ArrayList<BombDistance> result = new ArrayList<>();
        for (int i = 0; i < orderedDistances.size(); i++) {
            if(!isSafe() || i == 4)
                result.add(new BombDistance(i, orderedDistances.get(i)));
            else
                result.add(new BombDistance(i, 0));
        }

        return result;
    }

    private boolean isSafe(){
        return sqrt(pow((playerX - bombX), 2)+pow((playerY - bombY), 2)) > bombRange;
    }

    private static ArrayList<Integer> makeOrder(final ArrayList<Double> distances) {
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=0; i<distances.size(); i++){
            result.add(getPosition(distances, i));
        }
        return result;
    }

    private static Integer getPosition(ArrayList<Double> distances, int index) {

        int position = 0;

        for (int i = 0; i<distances.size(); i++ ){
            if(distances.get(i) < distances.get(index))
                position++;
        }
        return position;
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
