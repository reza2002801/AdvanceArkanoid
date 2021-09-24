import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;


//--------------------------Bricks-----------------
public class Brick extends Rectangle implements ActionListener{
    boolean destroyed = false;
    int health;
    String kind;
    Boolean visible;
    Timer timer;
    int i=0;
    boolean hasprize;
    String prizekind;

    @Override
    public String toString() {
        return "Brick{" +
                "destroyed=" + destroyed +
                ", health=" + health +
                ", kind='" + kind + '\'' +
                ", visible=" + visible +
                ", timer=" + timer +
                ", i=" + i +
                ", hasprize=" + hasprize +
                ", prizekind='" + prizekind + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                '}';
    }

    Brick(double x, double y, String kind, int health, String prizekind) {
        timer=new Timer(2000,this);
        this.prizekind=prizekind;
        this.visible=true;
        this.kind=kind;
        this.health=health;
        this.x = x;
        this.y = y;
        this.sizeX = Arkanoid.BLOCK_WIDTH;
        this.sizeY = Arkanoid.BLOCK_HEIGHT;

        timer.start();
    }
        void draw(Graphics g) {
            if(this.y>=Arkanoid.SCREEN_HEIGHT-50){
                Arkanoid.scoreboard.die();
            }

            if(!this.kind.equals("invisibleBricks") && this.visible.equals(true)) {
                if(this.kind.equals("vis_invisBrick")){
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.drawImage(Arkanoid.vis_invisBrickImage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);

                }else if(this.kind.equals("gg")){
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.drawImage(Arkanoid.oridinarybrickimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
                }else if(this.kind.equals("woodenBricks")){
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.drawImage(Arkanoid.woodenBricksimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
//                    g.setColor(Color.cyan);
//                    g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);
                }else if(this.kind.equals("prizeBricks")){
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.drawImage(Arkanoid.giftBrickimage, (int)this.x-(int)this.sizeX/2, (int)this.y-(int)this.sizeY/2, null);
                }
//                g.setColor(Color.cyan);
//                g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);
            }



        }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!Arkanoid.pause){
            if(this.visible.equals(true)&&this.kind.equals("vis_invisBrick")){
                this.visible=false;
            }else if(this.visible.equals(false) &&this.kind.equals("vis_invisBrick")){
                this.visible=true;
            }
        }


//        Arkanoid.bricks.add(new Brick(0,0,"d",1,Color.YELLOW));


    }
}