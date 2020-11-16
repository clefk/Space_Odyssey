package com.gamedev;

import java.awt.*;

public class Enemy extends GameObject {

    private final static int ENEMY_WIDTH = 32;
    private final static int ENEMY_HEIGHT = 32;
    public static int score = 0;
    private boolean shooting;
    private final Handler handler;
    private final Image image;
    private int offsetX;
    private int offsetY;



    public Enemy(int x, int y, ID id, Handler handler, boolean shooting)
    {
        super(x, y, id);
        this.shooting = shooting;
        this.handler = handler;
        image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/alien25.png"));
        init();
    }

    public void init(){

        offsetX = getX();
        offsetY = getY();
        setVelX(2);
    }

    public void tick(){
        x+=velX;

        if(x < offsetX-100 || x > offsetX+100) velX *= -1;

        if(shooting){
            Projectile bullet = new Projectile(getX(), getY(), ID.Projectile, "Enemy", handler);
            handler.addObject(bullet);
            bullet.setVelY(5);
            shooting = false;
        }
    }

    public void setShooting(boolean shooting) { this.shooting = shooting; }

    public void render(Graphics g){
        g.drawImage(image, x, y, null);

        Rectangle enemyBounds = new Rectangle(x, y, ENEMY_WIDTH,  ENEMY_HEIGHT);

        /*Collision Detection*/
        for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Projectile){
                Projectile bullet = (Projectile)handler.object.get(i);
                if(bullet.getSource().equals("Player") && enemyBounds.getBounds().intersects(bullet.getX(), bullet.getY(),
                        Projectile.getProjectileHeight(), Projectile.getProjectileWidth())){
                    handler.object.remove(bullet);
                    handler.object.remove(this);
                    score+=50;
                }
            }
        }
    }
}
