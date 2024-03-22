import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayGround extends JPanel implements KeyListener {
    private Image backgroundImage;
    private JLabel textLabel;
    public int scoreL = 0;
    public int scoreR = 0;
    Ball[] balls = new Ball[2];
    Bat[] bats = new Bat[1];
    Block[] blocks = new Block[3]; // Create an array of blocks

    private boolean[] keysPressed = new boolean[256];
    int ballvelocityY = 6;
    int ballvelocityX = 0;
    int batSpeed = 20;
    boolean ballTeleported = false;
    private boolean[][] bricks = new boolean[4][7];


    public PlayGround() {
        backgroundImage = new ImageIcon("C:/Users/Kisters123/IdeaProjects/JavaProject/src/99.jpg").getImage();
        balls[0] = new Ball(1650, 10, ballvelocityX, ballvelocityY, Color.BLACK);
        bats[0] = new Bat(800, 940, Color.BLACK);
        blocks[0] = new Block(100, 100, 0, 0, Color.RED);
        blocks[1] = new Block(400, 200, 0, 0, Color.GREEN);
        blocks[2] = new Block(700, 300, 0, 0, Color.BLUE);
        for (int y = 0; y < bricks.length; y++) {
            for (int x = 0; x < bricks[y].length; x++) {
                bricks[y][x] = true;
            }
        }

        textLabel = new JLabel(scoreL + ":)", SwingConstants.CENTER);
        textLabel.setForeground(Color.red);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        add(textLabel);

        setFocusable(true);
        requestFocusInWindow();

        Timer timer = new Timer(16, e -> {
            if (keysPressed[KeyEvent.VK_A]) {
                moveU();
            }
            if (keysPressed[KeyEvent.VK_D]) {
                moveD();
            }
            move();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Ball b = balls[0];
        g.setColor(Color.MAGENTA);
        g.fillOval((int) b.x, (int) b.y, 30, 30);

        Bat t = bats[0];
        g.setColor(Color.CYAN);
        g.fillRect((int) t.tx, (int) t.ty, 134, 10);

        paintBricks(g);

    }
    private void paintBricks(Graphics g) {
        int brickWidth = getWidth() / (bricks[0].length + 1);
        int brickHeight = getHeight() / (2 * bricks.length);
        int offsetY = brickHeight / 2;
        int offsetX = brickWidth / 2;

        Ball ball = balls[0];

        for (int y = 0; y < bricks.length; y++) {
            for (int x = 0; x < bricks[y].length; x++) {
                if (bricks[y][x]) {
                    int brickX = offsetX + x * brickWidth;
                    int brickY = offsetY + y * brickHeight;

                    // Draw the brick
                    g.setColor(Color.GRAY);
                    g.fillRect(brickX, brickY, brickWidth, brickHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(brickX, brickY, brickWidth, brickHeight);
                }
            }
        }
    }
//    public void brickbounce(){
//        Ball ball = balls[0];
//
//        if (ball.x + ball.size >= brickX && ball.x <= brickX + brickWidth) {
//
//            bricks[y][x] = false;
//            scoreL++;
//            updateScoreLabel();
//
//            ball.velocityY = -ball.velocityY;
//
//            return;
//        }
//        if (ball.y + ball.size >= brickY && ball.y <= brickY + brickHeight) {
//            bricks[y][x] = false;
//            scoreL++;
//            updateScoreLabel();
//
//            ball.velocityX = -ball.velocityX;
//            return;
//        }
//    }

    public void moveU() {
        Bat t = bats[0];
        if (t.tx > 0) {
            t.tx -= batSpeed;
            repaint();
        }
    }

    public void moveD() {
        Bat t = bats[0];
        if (t.tx < 1550) {
            t.tx += batSpeed;
            repaint();
        }
    }

    public void move() {
        Ball b = balls[0];
        if (ballTeleported) {
            return;
        }
        b.x += b.velocityX;
        b.y += b.velocityY;
        repaint();

        Bat t = bats[0];

        if (b.x <= 0 || b.x + b.size >= getWidth()) {
            b.velocityX = -b.velocityX;
        }

        bounce(b);

        if (b.x <= t.tx + 134 && b.x + b.size >= t.tx && b.y + b.size >= t.ty && b.y <= t.ty + 10) { //in der if Klammer wird geschaut ob Der Ball mit allen Koordinaten den Schläger trifft. es ist quasi: if 'links von rechten Punkt am Schläger' und 'rechts vom linken punkt am schläger' zutrifft dann true. das gleich dann noch mit der y Koordinate
            b.velocityY = -b.velocityY; //velocity umkehren
            double relativeIntersectX = (b.x + (b.size / 2)) - (t.tx + 67);
            double normalizedRelativeIntersectionX = (relativeIntersectX / (134 / 2));
            b.velocityX = normalizedRelativeIntersectionX * 7;
        }
    }

    private void bounce(Ball b) {
        int newX = (int) b.x + (int) b.velocityX;
        int newY = (int) b.y + (int) b.velocityY;

        if (newY <= 0) {
            b.velocityY = -b.velocityY;
            b.y = 0;
        }

        if (newY + b.size >= getHeight()) {
            teleport(b);
        }
    }

    private void updateScoreLabel() {
        textLabel.setText(scoreL + ":)");
    }

    private void teleport(Ball b) {
        b.y = 800;
        b.x = 800;
        b.velocityX = 0;
        b.velocityY = 0;

        Timer timer = new Timer(3000, e -> {
            b.velocityX = ballvelocityX;
            b.velocityY = ballvelocityY;
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed[e.getKeyCode()] = true;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed[e.getKeyCode()] = false;
    }
}
