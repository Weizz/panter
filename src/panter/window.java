package panter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class window extends JFrame{
    panter parent = null;
    page draw_area = null;
    
    window(String name, Dimension size, panter p){
        super(name);
        parent = p;        
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(size);
        this.setLocation(screen.width/2-800/2, screen.height/2-500/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        Menu menu = new Menu(this);
        Toolbar toolbar = new Toolbar(this);
        draw_area = new page(this);
        
        this.getContentPane().add(BorderLayout.PAGE_START, toolbar);
        this.getContentPane().add(BorderLayout.CENTER, draw_area);
        this.getContentPane().add(BorderLayout.PAGE_END, menu);
        this.setVisible(true);
    }
}
