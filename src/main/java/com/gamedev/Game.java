package com.gamedev;

/*import com.sun.xml.internal.ws.assembler.jaxws.HandlerTubeFactory;*/

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    private Thread thread;

    public static final int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
    public static final int ENEMY_OBJECTS = 32;
    private boolean running  = false;
    private int offsetX = 200;
    private int offsetY = 20;
    private int enemyShooting;
    private int tickCounter = 0;
    private int fps;
    private Handler handler;
    private Player player;
    private Image image;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();


    public Game() {
        init();
    }

    public void init() {
        image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/bg.jpg"));
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        new Display("Space Odyssey", WIDTH, HEIGHT, this);
        /*Create Player*/
        player = new Player(WIDTH / 2 - Player.getPlayerWidth() / 2, HEIGHT - 100, ID.Player, handler);
        handler.addObject(player);
        handler.addObject(new HUD(20, 20, ID.HUD, handler));
        handler.addObject(new ResultScene(300, 200, ID.ResultScene, handler));

        /*TODO:ADD BOSS*/
        /*Create Enemies*/
        for (int i =  0; i < ENEMY_OBJECTS; i++) {

            if (i % (ENEMY_OBJECTS / 4) == 0) {
                offsetY += 50;
                offsetX = 200;
            }

            Enemy enemyObject = new Enemy(offsetX, offsetY, ID.Enemy, handler, false);
            enemies.add(enemyObject);
            handler.addObject(enemyObject);
            offsetX += 50;
        }
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer +=1000;
                fps = frames;

                frames = 0;
            }
        }
        stop();
    }

    /*Do Stuff*/
    private void tick() {
        tickCounter++;
        Random rnd = new Random();
        enemyShooting = rnd.nextInt(enemies.size());

        for(int i = 0; i < enemies.size(); i++){
            if(i == enemyShooting && tickCounter == 20) {
                enemies.get(i).setShooting(true);
                tickCounter = 0;
            }
        }
        handler.tick();
    }

    /*Paint Stuff*/
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, this);

        g.setColor(Color.WHITE);
        g.drawString("FPS: "+ fps, 700, 50);
        handler.render(g);

        g.dispose();
        bs.show();
    }


    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static void main(String [] args) {
        new Game();
    }
}
