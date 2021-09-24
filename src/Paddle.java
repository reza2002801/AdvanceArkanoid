import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

class Paddle extends Rectangle  implements Serializable,ActionListener {

    double velocity = 0.0;
    Timer timer;
    boolean isfool;

    public Paddle(double x, double y) {

        timer=new Timer(10000,this);
//        timer.setRepeats(false);
        this.isfool=false;

        this.x = x;
        this.y = y;
        this.sizeX = Arkanoid.PADDLE_WIDTH;
        this.sizeY = Arkanoid.PADDLE_HEIGHT;
//        timer.start();
    }

    void update() {
        if(!Arkanoid.pause){
            x += velocity * Arkanoid.FT_STEP;

        }
    }

    void stopMove() {
        velocity = 0.0;
    }

    void moveLeft() {
        if(!Arkanoid.pause){
            if (!isfool){
                if (left() > 0.0+ this.sizeX/3) {
                    velocity = -Arkanoid.PADDLE_VELOCITY;
                } else {
                    velocity = 0.0;
                }
            }else {
                if (right() < Arkanoid.SCREEN_WIDTH-this.sizeX/3) {
                    velocity = Arkanoid.PADDLE_VELOCITY;
                } else {
                    velocity = 0.0;
                }
            }

        }

    }

    void moveRight() {
        if(!Arkanoid.pause){
            if(!this.isfool){
                if (right() < Arkanoid.SCREEN_WIDTH-this.sizeX/3) {
                    velocity = Arkanoid.PADDLE_VELOCITY;
                } else {
                    velocity = 0.0;
                }
            }else {
                if (left() > 0.0+ this.sizeX/3) {
                    velocity = -Arkanoid.PADDLE_VELOCITY;
                } else {
                    velocity = 0.0;
                }
            }
        }


    }

    void draw(Graphics g) {

            if(this.sizeX==90){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.paddleimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.sizeX==120){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.paddlebigimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.sizeX==50){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.paddlesmallimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }


//        g.setColor(Color.RED);
//        g.fillRect((int) (left()), (int) (top()), (int) sizeX, (int) sizeY);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!!Arkanoid.pause){
            if(this.sizeX!=Arkanoid.PADDLE_WIDTH){
                this.sizeX=Arkanoid.PADDLE_WIDTH;
            }
            if(this.isfool=true){
                this.isfool=false;
            }
            timer.stop();
        }

    }
}
