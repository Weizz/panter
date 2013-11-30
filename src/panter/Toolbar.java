package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Toolbar extends JPanel{
    window parent = null;
    JButton draw_btn = new JButton("Draw");
    JButton undraw_btn = new JButton("UNDraw");
    Toolbar(window p){
        super(new FlowLayout(FlowLayout.LEFT));
        parent = p;
        
        JButton exit_btn = new JButton("Exit");
        
        undraw_btn.setEnabled(false);
        
        exit_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        System.exit(0);
                        //dispose();
                    }
                }
        );
        
        draw_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        parent.parent.draw_flag=true;
                        Toolbar.this.draw_btn.setEnabled(false);
                        Toolbar.this.undraw_btn.setEnabled(true);
                        System.out.println(parent.parent.draw_flag);
                    }
                }
        );
        
        undraw_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        parent.parent.draw_flag=false;
                        Toolbar.this.draw_btn.setEnabled(true);
                        Toolbar.this.undraw_btn.setEnabled(false);
                        System.out.println(parent.parent.draw_flag);
                    }
                }
        );
        
        this.add(draw_btn);
        this.add(undraw_btn);
        this.add(exit_btn);
        this.setBackground(Color.lightGray);
    }
}
