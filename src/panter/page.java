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
    ArrayList<PObject> objects = new ArrayList<PObject>();
    PObject selectObj = null;

    resizePanel n = null, e = null, s = null, w = null,
                ne = null, se = null, sw = null, nw = null;
    
    page(window p){
        super();
        parent = p;
        
        this.setLayout(null);
        
        this.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        if(parent.parent.status == Status.drawline){
                            if(lastpoint.x != (-1)){    //有前一個點
                                System.out.println(e.getX() + ":" + e.getY());
                                Graphics g = page.this.getGraphics();
                                Graphics2D g2 = (Graphics2D)g;
                                g2.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));   //java.awt.BasicStroke
                                g2.setColor(gcolor);
                                g2.drawLine(lastpoint.x, lastpoint.y, e.getX(), e.getY());
                                lines.add(new Line(lastpoint, e.getPoint(), thick, gcolor, 0));
                            }
                            lastpoint = e.getPoint();   //
                        }
                        if(page.this.selectObj != null){        //在目前有選擇中物件的狀態下,按下頁面,變為沒有選擇中物件
                            page.this.selectObj.status = Status.unselect;
                            page.this.selectObj = null;
                            repaint();
                        }
                    }
                    
                    public void mousePressed(MouseEvent e){
                        if(parent.parent.status == Status.freedraw){
                            lastpoint = e.getPoint();
                            Graphics g = page.this.getGraphics();
                            Graphics2D g2 = (Graphics2D)g;
                            g2.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));
                            g2.setColor(gcolor);
                            g2.drawLine(lastpoint.x, lastpoint.y, e.getPoint().x, e.getPoint().y);  //畫出按第一下的點
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
                            PRect rect = null;
                            if(lastpoint.x < e.getX() && lastpoint.y < e.getY()){
                                page.this.add(rect = new PRect(page.this, lastpoint, new Dimension(e.getX()-lastpoint.x, e.getY()-lastpoint.y)));
                            }
                            else if(lastpoint.y < e.getY()){
                                page.this.add(rect = new PRect(page.this, new Point(e.getX(), lastpoint.y), new Dimension(lastpoint.x-e.getX(), e.getY()-lastpoint.y)));                                
                            }
                            else if(lastpoint.x < e.getX()){
                                page.this.add(rect = new PRect(page.this, new Point(lastpoint.x, e.getY()), new Dimension(e.getX()-lastpoint.x, lastpoint.y-e.getY())));
                            }
                            else{
                                page.this.add(rect = new PRect(page.this, e.getPoint(), new Dimension(lastpoint.x-e.getX(), lastpoint.y-e.getY())));
                            }
                            
                            objects.add(rect);
                            if(selectObj != null)   //將上一個選擇中的物件狀態設為unselect
                                selectObj.status = Status.unselect;
                            selectObj = rect;
                            rect.status = Status.selected;
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
        
        for(Line l:lines){              //走訪ArrayList<lines>
            if(l.getType() == 1){   //type=1 : 做橡皮擦動作
                g2.clearRect(l.getB().x-l.getThick()/2, l.getB().y-l.getThick()/2, l.getThick(), l.getThick());
            }
            else{
                g2.setStroke(new BasicStroke(l.getThick(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30));
                g2.setColor(l.getColor());
                g2.drawLine(l.getA().x, l.getA().y, l.getB().x, l.getB().y);
            }
        }
        
        if(parent.parent.status == Status.create && lastpoint.x!=-1){   //如果正在拉物件(有開始位置(lastpoint!=-1)),描出預覽圖
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5, new float[] {2, 6}, 5));   //設置虛線，new float[] {實線長度,虛線長度}
                drawCreate(lastpoint, nowpoint, g2);    //自己寫的副程式,把起始點.現在位置丟進去,畫出矩形
        } 
        
        if(selectObj != null && selectObj.status == Status.selected){   //目前有正被選擇的物件時，在四周繪製外框
            g2.setColor(Color.blue);                                  //selectObj.status == Status.selected 在物件move的時候不顯示外框 (物件move時selectObj還是會指向它)
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5, new float[] {10, 7}, 5));  //設置虛線，new float[] {實線長度,虛線長度}
            g2.drawRect(selectObj.getLocation().x, selectObj.getLocation().y, selectObj.getWidth(), selectObj.getHeight()); //在物件四周畫出框
            
            drawResizePanel(g); //副程式,用來劃出調大小的框框、加入reSizePanel
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
    
    private void drawResizePanel(Graphics g){    
        final int RESIZE_N = 0, RESIZE_E = 1, RESIZE_S = 2, RESIZE_W = 3,
                  RESIZE_NE = 4, RESIZE_SE = 5, RESIZE_SW = 6, RESIZE_NW = 7;   //用來傳進resizePanel,方便閱讀用
        if(n == null){  //第一次創出物件時,把條大小框框new出來
            n = new resizePanel(this, RESIZE_N); e = new resizePanel(this, RESIZE_E); s = new resizePanel(this, RESIZE_S); w = new resizePanel(this, RESIZE_W); //new出resizePanel
            ne = new resizePanel(this, RESIZE_NE); se = new resizePanel(this, RESIZE_SE); sw = new resizePanel(this, RESIZE_SW); nw = new resizePanel(this, RESIZE_NW);
        }else{          //選到其他物件時,把框框移到他那
            n.reLocate(); e.reLocate(); s.reLocate(); w.reLocate(); 
            ne.reLocate(); se.reLocate(); sw.reLocate(); nw.reLocate();
        }
        final int OVAL_SIZE = 15, RACT_SIZE = 10;   //後面畫出圓形、方形格子的大小設定
                
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 30));
        g.setColor(Color.white);
        g.fillOval(nw.getX(), nw.getY(), OVAL_SIZE, OVAL_SIZE);         //畫出調大小框框 ...
        g.fillOval(ne.getX(), ne.getY(), OVAL_SIZE, OVAL_SIZE);
        g.fillOval(sw.getX(), sw.getY(), OVAL_SIZE, OVAL_SIZE);
        g.fillOval(se.getX(), se.getY(), OVAL_SIZE, OVAL_SIZE);
        g.fillRect(n.getX(), n.getY(), RACT_SIZE, RACT_SIZE);
        g.fillRect(w.getX(), w.getY(), RACT_SIZE, RACT_SIZE);
        g.fillRect(e.getX(), e.getY(), RACT_SIZE, RACT_SIZE);
        g.fillRect(s.getX(), s.getY(), RACT_SIZE, RACT_SIZE);
        g.setColor(Color.BLACK);
        g.drawOval(nw.getX(), nw.getY(), OVAL_SIZE, OVAL_SIZE);
        g.drawOval(ne.getX(), ne.getY(), OVAL_SIZE, OVAL_SIZE);
        g.drawOval(sw.getX(), sw.getY(), OVAL_SIZE, OVAL_SIZE);
        g.drawOval(se.getX(), se.getY(), OVAL_SIZE, OVAL_SIZE);
        g.drawRect(n.getX(), n.getY(), RACT_SIZE, RACT_SIZE);
        g.drawRect(w.getX(), w.getY(), RACT_SIZE, RACT_SIZE);
        g.drawRect(e.getX(), e.getY(), RACT_SIZE, RACT_SIZE);
        g.drawRect(s.getX(), s.getY(), RACT_SIZE, RACT_SIZE);           //...。
        
        this.add(nw);   //把resizePanel加進來
        this.add(ne);
        this.add(sw);
        this.add(se);
        this.add(n);
        this.add(w);
        this.add(e);
        this.add(s);        
    }
}
