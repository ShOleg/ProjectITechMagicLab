/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyPrg;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import technopolis.Designer.TableControl.MultiTable.DateFieldReg;
import technopolis.Designer.администратор.Profile;
import technopolis.action.SuperAction;
import technopolis.constant.iniFile.IniConst;
import technopolis.constant.iniFile.IniSettings;
import technopolis.swing.ProgressBarFrame;

/**
 *
 * @author ShOleg
 */
public class ConvertProf470 extends SuperAction {

    @Override
    public boolean runAction() {

        final ProgressBarFrame p = new ProgressBarFrame();
        p.setIndeterminate(true);
        p.setTitle("конвертация пакета 470");

        technopolis.swing.SwingWorker worker = new technopolis.swing.SwingWorker() {

            @Override
            public Object construct() {
                p.show();
                return executePrf();
            }

            @Override
            public void finished() {
                p.dispose();
                if (!"Ok".equals(getValue())) {
                    JOptionPane.showInternalMessageDialog(technopolis.MainFrame.getDeskTop(), getValue(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.start();

        return true;
    }

    private String executePrf() {
        HashMap hAdmOld = new HashMap();

        File[] listOld = new java.io.File(IniSettings.get(IniConst.DATA_SERVER) + "/User/").listFiles();

        for (File file : listOld) {
            String xnameRole = file.getName();

            if (xnameRole.endsWith(".prf")) {

                System.out.println(xnameRole);

                Profile prf = new Profile(xnameRole);
                prf.CreateCatalog();
                prf.GetFromServer();
                prf.getValues();

                Set set = prf.gethAdm().entrySet();
                for (Iterator iter = set.iterator(); iter.hasNext();) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String xkey = (String) entry.getKey();

                    if (xkey.startsWith("Регистрация.") && xkey.endsWith("Доступ")) {
                        if (entry.getValue() instanceof DateFieldReg) {
                            hAdmOld.put(xkey, entry.getValue());
                            System.out.println(xkey + " - " + ((DateFieldReg) entry.getValue()).getData());
                        }
                    }
                }

                if (hAdmOld.isEmpty()) {
                    System.out.println(" не обработана профиль " + xnameRole);
                }

                //   System.out.println("======================================================================================");
                Set setAdd = hAdmOld.entrySet();
                for (Iterator iter = setAdd.iterator(); iter.hasNext();) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String xkey = (String) entry.getKey();

                    if (entry.getValue() instanceof DateFieldReg) {

                        DateFieldReg xValue = (DateFieldReg) entry.getValue();

                        String nameObject = xkey.replace(".Регистрация", "");

                        if (xValue.getData().equals("Всегда")) {
                            prf.gethAdm().put(nameObject, true);
                        } else {
                            prf.gethAdm().put(nameObject, false);
                        }

                        if (xValue.getData().equals("В срок")) {
                            prf.gethAdm().put(nameObject, true);

                            nameObject = xkey.replace(".Доступ", "");
                            prf.gethAdm().put(nameObject + ".Параметр", "%AppServer%\\Revelation\\Ingress\\РегистрацияДокументаВсрок.js");
                        }
                    }
                }

                System.out.println(xnameRole);

                if (!hAdmOld.isEmpty()) {
                    prf.saveModificaz(prf.gethAdm());
                    prf.PutToServer();
                    prf.eraseCatalog();
                } else {
                    prf.eraseCatalog();
                }

            }
        }

        return "Ok";

    }
}
