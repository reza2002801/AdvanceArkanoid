import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class keeper extends JFrame {
    static boolean b;
//    public static LinkedList<String> userscores;
    public static void main(String[] args) {



    }public keeper() {
        this.b=true;

    }
    public static void updatefile(String playername,int score) throws IOException {
        LinkedList<String> userscores=new LinkedList<>();
        File myObj = new File("Scores.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNext()){
            String s=myReader.next();
            userscores.add(s);
        }
        String f=playername+":"+String.valueOf(score);
        userscores.add(f);
        FileWriter mywriter=new FileWriter("Scores.txt");
        if(userscores.size()!=0){
            for(String s:userscores){
                mywriter.write(s+"\n");
            }
        }

        mywriter.close();

    }
    public static String[] prsnScr() throws FileNotFoundException {
        LinkedList<String> userscores=new LinkedList<>();
        LinkedList<String> userscores2=new LinkedList<>();
        LinkedList<String> userscores3=new LinkedList<>();
        File myObj = new File("Scores.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNext()){
            String s=myReader.next();
            userscores.add(s);
        }
        LinkedList<String> keys=new LinkedList<>();
        for (int i=0;i< userscores.size();i++){
            String[] g=userscores.get(i).split(":");
            String key=g[0];
            int f=0;
            if(!keys.contains(key)){
                keys.add(key);
                for(int j=0;j<userscores.size();j++){
                    String[] sec=userscores.get(j).split(":");
                    if(sec[0].equals(key)){
                        f+=Integer.parseInt(sec[1]);
                    }
                }
                userscores2.add(g[0]+":"+String.valueOf(f));
            }


        }
        HashSet<String> d=new HashSet<>();
        for (String s:userscores2){
            d.add(s);
        }
        String[] str2=new String[d.size()];
        str2=d.toArray(new String[d.size()]);
        for (int i=0;i<str2.length;i++){
            userscores3.add(str2[i]);
        }
        return str2;
    }

    public static void keeper1 (Arkanoid a) {
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
                    Arkanoid.encoder(a);
                    keeper.b=false;

                }

            }
        });
        b22.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(txtfld12.getText()!=null &txtfld32.getText()!=null){

                    a.GameName=txtfld12.getText();
                    a.PlayerName=txtfld32.getText();
                    Arkanoid.encoder(a);
                    keeper.b=false;


                }

            }
        });
    }
}
