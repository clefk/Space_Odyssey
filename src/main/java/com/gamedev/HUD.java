package com.gamedev;

import java.awt.*;

public class HUD extends GameObject{

    private int percentage;
    private Handler handler;
    private final int FONT_SIZE = 16;

    public HUD(int x, int y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
    }

    public void tick(){
        for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Player){
                Player player = (Player)handler.object.get(i);
                percentage = player.getPercentage();
            }
        }
    }

    public void render(Graphics g){
        g.drawRect(x, y, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(x, y, percentage*2, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));
        g.drawString("SCORE: "+Enemy.score, 20, 70);
    }
}
