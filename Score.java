import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

// Based on a tutorial by Bro Code
// Original video: https://youtu.be/oLirZqJFKPE
// I do not claim ownership of the original code.
// You may notice some slight changes, but the core logic remains largely the same as in the original tutorial.

// Score class displays the current points and the net line down the middle.

public class Score extends Rectangle {
        
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;

    Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE); // Make sure the score and net are visible!
        g.setFont(new Font(null, Font.ITALIC, 60));
        
        // Draw the net down the middle
        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
        
        // Draw the scores
        // Uses division and modulo to ensure it formats properly as a two-digit number (e.g., 05 instead of 5)
        g.drawString(String.valueOf(player1/10) + String.valueOf(player1%10), (GAME_WIDTH/2)-115, 60);
        g.drawString(String.valueOf(player2/10) + String.valueOf(player2%10), (GAME_WIDTH/2)+25, 60);
    }
}