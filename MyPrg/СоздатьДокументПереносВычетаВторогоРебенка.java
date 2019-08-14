/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyPrg;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ParameterRow;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.jbcl.layout.VerticalFlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import technopolis.action.ActionFrame;
import technopolis.action.SuperAction;
import technopolis.swing.DatePeriodPanel;
import technopolis.swing.JDicPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import technopolis.period.Period;
import technopolis.модель.Пользователь;

/**
 *
 * @author Олег
 */
public class СоздатьДокументПереносВычетаВторогоРебенка extends SuperAction {

    private java.text.SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private String шифрыИсточники = "'ВчДети','ВчДети2','ВчДети2о'";
    private String шифрыПолучатели = "'ВчДетиВ','ВчДетиВ2','ВчДетиВо'";
    boolean flDok = false;

    @Override
    public boolean runAction() {
        final JDicPanel entPanel = new JDicPanel("МоеПредприятие.Корпорация", "Код", "Предприятие");
        entPanel.setValue(getString("enter"));
        final JDicPanel datePanel = new JDicPanel("Словарь.Период", "Код", "Документ переноса будет создан на ");
        datePanel.setValue(getString("datePanel"));

        final DatePeriodPanel dpp = new DatePeriodPanel();

        JLabel lblShIst = new JLabel("Шифры источники :" + шифрыИсточники);
        JLabel lblShPr = new JLabel("Шифры получатели :" + шифрыПолучатели);

        JPanel param = new JPanel(new VerticalFlowLayout());
        param.add(entPanel);
        param.add(dpp);
        param.add(datePanel);
        param.add(lblShIst);
        param.add(lblShPr);

        JPanel mainP = new JPanel(new VerticalFlowLayout());
        mainP.add(param, BorderLayout.NORTH);

        ActionFrame af = new ActionFrame(this.getDealerAction(), mainP);
        af.addMakeAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    saveSetting("enter", entPanel.getValue());
                    saveSetting("datePanel", datePanel.getValue());

                    work(entPanel.getValue(), dpp.getStartDate(), dpp.getEndDate(), datePanel.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(СоздатьДокументПереносВычетаВторогоРебенка.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //af.showFrame();
        return false;
    }

    private void work(String predpr, java.util.Date start, java.util.Date end, String mon) throws SQLException {
        long kodDok = technopolis.utility.UniqueCode.get();
        boolean fl = true;


        Column colPredpr = new Column("PREDPR", null, Variant.STRING);
        Column colStart = new Column("START", null, Variant.DATE);
        Column colEnd = new Column("END", null, Variant.DATE);

        ParameterRow p = new ParameterRow();
        p.setColumns(new Column[]{colPredpr, colStart, colEnd});

        p.setString("PREDPR", predpr);
        p.setDate("START", new java.sql.Date(start.getTime()));
        p.setDate("END", new java.sql.Date(end.getTime()));

        String SQL = "SELECT Z.\"ТабНомер\", Z.\"Шифр\", (Z.\"Сумма\" / Z.\"Количество\") AS \"Summa\" "
                + "   FROM T.\"Документ_Зарплата\" AS \"Z\", T.\"Документ_Реквизит\" AS \"D\" "
                + "   WHERE D.\"КодДок\" = Z.\"КодДок\" "
                + "     AND D.\"Предприятие\" =: PREDPR "
                + "     AND Z.\"Шифр\" IN (" + шифрыИсточники + ")"
                + "     AND Z.\"Количество\" >= 2"
                + "     AND Z.\"РасчетС\" >= :START AND Z.\"РасчетС\" <= :END";

        SQL = "SELECT Q.\"ТабНомер\", Q.\"Шифр\", SUM(Q.\"Summa\") AS \"Сумма\" FROM (" + SQL + ") AS Q GROUP BY Q.\"ТабНомер\", Q.\"Шифр\"";

        QueryDataSet q = new QueryDataSet();
        q.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(technopolis.MainFrame.getDB_ТехКорпорация(), SQL, p, true, Load.ALL));
        q.executeQuery();

        if (q.getRowCount() > 0) {

            do {

                Period period = technopolis.period.PeriodUtil.getPeriod(mon);

                if (fl) {
                    insertDR(kodDok, period, predpr, new java.sql.Date(start.getTime()), new java.sql.Date(end.getTime()));
                    fl = false;

                    flDok = true;
                }

                insertSostav(kodDok, q.getString("ТабНомер"), q.getString("Шифр"), q.getBigDecimal("Сумма").multiply(new BigDecimal(-1)), period, new BigDecimal(-1));

                String shifr = "";
                if (q.getString("Шифр").equals("ВчДети")) {
                    shifr = "ВчДетиВ";
                } else if (q.getString("Шифр").equals("ВчДети2")) {
                    shifr = "ВчДетиВ2";
                } else if (q.getString("Шифр").equals("ВчДети2о")) {
                    shifr = "ВчДети2Во";
                }

                insertSostav(kodDok, q.getString("ТабНомер"), shifr, q.getBigDecimal("Сумма"), period, new BigDecimal(1));

            } while (q.next());
        }

        q.close();

        if (flDok) {
            JOptionPane.showMessageDialog(technopolis.MainFrame.getDeskTop(), "Документ создан", "Внимание", JOptionPane.CLOSED_OPTION);
        } else {
            JOptionPane.showMessageDialog(technopolis.MainFrame.getDeskTop(), "Нет данных для создания документа", "Внимание", JOptionPane.CLOSED_OPTION);
        }


    }

    private void insertDR(long kodDok, Period period, String predpr, java.sql.Date dStart, java.sql.Date dEnd) {
        String dS = formatter.format(dStart);
        String dPo = formatter.format(dEnd);

        String SQL = "INSERT INTO T.\"Документ_Реквизит\" (\"КодДок\",\"Документ\",\"Комментарий\",\"Период\",\"ДатаС\",\"ДатаПо\",\"Подразделение\",\"Предприятие\",\"Автор\",\"Группа\",\"Изменено\",\"Дата\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement(SQL);
        try {
            pst.setLong(1, kodDok);
            pst.setString(2, "Документ.НачислениеУдержание");
            pst.setString(3, "Перенос суммы вычетов за период с:"+dS+" по:"+dPo+" на второго иждевенца с шифров ВчДети,ВчДети2,ВчДети2о на ВчДетиВ,ВчДетиВ2,ВчДетиВо");
            pst.setString(4, period.getCode());
            pst.setDate(5, period.getDateStart());
            pst.setDate(6, period.getDateEnd());
            pst.setString(7, "");
            pst.setString(8, predpr);
            pst.setString(9, Пользователь.Код());
            pst.setString(10, Пользователь.РабочаяГруппа);
            pst.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
            pst.setDate(12, period.getDateEnd());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }

    private void insertSostav(long kodDok, String tabNomer, String shifr, BigDecimal summa, Period period, BigDecimal qnt) {
        String SQL = "INSERT INTO T.\"Документ_ШифрЗарплата\" (\"КодДок\",\"Кортеж\",\"ТабНомер\",\"ДатаС\",\"ДатаПо\",\"Шифр\",\"Сумма\",\"Месяц\",\"Период\",\"Комментарий\",\"Автор\",\"Группа\",\"Изменено\",\"Количество\",\"Вкл\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement(SQL);
        try {
            pst.setLong(1, kodDok);
            pst.setLong(2, technopolis.utility.UniqueCode.get());
            pst.setString(3, tabNomer);
            pst.setDate(4, period.getDateStart());
            pst.setDate(5, period.getDateEnd());
            pst.setString(6, shifr);
            pst.setBigDecimal(7, summa);
            pst.setString(8, period.getCode());
            pst.setString(9, period.getCode());
            pst.setString(10, "");
            pst.setString(11, Пользователь.Код());
            pst.setString(12, Пользователь.РабочаяГруппа);
            pst.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
            pst.setBigDecimal(14, qnt);
            pst.setString(15, "Нет");
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }
}
