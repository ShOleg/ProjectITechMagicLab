// Пример генератора формы ввода.
// Технополис 2017
var Jewel = Java.type("Jewel.Master");
var W0 = new Jewel();
var W1 = new Jewel(act);
var W2 = new Jewel(act);
var style = "-fx-font-size:40px";

var prop = '{"title":"Русский заголовок окна", "screen":"12,12,44,44"}';
W1.winOpen(prop);
W1.say("методы позволяют получить данные", style);
W1.say("данных текущего объекта,");
W1.say("метод позволяет получить данные","-fx-font-size:28px");

var v1 = '{           "type":"label", "value":"Строка",  "grid":"0,0"}';
var v2 = '{ "id":"v2","type":"text" , "value":"22222",   "grid":"1,0"}';

var v3 = '{           "type":"label", "value":"numberPattern", "grid":"2,0"}';
var v4 = '{ "id":"v4","type":"number","value":1234567890.12345, "pattern":"/d{0,12}([/.]/d{0,5})?",  "grid":"3,0"}';
var vbtn1= '{         "type":"button","value":"showNumberPattern","action":"funcNumberPattern",      "grid":"4,0"}';

var v111 = '{           "type":"label",  "value":"numberFormat",                    "grid":"0,1"}';
var v21 = '{ "id":"v21","type":"number", "value":11111.111, "format":"99999.999",   "grid":"1,1"}';
var vbtn2= '{           "type":"button", "value":"show","action":"funcNumberFormat","grid":"2,1"}';

var v31 = '{            "type":"label", "value":"numberInt",  "grid":"3,1"}';
var v41 = '{ "id":"v41","type":"number","value":100,          "grid":"4,1"}';
var vbtn3= '{           "type":"button","value":"showNumberInt","action":"funcNumberInt","grid":"5,1"}';

var v5 = '{           "type":"label", "value":"numberspinner",  "grid":"0,2"}';
var v6 = '{ "id":"v6","type":"number","value":5,         "grid":"1,2"}';
var vbtn4= '{         "type":"button","value":"showSumma","action":"funcSumma","grid":"4,2"}';

var v7 = '{           "type":"label", "value":"дата с",  "grid":"0,3"}';
var v8 = '{ "id":"v8","type":"date" , "value":"",        "grid":"1,3"}';
var vbtn5= '{         "type":"button","value":"showDateS","action":"funcDate","grid":"2,3","alignment":"right"}';

var v9 = '{           "type":"label", "value":"дата по",     "grid":"0,4"}';
var v10= '{ "id":"v10","type":"date", "value":"",            "grid":"1,4"}';

var v11= '{ "id":"v11","type":"сheckBox","value":"сheckBox", "grid":"0,5"}';
var vbtn6= '{          "type":"button","value":"showCheckBox","action":"funcCheckBox","grid":"1,5","alignment":"right"}';

var v13= '{ "type":"button", "value":"Ok"    , "action":"funcForm",     "grid":"0,6","alignment":"right"}';
var v14= '{ "type":"button", "value":"Cancel", "action":"funcCancel",   "grid":"1,6","alignment":"right"}';
W1.form( v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v13, v14, v111, v21, v31, v41, vbtn1, vbtn2, vbtn3, vbtn4, vbtn5, vbtn6);

function funcForm() {
	var v0= '{            "type":"label", "value":"лэйбл",             "grid":"0,0","alignment":"right"}';
	var v1= '{ "id":"v12","type":"text" , "value":"значение",          "grid":"1,0"}';
	var v2= '{            "type":"button","value":"alert",             "grid":"0,1", "action":"funcAlert","alignment":"right"}';
	var v22='{            "type":"button","value":"execObject",        "grid":"1,1", "action":"funcExecObject","alignment":"right"}';
	var v23='{ "id":"v23","type":"button","value":"Unique",            "grid":"1,2", "action":"funcUnique","alignment":"right"}';
	var v24='{            "type":"button","value":"User",              "grid":"1,3", "action":"funcUser","alignment":"right"}';
	var v3= '{            "type":"button","value":"Path",              "grid":"0,2", "action":"funcPath","alignment":"right"}';
	var v4= '{            "type":"button","value":"SQL",               "grid":"0,3", "action":"funcSQL","alignment":"right"}';
	var v5= '{            "type":"button","value":"ChoiceDialog",      "grid":"0,4", "action":"funcChoiceDialog","alignment":"right"}';
	var v6= '{            "type":"button","value":"TextInputDialog",   "grid":"0,5", "action":"funcTextInputDialog","alignment":"right"}';
	var v7= '{            "type":"button","value":"prompt",            "grid":"0,6", "action":"funcPrompt","alignment":"right"}';
	var v8= '{            "type":"button","value":"confirm",           "grid":"0,7", "action":"funcConfirm"}';
	var v10='{            "type":"button","value":"openFile",          "grid":"0,8", "action":"funcOpenFile"}';
	var v11='{            "type":"button","value":"table",             "grid":"0,9", "action":"funcTable"}';
	var v12='{            "type":"button","value":"Панорама",          "grid":"1,4", "action":"funcPanorama"}';
	var v13='{            "type":"button","value":"Автобот",           "grid":"1,5", "action":"funcAutobot"}';
	var v14='{            "type":"button","value":"renameFile",        "grid":"1,6", "action":"funcRenameFile"}';

	W1.form(v0,v1,v2,v3,v4,v5,v6,v7,v8,v10,v22,v23,v24,v11,v12,v13,v14)
}



