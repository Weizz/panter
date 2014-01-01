package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class resizePanel extends JPanel{
    public final int RESIZE_N = 0, RESIZE_E = 1, RESIZE_S = 2, RESIZE_W = 3,
                     RESIZE_NE = 4, RESIZE_SE = 5, RESIZE_SW = 6, RESIZE_NW = 7;    //用來判斷是哪個方位的框
    final int OVAL_SIZE = 15, RACT_SIZE = 10;   //後面設定大小用
    page parent = null;
    int dir = 0;    //儲存自己是哪個方位的框  ex.這是北(N)邊的框 => dir = diraction = RESIZE_N = 0
    Dimension objSize = null;   //紀錄調指位置前物件的大小
    Point objStart = new Point(-1, -1);      //紀錄調整前物件的位置
    
    resizePanel(page p, int diraction){
        super();
        parent = p;
        dir = diraction;
        
        this.setOpaque(false);  //設成透明,因為框框的圖畫在page上,會被蓋掉
        if(diraction <= 3)  //diraction = 0~3 (N.E.S.W) 設定大小為方形框的大小
            this.setSize(new Dimension(RACT_SIZE, RACT_SIZE));
        else                //diraction = 4~7 (N.E.S.W) 設定大小為圓形框的大小
            this.setSize(new Dimension(OVAL_SIZE, OVAL_SIZE));
        
        if(diraction == RESIZE_N)       //設定擺在哪
            setLocation(parent.selectObj.getLocation().x + parent.selectObj.getWidth()/2 - RACT_SIZE/2, parent.selectObj.getLocation().y - RACT_SIZE/2);
        if(diraction == RESIZE_E)
            setLocation(parent.selectObj.getLocation().x +  parent.selectObj.getWidth() - RACT_SIZE/2, parent.selectObj.getLocation().y +  parent.selectObj.getHeight()/2 - RACT_SIZE/2);
        if(diraction == RESIZE_S)
            setLocation(parent.selectObj.getLocation().x + parent.selectObj.getWidth()/2 - RACT_SIZE/2, parent.selectObj.getLocation().y + parent.selectObj.getHeight() - RACT_SIZE/2);
        if(diraction == RESIZE_W)
            setLocation(parent.selectObj.getLocation().x - RACT_SIZE/2, parent.selectObj.getLocation().y + parent.selectObj.getHeight()/2 - RACT_SIZE/2);
        if(diraction == RESIZE_NE)
            setLocation(parent.selectObj.getLocation().x + parent.selectObj.getWidth() - OVAL_SIZE/2, parent.selectObj.getLocation().y - OVAL_SIZE/2);
        if(diraction == RESIZE_SE)
            setLocation(parent.selectObj.getLocation().x + parent.selectObj.getWidth() - OVAL_SIZE/2, parent.selectObj.getLocation().y + parent.selectObj.getHeight() - OVAL_SIZE/2);
        if(diraction == RESIZE_SW)
            setLocation(parent.selectObj.getLocation().x-OVAL_SIZE/2, parent.selectObj.getLocation().y + parent.selectObj.getHeight()-OVAL_SIZE/2);
        if(diraction == RESIZE_NW)
            setLocation(parent.selectObj.getLocation().x-OVAL_SIZE/2, parent.selectObj.getLocation().y-OVAL_SIZE/2);
        
        addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {        //滑鼠移到框上面時,設定鼠標
                switch(dir){
                    case 0: case 2:     //N跟S鼠標一樣
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        break;
                    case 1: case 3:
                        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        break;
                    case 4: case 6:
                        setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                        break;
                    case 5: case 7:
                        setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        break;
                }
            }
            public void mousePressed(MouseEvent e){
                parent.selectObj.status = Status.resize;    //變更selectObj的狀態為resize,調整時不出現邊框(只有在selected時才會畫出邊框)
                objSize = parent.selectObj.getSize();     //紀錄調指位置前物件的大小
                objStart = parent.selectObj.getLocation();  //紀錄調整前物件的位置
                parent.repaint();
            }
            public void mouseReleased(MouseEvent e){
                parent.selectObj.status = Status.selected;  //狀態改回selected
                objStart = new Point(-1, -1);   //設為-1,-1 , mouseDragged時判斷是否為按住拖移用
                parent.repaint();   //呼叫page重畫
            } 
        });
        
        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){     //各個方位調大小的方法
                if(objStart.x != -1){
                    if(dir == RESIZE_NW){
                        parent.selectObj.setSize(objSize.width - e.getX() + (int)resizePanel.this.getWidth()/2, objSize.height - e.getY() + (int)resizePanel.this.getHeight()/2);
                        parent.selectObj.setLocation(resizePanel.this.getLocation().x + e.getPoint().x, resizePanel.this.getLocation().y + e.getPoint().y);
                    }
                    if(dir == RESIZE_NE){
                        parent.selectObj.setSize(objSize.width + e.getX() - (int)resizePanel.this.getWidth()/2, objSize.height - e.getY() + (int)resizePanel.this.getHeight()/2);
                        parent.selectObj.setLocation(objStart.x, resizePanel.this.getLocation().y + e.getPoint().y);
                    }
                    if(dir == RESIZE_SE){
                        parent.selectObj.setSize(objSize.width + e.getX() - (int)resizePanel.this.getWidth()/2, objSize.height + e.getY() - (int)resizePanel.this.getHeight()/2);
                        parent.selectObj.setLocation(objStart);
                    }
                    if(dir == RESIZE_SW){
                        parent.selectObj.setSize(objSize.width - e.getX() + (int)resizePanel.this.getWidth()/2, objSize.height + e.getY() - (int)resizePanel.this.getHeight()/2);
                        parent.selectObj.setLocation(resizePanel.this.getLocation().x + e.getPoint().x, objStart.y);
                    }
                    if(dir == RESIZE_N){
                        parent.selectObj.setSize(objSize.width, objSize.height - e.getY() - (int)resizePanel.this.getHeight()/2);
                        parent.selectObj.setLocation(objStart.x, resizePanel.this.getLocation().y + e.getPoint().y);
                    }
                    if(dir == RESIZE_E){
                        parent.selectObj.setSize(objSize.width + e.getX() - (int)resizePanel.this.getWidth()/2, objSize.height);
                        parent.selectObj.setLocation(objStart);
                    }
                    if(dir == RESIZE_S){
                        parent.selectObj.setSize(objSize.width, objSize.height + e.getY() - (int)resizePanel.this.getHeight()/2);
                        parent.selectObj.setLocation(objStart);
                    }
                    if(dir == RESIZE_W){
                        parent.selectObj.setSize(objSize.width - e.getX() - (int)resizePanel.this.getWidth()/2, objSize.height);
                        parent.selectObj.setLocation(resizePanel.this.getLocation().x + e.getPoint().x, objStart.y);
                    }
                }
            }
        });
    }
}
