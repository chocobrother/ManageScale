/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uclab.ui;


import javax.swing.JFrame;
import managescale.LoginFrame;

/**
 *
 * @author train
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    static void initComponent(){   
        
//        JFrame window = new MainJFrame();
////        window.repaint();
//        window.setVisible(true);           
//        MainJFrame frame = new MainJFrame();
                JFrame frame = new LoginFrame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here        
        initComponent();
    }    
}
