// Пример выполнения файла js.
// Технополис 2017

var Jewel = Java.type("Jewel.Master");
var Jewel = new Jewel();

var day = new Date().getDay();

switch (day ) {
 
  case 1:
	Jewel.execObject("МоеПредприятие.Корпорация")
	break;
  case 2:
	Jewel.execAction("Калейдоскоп.Часы")
    	break;
  case 3:
	Jewel.execSQL("INSERT INTO T.\"Анонс\" (\"Объект\",\"Тема\") VALUES ('Пример DayWeek','Эта строка вставлена кодом js')")
    	break;
  case 5:
	Jewel.execAction("Отчет.СобытияДня")
	break;
  default:
	Jewel.execAction("Сервис.Калькулятор")

}
