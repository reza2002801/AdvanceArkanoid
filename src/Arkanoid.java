import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

public class Arkanoid extends JFrame implements KeyListener  {
    public static boolean pause=true;

    private static final long serialVersionUID = 1L;

    /* CONSTANTS */
    Timer timer;
    public static String PlayerName;

    public static final int SCREEN_WIDTH = 850;
    public static final int SCREEN_HEIGHT = 800;

    public static double BALL_RADIUS = 10.0;
    public static double BALL_VELOCITY = 0.5;

    public static final double PADDLE_WIDTH = 90.0;
    public static final double PADDLE_HEIGHT = 20.0;
    public static final double PADDLE_VELOCITY = 0.7;

    public static final double GIFT_WIDTH=20.0;
    public static final double GIFT_HEIGHT=20.0;


    public static final double BLOCK_WIDTH = 60.0;
    public static final double BLOCK_HEIGHT = 20.0;

    public static final int COUNT_BLOCKS_X = 12;
    public static final int COUNT_BLOCKS_Y = 5;

    public static int PLAYER_LIVES = 3;
    public static double BALL_VELOCITY1=0.4;

    public static final double FT_SLICE = 1.0;
    public static double FT_STEP = 1.0;
    public static String GameName="";
    public static LinkedList<gift> prize=new LinkedList<>();
    public static Double BALL_VELOCITY2=0.4;


    public static final String FONT = "Courier New";

    /* GAME VARIABLES */

    private boolean tryAgain = false;
    private boolean running = false;

