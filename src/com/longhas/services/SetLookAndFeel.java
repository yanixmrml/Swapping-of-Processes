/*
 * MemorySimulationFrame.java
 * Author : LONGHAS, MARK RYAN M.
 * Created on Oct 3, 2010, 10:33:35 PM
 */


package com.longhas.services;

import com.longhas.view.MemorySimulationFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SetLookAndFeel {
    private static UIManager.LookAndFeelInfo looks[];
   
    public static void setLookandFeel(){
        
        looks = UIManager.getInstalledLookAndFeels();
        
        int value=0;
        
        for(int index=0; index<looks.length;index++){
            System.out.println(looks[index].getName());
            if(looks[index].getName().equals("Motif")){value=index;}
        }
       
        try {
            UIManager.setLookAndFeel(looks[value].getClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemorySimulationFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MemorySimulationFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MemorySimulationFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MemorySimulationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
    }
    
}
