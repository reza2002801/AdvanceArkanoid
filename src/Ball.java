import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

class Ball extends GameObject implements ActionListener, Serializable {
    boolean isfirreball;
    Timer timer;

    double x, y;
    double radius = Arkanoid.BALL_RADIUS;
    double velocityX = Arkanoid.BALL_VELOCITY;
    double velocityY = Arkanoid.BALL_VELOCITY;

    Ball(int x, int y) {
        timer=new Timer(5000,this);
        this.x = x;
        this.y = y;
        this.isfirreball=false;
        timer.start();
    }
    void draw(Graphics g) {

        if(this.isfirreball){
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(Arkanoid.fireballimage, (int)this.x-(int)this.radius, (int)this.y-(int)this.radius, null);
        }else {
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(Arkanoid.ballimage, (int)this.x-(int)this.radius, (int)this.y-(int)this.radius, null);
        }
//        g.setColor(Color.BLUE);
//        g.fillOval((int) left(), (int) top(), (int) radius * 2,
//                (int) radius * 2);


    }

    void update(ScoreBoard scoreBoard, Paddle paddle) {


        if(!Arkanoid.pause){
            x += velocityX * Arkanoid.FT_STEP;
            y += velocityY * Arkanoid.FT_STEP;

            if (left() < 0)
                velocityX = Arkanoid.BALL_VELOCITY;
            else if (right() > Arkanoid.SCREEN_WIDTH)
                velocityX = -Arkanoid.BALL_VELOCITY;
            if (top() < 0) {
                velocityY = Arkanoid.BALL_VELOCITY;
            } else if (bottom() > Arkanoid.SCREEN_HEIGHT) {
                if(Arkanoid.balls.size()<=1|| Arkanoid.balls==null){
                    velocityY = -Arkanoid.BALL_VELOCITY;
                    x = paddle.x;
                    y = paddle.y - 50;
                    scoreBoard.die();


                }else{
                    Arkanoid.balls.remove(this);
                }

            }

        }

    }

    double left() {
        return x - radius;
    }

    double right() {
        return x + radius;
    }

    double top() {
        return y - radius;
    }

    double bottom() {
        return y + radius;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!Arkanoid.pause){
            Arkanoid.c+=0.007;
            Arkanoid.BALL_VELOCITY1=Arkanoid.c*Arkanoid.BALL_VELOCITY2;
            if(this.isfirreball==true){
                this.isfirreball=false;
            }
            if(Arkanoid.BALL_VELOCITY!=Arkanoid.BALL_VELOCITY1){
                Arkanoid.BALL_VELOCITY=Arkanoid.BALL_VELOCITY1;
            }
        }



    }
}