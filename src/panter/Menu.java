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
        JMenuItem save = new JMenuItem("Save as .txt");
        JMenuItem read = new JMenuItem("Read .txt");
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
        
        save.addMouseListener(
                new MouseAdapter(){
                    public void mousePressed(MouseEvent e){
                           String all = "";
                           for(Line l : Menu.this.parent.draw_area.lines){
                               all += l.toString() + "\n";
                           }
                           File file = new File("123.txt");
                            try {
                                file.createNewFile();
                                FileWriter fw = new FileWriter(file);
                                fw.write(all);
                                System.out.println("Bulid Success");
                                fw.close();
                            } catch (IOException ex) {}
                    }
                }
        );
        
        read.addMouseListener(
                new MouseAdapter(){
                    public void mousePressed(MouseEvent e){
                        try{
                            BufferedReader reader = new BufferedReader(new FileReader("123.txt"));
                            String data;
                            while ((data = reader.readLine()) != null){
                                Line l = new Line(data);
                                Menu.this.parent.draw_area.lines.add(l);
                            }
                            reader.close();
                        }
                        catch (IOException ex){}
                        Menu.this.parent.draw_area.repaint();
                    }
                }
        );
        
        file.add(open);
        file.add(save);
        file.add(read);
        file.addSeparator();
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
