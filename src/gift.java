import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;


public class gift extends  Rectangle {

    boolean start;
    Timer timer;
    boolean active;
//    double sizeX;
//    double sizeY;
    String kind;
    public gift(double x,double y,String kind) {
        this.kind=kind;
        this.start=false;
        this.active=false;
        this.x=x;
        this.y=y;
        this.sizeX = Arkanoid.GIFT_WIDTH;
        this.sizeY = Arkanoid.GIFT_HEIGHT;

    }
    void update() {
        if(!Arkanoid.pause){
            y += (Arkanoid.BALL_VELOCITY/2) * Arkanoid.FT_STEP;

        }




    }

    @Override
    public String toString() {
        return "gift{" +
                "x=" + x +
                ", y=" + y +
                ", sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", start=" + start +
                ", timer=" + timer +
                ", active=" + active +
                ", kind='" + kind + '\'' +
                '}';
    }

    void draw(Graphics g) {

            if(this.kind.equals("fb")){
//            g.setColor(Color.RED);
//            g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.fireball1image, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.kind.equals("bp")){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.biggerpaddleimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.kind.equals("sp")){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.smallerpaddleimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.kind.equals("mb")){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.multipleballimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.kind.equals("fab")){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.fasterballimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.kind.equals("sb")){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.slowerballimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.kind.equals("ip")){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.foolpaddleimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }else if(this.kind.equals("rg")){
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(Arkanoid.randomimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
            }
//        g.setColor(Color.RED);
//            g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);





    }


}