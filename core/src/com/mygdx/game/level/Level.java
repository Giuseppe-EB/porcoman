package com.mygdx.game.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {
    String csvFile = new String("level.csv");


    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ";";
    String current = new java.io.File( "." ).getCanonicalPath();
    private Texture texture = new Texture("wall.png");

    boolean isdraw = false;
    //System.out.println("Current dir:" + current);

    private ArrayList<ArrayList<Integer>> matrix ;


    public Level() throws IOException {
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

    public void draw(SpriteBatch batch) {
            //System.out.println("porcodio1");
            for (int col = 0; col < matrix.size(); col++) {
                //System.out.println("porcodio");
                for (int row = 0; row < matrix.get(col).size(); row++) {
                    int i = matrix.get(col).get(row);
                    if (i == 1) {
                        batch.draw(texture, row * 40, col * 40);
                    }
                }
            }


    }
}

