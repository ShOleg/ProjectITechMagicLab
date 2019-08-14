package MyPrg;

import technopolis.MainFrame;
import technopolis.action.SuperAction;
import technopolis.util.Util;

import java.sql.PreparedStatement;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.03.11
 * Time: 9:12
 */
public class FillPersonalFileFamily extends SuperAction {
	@Override
	public boolean runAction() {


		try {
			String pref = "ЧС";
			long code = Util.getUnicumID();
			//UPDATE T."Картотека_ЛичноеДело_Семья" SET "Код" = NULL
			/*
			view
			select "Код" FROM T."Картотека_ЛичноеДело_Семья"
			gROUP BY "Код"
			HAVING COUNT("Код") >1
			*/
			String delete = "DELETE FROM T.\"Картотека_ЛичноеДело_Семья\" WHERE \"ДатаРождения\" IS NULL AND \"Имя\" IS NULL AND \"Тип\" IS NULL";
			String update1 = "UPDATE T.\"Картотека_ЛичноеДело_Семья\" SET \"Код\" = '1' " +
			                 " WHERE (\"Код\" IS NULL OR \"Код\" = '')";
			//
//			String update = "UPDATE T.\"Картотека_ЛичноеДело_Семья\" as F1 SET \"Код\" = 'ЧС' || RTRIM(CHAR(" +
//			                "(SELECT count(*) FROM T.\"Картотека_ЛичноеДело_Семья\" as F2 WHERE F2.\"КодЧел\" < F1.\"КодЧел\" AND F2.\"Код\" = '1')))";
			String update = "UPDATE T.\"Картотека_ЛичноеДело_Семья\" as F1 SET \"Код\" = 'ЧС'" +
			                " || RTRIM(CHAR(" +
			                "(SELECT count(*) FROM T.\"Картотека_ЛичноеДело_Семья\" as F2 WHERE F2.\"КодЧел\" < F1.\"КодЧел\" AND F2.\"Код\" = '1')" +
			                " +(1000000 * (SELECT count(*) + 1 FROM T.\"Картотека_ЛичноеДело_Семья\" as F2 WHERE F2.\"КодЧел\" = F1.\"КодЧел\" AND F2.\"ДатаРождения\" < COALESCE(F1.\"ДатаРождения\",CURRENT_DATE) AND F2.\"Код\" = '1')) " +
			                " +(10000000 * (SELECT count(*) + 1 FROM T.\"Картотека_ЛичноеДело_Семья\" as F2 WHERE F2.\"КодЧел\" = F1.\"КодЧел\" AND COALESCE(F2.\"ДатаРождения\",CURRENT_DATE) = COALESCE(F1.\"ДатаРождения\",CURRENT_DATE) AND COALESCE(F2.\"Имя\",'') < COALESCE(F1.\"Имя\",'') AND F2.\"Код\" = '1')) " +
			                " +(100000000 * (SELECT count(*) + 1 FROM T.\"Картотека_ЛичноеДело_Семья\" as F2 WHERE F2.\"КодЧел\" = F1.\"КодЧел\" AND COALESCE(F2.\"ДатаРождения\",CURRENT_DATE) = COALESCE(F1.\"ДатаРождения\",CURRENT_DATE) AND COALESCE(F2.\"Имя\",'') = COALESCE(F1.\"Имя\",'') AND COALESCE(F2.\"Тип\",'') < COALESCE(F1.\"Тип\",'') AND F2.\"Код\" = '1')) " +
			                " +(100000 * (SELECT count(*) + 1 FROM T.\"Картотека_ЛичноеДело_Семья\" as F2 WHERE F2.\"КодЧел\" = F1.\"КодЧел\" AND COALESCE(F2.\"ДатаРождения\",CURRENT_DATE) = COALESCE(F1.\"ДатаРождения\",CURRENT_DATE) AND COALESCE(F2.\"Имя\",'') = COALESCE(F1.\"Имя\",'') AND COALESCE(F2.\"Тип\",'') = COALESCE(F1.\"Тип\",'') AND F2.\"Изменено\" < F1.\"Изменено\" AND F2.\"Код\" = '1')) " +

			                ")) ";


//			select "КодЧел","ДатаРождения","Имя","Тип","Изменено",COUNT(*) FROM T."Картотека_ЛичноеДело_Семья"
//			gROUP BY "КодЧел","ДатаРождения","Имя","Тип","Изменено"
//			                                  + (RANDOM() *  )
//			UPDATE T."МоеПредприятие_РабочаяГруппа"  AS M1 SET "Порядок" =
//                (SELECT count(*) FROM T."МоеПредприятие_РабочаяГруппа" as M2 WHERE m2."Код" < m1."Код" )

			PreparedStatement pst;
			pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(delete);
			pst.executeUpdate();
			pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(update1);
			pst.executeUpdate();
			pst = MainFrame.getDB_ТехКорпорация().createPreparedStatement(update);
			pst.executeUpdate();
			pst.close();
			Util.showMessageDialog("Всё выполнено");
		} catch (Exception e) {
			e.printStackTrace();
			Util.showErrorDialog(e);
		}

		Random rnd = new Random();
		rnd.nextInt(10);
		return false;
	}
}
