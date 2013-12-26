package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class PRect extends PObject{
    page parent = null;
    Status status = null;
        
    public PRect(page p, Point place, Dimension size) {
        super(p);
        parent = p;
        
        status = Status.selected;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLocation(place);
        this.setSize(size);
        
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                PRect.this.status = Status.selected;
                PRect.this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
            }
        });
    }
    
    public void paintComponent(Graphics g){
        g.drawOval(1, 1, this.getWidth()-3, this.getHeight()-3);
    }
}
