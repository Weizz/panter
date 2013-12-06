package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import panter.panter.Status;

public class Toolbar extends JPanel{
    window parent = null;
    JButton draw_btn = new JButton("Draw");
    JButton undraw_btn = new JButton("UNDraw");
    Toolbar(window p){
        super(new FlowLayout(FlowLayout.LEFT));
        parent = p;
        
        JButton exit_btn = new JButton("Exit");
        JButton choice_btn = new JButton("Choice");
        
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
                        parent.parent.status = Status.drawline;
                        Toolbar.this.draw_btn.setEnabled(false);
                        Toolbar.this.undraw_btn.setEnabled(true);
                        Toolbar.this.parent.draw_area.lastpoint = new Point(-1, -1);
                    }
                }
        );
        
        undraw_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        parent.parent.status = Status.undraw;
                        Toolbar.this.draw_btn.setEnabled(true);
                        Toolbar.this.undraw_btn.setEnabled(false);
                    }
                }
        );
        
        this.add(draw_btn);
        this.add(undraw_btn);
        this.add(choice_btn);
        this.add(exit_btn);
        this.setBackground(Color.lightGray);
    }
}
