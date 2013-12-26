package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class page extends JPanel{
    window parent = null;
    Point lastpoint = new Point(-1, -1);
    Point nowpoint = new Point(-1, -1);
    Color gcolor = Color.BLUE;
    int thick = 5;
    ArrayList<Line> lines = new ArrayList<Line>();
    
    page(window p){
        super();
        parent = p;
        
        this.setLayout(null);
        
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
                                lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, 0));
                            }
                            lastpoint = e.getPoint();
                        }
                    }
                    
                    public void mousePressed(MouseEvent e){
                        if(parent.parent.status == Status.freedraw){
                            lastpoint = e.getPoint();
                            lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, 0));
                        }
                        if(parent.parent.status == Status.erase){
                            Graphics g = page.this.getGraphics();
                            g.clearRect(e.getX()-thick/2, e.getY()-thick/2, thick, thick);
                            lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, 1));
                        }
                        if(parent.parent.status == Status.create){
                            lastpoint = e.getPoint();
                        }
                    }
                    
                    public void mouseReleased(MouseEvent e){
                        if(parent.parent.status == Status.create){
                            if(lastpoint.x < e.getX() && lastpoint.y < e.getY()){
                                page.this.add(new PRect(page.this, lastpoint, new Dimension(e.getX()-lastpoint.x, e.getY()-lastpoint.y)));
                            }
                            else if(lastpoint.y < e.getY()){
                                page.this.add(new PRect(page.this, new Point(e.getX(), lastpoint.y), new Dimension(lastpoint.x-e.getX(), e.getY()-lastpoint.y)));
                                System.out.println(new Dimension(e.getX()-lastpoint.x, e.getY()-lastpoint.y));
                                System.out.println(e.getY() + " - " + lastpoint.y);
                            }
                            else if(lastpoint.x < e.getX()){
                                page.this.add(new PRect(page.this, new Point(lastpoint.x, e.getY()), new Dimension(e.getX()-lastpoint.x, lastpoint.y-e.getY())));
                            }
                            else{
                                page.this.add(new PRect(page.this, e.getPoint(), new Dimension(lastpoint.x-e.getX(), lastpoint.y-e.getY())));
                            }
                            
                            lastpoint = new Point(-1, -1);
                            repaint();
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
                                lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, 0));
                                lastpoint = e.getPoint();
                            }
                        }
                        if(parent.parent.status == Status.erase){
                            Graphics g = page.this.getGraphics();
                            g.clearRect(e.getX()-thick/2, e.getY()-thick/2, thick, thick);
                            lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, 1));
                        }
                        if(parent.parent.status == Status.create){
                            nowpoint = e.getPoint();
                            repaint();
                        }
                    }
                }
        );        
    }
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g.drawImage(page.this.parent.parent.image, 0, 0, this);
        
            
        for(Line l:lines){
            if(l.getType() == 1){
                g2.clearRect(l.getB().x-l.getThick()/2, l.getB().y-l.getThick()/2, l.getThick(), l.getThick());
            }
            else{
                g2.setStroke(new BasicStroke(l.getThick(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));
                g2.setColor(l.getColor());
                g2.drawLine(l.getA().x, l.getA().y, l.getB().x, l.getB().y);
            }
        }
        
        
        if(parent.parent.status == Status.create && lastpoint.x!=-1){
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5, new float[] {2, 6}, 5));
                drawCreate(lastpoint, nowpoint, g2);
        } 
    }
    
    private void drawCreate(Point start, Point end, Graphics2D g2){
        if(end.x<start.x && end.y<start.y){
                g2.drawRect(end.x, end.y, start.x-end.x, start.y-end.y);
            }
            else if(end.x<start.x){
                g2.drawRect(end.x, start.y, start.x-end.x, end.y-start.y);
            }
            else if(end.y<start.y){
                g2.drawRect(start.x, end.y, end.x-start.x, start.y-end.y);
            }
            else{
                g2.drawRect(start.x, start.y, end.x-start.x, end.y-start.y);
            }
    }
}
