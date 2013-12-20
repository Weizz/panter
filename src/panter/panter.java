package panter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class panter {
    enum Status {drawline, freedraw, undraw, erase, create}
    Status status = Status.undraw;
    BufferedImage image = null;
    
    panter(){
        window newwindow = new window("Panter Beta", new Dimension(800, 500), this);
        }
}
