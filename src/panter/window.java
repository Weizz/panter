package panter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class window extends JFrame{
    window(String name, Dimension size){
        super(name);
        this.setSize(size);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screen.width/2-500/2, screen.height/2-500/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton exit_btn = new JButton("Exit");
        exit_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        dispose();
                        System.exit(0);
                    }
                }
        );
        JTextArea frm = new JTextArea("123");
        frm.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        PointerInfo pi = MouseInfo.getPointerInfo();
                        Point point = pi.getLocation();
                        System.out.println("x:" + point.x + " | y: "  + point.y);
                    }
                }
        );
        this.getContentPane().add(BorderLayout.CENTER,frm);
        this.getContentPane().add(BorderLayout.SOUTH,exit_btn);
        this.setVisible(true);
    }
}
