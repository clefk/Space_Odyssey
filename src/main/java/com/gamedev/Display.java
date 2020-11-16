package com.gamedev;

import javax.swing.JFrame;
import java.awt.*;

public class Display extends Canvas{

    public Display(String title, int width, int height, Game game) {
        /*Init JFrame*/
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(game);

        game.start();
    }


}
