package panter;

import java.awt.*;

public class Line {
    private Point a, b;
    private int thick;
    private Color color;
    private boolean erase;
    
    Line(Point a, Point b, int thick, Color color, boolean erase){
        this.a = a;
        this.b = b;
        this.thick = thick;
        this.color = color;
        this.erase = erase;
    }
    
    public Point getA(){
        return a;
    }
    
    public Point getB(){
        return b;
    }
    
    public int getThick(){
        return thick;
    }
    
    public Color getColor(){
        return color;
    }
    
    public boolean isErase(){
        return erase;
    }
    
}
