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
public class ПереносДанныхУниверсал extends SuperAction {

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
                } else {
                    JOptionPane.showInternalMessageDialog(technopolis.MainFrame.getDeskTop(), "Действие выполнено.", "Внимание", JOptionPane.INFORMATION_MESSAGE);
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
        Calendar cal1 = new GregorianCalendar(2012, 1, 1);
        java.sql.Date date1 = new java.sql.Date(cal1.getTimeInMillis());

        Calendar cal2 = new GregorianCalendar(2013, 1, 3);
        java.sql.Date date2 = new java.sql.Date(cal2.getTimeInMillis());

        PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement(getSQL());

        pst.setString(1, "Универсал ОАО");
        pst.setDate(2, date2);
        pst.setDate(3, date1);

        pst.setString(4, "Универсал ОАО");
        pst.setDate(5, date2);
        pst.setDate(6, date1);

        pst.setString(7, "Универсал ОАО");
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
        Calendar cal1 = new GregorianCalendar(2012, 1, 1);
        java.sql.Date date1 = new java.sql.Date(cal1.getTimeInMillis());

        Calendar cal2 = new GregorianCalendar(2013, 1, 3);
        java.sql.Date date2 = new java.sql.Date(cal2.getTimeInMillis());

        PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement(getSQLtabel());

        pst.setString(1, "Универсал ОАО");
        pst.setDate(2, date2);
        pst.setDate(3, date1);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            final long codeDoc = Util.getUnicumID();

            insertDRtabel(codeDoc);

            do {
                insertSostavTabel(codeDoc, rs, "Универсал ООО", "У");
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
        pst.setString(3, "Универсал ООО");
        pst.setDate(4, date);
        pst.setString(5, "Апрель 2013");
        pst.setString(6, Пользователь.Код());
        pst.setString(7, Пользователь.РабочаяГруппа);
        pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
        pst.setString(9, "Перенос данных для среднего заработка из Универсал ОАО");
        pst.executeUpdate();
        pst.close();
    }

    private void insertDRtabel(final long codeDoc) throws SQLException {
        Calendar cal1 = new GregorianCalendar(2013, 3, 1);
        java.sql.Date date = new java.sql.Date(cal1.getTimeInMillis());

        String insertDoc = "INSERT INTO T.\"Документ_Реквизит\" (\"КодДок\",\"Документ\",\"Предприятие\",\"Дата\",\"Период\",\"Автор\",\"Группа\",\"Изменено\",\"Комментарий\") VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(insertDoc);
        pst.setLong(1, codeDoc);
        pst.setString(2, "Документ.ПерерасчетЗарПлаты");
        pst.setString(3, "Универсал ООО");
        pst.setDate(4, date);
        pst.setString(5, "Апрель 2013");
        pst.setString(6, Пользователь.Код());
        pst.setString(7, Пользователь.РабочаяГруппа);
        pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
        pst.setString(9, "Перенос данных для среднего заработка из Универсал ОАО");
        pst.executeUpdate();
        pst.close();
    }

    private void insertSostav(final long codeDoc, ResultSet rs) throws SQLException {

        if (!isFind("Универсал ООО", "У" + rs.getString("ТабНомер"))) {
            return;
        }

        String insertDoc = "INSERT INTO T.\"Документ_ШифрЗарплата\" (\"КодДок\",\"Кортеж\",\"Месяц\",\"ТабНомер\",\"ДатаС\",\"ДатаПо\",\"Шифр\",\"Сумма\",\"Дни\",\"Автор\",\"Группа\",\"Изменено\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(insertDoc);
        pst.setLong(1, codeDoc);
        pst.setLong(2, Util.getUnicumID());
        pst.setString(3, "Апрель 2013");
        pst.setString(4, "У" + rs.getString("ТабНомер"));
        pst.setDate(5, rs.getDate("ДатаС"));
        pst.setDate(6, rs.getDate("ДатаПо"));
        pst.setString(7, rs.getString("Шифр"));
        pst.setBigDecimal(8, rs.getBigDecimal("Сумма"));
        pst.setShort(9, rs.getShort("Дни"));
        pst.setString(10, Пользователь.Код());
        pst.setString(11, Пользователь.РабочаяГруппа);
        pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
        pst.executeUpdate();
        pst.close();
    }

    public static void insertSostavTabel(final long codeDoc, ResultSet rs, String predpr, String pref) throws SQLException {

        if (!isFind(predpr, pref + rs.getString("ТабНомер"))) {
            return;
        }

        String insertDoc = "INSERT INTO T.\"Документ_ТабельПерерасчет\" (\"КодДок\",\"Кортеж\",\"Месяц\",\"ТабНомер\",\"ДатаС\",\"ДатаПо\",\"Дата\",\"Дни\",\"Часы\",\"ВсегоДни\",\"ВсегоЧасы\",\"НормаДни\",\"НормаЧасы\",\"Автор\",\"Группа\",\"Изменено\",\"КодЧел\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(insertDoc);
        pst.setLong(1, codeDoc);
        pst.setLong(2, Util.getUnicumID());
        pst.setString(3, "Апрель 2013");
        pst.setString(4, pref + rs.getString("ТабНомер"));
        pst.setDate(5, rs.getDate("ДатаС"));
        pst.setDate(6, rs.getDate("ДатаПо"));
        pst.setDate(7, rs.getDate("Дата"));
        pst.setShort(8, rs.getShort("Дни"));
        pst.setInt(9, rs.getInt("Часы"));
        pst.setShort(10, rs.getShort("ВсегоДни"));
        pst.setInt(11, rs.getInt("ВсегоЧасы"));
        pst.setShort(12, rs.getShort("НормаДни"));
        pst.setInt(13, rs.getInt("НормаЧасы"));
        pst.setString(14, Пользователь.Код());
        pst.setString(15, Пользователь.РабочаяГруппа);
        pst.setTimestamp(16, new Timestamp(System.currentTimeMillis()));
        pst.setLong(17, rs.getLong("КодЧел"));
        pst.executeUpdate();
        pst.close();
    }

    public static boolean isFind(String predpr, String tabNomer) throws SQLException {
        boolean ret = false;
        String SQL = "SELECT COUNT(\"ТабНомер\") AS \"CNT\" FROM T.\"Картотека_Сотрудник\" WHERE \"Предприятие\"=? AND \"ТабНомер\"=?";
        PreparedStatement pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(SQL);
        pst.setString(1, predpr);
        pst.setString(2, tabNomer);
        ResultSet rs = pst.executeQuery();
        if (rs.next() && rs.getInt("CNT") > 0) {
            ret = true;
        }
        rs.close();
        pst.close();

        return ret;
    }

    public static String getSQL() {
        String SQL = "SELECT SUBSTR(\"ТабНомер\", 3, LENGTH(\"ТабНомер\")-2) AS \"ТабНомер\", 'ИсклО' AS \"Шифр\", \"ДатаС\", \"ДатаПо\", SUM(\"Дни\") AS \"Дни\", SUM(\"Сумма\") AS \"Сумма\", 0 AS \"Месяц\" "
                + "   FROM T.\"Документ_Зарплата\" "
                + "   WHERE  \"ТабНомер\" IN (select \"ТабНомер\" from t.\"Картотека_Сотрудник\"           where \"Предприятие\" = ?            ) AND "
                + "          \"Шифр\"     IN (select \"Состав\"   from t.\"МоеПредприятие_ПараметрСписок\" where \"Имя\"         = 'ПособиеФСС_ИсклОплачДни'  ) AND "
                + "          \"Шифр\" NOT IN (select \"Состав\"   from t.\"МоеПредприятие_ПараметрСписок\" where \"Имя\"         = 'ПособиеФСС_ИсклНеОплачДни') AND "
                + "          \"ДатаС\" <= ? AND \"ДатаПо\" >= ? "
                + "   GROUP BY \"ТабНомер\", \"ДатаС\", \"ДатаПо\" "
                + "   HAVING SUM(\"Сумма\") <> 0"
                + "   UNION ALL"
                + "    SELECT SUBSTR(\"ТабНомер\", 3, LENGTH(\"ТабНомер\")-2) AS \"ТабНомер\", 'ИсклБО' AS \"Шифр\", \"ДатаС\", \"ДатаПо\", SUM(\"Дни\") AS \"Дни\", SUM(\"Сумма\") AS \"Сумма\", 0 AS \"Месяц\" "
                + "   FROM T.\"Документ_Зарплата\" "
                + "   WHERE  \"ТабНомер\" IN (select \"ТабНомер\" from t.\"Картотека_Сотрудник\"           where \"Предприятие\" = ?            ) AND "
                + "          \"Шифр\"     IN (select \"Состав\"   from t.\"МоеПредприятие_ПараметрСписок\" where \"Имя\"         = 'ПособиеФСС_ИсклНеОплачДни'  ) AND "
                + "          \"Шифр\" NOT IN (select \"Состав\"   from t.\"МоеПредприятие_ПараметрСписок\" where \"Имя\"         = 'ПособиеФСС_ИсклОплачДни') AND "
                + "          \"ДатаС\" <= ? AND \"ДатаПо\" >= ? "
                + "   GROUP BY \"ТабНомер\", \"ДатаС\", \"ДатаПо\" "
                + "   HAVING SUM(\"Дни\") <> 0"
                + "   UNION ALL"
                + "   SELECT SUBSTR(\"ТабНомер\", 3, LENGTH(\"ТабНомер\")-2) AS \"ТабНомер\", 'ЗарПл' AS \"Шифр\", MIN(\"ДатаС\") AS \"ДатаС\", MAX(\"ДатаПо\") AS \"ДатаПо\", 0 AS \"Дни\", SUM(\"Сумма\") AS \"Сумма\", MONTH(\"ДатаС\") "
                + "   FROM T.\"Документ_Зарплата\" "
                + "   WHERE  \"ТабНомер\" IN (select \"ТабНомер\" from t.\"Картотека_Сотрудник\"           where \"Предприятие\" = ?          ) AND "
                + "          \"Шифр\"     IN (select \"Состав\"   from t.\"МоеПредприятие_ПараметрСписок\" where \"Имя\"         = 'Доход_средний_отпуск'  ) AND "
                + "          \"ДатаС\" <= ? AND \"ДатаПо\" >= ? "
                + "   GROUP BY \"ТабНомер\", MONTH(\"ДатаС\") "
                + "   HAVING SUM(\"Сумма\") <> 0";
        return SQL;
    }

    public static String getSQLtabel() {
        String SQL = "SELECT \"КодЧел\", SUBSTR(\"ТабНомер\", 3, LENGTH(\"ТабНомер\")-2) AS \"ТабНомер\",\"ДатаС\",\"ДатаПо\",\"Дата\",SUM(\"Дни\") AS \"Дни\", SUM(\"Часы\") AS \"Часы\", SUM(\"ВсегоДни\") AS \"ВсегоДни\", SUM(\"ВсегоЧасы\") AS \"ВсегоЧасы\", "
                + "      SUM(\"НормаДни\") AS \"НормаДни\", SUM(\"НормаЧасы\") AS \"НормаЧасы\", count(*) "
                + "   FROM T.\"Документ_ЗарплатаТабель\" "
                + "   WHERE  \"ТабНомер\" IN (select \"ТабНомер\" from t.\"Картотека_Сотрудник\" where \"Предприятие\"= ?) AND "
                + "          \"ДатаС\" <= ?  AND \"ДатаПо\" >= ? "
                + "   GROUP BY \"КодЧел\",\"ТабНомер\",\"ДатаС\",\"ДатаПо\",\"Дата\" "
                + "   HAVING SUM(\"Дни\") <> 0 or SUM(\"Часы\") <>0 or SUM(\"ВсегоДни\") <>0 or SUM(\"ВсегоЧасы\")<>0 or SUM(\"НормаДни\")<>0 or SUM(\"НормаЧасы\")<>0";

        return SQL;
    }
}
