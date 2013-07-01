/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.longhas.services;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class LookAndFeelModel
{


    public static void setLookAndFeelModel()
    {

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();

        try
        {
            UIManager.setLookAndFeel(lookAndFeel);
        }
        catch (UnsupportedLookAndFeelException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }

    }

}
