import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class gamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.5555));
    static final Dimension GAME_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDHT = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    paddle paddle1;
    paddle paddle2;
    ball ball;
    score score;


    gamePanel(){
        newPaddle();
        newBall();
        score = new score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(GAME_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void newPaddle(){
        paddle1 = new paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDHT,PADDLE_HEIGHT,1);
        paddle2 = new paddle((GAME_WIDTH)-(PADDLE_WIDHT),(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDHT,PADDLE_HEIGHT,2);
    }

    public void newBall(){
        //If we wan t the ball to start from random place in y axes and middle of the x axes
        random = new Random();
        ball = new ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);

        //If we want the ball to start from the center
        //ball = new ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),(GAME_HEIGHT/2)-(BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
    }

    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }

    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollesion() {
        // bounce ball off top and bottom window edges
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= (GAME_HEIGHT) - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        // bounce ball off paddles
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity = ball.xVelocity + 1;          //Optional part for more difficulty
            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddle2)) {
            ball.xVelocity = ball.xVelocity + 1;
            ball.xVelocity = -ball.xVelocity;
            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //stops paddles at window edges
        if (paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if (paddle1.y >= (GAME_HEIGHT) - (PADDLE_HEIGHT)) {
            paddle1.y = (GAME_HEIGHT) - (PADDLE_HEIGHT);
        }
        if (paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if (paddle2.y >= (GAME_HEIGHT) - (PADDLE_HEIGHT)) {
            paddle2.y = (GAME_HEIGHT) - (PADDLE_HEIGHT);
        }

        if (ball.x <= 0) {
            score.player2++;
            newPaddle();
            newBall();
        }

        if (ball.x >= GAME_WIDTH-BALL_DIAMETER){
            score.player1++;
            newPaddle();
            newBall();
        }
    }

    public void run(){
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        while (true){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            if(delta >=1){
                move();
                checkCollesion();
                repaint();
                delta--;
            }
        }

    }

    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
