package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MouseInput {
    Vector3 vector;
    OrthographicCamera cam;
    public MouseInput(OrthographicCamera cam)
    {
        this.cam=cam;
        vector= new Vector3(0,0,0);
    }
    public void convertInput()
    {
        vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(vector);
    }
    public float x()
    {
        return vector.x;
    }
    public float y()
    {
        return vector.y;
    }
}