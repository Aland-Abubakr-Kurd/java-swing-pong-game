import java.awt.Color;
import javax.swing.JFrame;

// Based on a tutorial by Bro Code
// Original video: https://youtu.be/oLirZqJFKPE
// I do not claim ownership of the original code.
// You may notice some slight changes, but the core logic remains largely the same as in the original tutorial.

// GameFrame sets up the main window for the application.

public class GameFrame extends JFrame {

    GamePanel gamePanel;
    
    GameFrame() {
        gamePanel = new GamePanel();
        this.add(gamePanel);
        
        // Window settings
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // pack() resizes the frame so it perfectly fits the GamePanel size
        this.pack(); 
        
        // Must be called AFTER pack() so the window centers correctly on screen
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}