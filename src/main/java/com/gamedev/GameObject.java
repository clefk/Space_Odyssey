package com.gamedev;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

public abstract class GameObject {
    protected int x, y;
    protected ID id;
    protected int velX, velY;

    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics g);


    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getY(){
        return y;
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public int getVelX(){
        return velX;
    }
    public void setVelY(int velY){
        this.velY = velY;
    }
    public int getVelY(){
        return velY;
    }
    public void setId(ID id) {
        this.id = id;
    }
    public ID getId() {
        return id;
    }

}
