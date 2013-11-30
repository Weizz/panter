package panter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class window extends JFrame{
    panter parent = null;
    window(String name, Dimension size, panter p){
        super(name);
        parent = p;
        this.setSize(size);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screen.width/2-500/2, screen.height/2-500/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton exit_btn = new JButton("Exit");
        exit_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        parent.speak();
                        //dispose();
                        //System.exit(0);
                    }
                }
        );
        JTextArea frm = new JTextArea("123");
        frm.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        System.out.println("x:" + e.getX() + " | y: "  + e.getY());
                    }
                }
        );
        this.getContentPane().add(BorderLayout.CENTER,frm);
        this.getContentPane().add(BorderLayout.SOUTH,exit_btn);
        this.setVisible(true);
    }
}
