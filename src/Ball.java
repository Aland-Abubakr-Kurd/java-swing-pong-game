// Based on a tutorial by Bro Code
// Original video: https://youtu.be/oLirZqJFKPE
// I do not claim ownership of the original code.
// You may notice some slight changes, but the core logic remains largely the same as in the original tutorial.

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

// Ball class handles the bouncing projectile.
// Also inherits from Rectangle for easy collision detection.

public class Ball extends Rectangle {

    Random random;
    double xVelocity;
    double yVelocity;
    int initialSpeed = 3; // Starting speed of the ball
        
    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
        
        // Pick a random X direction (left or right)
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0) {
            randomXDirection--;
        }
        setXDirection(randomXDirection * initialSpeed);

        // Pick a random Y direction (up or down)
        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0) {
            randomYDirection--;
        }
        setYDirection(randomYDirection * initialSpeed);
    }

    public void setXDirection(double randomXDirection) {
        xVelocity = randomXDirection;
    }
    
    public void setYDirection(double randomYDirection) {
        yVelocity = randomYDirection;
    }
    
    public void move() {
        x += xVelocity;
        y += yVelocity;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height); // Draw a circle inside the Rectangle's bounds
    }
}