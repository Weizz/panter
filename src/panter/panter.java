package panter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class panter {
    Status status = Status.undraw;
    BufferedImage image = null;
    
    panter(){
        window newwindow = new window("Panter V1", new Dimension(800, 500), this);
        }
}
