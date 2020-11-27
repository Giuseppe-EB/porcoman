package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public abstract class Sprite {

    Texture texture;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Sprite(String path){
        this.texture = new Texture(path);

    }
    public abstract void action();
    public abstract void draw(SpriteBatch batch);

    public boolean isAlive(){
        return true;
    }
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getX() {
        int temp = x/40;
        temp*=40;
        return temp;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        int temp = y/40;
        temp*=40;
        return temp;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
