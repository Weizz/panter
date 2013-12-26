package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Toolbar extends JPanel{
    window parent = null;
    JButton draw_btn = new JButton("Draw");
    JButton undraw_btn = new JButton("UNDraw");
    JButton free_btn = new JButton("FreeDraw");
    JButton create_btn = new JButton("Create");
    JButton erase_btn = new JButton("Eraser");
    
    Toolbar(window p){
        super(new FlowLayout(FlowLayout.LEFT));
        parent = p;
        
        JButton exit_btn = new JButton("Exit");
        JButton choice_btn = new JButton("Color");
        JButton thick_btn = new JButton("Thickness");
        undraw_btn.setEnabled(false);
        
        draw_btn.setBackground(Color.lightGray);
        free_btn.setBackground(Color.lightGray);
        exit_btn.setBackground(Color.lightGray);
        erase_btn.setBackground(Color.lightGray);
        thick_btn.setBackground(Color.lightGray);
        create_btn.setBackground(Color.lightGray);
        undraw_btn.setBackground(Color.lightGray);
        choice_btn.setBackground(Color.lightGray);
        
        exit_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        System.exit(0);
                    }
                }
        );
        
        draw_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        parent.parent.status = Status.drawline;
                        Toolbar.this.draw_btn.setEnabled(false);
                        Toolbar.this.undraw_btn.setEnabled(true);
                        Toolbar.this.free_btn.setEnabled(true);
                        Toolbar.this.create_btn.setEnabled(true);
                        Toolbar.this.erase_btn.setEnabled(true);
                        Toolbar.this.parent.draw_area.lastpoint = new Point(-1, -1);
                    }
                }
        );
        
        undraw_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        parent.parent.status = Status.undraw;
                        Toolbar.this.draw_btn.setEnabled(true);
                        Toolbar.this.free_btn.setEnabled(true);
                        Toolbar.this.undraw_btn.setEnabled(false);
                        Toolbar.this.create_btn.setEnabled(true);
                        Toolbar.this.erase_btn.setEnabled(true);
                    }
                }
        );
        
        
        free_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        parent.parent.status = Status.freedraw;
                        Toolbar.this.draw_btn.setEnabled(true);
                        Toolbar.this.free_btn.setEnabled(false);
                        Toolbar.this.undraw_btn.setEnabled(true);
                        Toolbar.this.create_btn.setEnabled(true);
                        Toolbar.this.erase_btn.setEnabled(true);
                    }
                }
        );
        
        choice_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        Color color = JColorChooser.showDialog(null, "Background Setting", Color.white);
                        parent.draw_area.gcolor = color;
                    }
                }
        );
        
        thick_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        new thickdialog(Toolbar.this);
                    }
                }
        );
        
        erase_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        Toolbar.this.draw_btn.setEnabled(true);
                        Toolbar.this.free_btn.setEnabled(true);
                        Toolbar.this.undraw_btn.setEnabled(true);
                        Toolbar.this.create_btn.setEnabled(true);
                        Toolbar.this.erase_btn.setEnabled(false);
                        parent.parent.status = Status.erase;
                    }
                }
        );
        
        create_btn.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        Toolbar.this.draw_btn.setEnabled(true);
                        Toolbar.this.free_btn.setEnabled(true);
                        Toolbar.this.undraw_btn.setEnabled(true);
                        Toolbar.this.erase_btn.setEnabled(true);
                        Toolbar.this.create_btn.setEnabled(false);
                        parent.parent.status = Status.create;
                    }
                }
        );
        
        
        this.add(draw_btn);
        this.add(free_btn);
        this.add(create_btn);
        this.add(erase_btn);
        this.add(undraw_btn);
        this.add(choice_btn);
        this.add(thick_btn);
        this.add(exit_btn);
        this.setBackground(Color.gray.darker().darker());
    }
}
