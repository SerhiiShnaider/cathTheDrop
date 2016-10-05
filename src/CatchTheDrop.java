import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by Elvis on 05.10.2016.
 */
public class CatchTheDrop extends JFrame {

    private static CatchTheDrop catchTheDrop;
    private static Image background;
    private static Image gameOver;
    private static Image drop;
    private static float dropLeft = 600;
    private static float dropTop = -100;
    private static float dropVar = 100;
    private static long lastFrameTime;
    private static int score = 0;


    public static void main(String[] args) throws IOException {
        catchTheDrop = new CatchTheDrop();
        background = ImageIO.read(catchTheDrop.getClass().getResourceAsStream("background.png"));
        drop = ImageIO.read(catchTheDrop.getClass().getResourceAsStream("drop.png"));
        gameOver = ImageIO.read(catchTheDrop.getClass().getResourceAsStream("game_over.png"));
        catchTheDrop.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        catchTheDrop.setLocation(200, 100);
        catchTheDrop.setSize(906, 478);
        catchTheDrop.setResizable(false);
        lastFrameTime = System.nanoTime();
        Canvas canvas = new Canvas();

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float dropRight = dropLeft + drop.getWidth(null);
                float dropBottom = dropTop + drop.getHeight(null);
                boolean isDrop = x >= dropLeft && x <= dropRight && y >= dropTop && y <= dropBottom;
                if (isDrop) {
                    dropTop = -100;
                    dropLeft = (int) (Math.random() * (canvas.getWidth() - drop.getWidth(null)));
                    dropVar += 20;
                    score++;
                    catchTheDrop.setTitle("Score : " + score);
                }
            }
        });

        catchTheDrop.add(canvas);
        catchTheDrop.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;

        dropTop = dropTop + dropVar * deltaTime;
        g.drawImage(background, 0, 0, null);
        g.drawImage(drop, (int) dropLeft, (int) dropTop, null);
        if (dropTop > catchTheDrop.getHeight()) {
            g.drawImage(gameOver, 280, 120, null);
        }
    }

    private static class Canvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
