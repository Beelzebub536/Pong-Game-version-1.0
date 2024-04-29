import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 450;
    private static final int CEILING = 25;
    private static final int PADDLE_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 80;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_SPEED = 10;
    private static final int BALL_SPEED = 5;

    private int paddle1Y = 25 + (HEIGHT - CEILING) / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = paddle1Y;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = 25 + (HEIGHT - CEILING) / 2 - BALL_SIZE / 2;
    private int ballXSpeed = BALL_SPEED;
    private int ballYSpeed = BALL_SPEED;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPress = false;
    private boolean sPress = false;
    private static int score1 = 0;
    private static int score2 = 0;
    private final JLabel scoreBoard;

    public PongGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        scoreBoard = new JLabel("Player 1: " + score1 + " | Player 2: " + score2);
        scoreBoard.setForeground(Color.WHITE);
        scoreBoard.setFont(new Font("Arial", Font.PLAIN, 18));
        add(scoreBoard, BorderLayout.NORTH);

        Timer timer = new Timer(1000 / 60, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawLine(20, CEILING, WIDTH - 20, CEILING);
        g.drawLine(WIDTH / 2, CEILING + 20, WIDTH / 2, HEIGHT - 20);
        g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkCollisions();
        movePaddles();
        moveBall();
        updateScoreLabel();
        repaint();
    }

    private void movePaddles() {
        if (upPressed && paddle1Y > CEILING) {
            paddle1Y -= PADDLE_SPEED;
        }
        if (wPress && paddle2Y > CEILING) {
            paddle2Y -= PADDLE_SPEED;
        }
        if (downPressed && paddle1Y < HEIGHT - PADDLE_HEIGHT) {
            paddle1Y += PADDLE_SPEED;
        }
        if (sPress && paddle2Y < HEIGHT - PADDLE_HEIGHT) {
            paddle2Y += PADDLE_SPEED;
        }
    }

    private void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;
        if (ballY <= CEILING || ballY >= HEIGHT - BALL_SIZE) {
            ballYSpeed = -ballYSpeed;
        }
    }

    private void checkCollisions() {
        Rectangle paddle1 = new Rectangle(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        Rectangle paddle2 = new Rectangle(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        Rectangle ball = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
        if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
            ballXSpeed = -ballXSpeed;
        }

        if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
            if (ballX <= 0)
                score2 += 10;
            else
                score1 += 10;
            ballX = WIDTH / 2 - BALL_SIZE / 2;
            ballY = HEIGHT / 2 - BALL_SIZE / 2;
        }
    }

    private void updateScoreLabel() {
        scoreBoard.setText("Player 1: " + score1 + " | Player 2: " + score2);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            wPress = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            sPress = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            wPress = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            sPress = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new PongGame(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
