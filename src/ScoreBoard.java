import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;

class ScoreBoard implements Serializable {
    Timer timer;
    int score = 0;
    int lives = Arkanoid.PLAYER_LIVES;
    boolean win = false;
    boolean gameOver = false;
    String text = "";

    Font font;

    ScoreBoard() {

        font = new Font(Arkanoid.FONT, Font.PLAIN, 12);
        text = "Welcome to Arkanoid Java version";
    }

    void increaseScore() {
        score++;
        if (score == (1000)) {
            win = true;
            text = "You have won! \nYour score was: " + score
                    + "\n\nPress Enter to restart";
        } else {
            updateScoreboard();
        }
    }

    void die() {
        lives--;
        if (lives == 0) {
            gameOver = true;
            text = "You have lost! \nYour score was: " + score
                    + "\n\nPress Enter to restart";
        } else {
            updateScoreboard();
        }
    }

    void updateScoreboard() {
        text = " Player: "+Arkanoid.PlayerName+"  GameName: "+Arkanoid.GameName+ "  Score: " + score + "  Lives: " + lives;
    }

    void draw(Graphics g) {
        if (win || gameOver) {
            font = font.deriveFont(50f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.BLACK);
            g.setFont(font);
            int titleHeight = fontMetrics.getHeight();
            int lineNumber = 1;
            for (String line : text.split("\n")) {
                int titleLen = fontMetrics.stringWidth(line);
                g.drawString(line, (Arkanoid.SCREEN_WIDTH / 2) - (titleLen / 2),
                        (Arkanoid.SCREEN_HEIGHT / 4) + (titleHeight * lineNumber));
                lineNumber++;

            }
        } else {
            font = font.deriveFont(20f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.BLACK);
            g.setFont(font);
            int titleLen = fontMetrics.stringWidth(text);
            int titleHeight = fontMetrics.getHeight();
            g.drawString(text, (Arkanoid.SCREEN_WIDTH / 2) - (titleLen / 2),
                    titleHeight + 32);

        }
    }


}
