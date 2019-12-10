package com.gamedev;

import java.awt.*;
import javax.swing.*;
import java.awt.image.ImageObserver;
import java.awt.Component;


public class Player extends GameObject{

    private static final int PLAYER_WIDTH = 64;
    private static final int PLAYER_HEIGHT = 64;
    private int percentage = 100;
    private Image image;
    private Handler handler;
    private KeyInput in;


    public Player(int x, int y, ID id, Handler handler)
    {
        super(x, y, id);
        this.handler = handler;
        in = new KeyInput(handler);
        image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/spaceship64.png"));
    }

    public void tick(){
        x += velX;
        x = Game.clamp(x, 0, Game.WIDTH-PLAYER_WIDTH);

        if(in.isShooting()) {
            Projectile bullet = new Projectile(this.getX()+(PLAYER_WIDTH/2-Projectile.getProjectileWidth()/2), this.getY(), ID.Projectile, "Player", handler);
            handler.addObject(bullet);
            bullet.setVelY(-10);
            in.setShooting(false);
        }
    }

    public int getPercentage(){
        return percentage;
    }
    public static int getPlayerWidth()
    {
        return PLAYER_WIDTH;
    }

    public static int getPlayerHeight()
    {
        return PLAYER_HEIGHT;
    }


    public void render(Graphics g){

        g.drawImage(image, x, y, null);

        Rectangle playerBounds = new Rectangle(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);

        for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Projectile){
                Projectile bullet = (Projectile)handler.object.get(i);
                if(bullet.getSource().equals("Enemy") && playerBounds.getBounds().intersects(bullet.getX(), bullet.getY(),
                        Projectile.getProjectileHeight(), Projectile.getProjectileWidth())){
                    handler.object.remove(bullet);
                    if(percentage > 0)
                        percentage-=20;
                    else
                        percentage = 0;
                }
            }
        }

    }


}
