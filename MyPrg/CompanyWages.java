package MyPrg;

import javax.swing.JOptionPane;
import technopolis.action.SuperAction;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ShOleg
 */
public class CompanyWages extends SuperAction{

    @Override
    public boolean runAction() {
        JOptionPane.showMessageDialog(null, "Здесь могут выполняться ваши самостоятельные классы отчётов");
        return true;
    }
    
}
