package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.io.File.*;
import java.awt.image.*;
import javax.imageio.*;

public class Menu extends JMenuBar{
    window parent = null;
    
    Menu(window p){
        super();
        parent = p;
        
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem one = new JMenuItem("ONE");
        JMenuItem two = new JMenuItem("TWO");
        JMenuItem three = new JMenuItem("THREE");
        
        open.addMouseListener(
                new MouseAdapter(){
                    public void mousePressed(MouseEvent e){
                        JFileChooser chooser = new JFileChooser();
                        FileFilterTest filter=new FileFilterTest();
                        chooser.setFileFilter(filter);
                        int returnval = chooser.showDialog(Menu.this.parent, "開啟");
                        if (returnval == JFileChooser.APPROVE_OPTION) {
                            System.out.println(chooser.getSelectedFile().getPath());
                            Menu.this.parent.parent.image = LoadImage(chooser.getSelectedFile().getPath());
                            Menu.this.parent.draw_area.getGraphics().drawImage(Menu.this.parent.parent.image, 0, 0, null);
                        }
                    }
                }
        );
        
        file.add(open);
        file.addSeparator();
        file.add(one);
        file.add(two);
        file.add(three);
        
        this.setBackground(Color.lightGray);
        this.add(file);
    }
    
    public class FileFilterTest extends javax.swing.filechooser.FileFilter{
        public boolean accept(java.io.File f) {
            if (f.isDirectory())return true;
                return f.getName().endsWith(".bmp");  //filter file end with .bmp 
        } 
        public String getDescription(){
            return "點陣圖檔案 (*.bmp)";
        }
    }
    
    public static BufferedImage LoadImage(String Filename){ 
        BufferedImage image; 
        try{ 
            image=ImageIO.read(new File(Filename)); 
        }catch(Exception e){ 
            javax.swing.JOptionPane.showMessageDialog(null, 
                "載入圖檔錯誤: "+Filename); 
            image=null; 
        } 
        return image; 
    }
}
