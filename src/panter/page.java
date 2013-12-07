package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import panter.panter.Status;

public class page extends JPanel{
    window parent = null;
    Point lastpoint = new Point(-1, -1);
    Color gcolor = Color.BLUE;
    int thick = 5;
    page(window p){
        super();
        parent = p;
        this.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        if(parent.parent.status == Status.drawline){
                            if(lastpoint.x != (-1)){
                                System.out.println(e.getX() + ":" + e.getY());
                                Graphics g = page.this.getGraphics();
                                Graphics2D g2 = (Graphics2D)g;
                                g2.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));   //java.awt.BasicStroke
                                g2.setColor(gcolor);
                                g2.drawLine(lastpoint.x, lastpoint.y, e.getX(), e.getY());
                            }
                            lastpoint = e.getPoint();
                        }
                    }
                    
                    public void mousePressed(MouseEvent e){
                        if(parent.parent.status == Status.freedraw){
                            lastpoint = e.getPoint();
                        }
                    }
                }
        );
        
        this.addMouseMotionListener(
                new MouseAdapter(){
                    public void mouseDragged(MouseEvent e){
                        if(parent.parent.status == Status.freedraw){
                            if(lastpoint.x != -1){
                                Graphics g = page.this.getGraphics();
                                g = page.this.getGraphics();
                                Graphics2D g2 = (Graphics2D)g;
                                g2.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));
                                g2.setColor(gcolor);
                                g2.drawLine(lastpoint.x, lastpoint.y, e.getX(), e.getY());
                                lastpoint = e.getPoint();
                            }
                        }
                    }
                }
        );
    }
}
