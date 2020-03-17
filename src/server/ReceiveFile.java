package server;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

public class ReceiveFile extends JFrame {
    public static int SEND=0;
    int mode;
    File f;
    ReceiveFile pt;
    InetAddress hostip;
    long filesize;
    DatagramSocket sock;
    JLabel img=new JLabel("",SwingConstants.CENTER);
    JProgressBar jpb=new JProgressBar();
    byte[] b;
    SocketAddress sa;
    public ReceiveFile(int mod, File fi, String ip)
    {
        mode=mod;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pt=this;
        setSize(600,480);
        setLayout(null);
        add(jpb);
        jpb.setBounds(5,5,585,30);
        add(img);
        setResizable(false);
        img.setVerticalTextPosition(SwingConstants.CENTER);
        img.setHorizontalTextPosition(SwingConstants.CENTER);
        img.setBounds(5,40,585,410);
        img.setBorder(new EtchedBorder());
        jpb.setValue(0);
        jpb.setStringPainted(true);
        try{
            if(mod==1)
            {
                hostip=InetAddress.getByName(ip);
                sock=new DatagramSocket();
                img.setText("<HTML><b>Trying to connect to the given IP.</b></HTML>");
            }
            MyThread.start();
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex);System.exit(0);}
    }

    public static void main(String args[])
    {
        File ftemp;
        String ip = "";
        new ReceiveFile(1,null,ip).setVisible(true);
    }

    Thread MyThread=new Thread()
    {
        public void run()
        {
            DatagramPacket p;
            String s="";
            try{
                    s="GETPIC";
                    p=new DatagramPacket(s.getBytes(),s.length(),hostip,33780);
                    sock.send(p);
                    b=new byte[65507];
                    p=new DatagramPacket(b,65507);
                    sock.receive(p);
                    s=new String(p.getData());
                    f=new File("output.jpeg");
                    filesize=Long.parseLong(s.substring(s.indexOf(":")+1,s.indexOf("$$")));
                    int i,l;
                    FileOutputStream fo=new FileOutputStream(f);
                    b=new byte[65507];
                    sock.setSoTimeout(5000);
                    img.setText("Receiving image");
                    long startTime = System.currentTimeMillis(), endTime = 0l;
                    for(i=0;i<filesize;)
                    {
                        b=new byte[65507];
                        p=new DatagramPacket(b,65507);
                        sock.receive(p);
                        if(p.getLength()>0)
                        {
                            i+=p.getLength();
                            jpb.setValue(i*100/(int)filesize);
                            jpb.setString(Integer.toString(jpb.getValue())+" %");
                            fo.write(p.getData());
                            s="ACK";
                            p=new DatagramPacket(s.getBytes(),s.length(),hostip,33780);
                            sock.send(p);
                        }
                    }
                    fo.flush();
                    fo.close();
                    endTime = System.currentTimeMillis();
                    System.out.println("Time in milisec " + (endTime - startTime));
                    ImageIcon im = new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH));
                    img.setIcon(im);
                    img.setText("This is the received image");
            }catch(Exception ex){}
        }
    };
}