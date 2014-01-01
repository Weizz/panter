package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PObject extends JComponent{
    page parent = null;
    Status status = null;
    Point pressPoint = null, startLocation = null;  //pressPoint: 紀錄滑鼠按下的點用, startLocation: 物件開始移動前的位置
    
    PObject(page p){
        super();
        parent = p;
        
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){        //當物件被按下,將上一個選中的物件狀態設成unselect,selectObj指向自己
                if(parent.selectObj != null)              
                    parent.selectObj.status = Status.unselect;  
                parent.selectObj = PObject.this;
                parent.repaint();   //呼叫page重畫,刷新被選中的物件外框
                PObject.this.status = Status.selected;
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));  //設游標為十字游標(移動游標)
            }
            
            public void mousePressed(MouseEvent e){         //如果該物件再被選重的狀態,記錄下滑鼠按下的點,狀態變為move
                if(PObject.this.status == Status.selected){
                    pressPoint = e.getLocationOnScreen();   //紀錄滑鼠按下的點,以後計算位移用
                    startLocation = PObject.this.getLocation();     //紀錄該物件原本的座標,以後加上位移即為移動後的座標
                    PObject.this.status = Status.move;
                }
            }
            
            public void mouseReleased(MouseEvent e){
                if(PObject.this.status == Status.move){ //移動完放開後,狀態變回selected,呼叫page的repaint畫出邊框
                    PObject.this.status = Status.selected;
                    parent.repaint();
                }
            }
            
            public void mouseEntered(MouseEvent e) {    //如果為正被選擇物件,滑鼠移入時游標變為十字
                if(PObject.this.status == Status.selected)
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
            
            public void mouseExited(MouseEvent e) {     //游標離開改回預設游標
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
                
        this.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){
                if(PObject.this.status == Status.move){
                    PObject.this.setLocation(startLocation.x + e.getLocationOnScreen().x - pressPoint.x,    //設物件位置為 : (物件一開始的位置) + (滑鼠現在位置 - 滑鼠按下位置)
                                             startLocation.y + e.getLocationOnScreen().y - pressPoint.y);   //                                           ^滑鼠位移
                parent.repaint();   //呼叫page重畫,刷新頁面(物件移動後他移動前畫出的圖形還會在,呼叫repaint把它清掉)
                }
            }
        });
        
    }
}
