package com.gamedev;

import java.awt.*;

public class ResultScene extends GameObject {

    /*Default - 0
    * Victory - 1
    * Loss    - 2*/
    private int result = 0;
    private final Handler handler;
    private String finalText = " ";
    private final int FONT_SIZE = 70;

    public ResultScene(int x, int y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
    }

    public void tick(){

        for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Player){
                Player player = (Player)handler.object.get(i);
                int percentage = player.getPercentage();
                if(Enemy.score >= 50 * Game.ENEMY_OBJECTS && percentage > 0){
                    result = 1;
                    finalText = "VICTORY";
                }
                else if(percentage == 0){
                    result = 2;
                    finalText = "GAME OVER";
                }
                else  result = 0;
            }
        }

    }

    public void render(Graphics g){
        g.setColor(Color.WHITE);

        if(result > 0) {
            Font font = new Font("Arial", Font.BOLD, FONT_SIZE);
            FontMetrics metrics = g.getFontMetrics(font);
            x = Game.WIDTH/2 - metrics.stringWidth(finalText)/2;
            y = Game.HEIGHT/2 - metrics.getHeight()/2 + 50;
            g.setFont(font);
            if (result == 1) g.drawString(finalText, x, y);
            else if (result == 2) g.drawString(finalText, x, y);
            for(int i = 0; i < handler.object.size(); i++)
                if (handler.object.get(i).getId() != ID.ResultScene)
                    handler.object.remove(handler.object.get(i));
        }


    }


}
