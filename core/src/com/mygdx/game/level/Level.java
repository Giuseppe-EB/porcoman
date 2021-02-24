package com.mygdx.game.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprite.IncreaseBombRange;
import com.mygdx.game.sprite.Key;
import com.mygdx.game.sprite.PowerUp;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class Level {

    private boolean doorLocked = true;

    public boolean isDoorLocked() {
        return doorLocked;
    }

    public void setDoorLocked(boolean doorLocked) {
        this.doorLocked = doorLocked;
        if(!doorLocked)
            this.porta = new Texture("aperta.png");
    }

    private static Random random = new Random();

    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    private int bombRange = 1;

    private static Logger log= Logger.getLogger("log");

    String csvFile = new String("level.csv");

    public String getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(String csvFile) {
        switch_level = true;
        powerUps.clear();
        bombRange = 1;
        this.csvFile = csvFile;
    }

    private boolean switch_level = true;
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ";";
    private Texture texture = new Texture("wall.png");
    private Texture porta= new Texture("chiusa.png");
    private Texture ind_wall = new Texture("wall_2.png");

    public void setPorta(String s) {
        this.porta = new Texture(s);
    }

    private static Level level;
    private int bomb_count;
    boolean isdraw = false;
    //System.out.println("Current dir:" + current);

    private ArrayList<ArrayList<Integer>> matrix ;


    private Level() {
        bomb_count = 0;
        matrix = new ArrayList<ArrayList<Integer>>();
    }
    public static Level getInstance(){
        return new Level();
    }
    private void loadLevel(){
        try {
            System.out.println(csvFile);
            matrix.clear();
            //matrix = new ArrayList<ArrayList<Integer>>();
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                ArrayList<Integer> row = new ArrayList<Integer>();
                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                for (String s : country){
                    row.add(Integer.parseInt(s));
                }
                //System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
                matrix.add(row);
            }
            for(int i=0, j=matrix.size()-1 ; i<j; i++)
            {

                matrix.add(i, matrix.remove(j));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int getCell(int j, int i){
        if(i<0||j<0||i>matrix.size()-1||j>matrix.get(i).size()-1) {
            //System.out.println("porcodio");
            return 11;
        }
        return matrix.get(i).get(j);
    }
    public void setCell(int j, int i){
        /*if(i<0||j<0||i>15||j>31) {
            System.out.println("porcodio");
        }*/
        matrix.get(i).set(j, 1);
    }
    public void draw(SpriteBatch batch) {
            //System.out.println("porcodio1");
            if(switch_level) {
                System.out.println("switch level....");
                this.loadLevel();
                switch_level = false;
            }
            for (int col = 0; col < matrix.size(); col++) {
                //System.out.println("porcodio");
                for (int row = 0; row < matrix.get(col).size(); row++) {
                    int i = matrix.get(col).get(row);
                    if (i == 1) {
                        batch.draw(texture, row * 40, col * 40);
                    }
                    else if( i == 2 && bomb_count<100) {
                        bomb_count++;
                    }
                    else if( i == 2 ){
                        bomb_count = 0;
                        explosion(col, row, bombRange);
                    }
                    else if(i == 10)
                    {
                        batch.draw(porta,row*40,col*40);
                        //System.out.println(row + "   " + col);
                    }
                    else if(i == 11){
                        batch.draw(ind_wall,row*40, col*40);
                    }
                }
            }


    }

    private void addPowerUp(int x, int y){

        int type = random.nextInt() % 2;
        boolean drop = random.nextBoolean();

        /*
        LEGEND
                0 ===> IncreaseBomb
                1 ===> Key
        */

        if(drop){
            switch (type){
                case 0:
                    powerUps.add(new IncreaseBombRange(x*40, y*40));
                    break;
                case 1:
                    if(doorLocked)
                        powerUps.add(Key.getInstance(x*40, y*40));
                    break;
                default:
                    return;
            }
        }

    }

    private void analyzeExplosionX(int i, int j, int bombRange){

        if(this.getCell(j, i - 1) != 11 ) {
            for (int col = i; col >= i - bombRange; col--) {

                int currentPos = this.getCell(j, col);

                if (currentPos != 11 && currentPos != 0 && currentPos != 2 && currentPos != 10) {
                    matrix.get(col).set(j, 0);
                    addPowerUp(j, col);
                    break;
                }
            }
        }
        if(this.getCell(j, i + 1 ) != 11 ) {
            for (int col = i; col <= i + bombRange; col++) {

                int currentPos = this.getCell(j, col);

                if (currentPos != 11 && currentPos != 0 && currentPos != 2 && currentPos != 10) {
                    matrix.get(col).set(j, 0);
                    addPowerUp(j, col);
                    break;
                }
            }
        }
    }

    private void analyzeExplosionY(int i, int j, int bombRange){

        if(this.getCell(j - 1, i) != 11) {
            for (int row = j; row >= j - bombRange; row--) {

                int currentPos = this.getCell(row, i);

                if (currentPos != 11 && currentPos != 0 && currentPos != 2 && currentPos != 10) {
                    matrix.get(i).set(row, 0);
                    addPowerUp(row, i);
                    break;
                }
            }
        }
        if(this.getCell(j + 1, i ) != 11) {
            for (int row = j; row <= j + bombRange; row++) {

                int currentPos = this.getCell(row, i);

                if (currentPos != 11 && currentPos != 0 && currentPos != 2 && currentPos != 10) {
                    matrix.get(i).set(row, 0);
                    addPowerUp(row, i);
                    break;
                }
            }
        }
    }

    private void explosion(int i, int j, int bombRange) {

        log.warning("explosion on pos: [ " + j + ", " + i + " ]" );

        analyzeExplosionX(i, j, bombRange);
        analyzeExplosionY(i, j, bombRange);

        matrix.get(i).set(j, 0);

    }

    /*
            2 =======> BOMB
     */
    public void add_bomb(int j, int i) {

        matrix.get(i).set(j, 2);

    }

    public void save() throws IOException {

        FileWriter file = new FileWriter("level_building.csv");
        for (int col = 0; col < matrix.size(); col++) {
            //System.out.println("porcodio");
            for (int row = 0; row < matrix.get(col).size(); row++) {
                int i = matrix.get(col).get(row);
                    file.append(i + ";");
            }
            file.append("\n");
        }
        file.close();
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }
}

