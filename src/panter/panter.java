package panter;

import java.awt.*;
import javax.swing.*;

public class panter {
    panter(){
        window newwindow = new window("Panter Beta", new Dimension(500,500),this);
        }
    public void speak(){
        System.out.printf("panter speak\n");
    }
}
