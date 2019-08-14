/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyPrg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import technopolis.MainFrame;
import technopolis.action.SuperAction;
import technopolis.swing.ProgressBarFrame;
import technopolis.util.Util;
import technopolis.модель.Пользователь;

/**
 *
 * @author ShOleg
 */
public class ПереносДанныхМеркурий extends SuperAction {

    @Override
    public boolean runAction() {
        final ProgressBarFrame p = new ProgressBarFrame();
        p.setIndeterminate(true);
        p.setTitle(getDealerAction().getCaption());

        technopolis.swing.SwingWorker worker = new technopolis.swing.SwingWorker() {

            @Override
            public Object construct() {
                p.show();
                return doWork();
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

    private String doWork() {
        try {
            createNachUd();

            createTabel();

        } catch (SQLException ex) {
            return "Error";
        }

        return "Ok";
    }

    private String createNachUd() throws SQLException {
        Calendar cal1 = new GregorianCalendar(2012, 3, 1);
        java.sql.Date date1 = new java.sql.Date(cal1.getTimeInMillis());

        Calendar cal2 = new GregorianCalendar(2013, 2, 31);
        java.sql.Date date2 = new java.sql.Date(cal2.getTimeInMillis());

        PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement(ПереносДанныхУниверсал.getSQL());

        pst.setString(1, "ООО Меркурий");
        pst.setDate(2, date2);
        pst.setDate(3, date1);

        pst.setString(4, "ООО Меркурий");
        pst.setDate(5, date2);
        pst.setDate(6, date1);

        pst.setString(7, "ООО Меркурий");
        pst.setDate(8, date2);
        pst.setDate(9, date1);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            final long codeDoc = Util.getUnicumID();
            insertDR(codeDoc);

            do {

                insertSostav(codeDoc, rs);

            } while (rs.next());
        }

        rs.close();
        pst.close();

        return "Ok";
    }
    
    private String createTabel() throws SQLException {
        Calendar cal1 = new GregorianCalendar(2012, 3, 1);
        java.sql.Date date1 = new java.sql.Date(cal1.getTimeInMillis());

        Calendar cal2 = new GregorianCalendar(2013, 2, 31);
        java.sql.Date date2 = new java.sql.Date(cal2.getTimeInMillis());

        PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement(ПереносДанныхУниверсал.getSQLtabel());
        pst.setString(1, "ООО Меркурий");
        pst.setDate(2, date2);
        pst.setDate(3, date1);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            final long codeDoc = Util.getUnicumID();
            insertDRtabel(codeDoc);

            do {
               ПереносДанныхУниверсал.insertSostavTabel(codeDoc, rs, "Альфа", "А2");
            } while (rs.next());
        }

        rs.close();
        pst.close();

        return "Ok";
    }

    private void insertDR(final long codeDoc) throws SQLException {
        Calendar cal1 = new GregorianCalendar(2013, 3, 1);
        java.sql.Date date = new java.sql.Date(cal1.getTimeInMillis());

        String insertDoc = "INSERT INTO T.\"Документ_Реквизит\" (\"КодДок\",\"Документ\",\"Предприятие\",\"Дата\",\"Период\",\"Автор\",\"Группа\",\"Изменено\",\"Комментарий\") VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(insertDoc);
        pst.setLong(1, codeDoc);
        pst.setString(2, "Документ.НачислениеУдержание");
        pst.setString(3, "Альфа");
        pst.setDate(4, date);
        pst.setString(5, "Апрель 2013");
        pst.setString(6, Пользователь.Код());
        pst.setString(7, Пользователь.РабочаяГруппа);
        pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
        pst.setString(9, "Перенос данных для среднего заработка из ООО Меркурий");
        pst.executeUpdate();
    }
    
    private void insertDRtabel(final long codeDoc) throws SQLException {
        Calendar cal1 = new GregorianCalendar(2013, 3, 1);
        java.sql.Date date = new java.sql.Date(cal1.getTimeInMillis());

        String insertDoc = "INSERT INTO T.\"Документ_Реквизит\" (\"КодДок\",\"Документ\",\"Предприятие\",\"Дата\",\"Период\",\"Автор\",\"Группа\",\"Изменено\",\"Комментарий\") VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(insertDoc);
        pst.setLong(1, codeDoc);
        pst.setString(2, "Документ.ПерерасчетЗарПлаты");
        pst.setString(3, "Альфа");
        pst.setDate(4, date);
        pst.setString(5, "Апрель 2013");
        pst.setString(6, Пользователь.Код());
        pst.setString(7, Пользователь.РабочаяГруппа);
        pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
        pst.setString(9, "Перенос данных для среднего заработка из ООО Меркурий");
        pst.executeUpdate();
    }

    private void insertSostav(final long codeDoc, ResultSet rs) throws SQLException {
        if (!ПереносДанныхУниверсал.isFind("Альфа", "А2" + rs.getString("ТабНомер"))) {
            return;
        }

        String insertDoc = "INSERT INTO T.\"Документ_ШифрЗарплата\" (\"КодДок\",\"Кортеж\",\"Месяц\",\"ТабНомер\",\"ДатаС\",\"ДатаПо\",\"Шифр\",\"Сумма\",\"Дни\",\"Автор\",\"Группа\",\"Изменено\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(insertDoc);
        pst.setLong(1, codeDoc);
        pst.setLong(2, Util.getUnicumID());
        pst.setString(3, "Апрель 2013");
        pst.setString(4, "А2" + rs.getString("ТабНомер"));
        pst.setDate(5, rs.getDate("ДатаС"));
        pst.setDate(6, rs.getDate("ДатаПо"));
        pst.setString(7, rs.getString("Шифр"));
        pst.setBigDecimal(8, rs.getBigDecimal("Сумма"));
        pst.setShort(9, rs.getShort("Дни"));
        pst.setString(10, Пользователь.Код());
        pst.setString(11, Пользователь.РабочаяГруппа);
        pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
        pst.executeUpdate();
    }
}
