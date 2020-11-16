package com.gamedev;

import java.awt.*;
import java.io.InputStream;


public class Projectile extends GameObject {

    private final String source;
    private final Handler handler;
    private final Image image;

    public Projectile(int x, int y, ID id, String source, Handler handler){
        super(x, y, id);
        this.source = source;
        this.handler = handler;

        if(source.equals("Enemy"))
            image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/bluelaserEnemy.png"));
        else
            image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/bluelaserPlayer.png"));
    }

    private static final int PROJECTILE_WIDTH = 15;
    private static final int PROJECTILE_HEIGHT = 50;

    public String getSource(){
        return source;
    }
    public static int getProjectileHeight() {
        return PROJECTILE_HEIGHT;
    }
    public static int getProjectileWidth(){
        return PROJECTILE_WIDTH;
    }

    public void tick(){
        y += velY;
        if(y > Game.HEIGHT || y < 0)
            handler.object.remove(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, null);


    }
}
