package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import panter.panter.Status;
import java.util.*;

public class page extends JPanel{
    window parent = null;
    Point lastpoint = new Point(-1, -1);
    Color gcolor = Color.BLUE;
    int thick = 5;
    ArrayList<Line> lines = new ArrayList<Line>();
    
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
                                lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, false));
                            }
                            lastpoint = e.getPoint();
                        }
                    }
                    
                    public void mousePressed(MouseEvent e){
                        if(parent.parent.status == Status.freedraw){
                            lastpoint = e.getPoint();
                            lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, false));
                        }
                        if(parent.parent.status == Status.erase){
                            Graphics g = page.this.getGraphics();
                            g.clearRect(e.getX()-thick/2, e.getY()-thick/2, thick, thick);
                            lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, true));
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
                                Graphics2D g2 = (Graphics2D)g;
                                g2.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));
                                g2.setColor(gcolor);
                                g2.drawLine(lastpoint.x, lastpoint.y, e.getX(), e.getY());
                                lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, false));
                                lastpoint = e.getPoint();
                            }
                        }
                        if(parent.parent.status == Status.erase){
                            Graphics g = page.this.getGraphics();
                            g.clearRect(e.getX()-thick/2, e.getY()-thick/2, thick, thick);
                            lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, true));
                        }
                    }
                }
        );
    }
    
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g.drawImage(page.this.parent.parent.image, 0, 0, this);
        for(Line l:lines){
            if(l.isErase()){
                g2.clearRect(l.getB().x, l.getB().y, thick, thick);
            }
            else{
                g2.setStroke(new BasicStroke(l.getThick(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));
                g2.setColor(l.getColor());
                g2.drawLine(l.getA().x, l.getA().y, l.getB().x, l.getB().y);
            }
        }
    }
}