function funcRenameFile(){
	//W1.renameFile("*","newName.js");
	W1.renameFile("*","*");
}


function funcOpenFile(){
	W1.openFile(W1.getFileServer()+"/Веб-страница на рабочем столе.url");
	W1.openFile("%AppServer%/Shape/Статистика по ТехКорпорации.ott");
	//W1.openFile("C:/User/Desktop/Java DecimalFormat.url");
}

function funcExecObject() {
	W1.execObject("Картотека.Актив");
}

function funcPath() {
	W1.alert("path ",W1.getDBMS()+"; "+W1.getAppServer()+"; "+W1.getDataServer()+";"+W1.getFileServer(),"-fx-font-size:22px");
        W1.say(W1.getDBMS()+"; "+W1.getAppServer()+"; "+W1.getDataServer()+";"+W1.getFileServer());
}

function funcAlert() {
	W1.alert("Надпись ",W1.getText("v12"),"-fx-font-size:36px");
        W1.say(W1.getText("v12"));
}

function funcChoiceDialog() {
	var xxx = W1.choiceList("Окно выбора","текст","Введите данные :","1","2","3","4","5");
	W1.alert("Что же выбрали ",xxx,"-fx-font-size:24px");
        W1.say(xxx);
}

function funcTextInputDialog() {
	var xxx = W1.textInputDialog("titleXXX","текст","Введите данные ?:",W1.getText("v2"));
	W1.alert(xxx);
        W1.say(xxx);
}

function funcPrompt() {
	var xxx = W1.prompt("Сколько вам лет ","100");
	W1.alert("Вам " + xxx + " лет!");
        W1.say(xxx);
}

function funcConfirm() {
	var xxx = W1.confirm("Вы - администратор?");
	W1.alert("Ответ " + xxx);
        W1.say(xxx);
}

function funcNumberPattern() {
	var xxx = W1.getNumber("v4");
	W1.alert(xxx);
}

function funcUnique() {
	var xxx = W1.getUnique();
	W1.alert(xxx);
}

function funcPanorama() {
	var xxx = W1.execPanorama("Test 1");

}

function funcAutobot() {
	var xxx = W1.execAutobot("Пример интерфейса расчёта");

}

function funcUser() {
	var xxx = W1.getUser();
	W1.alert(xxx);
}

function funcNumberInt() {
	var xxx = W1.getNumber("v41");
	W1.alert(xxx);
}

function funcNumberFormat() {
	var xxx = W1.getNumber("v21");
	W1.alert(xxx);
}

function funcSumma() {
	var x1 = W1.getNumber("v4");
	var x2 = W1.getNumber("v41");
	var x3 = W1.getNumber("v21");
	var x4 = W1.getNumber("v6");
	W1.alert(x1+x2+x3+x4);
}

function funcDate() {
	var date = W1.getDate("v8");
//        var date =  new Date();
//	W1.alert("день 1 = " + date.getDay() + " месяц = "+date.getMonth()+ " год="+date.getYear());
//	W1.alert("дата 2 = " + date.toString() + "день = " + date.getDay()  + " месяц = "+date.getMonth()+ " год="+(date.getYear()+1900) );
//	W1.alert("дата 3 = " + date.toString()  );
	W1.alert("дата 3 = " + date.toString()  + " месяц = "+date.getMonth()  );
}

function funcCheckBox() {
	var v = W1.getBoolean("v11");
	W1.alert(v);
}

function funcSQL() {
	var W100 = new Jewel();
	var userNW = '{ "title": "testDataRow", "screen":"2,2,50,20"}';
	var ret0 = W100.winOpen(userNW );
	var json = W100.getSelect("SELECT * FROM T.\"Словарь_ВидАктив\"");
        json = JSON.parse(json);

	W100.say(json[0].Код + ", "+json[0].Наименование,style);	
	W100.say(json[1].Код + ", "+json[1].Наименование,style);	
	W100.say(json[2].Код + ", "+json[2].Наименование,style);	

	W1.say(json[0].Код + ", "+json[0].Наименование);	
	W1.say(json[1].Код + ", "+json[1].Наименование);	
	W1.say(json[2].Код + ", "+json[2].Наименование,"-fx-background-color: white; -fx-font-size:  18px");	
}

function funcTable() {
	var W100 = new Jewel();
	var userNW = '{ "title": "testDataRow", "screen":"2,2,50,20"}';
	var ret0 = W100.winOpen(userNW );
	var json = W100.getSelect("SELECT * FROM T.\"Картотека_Сотрудник\"");
	 W100.table(json);
	 W1.table(json);
}

function funcCancel() {
           W1.winClose();
}	

function funcClear() {
           W1.winClear();
}	

function funcPrint() {
           W1.say("Шаланды полные кефали;В Одессу Костя приводил;И все биндюжники вставали;когда в пивную он входил;Синеет море за бульваром;каштан над городом цветет;наш Константин берет гитару;и тихим голосом поет;");
}	

//W1.getNumber("v4");	
//W1.getNumberSpinner("v6");
//var date = W1.getDate("v8");	
//date.getMonth();
//W1.getBoolean("v11");
