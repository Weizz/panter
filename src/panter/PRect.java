package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class PRect extends PObject{
    page parent = null;
        
    public PRect(page p, Point place, Dimension size) {
        super(p);
        parent = p;
        
        status = Status.selected;
        this.setLocation(place);
        this.setSize(size);
        
        
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 30));
        g2.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
    }
}
