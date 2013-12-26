package panter;

import java.awt.*;
import javax.swing.*;

public class PObject extends JComponent{
    page parent = null;
    
    PObject(page p){
        super();
        parent = p;
    }
}