    public static Paddle paddle = new Paddle(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 50);
    Ball ball = new Ball((int)SCREEN_WIDTH / 2 +20, (int)SCREEN_HEIGHT - 50 -(int) PADDLE_HEIGHT- (int) BALL_RADIUS);
    Ball ball1 = new Ball(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    Ball ball2 = new Ball(SCREEN_WIDTH / 2-30, SCREEN_HEIGHT / 2);
    public static LinkedList<Ball> balls=new LinkedList<>();
    public static LinkedList<Brick> bricks =new LinkedList<>();
    public static LinkedList<Brick> extra_bricks = new LinkedList<>();
    public static ScoreBoard scoreboard = new ScoreBoard();

    private double lastFt;
    private double currentSlice;
//    gift n=new gift(100,200,"dfh");




// ---------------------------------Ball----------


    boolean isIntersecting(GameObject mA, GameObject mB) {
        return mA.right() >= mB.left() && mA.left() <= mB.right()
                && mA.bottom() >= mB.top() && mA.top() <= mB.bottom();
    }

    void testCollision(Paddle mPaddle, Ball mBall) {
        if (!isIntersecting(mPaddle, mBall))
            return;
        mBall.velocityY = -BALL_VELOCITY;

        if (mBall.x < mPaddle.x && mBall.velocityX<0){
            double places= Math.abs(Math.cos((180 * 2 * mPaddle.x - 180 * 2 * mBall.x) / mPaddle.sizeX));
            mBall.velocityX = -BALL_VELOCITY*places;
        }
        else if(mBall.x > mPaddle.x && mBall.velocityX<0){
            double places= -1.5*Math.abs(Math.cos((180 * 2 * mPaddle.x - 180 * 2 * mBall.x) / mPaddle.sizeX));
            mBall.velocityX = -BALL_VELOCITY*places;
        }
        else if(mBall.x < mPaddle.x && mBall.velocityX>0){
            double places= 1.5*Math.abs(Math.cos((180 * 2 * mPaddle.x - 180 * 2 * mBall.x) / mPaddle.sizeX));
            mBall.velocityX = -BALL_VELOCITY*places;
        }
        else if(mBall.x >mPaddle.x &&mBall.velocityX>0){
            double places= Math.abs(Math.cos((180 * 2 * mPaddle.x - 180 * 2 * mBall.x) / mPaddle.sizeX));
            mBall.velocityX = +1.5*BALL_VELOCITY*places;
        }

//  0      else
//            mBall.velocityX = BALL_VELOCITY;
    }

    void testCollision(Brick mBrick, Ball mBall, ScoreBoard scoreboard) {
        if (!isIntersecting(mBrick, mBall))
            return;
        if(mBrick.visible==true){
            if(!mBall.isfirreball){
                mBrick.health --;

                scoreboard.increaseScore();

                double overlapLeft = mBall.right() - mBrick.left();
                double overlapRight = mBrick.right() - mBall.left();
                double overlapTop = mBall.bottom() - mBrick.top();
                double overlapBottom = mBrick.bottom() - mBall.top();

                boolean ballFromLeft = overlapLeft < overlapRight;
                boolean ballFromTop = overlapTop < overlapBottom;

                double minOverlapX = ballFromLeft ? overlapLeft : overlapRight;
                double minOverlapY = ballFromTop ? overlapTop : overlapBottom;

                if (minOverlapX < minOverlapY) {
                    mBall.velocityX = ballFromLeft ? -BALL_VELOCITY : BALL_VELOCITY;
                } else {
                    mBall.velocityY = ballFromTop ? -BALL_VELOCITY : BALL_VELOCITY;
                }
            }else{
                mBrick.health=0;
                scoreboard.increaseScore();
            }
        }

    }
    void testCollision(Paddle mPaddle, gift mgift,Ball mBall) {
        if (!isIntersecting(mPaddle, mgift))
            return;
        String[] pk={"fb","bp","sp","mb","fab","sb","ip","rg"};
        if(mgift.kind=="rg"){
            int a=new  Random().nextInt(6);
            mgift.kind=pk[a];
            if(mgift.kind.equals("bp")){
                mPaddle.sizeX = 120 ;
                mPaddle.timer.start();
            }else if(mgift.kind.equals("sp")){
                mPaddle.sizeX=50;
                mPaddle.timer.start();
            }
        else if(mgift.kind.equals("mb")){
            this.balls.add(new Ball((int)mPaddle.x +20, (int)mPaddle.y -(int) mPaddle.sizeY- (int) mBall.radius));
            this.balls.add(new Ball((int)mPaddle.x -20, (int)mPaddle.y -(int) mPaddle.sizeY- (int) mBall.radius) );
        }
            else if(mgift.kind.equals("fab")){
//            FT_STEP *=1.2;
                BALL_VELOCITY=0.8;
                mBall.timer.start();
                mBall.velocityY=BALL_VELOCITY*mBall.velocityY/Math.abs(mBall.velocityY);

            }
            else if(mgift.kind.equals("sb")){
                mBall.timer.start();
//            FT_STEP /=1.2;
                BALL_VELOCITY=0.3;
                mBall.velocityY=BALL_VELOCITY*mBall.velocityY/Math.abs(mBall.velocityY);
            }
//
            else if(mgift.kind.equals("ip")){
                mPaddle.timer.start();
                mPaddle.isfool=true;
            }
            else if(mgift.kind.equals("fb")){
                for(int i=0;i<balls.size();i++){
                    balls.get(i).timer.start();
                    balls.get(i).isfirreball=true;
                }
            }
            pu.remove(mgift);
        }else if(mgift.kind.equals("bp")){
            mPaddle.sizeX = 120 ;
            mPaddle.timer.start();
        }else if(mgift.kind.equals("sp")){
            mPaddle.sizeX=50;
            mPaddle.timer.start();
        }
        else if(mgift.kind.equals("mb")){
            this.balls.add(new Ball((int)mPaddle.x +20, (int)mPaddle.y -(int) mPaddle.sizeY- (int) mBall.radius));
            this.balls.add(new Ball((int)mPaddle.x -20, (int)mPaddle.y -(int) mPaddle.sizeY- (int) mBall.radius) );
        }
        else if(mgift.kind.equals("fab")){
//            FT_STEP *=1.2;
            BALL_VELOCITY=0.85;
            mBall.timer.start();
            mBall.velocityY=BALL_VELOCITY*mBall.velocityY/Math.abs(mBall.velocityY);

        }
        else if(mgift.kind.equals("sb")){
            mBall.timer.start();
//            FT_STEP /=1.2;
            BALL_VELOCITY=0.25;
            mBall.velocityY=BALL_VELOCITY*mBall.velocityY/Math.abs(mBall.velocityY);
        }
//
        else if(mgift.kind.equals("ip")){
            mPaddle.timer.start();
            mPaddle.isfool=true;
        }
        else if(mgift.kind.equals("fb")){
            for(int i=0;i<balls.size();i++){
                balls.get(i).timer.start();
                balls.get(i).isfirreball=true;
            }

        }
        pu.remove(mgift);


    }

    void initializeBricks(List<Brick> bricks) {
        // deallocate old bricks
        bricks.clear();
        Random r=new Random();
        String[] pk={"fb","bp","sp","mb","fab","sb","ip","rg"};

        for (int iX = 0; iX < COUNT_BLOCKS_X; ++iX) {
            for (int iY = 0; iY < COUNT_BLOCKS_Y; ++iY) {
                int a=r.nextInt(5);
                if(a==0){
                    bricks.add(new Brick((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                            (iY + 2) * (BLOCK_HEIGHT + 3) + 20,"vis_invisBrick",1,null));
                }else if(a==1){
                    bricks.add(new Brick((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                            (iY + 2) * (BLOCK_HEIGHT + 3) + 20,"woodenBricks",2,null));
                }else if(a==2){
                    bricks.add(new Brick((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                            (iY + 2) * (BLOCK_HEIGHT + 3) + 20,"invisibleBricks",1,null));
                }else if(a==3){
                    bricks.add(new Brick((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                            (iY + 2) * (BLOCK_HEIGHT + 3) + 20,"gg",1,null));
                }else if(a==4){
                    int b=r.nextInt(pk.length);

                    bricks.add(new Brick((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                            (iY + 2) * (BLOCK_HEIGHT + 3) + 20,"prizeBricks",1,pk[b]));
                }


            }
        }




    }
    public static Image backgroundImage;
    public static Image paddleimage;
    public static Image paddlebigimage;
    public static Image paddlesmallimage;
    public static Image ballimage;
    public static Image fireballimage;
    public static Image fireball1image;
    public static Image woodenBricksimage;
    public static Image oridinarybrickimage; //
    public static Image vis_invisBrickImage;
    public static Image giftBrickimage;
    public static Image smallerpaddleimage;
    public static Image biggerpaddleimage;

    public static Image fasterballimage;

    public static Image slowerballimage;

    public static Image randomimage;
    public static Image multipleballimage;
    public static Image foolpaddleimage;
    static {
        try {
            smallerpaddleimage =ImageIO.read(new File("smallerpaddle.png"));
            biggerpaddleimage =ImageIO.read(new File("biggerpaddle.png"));
            fasterballimage =ImageIO.read(new File("fasteball.png"));
            slowerballimage =ImageIO.read(new File("slowball.png"));
            randomimage =ImageIO.read(new File("random.png"));
            multipleballimage =ImageIO.read(new File("trippleball.png"));
            foolpaddleimage =ImageIO.read(new File("foolpaddle.png"));
            backgroundImage = ImageIO.read(new File("BackGround.jpg"));
            paddleimage =ImageIO.read(new File("paddle1.png"));
            paddlebigimage =ImageIO.read(new File("Paddlebig.png"));
            paddlesmallimage =ImageIO.read(new File("Paddlesmall.png"));
            ballimage =ImageIO.read(new File("ball.png"));
            woodenBricksimage =ImageIO.read(new File("woodenbrick.png"));
            fireballimage =ImageIO.read(new File("fireball.png"));
            fireball1image =ImageIO.read(new File("fireball1.png"));
            oridinarybrickimage =ImageIO.read(new File("ordinarybrick.png"));
            vis_invisBrickImage =ImageIO.read(new File("vis_invisbrick.png"));
            giftBrickimage =ImageIO.read(new File("giftBrick.png"));



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void paint( Graphics g ) {
//        super.paint(g);
//        g.drawImage(backgroundImage, 0, 0, null);
//    }

    public Arkanoid() throws IOException {
//        this.bricks=new LinkedList<Brick>();
        this.balls.add(ball);
//        this.balls.add(ball1);
//        this.balls.add(ball2);
        JLabel background1 = new JLabel(new ImageIcon("BackGround.png"));
        Image icon = Toolkit.getDefaultToolkit().getImage("GameIcon.png");
        this.setIconImage(icon);
        this.setBackground(Color.CYAN);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(background1);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setVisible(true);
//        this.setIconImage();
        this.addKeyListener(this);
        this.setLocationRelativeTo(null);
        new brickadder();


        this.createBufferStrategy(2);

//        initializeBricks(bricks);

    }

    void run() throws IOException {

        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = bf.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        running = true;

        while (running) {

            long time1 = System.currentTimeMillis();

            if (!scoreboard.gameOver && !scoreboard.win) {
                tryAgain = false;
                try{
                    update();
                }catch(Exception e){
//                    e.printStackTrace();
                    System.out.println("");
                }
                try{
                    drawScene(ball, bricks, scoreboard);

                }catch (Exception e){
                    System.out.println("");
                }


                // to simulate low FPS
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                if (tryAgain) {
                    try {
                        keeper.updatefile(this.PlayerName,scoreboard.score);
                    } catch (IOException e) {
                        FileWriter mywriter=new FileWriter("Scores.txt");
                    }
                    Arkanoid.pause=true;
                    tryAgain = false;
                    initializeBricks(this.fu);
                    scoreboard.lives = PLAYER_LIVES;
                    Arkanoid.pu.clear();
                    Arkanoid.c=1.000;
                    Arkanoid.q=2;
                    Arkanoid.BALL_VELOCITY1=0.4;
                    scoreboard.score = 0;
                    scoreboard.win = false;
                    scoreboard.gameOver = false;
                    scoreboard.updateScoreboard();
                    ball.x = SCREEN_WIDTH / 2;
                    ball.y = SCREEN_HEIGHT / 2;
                    paddle.x = SCREEN_WIDTH / 2;
                }
            }

            long time2 = System.currentTimeMillis();
            double elapsedTime = time2 - time1;

            lastFt = elapsedTime;

            double seconds = elapsedTime / 1000.0;
            if (seconds > 0.0) {
                double fps = 1.0 / seconds;
                this.setTitle("FPS: " + fps);
            }

        }


        keeper k=new keeper();
        k.keeper1(this);
        while (k.b){
            System.out.println("");

        }
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }

    private void update() {

        currentSlice += lastFt;

        for (; currentSlice >= FT_SLICE; currentSlice -= FT_SLICE) {
//            n.update();
            if(pu.size()!=0 && pu!=null){
                for(gift  gif:pu){
                    gif.update();
                    testCollision(paddle,gif,ball);
                }
            }
            for (int i=0;i<balls.size();i++){
                balls.get(i).update(scoreboard, paddle);
            }


//            ball.update(scoreboard, paddle);
            paddle.update();
            for (int i=0;i<balls.size();i++){
                testCollision(paddle, balls.get(i));
            }
//            testCollision(paddle, ball);

            Iterator<Brick> it = fu.iterator();
            while (it.hasNext()) {
                Brick brick = it.next();
                for (int i=0;i<balls.size();i++){
                    testCollision(brick, balls.get(i), scoreboard);
                }
//                testCollision(brick, ball, scoreboard);
                if (brick.health==0) {
                    if(brick.prizekind != null){

                        pu.add(new gift(brick.x,brick.y,brick.prizekind));
                        it.remove();
                    }
                    else {
                        it.remove();
                    }
                }
            }


        }
    }

    private void drawScene(Ball ball, List<Brick> bricks, ScoreBoard scoreboard) {
        // Code for the drawing goes here.
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {

            g = bf.getDrawGraphics();
//            g.drawImage(backgroundImage,0,0,null);
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(Arkanoid.backgroundImage, (int)0, (int)0, null);


//            g.setColor(Color.CYAN);
//            g.fillRect(0, 0, getWidth(), getHeight());
//            n.draw(g);
            if(pu.size()!=0 && pu!=null){
                for (gift gif : pu) {
                    gif.draw(g);
                }
            }
            for (int i=0;i<balls.size();i++){
                try {
                    balls.get(i).draw(g);
                }catch (Exception e){
                    System.out.println("brick");
                }

            }
//            ball.draw(g);
            paddle.draw(g);
            for (Brick brick : fu) {
                try {
                    brick.draw(g);
                }catch (Exception e){
                    System.out.println("brick");
                }

            }

            scoreboard.draw(g);

        } finally {
            g.dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync();

    }
    public static int a=0;
//    public static ArrayList<Arkanoid> plays=new ArrayList<>();


    public static LinkedList<Brick> getBricks() {
        return bricks;
    }


    //-------------------encode-decode-------------------------


    public static void encoder(Arkanoid a){
        try {
            FileWriter myWriter = new FileWriter(a.PlayerName+"_"+a.GameName+".txt");
            myWriter.write(String.valueOf( a.paddle.x)+"\n");
//            myWriter.newLine();
            myWriter.write(String.valueOf(a.paddle.sizeX)+"\n");
            myWriter.write(String.valueOf(a.PLAYER_LIVES)+"\n");
            myWriter.write(String.valueOf(a.scoreboard.score)+"\n");
            myWriter.write(String.valueOf(a.c)+"\n");
            for(Ball bal:a.balls){
                myWriter.write("2"+String.valueOf(bal.x)+"\n");
                myWriter.write("2"+String.valueOf(bal.y)+"\n");
                myWriter.write("2"+String.valueOf(bal.velocityX)+"\n");
                myWriter.write("2"+String.valueOf(bal.velocityY)+"\n");
                myWriter.write("2"+String.valueOf(bal.isfirreball)+"\n");
            }
            for(Brick brick:a.fu){
                myWriter.write("3"+String.valueOf(brick.x)+"\n");
                myWriter.write("3"+String.valueOf(brick.y)+"\n");
                myWriter.write("3"+String.valueOf(brick.kind)+"\n");
                myWriter.write("3"+String.valueOf(brick.visible)+"\n");
                myWriter.write("3"+String.valueOf(brick.prizekind)+"\n");

            }
            for(gift gif:a.pu){
                myWriter.write("4"+String.valueOf(gif.x)+"\n");
                myWriter.write("4"+String.valueOf(gif.y)+"\n");
                myWriter.write("4"+String.valueOf(gif.kind)+"\n");
            }
            myWriter.write("end!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Arkanoid decoder(Arkanoid a,String FileName) throws FileNotFoundException {
        a.isread=true;

//                        LinkedList<Ball> ballss=new LinkedList<>();
//                        LinkedList<Brick> brickss=new LinkedList<>();
//                        LinkedList<gift> prizee=new LinkedList<>();
        File myObj = new File(FileName);
//                        bricks.clear();

        Scanner myReader = new Scanner(myObj);
        int i=0;
        int j=0;
        Paddle p=Arkanoid.paddle;
        double balx=0;
        double bally=0;
        double velx=0;
        double vely=0;
        boolean b=true;
        String mm="";
        String nnn="0";

        ScoreBoard s=Arkanoid.scoreboard;
        while (myReader.hasNextLine()) {
            String shittttt=myReader.nextLine();
            System.out.println(i+" "+j);
            System.out.println(shittttt+"+");
            if(i==0 &&j==0){
                p.x=Double.parseDouble(shittttt);
            }else if(i==1&&j==1){
                p.sizeX=Double.parseDouble(shittttt);

            }else if(i==2&&j==2){
                Arkanoid.PLAYER_LIVES=Integer.parseInt(shittttt);
            }else if(i==3&&j==3){
                s.score=Integer.parseInt(shittttt);

            }else if(i==4&&j==4){
                a.c=Double.parseDouble(shittttt);
                i=-1;
            }
            else if(String.valueOf(shittttt.charAt(0)).equals("2")&& i%5==0&&j>4){

                balx=Double.parseDouble(shittttt.substring(1));

            }else if(String.valueOf(shittttt.charAt(0)).equals("2")&& i%5==1&&j>3){
                bally=Double.parseDouble(shittttt.substring(1));
            }
            else if(String.valueOf(shittttt.charAt(0)).equals("2")&& i%5==2&&j>3){
                velx=Double.parseDouble(shittttt.substring(1));
            }else if(String.valueOf(shittttt.charAt(0)).equals("2")&& i%5==3&&j>3){
                vely=Double.parseDouble(shittttt.substring(1));
            }else if(String.valueOf(shittttt.charAt(0)).equals("2")&& i%5==4&&j>3){
                b=Boolean.parseBoolean(shittttt.substring(1));
                i=-1;
                a.balls.add(new Ball((int) balx,(int)bally));
                a.balls.get(Arkanoid.balls.size()-1).velocityY=vely;
                a.balls.get(Arkanoid.balls.size()-1).velocityX=velx;
                a.balls.get(Arkanoid.balls.size()-1).isfirreball=b;

            }else if(String.valueOf(shittttt.charAt(0)).equals("3")&& i%5==0&&j>3){
                balx=Double.parseDouble(shittttt.substring(1));
            }else if(String.valueOf(shittttt.charAt(0)).equals("3")&& i%5==1&&j>3){
                bally=Double.parseDouble(shittttt.substring(1));
            }else if(String.valueOf(shittttt.charAt(0)).equals("3")&& i%5==2&&j>3){
                mm=shittttt.substring(1);

//                                System.out.println("#"+b);
            }else if(String.valueOf(shittttt.charAt(0)).equals("3")&& i%5==3&&j>3){
                b=Boolean.parseBoolean(shittttt.substring(1));
//                                mm=shittttt.substring(1);
            }else if(String.valueOf(shittttt.charAt(0)).equals("3")&& i%5==4&&j>3){
                if(!shittttt.substring(1).equals("null")){
                    nnn=shittttt.substring(1);
                }else {
                    nnn=null;
                }
                a.fu.add(new Brick(balx,bally,mm,1,nnn));
                a.fu.get(a.fu.size()-1).visible=b;
                System.out.println("@"+b);
                i=-1;
            }else if(String.valueOf(shittttt.charAt(0)).equals("4")&& i%3==0&&j>3){
                balx=Double.parseDouble(shittttt.substring(1));
            }else if(String.valueOf(shittttt.charAt(0)).equals("4")&& i%3==1&&j>3){
                bally=Double.parseDouble(shittttt.substring(1));
            }else if(String.valueOf(shittttt.charAt(0)).equals("4")&& i%3==2&&j>3){
                mm=shittttt.substring(1);
                a.pu.add(new gift(balx,bally,mm));
                i=-1;
            }
            j++;
            i++;
        }
        myReader.close();

        System.out.println(a.balls);
        a.prize=pu;
        System.out.println(a.prize);

//                        f.bricks=brickss;
//                        f.balls=ballss;
        a.paddle=p;
        a.scoreboard=s;
        a.bricks=fu;
        return a;


    }
    //-------------------encode-decode-------------------------

    public static void setBricks(LinkedList<Brick> bricks) {
        Arkanoid.bricks = bricks;
    }
    public static Boolean isread=false;
    public  static LinkedList<Brick> fu=new LinkedList<>();
    public  static LinkedList<gift> pu=new LinkedList<>();
    public static Double c= Double.valueOf(1);


    public static void main(String[] args) throws FileNotFoundException {
        JScrollPane scrollPane = new JScrollPane();



        JFrame frame1=new JFrame();
        JList<String> jList=new JList<>(keeper.prsnScr());
        jList.setBounds(150,300,150,200);
        scrollPane.setBounds(150,300,150,150);
        scrollPane.setViewportView(jList);

        JPanel panel=new JPanel();
        JButton b1=new JButton("NewGame");
        JButton b2=new JButton("PreviousGame");
        TextField txtfld1=new TextField();
        TextField txtfld2=new TextField();
        TextField txtfld3=new TextField();
        JLabel label1=new JLabel("NewGameName");
        JLabel label2=new JLabel("PreviousGameName");
        JLabel label3=new JLabel("PlayerName");
        JLabel label4=new JLabel("ScoreTable");

        frame1.getContentPane();
        JLabel label=new JLabel("Do You Want To Start A NewGame or a Previous one");
        Dimension size=label.getPreferredSize();
        Dimension sizeb1=b1.getPreferredSize();
        Dimension sizeb2=b2.getPreferredSize();
//        Dimension size=new Dimension(90,30);
        label.setBounds(50,50,size.width,size.height);
        b1.setBounds(50,150,100,40);
        b2.setBounds(50,250,100,40);
        txtfld1.setBounds(250,150,100,30);
        txtfld2.setBounds(250,250,100,30);
        txtfld3.setBounds(170,80,100,20);
        label1.setBounds(200,120,size.width,size.height);
        label2.setBounds(200,220,size.width,size.height);
        label3.setBounds(70,80,100,size.height);
        label4.setBounds(80,300,size.width,size.height);



//        b2.addActionListener(this);

//        label.setBackground(Color.BLACK);
//        label.setOpaque(true);
        panel.setLayout(null);
        panel.add(label);
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(txtfld1);
        panel.add(txtfld2);
        panel.add(txtfld3);
        panel.add(scrollPane);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.add(panel);
        panel.add(b1);
        panel.add(b2);
        frame1.setSize(400,500);
        frame1.setVisible(true);



        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(txtfld1.getText()!=null &txtfld3.getText()!=null){
                    a=1;
                    GameName=txtfld1.getText();
                    PlayerName=txtfld3.getText();
                    frame1.dispose();

                }

            }
        });
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(txtfld1.getText()!=null &txtfld3.getText()!=null){
                    a=2;
                    GameName=txtfld2.getText();
                    PlayerName=txtfld3.getText();
                    frame1.dispose();

                }

            }
        });
        while(true){
            System.out.println(GameName);
            if(a==2){
                try{
                    Arkanoid f=new Arkanoid();
                    f=decoder(new Arkanoid(),PlayerName+"_"+GameName+".txt");
//                    System.out.pritln(f.balls);
//                    System.out.println(f.fu);
//                    System.out.println(f.pu);n
                    f.run();

                }catch(Exception erer){
                    erer.printStackTrace();
                }
                break;
            }
            else if(a==1){
                try{
                    Arkanoid f=new Arkanoid();
                    f.initializeBricks(fu);
                    f.run();

                }catch(Exception erer){
                    System.out.println("f");
                    erer.printStackTrace();
                }
//                System.out.println(i);

                break;
            }
        }

//        try{
//            if(){
//
//            }
//        }catch (Exception e){
//
//        }








    }
    public static int q=2;
    public static void lastfram(Arkanoid a) throws FileNotFoundException {
        JFrame frame12=new JFrame();

        JPanel panel2=new JPanel();
        JButton b12=new JButton("Update");
        JButton b22=new JButton("SaveAs");
        TextField txtfld12=new TextField();
        TextField txtfld32=new TextField();
        JLabel label1=new JLabel("GameName");
        JLabel label3=new JLabel("PlayerName");

        frame12.getContentPane();
        JLabel label=new JLabel("Do You Want To Update the game of Save As a NewGame");
        Dimension size=label.getPreferredSize();
        Dimension sizeb1=b12.getPreferredSize();
        Dimension sizeb2=b22.getPreferredSize();
//        Dimension size=new Dimension(90,30);
        label.setBounds(50,50,size.width,size.height);
        b12.setBounds(50,150,100,40);
        b22.setBounds(50,250,100,40);
        txtfld12.setBounds(250,150,100,30);
        txtfld32.setBounds(170,80,100,20);
        label1.setBounds(200,120,size.width,size.height);

        label3.setBounds(70,80,100,size.height);



//        b2.addActionListener(this);

//        label.setBackground(Color.BLACK);
//        label.setOpaque(true);
        panel2.setLayout(null);
        panel2.add(label);
        panel2.add(label1);

        panel2.add(label3);
        panel2.add(txtfld12);

        panel2.add(txtfld32);
        panel2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        frame12.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame12.add(panel2);
        panel2.add(b12);
        panel2.add(b22);
        frame12.setSize(400,500);
        frame12.setVisible(true);
        b12.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(txtfld12.getText()!=null &txtfld32.getText()!=null){
                    encoder(a);

                }

            }
        });
        b22.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(txtfld12.getText()!=null &txtfld32.getText()!=null){

                    a.GameName=txtfld12.getText();
                    a.PlayerName=txtfld32.getText();
                    encoder(a);


                }

            }
        });
    }

@Override
public void keyPressed(KeyEvent event) {

        if(event.getKeyCode()==KeyEvent.VK_UP){
            if(Arkanoid.pause){
                Arkanoid.pause=false;
            }else {
                Arkanoid.pause=true;
            }
        }
    if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
//        lastfram(this);

        running = false;
    }
    if (event.getKeyCode() == KeyEvent.VK_ENTER) {
        tryAgain = true;
    }
    switch (event.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            paddle.moveLeft();
            break;
        case KeyEvent.VK_RIGHT:
            paddle.moveRight();
            break;
        default:
            break;
    }
}

    @Override
    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                paddle.stopMove();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

}
