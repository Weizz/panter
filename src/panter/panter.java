package panter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class panter {
    enum Status {drawline, freedraw, undraw}
    Status status = Status.undraw;
    BufferedImage image = null;
    
    panter(){
        window newwindow = new window("Panter Beta", new Dimension(500, 500), this);
        }
}
