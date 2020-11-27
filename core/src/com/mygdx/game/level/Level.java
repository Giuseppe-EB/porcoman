package com.mygdx.game.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;
import java.util.ArrayList;

public class Level {
    String csvFile = new String("level.csv");


    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ";";
    String current = new java.io.File( "." ).getCanonicalPath();
    private Texture texture = new Texture("wall.png");

    private int bomb_count;
    boolean isdraw = false;
    //System.out.println("Current dir:" + current);

    private ArrayList<ArrayList<Integer>> matrix ;


    public Level() throws IOException {
        bomb_count = 0;
        matrix = new ArrayList<ArrayList<Integer>>();
        try {
            System.out.println("Current dir:" + current);
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                ArrayList<Integer> row = new ArrayList<Integer>();
                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                for (String s : country){
                    row.add(Integer.parseInt(s));
                }
                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
                matrix.add(row);
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
        if(i<0||j<0||i>15||j>31) {
            System.out.println("porcodio");
            return 1;
        }
        return matrix.get(i).get(j);
    }
    public void setCell(int j, int i){
        if(i<0||j<0||i>15||j>31) {
            System.out.println("porcodio");
        }
        matrix.get(i).set(j, 1);
    }
    public void draw(SpriteBatch batch) {
            //System.out.println("porcodio1");
            for (int col = 0; col < matrix.size(); col++) {
                //System.out.println("porcodio");
                for (int row = 0; row < matrix.get(col).size(); row++) {
                    int i = matrix.get(col).get(row);
                    if (i == 1) {
                        batch.draw(texture, row * 40, col * 40);
                    }
                    else if(i == 2&&bomb_count<100) {
                        bomb_count++;
                    }
                    else if(i == 2 ){
                        bomb_count = 0;
                        explosion(col, row);
                    }
                }
            }


    }

    private void explosion(int i,int j) {
        if(i>0)
            matrix.get(i-1).set(j, 0);
        if(i<15)
            matrix.get(i+1).set(j, 0);
        if(j>0)
            matrix.get(i).set(j-1, 0);
        if(j<31)
            matrix.get(i).set(j+1, 0);
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
}

