package panter;

import java.awt.*;

public class Line {
    private Point a, b;
    private int thick;
    private Color color;
    private int type;       // 0 for null , 1 for Eraser , 2 for Create
    
    Line(Point a, Point b, int thick, Color color, int type){
        this.a = a;
        this.b = b;
        this.thick = thick;
        this.color = color;
        this.type = type;
    }
    
    Line(String file){
        String[] data = file.split(";");
        a = new Point(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        b = new Point(Integer.parseInt(data[2]), Integer.parseInt(data[3]));
        color = new Color(Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]));
        thick = Integer.parseInt(data[7]);
        type = Integer.parseInt(data[8]);
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
    
    public int getType(){
        return type;
    }
    
    public String toString(){
        String s;
        s = String.valueOf(a.x) + ";" + String.valueOf(a.y) + ";" + String.valueOf(b.x) + ";" + String.valueOf(b.y) + ";" + String.valueOf(color.getRed()) + ";" + String.valueOf(color.getGreen()) + ";" + String.valueOf(color.getBlue()) + ";" + String.valueOf(thick) + ";" + String.valueOf(type);
        return s;
    }
}
