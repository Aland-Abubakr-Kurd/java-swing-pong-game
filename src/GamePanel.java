// Based on a tutorial by Bro Code
// Original video: https://youtu.be/oLirZqJFKPE
// I do not claim ownership of the original code.
// You may notice some slight changes, but the core logic remains largely the same as in the original tutorial.

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;

// GamePanel is the core engine of the game.
// It implements Runnable to allow the game loop to run on a separate Thread.

public class GamePanel extends JPanel implements Runnable {

    // --- Game Constants ---
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555)); 
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    
    // --- Game Variables ---
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1; // Left paddle
    Paddle paddle2; // Right paddle
    Ball ball;
    Score score;

    GamePanel() {
        newPaddle();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        
        // Setup panel properties
        this.setFocusable(true); // Allows the panel to receive keyboard inputs
        this.addKeyListener(new AL()); // Listens for key presses
        this.setPreferredSize(SCREEN_SIZE);

        // Start the game loop thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Spawns a new ball in the center of the screen with a random Y position
    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(GAME_HEIGHT-BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }
    
    // Spawns both paddles at their starting positions on the left and right edges
    public void newPaddle() {
        paddle1 = new Paddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle((GAME_WIDTH-PADDLE_WIDTH), (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    
    // --- DOUBLE BUFFERING ---
    // Instead of drawing directly to the screen (which causes flickering), 
    // we draw everything to an invisible image first, then draw that image to the screen all at once.
    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics); // Draw game objects to the invisible image
        g.drawImage(image, 0, 0, this); // Display the final image
    }
    
    // Calls the individual draw methods of all game objects
    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    
    // Updates the coordinates of all moving objects
    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    
    // Handles all physics and boundary logic
    public void checkCollision() {

        // 1. Prevent paddles from moving off the screen
        if (paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if (paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT)) {
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        if (paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if (paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT)) {
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }

        // 2. Bounce ball off top and bottom window edges
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= (GAME_HEIGHT-BALL_DIAMETER)) {
            ball.setYDirection(-ball.yVelocity);
        }

        // 3. Bounce ball off paddles
        // Because Ball and Paddle inherit from Rectangle, we can just use .intersects()!
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // Increase ball speed slightly after hitting a paddle for difficulty
            
            // Maintain the upward/downward angle, just faster
            if (ball.yVelocity > 0) {
                ball.yVelocity = ball.yVelocity + 0.75;
            } else {
                ball.yVelocity = ball.yVelocity - 0.75;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        
        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // Increase speed
            
            if (ball.yVelocity > 0) {
                ball.yVelocity = ball.yVelocity + 0.75;
            } else {
                ball.yVelocity = ball.yVelocity - 0.75;
            }
            ball.setXDirection(-ball.xVelocity); // Send it left
            ball.setYDirection(ball.yVelocity);
        }

        // 4. Score points and reset if ball goes past left/right edges
        if (ball.x <= 0) {
            score.player2++; // Ball passed paddle 1
            newPaddle();
            newBall();
        }
        if (ball.x >= (GAME_WIDTH-BALL_DIAMETER)) {
            score.player1++; // Ball passed paddle 2
            newPaddle();
            newBall();
        }
    }
    
    // --- THE GAME LOOP ---
    // This forces the game to run at exactly 60 updates per second (FPS), 
    // regardless of how fast the computer's processor is.
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000.0 / amountOfTicks; // Nanoseconds per frame
        double delta = 0.0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            // If enough time has passed for 1 frame, update the game
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    
    // Inner class to handle keyboard input
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}