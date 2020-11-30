package com.mygdx.game.sprite;

public class Hitbox {

    private int x;
    private  int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        //Hitbox temp = this;
        this.x = x;
        //return temp;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        //Hitbox temp = this;
        this.y = y;
    }

    public Hitbox(int x, int y){
        this.x=x;
        this.y=y;
    }
    public void update(int x, int y){
        this.x=x;
        this.y=y;
    }
    public boolean collision(Hitbox hitbox){
        int x = this.x/40;
        int y = this.y/40;
        int x2 = hitbox.x/40;
        int y2 = hitbox.y/40;
        if(x==x2 && y==y2) {
            System.out.println("colpito");
            return true;
        }
        return false;
    }
}
