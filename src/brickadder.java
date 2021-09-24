import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Random;

public class brickadder implements ActionListener {
    Timer timer;

    public brickadder() {
        timer=new Timer(10000,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!Arkanoid.pause){
            String[] pk={"fb","bp","sp","mb","fab","sb","ip","rg"};
            Iterator<Brick> it = Arkanoid.fu.iterator();
            while (it.hasNext()){
                Brick brick=it.next();
                if(brick!=null){
                    brick.y+=23;
                }
            }

            Random r=new Random();
            for (int iX = 0; iX < 12; ++iX) {
                for (int iY = 0; iY < 1; ++iY) {
                    int a=r.nextInt(5);
                    if(a==0){
                        Arkanoid.fu.add(new Brick((iX + 1) * (Arkanoid.BLOCK_WIDTH + 3) + 22,
                                (iY + 2) * (Arkanoid.BLOCK_HEIGHT + 3) + 20,"vis_invisBrick",1,null));
                    }else if(a==1){
                        Arkanoid.fu.add(new Brick((iX + 1) * (Arkanoid.BLOCK_WIDTH + 3) + 22,
                                (iY + 2) * (Arkanoid.BLOCK_HEIGHT + 3) + 20,"woodenBricks",2,null));
                    }else if(a==2){
                        Arkanoid.fu.add(new Brick((iX + 1) * (Arkanoid.BLOCK_WIDTH + 3) + 22,
                                (iY + 2) * (Arkanoid.BLOCK_HEIGHT + 3) + 20,"invisibleBricks",1,null));
                    }else if(a==3){
                        Arkanoid.fu.add(new Brick((iX + 1) * (Arkanoid.BLOCK_WIDTH + 3) + 22,
                                (iY + 2) * (Arkanoid.BLOCK_HEIGHT + 3) + 20,"gg",1,null));
                    }else if(a==4){
                        int b=r.nextInt(pk.length);

                        Arkanoid.fu.add(new Brick((iX + 1) * (Arkanoid.BLOCK_WIDTH + 3) + 22,
                                (iY + 2) * (Arkanoid.BLOCK_HEIGHT + 3) + 20,"prizeBricks",1,pk[b]));
                    }


                }
            }
        }

    }
}
