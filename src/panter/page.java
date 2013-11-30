package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class page extends JPanel{
    window parent = null;
    
    page(window p){
        super();
        parent = p;
        
        this.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        if(parent.parent.draw_flag){
                            System.out.println(e.getX() + ":" + e.getY());
                            int x = e.getX(), y = e.getY();
                            Graphics g = page.this.getGraphics();
                            g.setColor(Color.blue);
                            g.drawLine(0, 0, x, y);
                            //g.draw3DRect(0, 0, x, y, true);
                        }
                    }
                }
        );
    }
}
