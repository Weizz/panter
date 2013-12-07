package panter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;  


public class thickdialog extends JDialog{
    Toolbar parent = null;
    JSlider s = null;
    JPanel fence = new JPanel();
    Canvas preview = new Canvas(){
            public void paint(Graphics g) {
                g.fillOval(preview.getWidth()/2-s.getValue()/2, preview.getHeight()/2-s.getValue()/2, s.getValue(), s.getValue());
            }
        };
    JLabel label =  new JLabel("Size : ", JLabel.LEFT);
    JTextField text = null;
    
    thickdialog(Toolbar p){
        super();
        parent = p;
        
        this.setSize(new Dimension(230,240));
        this.setResizable(false);
        this.setModal(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
        
        s = new JSlider(JSlider.HORIZONTAL, 0, 50, thickdialog.this.parent.parent.draw_area.thick);
        s.setPaintTicks(true);
        s.setMajorTickSpacing(10);
        s.setMinorTickSpacing(1);
        s.setPaintLabels(true);
        TitledBorder titleBorder = new TitledBorder("Preview");
        fence.setBorder(titleBorder);
        preview.setSize(new Dimension(80,80));
        fence.add(BorderLayout.CENTER, preview);
        
        text = new JTextField(Integer.toString(thickdialog.this.parent.parent.draw_area.thick), 3);
        s.addMouseMotionListener(
                new MouseAdapter(){
                    public void mouseDragged(MouseEvent e){
                        text.setText(Integer.toString(s.getValue()));
                        Graphics g = thickdialog.this.preview.getGraphics();
                        g.clearRect(0, 0, thickdialog.this.preview.getWidth(), thickdialog.this.preview.getHeight());
                        g.fillOval(thickdialog.this.preview.getWidth()/2-thickdialog.this.s.getValue()/2, thickdialog.this.preview.getHeight()/2-thickdialog.this.s.getValue()/2, thickdialog.this.s.getValue(), thickdialog.this.s.getValue());
                    }
                }
        );
        s.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        text.setText(Integer.toString(s.getValue()));
                        Graphics g = thickdialog.this.preview.getGraphics();
                        g.clearRect(0, 0, thickdialog.this.preview.getWidth(), thickdialog.this.preview.getHeight());
                        g.fillOval(thickdialog.this.preview.getWidth()/2-thickdialog.this.s.getValue()/2, thickdialog.this.preview.getHeight()/2-thickdialog.this.s.getValue()/2, thickdialog.this.s.getValue(), thickdialog.this.s.getValue());
                    }
                }
        );
        
        
        text.setHorizontalAlignment(JTextField.CENTER);
        text.addKeyListener(
                new KeyAdapter(){
                    public void keyReleased(KeyEvent e){
                        System.out.println(e.getKeyChar());
                        if(isNumeric(thickdialog.this.text.getText()) && !thickdialog.this.text.getText().isEmpty())
                            s.setValue(Integer.parseInt(text.getText()));
                        System.out.println(text.getText());
                        Graphics g = thickdialog.this.preview.getGraphics();
                        g.clearRect(0, 0, thickdialog.this.preview.getWidth(), thickdialog.this.preview.getHeight());
                        g.fillOval(thickdialog.this.preview.getWidth()/2-thickdialog.this.s.getValue()/2, thickdialog.this.preview.getHeight()/2-thickdialog.this.s.getValue()/2, thickdialog.this.s.getValue(), thickdialog.this.s.getValue());
                    }
                }
        );
        
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        ok.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        thickdialog.this.parent.parent.draw_area.thick = thickdialog.this.s.getValue();
                        //System.out.println(thickdialog.this.parent.);
                        thickdialog.this.dispose();
                    }
                }
        );
        cancel.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        thickdialog.this.dispose();
                    }
                }
        );
        
        
        label.setDisplayedMnemonic(KeyEvent.VK_S);
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        jp.add(label);
        jp.add(text);
        jp.add(ok);
        jp.add(cancel);
        
        
        this.add(BorderLayout.PAGE_START, s);
        this.add(BorderLayout.CENTER, fence);
        this.add(BorderLayout.PAGE_END, jp);
        this.setVisible(true);
    }

    public static boolean isNumeric(String str){
        for (char c : str.toCharArray()){
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    
}


