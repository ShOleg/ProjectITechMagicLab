// Импорт данных о расчетах с организациями из программы 1С
// Выполняется прием данных в формате JSON в БД Техкорпорация
// И создание первичных документов.

var Jewel = Java.type("Jewel.Master");
var W1 = new Jewel();
var inJSON;
var result;
var groupBy;
var logi="";

// группировка массива JSON по элементу NOM (Номер документа)
groupBy = function(xs, key) {
	return xs.reduce(function(rv, x) {
	(rv[x[key]] = rv[x[key]] || []).push(x);
	return rv;
	}, {});
};


var myArray = new Array()
myArray[0] = 'Счет'
myArray[1] = 'СчетФактура'
myArray[2] = 'Акт'
myArray[3] = 'Выйти'

var ret = W1.choice("Выполнить прием данных из 1С", myArray);


if(ret != "Выйти"){

        loadInFile();                           

	funcControl();

	if(logi==""){   // если нет ошибок во входном файле то выполняем прием данных

		result=groupBy(inJSON, 'NOM');    // группируем  по элементу NOM

		switch(ret) { 
			case "Счет": 
                		func("Д2", "Документ_СчетВыставленный")
				break;
			case "СчетФактура": 
				func("Х2", "Документ_АктСдачиРабот")
				break;
			case "Акт": 
				func("Ч2", "Документ_СчетФактураВыставленный")
				break;
		}

		W1.alert("Выполнено успешно");
 
	} else {

      		W1.alert("Ошибки во входном файле :\n "+ logi);

	}
}



//-----------------------------------------------------

function func(xKodDoc, tableName) {
	var kodDoc = "Д2";

  	var lengthJSON = Object.keys(inJSON).length;
  	var lengthNom = Object.keys(result).length;

       	for (var i = 0; i < lengthNom; i++) {              // цикл по номерам док-тов
		var nom = Object.keys(result)[i];
		var fl = true;
		var kodDoc = W1.getUnique();

               	for (var j = 0; j < lengthJSON; j++) {     // проходим по основному входному массиву данных, беря в работу только KODDOC и  тип Документа
			var nomer = inJSON[j].NOM;
			var koddoc = inJSON[j].KODDOC;

			if(nom==nomer && koddoc == xKodDoc){

				if(fl){
					var SQL = "INSERT INTO T.\"Документ_Реквизит\" (\"КодДок\",\"Дата\",\"Номер\", \"Организация\", \"Подразделение\",  \"Документ\",  \"Сумма\", \"Комментарий\", \"Предприятие\", \"ДатаОригинала\", \"НомерОригинала\", \"ВидОперации\",\"Изменено\",\"Автор\")  VALUES("+kodDoc+","+"'"+inJSON[j].DAY+ "','"+ inJSON[j].NOM+"','"+inJSON[j].ORG+"','"+inJSON[j].DEP+"','Документ.СчетВыставленный', 0, 'документ из 1С', 'Аэронавигация ЦС','"+inJSON[j].DAYOLD+"','"+inJSON[j].NOMOLD+"','"+inJSON[j].VIDOPER+"',CURRENT_TIMESTAMP,'"+ W1.getUser()+"')";
					W1.execSQL(SQL);

					fl = false;
				}

				var kortej = W1.getUnique();

				var SQL = "INSERT INTO T.\"" + tableName + "\" (\"КодДок\",\"Кортеж\", \"Количество\" ,\"Цена\", \"Сумма\", \"СуммаНДС\", \"НДС\",  \"СуммаВсего\",  \"Деятельность\",  \"СтатьяДоходов\",  \"КатегорияНДС\",  \"Договор\",   \"Операция\",  \"Подразделение\",  \"Изменено\",\"Автор\",\"Актив\") 	VALUES("+kodDoc+","+kortej+","+inJSON[j].FACT+","+inJSON[j].CENA+","+inJSON[j].SUMM+","+inJSON[j].SUMNDS+",'"+inJSON[j].NDS+"',"+inJSON[j].DSUM+",'"+inJSON[j].DAN+"','"+inJSON[j].DAD+"','"+inJSON[j].REM+"','"+inJSON[j].DOG+"','"+inJSON[j].OPER+"','"+inJSON[j].DEP+"',CURRENT_TIMESTAMP,'"+ W1.getUser()+"','"+inJSON[j].KOD+"')";
                                W1.execSQL(SQL);
			}
		}
		
	}

}


function funcControl() {

        var length = Object.keys(inJSON).length;

	for (var i = 0; i < length; i++) {
	
		var k_org = W1.getSelect("SELECT \"Код\" FROM T.\"Картотека_Организация\"  WHERE \"ИНН\" = '" + inJSON[i].INN + "' AND \"КПП\" = '" + inJSON[i].KPP + "'");
	        k_org = JSON.parse(k_org);

	        var lengthOrg = Object.keys(k_org).length;
		if (lengthOrg==0){
		    k_org = W1.getSelect("SELECT \"Код\" FROM T.\"Картотека_Организация\"  WHERE \"Код\" = '" + inJSON[i].ORG + "'");
		    k_org = JSON.parse(k_org);
                    lengthOrg = Object.keys(k_org).length;
		    if( lengthOrg==0){
			logi = logi +  inJSON[i].KODDOC + " - " + inJSON[i].DAY + " - " + inJSON[i].NOM + " - " + inJSON[i].ORG + "\n";
		    }	                                                                                                        
		}
	}  
}


function loadInFile(){
	var xJSON =  W1.readFile("1C.json");
        inJSON = JSON.parse(xJSON);
}







