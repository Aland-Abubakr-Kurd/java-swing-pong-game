import java.awt.*;
import java.awt.event.*;

// Based on a tutorial by Bro Code
// Original video: https://youtu.be/oLirZqJFKPE
// I do not claim ownership of the original code.
// You may notice some slight changes, but the core logic remains largely the same as in the original tutorial.

// Paddle class handles the players' bats.
// Inheriting from Rectangle gives it built-in x, y, width, height, and collision detection!

public class Paddle extends Rectangle {
        
    int id; // Identifies if this is player 1 or player 2
    int yVelocity; // Speed on the Y-axis
    int speed = 10;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;
    }

    // Moves paddle when a key is pressed down
    public void keyPressed(KeyEvent e) {
        switch(id) {
            case 1: // Player 1 uses W and S
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(+speed);
                    move();
                }
                break;
            case 2: // Player 2 uses Up and Down arrows
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(+speed);
                    move();
                }
                break;
        }
    }
    
    // Stops paddle from sliding when the key is released
    public void keyReleased(KeyEvent e) {
        switch(id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                    move();
                }
                break;
        }
    }
    
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }
    
    public void move() {
        y = y + yVelocity;
    }
    
    public void draw(Graphics g) {
        if (id == 1) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(x, y, width, height); // Draws the rectangle using inherited fields
    }
}